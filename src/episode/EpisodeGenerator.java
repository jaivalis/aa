package episode;

import environment.Algorithms.action;
import policy.Policy;
import state.State;
import statespace.StateSpace;

public class EpisodeGenerator {

    /**
     * Creates an instance of episode using a given policy for the Predator.
     * @param stateSpace
     * @param initialState
     * @param pi
     * @param gamma
     * @return
     */
	public static Episode generate(StateSpace stateSpace, State initialState, Policy pi, double gamma) {
    	Episode episode = new Episode();

        State s = initialState;
        State s_prime = initialState;

        int steps = 0;
        do {
        	steps++;
            action a =  pi.getAction(s);
            s = s_prime;
            s_prime = stateSpace.produceStochasticTransition(s, a);
            double r = s_prime.getStateReward();
            episode.addStep(s, a, r, s_prime);
        } while(!s_prime.isTerminal()&&steps<1000000);

        episode.refreshDiscounted(gamma);
		return episode;
	}
}
