package aa2013;

import java.util.Random;

import aa2013.Board.action;

public class RandomPolicy implements Policy {
	
	@Override
	public action getAction(State s) {	
		Random r = new Random();
		float randint = r.nextFloat();

		if (randint < 0.2) { return action.NORTH; }
		if (randint < 0.4) { return action.SOUTH; }
		if (randint < 0.6) { return action.EAST; }
		if (randint < 0.8) { return action.WEST; }
		return action.WAIT;
	}

	@Override
	public double getActionProbability(action a) {
		switch (a) {
			case NORTH:	case SOUTH:	case EAST: case WEST: case WAIT: return 0.2;
			default:	return 0.0;
		}
	}
}
