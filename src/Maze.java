import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class Maze implements MouseListener, ActionListener {
	//	Square[][] grid;
	private int width, height;
	private Board board;


	private JFrame main;

	private JButton instantaneousSearch;
	private JButton delayedSearch;
	private JTextField delay;
	private JButton endGame;
	private JButton restart;

	private Point start;
	//	private Point finish;

	//	boolean goal = false;
	private Timer t = null;

	QLearning algorithm;
	
	private boolean inProgress = false;

	public Maze(int width, int height, Point start, Point finish) {
		this.start = start;
		//		this.finish = finish;

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
		instantaneousSearch.setBounds(362, 11, 181, 23);
		delayedSearch = new JButton("Delayed Search");
		delayedSearch.setBounds(362, 45, 181, 23);
		delay = new JTextField("20");
		delay.setBounds(555, 46, 100, 20);
		delay.setPreferredSize(new Dimension(100, 20));
		endGame = new JButton("Pause");
		endGame.setBounds(362, 79, 181, 23);
		board.setLayout(null);

		board.add(instantaneousSearch);
		board.add(delayedSearch);
		board.add(delay);
		board.add(endGame);

		restart = new JButton("Reset");
		restart.addActionListener(this);
		restart.setBounds(362, 113, 181, 23);
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
	private void delayedSearch(int delay) {
		t = new Timer(delay, this);
		t.start();
	}

	private void noDelaySearch() {
		// TODO explain the "while"
		algorithm.search();
		
		PrintWriter writer = null;
		try {
			 writer = new PrintWriter("results.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.println("Cumulative reward,# Mazes Completed");
		
		for(int i = 0; i < algorithm.cumulativeRewards.size(); i++){
			writer.println(algorithm.cumulativeRewards.get(i) + "," + (i+1));
		}
		
		writer.close();
	}

//	// TODO: Maybe make this a static method in some QLearning class
//	/*private boolean search() {
//		double bestWeight = board.grid[board.currentSquare.adjacent.get(0).x][board.currentSquare.adjacent.get(0).y].weight;
//		//System.out.println("current: " + bestWeight);
//		//if(board.previousActive != null) System.out.println("previous: " + board.previousActive.weight);
//		Square bestSquare = board.currentSquare.adjacent.get(0);
//		double stepSize = 0.1;
//
//		int r = -1;
//
//		Random rand = new Random();
//
//		boolean greedy = true; // TODO: Based on some exploration probability we should determine this
//
//		List<Square> duplicateWeight = new ArrayList<Square>();
//		
//		if(greedy){
//			//	while(true){
//			System.out.println("Initial best weight: " + bestWeight);
//			// Check the nodes with the best weight
//			for(Square s : board.currentSquare.adjacent){
//				// We are going to keep track of the square with the best weight (and its value)
//				if(board.grid[s.x][s.y].weight > bestWeight){
//					bestWeight = board.grid[s.x][s.y].weight;
//					bestSquare = board.grid[s.x][s.y];
//					System.out.println("The best weight is updated to " + bestWeight);
//				}else if(board.grid[s.x][s.y].weight == bestWeight){
//					//duplicateWeight.add(e)
//					// TODO: IMPLEMENT THIS TO FIX THINGS THAT ARE BROKEN CURRENTLY
//					duplicateWeight.add(board.grid[s.x][s.y]);
//				}
//			}
//		}else{
//			bestSquare = board.currentSquare.adjacent.get(rand.nextInt(board.currentSquare.adjacent.size()));
//		}
//		
//		//if()
//
//		// TODO: What about a case in which two weights are the same and NOT 0? Gotta account for this
//
//		// Weights are all the same
//	//	if (bestSquare == null) {
//
//			// TODO: What if adjacency list is empty?
//			//bestSquare = board.currentSquare.adjacent.get(rand.nextInt(board.currentSquare.adjacent.size()));
//
//		//}
//		// TODO: IF PREVIOUS ACTIVE IS NULL?
//
//	/*	if(board.previousActive != null){ // If we have made our first move
//			
//			// Basically this is just a complicated way of adding a weight to a square that was just occupied
//			for (Square adjacent : board.previousActive.adjacent) {
//				if (adjacent.x == board.currentSquare.x && adjacent.y == board.currentSquare.y) {
//					//if (board.grid[board.currentSquare.x][board.currentSquare.y].c != Color.PINK){
//						System.out.println();
//
//						System.out.println("Previous active: " + board.previousActive);
//
//						System.out.println("Setting pink to: " + board.currentSquare.x + ", " + board.currentSquare.y);
//
//						// TODO: Ill work on simplifying this, basically just setting the weight of the previous square using algorithm
//						double currentWeight = board.grid[board.currentSquare.x][board.currentSquare.y].weight;
//						System.out.println(board.currentSquare.adjacent);
//						board.grid[board.currentSquare.x][board.currentSquare.y].weight = currentWeight + (stepSize * (r + (bestWeight - currentWeight))); 
//						System.out.println("current: " + bestWeight);
//						if(board.previousActive != null) System.out.println("previous: " + board.previousActive.weight);
//						//board.grid[board.currentSquare.x][board.currentSquare.y].setColor(Color.PINK);
//					//}
//				}
//			}	
//		}else{
//			double currentWeight = board.grid[board.currentSquare.x][board.currentSquare.y].weight;
//			board.grid[board.currentSquare.x][board.currentSquare.y].weight = currentWeight + (stepSize * (r + (bestWeight - currentWeight)));
//		}*/
//		double currentWeight = board.grid[board.currentSquare.x][board.currentSquare.y].weight;
//
//		
//		System.out.println("We are at: " + board.currentSquare);
//		System.out.println("We want to get to: " + bestSquare + " with weight of: " + board.grid[bestSquare.x][bestSquare.y].weight);
//		System.out.println(board.currentSquare.adjacent);
//		System.out.println("Assigning this weight to current square: " + ( currentWeight + (stepSize * (r + (board.grid[bestSquare.x][bestSquare.y].weight - currentWeight)))));
//		System.out.println("currentSquare: " + board.currentSquare.weight + " actual: " + board.grid[board.currentSquare.x][board.currentSquare.y].weight);
//		System.out.println();
//		
//		board.grid[board.currentSquare.x][board.currentSquare.y].weight = currentWeight + (stepSize * (r + (board.grid[bestSquare.x][bestSquare.y].weight - currentWeight))); 
//
//		if (bestWeight == 100) {
//			return true;
//		}
//
//		// Now we know which square we are going to progress to
//		move(bestSquare);
//		main.repaint();
//
//		return false;
//	}
//
//	private void move(Square s){
//
//		// Change the active square to the one we want to move to
//		board.changeActive(s);
//		//this.currentSquare = board.currentSquare;
//
//	}

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

			inProgress = false;
			if(t != null) t.stop();
			main.repaint();

		}

		// On each timer tick perform a move, unless we have reached the goal
		if(e.getSource() == t){
			if(algorithm.iteration()) t.stop();
		}
	}
}