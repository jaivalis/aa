package environment;

import java.util.Iterator;
import java.util.Set;

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
	 * @param policy; The policy to evaluate.
	 */
	public void policyEvaluation(/*Policy policy, */) {
		double delta = 0.0; // defines the maximum difference observed in the stateValue of all states
		int debugRuns = 0;
		
		Policy policy = this.predator.getPolicy();
		
		do { // Repeat
			delta = 0.0;
			Iterator<State> stateSpaceIt = this.stateSpace.iterator();
			while(stateSpaceIt.hasNext()) {
				State currState = stateSpace.next(); // for s in S+
				double V_s = 0.0;
				double v = currState.getStateValue();

				for (action ac : Environment.action.values()) { // summation over a
					double pi = policy.getActionProbability(currState, ac);
					ProbableTransitions probableTransitions = stateSpace.getProbableTransitions(currState, ac);
					Set<State> neighbours = probableTransitions.getStates();
					double sum = 0.0;
					for(State s_prime : neighbours){ // summation over s'
						double p = probableTransitions.getProbability(s_prime);
						sum += p * (s_prime.getStateReward() + GAMMA * s_prime.getStateValue());
					}
					V_s += pi * sum;
				}
				currState.setStateValue(V_s);				
				delta = Math.max(Math.abs(V_s - v), delta);
			}
			debugRuns++;
		} while (delta > THETA);
		System.out.println(this.stateSpace.getState(10, 10, 10, 10));
		System.out.println("Runs: " + debugRuns);
	}

	public boolean policyImprovement(/*Policy policy*/) {
		boolean policyStable = true;
		Policy policy = this.predator.getPolicy();
		for (State s : this.stateSpace) {
			action b = policy.getAction(s);
			
			double max = 0.0;
			action argmax_a = null;
			for (action a : Environment.action.values()) {
				double sum = 0.0;
				for (State s_prime : this.stateSpace.getNeighbors(s)) {
					double p;
					if (this.stateSpace.getTransitionAction(s, s_prime) == a) { p = 1.0; }
					else { p = 0.0; }
					// P^(pi(s))_ss' has only two possible values: 1 if the action will lead to s', 0 otherwise
//					// ac: the action that would be required to move to state st
					double r = stateSpace.getActionReward(s, a);
					sum += p * (r + GAMMA * s_prime.getStateValue());
				}
				if(sum > max) {
					argmax_a = a;
					max = sum;
				}
			}
			policy.setUniqueAction(s, argmax_a);			
			if(argmax_a != b) { policyStable = false; }
		}
		return policyStable;
	}
	
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
	public void policyIteration(/*Policy policy*/) {
		Policy policy = this.predator.getPolicy();
		this.stateSpace.initializeStateValues(0.0);
		
		do {
			this.policyEvaluation();
			
			this.stateSpace.printActions(policy);
			
		} while (!this.policyImprovement());
	}
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
