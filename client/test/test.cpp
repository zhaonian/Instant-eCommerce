#include <random>
#include <algorithm>
#include "test.h"


std::mt19937 engine;
std::uniform_real_distribution<float>   ur(0,1);

test::if_test::if_test()
{
}

test::if_test::~if_test()
{
}

float
test::if_test::draw_rand() const
{
        return ur(engine);
}

std::string
test::if_test::draw_rand(size_t length) const
{
        auto randchar = []() -> char
        {
                        const char charset[] =
                        "0123456789"
                        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                        "abcdefghijklmnopqrstuvwxyz";
                        const size_t max_index = (sizeof(charset) - 1);
                        return charset[ rand() % max_index ];
        };
        std::string str(length,0);
        std::generate_n( str.begin(), length, randchar );
        return str;
}
