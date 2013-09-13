package aa2013;

import java.text.DecimalFormat;
import java.util.ArrayList;

import aa2013.Environment.action;

public class Grid {

	private State[][] states;
	private int dim;
	

	public final double PREYREWARD = 10.0;
	
	public Grid(int dim) {
		this.dim = dim;
		this.states = new State[dim][dim];
		for(int i = 0; i < this.states.length; i++) {
			for(int j = 0; j < this.states[i].length; j++) {
				this.states[i][j] = new State(new Coordinates(i,j));
			}
		}
	}
	
	public State[][] getStates() { return this.states; }
	
	public State getState(Coordinates pos) { return this.states[pos.getX()][pos.getY()]; }
	public State getState(Actor a) { return this.getState(a.getCoordinates()); }
	
	public void setActor(Actor a, Coordinates c) { this.states[c.getX()][c.getY()].setActor(a); }
	
	public int getDim() { return this.dim; }
	
	public void initializeStateValues(double d) {
		for(int i = 0; i < this.states.length; i++) {
			for(int j = 0; j < this.states[i].length; j++) {
				this.states[i][j].setStateValue(d);
				this.states[i][j].setStateReward(d);
			}
		}
	} 
	
	/**
	 * Update the board according to Actor move.
	 */
	public void moveActor(Actor a, Coordinates c) {
		Coordinates oldPos = a.getCoordinates();
		this.states[oldPos.getX()][oldPos.getY()].setActor(null);

		if (a instanceof Prey) { // move reward
			this.states[oldPos.getX()][oldPos.getY()].setStateReward(0);
			this.states[c.getX()][c.getY()].setStateReward(this.PREYREWARD);
		}
		a.move(c);
		this.states[oldPos.getX()][oldPos.getY()].setActor(a);
	}
	
	/**
	 * Returns the coordinates of the block to be visited next according to the action chosen.
	 */
	public Coordinates nearbyCoordinates(Coordinates orig, action dir) {
		Coordinates dest = null;
		switch (dir) {
			case WAIT:
				dest = orig.getCopy();
				break;
			case NORTH:
				dest = new Coordinates(
					orig.getX() - 1 < 0 
						? dim - 1 
						: orig.getX() - 1,
					orig.getY()
				);
				break;
			case SOUTH:
				dest = new Coordinates(
					orig.getX() + 1 > dim - 1 
						? 0 
						: orig.getX() + 1,
					orig.getY()
				);
				break;
			case EAST:
				dest = new Coordinates(
					orig.getX(),
					orig.getY()+1 > dim-1
						? 0
					    : orig.getY() + 1
					);
				break;
			case WEST:
				dest = new Coordinates(
					orig.getX(),
					orig.getY() - 1 < 0
						? dim - 1
						: orig.getY() - 1
				);
				break;
		}
		return dest;
	}
	
	/**
	 * returns all the states that can be visited from coordinates x,y
	 */
	public ArrayList<State> getNeighbors(Coordinates c) {
		ArrayList<State> neighbours = new ArrayList<State>();
		
		for(action a : Environment.action.values()) {
			neighbours.add(this.getState(this.nearbyCoordinates(c, a)));
		}
		
		return neighbours;
	}
	
	/**
	 * used for debugging
	 */
	public void printStateValues() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		for(int i = 0; i < this.states.length; i++){
			for(int j = 0; j < this.states[i].length; j++){				
				System.out.print(twoDForm.format(this.states[i][j].getStateValue()) + "\t");
				// System.out.print(this.board[i][j].getStateValue() + "\t");
			}
			System.out.println();
		}
		System.out.println(twoDForm.format(this.states[5][5].getStateValue()) + "\t");
	}	

	/**
	 * used for debugging actions
	 */
	public void printActions(Policy policy) {
		for(int i = 0; i < this.states.length; i++){
			for(int j = 0; j < this.states[i].length; j++){
				System.out.print(policy.getActionString(this.states[i][j]) + "\t");
				// System.out.print(this.board[i][j].getStateValue() + "\t");
			}
			System.out.println();
		}
	}
	
	@Override
	public String toString() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		String ret = "";
		for(int i = 0; i < this.states.length; i++){
			for(int j = 0; j < this.states[i].length; j++){
				ret += twoDForm.format(this.states[i][j].getStateValue()) + "\t";
			} ret += "\n";
		}
		return ret;
	}

}
