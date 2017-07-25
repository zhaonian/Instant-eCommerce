#include <boost/asio.hpp>
#include <boost/lambda/bind.hpp>
#include <boost/lambda/lambda.hpp>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>
#include <iostream>
#include "util.h"
#include "server.h"



static void
connect(boost::asio::ip::tcp::socket& sock, boost::asio::io_service& service, std::string const& host, unsigned port, std::string& error)
{
        // Resolve the host name and service to a list of endpoints.
        boost::asio::ip::tcp::resolver::query query(host, std::to_string(port));
        boost::asio::ip::tcp::resolver::iterator iter = boost::asio::ip::tcp::resolver(service).resolve(query);

        // Set a deadline for the asynchronous operation. As a host name may
        // resolve to multiple endpoints, this function uses the composed operation
        // async_connect. The deadline applies to the entire operation, rather than
        // individual connection attempts.
        boost::asio::deadline_timer deadline(service);
        boost::posix_time::time_duration timeout(0, 0, 5);
        deadline.expires_at(boost::posix_time::pos_infin);
        deadline.expires_from_now(timeout);

        // Set up the variable that receives the result of the asynchronous
        // operation. The error code is set to would_block to signal that the
        // operation is incomplete. Asio guarantees that its asynchronous
        // operations will never fail with would_block, so any other value in
        // ec indicates completion.
        boost::system::error_code ec = boost::asio::error::would_block;

        // Start the asynchronous operation itself. The boost::lambda function
        // object is used as a callback and will update the ec variable when the
        // operation completes. The blocking_udp_client.cpp example shows how you
        // can use boost::bind rather than boost::lambda.
        boost::asio::async_connect(sock, iter, boost::lambda::var(ec) = boost::lambda::_1);

        // Block until the asynchronous operation has completed.
        do {
                service.run_one();
        } while (ec == boost::asio::error::would_block);

        // Determine whether a connection was successfully established. The
        // deadline actor may have had a chance to run and close our socket, even
        // though the connect operation notionally succeeded. Therefore we must
        // check whether the socket is still open before deciding if we succeeded
        // or failed.
        if (ec || !sock.is_open())
                error = boost::system::system_error(ec ? ec : boost::asio::error::operation_aborted).what();
        else
                error.clear();
}

static std::string
json_request_detail(std::string const& host, std::string const& path, std::string const& type, util::json_t const& json)
{
        std::stringstream ss;
        ss << type << ' ' << path << " HTTP/1.1" << "\r\n";
        ss << "Host: " << host << "\r\n";
        ss << "Accept: application/json" << "\r\n";
        ss << "Content-Type: application/json" << "\r\n";
        ss << "Connection: close\r\n";

        std::ostringstream oss;
        boost::property_tree::json_parser::write_json(oss, json, false);
        std::string const& s = oss.str();
        ss << "Content-Length: " << s.length() << "\r\n";

        ss << "\r\n";
        ss << s << "\r\n";
        ss << "\r\n";

        return ss.str();
}

static std::string
send_json_request(std::string const& host, unsigned port, std::string const& path, std::string const& type, util::json_t const& json)
{
        // establish connection.
        boost::asio::io_service service;
        boost::asio::ip::tcp::socket sock(service);

        std::string error;
        connect(sock, service, host, port, error);
        if (!error.empty()) {
                return "HTTP/1.1 502\r\n";
        }

        // send request.
        std::string const& request = json_request_detail(host, path, type, json);
        try {
                sock.send(boost::asio::buffer(request));
        } catch (boost::system::system_error const& e) {
                std::cout << e.what() << std::endl;
                return "";
        }

        // read response.
        boost::system::error_code ec;
        std::string response;
        do {
                char buf[1024];
                size_t bytes_transferred = sock.receive(boost::asio::buffer(buf), {}, ec);
                if (!ec) response.append(buf, buf + bytes_transferred);
        } while (!ec);

        return response;
}

static bool
parse_json_response(util::json_t& json, std::string const& response, std::string& error)
{
        error.clear();

        // extract error code.
        std::vector<std::string> const& lines = util::split(response, '\n');
        std::vector<std::string> const& request_fields = util::split(lines[0], ' ');
        if (request_fields[1] != "200" && request_fields[1] != "500") {
                error = request_fields[1];
                return false;
        }

        // skip header and extract messages.
        bool is_body = false;
        std::stringstream message;
        for (unsigned i = 1; i < lines.size(); i ++) {
                std::string const& line = lines[i];

                if (is_body) {
                        // extract body message.
                        while (++ i < lines.size() - 2) {
                                std::string const& line = lines[i];
                                message.write(line.c_str(), line.length() - 1);
                        }
                        break;
                }

                if (line.length() == 1)
                        is_body = true;
        }

        // parse into json object.
        try {
                boost::property_tree::read_json(message, json);
        } catch (boost::property_tree::json_parser_error const& e) {
                error = e.what();
                return false;
        }
        return true;
}

core::central_server::central_server()
{
}


bool
core::central_server::connect(std::string const& host_name, unsigned port, std::string& error)
{
        m_host_name = host_name;
        m_port = port;

        util::json_t info;
        send_json_request(info, "/api/server_info", request_type::get, util::json_t(), error);
        if (error.empty()) {
                try {
                        m_server_info.import_json(info);
                        m_is_connected = info.get<std::string>("status") == "1";
                } catch (...) {
                        error = "Invalid response";
                        m_is_connected = false;
                }
        } else {
                m_is_connected = false;
        }

        return m_is_connected;
}

bool
core::central_server::is_connected() const
{
        return m_is_connected;
}

std::string const&
core::central_server::server_version() const
{
        return m_server_info.version_string;
}

bool
core::central_server::send_json_request(util::json_t& data, std::string const& path, request_type type, util::json_t const& json, std::string& error)
{
        std::string response = ::send_json_request(m_host_name, m_port, path, type == request_type::get ? "GET" : "POST", json);
        if (parse_json_response(data, response, error)) {
                try {
                        error = data.get<std::string>("message");
                        return false;
                } catch (...) {
                        error.clear();
                        return true;
                }
        } else
                return false;
}

core::central_server    g_server;

core::central_server*
core::get_central_server()
{
        return &g_server;
}
