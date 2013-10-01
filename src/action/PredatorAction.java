package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import environment.Algorithms;
import environment.Algorithms.action;

public class PredatorAction extends PossibleActions {
	
	/**
	 * Constructor initializes all predator action probabilities to 0.2.
	 */
	public PredatorAction() { 	
		actionProbability = new HashMap<Algorithms.action, Double>(); 
		actionProbability.put(action.NORTH, 0.2);
		actionProbability.put(action.SOUTH, 0.2);
		actionProbability.put(action.EAST, 0.2);
		actionProbability.put(action.WEST, 0.2);
		actionProbability.put(action.WAIT, 0.2);
	}
}