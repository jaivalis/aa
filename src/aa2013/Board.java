package aa2013;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Board {

	private State[][] board;
	private State[][] oldBoard;
	private final int dim;
	
	private ArrayList<Predator> predators;
	private Prey prey;

	private final double PREYREWARD = 10.0;
	private final double THETA = 1.0; // threshold for the loop end condition
	private final double GAMMA = 0.8;
		
	public enum action { NORTH, SOUTH, EAST, WEST, WAIT };
	
	public Board(int dim) {
		this.dim = dim;
		this.board = new State[dim][dim];
		for(int i = 0; i < this.board.length; i++){
			for(int j = 0; j < this.board[i].length; j++){
				this.board[i][j] = new State(i, j);
			}
		}
		this.oldBoard = board;
		
		Predator pred = new Predator(0,0);
		this.prey = new Prey(5,5);
		
		this.predators = new ArrayList<Predator>();
		this.predators.add(pred);
		
		board[prey.getX()][prey.getY()].setActor(prey);
		board[pred.getX()][pred.getY()].setActor(pred);
	}
	
	public void nextRound(){
		// move predator(s)
		for(Predator p : predators) {
			action nextMoveDirection = p.getNextMoveDirection(this.board[p.getX()][p.getY()]);			
			ArrayList<Integer> newCoordinates = getCoordinates(p.getX(), p.getY(), nextMoveDirection);
			
			p.move(newCoordinates);
		}
		collisionDetection();
		// move prey
		ArrayList<Integer> newCoordinates = new ArrayList<Integer>();
		do {
			action nextMoveDirection = prey.getNextMoveDirection(this.board[prey.getX()][prey.getY()]);		
			newCoordinates = getCoordinates(prey.getX(), prey.getY(), nextMoveDirection);
		} while ( !isPositionAvailable(newCoordinates) );
		prey.move(newCoordinates);

		collisionDetection();
	}
	
	/**
	 * used before the prey moves so that it does not move on predators.
	 */
	private boolean isPositionAvailable(ArrayList<Integer> coords) {
		int x = coords.get(0);
		int y = coords.get(1);
		for (Predator p : predators) {
			if (p.getX() == x && p.getY() == y) { return false; }
		} return true;
	}
	
	private void collisionDetection() {		
		for(Predator p : predators) {
			if (p.getX() == prey.getX() && p.getY() == prey.getY()) { prey.setAlive(false); }
		}
	}
	
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
	
	public boolean isEpisodeOver() { return !this.prey.getAlive(); }
	
	public void printCoordinates() {
		for(Predator p : predators) { System.out.println("\t" + p); }
		System.out.println("\t" + prey);
		System.out.println("end of round");
	}
	
	public void policyEvaluation() {
		double maxMargin = 0.0; // defines the maximum difference observed in the stateValue of all states
		int debugRuns=0;
		// initialize V(s) = 0, for all states.
		initializeStateValues(0.0);
		
		this.oldBoard = board;
		
		// initialize Reward for prey cell.
		this.board[prey.getX()][prey.getY()].setStateReward(PREYREWARD);
		
		do {
			maxMargin = 0.0;
			
			for(int i = 0; i < this.board.length; i++){
				for(int j = 0; j < this.board[i].length; j++){
					State currState = this.board[i][j];
					double Vkplus1 = 0.0;
					double oldStateValue = currState.getStateValue();
					
					for (State st : getNeighbors(i, j)) {
						// the action that would be required to move to state st
						action ac = this.board[i][j].getTransitionAction(st);
						// the probability of taking action  in state  under policy π (0.2 in this case)
						double pi = predators.get(0).getPolicy().getActionProbability(ac);
						
						Vkplus1 += pi * (st.getStateReward() + GAMMA * st.getStateValue());
					}
					this.board[i][j].incrementStateValue(Vkplus1);
					
					//after new state value is set update the value of maxMargin.
					maxMargin = Math.max(Math.abs(Vkplus1-oldStateValue), maxMargin);
				}
			}
			
			debugRuns++;
			// loop end
			this.oldBoard = board;
		} while (maxMargin > THETA);
		
		// output stateValues.
		for(int i = 0; i < this.board.length; i++){
			for(int j = 0; j < this.board[i].length; j++){
				System.out.println(this.board[i][j]);
			}
		}
		System.out.println("Runs: " + debugRuns);
	}
	
	private void printBoardDebug() {
		DecimalFormat twoDForm = new DecimalFormat("#.###");
		for(int ii = 0; ii < this.board.length; ii++){
			for(int jj = 0; jj < this.board[ii].length; jj++){				
				System.out.print(twoDForm.format(this.board[ii][jj].getStateValue()) + "\t");
//				System.out.print(this.board[ii][jj].getStateValue() + "\t");
			}
			System.out.println();
		}
	}
	
	private ArrayList<State> getNeighbors(int x, int y) {
		ArrayList<State> neighbours = new ArrayList<State>();
		int neighx, neighy;
		
		// NORTH
		neighy = y;
		if (x-1 < 0) { neighx = dim - 1; }
		else { neighx = x-1; }
		neighbours.add(this.board[neighx][neighy]);
		
		// SOUTH		
		neighy = y;
		if (x+1 > dim-1) { neighx = 0; }
		else { neighx = x+1; }
		neighbours.add(this.board[neighx][neighy]);
		
		// EAST
		neighx = x;
		if (y+1 > dim-1) { neighy = 0; }
		else { neighy = y+1; }
		neighbours.add(this.board[neighx][neighy]);
		
		// WEST
		neighx = x;
		if (y-1 < 0) { neighy = dim - 1; }
		else { neighy = y-1; }
		neighbours.add(this.board[neighx][neighy]);
		
		return neighbours;
	}
	
	private void initializeStateValues(double d) {
		for(int i = 0; i < this.board.length; i++){
			for(int j = 0; j < this.board[i].length; j++){
				this.board[i][j].setStateValue(d);
				this.board[i][j].setStateReward(d);
			}
		}
	}
}
