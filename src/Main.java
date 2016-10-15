import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
	public static void main(String[] args){
		int boardHeight = 10;
		int boardWidth = 10;
		
		Maze m = new Maze(boardWidth, boardHeight, new Point(0, 0), new Point(9, 0));

		//QLearning algorithm = new QLearning(m.board);
		//algorithm.search();
		//inProgress = true;
		//QLearning algorithm = new QLearning(m.board);
		//algorithm.search();
		//m.delayedSearch(100);
		
	}
}