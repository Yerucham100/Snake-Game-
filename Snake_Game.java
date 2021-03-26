package snake;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

class Snake extends JFrame{
	
	public Snake(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Board());
		setSize(707,729);
		setTitle("Snake");
		setLocationRelativeTo(null);
		setResizable(false);
		
	}
	public static void main(String[] args){
		Snake snake = new Snake();
		snake.setVisible(true);
	}
}


public class Board extends JPanel implements ActionListener {
	private JOptionPane pane = new JOptionPane();
	private int noOfDots = 34;
	private int noOfParts = 3;
	private int foodCount = 0;
	private final int foodLimit = 1000;
	private int food_x;
	private int food_y;
	private Integer scoreCount = 0;
	private int SPACE = 0;
	private Random rand = new Random();
	private int x[] = new int [foodLimit];
	private int y[] = new int [foodLimit
	                           ];
	private final int snakeDiameter = 20;
	private int foodDiameter = 20;
	private final int bigFood = 40;
	private final int smallFood = 20;
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame;
	private boolean noFood = true;
	private Timer timer;
	private final int DELAY = 150;
	private final int [][] boundary ={{0,0},{680,0},{0,680}};
	ImageIcon ii = new ImageIcon("C:\\Users\\DIACIOUS\\Documents\\Java\\Images\\grasses.jpeg");
	Image img = ii.getImage();
	

	public Board() {
	addKeyListener(new TAdapter());
	setFocusable(true);
 
    initUI();
	}
	private void initUI(){
		for(int i=0;i<noOfParts;i++){
			x[i]= 100-i*snakeDiameter;
			y[i]= 40;
		}
		timer = new Timer(DELAY,this);
		timer.start();
		inGame = true;
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(inGame){
		doSketch(g);
		}
		else{
			timer.stop();
			drawGameOver();
		}
	}
	private void doSketch(Graphics g){
		g.drawImage(img,0,0,null);
		g.setColor(Color.RED);
		g.fillOval(x[0], y[0],snakeDiameter, snakeDiameter);
		g.setColor(Color.WHITE);
		for(int i=1;i<noOfParts;i++){
			g.fillOval(x[i], y[i],snakeDiameter, snakeDiameter);
		}
		if(noFood){
			generateFood();
		}
		g.setColor(Color.BLUE);
		g.fillOval((food_x*20)-SPACE, (food_y*20)-SPACE, foodDiameter, foodDiameter);
		String sC = scoreCount.toString();
		g.setColor(Color.YELLOW);
		
		g.fillRect(boundary[0][0], boundary[0][1], 700, 20);
		g.fillRect(boundary[0][0], boundary[0][1], 20,700);
		g.fillRect(boundary[1][0], boundary[1][1], 20,700);
		g.fillRect(boundary[2][0], boundary[2][1], 700, 20);
		
		g.setColor(Color.RED);
		g.drawString("Score: "+sC, 15, 15);
		
	}
	private void drawGameOver(){

		pane.showMessageDialog(null, "Game Over!");
	}
	private void generateFood(){
		if(foodCount%5!=0||foodCount==0){
			foodDiameter = smallFood;
			SPACE = 0;
		food_x = rand.nextInt(noOfDots-1);
		for(int a:x){
			while(food_x==a){
			   food_x = rand.nextInt(noOfDots-1);
			}
		}
		food_y = rand.nextInt(noOfDots-1);
		for(int a:y){
			while(food_y==a){
			   food_y = rand.nextInt(noOfDots-1);
			}
		}
		}
		else{
			foodDiameter = bigFood;
			SPACE = 10;
			food_x = rand.nextInt(noOfDots-1);
			for(int a:x){
				while(food_x==a){
				   food_x = rand.nextInt(noOfDots-1);
				}
			}
			food_y = rand.nextInt(noOfDots-1);
			for(int a:y){
				while(food_y==a){
				   food_y = rand.nextInt(noOfDots-1);
				}
			}
		}
		noFood = false;
	}
	private void move(){
		if(left){
			if(x[0]<x[1]||y[0]>y[1]||y[0]<y[1]){
			for(int i=noOfParts-1;i>0;i--){
				x[i] = x[i-1];
				y[i] = y[i-1];
			}
		 x[0] = x[0] - snakeDiameter;
		 }
			else{
				left = false;
				right = true;
			}
		}
		 if(right){
			 if(x[0]>x[1]||y[0]>y[1]||y[0]<y[1]){
			 for(int i=noOfParts-1;i>0;i--){
					x[i] = x[i-1];
					y[i] = y[i-1];
				}
			 x[0] = x[0] + snakeDiameter;
			 }
			 else{
				 left = true;
			     right = false;
			 }
		 }
		 if(up){
			 if(y[0]<y[1]||x[0]<x[1]||x[0]>x[1]){
			 for(int i=noOfParts-1;i>0;i--){
				    x[i] = x[i-1];
					y[i] = y[i-1];
				}
			 y[0] = y[0] - snakeDiameter;
			 }
			 else{
				 up = false;
				 down = true;
			 }
		 }
		 if(down){
			 if(y[0]>y[1]||x[0]>x[1]||x[0]<x[1]){
			 for(int i=noOfParts-1;i>0;i--){
				    x[i] = x[i-1];
					y[i] = y[i-1];
				}
			 y[0] = y[0] + snakeDiameter;
			 }
			 else{
				 up = true;
				 down = false;
			 }
		 }
		 repaint();
	}
	public void actionPerformed(ActionEvent e){
		move();
		checkCollision();
		repaint();
	}
	class TAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
		 int key = e.getKeyCode();
		 switch(key){
		 case KeyEvent.VK_RIGHT:
			 right = true;
			 left = false;
			 up = false;
			 down = false;
			 break;
		 case KeyEvent.VK_LEFT:
			 right = false;
			 left = true;
			 up = false;
			 down = false;
			 break;
		 case KeyEvent.VK_UP:
			 right = false;
			 left = false;
			 up = true;
			 down = false;
			 break;
		 case KeyEvent.VK_DOWN:
			 right = false;
			 left = false;
			 up = false;
			 down = true;
			 break;
		 }
		}
	}
	private void checkCollision(){
		Rectangle c2 = new Rectangle(x[0],y[0],snakeDiameter,snakeDiameter);
		for(int i=1;i<noOfParts;i++){
			Rectangle c1 = new Rectangle(x[i],y[i],snakeDiameter,snakeDiameter);
			if(c2.intersects(c1)){
				inGame = false;
			}
		}
		Rectangle c3 = new Rectangle(food_x*20, food_y*20, foodDiameter, foodDiameter);
		if(c2.intersects(c3)){
			grow();
			if(foodDiameter>20){
				scoreCount+=32;
			}
			else{
				scoreCount+=8;
			}
			foodCount++;
		}
		Rectangle c4 = new Rectangle(boundary[0][0],boundary[0][1],700 , 20);
		Rectangle c5 = new Rectangle(boundary[0][0],boundary[0][1],20 , 700);
		Rectangle c6 = new Rectangle(boundary[1][0],boundary[1][1],20 , 700);
		Rectangle c7 = new Rectangle(boundary[2][0],boundary[2][1],700 , 20);
		if(c2.intersects(c4)||c2.intersects(c5)||c2.intersects(c6)||c2.intersects(c7)){
			inGame = false;
		}
		
		
	}
	private void grow(){
		x[noOfParts] = x[noOfParts-1];
		y[noOfParts] = y[noOfParts-1];
		noOfParts++;
		noFood = true;
		repaint();
	}
	protected int keepScore(){
		return scoreCount;
	}
	
}
