package kunaltest;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Board extends JPanel{
	
	/* Hardcoded for now */
	int width;
	int height;
	int squareSize;
	
	Square[][] grid;

	public Board(int width, int height, int squareSize){
		
		this.width = width;
		this.height = height;
		this.squareSize = squareSize;
		
		grid = new Square[width][height];
		
		// Set up the board
		for(int i = 0; i < width; i++){ 
			for(int j = 0; j < height; j++){
				grid[i][j] = new Square(i, j, squareSize);
			}
		}
	}
	public void paintComponent(Graphics g){

		for(int i = 0; i < width; i++){ 
			for(int j = 0; j < height; j++){
				if(grid[i][j].wall == 0){
					grid[i][j].drawSquare(g, squareSize, false);
				}else{
					grid[i][j].drawSquare(g, squareSize, true);
				}
			}
		}
	}
}