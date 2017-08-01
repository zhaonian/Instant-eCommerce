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

core::identity_request::identity_request(identity id):
        id(id)
{
}

void
core::identity_request::import_json(util::json_t const& json)
{
        id.import_json(json.get_child("userIdentity"));
}

util::json_t
core::identity_request::export_json() const
{
        util::json_t json;
        json.push_back(std::make_pair("userIdentity", id.export_json()));
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
        m_category = json.get<std::string>("permissionCategory");
        m_sub_category = json.get<std::string>("permissionSubCategory");
        m_code = json.get<unsigned>("permissionType");
        m_action = json.get<std::string>("actionDetail");
}

util::json_t
core::permission::export_json() const
{
        util::json_t json;
        json.put("permissionCategory", m_category);
        json.put("permissionSubCategory", m_sub_category);
        json.put("permissionType", m_code);
        json.put("actionDetail", m_action);
        return json;
}

core::permission_set::permission_set()
{
}

core::permission_set::permission_set(std::vector<core::permission> const& perms):
        perms(perms)
{
}

void
core::permission_set::import_json(util::json_t const& json)
{
        perms.clear();
        for (util::json_t::const_iterator it = json.begin(); it != json.end(); ++ it) {
                core::permission perm;
                perm.import_json(it->second);
                perms.push_back(perm);
        }
}

util::json_t
core::permission_set::export_json() const
{
        util::json_t json;
        for (core::permission const& perm: perms) {
                json.push_back(std::make_pair("", perm.export_json()));
        }
        return json;
}

core::permission_request::permission_request(identity const& id, permission_set const& perms):
        id(id), perms(perms)
{
}

void
core::permission_request::import_json(util::json_t const& json)
{
        id.import_json(json.get_child("userIdentity"));
        perms.import_json(json.get_child("permissions"));
}

util::json_t
core::permission_request::export_json() const
{
        util::json_t json;
        json.push_back(std::make_pair("userIdentity", id.export_json()));
        json.push_back(std::make_pair("permissions", perms.export_json()));
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
        return m_name;
}

void
core::user::import_json(util::json_t const& json)
{
        m_name = json.get<std::string>("name");
        m_uid = json.get<unsigned>("uid");
}

util::json_t
core::user::export_json() const
{
        util::json_t json;
        json.put("name", m_name);
        json.put("uid", m_uid);
        return json;
}


core::identity
core::auth(central_server& server, std::string const& name, std::string const& password, std::string const& workspace, std::string& error)
{
        util::json_t user_info;
        user_info.put<std::string>("username", name);
        user_info.put<std::string>("password", password);

        identity iid;
        server.send_json_request(iid, "/api/user/login/" + workspace, request_type::post, user_info, error);
        return iid;
}

bool
core::auth_create(central_server& server, std::string const& name, std::string const& password, std::string& error)
{
        util::json_t user_info;
        user_info.put<std::string>("username", name);
        user_info.put<std::string>("password", password);

        return server.send_json_request("/api/user/create", request_type::post, user_info, error);
}

void
core::unauth(central_server& server, identity& id)
{
        server.send_json_request("/api/user/logout", request_type::post, identity_request(id));
}

std::vector<core::permission>
core::auth_get_all_permissions(central_server& server, identity const& id)
{
        permission_set ps;
        std::string error;
        if (!server.send_json_request(ps, "/api/security/view_all_permissions", request_type::get, identity_request(id), error)) {
                std::cerr << error << std::endl;
                return ps.perms;
        }
        return ps.perms;
}

std::vector<core::permission>
core::auth_get_user_permissions(central_server& server, identity const& id, user const& u)
{
        permission_set ps;
        std::string error;
        if (!server.send_json_request(ps, "/api/security/view_user_permissions/" + std::to_string(u.uid()),
                                      request_type::get, identity_request(id), error)) {
                std::cerr << error << std::endl;
                return ps.perms;
        }
        return ps.perms;
}
