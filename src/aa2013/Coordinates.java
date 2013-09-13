package aa2013;

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

	public int getX() { return x; }	
	public int getY() { return y; }
	
	public Coordinates getCopy() {
		return new Coordinates(this.x, this.y);
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
	
}
