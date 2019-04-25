#include "Assistance.h"		// ¸¨ÖúÈí¼þ°ü
#include "HuffmanTree.h"	// ¹þ·òÂüÊ÷Àà

int main(void)
{
    int w[50],num;
    while(cin>>num){
        int n;
        for(int i=1;i<=num;i++){
            cin>>n;
            for(int j=0;j<n;j++) cin>>w[j];
            HuffmanTree<int, int> hmTree(w, w, n);
            cout<<"Case "<<i<<endl;
            for(int t=0;t<n;t++){
                String strTmp = hmTree.Code(t);
                cout<<w[t]<<" "<<strTmp.CStr()<< endl;
            }
        }
    }
	return 0;
}
