package environment;

import policy.Policy;
import actor.Predator;
import actor.Prey;

public class Environment {	
	private StateSpace stateSpace;
	private Prey prey;
	private Predator predator;
	
	private final double THETA = 0.00000001; // threshold for the loop end condition
	private final double GAMMA = 0.8;
	
	public enum action { NORTH, SOUTH, EAST, WEST, WAIT };
	
	public Environment() {
		this.stateSpace = new StateSpace();
		
		this.predator = new Predator(new Coordinates(0,0), stateSpace);
		this.prey = new Prey(new Coordinates(5,5), stateSpace);

//		state.setPrey(this.prey.getCoordinates());
//		state.setPredator(this.predator.getCoordinates());
	}
	

	public boolean isEpisodeOver() { return !this.prey.getAlive(); }
	
	/**
	 * moved to State.
	 */
//	private void collisionDetection() {		
//		for(Predator p : predators) {
//			if (p.getCoordinates().equals(prey.getCoordinates())) { prey.setAlive(false); }
//		}
//	}
	
//	public void nextRound() {
//		for(Predator p : predators) { // move predator(s)
//			Cell pState = this.state.getState(p.getCoordinates());
//			action nextMoveDirection = p.getNextMoveDirection(pState);			
//			Coordinates newCoordinates = this.state.nearbyCoordinates(p.getCoordinates(), nextMoveDirection);
//
//			this.state.moveActor(p, newCoordinates);
//		} collisionDetection();
//		
//		// move prey.
//		Coordinates newCoordinates = null;
//		do { // find appropriate coordinates (not on predators).
//			Cell preyState = this.state.getState(prey.getCoordinates());
//			action nextMoveDirection = prey.getNextMoveDirection(preyState);		
//			newCoordinates = this.state.nearbyCoordinates(prey.getCoordinates(), nextMoveDirection);
//		} while ( !isPositionAvailable(newCoordinates) );
//		
//		this.state.moveActor(prey, newCoordinates);
//		collisionDetection();
//	}
	
//	/**
//	 * used before the prey moves so that it does not move on predators.
//	 */
//	private boolean isPositionAvailable(Coordinates c) {
//		for (Predator p : predators) {
//			if (p.getCoordinates().equals(c)) { return false; }
//		} return true;
//	}
//	
	
//	/**
//	 * used for debugging
//	 */
//	public void printCoordinates() {
//		for(Predator p : predators) { System.out.println("\t" + p); }
//		System.out.println("\t" + prey);
//		System.out.println("end of round");
//	}
	
