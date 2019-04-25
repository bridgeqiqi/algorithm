#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>


using namespace std;

const int maxn=4096;
int chessboard[maxn][maxn];
int n;//2^k
int x,y;
int step=1;

struct KeyNode{
    int x;
    int y;
};

void do_work(int minx,int miny,int maxx,int maxy,int keyx,int keyy);

int main(){

    printf("���������̵Ĵ�Сn*n,nΪ2���ݴΣ�");
    scanf("%d",&n);

    printf("�����������ڵ�λ������(x,y),��1��ʼ����,x=");
    scanf("%d",&x);
    printf("�����������ڵ�λ������(x,y),��1��ʼ����,y=");
    scanf("%d",&y);


    step=1;
    //��ʼ������
    memset(chessboard,0,sizeof chessboard);
    //���������Ϣ
    chessboard[x-1][y-1]=1;

    do_work(0,0,n-1,n-1,x-1,y-1);

    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            printf("%d ",chessboard[i][j]);
        }
        printf("\n");
    }


    printf("\n\n");

    return 0;
}



void do_work(int minx,int miny,int maxx,int maxy,int keyx,int keyy){
    if(maxx-minx==1){
        ++step;
        chessboard[minx][miny]=(minx==keyx&&miny==keyy)?chessboard[keyx][keyy]:step;//(chessboard[keyx][keyy]+1);
        chessboard[minx][maxy]=(minx==keyx&&maxy==keyy)?chessboard[keyx][keyy]:step;//(chessboard[keyx][keyy]+1);
        chessboard[maxx][miny]=(maxx==keyx&&miny==keyy)?chessboard[keyx][keyy]:step;//(chessboard[keyx][keyy]+1);
        chessboard[maxx][maxy]=(maxx==keyx&&maxy==keyy)?chessboard[keyx][keyy]:step;//(chessboard[keyx][keyy]+1);
        return;
    }

    int midx = (maxx+minx)/2;
    int midy = (maxy+miny)/2;

    KeyNode left_up;
    KeyNode right_up;
    KeyNode left_down;
    KeyNode right_down;



    if(keyx<=midx && keyy<=midy){
        //�������������

        //����L�Ϳ�
        chessboard[midx][midy+1]=++step;//chessboard[keyx][keyy]+1;
        chessboard[midx+1][midy]=step;//chessboard[keyx][keyy]+1;
        chessboard[midx+1][midy+1]=step;//chessboard[keyx][keyy]+1;


        left_up.x=keyx;
        left_up.y=keyy;
        right_up.x=midx;
        right_up.y=midy+1;
        left_down.x=midx+1;
        left_down.y=midy;
        right_down.x=midx+1;
        right_down.y=midy+1;


    }else if (keyx<=midx && keyy>midy){
        //�������������

        //����L�Ϳ�
        chessboard[midx][midy]=++step;//chessboard[keyx][keyy]+1;
        chessboard[midx+1][midy]=step;//chessboard[keyx][keyy]+1;
        chessboard[midx+1][midy+1]=step;//chessboard[keyx][keyy]+1;


        left_up.x=midx;
        left_up.y=midy;
        right_up.x=keyx;
        right_up.y=keyy;
        left_down.x=midx+1;
        left_down.y=midy;
        right_down.x=midx+1;
        right_down.y=midy+1;



    }else if (keyx>midx && keyy<=midy){
        //�������������

        //����L�Ϳ�
        chessboard[midx][midy]=++step;//chessboard[keyx][keyy]+1;
        chessboard[midx][midy+1]=step;//chessboard[keyx][keyy]+1;
        chessboard[midx+1][midy+1]=step;//chessboard[keyx][keyy]+1;


        left_up.x=midx;
        left_up.y=midy;
        right_up.x=midx;
        right_up.y=midy+1;
        left_down.x=keyx;
        left_down.y=keyy;
        right_down.x=midx+1;
        right_down.y=midy+1;


    }else{
        //�������������

        //����L�Ϳ�
        chessboard[midx][midy]=++step;//chessboard[keyx][keyy]+1;
        chessboard[midx][midy+1]=step;//chessboard[keyx][keyy]+1;
        chessboard[midx+1][midy]=step;//chessboard[keyx][keyy]+1;


        left_up.x=midx;
        left_up.y=midy;
        right_up.x=midx;
        right_up.y=midy+1;
        left_down.x=midx+1;
        left_down.y=midy;
        right_down.x=keyx;
        right_down.y=keyy;
    }





    //�ֱ�����ĸ�����
    do_work(minx,miny,midx,midy,left_up.x,left_up.y);
    do_work(minx,midy+1,midx,maxy,right_up.x,right_up.y);
    do_work(midx+1,miny,maxx,midy,left_down.x,left_down.y);
    do_work(midx+1,midy+1,maxx,maxy,right_down.x,right_down.y);


    return;


}
