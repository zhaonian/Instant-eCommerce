#include <boost/asio.hpp>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>
#include <iostream>
#include "util.h"
#include "server.h"



static void
connect(boost::asio::ip::tcp::socket& sock, std::string const& host, unsigned port, std::string& error)
{
        try {
                sock.connect(boost::asio::ip::tcp::endpoint(boost::asio::ip::address_v4::from_string(host), port));
                error.clear();
        } catch (boost::system::system_error const& e) {
                error = e.what();
        }
}

static std::string
json_request_detail(std::string const& host, std::string const& path, std::string const& type, core::json_t const& json)
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
send_json_request(std::string const& host, unsigned port, std::string const& path, std::string const& type, core::json_t const& json)
{
        // establish connection.
        boost::asio::io_service service;
        boost::asio::ip::tcp::socket sock(service);

        std::string error;
        connect(sock, host, port, error);
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
parse_json_response(core::json_t& json, std::string const& response, std::string& error)
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
core::central_server::connect(std::string const& db_name, std::string const& host_name, unsigned port)
{
        m_host_name = host_name;
        m_port = port;

        send_json_request(m_connection, "/api/user/connection/" + db_name, request_type::get, json_t());
        m_is_connected = m_error.empty();
        return m_is_connected;
}

bool
core::central_server::is_connected() const
{
        return m_is_connected;
}

std::string
core::central_server::error() const
{
        return m_error;
}

bool
core::central_server::send_json_request(json_t& data, std::string const& path, request_type type, json_t const& json)
{
        std::string response = ::send_json_request(m_host_name, m_port, path, type == request_type::get ? "GET" : "POST", json);
        return parse_json_response(data, response, m_error);
}


core::central_server    g_server;

core::central_server*
core::get_central_server()
{
        return &g_server;
}
