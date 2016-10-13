import java.awt.Point;

public class Main {
	public static void main(String[] args){
		int boardHeight = 10;
		int boardWidth = 10;
		
		Maze m = new Maze(boardWidth, boardHeight, new Point(0, 0), new Point(boardWidth - 1, boardHeight - 1));

		//QLearning algorithm = new QLearning(m.board);
		//algorithm.search();
		//inProgress = true;
		//QLearning algorithm = new QLearning(m.board);
		//algorithm.search();
		//m.delayedSearch(100);

	}
}