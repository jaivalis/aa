package aa2013;

import java.util.ArrayList;

import aa2013.Environment.action;

public abstract class Actor {
	protected int x,y;
	protected Policy policy;
	
	public Policy getPolicy() { return this.policy; }
	
	public int getX() { return this.x; }	
	public int getY() {	return this.y; }
	
	public void setX(int x) { this.x = x; }	
	public void setY(int y) { this.y = y; }
	
	public void move(ArrayList<Integer> newCoordinates) { 
		this.x = newCoordinates.get(0); 
		this.y = newCoordinates.get(1);
	}
	
	public void move(int xNew, int yNew) {
		this.x = xNew; 
		this.y = yNew;
	}

	public action getNextMoveDirection(State s) { return this.policy.getAction(s); }
}
