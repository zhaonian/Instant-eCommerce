#ifndef SERVER_H
#define SERVER_H

#include <string>
#include <boost/asio.hpp>
#include <boost/property_tree/ptree.hpp>

namespace core
{

enum request_type
{
        get,
        post
};

typedef boost::property_tree::ptree     json_t;

class central_server
{
public:
        central_server();

        bool            connect(std::string const& host_name, unsigned port);
        bool            is_connected() const;
        bool            send_json_request(json_t& data, std::string const& path, request_type type, json_t const& json);
        std::string     error() const;
private:
        bool                            m_is_connected = false;
        std::string                     m_host_name;
        unsigned                        m_port;

        std::string                     m_error;
        json_t                          m_connection;
};

central_server*         get_central_server();

}

#endif // SERVER_H
