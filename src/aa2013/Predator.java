package aa2013;

public class Predator extends Actor {
	
	public Predator(Grid g, int x, int y) {
		this.x = x; this.y = y;
		this.policy = new PredatorPolicy(g);
	}

	@Override
	public String toString() { return "Predator (" + this.x + ", " + this.y + ")"; }
}
