import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class Maze implements MouseListener, ActionListener {

	// Important stuff
	private int width, height;
	private Board board;

	// Graphics stuff (can be ignored)
	private JFrame main;

	private JButton instantaneousSearch;
	private JButton delayedSearch;
	private JTextField delay;
	private JButton endGame;
	private JButton restart;

	// More important stuff. Start point is the coordinate of the start square for the maze
	private Point start;
	private Point finish;

	// Not using this currently, it is for the delayed search if we actually want to see what is happening
	private Timer t = null;

	// Algorithm stuffs
	QLearning algorithm;

	// If the search is currently in progress we dont want the user to be able to use buttons
	private boolean inProgress = false;

	public Maze(int width, int height, Point start, Point finish) {
		this.start = start;
		this.finish = finish;

		this.width = width;
		this.height = height;

		// Set up the board
		board = new Board(width, height, start, finish);
		algorithm = new QLearning(board);

		initializeGraphics();
	}

	// For the most part this can be ignored, just initializing graphics objects
	private void initializeGraphics() {
		main = new JFrame();

		main.setContentPane(board);

		instantaneousSearch = new JButton("Instantaneous Search");
		instantaneousSearch.setBounds(603, 11, 181, 23);
		delayedSearch = new JButton("Delayed Search");
		delayedSearch.setBounds(603, 45, 181, 23);
		delay = new JTextField("20");
		delay.setBounds(834, 46, 100, 20);
		delay.setPreferredSize(new Dimension(100, 20));
		endGame = new JButton("Pause");
		endGame.setBounds(603, 81, 181, 23);
		board.setLayout(null);

		board.add(instantaneousSearch);
		board.add(delayedSearch);
		board.add(delay);
		board.add(endGame);

		restart = new JButton("Reset");
		restart.addActionListener(this);
		restart.setBounds(603, 115, 181, 23);
		board.add(restart);

		main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		main.setSize(1000, 1000);
		main.setVisible(true);

		board.addMouseListener(this);
		instantaneousSearch.addActionListener(this);
		delayedSearch.addActionListener(this);
		endGame.addActionListener(this);
	}

	// Initialize and start a timer
	// NOTE: DONT USE THIS TYPE OF SEARCH ANYMORE
	private void delayedSearch(int delay) {
		t = new Timer(delay, this);
		t.start();
	}

	// Triggered by the Instantaneous search buttons, should always do this type of search unless debugging
	private void noDelaySearch() {

		// FOR EXPERIMENT 1 -- SET UP SOME GOALS AND SEE HOW OFTEN WE FIND THEM
		//	board.setReward(50, 0, 999); // Putting them in corners and one in the middle of the board
		//board.setReward(50, 999, 0);
		//	board.setReward(50, 499, 0);
		//board.setReward(50, 0, 499);
		//board.setReward(50, 249, 0);
		//board.setReward(50, 0, 249);
		//board.setReward(50, 50, 50);
		board.setReward(50, 0, 19);
		board.setReward(50, 19, 0);
		
		board.grid[0][18].weight = -50;
		board.grid[1][18].weight = -50;
		board.grid[1][19].weight = -50;
		board.grid[0][17].weight = -50;
		board.grid[1][17].weight = -50;
		board.grid[2][17].weight = -50;
		board.grid[2][18].weight = -50;
		board.grid[2][19].weight = -50;

		board.grid[18][0].weight = -50;
		board.grid[18][1].weight = -50;
		board.grid[19][1].weight = -50;
		board.grid[17][0].weight = -50;
		board.grid[17][1].weight = -50;
		board.grid[17][2].weight = -50;
		board.grid[18][2].weight = -50;
		board.grid[19][2].weight = -50;
		

		// The search function in algorithm will find the goal 50 times, accumulating data each time (can be configured)
		algorithm.search();


		// After we are done searching we can print out the results (cumulative reward based on which trial it is on)
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(width + "x" + height + "-" + algorithm.stepSize + "-visited" + ".csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writer.println("# Mazes Completed,Visited Squares");

		for(int i = 0; i < algorithm.cumulativeRewards.size(); i++){
			writer.println((i+1) + "," + algorithm.visitedList.get(i));
		}

		System.out.println("TOTAL TIME: " + algorithm.totalTime);

		writer.close();
	}

	@Override
	public void mouseClicked(MouseEvent e) {


	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(inProgress) return;

		// If a square is clicked, turn it into a wall (maybe we can find a different system for creating mazes, such as through text file though)
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				// Which square is clicked -- just trust that this works
				if(e.getX() > i*Square.SIZE && e.getX() < i*Square.SIZE + Square.SIZE 
						&& e.getY() > j*Square.SIZE && e.getY() < j*Square.SIZE + Square.SIZE){
					System.out.println("click detected in: " + i + ", " + j);

					if(!board.grid[i][j].wall && !board.grid[i][j].end && !board.grid[i][j].start){
						board.grid[i][j].wall = true;

						board.removeAdjacentSquare(board.grid[i][j]);
					}

					main.repaint();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// This search completes as fast as it can
		if(e.getSource() == instantaneousSearch){
			if(!inProgress){
				inProgress = true;
				noDelaySearch();
				main.repaint();
				//this.search();
				//t.start();
			}
		}

		// This search uses a swing timer to visually display the path being taken
		if(e.getSource() == delayedSearch){
			if(!inProgress){
				inProgress = true;
				delayedSearch(Integer.parseInt(delay.getText())); // Let the user enter in the delay they want to use in the JTextField
			}
		}

		// Kind of misleading, this "pauses" the search
		if(e.getSource() == endGame){
			inProgress = false;
			if(t != null) t.stop();
		}

		// This resets the search to the starting square and stops the search
		if(e.getSource() == restart){
			board.changeActive(new Square(start.x, start.y));
			board.previousActive = null;
			//currentSquare = board.currentSquare;
			for(int i = 0; i < board.width; i++){
				for(int j = 0; j < board.height; j++){
					board.grid[i][j].setColor(null);
					if(i == start.x && j == start.x) board.grid[i][j].setColor(Color.GREEN);
					if(i == finish.x && j == finish.x) board.grid[i][j].setColor(Color.RED);


				}
			}

			inProgress = false;
			if(t != null) t.stop();
			main.repaint();

		}

		// On each timer tick perform a move, unless we have reached the goal
		if(e.getSource() == t){
			if(algorithm.iteration()){
				t.stop();
				//System.out.println("Time for goal: " + algorithm.numGoals + " is " + total);
				//algorithm
				algorithm.numGoals++;
				
				System.out.println("Completed maze " + algorithm.numGoals + " times");
				System.out.println("Cumulative reward is: " + algorithm.cumulativeReward);
				algorithm.cumulativeRewards.add(algorithm.cumulativeReward);
				algorithm.cumulativeReward = 0;
				System.out.println("We visited: " + algorithm.visitedSquares);
				algorithm.visitedSquares = 0;
			}
		}
	}
}