#include <boost/property_tree/json_parser.hpp>
#include <iostream>
#include "server.h"
#include "auth.h"


core::identity::identity(std::string const& error, unsigned):
        m_error(error)
{
}

core::identity::identity(std::string const& iid):
        m_iid(iid)
{
}

std::string
core::identity::iid() const
{
        return m_iid;
}

bool
core::identity::is_valid() const
{
        return !m_iid.empty();
}

std::string
core::identity::error() const
{
        return m_error;
}


core::identity
core::auth(central_server& server, std::string const& name, std::string const& password)
{
        json_t user_info, result;
        user_info.put<std::string>("username", name);
        user_info.put<std::string>("password", password);
        if (!server.send_json_request(result, "/api/user/login", request_type::post, user_info)) {
                return identity(server.error(), 0);
        }

        try {
                std::string const& message = result.get<std::string>("message");
                return identity(message, 0);
        } catch (...) {
                return identity(result.get<std::string>("id"));
        }
}

core::identity
core::auth_create(central_server& server, std::string const& name, std::string const& password)
{
        json_t user_info, result;
        user_info.put<std::string>("username", name);
        user_info.put<std::string>("password", password);
        server.send_json_request(result, "/api/user/create", request_type::post, user_info);

        try {
                std::string const& message = result.get<std::string>("message");
                return identity(message, 0);
        } catch (...) {
                return identity(result.get<std::string>("id"));
        }
}

void
core::unauth(central_server& server, identity& id)
{
        json_t id_info, result;
        id_info.put<std::string>("id", id.iid());
        server.send_json_request(result, "/api/user/logout", request_type::post, id_info);
}
