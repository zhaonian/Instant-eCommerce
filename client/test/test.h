#ifndef TEST_H
#define TEST_H

#include <string>

namespace test
{

class if_test
{
public:
        if_test();
        virtual ~if_test();
        virtual void run() const = 0;
protected:
        float           draw_rand() const;
        std::string     draw_rand(size_t length) const;

};

}

#endif // IF_TEST_H
