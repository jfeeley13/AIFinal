import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QLearning{
	
	Board board;
	double cumulativeReward;
	final int R = -1;
	double stepSize;
	
	int numGoals = 0;
	
	List<Double> cumulativeRewards = new ArrayList<Double>();
	
	public QLearning(Board board){
		this.board = board;
		this.cumulativeReward = 0;
		this.stepSize = 0.1;
	}
	
	// Run the algorithm until we find the goal 50 times. Record data
	public void search(){
		
		while(numGoals < 50){
			if(iteration() == true) {
				numGoals++;
				System.out.println("Completed maze " + numGoals + " times");
				System.out.println("Cumulative reward is: " + cumulativeReward);
				cumulativeRewards.add(cumulativeReward);
				cumulativeReward = 0;
			}	
		}	
	}
	
	public boolean iteration(){
		
		// If we are using greedy approach, this is the square (and its respective weight) we want to move to
		double bestWeight = 0;
		Square bestSquare = null;

		// If we are using a random approach we will just pick a random adjacent square to move to
		Random rand = new Random();

		boolean greedy = true; // TODO: Based on some exploration probability we should determine this

		List<Square> duplicateWeight = new ArrayList<Square>(); // If we have multiple squares with the same weight we need to pick one at random
		
		if(greedy){
			
			
			boolean firstLoop = true;
			for(Square s : board.currentSquare.adjacent){
				
				// This will be our starting point for comparison 
				if(firstLoop){
					bestWeight = board.grid[s.x][s.y].weight;
					bestSquare = board.grid[s.x][s.y];
					firstLoop = false;
					continue;
				}
				
				// We are going to keep track of the square with the best weight (and its value)
				if(board.grid[s.x][s.y].weight > bestWeight){
					bestWeight = board.grid[s.x][s.y].weight;
					bestSquare = board.grid[s.x][s.y];
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

		// Now we are going to use the weight of the current square and the weight of the square we want to go to to compute the NEW weight of the current square
		double currentWeight = board.grid[board.currentSquare.x][board.currentSquare.y].weight;

		// Yay debugging
		System.out.println("We are at: " + board.currentSquare);
		System.out.println("We want to get to: " + bestSquare + " with weight of: " + board.grid[bestSquare.x][bestSquare.y].weight);
		System.out.println(board.currentSquare.adjacent);
		System.out.println("Current weight: " + currentWeight + " Best Weight: " + board.grid[bestSquare.x][bestSquare.y].weight);
		System.out.println("Assigning this weight to current square: " + ( currentWeight + (stepSize * (R + (board.grid[bestSquare.x][bestSquare.y].weight - currentWeight)))));
		System.out.println();
		
		// This is sorta messy, look at google doc for this formula. Basically settings weight of current square to formula output
		board.grid[board.currentSquare.x][board.currentSquare.y].weight = currentWeight + (stepSize * (R + (board.grid[bestSquare.x][bestSquare.y].weight - currentWeight))); 
		
		// Until we reach the goal lets see what are cumulative reward is (we want to maximize this and minimize the number of times needed to get to the goal)
		cumulativeReward += board.grid[board.currentSquare.x][board.currentSquare.y].weight;
		
		// Cool we are at the goal, return
		if (bestWeight == 100) {
			return true;
		}

		// We aren't at the goal, lets move to the next square and keep looking
		move(bestSquare);

		return false;
	}
	
	private void move(Square s){

		// Change the active square to the one we want to move to
		board.changeActive(s);

	}
}