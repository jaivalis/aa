package aa2013;

import java.util.ArrayList;

public class Board {
	
	private State[][] board; // XXX : delete
	private final int dim;
	
	private ArrayList<Predator> predators;
	private Prey prey;
	
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
		ArrayList<Integer> coordinates = new ArrayList();		
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
		// at the beginning set all the V's at 0
		for(int i = 0; i < this.board.length; i++){
			for(int j = 0; j < this.board[i].length; j++){
				this.board[i][j].setStateValue(0.0f);
			}
		}
	}
}
