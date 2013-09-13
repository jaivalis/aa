package aa2013;

import java.util.ArrayList;
import java.util.Iterator;

public class Environment {	
	private Grid grid;
	private Prey prey;
	private ArrayList<Predator> predators;

	
	private final double THETA = 0.00001; // threshold for the loop end condition
	private final double GAMMA = 0.8;
	
		
	public enum action { NORTH, SOUTH, EAST, WEST, WAIT };
	
	public Environment(int dim) {
		this.grid = new Grid(dim);
		
		Predator pred = new Predator(this.grid, new Coordinates(0,0));
		this.prey = new Prey(this.grid, new Coordinates(5,5));
		
		this.predators = new ArrayList<Predator>();
		this.predators.add(pred);

		this.grid.setActor(prey, prey.getCoordinates());
		this.grid.setActor(pred, pred.getCoordinates());
	}
	

	public boolean isEpisodeOver() { return !this.prey.getAlive(); }
	
	public void collisionDetection() {		
		for(Predator p : predators) {
			if (p.getCoordinates().sameAs(prey.getCoordinates())) { prey.setAlive(false); }
		}
	}
	
	public void nextRound() {
		for(Predator p : predators) { // move predator(s)
			State pState = this.grid.getState(p.getCoordinates());
			action nextMoveDirection = p.getNextMoveDirection(pState);			
			Coordinates newCoordinates = this.grid.nearbyCoordinates(p.getCoordinates(), nextMoveDirection);
			
//			p.move(newCoordinates);
			this.grid.moveActor(p, newCoordinates);
		} collisionDetection();
		
		// move prey.
		Coordinates newCoordinates = null;
		do { // find appropriate coordinates (not on predators).
			State preyState = this.grid.getState(prey.getCoordinates());
			action nextMoveDirection = prey.getNextMoveDirection(preyState);		
			newCoordinates = this.grid.nearbyCoordinates(prey.getCoordinates(), nextMoveDirection);
		} while ( !isPositionAvailable(newCoordinates) );
		
		this.grid.moveActor(prey, newCoordinates);

		collisionDetection();
	}
	
	/**
	 * used before the prey moves so that it does not move on predators.
	 */
	private boolean isPositionAvailable(Coordinates c) {
		for (Predator p : predators) {
			if (p.getCoordinates().sameAs(c)) { return false; }
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
					Coordinates pos = new Coordinates(i,j);
					State currState = grid.getState(pos);
					double Vkplus1 = 0.0; // the new value V.
					double oldStateValue = currState.getStateValue();
					
					for (action ac : Environment.action.values()) {
						// ac: the action that would be required to move to state st
						
						// the probability of taking action  in state  under policy π (0.2 in this case)
						double pi = predators.get(0).getPolicy().getActionProbability(currState, ac);
						Coordinates nearby = this.grid.nearbyCoordinates(pos, ac);
						State st = grid.getState(nearby);
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

	/**
	 * For extensive explanation see : http://webdocs.cs.ualberta.ca/~sutton/book/ebook/node41.html
	 */	
	public void policyEvaluationForImprovement() {
		double delta = 0.0; // defines the maximum difference observed in the stateValue of all states
				
		do {
			delta = 0.0;
			
			for(int i = 0; i < this.grid.getDim(); i++) {
				for(int j = 0; j < this.grid.getDim(); j++) {
					Coordinates pos = new Coordinates(i,j);
					State s = grid.getState(pos);
					action pi_s = predators.get(0).getPolicy().getAction(s);
					double v = s.getStateValue();
					ArrayList<State> neighbours = grid.getNeighbors(pos);
					Iterator<State> it = neighbours.iterator();
					double v_s = 0.0;
					while(it.hasNext()) {
						State s_prime = it.next();
						Coordinates pi_s_nextpos = grid.nearbyCoordinates(s.getCoordinates(), pi_s);
						
						// P^(pi(s))_ss' has only two possible values: 1 if the action will lead to s', 0 otherwise
						double p = s_prime.sameAs(grid.getState(pi_s_nextpos)) ? 1.0 : 0.0;
						// ac: the action that would be required to move to state st
						v_s += p * (s.getStateReward() + GAMMA * s_prime.getStateValue());
					}
					
					s.setStateValue(v_s);
					
					// after new state value is set update the value of delta.
					delta = Math.max(Math.abs(v_s - v), delta);
				}
			}
		} while (delta > THETA);
	}

	public boolean policyImprovement()
	{
		boolean policyStable = true;

		for(int i = 0; i < this.grid.getDim(); i++) {
			for(int j = 0; j < this.grid.getDim(); j++) {
				Coordinates pos = new Coordinates(i,j);
				State s = grid.getState(pos);
				action b = predators.get(0).getPolicy().getAction(s);
				action pi_s = null;
				
				double max = 0.0;
				action argmax_a = null;
				for(action a : Environment.action.values()) {
					ArrayList<State> neighbours = grid.getNeighbors(pos);
					Iterator<State> it = neighbours.iterator();
					double sum = 0.0;
					while(it.hasNext()) {
						State s_prime = it.next();
						Coordinates pi_s_nextpos = grid.nearbyCoordinates(s.getCoordinates(), pi_s);
						
						// P^(pi(s))_ss' has only two possible values: 1 if the action will lead to s', 0 otherwise
						double p = s_prime.sameAs(grid.getState(pi_s_nextpos)) ? 1.0 : 0.0;
						// ac: the action that would be required to move to state st
						sum += p * (s.getStateReward() + GAMMA * s_prime.getStateValue());
					}
					if(sum > max){
						argmax_a = a;
						max = sum;
					}
				}
				predators.get(0).getPolicy().setTheOnlyAction(s, argmax_a);
				if(argmax_a != b){
					policyStable = false;
				}
			}
		}
		return policyStable;
	}
	
	public void policyIteration() {
		this.grid.initializeStateValues(0.0); // initialize R(s) = V(s) = 0, for all states.
		
		// initialize Reward for prey cell.
		this.grid.getState(prey).setStateReward(grid.PREYREWARD);
		int debugRuns = 0;

		do {
			this.policyEvaluationForImprovement();
			debugRuns++;
			this.grid.printActions(predators.get(0).getPolicy());
			System.out.println("Runs: " + debugRuns);

		} while(! this.policyImprovement());
	}
}