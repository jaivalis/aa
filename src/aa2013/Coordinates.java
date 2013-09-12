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

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Coordinates getCopy() {
		return new Coordinates(this.x, this.y);
	}
	
	public boolean sameAs(Coordinates c) {
		return this.getX() == c.getX() 
				&& this.getY() == c.getY();
	}
	
}
