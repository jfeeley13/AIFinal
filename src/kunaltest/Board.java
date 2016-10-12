package kunaltest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Board extends JPanel{

	int width;
	int height;
	int squareSize;

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
					grid[i][j].start = true; 
					grid[i][j].active = true;
					currentSquare = grid[i][j];
					grid[i][j].setColor(Color.GREEN);
				}

				if(i == finish.x && j == finish.y){
					grid[i][j].end = true;
					grid[i][j].setColor(Color.RED);
				}

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
					grid[i][j].drawSquare(g, squareSize);
				}else if(grid[i][j].start == true){
					grid[i][j].drawSquare(g, squareSize);
				}else if(grid[i][j].wall == false){
					grid[i][j].drawSquare(g, squareSize);
				}else{
					grid[i][j].setColor(Color.BLACK);
					grid[i][j].drawSquare(g, squareSize);
				}

				if(grid[i][j].active == true){
					grid[i][j].drawCircle(g);
				}

					//System.out.println("Square: " + i + ", " + j);
				for(Map.Entry<Square, Integer> entry: grid[i][j].adjacent.entrySet()){
						//System.out.println("\t" + entry.getKey());
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
				for(Map.Entry<Square, Integer> e : grid[i][j].adjacent.entrySet()){
					if(e.getKey().x == s.x && e.getKey().y == s.y){
						grid[i][j].adjacent.remove(e.getKey());
					}
				}
			}
		}
	}
}