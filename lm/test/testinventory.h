#ifndef TEST_INVENTORY_H
#define TEST_INVENTORY_H

#include "test.h"

namespace test
{

class test_inventory: public if_test
{
public:
        test_inventory();
        void    run() const override;
};

}

#endif // TEST_INVENTORY_H
