package kunaltest;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args){
		int boardHeight = 30;
		int boardWidth = 30;

		Maze m = new Maze(boardWidth, boardHeight, new Square(0, 0), new Square(boardWidth - 1, boardHeight - 1));
	}



}
