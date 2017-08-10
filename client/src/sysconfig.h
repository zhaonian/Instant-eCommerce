#ifndef SYSCONFIG_H
#define SYSCONFIG_H

#include <string>
#include <map>

namespace core
{

class sysconfig
{
public:
        sysconfig(std::string const& path);

        std::string     get_host_name() const;
        void            set_host_name(std::string const& h);

        unsigned        get_host_port() const;
        void            set_host_port(unsigned port);

        std::string     get_login_workspace() const;
        void            set_login_workspace(std::string const& database);

        std::string     get_login_user_name() const;
        void            set_login_user_name(std::string const& u);

        std::string     get_login_user_password() const;
        void            set_login_user_password(std::string const& p);
private:
        std::string                             m_path;
        std::map<std::string, std::string>      m_attributes;
};

sysconfig*      get_system_config();

}

#endif // SYSCONFIG_H
