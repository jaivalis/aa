package aa2013;

import java.util.ArrayList;

public abstract class Actor {
	protected int x,y;
	
	public abstract Board.direction getNextMoveDirection();
	
	public int getX() { return this.x; }	
	public int getY() {	return this.y; }
	
	public void setX(int x) { this.x = x; }	
	public void setY(int y) { this.y = y; }
	
	public void move(ArrayList<Integer> newCoordinates) { 
		this.x = newCoordinates.get(0); 
		this.y = newCoordinates.get(1);
	}
}
