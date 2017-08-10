#ifndef WORKSPACE_H
#define WORKSPACE_H


#include <string>
#include "server.h"
#include "auth.h"


namespace core
{

class workspace: public util::jserializable
{
public:
        workspace();
        workspace(unsigned wid, std::string const& name);

        unsigned        wid() const;
        std::string     name() const;

        void            import_json(util::json_t const& json) override;
        util::json_t    export_json() const override;
private:
        unsigned        m_wid;
        std::string     m_name;
};

class workspace_info: public util::jserializable
{
public:
        workspace_info();
        workspace_info(workspace const& ws, user const& u, std::string const& password);

        core::workspace workspace_() const;
        core::user      user_() const;
        std::string     password() const;

        void            import_json(util::json_t const& json) override;
        util::json_t    export_json() const override;
private:
        workspace       m_ws;
        user            m_user;
        std::string     m_password;
};

workspace_info          workspace_create(central_server& server, identity const& iid, std::string const& name, std::string& error);
void                    workspace_delete(central_server& server, identity const& iid, workspace const& w, std::string& error);
void                    workspace_add_user(central_server& server, identity const& iid, workspace const& w, user const& user,
                                           std::vector<permission> const& perm, std::string& error);
void                    workspace_set_user_permission(central_server& server, identity const& iid, workspace const& w, user const& user,
                                                      std::vector<permission> const& perm, std::string& error);
void                    workspace_remove_user(central_server& server, identity const& iid, workspace const& w, user const& user,
                                              std::string& error);


}

#endif // WORKSPACE_H
