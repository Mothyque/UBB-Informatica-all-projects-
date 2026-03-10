#include <iostream>
#include <iomanip>
using namespace std;

double pi(double x)
{
    if(x > 50000)
    {
        return 0;
    }
    else
    { 
        return (x * x) / (6 + pi(x + 2));
    }
}

int main()
{
    cout << fixed << setprecision(14);
    cout << 3 + pi(1);
}