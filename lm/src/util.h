#ifndef UTIL_H
#define UTIL_H

#include <vector>
#include <string>
#include <sstream>

namespace util
{

static std::vector<std::string>
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

}

#endif // UTIL_H
