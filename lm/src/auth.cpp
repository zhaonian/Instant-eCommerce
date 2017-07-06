#include <boost/property_tree/json_parser.hpp>
#include <iostream>
#include "server.h"
#include "auth.h"


core::identity::identity(std::string const& error):
        m_error(error)
{
}

core::identity::identity(std::string const& user_name, std::string const& iid):
        m_user_name(user_name), m_iid(iid)
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
core::identity::user_name() const
{
        return m_user_name;
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
                return identity(server.error());
        }

        try {
                return identity(result.get<std::string>("message"));
        } catch (...) {
                return identity(name, result.get<std::string>("id"));
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
                return identity(result.get<std::string>("message"));
        } catch (...) {
                return identity(name, result.get<std::string>("id"));
        }
}

void
core::unauth(central_server& server, identity& id)
{
        json_t id_info, result;
        id_info.put<std::string>("id", id.iid());
        server.send_json_request(result, "/api/user/logout", request_type::post, id_info);
}
