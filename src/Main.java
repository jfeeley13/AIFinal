import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
	public static void main(String[] args){
		int boardHeight = 3;
		int boardWidth = 3;
		
		Maze m = new Maze(boardWidth, boardHeight, new Point(0, 0), new Point(boardWidth - 1, boardHeight - 1));

		//QLearning algorithm = new QLearning(m.board);
		//algorithm.search();
		//inProgress = true;
		//QLearning algorithm = new QLearning(m.board);
		//algorithm.search();
		//m.delayedSearch(100);
		
	}
}