#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>
#include <stack>


using namespace std;
const int INF = 0x3f3f3f3f;
const int maxn = 100;
int G[maxn][maxn];
int vis[maxn];
int pre[maxn];
int lowcost[maxn];
int n;
int src,dest;

bool Dijkstra(int src,int dest);
void PrintShortestPath(int src,int dest);

int main(){

    while(scanf("%d",&n)==1){
        memset(G,0,sizeof(G));
        memset(vis,0,sizeof(vis));
        memset(pre,-1,sizeof(pre));
        memset(lowcost,INF,sizeof(lowcost));

        //存图，记录相邻两点距离
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                scanf("%d",&G[i][j]);
                if(G[i][j]==-1){
                    G[i][j] = INF;
                }
            }
        }
        scanf("%d%d",&src,&dest);
        if(Dijkstra(src,dest)){
            PrintShortestPath(src,dest);
        }
    }

    return 0;
}


bool Dijkstra(int src,int dest){
    lowcost[src]=0;
    for(int i=1;i<=n;i++){
        int p = -1;
        int minn = INF;
        //找到离
        for(int j=1;j<=n;j++){
            if(vis[j]==0 && lowcost[j]<minn){
                minn = lowcost[j];
                p = j;
            }
        }
        if(p == -1)
            break;
        vis[p] = 1;
        for(int j=1;j<=n;j++){
            if(vis[j]==0 && lowcost[p]+G[p][j]<lowcost[j]){
                lowcost[j] = lowcost[p]+G[p][j];
                pre[j] = p;
            }
        }
    }
    //print
    if(lowcost[dest]==INF){
        printf("There is no way from %d to %d.\n",src,dest);
        return false;
    }else{
        printf("The least distance from %d->%d is %d.\n",src,dest,lowcost[dest]);
        return true;
    }
}



void PrintShortestPath(int src,int dest){
        printf("The path is ");
        stack<int> s;
        while(!s.empty())s.pop();
        s.push(dest);
        int t = dest;
        while(t!=1){
            s.push(pre[t]);
            t = pre[t];
        }
        while(!s.empty()){
            if(s.size()==1){
                printf("%d.\n",s.top());
            }else{
                printf("%d-->",s.top());
            }
            s.pop();
        }

}
