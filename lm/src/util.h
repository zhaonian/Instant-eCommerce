#ifndef UTIL_H
#define UTIL_H

#include <vector>
#include <string>
#include <sstream>
#include <boost/property_tree/ptree.hpp>

namespace util
{

static inline std::vector<std::string>
split(std::string const &s, char delim)
{
        std::vector<std::string> parts;
        std::stringstream ss(s);
        std::string item;
        while (std::getline(ss, item, delim)) {
                parts.push_back(item);
        }
        return parts;
}

typedef boost::property_tree::ptree     json_t;

template <typename T>
static inline std::vector<T>
as_vector(json_t const& pt, json_t::key_type const& key)
{
        std::vector<T> r;
        for (auto& item : pt.get_child(key))
                r.push_back(item.second.get_value<T>());
        return r;
}

}

#endif // UTIL_H
