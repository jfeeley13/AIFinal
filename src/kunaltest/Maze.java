package kunaltest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Maze implements MouseListener, ActionListener{

	int squareSize = 30;
	JFrame main;
	Graphics g;
	int width, height;

	Board board;
	Square[][] grid;
	Square currentSquare;
	JButton instantaneousSearch;
	JButton delayedSearch;
	JTextField delay;
	JButton endGame;
	JButton restart;

	Point start;
	Point finish;

	boolean goal = false;

	//    BufferedImage buffer;
	Timer t = null;

	boolean inProgress = false;

	public Maze(int width, int height, Point start, Point finish){

		this.start = start;
		this.finish = finish;

		main = new JFrame();
		this.width = width;
		this.height = height;

		JPanel p = new JPanel();

		// this.buffer = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

		/* new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	search();
                main.repaint();
            }
        }).start();*/
		//	t = new Timer(0, this);

		board = new Board(width, height, start, finish);
		grid = board.grid;
		currentSquare = board.currentSquare;

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

		//main.add(p);

		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(1000, 1000);
		main.setVisible(true);


		board.addMouseListener(this);
		instantaneousSearch.addActionListener(this);
		delayedSearch.addActionListener(this);
		endGame.addActionListener(this);

	}

	public void delayedSearch(int delay){
		t = new Timer(delay, this);
		t.start();
	}

	public void noDelaySearch(){

		while(!search()){
			//search();
		}


	}

	// TODO: Maybe make this a static method in some QLearning class
	public boolean search(){

		boolean foundGoal = false;
		int bestWeight = 0;
		Square bestSquare = null;

		Random r = new Random();

		//	while(true){
		// Check the nodes with the best weight
		for(Square s : currentSquare.adjacent){
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
			Square randomSquare = currentSquare.adjacent.get(r.nextInt(currentSquare.adjacent.size()));
			bestSquare = randomSquare;

		}else{
			// Set the weight from the previous current to the current square (which leads to the goal) also to be 100!
			// TODO: IF PREVIOUS ACTIVE IS NULL?
			for(Square adjacent : board.previousActive.adjacent){
				if(adjacent.x == currentSquare.x && adjacent.y == currentSquare.y){
					if(board.grid[currentSquare.x][currentSquare.y].c != Color.PINK){
						System.out.println();

						System.out.println("Previous active: " + board.previousActive);

						System.out.println("Setting pink to: " + currentSquare.x + ", " + currentSquare.y);
						adjacent.weight = 80;
						board.grid[currentSquare.x][currentSquare.y].setColor(Color.PINK);
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

		//return bestWeight;
		//bestWeight = 0;
		//return;
		//}
	}

	private void move(Square s){

		// This way we can actually see it move (may change this implementation later)
		//try {
		//Thread.sleep(20);
		//	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}

		board.changeActive(s);
		this.currentSquare = board.currentSquare;

	}

	@Override
	public void mouseClicked(MouseEvent e) {


	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(inProgress) return;

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(e.getX() > i*squareSize && e.getX() < i*squareSize + squareSize 
						&& e.getY() > j*squareSize && e.getY() < j*squareSize + squareSize){
					System.out.println("click detected in: " + i + ", " + j);

					if(board.grid[i][j].wall == false  && board.grid[i][j].end == false && board.grid[i][j].start == false){
						board.grid[i][j].wall = true;

						board.removeAdjacentSquare(board.grid[i][j]);

						//}
						/*for(int k = 0; k < width; k++){
							for(int l = 0; l < height; l++){
								for(int m = 0; m < board.grid[k][l].adjacent.size(); m++){
									if(board.grid[k][l].adjacent.get(m).x == i && board.grid[k][l].adjacent.get(m).y == j){
										System.out.println("We removed: " + board.grid[k][l].adjacent.get(m) + " from: " + board.grid[k][l]);
										board.grid[k][l].adjacent.remove(m);
									}
								}
							}
						}*/
					}

					main.repaint();
				}
			}
		}

		//System.out.println(board.grid[0][0].adjacent);

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
		if(e.getSource() == instantaneousSearch){
			if(!inProgress){
				inProgress = true;
				noDelaySearch();
				//this.search();
				//t.start();
			}
		}

		if(e.getSource() == delayedSearch){
			if(!inProgress){

				inProgress = true;
				delayedSearch(Integer.parseInt(delay.getText()));
			}
		}

		if(e.getSource() == endGame){
			///	t.stop();
			inProgress = false;
			if(t != null) t.stop();
		}

		if(e.getSource() == restart){
			board.changeActive(new Square(start.x, start.y));
			board.previousActive = null;
			this.currentSquare = board.currentSquare;

			inProgress = false;
			if(t != null) t.stop();
			main.repaint();

		}
		if(e.getSource() == t){
			if(search() == true) t.stop();
		}
	}
}