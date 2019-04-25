#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>
#include <string>
#include <queue>


using namespace std;

const int N = 8;

int G[N][N];
int vis[N][N];
int src_x;
int src_y;
int dest_x;
int dest_y;

int dir[8][2]={-1,-2,-2,-1,-1,2,-2,1,1,2,2,1,1,-2,2,-1};

bool check(int x,int y){
    if(x<0||y<0||x>=N||y>=N){
        return false;
    }else{
        return true;
    }
}


class node{
public:
    int x;
    int y;
    node(int x,int y){
        this->x = x;
        this->y = y;
    }
};

int bfs(int src_x,int src_y,int dest_x,int dest_y);
void dfs(int src_x,int src_y,int dest_x,int dest_y,int &ans);

int main(){

    string s;
    while(getline(cin,s)){
        if(s.length()<=4)continue;
        src_x = s[0]-'a';
        src_y = s[1]-'1';
        dest_x = s[3]-'a';
        dest_y = s[4]-'1';

        //printf("%d %d %d %d\n",src_x,src_y,dest_x,dest_y);


        memset(G,0,sizeof(G));
        memset(vis,0,sizeof(vis));

        //bfs
        //int ans = bfs(src_x,src_y,dest_x,dest_y);

        //dfs
        int ans=200;
        vis[src_x][src_y]=1;
        dfs(src_x,src_y,dest_x,dest_y,ans);


        printf("%c%c==>%c%c: ",s[0],s[1],s[3],s[4]);
        printf("%d moves\n",ans);



    }


    return 0;
}

int bfs(int src_x,int src_y,int dest_x,int dest_y){

    queue<node> q;
    while(!q.empty())q.pop();
    q.push(node(src_x,src_y));
    while(!q.empty()){
        node t = q.front();
        q.pop();
        int t_x = t.x;
        int t_y = t.y;

        //判断如果到达终点，则输出步数
        if(t_x == dest_x && t_y == dest_y){
            return vis[t_x][t_y];
        }

        for(int i=0;i<8;i++){
            int tt_x = t_x + dir[i][0];
            int tt_y = t_y + dir[i][1];
            if(vis[tt_x][tt_y]==0 && check(tt_x,tt_y)){
                //如果是可达点，则步数为过来的那个步数加1，然后再放入队列中
                vis[tt_x][tt_y]=vis[t_x][t_y]+1;
                q.push(node(tt_x,tt_y));
            }
        }
    }
}



//要剪枝、初始值设为1，否则会兜圈子（死循环），所以答案要减一
void dfs(int src_x,int src_y,int dest_x,int dest_y,int &ans){

    node t = node(src_x,src_y);

    if(src_x == dest_x && src_y==dest_y){
        ans = min(ans,vis[src_x][src_y]-1);
        return;
    }

    for(int i=0;i<8;i++){
        int tt_x = src_x + dir[i][0];
        int tt_y = src_y + dir[i][1];
        if(vis[tt_x][tt_y]==0 && check(tt_x,tt_y)){
            //printf("%d %d %d %d\n",src_x,src_y,tt_x,tt_y);
            //getchar();
            vis[tt_x][tt_y] = vis[src_x][src_y]+1;
            if(vis[tt_x][tt_y]>=ans){
                ;//剪枝
            }else{
                dfs(tt_x,tt_y,dest_x,dest_y,ans);
            }
            vis[tt_x][tt_y] = 0;//这步很关键
        }
    }

    return;

}




