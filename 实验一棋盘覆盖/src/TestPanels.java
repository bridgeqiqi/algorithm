import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class TestPanels extends JFrame implements ActionListener {
	
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
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestPanels frame = new TestPanels();
		frame.setTitle("棋盘覆盖");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setVisible(true);
	}
	
	
	public TestPanels() {
		// TODO Auto-generated constructor stub
		init_view();
		setcontentView(rows,cols);
		
		
	}
	
	
	
	
	

	public void actionPerformed(ActionEvent e) {
		String id = e.getActionCommand();
		switch (id) {
			case "ensure":
				if (finish==false) {
					JOptionPane.showMessageDialog(null, "程序还未结束，不可重新开始", "提示框", JOptionPane.ERROR_MESSAGE);
				}else {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (finish == false) {
								JOptionPane.showMessageDialog(null, "程序还未结束，不可重新开始", "提示框", JOptionPane.ERROR_MESSAGE);
							}else {
								int num = Integer.parseInt(rowsandcols.getText());
								rows = num;
								cols = num;
								System.out.println("ensure"+num);
								parent_borderlayout.remove(child_gridlayout_chessboard);
								init_chessboard(num, num);
								parent_borderlayout.add(child_gridlayout_chessboard,BorderLayout.CENTER);
								flag = true;
								
								pack();
							}
							
							
						
						}
					}).start();
				}
				
				break;
				
			case "startGame":
				if (flag == true) {
					JOptionPane.showMessageDialog(null, "请先选择一个关键点（红点）", "提示框", JOptionPane.ERROR_MESSAGE);
				}else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (finish == false) {
								JOptionPane.showMessageDialog(null, "程序还未结束，不可重新开始", "提示框", JOptionPane.ERROR_MESSAGE);
							}else {
								step=1;
								start();
							}
							
						}
					}).start();
					
				}
				break;
				
			case "restartGame":
				if (finish==false) {
					JOptionPane.showMessageDialog(null, "程序还未结束，不可重新开始", "提示框", JOptionPane.ERROR_MESSAGE);
				}else {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
								parent_borderlayout.remove(child_gridlayout_chessboard);
								init_chessboard(init_row,init_col);
								parent_borderlayout.add(child_gridlayout_chessboard,BorderLayout.CENTER);
								rowsandcols.setText(String.valueOf(init_row));
								flag = true;
							
								
								pack();
								
							
						}
					}).start();
				}
				
				
				break;
			
			default:
				if (finish==false) {
					JOptionPane.showMessageDialog(null, "程序还未结束，不可设置关键点", "提示框", JOptionPane.ERROR_MESSAGE);
				}else {
					JButton test = (JButton) e.getSource();
					StringTokenizer stringTokenizer = new StringTokenizer(id, " ,");
					int temprows=0;
					int tempcols=0;
					if (stringTokenizer.hasMoreTokens()) {
						temprows=Integer.parseInt(stringTokenizer.nextToken());
					}
					if (stringTokenizer.hasMoreTokens()) {
						tempcols=Integer.parseInt(stringTokenizer.nextToken());
					}
					System.out.println(temprows);
					System.out.println(tempcols);
					if (flag==true) {
						test.setBackground(Color.RED);
						chessboard[temprows][tempcols]=1;
						this.prerows=temprows;
						this.precols=tempcols;
						TestPanels.setKeyrow(temprows);
						TestPanels.setKeycol(tempcols);
						this.temp = test;
						flag=false;
					}else {
						this.temp.setBackground(Color.WHITE);
						test.setBackground(Color.RED);
						this.temp=test;
						chessboard[temprows][tempcols]=1;
						chessboard[this.prerows][this.precols]=0;
						this.prerows=temprows;
						this.precols=tempcols;
						TestPanels.setKeyrow(temprows);
						TestPanels.setKeycol(tempcols);
					}
				}
				break;
		}
		
	}
	
	
	private void start() {
		finish = false;
		do_work(0,0,TestPanels.rows-1,TestPanels.cols-1,this.prerows,this.precols);
		finish = true;
	}
	
	private void do_work(int minx,int miny,int maxx,int maxy,int keyx,int keyy) {
		 if(maxx-minx==1){
			++step;
			chessboard[minx][miny]=(minx==keyx&&miny==keyy)?chessboard[keyx][keyy]:step;//(chessboard[keyx][keyy]+1);
	        chessboard[minx][maxy]=(minx==keyx&&maxy==keyy)?chessboard[keyx][keyy]:step;//(chessboard[keyx][keyy]+1);
	        chessboard[maxx][miny]=(maxx==keyx&&miny==keyy)?chessboard[keyx][keyy]:step;//(chessboard[keyx][keyy]+1);
	        chessboard[maxx][maxy]=(maxx==keyx&&maxy==keyy)?chessboard[keyx][keyy]:step;//(chessboard[keyx][keyy]+1);
	        
		    
		    
		    	
			for(int i=0;i<TestPanels.rows;i++){
		        for(int j=0;j<TestPanels.cols;j++){
		        	if (chessboard[i][j]==0) {
						jButtons[i][j].setBackground(Color.WHITE);
					}else {
						if (i==TestPanels.keyrow && j == TestPanels.keycol) {
							jButtons[i][j].setBackground(Color.RED);
						}else {
							int h = chessboard[i][j];
							jButtons[i][j].setBackground(Color.YELLOW);
							
						}
					}
		        }
		    }
					
		    
		    for(int i=0;i<TestPanels.rows;i++){
		        for(int j=0;j<TestPanels.cols;j++){
		            System.out.print(chessboard[i][j]+" ");
		        	
		        }
		        System.out.print("\n");
		    }
		    System.out.print("----------\n");
		    
		    
		    
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		    
		    return;
		 }
		 
		 int midx = (maxx+minx)/2;
		 int midy = (maxy+miny)/2;

		    KeyNode left_up = new KeyNode();
		    KeyNode right_up = new KeyNode();
		    KeyNode left_down = new KeyNode();
		    KeyNode right_down = new KeyNode();



		    if(keyx<=midx && keyy<=midy){
		        //红点在左上区域

		        //放置L型块
		        chessboard[midx][midy+1]=++step;
		        chessboard[midx+1][midy]=step;
		        chessboard[midx+1][midy+1]=step;


		        left_up.x=keyx;
		        left_up.y=keyy;
		        right_up.x=midx;
		        right_up.y=midy+1;
		        left_down.x=midx+1;
		        left_down.y=midy;
		        right_down.x=midx+1;
		        right_down.y=midy+1;


		    }else if (keyx<=midx && keyy>midy){
		        //红点在右上区域

		        //放置L型块
		        chessboard[midx][midy]=++step;
		        chessboard[midx+1][midy]=step;
		        chessboard[midx+1][midy+1]=step;


		        left_up.x=midx;
		        left_up.y=midy;
		        right_up.x=keyx;
		        right_up.y=keyy;
		        left_down.x=midx+1;
		        left_down.y=midy;
		        right_down.x=midx+1;
		        right_down.y=midy+1;



		    }else if (keyx>midx && keyy<=midy){
		        //红点在左下区域

		        //放置L型块
		        chessboard[midx][midy]=++step;
		        chessboard[midx][midy+1]=step;
		        chessboard[midx+1][midy+1]=step;


		        left_up.x=midx;
		        left_up.y=midy;
		        right_up.x=midx;
		        right_up.y=midy+1;
		        left_down.x=keyx;
		        left_down.y=keyy;
		        right_down.x=midx+1;
		        right_down.y=midy+1;


		    }else{
		        //红点在右下区域

		        //放置L型块
		        chessboard[midx][midy]=++step;
		        chessboard[midx][midy+1]=step;
		        chessboard[midx+1][midy]=step;


		        left_up.x=midx;
		        left_up.y=midy;
		        right_up.x=midx;
		        right_up.y=midy+1;
		        left_down.x=midx+1;
		        left_down.y=midy;
		        right_down.x=keyx;
		        right_down.y=keyy;
		    }

		   
		    for(int i=0;i<TestPanels.rows;i++){
		        for(int j=0;j<TestPanels.cols;j++){
		        	if (chessboard[i][j]==0) {
						jButtons[i][j].setBackground(Color.WHITE);
					}else {
						if (i==TestPanels.keyrow && j == TestPanels.keycol) {
							jButtons[i][j].setBackground(Color.RED);
						}else {
							int h = chessboard[i][j]; 
							
							jButtons[i][j].setBackground(Color.YELLOW);
						}
					}
		        }
		    }
		    
		    for(int i=0;i<TestPanels.rows;i++){
		        for(int j=0;j<TestPanels.cols;j++){
		            System.out.print(chessboard[i][j]+" ");
		        	
		        }
		        System.out.print("\n");
		    }
		    System.out.print("----------\n");

		    try {
				Thread.sleep(1000);
		    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		    }		 		
		    
		    		
		    //分别调用四个区域
		    do_work(minx,miny,midx,midy,left_up.x,left_up.y);
		    do_work(minx,midy+1,midx,maxy,right_up.x,right_up.y);
		    do_work(midx+1,miny,maxx,midy,left_down.x,left_down.y);
		    do_work(midx+1,midy+1,maxx,maxy,right_down.x,right_down.y);


		    return;
		 
		 
	}
	
	
	
	public class KeyNode{
		int x;
		int y;
	}
	
	
	private void init_view() {
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
		
		TestPanels.rows = rows;
		TestPanels.cols = cols;
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
		
		TestPanels.rows = rows;
		TestPanels.cols = cols;
		jButtons = new JButton[rows][cols];
		chessboard = new int[rows][cols];
		for(int i=0;i<TestPanels.rows;i++) {
			for (int j=0;j<TestPanels.cols;j++) {
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
		TestPanels.rows = rows;
	}


	public static int getCols() {
		return cols;
	}


	public static void setCols(int cols) {
		TestPanels.cols = cols;
	}


	public static int getKeyrow() {
		return keyrow;
	}


	public static void setKeyrow(int keyrow) {
		TestPanels.keyrow = keyrow;
	}


	public static int getKeycol() {
		return keycol;
	}


	public static void setKeycol(int keycol) {
		TestPanels.keycol = keycol;
	}
	
	
	
	
	
}

