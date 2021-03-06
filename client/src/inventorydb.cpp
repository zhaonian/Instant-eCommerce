#include <vector>
#include "inventorydb.h"


core::inventorydb::inventorydb(central_server& server, identity const& id):
        m_server(server), m_id(id)
{
}

std::string
core::inventorydb::connection_name() const
{
        return m_id.workspace_name();
}

std::string
core::inventorydb::user_name() const
{
        return m_id.user_name();
}

std::vector<core::inventory_category>
core::inventorydb::get_inventory_categories(std::string& error)
{
        error = "You don't have the permission to view inventory categories.";
        return std::vector<core::inventory_category>();
}

std::vector<core::inventory>
core::inventorydb::get_inventory(inventory_category const& category, unsigned i_start, unsigned i_end, std::string& error)
{
        return std::vector<core::inventory>();
}
