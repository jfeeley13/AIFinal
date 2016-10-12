package kunaltest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Maze implements MouseListener, ActionListener{
	
	Square[][] grid;
	int width, height;
	Board board;

	
	JFrame main;
	
	JButton instantaneousSearch;
	JButton delayedSearch;
	JTextField delay;
	JButton endGame;
	JButton restart;

	Point start;
	Point finish;

	boolean goal = false;
	Timer t = null;

	boolean inProgress = false;

	public Maze(int width, int height, Point start, Point finish){

		this.start = start;
		this.finish = finish;
		
		this.width = width;
		this.height = height;
		
		// Set up the board
		board = new Board(width, height, start, finish);

		initializeGraphics();

	}
	
	// For the most part this can be ignored, just initializing graphics objects
	private void initializeGraphics(){
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

		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(1000, 1000);
		main.setVisible(true);

		board.addMouseListener(this);
		instantaneousSearch.addActionListener(this);
		delayedSearch.addActionListener(this);
		endGame.addActionListener(this);
	}

	// Initialize and start a timer
	public void delayedSearch(int delay){
		t = new Timer(delay, this);
		t.start();
	}

	public void noDelaySearch(){

		while(!search()){} // Just keep going until we reach the goal

	}

	// TODO: Maybe make this a static method in some QLearning class
	public boolean search(){

		boolean foundGoal = false;
		int bestWeight = 0;
		Square bestSquare = null;

		Random r = new Random();

		//	while(true){
		// Check the nodes with the best weight
		for(Square s : board.currentSquare.adjacent){
			if(board.grid[s.x][s.y].weight > bestWeight){
				bestWeight = board.grid[s.x][s.y].weight;
				bestSquare = board.grid[s.x][s.y];
			}
		}

		//System.out.println(currentSquare.adjacent);
		// Weights are all the same
		if(bestWeight == 0){
			//System.out.println(keys.size());

			// TODO: What if adjacency list is empty?
			Square randomSquare = board.currentSquare.adjacent.get(r.nextInt(board.currentSquare.adjacent.size()));
			bestSquare = randomSquare;

		}else{
			// Set the weight from the previous current to the current square (which leads to the goal) also to be 100!
			// TODO: IF PREVIOUS ACTIVE IS NULL?
			for(Square adjacent : board.previousActive.adjacent){
				if(adjacent.x == board.currentSquare.x && adjacent.y == board.currentSquare.y){
					if(board.grid[board.currentSquare.x][board.currentSquare.y].c != Color.PINK){
						System.out.println();

						System.out.println("Previous active: " + board.previousActive);

						System.out.println("Setting pink to: " + board.currentSquare.x + ", " + board.currentSquare.y);
						adjacent.weight = 80;
						board.grid[board.currentSquare.x][board.currentSquare.y].setColor(Color.PINK);
					}
				}
			}			
		}

		if(bestWeight == 100){
			foundGoal = true;
			return foundGoal;
		}

		// Now we know which square we are going to progress to
		move(bestSquare);
		main.repaint();

		return foundGoal;
	}

	private void move(Square s){

		// Change the active square to the one we want to move to
		board.changeActive(s);
		//this.currentSquare = board.currentSquare;

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

					if(board.grid[i][j].wall == false  && board.grid[i][j].end == false && board.grid[i][j].start == false){
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
			if(search() == true) t.stop();
		}
	}
}