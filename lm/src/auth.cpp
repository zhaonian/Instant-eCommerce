#include "server.h"
#include "auth.h"


core::identity::identity()
{
}

core::identity::identity(std::string const& iid):
        m_iid(iid)
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


core::identity
core::auth(central_server const& server, std::string const& name, std::string const& password)
{
        return identity();
}

core::identity
core::auth_create(central_server const& server, std::string const& name, std::string const& password)
{
        return identity();
}

void
core::unauth(identity& id)
{
}
