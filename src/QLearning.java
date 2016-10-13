/*package kunaltest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.SwingUtilities;

// TODO: THIS CLASS ISNT USED RIGHT NOW
public class QLearning {

	Board b;
	Square currentSquare;

	public QLearning(Board b){
		this.b = b;
		this.currentSquare = b.currentSquare;
	}

	public void search(){

		int bestWeight = 0;
		Square bestSquare = null;

		Random r = new Random();

		while(true){
			// Check the nodes with the best weight
			for(Map.Entry<Square, Integer> entry : currentSquare.adjacent.entrySet()){
				if(entry.getValue() > bestWeight){
					bestWeight = entry.getValue();
					bestSquare = entry.getKey();
				}
			}

			System.out.println(currentSquare.adjacent);
			// Weights are all the same
			if(bestWeight == 0){
				List<Square> keys = new ArrayList<Square>(currentSquare.adjacent.keySet());
				System.out.println(keys.size());
				Square randomSquare = keys.get(r.nextInt(keys.size()));
				bestSquare = randomSquare;
			}

			// Now we know which square we are going to progress to
			move(bestSquare);
			if(bestWeight == 100) break;
			bestWeight = 0;
			//return;
		}

	}

	private void move(Square s){

		// This way we can actually see it move (may change this implementation later)
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		b.changeActive(s);
		this.currentSquare = b.currentSquare;
	}
}*/
