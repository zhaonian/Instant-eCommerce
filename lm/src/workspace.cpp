#include "workspace.h"


core::workspace::workspace(unsigned wid, std::string const& name):
        m_wid(wid), m_name(name)
{
}

unsigned
core::workspace::wid() const
{
        return m_wid;
}

std::string
core::workspace::name() const
{
        return m_name;
}
