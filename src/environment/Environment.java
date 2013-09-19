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
	}
	
	public void simulate(int episodeCount) {
		int episodes = episodeCount;
		do {
			// initialize Episode
			this.predator.setCoordinates(new Coordinates(0, 0));
			this.prey.setCoordinates(new Coordinates(5, 5));
			this.prey.setAlive(true);
			State initialState = this.stateSpace.getState(this.prey.getCoordinates(), this.predator.getCoordinates());
			
			int rounds = 0;
			while (!isEpisodeOver()) { // run episode
				initialState = this.nextRound(initialState);
				rounds++;
			}
			//REPORT
			System.out.println("[simulate()] rounds: " + rounds);
			episodes--;
		} while (episodes > 0);
	}

	public boolean isEpisodeOver() { return !this.prey.getAlive(); }
	
	public State nextRound(State s) {
		State currentState = s;
		this.predator.move(currentState);
		
		// update currentState.
		currentState = this.stateSpace.getState(this.prey.getCoordinates(), this.predator.getCoordinates()); 
		this.collisionDetection();
		
		this.prey.move(currentState);
		this.collisionDetection();
		return this.stateSpace.getState(this.prey.getCoordinates(), this.predator.getCoordinates());
	}
	
	private void collisionDetection() {
		if (predator.getCoordinates().equals(prey.getCoordinates())) { prey.setAlive(false); }
	}

	/**
	 * Task 1.2
	 * For extensive explanation see : http://webdocs.cs.ualberta.ca/~sutton/book/ebook/node41.html
	 * @param policy; The policy to evaluate.
	 */
	public void policyEvaluation(/*Policy policy, */) {
		double delta = 0.0; // defines the maximum difference observed in the stateValue of all states
		int sweeps = 0;
		
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
			sweeps++;
		} while (delta > THETA);

		// REPORT
		Coordinates preyCoordinates = new Coordinates(0,0);
		Coordinates predatorCoordinates = new Coordinates(0,0);
		
		System.out.println(this.stateSpace.getState(preyCoordinates, predatorCoordinates));
		System.out.println("[policyEvaluation()] Sweeps: " + sweeps);
		// /REPORT
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
	
	public int valueIteration(double local_gamma) {		
		double delta;
		this.stateSpace.initializeStateValues(0.0);
		int numIterations = 0;
		do {
			numIterations++;
			delta = 0.0;
			
			// for each s in S
			Iterator<State> stateSpaceIt = this.stateSpace.iterator();
			while(stateSpaceIt.hasNext()) {
				State s = stateSpaceIt.next();
				double v = s.getStateValue();
				double max = 0.0;
				for (action a: Environment.action.values()) { // max over a
					ProbableTransitions probableTransitions = stateSpace.getProbableTransitions(s, a);
					Set<State> neighbours = probableTransitions.getStates();
					double sum = 0.0;
					for(State s_prime : neighbours){ // summation over s'
						double p = probableTransitions.getProbability(s_prime);
						sum += p * (s_prime.getStateReward() + GAMMA * s_prime.getStateValue());
					}
					max = Math.max(max, sum);
				}
				double V_s = max;

				s.setStateValue(V_s);
				delta = Math.max(delta, Math.abs(s.getStateValue() - v));
			}
			if(numIterations > 100000) { return 100000; }
		} while(delta > this.THETA);
		// output State values FIXME TODO
		return numIterations;
	}
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

	public void policyIteration(/*Policy policy*/) {
		int debugIterations = 0;
		Policy policy = this.predator.getPolicy();
		this.stateSpace.initializeStateValues(0.0);
		
		do {
			this.policyEvaluation();			
			this.stateSpace.printActions(policy);
			debugIterations++;
		} while (!this.policyImprovement());
		System.out.println("[policyIteration()] Number of Iterations : " + debugIterations);	
	}
}
