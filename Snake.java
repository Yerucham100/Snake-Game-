package snake;
import java.awt.BorderLayout;

import javax.swing.*;
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


