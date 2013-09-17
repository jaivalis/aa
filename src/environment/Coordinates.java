package environment;

import environment.Environment.action;

/**
 * represents a bi-dimensional coordinate.
 * x and y are set at construction time and cannot be changed later.
 * @author stablum
 *
 */
public class Coordinates {
	private int x;
	private int y;
	
	public Coordinates(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}	
	
	public Coordinates(Coordinates c) {
		return new Coordinates(c.x, c.y);
	}

	public int getX() { return x; }	
	public int getY() { return y; }
	

	
	public String toString() { 
		return "(" + this.x + ", " + this.y + ")";
	}
	
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
	 * Returns the new coordinates after taking action a 
	 */
	public Coordinates shift(action a) {
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
