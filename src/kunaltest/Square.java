package kunaltest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Square{

	int x, y;
	int width = 30; // TODO: Change this hardcoded stuff
	Color c;
	CopyOnWriteArrayList<Square> adjacent;
	int weight = 0;
	boolean wall = false;
	
	static final int SIZE = 20;
	
	boolean end = false;
	boolean start = false;
	boolean active = false;
	
	public Square(int x, int y){
		this.x = x;
		this.y = y;
		adjacent = new CopyOnWriteArrayList<Square>();
	}
	
	public void drawSquare(Graphics g, int squareSize){
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
	
	@Override
	public String toString(){
		return "X: " + x + " Y: " + y;
	}
	
	@Override
	public boolean equals(Object o){
		
		Square s = (Square) o;
		return this.x == s.x && this.y == s.y;
	}
	
	public void setColor(Color c){
		this.c = c;
	}
	
	public void setStart(){
		this.start = true; 
		this.active = true;
		this.setColor(Color.GREEN);
	}
	
	public void setEnd(){
		this.end = true;
		this.setColor(Color.RED);
		this.weight = 100;
	}
}
