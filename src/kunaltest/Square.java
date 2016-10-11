package kunaltest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Square{

	int x, y;
	int width = 30; // TODO: Change this hardcoded stuff
	List<Square> adjacent;
	boolean wall = false;
	
	boolean end = false;
	boolean start = false;
	
	
	public Square(int x, int y){
		this.x = x;
		this.y = y;
		adjacent = new ArrayList<Square>();
	}
	
	public void drawSquare(Graphics g, int squareSize, Color c){
		if(c == null){
			g.drawRect(x*width, y*width, width, width);
		}else{
			g.setColor(c);
			g.fillRect(x*width, y*width, width, width);
		}
		g.setColor(Color.BLACK);
	}
	
	public String toString(){
		return "X: " + x + " Y: " + y;
	}
}
