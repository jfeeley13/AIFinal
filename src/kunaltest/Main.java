package kunaltest;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args){
		int boardHeight = 20;
		int boardWidth = 20;

		Maze m = new Maze(boardWidth, boardHeight, new Square(0, 0), new Square(boardWidth - 1, boardHeight - 1));
	}



}
