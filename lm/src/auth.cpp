#include <boost/property_tree/json_parser.hpp>
#include <iostream>
#include "util.h"
#include "server.h"
#include "auth.h"


core::identity::identity()
{
}

core::identity::identity(std::string const& workspace, std::string const& user_name):
        m_workspace(workspace), m_user_name(user_name)
{
}

std::string
core::identity::iid() const
{
        return m_iid;
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
core::identity::import_json(util::json_t const& json)
{
        m_iid = json.get<std::string>("uid");
}

util::json_t
core::identity::export_json() const
{
        util::json_t json;
        json.put<std::string>("uid", m_iid);
        return json;
}


core::permission::permission()
{
}

core::permission::permission(std::string const& category, std::string const& sub_category, unsigned code, std::string const& action):
        m_category(category), m_sub_category(sub_category), m_code(code), m_action(action)
{
}

std::string
core::permission::category() const
{
        return m_category;
}

std::string
core::permission::sub_category() const
{
        return m_sub_category;
}

unsigned
core::permission::code() const
{
        return m_code;
}

std::string
core::permission::action() const
{
        return m_action;
}

void
core::permission::import_json(util::json_t const & json)
{
}

util::json_t
core::permission::export_json() const
{
        util::json_t json;
        json.put<std::string>("permissionCategory", m_category);
        json.put<std::string>("permissionSubCategory", m_sub_category);
        json.put<unsigned>("permissionType", m_code);
        json.put<std::string>("actionDetail", m_action);
        return json;
}

core::user::user()
{
}

unsigned
core::user::uid() const
{
        return m_uid;
}

std::string
core::user::name() const
{
        return m_user_name;
}

void
core::user::import_json(util::json_t const& json)
{
}

util::json_t
core::user::export_json() const
{
}


core::identity
core::auth(central_server& server, std::string const& name, std::string const& password, std::string const& workspace, std::string& error)
{
        util::json_t user_info;
        user_info.put<std::string>("username", name);
        user_info.put<std::string>("password", password);

        identity iid(workspace, name);
        util::json_t result;
        if (!server.send_json_request(result, "/api/user/login/" + workspace, request_type::post, user_info, error))
                return iid;
        else
                iid.import_json(result);
}

bool
core::auth_create(central_server& server, std::string const& name, std::string const& password, std::string& error)
{
        util::json_t user_info;
        user_info.put<std::string>("username", name);
        user_info.put<std::string>("password", password);

        util::json_t result;
        if (!server.send_json_request(result, "/api/user/create", request_type::post, user_info, error)) {
                return false;
        } else {
                return true;
        }
}

void
core::unauth(central_server& server, identity& id)
{
        util::json_t result;
        std::string error;
        server.send_json_request(result, "/api/user/logout", request_type::post, id.export_json(), error);
}

static std::vector<core::permission>
import_permission_set(util::json_t const& json)
{
        std::vector<core::permission> perms;
        for (util::json_t::const_iterator it = json.begin(); it != json.end(); ++ it) {
                core::permission perm;
                perm.import_json(it->second);
                perms.push_back(perm);
        }
        return perms;
}

static util::json_t
export_permission_sets(std::vector<core::permission> const& perms)
{
        util::json_t json;
        for (core::permission const& perm: perms) {
                json.push_back(std::make_pair("", perm.export_json()));
        }
        return json;
}

std::vector<core::permission>
core::auth_get_all_permissions(central_server& server, identity const& id)
{
        std::vector<core::permission> perms;
        std::string error;
        util::json_t result;
        if (!server.send_json_request(result, "/api/security/view_all_permissions", request_type::get, id.export_json(), error)) {
                std::cerr << error << std::endl;
                return perms;
        }
        return import_permission_set(result);
}

std::vector<core::permission>
core::auth_get_user_permissions(central_server& server, identity const& id, user const& u)
{
        std::vector<core::permission> perms;
        std::string error;
        util::json_t result;
        if (!server.send_json_request(result, "/api/security/view_user_permissions/" + std::to_string(u.uid()),
                                      request_type::get, id.export_json(), error)) {
                std::cerr << error << std::endl;
                return perms;
        }
        return import_permission_set(result);
}
