#include <iostream>
using namespace std;
static  int totle=0;
void solve(int n)
{

    if(n==1) totle++;
    else{
        for(int i=2;i<=n;i++) {
            if(n%i==0)
                solve(n/i);
        }

    }

}
int main()
{
    solve(2000000000);
    cout<<totle;
    return 0;
}
