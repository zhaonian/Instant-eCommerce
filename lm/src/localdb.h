#ifndef LOCALDB_H
#define LOCALDB_H

#include <map>
#include "server.h"
#include "auth.h"
#include "inventory.h"


namespace core
{

class localdb
{
public:
        localdb(central_server& server, identity const& id);
        std::string                             connection_name() const;
        std::string                             user_name() const;

        std::vector<inventory_category>         get_inventory_categories();
        std::vector<inventory>                  get_inventory(inventory_category const& category, unsigned i_start, unsigned i_end);
private:
        central_server&         m_server;
        identity                m_id;
};

}

#endif // LOCALDB_H
