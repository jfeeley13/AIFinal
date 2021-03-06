import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * Represents a grid of squares and keeps track of a current square and previous square
 * Don't need to worry about this code too much
 */
public class Board extends JPanel{

	int width;
	int height;
	
	Square[][] grid;
	Square currentSquare;
	Square previousActive;

	public Board(int width, int height, Point start, Point finish){

		this.width = width;
		this.height = height;

		grid = new Square[width][height];


		// Set up the board
		for(int i = 0; i < width; i++){ 
			for(int j = 0; j < height; j++){
				grid[i][j] = new Square(i, j);

				if(i ==  start.x && j == start.y){
					grid[i][j].setStart();
					currentSquare = grid[i][j];
				}

				if(i == finish.x && j == finish.y){
					grid[i][j].setEnd();
				}

				if(i != width - 1){
					grid[i][j].adjacent.add(new Square(i+1, j));
				}

				if(i != 0){
					grid[i][j].adjacent.add(new Square(i-1, j));
				}

				if(j != height - 1){
					grid[i][j].adjacent.add(new Square(i, j+1));
				}

				if(j != 0){
					grid[i][j].adjacent.add(new Square(i, j-1));
				}
			}
		}
	}
	@Override
	public void paintComponent(Graphics g){

		for(int i = 0; i < width; i++){ 
			for(int j = 0; j < height; j++){
				if(grid[i][j].end == true){
					grid[i][j].drawSquare(g);
				}else if(grid[i][j].start == true){
					grid[i][j].drawSquare(g);
				}else if(grid[i][j].wall == false){
					grid[i][j].drawSquare(g);
				}else{
					grid[i][j].setColor(Color.BLACK);
					grid[i][j].drawSquare(g);
				}

				if(grid[i][j].active == true){
					grid[i][j].drawCircle(g);
				}
				
				if(grid[i][j].weight == 50){
					grid[i][j].setColor(Color.YELLOW);
					grid[i][j].drawSquare(g);
				}
			}
		}
	}

	public void changeActive(Square s){
		previousActive = currentSquare;
		grid[currentSquare.x][currentSquare.y].active = false;
		grid[s.x][s.y].active = true;
		currentSquare = grid[s.x][s.y];
		SwingUtilities.getRoot(this).repaint();
	}

	public void removeAdjacentSquare(Square s){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				for(Square square : grid[i][j].adjacent){
					if(square.x == s.x && square.y == s.y){
						grid[i][j].adjacent.remove(square);
					}
				}
			}
		}
	}
	
	public void setReward(double reward, int x, int y){		
		grid[x][y].reward = true;
	}
}