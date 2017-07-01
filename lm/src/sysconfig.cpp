#include <vector>
#include <fstream>
#include <sstream>
#include "util.h"
#include "sysconfig.h"


static void
load_map(std::map<std::string, std::string>& m, std::string const& path)
{
        std::ifstream file(path);
        if (!file.is_open())
                return;
        std::string line;
        while (std::getline(file, line)) {
                std::vector<std::string> const& parts = util::split(line, '=');
                if (parts.size() != 2)
                        continue;
                m[parts[0]] = parts[1];
        }
        file.close();
}

static void
save_map(std::map<std::string, std::string> const& m, std::string const& path)
{
        std::ofstream file(path);
        if (!file.is_open())
                return;
        for (std::pair<std::string, std::string> const& attri: m) {
                file << attri.first << '=' << attri.second << std::endl;
        }
        file.close();
}

core::sysconfig::sysconfig(std::string const& path):
        m_path(path)
{
        load_map(m_attributes, path);
}

std::string
core::sysconfig::get_host_name() const
{
        auto it = m_attributes.find("host_name");
        if (it == m_attributes.end())
                return "";
        else
                return it->second;
}

void
core::sysconfig::set_host_name(std::string const& h)
{
        m_attributes["host_name"] = h;
        save_map(m_attributes, m_path);
}

unsigned
core::sysconfig::get_host_port() const
{
        auto it = m_attributes.find("host_port");
        if (it == m_attributes.end())
                return 0;
        else
                return std::stoi(it->second);
}

void
core::sysconfig::set_host_port(unsigned port)
{
        m_attributes["host_port"] = std::to_string(port);
        save_map(m_attributes, m_path);
}

std::string
core::sysconfig::get_login_user_name() const
{
        auto it = m_attributes.find("user_name");
        if (it == m_attributes.end())
                return "";
        else
                return it->second;
}

void
core::sysconfig::set_login_user_name(std::string const& u)
{
        m_attributes["user_name"] = u;
        save_map(m_attributes, m_path);
}

std::string
core::sysconfig::get_login_user_password() const
{
        auto it = m_attributes.find("user_password");
        if (it == m_attributes.end())
                return "";
        else
                return it->second;
}

void
core::sysconfig::set_login_user_password(std::string const& p)
{
        m_attributes["user_password"] = p;
        save_map(m_attributes, m_path);
}

static core::sysconfig g_config("sys.cnf");

core::sysconfig*
core::get_system_config()
{
        return &g_config;
}
