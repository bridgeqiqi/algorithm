#include <iostream>
using namespace std;

void Dijkstra(int n, int v, int dist[], int prev[], int **c,int maxint, int num[])
{
    bool s[100];
    for(int i=1;i<=n;i++)
    {
        dist[i]=c[v][i];
        s[i]=false;
        if(dist[i]==maxint)
            prev[i]=0;
        else
            prev[i]=v;
        num[i]=1;
    }
    dist[v]=0;
    s[v]=true;
    //�ҳ���i���������㼯s�ڽ�����̴�Ȩ·��
    for(int i=1;i<n;i++)
    {
        int temp=maxint;
        int u=v;
        for(int j=1;j<=n;j++)
            if((!s[j])&&(dist[j]<temp))//�жϽ��j�Ƿ���s�����ڣ������㼯s�ڵĵ�Ĵ�Ȩ·���Ƿ�С�ڵ�ǰֵ
            {
                u=j;
                temp=dist[j];
            }
        s[u]=true;
        //���½��i��j֮������·��������¼
        for(int j=1;j<=n;j++)
        {
            if((!s[j])&&(c[u][j])<maxint)
            {
                int newdist=dist[u]+c[u][j];
                int newnum=num[u] + 1;
                if(newdist<dist[j])
                {
                    dist[j]=newdist;
                    prev[j]=u;
                }
                //������·����ͬ������������������ٵ����
                else if((newdist==dist[j])&&(num[j]>newnum))
                {
                    num[j]=newnum;
                    prev[j]=u;
                }
            }
        }
    }
}
//��ӡ���·��
void Path(int s, int t, int prev[], int start)
{
    if(t!=s)
    {
        int nownode=t;
        t=prev[t];
        Path(s,t,prev,start);
        cout << "->" << nownode;
    }
    else cout << start;
}
int main()
{
    int n,casenum=0;
    while(cin >> n)
    {
        casenum++;
        int maxint=1e9;
        int **c=new int*[n+1];
        for(int i=1;i<=n;i++)
            c[i]=new int[n+1];
        for(int i=1;i<=n;i++)
            for(int j=1;j<=n;j++)
            {
                cin >> c[i][j];
                if(c[i][j]==-1)
                    c[i][j]=maxint;
            }
         int s,t;
         cin >> s >> t;
         //int dist[n+1];
         int *dist=new int [n+1];
         //int prev[n+1];
         int *prev=new int [n+1];
         //int num[n+1];
         int *num=new int [n+1];
         Dijkstra(n,s,dist,prev,c,maxint,num);
         cout << "Case " << casenum << endl;
         cout << "The least distance from " << s << "->" << t << " is " << dist[t] << ". " << endl;
         cout << "The path is " ;
         Path(s,t,prev,s);
         cout << endl;
         delete dist;
         delete prev;
         delete num;
    }
    return 0;
}

/*
5
-1 10  -1 30 100
-1 -1 50 -1  -1
-1 -1 -1 -1 10
-1 -1 20 -1 60
-1 -1 -1 -1 -1
1 5

6
-1 1 12 -1 -1 -1
-1 -1 9 3 -1 -1
-1 -1 -1 -1 5 -1
-1 -1 4 -1 13 13
-1 -1 -1 -1 -1 4
-1 -1 -1 -1 -1 -1
1 6


*/
