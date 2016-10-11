package kunaltest;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Board extends JPanel{

	int width;
	int height;
	int squareSize;

	Square[][] grid;
	Square currentSquare;

	public Board(int width, int height, Square start, Square finish){

		this.width = width;
		this.height = height;

		grid = new Square[width][height];

		// Set up the board
		for(int i = 0; i < width; i++){ 
			for(int j = 0; j < height; j++){
				grid[i][j] = new Square(i, j);

				if(i ==  start.x && j == start.y){
					grid[i][j].start = true; 
					grid[i][j].active = true;
					currentSquare = grid[i][j];
				}

				if(i == finish.x && j == finish.y) grid[i][j].end = true;

				if(i != width - 1){
					if(grid[i][j].end == true)
						grid[i][j].adjacent.put(new Square(i+1, j), 100);
					else
						grid[i][j].adjacent.put(new Square(i+1, j), 0);
				}

				if(i != 0){
					if(grid[i][j].end == true)
						grid[i][j].adjacent.put(new Square(i-1, j), 100);
					else
						grid[i][j].adjacent.put(new Square(i-1, j), 0);
				}

				if(j != height - 1){
					if(grid[i][j].end == true)
						grid[i][j].adjacent.put(new Square(i, j+1), 100);
					else
						grid[i][j].adjacent.put(new Square(i, j+1), 0);
				}

				if(j != 0){
					if(grid[i][j].end == true)
						grid[i][j].adjacent.put(new Square(i, j-1), 100);
					else
						grid[i][j].adjacent.put(new Square(i, j-1), 0);
				}


			}
		}
	}
	public void paintComponent(Graphics g){

		for(int i = 0; i < width; i++){ 
			for(int j = 0; j < height; j++){
				if(grid[i][j].end == true){
					grid[i][j].drawSquare(g, squareSize, Color.RED);
				}else if(grid[i][j].start == true){
					grid[i][j].drawSquare(g, squareSize, Color.GREEN);
				}else if(grid[i][j].wall == false){
					grid[i][j].drawSquare(g, squareSize, null);
				}else{
					grid[i][j].drawSquare(g, squareSize, Color.BLACK);
				}

				if(grid[i][j].active == true){
					grid[i][j].drawCircle(g);
				}

				//	System.out.println("Square: " + i + ", " + j);
				//for(int k = 0; k < grid[i][j].adjacent.size(); k++){
				//		System.out.println("\t" + grid[i][j].adjacent.get(k));
				//	}
			}
		}
	}
}