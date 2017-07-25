#ifndef SERVER_H
#define SERVER_H

#include <string>
#include <boost/asio.hpp>

#include "util.h"

namespace core
{

enum request_type
{
        get,
        post
};


class central_server
{
public:
        central_server();

        bool                    connect(std::string const& host_name, unsigned port, std::string& error);
        bool                    is_connected() const;
        std::string const&      server_version() const;
        bool                    send_json_request(util::json_t& data,
                                                  std::string const& path, request_type type, util::json_t const& json,
                                                  std::string& error);
private:
        struct server_info
        {
                std::string version_string;

                void import_json(util::json_t const& json)
                {
                        version_string = json.get<std::string>("versionString");
                }
        };

        bool                            m_is_connected = false;

        std::string                     m_host_name;
        unsigned                        m_port;

        server_info                     m_server_info;
};

central_server*         get_central_server();

}

#endif // SERVER_H
