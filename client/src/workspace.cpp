#include "workspace.h"


core::workspace::workspace()
{
}

core::workspace::workspace(unsigned wid, std::string const& name):
        m_wid(wid), m_name(name)
{
}

unsigned
core::workspace::wid() const
{
        return m_wid;
}

std::string
core::workspace::name() const
{
        return m_name;
}

void
core::workspace::import_json(util::json_t const& json)
{
        m_name = json.get<std::string>("name");
        m_wid = json.get<unsigned>("wid");
}

util::json_t
core::workspace::export_json() const
{
        util::json_t json;
        json.put("name", m_name);
        json.put("wid", m_wid);
        return json;
}


core::workspace_info::workspace_info()
{
}

core::workspace_info::workspace_info(workspace const& ws, user const& u, std::string const& password):
        m_ws(ws), m_user(u), m_password(password)
{
}

core::workspace
core::workspace_info::workspace_() const
{
        return m_ws;
}

core::user
core::workspace_info::user_() const
{
        return m_user;
}

std::string
core::workspace_info::password() const
{
        return m_password;
}

void
core::workspace_info::import_json(util::json_t const& json)
{
        m_ws.import_json(json.get_child("workspace"));
        m_user.import_json(json.get_child("user"));
        m_password = json.get<std::string>("rawPasscode");
}

util::json_t
core::workspace_info::export_json() const
{
        util::json_t json;
        json.push_back(std::make_pair("workspace", m_ws.export_json()));
        json.push_back(std::make_pair("user", m_user.export_json()));
        json.put("rawPasscode", m_password);
        return json;
}


core::workspace_info
core::workspace_create(central_server& server, identity const& iid, std::string const& name, std::string& error)
{
        workspace_info info;
        server.send_json_request(info, "/api/workspace/create/" + name, request_type::post, iid, error);
        return info;
}

void
core::workspace_delete(central_server& server, identity const& iid, workspace const& w, std::string& error)
{
        server.send_json_request("/api/workspace/delete/" + w.wid(), request_type::post, iid, error);
}

void
core::workspace_add_user(central_server& server, identity const& iid, workspace const& w, user const& user,
                         std::vector<permission> const& perm, std::string& error)
{
        server.send_json_request("/api/workspace/user/add/" + std::to_string(w.wid()) + "/" + std::to_string(user.uid()),
                                 request_type::post, permission_request(iid, perm), error);
}

void
core::workspace_set_user_permission(central_server& server, identity const& iid, workspace const& w, user const& user,
                                    std::vector<permission> const& perm, std::string& error)
{
        server.send_json_request("/api/workspace/user/permission/" + std::to_string(w.wid()) + "/" + std::to_string(user.uid()),
                                 request_type::post, permission_request(iid, perm), error);
}

void
core::workspace_remove_user(central_server& server, identity const& iid, workspace const& w, user const& user, std::string& error)
{
        server.send_json_request("/api/workspace/user/remove/" + std::to_string(w.wid()) + "/" + std::to_string(user.uid()),
                                 request_type::post, identity_request(iid), error);
}
