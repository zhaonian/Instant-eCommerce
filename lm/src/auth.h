#ifndef AUTH_H
#define AUTH_H

#include <string>
#include "util.h"
#include "server.h"

namespace core
{

class identity: public util::jserializable
{
public:
        identity();
        identity(std::string const& workspace, std::string const& user_name);

        std::string     iid() const;
        std::string     user_name() const;
        std::string     workspace_name() const;

        void            import_json(util::json_t const& json) override;
        util::json_t    export_json() const override;
private:
        std::string     m_workspace;
        std::string     m_user_name;
        std::string     m_iid;
};

struct identity_request: public util::jserializable
{
        identity id;

        identity_request(identity id);
        void            import_json(util::json_t const& json);
        util::json_t    export_json() const override;
};

class permission: public util::jserializable
{
public:
        permission();
        permission(std::string const& category, std::string const& sub_category, unsigned code, std::string const& action);
        std::string     category() const;
        std::string     sub_category() const;
        unsigned        code() const;
        std::string     action() const;

        void            import_json(util::json_t const& json) override;
        util::json_t    export_json() const override;
private:
        std::string     m_category;
        std::string     m_sub_category;
        unsigned        m_code;
        std::string     m_action;
};

struct permission_set: public util::jserializable
{
        std::vector<core::permission> perms;

        permission_set();
        permission_set(std::vector<core::permission> const& perms);
        void            import_json(util::json_t const& json) override;
        util::json_t    export_json() const override;
};

struct permission_request: public util::jserializable
{
        identity id;
        permission_set perms;

        permission_request(identity const& id, permission_set const& perms);
        void            import_json(util::json_t const& json);
        util::json_t    export_json() const;
};

class user: public util::jserializable
{
public:
        user();
        unsigned        uid() const;
        std::string     name() const;

        void            import_json(util::json_t const& json) override;
        util::json_t    export_json() const override;
private:
        unsigned        m_uid;
        std::string     m_name;
};

bool                            auth_create(central_server& server, std::string const& name, std::string const& password, std::string& error);
identity                        auth(central_server& server, std::string const& name, std::string const& password, std::string const& workspace,
                                     std::string& error);
void                            unauth(central_server& server, identity& id);
std::vector<permission>         auth_get_all_permissions(central_server& server, identity const& id);
std::vector<permission>         auth_get_user_permissions(central_server& server, identity const& id, user const& user);

}

#endif // AUTH_H
