package aa2013;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import aa2013.Environment.action;

public class PredatorAction extends PossibleActions {
	
	/**
	 * Constructor initializes all predator action probabilities to 0.2.
	 */
	public PredatorAction() { 	
		actionProbability = new HashMap<Environment.action, Double>(); 
		actionProbability.put(action.NORTH, 0.2);
		actionProbability.put(action.SOUTH, 0.2);
		actionProbability.put(action.EAST, 0.2);
		actionProbability.put(action.WEST, 0.2);
		actionProbability.put(action.WAIT, 0.2);
	}

	@Override
	public action getAction() {
		if (areEquiprobable()) { return getRandomAction(); }
		
		action maxProbAction = null;
		double maxProb = 0.0;
		for (action ac : this.actionProbability.keySet()) {
			if ( maxProb <= this.actionProbability.get(ac)) {
				maxProbAction = ac;
			}
		}
		return maxProbAction;
	}
	
	private boolean areEquiprobable() {
		double prob = 0.2;
		for (action ac : this.actionProbability.keySet()) {
			if (this.actionProbability.get(ac) != prob) { return false; }
		} return true;
	}
	
	/**
	 * returns a random action from the keys of the HashMap.
	 */
	private action getRandomAction() {
		Random r = new Random();
		int randint = r.nextInt(5);
		ArrayList<action> keys = new ArrayList<action>(actionProbability.keySet());
		return keys.get(randint);
	}
}