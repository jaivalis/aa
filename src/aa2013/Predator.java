package aa2013;


public class Predator extends Actor{
	
	public Predator(int x, int y) {
		this.x = x;
		this.y = y;
		this.policy = new RandomPolicy();
	}

	@Override
	public String toString() { return "Predator (" + this.x + ", " + this.y + ")"; }
}
