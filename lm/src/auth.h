#ifndef AUTH_H
#define AUTH_H

#include <string>
#include "server.h"

namespace core
{
class identity
{
public:
        identity(std::string const& error);
        identity(std::string const& workspace, std::string const& user_name, std::string const& iid);

        std::string     iid() const;
        bool            is_valid() const;
        std::string     user_name() const;
        std::string     workspace_name() const;
        void            write_json(json_t& json) const;
        std::string     error() const;
private:
        std::string     m_error;
        std::string     m_workspace;
        std::string     m_user_name;
        std::string     m_iid;
};


bool            auth_create(central_server& server, std::string const& name, std::string const& password, std::string& error);
identity        auth(central_server& server, std::string const& name, std::string const& password, std::string const& workspace);
void            unauth(central_server& server, identity& id);

}

#endif // AUTH_H
