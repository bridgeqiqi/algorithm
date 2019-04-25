import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.sun.org.apache.bcel.internal.generic.FieldInstruction;

import jdk.nashorn.internal.ir.debug.NashornClassReader;
import sun.awt.image.IntegerInterleavedRaster;

public class ChessHorse extends JFrame implements ActionListener {
	
	private int step = 1;
	
	private boolean finish = true;//标志程序是否结束
	private static final int init_row = 8;
	private static final int init_col = 8;
	
	private boolean flag = true;
	private JButton temp = null;
	private JButton ensure = null;
	private JButton startGame = null;
	private JButton restartGame = null;
	private JTextField rowsandcols=null;
	private JButton[][] jButtons = null;
	private int[][] chessboard = null;
	private String input= "请输入棋盘大小(2的幂次)";
	
	private JPanel parent_borderlayout=null;
	private JPanel child_gridlayout_chessboard=null;
	private JPanel child_gridlayout_menu=null;
	private static int rows=8;
	private static int cols=8;
	private static int keyrow = 0;
	private static int keycol = 0;
	private int prerows=-1;
	private int precols=-1;
	
	
	//跳马
	private int vis[][] = new int[init_row][init_col];
	private int src_x;
	private int src_y;
	private int dest_x;
	private int dest_y;
	private int ans=200;

