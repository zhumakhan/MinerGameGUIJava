import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class graphic extends JFrame{
	
	int spacing=1;
	public int mx=-100;
	public int my=-100;
	boolean firstClick = true;
	Position pos = new Position();
	boolean victory=false;
	boolean defeat=false;
	boolean reset = false;
	int counter = 0;
	public graphic() {
		this.setTitle("Minesweeper1.0LevelB");
		this.setSize(800,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// close when press X
		this.setVisible(true);//whenever window created it is visible
		this.setResizable(true);// Resize
		
		Grid grid=new Grid();
		this.setContentPane(grid);
		Move move=new Move();
		this.addMouseMotionListener(move);
		
		Click click=new Click();
		this.addMouseListener(click);
		
	}
	public class Position{
		int bomb = 10;
		int[][] mines = new int[9][9];
		int[][] numbers = new int[9][9];
		boolean[][] revealed = new boolean[9][9];
		boolean[][] flagged= new boolean[9][9];
		boolean[][] rightclicked= new boolean [9][9];
		boolean[][] guessed= new boolean [9][9];
	
		
		Random rand=new Random();		
		public void fillMines() {
			while (bomb>0)
			{
				int bx,by=0;
				bx=rand.nextInt(9);
				by=rand.nextInt(9);
				if(bx!=inBoxX() && by!=inBoxY() && mines[bx][by] == 0)
				{
					mines[bx][by]=1;
					bomb--;
				}
			}
		}
		public void generateNumbers() {
			for(int i=0;i<9;i++){
				for(int j=0; j<9;j++){
					if(mines[i][j]==1)fillNumber(i,j);
				}
			}
		}
		public void fillNumber(int x, int  y) {
			if(x-1>-1 && y-1>-1 && x-1<9 && y-1<9 && mines[x-1][y-1]!=1) {
				numbers[x-1][y-1]++;
			}
			if(y-1>-1 && y-1<9 && mines[x][y-1]!=1) {
				numbers[x][y-1]++;
			}
			if(x+1>-1 && y-1>-1 && x+1<9 && y-1<9 && mines[x+1][y-1]!=1) {
				numbers[x+1][y-1]++;
			}
			if(x+1>-1 && x+1<9 && mines[x+1][y]!=1) {
				numbers[x+1][y]++;
			}
			if(x-1>-1 && x-1<9 && mines[x-1][y]!=1) {
				numbers[x-1][y]++;
			}
			if(x-1>-1 && y+1>-1 && x-1<9 && y+1<9 && mines[x-1][y+1]!=1) {
				numbers[x-1][y+1]++;
			}
			if(y+1>-1 && y+1<9 && mines[x][y+1]!=1) {
				numbers[x][y+1]++;
			}
			if(x+1>-1 && y+1>-1 && x+1<9 && y+1<9 && mines[x+1][y+1]!=1) {
				numbers[x+1][y+1]++;
			}
		}
		public void splash(int i, int j) {
			if(pos.revealed[i][j] == false) {pos.revealed[i][j] = true; counter++;}
			//right
			if(i+1 < 9 && revealed[i+1][j] == false) {
				if(numbers[i+1][j] != 0) {revealed[i+1][j] = true; counter++;}
				else if(mines[i+1][j] == 0)splash(i+1,j);
			}
			//left
			if(i-1 > -1 && revealed[i-1][j] == false) {
				if(numbers[i-1][j] != 0) {revealed[i-1][j] = true; counter++;}
				else if(mines[i-1][j] == 0)splash(i-1,j);
			}
			//up
			if(j+1 < 9 && revealed[i][j+1] == false) {
				if(numbers[i][j+1] != 0) {revealed[i][j+1] = true; counter++;}
				else if(mines[i][j+1] == 0)splash(i,j+1);
			}
			//down
			if(j-1 > -1 && revealed[i][j-1] == false) {
				if(numbers[i][j-1] != 0) {revealed[i][j-1] = true; counter++;}
				else if(mines[i][j-1] == 0)splash(i,j-1);
			}
		}
	}
	public class Grid extends JPanel{
		public void paintComponent(Graphics g){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 800, 800);
			g.setColor(Color.GRAY);
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++) {
					g.setColor(Color.gray);
					if (pos.revealed[i][j] && pos.mines[i][j]==1) {
						//if you step on mine
						defeat=true;
						g.setColor(Color.RED);
					} 
					
					if(pos.flagged[i][j]==true && pos.rightclicked[i][j]==true) {
						g.setColor(Color.WHITE);
						
					}
					if(mx>=spacing+i*80+10 && mx< spacing+i*80+10+80-2*spacing && my>= spacing+j*80+26 && my<spacing+j*80+26+80-2*spacing){
						//in rectangle, mouse moved;
						g.setColor(Color.ORANGE);
					}
					g.fillRect(spacing+i*80+10, spacing+j*80+10, 80-2*spacing,	80-2*spacing);
					//if number is in rectangle;
					if(pos.revealed[i][j]) {
						if(pos.numbers[i][j] != 0) {
							//the numbers
							pos.flagged[i][j]=false;
							g.setColor(Color.WHITE);
							g.setFont(new Font("Tahoma",Font.BOLD, 40));
							g.drawString(Integer.toString(pos.numbers[i][j]), spacing+i*80+3+40-spacing, spacing+j*80+17+40-spacing);
						}else if(pos.mines[i][j] == 0){
							//splash function
							pos.flagged[i][j]=false;
							pos.splash(i,j);
							g.setColor(Color.GREEN);
							g.fillRect(spacing+i*80+10, spacing+j*80+10, 80-2*spacing,	80-2*spacing);
						}else if(pos.mines[i][j]==1 && pos.flagged[i][j]==true) {
							pos.guessed[i][j]=true;}
						else {
							//shape of mine 
							g.setColor(Color.BLACK);
							g.fillRect(i*80+35, j*80+25, 30, 50);
							g.fillRect(i*80+25, j*80+35, 50, 30);
							g.fillRect(i*80+30, j*80+30, 40, 40);
							g.fillRect(i*80+20, j*80+45, 60, 10);
							g.fillRect(i*80+45, j*80+20, 10, 60);
						}
					}
					if(defeat) { if(pos.revealed[i][j] == false) {pos.revealed[i][j] = true; counter++;}}
					if (pos.flagged[i][j]==true && pos.rightclicked[i][j]==true) {
						g.setColor(Color.BLACK);
						g.fillRect(i*80+35, j*80+25, 5, 50);
						g.fillRect(i*80+40, j*80+27, 4, 30);
						g.fillRect(i*80+44, j*80+29, 4, 26);
						g.fillRect(i*80+48, j*80+31, 4, 22);
						g.fillRect(i*80+52, j*80+33, 4, 18);
						g.fillRect(i*80+56, j*80+37, 4, 14);
						g.fillRect(i*80+60, j*80+39, 4, 10);
						g.fillRect(i*80+64, j*80+40, 4, 5);
						
					}
					
				}
			}
			if(defeat)reset = true;
		}
	}
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			//System.out.println("The mouse was moved");
			mx=e.getX();
			my=e.getY();
			
		}
	}
	
	public class Click implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) { 
			if(firstClick) {
				firstClick = false;
				mx = e.getX();
				my = e.getY();
				pos.fillMines();
				pos.generateNumbers();
			}else {
				mx = e.getX();
				my = e.getY();
			}
			int x = inBoxX();// returns the coordinates of the box to reveal
			int y = inBoxY();
			if(x != -1 && y != -1) {
				if(pos.revealed[x][y] == false) {pos.revealed[x][y] = true;counter++;}
				if(counter == 71 && pos.mines[x][y] != 1) {
					victory = true;
				}
			}
			}
			if(e.getButton()== MouseEvent.BUTTON3) {
	
				int x = inBoxX();// returns the coordinates of the box to reveal
				int y = inBoxY();
				if(x != -1 && y != -1) {
					if(pos.revealed[x][y] == false && pos.flagged[x][y]==false) {
						pos.rightclicked[x][y]=true;
						pos.flagged[x][y] = true;
						} else if (pos.revealed[x][y] == false && pos.rightclicked[x][y]==true) {
							pos.flagged[x][y]= false;
					}
				}
			}
			
		
		}
			@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void resetGame(){
		victory=false;
		//defeat=false;
		System.out.println("YES");
		
	}
	
	public int inBoxX(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++) {
				if(mx>=spacing+i*80+10 && mx< spacing+i*80+10+80-2*spacing && my>= spacing+j*80+26 && my<spacing+j*80+26+80-2*spacing){
					return i;
				}
			}
		}
		return -1;
		
	}
	
	public int inBoxY() {
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++) {
				if(mx>=spacing+i*80+10 && mx< spacing+i*80+10+80-2*spacing && my>= spacing+j*80+26 && my<spacing+j*80+26+80-2*spacing){
					return j;
				}
			}
		}
		return -1;
		
	}
}


