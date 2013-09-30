package environment;

import environment.Algorithms.action;

/**
 * represents a bi-dimensional coordinate.
 * x and y are set at construction time and cannot be changed later.
 * @author stablum
 */
public class Coordinates {
	private int x;
	private int y;
		
	public Coordinates(int x, int y) {
		this.x = x;	this.y = y;
	}	
	
	public Coordinates(Coordinates c) {
		this.x = c.x; this.y = c.y;
	}

	public int getX() { return x; }	
	public int getY() { return y; }
	@Override
	public String toString() { return "(" + this.x + ", " + this.y + ")"; }
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Coordinates)) { return false; }
	    Coordinates otherCoordinates = (Coordinates) other;
		return this.getX() == otherCoordinates.getX() 
				&& this.getY() == otherCoordinates.getY();
	}
	
	/**
	 * Returns the action required to move from this coordinates to coordinates other.
	 * (requires this to be neighboring to other)
	 */
	public action getTransitionAction(Coordinates other) {
		int xOther = other.x, yOther = other.y;
		if (x == xOther	&& y == yOther) { return action.WAIT; }
		if (x == xOther - 1 || (xOther == 0 && x == 10)) { return action.SOUTH; }
		if (x == xOther + 1 || (xOther == Util.DIM-1 && x == 0)) { return action.NORTH; }
		if (y == yOther - 1 || (yOther == 0 && y == 10)) { return action.EAST; }
		if (y == yOther + 1 || (yOther == Util.DIM-1 && y == 0)) { return action.WEST; }
		return null;
	}
	
	/**
	 * this is a simple function that calculates the opposite of an action
	 * @param other the other pair of coordinates (besides 'this')
	 * @return action
	 */
	public action getOppositeTransitionAction(Coordinates other) {
		action ta = this.getTransitionAction(other);
		return ta.getOpposite();
	}

	// TODO : set to void.
	/**
	 * Returns the new coordinates after taking action a 
	 */
	public Coordinates getShifted(action a) {
		Coordinates dest = null;
		switch (a) {
			case WAIT:
				dest = new Coordinates(this);
				break;
			case NORTH:
				dest = new Coordinates(
					this.getX() - 1 < 0 
						? Util.DIM - 1 
						: this.getX() - 1,
						this.getY()
				);
				break;
			case SOUTH:
				dest = new Coordinates(
						this.getX() + 1 > Util.DIM - 1 
						? 0 
						: this.getX() + 1,
						this.getY()
				);
				break;
			case EAST:
				dest = new Coordinates(
						this.getX(),
						this.getY()+1 > Util.DIM-1
						? 0
					    : this.getY() + 1
					);
				break;
			case WEST:
				dest = new Coordinates(
						this.getX(),
						this.getY() - 1 < 0
						? Util.DIM - 1
						: this.getY() - 1
				);
				break;
		}
		return dest;
	}
	
}
