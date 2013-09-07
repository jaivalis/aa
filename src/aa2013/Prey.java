package aa2013;


public class Prey extends Actor {
	private boolean alive;

	public Prey(int x, int y) {
		this.x = x;
		this.y = y;
		this.alive = true;
		this.policy = new RandomPreyPolicy();
	}

	@Override
	public String toString() { return "Prey (" + this.x + ", " + this.y + ")"; }

	public boolean getAlive() { return alive; }
	public void setAlive(boolean a) { this.alive = a; }
}
