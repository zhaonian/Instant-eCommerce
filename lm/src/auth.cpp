#include <boost/property_tree/json_parser.hpp>
#include <iostream>
#include "server.h"
#include "auth.h"


core::identity::identity(std::string const& error):
        m_error(error)
{
}

core::identity::identity(std::string const& workspace, std::string const& user_name, std::string const& iid):
        m_workspace(workspace), m_user_name(user_name), m_iid(iid)
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
core::identity::workspace_name() const
{
        return m_workspace;
}

void
core::identity::write_json(json_t& json) const
{
        json.put<std::string>("userIdentity.uid", m_iid);
}

std::string
core::identity::error() const
{
        return m_error;
}


core::identity
core::auth(central_server& server, std::string const& name, std::string const& password, std::string const& workspace)
{
        json_t user_info, result;
        user_info.put<std::string>("username", name);
        user_info.put<std::string>("password", password);
        if (!server.send_json_request(result, "/api/user/login/" + workspace, request_type::post, user_info)) {
                return identity(server.error());
        }
        try {
                return identity(result.get<std::string>("message"));
        } catch (...) {
                return identity(workspace, name, result.get<std::string>("userIdentity.uid"));
        }
}

bool
core::auth_create(central_server& server, std::string const& name, std::string const& password, std::string& error)
{
        json_t user_info, result;
        user_info.put<std::string>("username", name);
        user_info.put<std::string>("password", password);
        if (!server.send_json_request(result, "/api/user/create", request_type::post, user_info)) {
                error = server.error();
                return false;
        }

        try {
                error = result.get<std::string>("message");
                return false;
        } catch (...) {
                error.clear();
                return true;
        }
}

void
core::unauth(central_server& server, identity& id)
{
        json_t id_info, result;
        id.write_json(id_info);
        server.send_json_request(result, "/api/user/logout", request_type::post, id_info);
}
