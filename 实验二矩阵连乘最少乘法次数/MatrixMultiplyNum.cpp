#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>

using namespace std;

const int maxn = 100;
int m[100][100];//记录从Ai乘到Aj的最少乘法次数
int s[100][100];//记录从Ai乘到Aj的最少乘法次数时需要的分割的地方
int a[101];//存放每个矩阵的行，i+1的列等于i的行
int n;
int kase=0;

void TraceBack(int i,int j,int s[][100]);

int main(){
    while(scanf("%d",&n)==1){
        for(int i=0;i<n+1;i++){
            scanf("%d",&a[i]);
        }
        memset(m,0,sizeof(m));
        memset(s,0,sizeof(s));


        for(int r = 2;r <= n;r++){
            for(int i=1;i<=n-r+1;i++){
                int j=i+r-1;
                m[i][j] = m[i+1][j] + a[i-1]*a[i]*a[j];
                s[i][j] = i;
                for(int k=i+1;k<j;k++){
                    int temp = m[i][k]+m[k+1][j]+a[i-1]*a[k]*a[j];
                    if(temp<m[i][j]){
                        m[i][j] = temp;
                        s[i][j]=k;
                    }
                }
            }
        }
        printf("Case %d\n",++kase);
        printf("%d ",m[1][n]);
        TraceBack(1,n,s);
        printf("\n");
    }



    return 0;
}



void TraceBack(int i,int j,int s[][100]){
    //if(i==j)return;
    //TraceBack(i,s[i][j],s);
    //TraceBack(s[i][j]+1,j,s);
    //cout<<"Mutiply A"<<i<<","<<s[i][j]<<endl;
    //cout<<"and A"<<(s[i][j]+1)<<","<<j<<endl;
    if(i == j){
        printf("A%d",i);
    }else{
        if(i==1 && j==n){
            TraceBack(i,s[i][j],s);
            TraceBack(s[i][j]+1,j,s);
        }else{
            printf("(");
            TraceBack(i,s[i][j],s);
            TraceBack(s[i][j]+1,j,s);
            printf(")");
        }

    }


}
