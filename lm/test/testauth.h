#ifndef TESTAUTH_H
#define TESTAUTH_H

#include "test.h"

namespace test
{

class test_auth: public if_test
{
public:
        test_auth();
        void    run() const override;
};

}

#endif // TESTAUTH_H
