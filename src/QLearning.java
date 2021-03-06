import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QLearning{
	

	Board board;
	double cumulativeReward = 0;
	final double R = -1.0;
	double stepSize = 0.1;

	double exploration = 0.0;

	int numGoals = 0;
	
	long totalTime;
	
	int visitedSquares = 0;
	
	int treasure1 = 0;
	int treasure2 = 0;
	
	List<Double> cumulativeRewards = new ArrayList<Double>();
	List<Square> treasuresFound = new ArrayList<Square>(); //FOR EXPERIMENT 1
	List<Integer> visitedList = new ArrayList<Integer>();
	
	QLearning(Board board){
		this.board = board;
	}
	
	// Run the algorithm until we find the goal 50 times. Record data
	void search(){
		
		long totalStartTime = System.currentTimeMillis();
		
		long startTime = System.currentTimeMillis();

		while(numGoals < 200){
			
			
			
			if(iteration()) {
				
				if(numGoals == 99 && board.grid[9][0].end == true){
					board.grid[9][0].end = false;
					board.grid[9][0].weight = 0;
					board.grid[0][9].setEnd();
					System.out.println("CHANGING END");
				}
				
				long endTime = System.currentTimeMillis();
				long total = endTime - startTime;
				
				numGoals++;

				//System.out.println("Time for goal: " + numGoals + " is " + total);
				
				System.out.println("Completed maze " + numGoals + " times");
				//System.out.println("Cumulative reward is: " + cumulativeReward);
				cumulativeRewards.add(cumulativeReward);
				cumulativeReward = 0;
				System.out.println("We visited: " + visitedSquares);
				visitedList.add(visitedSquares);
				visitedSquares = 0;
				
				System.out.println();
				board.changeActive(new Square(0, 0));
				board.previousActive = null;
				
				for(int i = 0; i < board.width; i++){
					for(int j = 0; j < board.height; j++){
						board.grid[i][j].setColor(null);
					}
				}
				startTime = System.currentTimeMillis();
			}	
			
		}
		
		System.out.println("Treasure at 0, 19 found: " + treasure1 + " times");
		System.out.println("Treasure at 19, 0 found: " + treasure2 + " times");

		
		long totalEndTime = System.currentTimeMillis();
		this.totalTime = totalEndTime - totalStartTime;
		
	}
	
	boolean iteration(){
		
		// If we are using greedy approach, this is the square (and its respective weight) we want to move to
		double bestWeight = 0;
		Square bestSquare = null;

		// If we are using a random approach we will just pick a random adjacent square to move to
		Random rand = new Random();

		boolean greedy = true; // TODO: Based on some exploration probability we should determine this
		if(Math.random() < exploration){
			greedy = false;
			//System.out.println("Exploring");
		}
		
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
			Square randomSquare = board.currentSquare.adjacent.get(rand.nextInt(board.currentSquare.adjacent.size()));
			bestSquare = randomSquare;
			bestWeight = board.grid[randomSquare.x][randomSquare.y].weight;
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

		// FOR EXPERIMENT 1
		if(board.grid[bestSquare.x][bestSquare.y].reward){ // Oheylookwefoundatreasure
			System.out.println("Found treasure: " + bestSquare + " at trial " + (numGoals + 1));
			
			if(bestSquare.x == 0 && bestSquare.y == 19) treasure1++;
			else treasure2++;
			
		}
	
		// Yay debugging
	//	System.out.println("We are at: " + board.currentSquare);
	//	System.out.println("We want to get to: " + bestSquare + " with weight of: " + board.grid[bestSquare.x][bestSquare.y].weight);
	//	System.out.println(board.currentSquare.adjacent);
		//System.out.println("Current weight: " + currentWeight + " Best Weight: " + board.grid[bestSquare.x][bestSquare.y].weight);
	//	System.out.println("Assigning this weight to current square: " + ( currentWeight + (stepSize * (R + (board.grid[bestSquare.x][bestSquare.y].weight - currentWeight)))));
	//	System.out.println();

		Square currentSquare = board.grid[board.currentSquare.x][board.currentSquare.y];
		// Now we are going to use the weight of the current square and the weight of the square we want to go to to compute the NEW weight of the current square
		double currentWeight = currentSquare.weight;

		// This is sorta messy, look at google doc for this formula. Basically settings weight of current square to formula output
		// TODO I get an error about "possible lossy conversion from double to int" here
		double newWeight = currentWeight + (stepSize * (R + (board.grid[bestSquare.x][bestSquare.y].weight - currentWeight)));
		currentSquare.weight = newWeight;

		// Until we reach the goal lets see what are cumulative reward is (we want to maximize this and minimize the number of times needed to get to the goal)
		cumulativeReward += currentSquare.weight;
		
		if(board.grid[board.currentSquare.x][board.currentSquare.y].c == null){
			board.grid[board.currentSquare.x][board.currentSquare.y].setColor(Color.PINK);
		}
		
		// Cool we are at the goal, return
		if (bestWeight == 100) {
			return true;
		}
		
		visitedSquares++;

		// We aren't at the goal, lets move to the next square and keep looking
		move(bestSquare);

		return false;
	}
	
	private void move(Square s){

		// Change the active square to the one we want to move to
		board.changeActive(s);

	}
}