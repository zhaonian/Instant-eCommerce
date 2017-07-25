#ifndef WORKSPACE_H
#define WORKSPACE_H


#include <string>
#include "auth.h"


namespace core
{

class workspace
{
public:
        workspace(unsigned wid, std::string const& name);
        unsigned        wid() const;
        std::string     name() const;
private:
        unsigned        m_wid;
        std::string     m_name;
};

workspace       workspace_create(identity const& iid, std::string const& name, std::string& error);
void            workspace_delete(identity const& iid, workspace const& w, std::string& error);
void            workspace_add_user(identity const& iid, workspace const& w, user const& user, std::vector<permission> const& perm);
void            workspace_set_user_permission(identity const& iid, workspace const& w, user const& user, std::vector<permission> const& perm);
void            workspace_remove_user(identity const& iid, workspace const& w, user const& user);


}

#endif // WORKSPACE_H
