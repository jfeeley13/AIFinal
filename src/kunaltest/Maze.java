package kunaltest;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Maze implements MouseListener, ActionListener{

	int squareSize = 30;
	JFrame main;
	Graphics g;
	int width, height;

	Board board;
	Square[][] grid;
	Square currentSquare;
	JButton startGame;
	
//    BufferedImage buffer;
	Timer t;

	boolean inProgress = false;

	public Maze(int width, int height, Point start, Point finish){

		main = new JFrame();
		this.width = width;
		this.height = height;
		
       // this.buffer = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

       /* new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	search();
                main.repaint();
            }
        }).start();*/
		t = new Timer(500, this);
		
		board = new Board(width, height, start, finish);
		grid = board.grid;
		currentSquare = board.currentSquare;

		main.setContentPane(board);

		startGame = new JButton("Start");
		main.add(startGame);
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(1000, 1000);
		main.setVisible(true);


		board.addMouseListener(this);
		startGame.addActionListener(this);

	}
	
	public void search(){

		int bestWeight = 0;
		Square bestSquare = null;

		Random r = new Random();
	
	//	while(true){
			// Check the nodes with the best weight
			for(Map.Entry<Square, Integer> entry : currentSquare.adjacent.entrySet()){
				if(entry.getValue() > bestWeight){
					bestWeight = entry.getValue();
					bestSquare = entry.getKey();
				}
			}

			//System.out.println(currentSquare.adjacent);
			// Weights are all the same
			if(bestWeight == 0){
				List<Square> keys = new ArrayList<Square>(currentSquare.adjacent.keySet());
				//System.out.println(keys.size());
				Square randomSquare = keys.get(r.nextInt(keys.size()));
				bestSquare = randomSquare;
			}

			// Now we know which square we are going to progress to
			move(bestSquare);
			//if(bestWeight == 100) break;
			
			main.repaint();
			//bestWeight = 0;
			//return;
	//	}
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
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(e.getX() > i*squareSize && e.getX() < i*squareSize + squareSize 
						&& e.getY() > j*squareSize && e.getY() < j*squareSize + squareSize){
					System.out.println("click detected in: " + i + ", " + j);

					if(board.grid[i][j].wall == false  && board.grid[i][j].end == false && board.grid[i][j].start == false){
						board.grid[i][j].wall = true;
						//for(int k = 0; k < board.grid[i][j].adjacent.size(); k++){
						for(Map.Entry<Square, Integer> entry: board.grid[i][j].adjacent.entrySet()){
							entry.getKey().adjacent.remove(board.grid[i][j]);
							//System.out.println("We removed: " + board.grid[i][j] + " from: " + entry.getKey());
						}
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
		if(e.getSource() == startGame){
			if(!inProgress){
				this.search();
				inProgress = true;
				t.start();
			}
		}
		
		if(e.getSource() == t){
			search();
		}
	}
}