package kunaltest;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JFrame implements MouseListener{

	int squareSize = 40;
	int width = 10;
	int height = 10;
	Graphics g = super.getGraphics();

	Square[][] board = new Square[width][height];
	DrawPane pane;

	public Board(int x, int y){
		
		for(int i = 0; i < width; i++){ 
			for(int j = 0; j < height; j++){
				board[i][j] = new Square(i, j, squareSize);
			}
		}

		pane = new DrawPane();

		setContentPane(pane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
		repaint();

		pane.addMouseListener(this);


	}

	class DrawPane extends JPanel{
		public void paintComponent(Graphics g){

			for(int i = 0; i < width; i++){ 
				for(int j = 0; j < height; j++){
					if(board[i][j].wall == 0){
						board[i][j].drawSquare(g, squareSize, false);
					}else{
						board[i][j].drawSquare(g, squareSize, true);
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(e.getX() > i*squareSize && e.getX() < i*squareSize + squareSize 
						&& e.getY() > j*squareSize && e.getY() < j*squareSize + squareSize){
					System.out.println("click detected in: " + i + ", " + j);
					//repaint();
					board[i][j].wall = 1 - board[i][j].wall;
					pane.repaint();
					this.repaint();
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