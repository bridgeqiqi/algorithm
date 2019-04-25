#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>
#include <stack>

using namespace std;


const int maxn = 100;
char a[maxn];
char b[maxn];
int dp[maxn][maxn];
int table[maxn][maxn];



stack<char> ss;

void printLCS(int i,int j);

int main(){
    int T;
    int a_length;
    int b_length;
    int kase=0;

    scanf("%d",&T);
    while(T--){
        scanf("%d",&a_length);
        scanf("%d",&b_length);
        getchar();
        for(int i=0;i<a_length;i++){
            scanf("%c",&a[i]);
            getchar();
            a[i+1]='\n';
        }
        for(int i=0;i<b_length;i++){
            scanf("%c",&b[i]);
            getchar();
            b[i+1]='\n';
        }


        memset(dp,0,sizeof(dp));
        //memset(prenode,-1,sizeof(prenode));

        for(int i=0;i<a_length;i++){
            dp[i][0] = (a[i]==b[0]);
            table[i][0] = dp[i][0];
        }
        for(int j=0;j<b_length;j++){
            dp[0][j] = (a[0]==b[j]);
            table[0][j] = dp[0][j];
        }
        for(int i=1;i<a_length;i++){
            for(int j=1;j<b_length;j++){
                if(a[i]==b[j]){
                    dp[i][j] = dp[i-1][j-1]+1;
                    table[i][j]=1;

                }else{
                    //dp[i][j]=max(dp[i][j-1],dp[i-1][j]);
                    if(dp[i][j-1]>dp[i-1][j]){
                        dp[i][j] = dp[i][j-1];
                        table[i][j]=2;

                    }else if(dp[i][j-1]<dp[i-1][j]){
                        dp[i][j] = dp[i-1][j];
                        table[i][j]=3;

                    }else{
                        dp[i][j] = dp[i-1][j];
                        table[i][j]=4;

                    }
                }
            }
        }


        while(!ss.empty())ss.pop();
        printf("Case %d\n",++kase);
        printf("%d LCS(X,Y):\n",dp[a_length-1][b_length-1]);
        printLCS(a_length-1,b_length-1);
        printf("\n");

    }



    return 0;
}



void printLCS(int i,int j){
    if(i==-1 ||j==-1){
        stack<char> s;
        while(!s.empty())s.pop();
        while(!ss.empty()){
            printf("%c ",ss.top());
            s.push(ss.top());
            ss.pop();
        }
        printf("\n");
        while(!s.empty()){
            ss.push(s.top());
            s.pop();
        }
        return;
    }

    if(table[i][j]==1){
        ss.push(a[i]);
        printLCS(i-1,j-1);
        ss.pop();
    }else{
        if(table[i][j]==2){
            printLCS(i,j-1);
        }else if(table[i][j]==3){
            printLCS(i-1,j);
        }else if(table[i][j]==4){

            printLCS(i,j-1);

            printLCS(i-1,j);

        }
    }
}

/*
2
7 6
A B C B D A B
B D C A B A
8 9
b a a b a b a b
a b a b b a b b a

*/




