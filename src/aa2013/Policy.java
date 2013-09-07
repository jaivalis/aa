package aa2013;

import aa2013.Board.action;

public interface Policy {
	public action getAction(State s);
}
