package aa2013;

import java.util.ArrayList;

public class Environment {	
	private Grid grid;
	private Prey prey;
	private ArrayList<Predator> predators;

	
	private final double THETA = 0.00001; // threshold for the loop end condition
	private final double GAMMA = 0.8;
	
		
	public enum action { NORTH, SOUTH, EAST, WEST, WAIT };
	
	public Environment(int dim) {
		this.grid = new Grid(dim);
		
		Predator pred = new Predator(this.grid, 0, 0);
		this.prey = new Prey(this.grid, 5, 5);
		
		this.predators = new ArrayList<Predator>();
		this.predators.add(pred);

		this.grid.setActor(prey, prey.getX(), prey.getY());
		this.grid.setActor(pred, pred.getX(), pred.getY());
	}
	

	public boolean isEpisodeOver() { return !this.prey.getAlive(); }
	
	public void collisionDetection() {		
		for(Predator p : predators) {
			if (p.getX() == prey.getX() && p.getY() == prey.getY()) { prey.setAlive(false); }
		}
	}
	
	public void nextRound() {
		for(Predator p : predators) { // move predator(s)
			State pState = this.grid.getState(p.getX(), p.getY());
			action nextMoveDirection = p.getNextMoveDirection(pState);			
			ArrayList<Integer> newCoordinates = this.grid.getCoordinates(p.getX(), p.getY(), nextMoveDirection);
			
//			p.move(newCoordinates);
			this.grid.moveActor(p, newCoordinates.get(0), newCoordinates.get(1));
		} collisionDetection();
		
		// move prey.
		ArrayList<Integer> newCoordinates = new ArrayList<Integer>();
		do { // find appropriate coordinates (not on predators).
			State preyState = this.grid.getState(prey.getX(), prey.getY());
			action nextMoveDirection = prey.getNextMoveDirection(preyState);		
			newCoordinates = this.grid.getCoordinates(prey.getX(), prey.getY(), nextMoveDirection);
		} while ( !isPositionAvailable(newCoordinates) );
		
		this.grid.moveActor(prey, newCoordinates.get(0), newCoordinates.get(1));

		collisionDetection();
	}
	
	/**
	 * used before the prey moves so that it does not move on predators.
	 */
	private boolean isPositionAvailable(ArrayList<Integer> coords) {
		int x = coords.get(0), y = coords.get(1);
		for (Predator p : predators) {
			if (p.getX() == x && p.getY() == y) { return false; }
		} return true;
	}
	
	/**
	 * used for debugging
	 */
	public void printCoordinates() {
		for(Predator p : predators) { System.out.println("\t" + p); }
		System.out.println("\t" + prey);
		System.out.println("end of round");
	}
	
	/**
	 * For extensive explanation see : http://webdocs.cs.ualberta.ca/~sutton/book/ebook/node41.html
	 */	
	public void policyEvaluation() {
		double delta = 0.0; // defines the maximum difference observed in the stateValue of all states
		int debugRuns = 0;
		
		this.grid.initializeStateValues(0.0); // initialize R(s) = V(s) = 0, for all states.
		
		// initialize Reward for prey cell.
		this.grid.getState(prey).setStateReward(grid.PREYREWARD);
		
		do {
			delta = 0.0;
			
			for(int i = 0; i < this.grid.getDim(); i++) {
				for(int j = 0; j < this.grid.getDim(); j++) {
					State currState = grid.getState(i, j);
					double Vkplus1 = 0.0; // the new value V.
					double oldStateValue = currState.getStateValue();
					
					for (State st : this.grid.getNeighbors(i, j)) {
						// the action that would be required to move to state st
						action ac = currState.getTransitionAction(st);
						// the probability of taking action  in state  under policy π (0.2 in this case)
						double pi = predators.get(0).getPolicy().getActionProbability(currState, ac);
						
						Vkplus1 += pi * (st.getStateReward() + GAMMA * st.getStateValue());
					}
					currState.setStateValue(Vkplus1);
					
					// after new state value is set update the value of delta.
					delta = Math.max(Math.abs(Vkplus1-oldStateValue), delta);
				}
			}			
			this.grid.printStateValues();
			debugRuns++;
		} while (delta > THETA);
		
		// output stateValues.
		this.grid.printStateValues();
		
		System.out.println("Runs: " + debugRuns);
	}
}
