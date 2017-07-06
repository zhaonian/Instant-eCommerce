#include "localdb.h"


core::localdb::localdb(central_server& server, identity const& id):
        m_server(server), m_id(id)
{
}

std::string
core::localdb::connection_name() const
{
        return m_server.connection_name();
}

std::string
core::localdb::user_name() const
{
        return m_id.user_name();
}
