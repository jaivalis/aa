package action;

import java.util.HashMap;
import java.util.Random;

import environment.Environment;
import environment.Environment.action;

/**
 * A little confusing at the moment because the prey does not use the 
 * actionProbability HashMap. But might be used in the future when prey
 * too will be an agent.
 */
public class PreyAction extends PossibleActions {

	public PreyAction() { 	
		actionProbability = new HashMap<Environment.action, Double>(); 
		actionProbability.put(action.NORTH, 0.05);
		actionProbability.put(action.SOUTH, 0.05);
		actionProbability.put(action.EAST, 0.05);
		actionProbability.put(action.WEST, 0.05);
		actionProbability.put(action.WAIT, 0.80);
	}
}
