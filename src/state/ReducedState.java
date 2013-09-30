package state;

import environment.Coordinates;
import environment.Algorithms;
import environment.Algorithms.action;

public class ReducedState extends State {
    public ReducedState() {}

    public ReducedState(Coordinates prey) {
        this.preyC = prey;
        this.predC = new Coordinates(0, 0);
    }

    public Algorithms.action getTransitionAction(ReducedState rs_prime) {
    	Coordinates other_pos = rs_prime.getPreyCoordinates();
    	return this.getPreyCoordinates().getOppositeTransitionAction(other_pos);
    }
}
