package kunaltest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

class Square {
	int x, y;
	//int SIZE = 30; // TODO: Change this hardcoded stuff
	Color c;
	CopyOnWriteArrayList<Square> adjacent;
	int weight = 0;
	boolean wall = false;
	
	static final int SIZE = 30;
	
	boolean end = false;
	boolean start = false;
	boolean active = false;
	
	Square(int x, int y) {
		this.x = x;
		this.y = y;
		adjacent = new CopyOnWriteArrayList<Square>();
	}
	
	void drawSquare(Graphics g) {
		if (c == null) {
			g.drawRect(x*SIZE, y*SIZE, SIZE, SIZE);
		} else {
			g.setColor(c);
			g.fillRect(x*SIZE, y*SIZE, SIZE, SIZE);
		}
		g.setColor(Color.BLACK);
	}
	
	void drawCircle(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(x*SIZE, y*SIZE, SIZE, SIZE);
	}
	
	@Override
	public String toString(){
		return "X: " + x + " Y: " + y;
	}
	
	@Override
	public boolean equals(Object o) {
		// Todo check if square?
		Square s = (Square) o;
		return this.x == s.x && this.y == s.y;
	}
	
	void setColor(Color c){
		this.c = c;
	}
	
	void setStart() {
		this.start = true; 
		this.active = true;
		this.setColor(Color.GREEN);
	}
	
	void setEnd() {
		this.end = true;
		this.setColor(Color.RED);
		this.weight = 100;
	}
}
