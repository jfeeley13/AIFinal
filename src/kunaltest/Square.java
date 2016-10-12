package kunaltest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Square{

	int x, y;
	int width = 30; // TODO: Change this hardcoded stuff
	ConcurrentHashMap<Square, Integer> adjacent;
	boolean wall = false;
	
	boolean end = false;
	boolean start = false;
	boolean active = false;
	
	public Square(int x, int y){
		this.x = x;
		this.y = y;
		adjacent = new ConcurrentHashMap<Square, Integer>();
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
	
	public void drawCircle(Graphics g){
		g.setColor(Color.BLUE);
		g.fillOval(x*width, y*width, width, width);
	}
	
	public String toString(){
		return "X: " + x + " Y: " + y;
	}
	
	@Override
	public boolean equals(Object o){
		
		Square s = (Square) o;
		return this.x == s.x && this.y == s.y;
	}
}
