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
				this.states[i][j] = new State(i, j);
			}
		}
	}
	
	public State[][] getStates() { return this.states; }
	
	public State getState(int x, int y) { return this.states[x][y]; }
	public State getState(Actor a) { return this.states[a.getX()][a.getY()]; }
	
	public void setActor(Actor a, int x, int y) { this.states[x][y].setActor(a); }
	
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
	public void moveActor(Actor a, int xNew, int yNew) {
		this.states[a.getX()][a.getY()].setActor(null);

		if (a instanceof Prey) { // move reward
			this.states[a.getX()][a.getY()].setStateReward(0);
			this.states[xNew][yNew].setStateReward(this.PREYREWARD);
		}
		a.move(xNew, yNew);
		this.states[a.getX()][a.getY()].setActor(a);
	}
	
	/**
	 * Returns the coordinates of the block to be visited next according to the action chosen.
	 */
	public ArrayList<Integer> getCoordinates(int x, int y, action dir) {
		ArrayList<Integer> coordinates = new ArrayList<Integer>();		
		switch (dir) {
			case WAIT:
				coordinates.add(x);
				coordinates.add(y);
				break;
			case NORTH:
				if (x-1 < 0) { coordinates.add(dim - 1); }
				else { coordinates.add(x-1); }
				
				coordinates.add(y);
				break;
			case SOUTH:
				if (x+1 > dim-1) { coordinates.add(0); }
				else { coordinates.add(x+1); }
				
				coordinates.add(y);
				break;
			case EAST:
				coordinates.add(x);
				
				if (y+1 > dim-1) { coordinates.add(0); }
				else { coordinates.add(y+1); }
				break;
			case WEST:
				coordinates.add(x);
				
				if (y-1 < 0) { coordinates.add(dim - 1); }
				else { coordinates.add(y-1); }
				break;
		} return coordinates;
	}
	
	/**
	 * returns all the states that can be visited from coordinates x,y
	 */
	public ArrayList<State> getNeighbors(int x, int y) {
		ArrayList<State> neighbours = new ArrayList<State>();
		int neighx, neighy;
		
		// SELF
		neighbours.add(this.states[x][y]);
		
		// NORTH
		neighy = y;
		if (x-1 < 0) { neighx = dim - 1; }
		else { neighx = x-1; }
		neighbours.add(this.states[neighx][neighy]);
		
		// SOUTH		
		neighy = y;
		if (x+1 > dim-1) { neighx = 0; }
		else { neighx = x+1; }
		neighbours.add(this.states[neighx][neighy]);
		
		// EAST
		neighx = x;
		if (y+1 > dim-1) { neighy = 0; }
		else { neighy = y+1; }
		neighbours.add(this.states[neighx][neighy]);
		
		// WEST
		neighx = x;
		if (y-1 < 0) { neighy = dim - 1; }
		else { neighy = y-1; }
		neighbours.add(this.states[neighx][neighy]);
		
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
}
