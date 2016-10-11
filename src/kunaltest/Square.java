package kunaltest;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Square{

	int x, y, width;
	List<Square> adjacent;
	int wall = 0;
	
	public Square(int x, int y, int width){
		this.x = x;
		this.y = y;
		this.width = width;
		adjacent = new ArrayList<Square>();
	}
	
	public void drawSquare(Graphics g, int squareSize, boolean fill){
		if(wall == 0) g.drawRect(x*width, y*width, width, width);
		else g.fillRect(x*width, y*width, width, width);
	}
	
	public void addWall(){
		wall = 1;
	}
}
