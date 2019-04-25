#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>


using namespace std;

const int maxn = 100;
int vis[maxn];
int n;
int weight[maxn];
int c1,c2;
bool flag = false;
int kase = 0;
int sum=0;

void dfs(int position,int &ans1,int &ans2);

int main(){

    while(scanf("%d",&n)==1){
        flag = false;
        sum=0;
        for(int i=0;i<n;i++){
            scanf("%d",&weight[i]);
            sum+=weight[i];
        }
        scanf("%d%d",&c1,&c2);

        int ans1=0,ans2=0;


        dfs(0,ans1,ans2);
        if(flag == false){
            printf("Case %d\nNo\n",++kase);
        }


    }

    return 0;
}


void dfs(int position,int &ans1,int &ans2){

    if(position == n && ans1+ans2<sum){
        //不可行解
        return;
    }

    if(position == n && ans1<=c1 && ans2<=c2 && sum==ans1+ans2){
        if(flag == true)return;
        //可行解
        printf("Case %d\n",++kase);
        printf("%d ",ans1);
        for(int i=0;i<n;i++){
            printf("%d",vis[i]);
        }
        printf("\n");
        flag = true;
        return;
    }

    vis[position]=1;
    ans1+=weight[position];
    if(ans1<=c1){
        //装在第一个船上，能装下
        dfs(position+1,ans1,ans2);
        //第一个船能装下但是装第二个船
        ans1-=weight[position];
        vis[position]=0;
        dfs(position+1,ans1,ans2);
        return;
    }else{
        //第一个船装不下，装第二个船上
        ans1-=weight[position];
        vis[position]=0;
        ans2+=weight[position];
        if(ans2<=c2){
            //第二个船装得下
            dfs(position+1,ans1,ans2);
        }else{
            //第二个船装不下
            vis[position]=2;
            ans2-=weight[position];
        }
        return;
    }


}