	private int dir[][]=new int[8][2];
	public class node{
		public int x;
		public int y;
		public node(int x,int y){
	        this.x = x;
	        this.y = y;
	    }
	};
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessHorse frame = new ChessHorse();
		frame.setTitle("跳马问题");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setVisible(true);
		frame.start();
	}
	
	
	public ChessHorse() {
		// TODO Auto-generated constructor stub
		init_view();
		setcontentView(rows,cols);
		
		
	}
	
	
	
	
	

	public void actionPerformed(ActionEvent e) {
		String id = e.getActionCommand();
		switch (id) {
			
		}
		
	}
	
	
	private void start() {
		finish = false;
		
		
		String string ="";
		Scanner scanner = new Scanner(System.in);
		string = scanner.nextLine();
		src_x = string.charAt(0)-'a';
        src_y = string.charAt(1)-'1';
        dest_x = string.charAt(3)-'a';
        dest_y = string.charAt(4)-'1';
        
        for(int i=0;i<init_row;i++) {
        	for(int j=0;j<init_col;j++) {
        		vis[i][j]=0;
        	}
        }
        ans =200;
        vis[src_x][src_y]=1;
        System.out.println("start");
        dfs(src_x, src_y, dest_x, dest_y,ans);
		System.out.println(ans+"moves");
		finish = true;
	}
	
	
	//要剪枝、初始值设为1，否则会兜圈子（死循环），所以答案要减一
	private void dfs(int src_x,int src_y,int dest_x,int dest_y,int anss){
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(vis[i][j]==0) {
					jButtons[i][j].setBackground(Color.WHITE);
				}else {
					jButtons[i][j].setBackground(Color.RED);
				}
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    node t = node(src_x,src_y);

	    if(src_x == dest_x && src_y==dest_y){
	        ans = Math.min(anss,vis[src_x][src_y]-1);
	        return;
	    }

	    for(int i=0;i<8;i++){
	        int tt_x = src_x + dir[i][0];
	        int tt_y = src_y + dir[i][1];
	      
	        if(check(tt_x,tt_y) && vis[tt_x][tt_y]==0  ){
	            //printf("%d %d %d %d\n",src_x,src_y,tt_x,tt_y);
	            //getchar();
	            vis[tt_x][tt_y] = vis[src_x][src_y]+1;
	            if(vis[tt_x][tt_y]>=ans){
	                ;//剪枝
	            }else {
	                dfs(tt_x,tt_y,dest_x,dest_y,anss);
	            }
	            vis[tt_x][tt_y] = 0;//这步很关键
	        }
	    }
	    
	    return;

	}
	
	
	private node node(int src_x2, int src_y2) {
		// TODO Auto-generated method stub
		return null;
	}


	private boolean check(int x,int y){
	    if(x<0||y<0||x>=init_row||y>=init_col){
	        return false;
	    }else{
	        return true;
	    }
	}

	
	private void init_view() {
		int a[][]= new int[8][2];
		a[0][0]=-1;
		a[0][1]=-2;
		a[1][0]=-2;
		a[1][1]=-1;
		a[2][0]=-1;
		a[2][1]=2;
		a[3][0]=-2;
		a[3][1]=1;
		a[4][0]=1;
		a[4][1]=2;
		a[5][0]=2;
		a[5][1]=1;
		a[6][0]=1;
		a[6][1]=-2;
		a[7][0]=2;
		a[7][1]=-1;
		
		dir = a;
		
		ensure = new JButton("确认");
		startGame = new JButton("开始游戏");
		restartGame = new JButton("重新开始");
		rowsandcols = new JTextField(8);
		
		
	
		
		ensure.setActionCommand("ensure");
		startGame.setActionCommand("startGame");
		restartGame.setActionCommand("restartGame");
		
		
		ensure.addActionListener(this);
		startGame.addActionListener(this);
		restartGame.addActionListener(this);
	}
	
	private void setcontentView(int rows,int cols) {
		
		ChessHorse.rows = rows;
		ChessHorse.cols = cols;
		rowsandcols.setText(String.valueOf(rows));
		
	
		init_parentborderlayout();
		init_chessboard(rows,cols);
		init_menu();
		
		
		
		parent_borderlayout.add(new JTextField("Time To Be Happy"),BorderLayout.NORTH);
		parent_borderlayout.add(child_gridlayout_chessboard,BorderLayout.CENTER);
		parent_borderlayout.add(child_gridlayout_menu,BorderLayout.EAST);
	
		
		
		this.add(parent_borderlayout);
	}
	
	private void init_parentborderlayout() {
		parent_borderlayout = new JPanel(new BorderLayout());
	}
	
	private void init_menu() {
		child_gridlayout_menu = new JPanel(new GridLayout(10, 1, 20, 20));
		child_gridlayout_menu.add(new JLabel(input));
		child_gridlayout_menu.add(rowsandcols);
		child_gridlayout_menu.add(ensure);
		child_gridlayout_menu.add(startGame);
		child_gridlayout_menu.add(restartGame);
	}
	
	private void init_chessboard(int rows,int cols) {
		child_gridlayout_chessboard = new JPanel(new GridLayout(rows, cols));
		
		ChessHorse.rows = rows;
		ChessHorse.cols = cols;
		jButtons = new JButton[rows][cols];
		chessboard = new int[rows][cols];
		for(int i=0;i<ChessHorse.rows;i++) {
			for (int j=0;j<ChessHorse.cols;j++) {
				jButtons[i][j]=new JButton();
				jButtons[i][j].setBackground(Color.WHITE);
				jButtons[i][j].setPreferredSize(new Dimension(50, 50));
				jButtons[i][j].setActionCommand(i+" "+j);
				jButtons[i][j].addActionListener(this);
				child_gridlayout_chessboard.add(jButtons[i][j]);
			}
		}
	}


	public static int getRows() {
		return rows;
	}


	public static void setRows(int rows) {
		ChessHorse.rows = rows;
	}


	public static int getCols() {
		return cols;
	}


	public static void setCols(int cols) {
		ChessHorse.cols = cols;
	}


	public static int getKeyrow() {
		return keyrow;
	}


	public static void setKeyrow(int keyrow) {
		ChessHorse.keyrow = keyrow;
	}


	public static int getKeycol() {
		return keycol;
	}


	public static void setKeycol(int keycol) {
		ChessHorse.keycol = keycol;
	}
	
	
	
	
	
}

