#ifndef TESTWORKSPACE_H
#define TESTWORKSPACE_H

#include "test.h"

namespace test
{

class test_workspace: public if_test
{
public:
        test_workspace();
        void    run() const override;
};

}

#endif // TESTWORKSPACE_H
