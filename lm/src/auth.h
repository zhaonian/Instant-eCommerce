#ifndef AUTH_H
#define AUTH_H

#include <string>
#include "server.h"

namespace core
{
class identity
{
public:
        identity();
        identity(std::string const& iid);

        std::string     iid() const;
        bool            is_valid() const;
private:
        std::string     m_iid;
};


identity        auth_create(central_server const& server, std::string const& name, std::string const& password);
identity        auth(central_server const& server, std::string const& name, std::string const& password);
void            unauth(identity& id);

}

#endif // AUTH_H
