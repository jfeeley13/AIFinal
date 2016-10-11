package kunaltest;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JFrame implements MouseListener{

	int squareSize = 30;
	Graphics g = super.getGraphics();
	int width, height;

	Board board;

	public Maze(int width, int height, Square start, Square finish){

		this.width = width;
		this.height = height;

		board = new Board(width, height, start, finish);

		setContentPane(board);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 1000);
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

					if(board.grid[i][j].wall == false  && board.grid[i][j].end == false && board.grid[i][j].start == false){
						board.grid[i][j].wall = true;
						for(int k = 0; k < width; k++){
							for(int l = 0; l < height; l++){
								for(int m = 0; m < board.grid[k][l].adjacent.size(); m++){
									if(board.grid[k][l].adjacent.get(m).x == i && board.grid[k][l].adjacent.get(m).y == j){
										System.out.println("We removed: " + board.grid[k][l].adjacent.get(m) + " from: " + board.grid[k][l]);
										board.grid[k][l].adjacent.remove(m);
									}
								}
							}
						}
					}

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