	/**
	 * Task 1.2
	 * For extensive explanation see : http://webdocs.cs.ualberta.ca/~sutton/book/ebook/node41.html
	 * @param initialize0; flag that indicates whether or not to initialize all the state Values to 0.0 or not.
	 */
	public void policyEvaluation() {
		double delta = 0.0; // defines the maximum difference observed in the stateValue of all states
		int debugRuns = 0;
		
		Policy policy = this.predator.getPolicy();
		policy.initializeStateValues(0.0);
		
		do {
			delta = 0.0;
			for (State currState : this.stateSpace) { // for s in S+
				double Vkplus1 = 0.0;
				double v = currState.getStateValue();
				if (currState.getStateValue() != 0.0) { 
					System.out.println("wow " + currState.getStateValue());
				}

				for (action ac : Environment.action.values()) {
					double pi = policy.getActionProbability(currState, ac);
//					Coordinates nearby = this.state.nearbyCoordinates(pos, ac);
					State st = currState.getNextState(ac);
					Vkplus1 += pi * (st.getStateReward() + GAMMA * st.getStateValue());
				}
				currState.setStateValue(Vkplus1);
				
				delta = Math.max(Math.abs(Vkplus1 - v), delta);
			}
			debugRuns++;
		} while (delta > THETA);
		System.out.println(this.stateSpace.getState(10, 10, 10, 10));
//							State currState = this.stateSpace.getState(i, j, k, l);
//							
//							Coordinates preyC = new Coordinates(i, j);
//							Coordinates predC = new Coordinates(k, l);
//							
//							currState.setPredator(predC);
//							currState.setPrey(preyC);
//							
//							double Vkplus1 = 0.0; // the new value V.
//							double oldStateValue = currState.getStateValue();
//							
//							for (action ac : Environment.action.values()) {
//								// ac: the action that would be required to move to state st
//								
//								// the probability of taking action  in state  under policy π (0.2 in this case)
//								double pi = policy.getActionProbability(currState, ac);
////								Coordinates nearby = this.state.nearbyCoordinates(pos, ac);
//								State st = currState.getNextState(ac);
//								Vkplus1 += pi * (st.getStateReward() + GAMMA * st.getStateValue());
//							}
//							currState.setStateValue(Vkplus1);
//							
//							// after new state value is set update the value of delta.
//							delta = Math.max(Math.abs(Vkplus1-oldStateValue), delta);
//		
//			debugRuns++;
//		} while (delta > THETA);
		
		// output stateValues.
		
		System.out.println("Runs: " + debugRuns);
	}
//	public void policyEvaluation(boolean initialize0) {
//		double delta = 0.0; // defines the maximum difference observed in the stateValue of all states
//		int debugRuns = 0;
//		
//		if(initialize0) {
//			this.grid.initializeStateValues(0.0); // initialize R(s) = V(s) = 0, for all states.
//		}
//		
//		// initialize Reward for prey cell.
//		this.grid.getState(prey).setStateReward(grid.PREYREWARD);
//		
//		do {
//			delta = 0.0;
//			
//			for(int i = 0; i < this.grid.getDim(); i++) {
//				for(int j = 0; j < this.grid.getDim(); j++) {
//					Coordinates pos = new Coordinates(i,j);
//					Cell currState = grid.getState(pos);
//					double Vkplus1 = 0.0; // the new value V.
//					double oldStateValue = currState.getStateValue();
//					
//					for (action ac : Environment.action.values()) {
//						// ac: the action that would be required to move to state st
//						
//						// the probability of taking action  in state  under policy π (0.2 in this case)
//						double pi = predators.get(0).getPolicy().getActionProbability(currState, ac);
//						Coordinates nearby = this.grid.nearbyCoordinates(pos, ac);
//						Cell st = grid.getState(nearby);
//						Vkplus1 += pi * (st.getStateReward() + GAMMA * st.getStateValue());
//					}
//					currState.setStateValue(Vkplus1);
//					
//					// after new state value is set update the value of delta.
//					delta = Math.max(Math.abs(Vkplus1-oldStateValue), delta);
//				}
//			}
//			debugRuns++;
//		} while (delta > THETA);
//		
//		// output stateValues.
//		this.grid.printStateValues();
//		
//		System.out.println("Runs: " + debugRuns);
//	}
//
//// START COMMENTS
//	/**
//	 * For details see : http://webdocs.cs.ualberta.ca/~sutton/book/ebook/node41.html
//	 */	
//	public void policyEvaluationForImprovement() {
//		double delta = 0.0; // defines the maximum difference observed in the stateValue of all states
//				
//		do {
//			delta = 0.0;
//			
//			for(int i = 0; i < this.state.getDim(); i++) {
//				for(int j = 0; j < this.state.getDim(); j++) {
//					Coordinates pos = new Coordinates(i,j);
//					Cell currState = state.getState(pos);
//					action pi_s = predators.get(0).getPolicy().getAction(currState);
//					double oldStateValue = currState.getStateValue();
//					double Vkplus1 = 0.0;
//					for (Cell neighbor : state.getNeighbors(pos)) {
//						Cell s_prime = neighbor;
//						Coordinates pi_s_nextpos = state.nearbyCoordinates(currState.getCoordinates(), pi_s);
//						
//						// P^(pi(s))_ss' has only two possible values: 1 if the action will lead to s', 0 otherwise
//						double p = s_prime.equals(state.getState(pi_s_nextpos)) ? 1.0 : 0.0;
//						// ac: the action that would be required to move to state st
//						Vkplus1 += p * (currState.getStateReward() + GAMMA * s_prime.getStateValue());
//					}					
//					currState.setStateValue(Vkplus1);
//					
//					// after new state value is set update the value of delta.
//					delta = Math.max(Math.abs(Vkplus1 - oldStateValue), delta);
//				}
//			}
//		} while (delta > THETA);
//
//		this.state.printStateValues();
//	}
//
//	/**
//	 * Assign to policy, the action a which returns the maximum return for any given state s.
//	 * For details see : http://webdocs.cs.ualberta.ca/~sutton/book/ebook/node43.html 
//	 */
//	public boolean policyImprovementFrancesco() {
//		boolean policyStable = true;
//
//		for(int i = 0; i < this.state.getDim(); i++) {
//			for(int j = 0; j < this.state.getDim(); j++) {
//				Coordinates currPos = new Coordinates(i,j);
//				Cell currState = state.getState(currPos);
//				action b = predators.get(0).getPolicy().getAction(currState);
//				
//				double max = 0.0;
//				action argmax_a = null;
//				for(action a : Environment.action.values()) {
//					double sum = 0.0;
//					for (Cell neighbor : state.getNeighbors(currPos)) {
//						Cell s_prime = neighbor;
//						double p;
//						if( this.state.getTransitionAction(currState, s_prime) == a ) {
//							p = 1.0;
//						} else { p = 0.0; }
//						// P^(pi(s))_ss' has only two possible values: 1 if the action will lead to s', 0 otherwise
//						// ac: the action that would be required to move to state st
//						double r = state.getActionReward(currState, a);
//						sum += p * (r + GAMMA * s_prime.getStateValue());
//					}
//					if(sum > max) {
//						argmax_a = a;
//						max = sum;
//					}
//				}
//				predators.get(0).getPolicy().setUniqueAction(currState, argmax_a);
//				
//				if(argmax_a != b) { policyStable = false; }
//			}
//		} return policyStable;
//	}
//	
//	public int valueIteration(double local_gamma) {		
//		double delta, v, max;
//		this.state.initializeStateValues(0.0);
//		this.state.getState(prey).setStateReward(state.PREYREWARD);
//		int numIterations = 0;
//		do {
//			numIterations++;
//			delta = 0.0;
//			for(int i = 0; i < this.state.getDim(); i++) {
//				for(int j = 0; j < this.state.getDim(); j++) {
//					Coordinates currPos = new Coordinates(i,j);
//					Cell currState = state.getState(currPos);
//					v = currState.getStateValue();
//					max = 0.0;
//					for(action a : Environment.action.values()) { // max
//						double sum = 0.0;
//						for (Cell neighbor : state.getNeighbors(currPos)) { // sum
//							Cell s_prime = neighbor;
//							double p;
//							if( this.state.getTransitionAction(currState, s_prime) == a ) {
//								p = 1.0;
//							} else { p = 0.0; }
//							double r = state.getActionReward(currState, a);
//							sum += p * (r + local_gamma * s_prime.getStateValue());
//						}
//						if(sum > max) {	max = sum; }
//					}
//					currState.setStateValue(max);
//
//					delta = Math.max(delta, Math.abs(currState.getStateValue() - v));
//
//				}
//			}
//			if(numIterations > 100000) { return 100000; }
//		} while(delta > this.THETA);
//		// output State values.
//		this.state.printStateValues();
//		return numIterations;
//	}
//	
//	
//	/**
//	 * increases gamma with step 0.001. Outputs to results.csv. Used for plotting.
//	 */
//	public void valueIterationGammas() {
//		double gamma = 0.0;
//		int size = 1000;
//		int[] iterations = new int[size];
//		BufferedWriter br;
//		try {
//			br = new BufferedWriter(new FileWriter("results.csv"));
//			for(int i = 0; i < size-1; i++) {
//				gamma += 0.001;
//				System.out.println("gamma:"+gamma);
//				iterations[i] = this.valueIteration(gamma);
//				System.out.println("num iterations:"+iterations[i]);
//				String str = i+","+gamma+","+iterations[i]+"\n";
//				System.out.println(str);
//				br.write(str);
//			} br.close();
//			
//		} catch (IOException e) { e.printStackTrace(); }
//	}
//
//	public void policyIteration() {
//		this.state.initializeStateValues(0.0); // initialize R(s) = V(s) = 0, for all states.
//		
//		// initialize Reward for prey cell.
//		this.state.getState(prey).setStateReward(state.PREYREWARD);
//		int debugRuns = 0;
//		
//		do {
//			this.policyEvaluation(true);
//			
//			debugRuns++;
//			this.state.printActions(predators.get(0).getPolicy());
//			System.out.println("Runs: " + debugRuns);
//		} while(! this.policyImprovementFrancesco());
//	}
}
