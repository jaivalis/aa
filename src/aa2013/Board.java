package aa2013;

import java.util.ArrayList;

public class Board {
	
	private State[][] board;

	private State[][] previousBoard; // used to determine if there are changes
	private final int dim;
	
	private ArrayList<Predator> predators;
	private Prey prey;

	private final double PREYVALUE = 0.0f;
	private final double EPSILON = 0.0f; // defines the margin required to
	private final double GAMMA = 0.8; // defines the margin required to
		
	public enum direction { NORTH, SOUTH, EAST, WEST, WAIT };
	
	public Board(int dim) {
		this.dim = dim;
		this.board = new State[dim][dim];
		
		Predator pred = new Predator(0,0);
		this.prey = new Prey(5,5);
		
		this.predators = new ArrayList();
		this.predators.add(pred);
		
		board[prey.getX()][prey.getY()].setActor(prey);
		board[pred.getX()][pred.getY()].setActor(pred);
	}
	
	public void nextRound(){
		// move predator(s)
		for(Predator p : predators) {
			direction nextMoveDirection = p.getNextMoveDirection();			
			ArrayList<Integer> newCoordinates = getCoordinates(p.getX(), p.getY(), nextMoveDirection);
			
			p.move(newCoordinates);
		}
		collisionDetection();
		// move prey
		ArrayList<Integer> newCoordinates = new ArrayList();
		do {
			direction nextMoveDirection = prey.getNextMoveDirection();		
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
	
	public ArrayList<Integer> getCoordinates(int x, int y, direction dir) {
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
		}	

		return coordinates;
	}
	
	public boolean isEpisodeOver() { return !this.prey.getAlive(); }
	
	public void printCoordinates() {
		for(Predator p : predators) { System.out.println("\t" + p); }
		System.out.println("\t" + prey);
		System.out.println("end of round");
	}
	
	// XXX : delete
	public void printBoard() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (board[i][j] == null) {
					System.out.print("-");
				} else {
					System.out.print(board[i][j]);
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	public void policyEvaluation() {
		int timeSlot = 1;
		// at the beginning set all the V's at 0
		initializeStateValues(0.0f);
		
		this.board[prey.getX()][prey.getY()].setStateValue(PREYVALUE);
		
		while (!policyEvaluationTerminationCondition()) {
			ArrayList<State> neighbours;
			for(int i = 0; i < this.board.length; i++){
				for(int j = 0; j < this.board[i].length; j++){
					neighbours = getNeighbours(i, j);
					
					for (State st : neighbours) {
						
					}
				}
			}
			
			
			// loop end
			timeSlot++;
			this.previousBoard = this.board;
		}
		
	}
	
	/**
	 * termination condition for the policy evaluation loop.
	 */
	private boolean policyEvaluationTerminationCondition() {
		for(int i = 0; i < this.board.length; i++){
			for(int j = 0; j < this.board[i].length; j++){
				if (this.board[i][j].getStateValue() - this.previousBoard[i][j].getStateValue() > EPSILON) {
					return false;
				}
			}
		}
		return false;
	}
	
	private ArrayList<State> getNeighbours(int x, int y) {
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
			}
		}
	}
}
