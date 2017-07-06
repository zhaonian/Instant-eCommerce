#ifndef INVENTORY_H
#define INVENTORY_H

#include <string>

namespace core
{

class inventory
{
public:
        inventory(unsigned sku,
                  unsigned category_id,
                  float suggested_price,
                  std::string const& ean,
                  std::string const& title,
                  std::string const& brand,
                  std::string const& description,
                  std::string const& amazon_item_type,
                  std::string const& amazon_product_type,
                  std::string const& bullet_1,
                  std::string const& bullet_2,
                  std::string const& bullet_3,
                  std::string const& bullet_4,
                  std::string const& bullet_5,
                  std::string const& keyword,
                  std::string const& main_image,
                  std::string const& image_2,
                  std::string const& image_3,
                  std::string const& image_4);
};

class warehouse_item
{
public:
        warehouse_item(unsigned wh_item_id, std::string const& sku);
private:
        unsigned        m_sku;
        unsigned        m_wh_item_id;
        unsigned        m_quantity;
};

class listed_inventory
{
public:
        listed_inventory();
private:
        unsigned        m_wh_item_id;
        float           m_our_price;
};

class inventory_category
{
public:
        inventory_category(unsigned id, std::string const& category);
private:
        unsigned        m_id;
        std::string     m_category;
};

}

#endif // INVENTORY_H
