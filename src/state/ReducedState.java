package state;

import environment.Coordinates;
import environment.Environment;
import environment.Environment.action;

public class ReducedState extends State {
    public ReducedState() {}

    public ReducedState(Coordinates prey) {
        this.preyC = prey;
        this.predC = new Coordinates(0, 0);
    }

    public Environment.action getTransitionAction(ReducedState rs_prime) {
    	Coordinates other_pos = rs_prime.getPreyCoordinates();
    	return this.getPreyCoordinates().getOppositeTransitionAction(other_pos);
    }
}
