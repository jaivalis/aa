package aa2013;

import java.util.Random;

import aa2013.Board.action;

public class RandomPreyPolicy implements Policy {

	@Override
	public action getAction(State s) {
		Random r = new Random();
		float randint = r.nextFloat();

		if (randint < 0.8)	{ return action.WAIT; }
		if (randint < 0.85)	{ return action.NORTH; }
		if (randint < 0.9)	{ return action.SOUTH; }
		if (randint < 0.95)	{ return action.EAST; }
		return action.WEST;
	}

}
