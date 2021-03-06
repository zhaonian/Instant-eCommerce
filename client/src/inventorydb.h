#ifndef LOCALDB_H
#define LOCALDB_H

#include <map>
#include "server.h"
#include "auth.h"
#include "inventory.h"


namespace core
{

class inventorydb
{
public:
        inventorydb(central_server& server, identity const& id);
        std::string                             connection_name() const;
        std::string                             user_name() const;

        std::vector<inventory_category>         get_inventory_categories(std::string& error);
        std::vector<inventory>                  get_inventory(inventory_category const& category, unsigned i_start, unsigned i_end,
                                                              std::string& error);
private:
        central_server&         m_server;
        identity                m_id;
};

}

#endif // LOCALDB_H
