import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QLearning{
	
	Board board;
	public QLearning(Board board){
		this.board = board;
	}
	
	public boolean search(){
		double bestWeight = board.grid[board.currentSquare.adjacent.get(0).x][board.currentSquare.adjacent.get(0).y].weight;
		//System.out.println("current: " + bestWeight);
		//if(board.previousActive != null) System.out.println("previous: " + board.previousActive.weight);
		Square bestSquare = board.currentSquare.adjacent.get(0);
		double stepSize = 0.1;

		int r = -1;

		Random rand = new Random();

		boolean greedy = true; // TODO: Based on some exploration probability we should determine this

		List<Square> duplicateWeight = new ArrayList<Square>();
		
		if(greedy){
			//	while(true){
			//System.out.println("Initial best weight: " + bestWeight);
			// Check the nodes with the best weight
			for(Square s : board.currentSquare.adjacent){
				// We are going to keep track of the square with the best weight (and its value)
				if(board.grid[s.x][s.y].weight > bestWeight){
					bestWeight = board.grid[s.x][s.y].weight;
					bestSquare = board.grid[s.x][s.y];
					//System.out.println("The best weight is updated to " + bestWeight);
				}
			}
		}else{
			bestSquare = board.currentSquare.adjacent.get(rand.nextInt(board.currentSquare.adjacent.size()));
		}
		
		// Now lets check to make sure that our best square didnt have the SAME weight as any other squares in the adjacency list
		for(Square s : board.currentSquare.adjacent){
			if(!s.equals(bestSquare) && board.grid[s.x][s.y].weight == bestWeight){
				duplicateWeight.add(board.grid[s.x][s.y]);
			}
		}
		
		// If there were indeed duplicates, pick from them randomly
		if(!duplicateWeight.isEmpty()) {
			duplicateWeight.add(bestSquare);
			bestSquare = duplicateWeight.get(rand.nextInt(duplicateWeight.size()));
			bestWeight = board.grid[bestSquare.x][bestSquare.y].weight;
			
		}

		//if()

		// TODO: What about a case in which two weights are the same and NOT 0? Gotta account for this

		// Weights are all the same
	//	if (bestSquare == null) {

			// TODO: What if adjacency list is empty?
			//bestSquare = board.currentSquare.adjacent.get(rand.nextInt(board.currentSquare.adjacent.size()));

		//}
		// TODO: IF PREVIOUS ACTIVE IS NULL?

	/*	if(board.previousActive != null){ // If we have made our first move
			
			// Basically this is just a complicated way of adding a weight to a square that was just occupied
			for (Square adjacent : board.previousActive.adjacent) {
				if (adjacent.x == board.currentSquare.x && adjacent.y == board.currentSquare.y) {
					//if (board.grid[board.currentSquare.x][board.currentSquare.y].c != Color.PINK){
						System.out.println();

						System.out.println("Previous active: " + board.previousActive);

						System.out.println("Setting pink to: " + board.currentSquare.x + ", " + board.currentSquare.y);

						// TODO: Ill work on simplifying this, basically just setting the weight of the previous square using algorithm
						double currentWeight = board.grid[board.currentSquare.x][board.currentSquare.y].weight;
						System.out.println(board.currentSquare.adjacent);
						board.grid[board.currentSquare.x][board.currentSquare.y].weight = currentWeight + (stepSize * (r + (bestWeight - currentWeight))); 
						System.out.println("current: " + bestWeight);
						if(board.previousActive != null) System.out.println("previous: " + board.previousActive.weight);
						//board.grid[board.currentSquare.x][board.currentSquare.y].setColor(Color.PINK);
					//}
				}
			}	
		}else{
			double currentWeight = board.grid[board.currentSquare.x][board.currentSquare.y].weight;
			board.grid[board.currentSquare.x][board.currentSquare.y].weight = currentWeight + (stepSize * (r + (bestWeight - currentWeight)));
		}*/
		double currentWeight = board.grid[board.currentSquare.x][board.currentSquare.y].weight;

		
		System.out.println("We are at: " + board.currentSquare);
		System.out.println("We want to get to: " + bestSquare + " with weight of: " + board.grid[bestSquare.x][bestSquare.y].weight);
		System.out.println(board.currentSquare.adjacent);
		System.out.println("Current weight: " + currentWeight + " Best Weight: " + board.grid[bestSquare.x][bestSquare.y].weight);
		System.out.println("Assigning this weight to current square: " + ( currentWeight + (stepSize * (r + (board.grid[bestSquare.x][bestSquare.y].weight - currentWeight)))));
	//	System.out.println("currentSquare: " + board.currentSquare.weight + " actual: " + board.grid[board.currentSquare.x][board.currentSquare.y].weight);
		System.out.println();
		
		board.grid[board.currentSquare.x][board.currentSquare.y].weight = currentWeight + (stepSize * (r + (board.grid[bestSquare.x][bestSquare.y].weight - currentWeight))); 

		if (bestWeight == 100) {
			return true;
		}

		// Now we know which square we are going to progress to
		move(bestSquare);
		//main.repaint();

		return false;
	}
	
	private void move(Square s){

		// Change the active square to the one we want to move to
		board.changeActive(s);
		//this.currentSquare = board.currentSquare;

	}
}