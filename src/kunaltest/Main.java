package kunaltest;

public class Main {

	public static void main(String[] args){
		int boardHeight = 3;
		int boardWidth = 3;

		Maze m = new Maze(boardWidth, boardHeight, new Square(0, 0), new Square(boardWidth - 1, boardHeight - 1));
		
		QLearning algorithm = new QLearning(m.grid, m.currentSquare);
		
	}

}
