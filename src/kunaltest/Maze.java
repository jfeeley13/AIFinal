package kunaltest;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JFrame implements MouseListener{

	int squareSize = 40;
	Graphics g = super.getGraphics();
	int width, height;

	Board board;

	public Maze(int width, int height){
		
		this.width = width;
		this.height = height;
		
		board = new Board(width, height, squareSize);

		setContentPane(board);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
		repaint();

		board.addMouseListener(this);


	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(e.getX() > i*squareSize && e.getX() < i*squareSize + squareSize 
						&& e.getY() > j*squareSize && e.getY() < j*squareSize + squareSize){
					System.out.println("click detected in: " + i + ", " + j);

					board.grid[i][j].wall = 1 - board.grid[i][j].wall;

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