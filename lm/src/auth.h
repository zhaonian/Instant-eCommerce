#ifndef AUTH_H
#define AUTH_H

#include <string>
#include "server.h"

namespace core
{
class identity
{
public:
        identity(std::string const& error, unsigned code);
        identity(std::string const& iid);

        std::string     iid() const;
        bool            is_valid() const;
        std::string     error() const;
private:
        std::string     m_error;
        std::string     m_iid;
};


identity        auth_create(central_server& server, std::string const& name, std::string const& password);
identity        auth(central_server& server, std::string const& name, std::string const& password);
void            unauth(central_server& server, identity& id);

}

#endif // AUTH_H
