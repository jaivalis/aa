package state;

import environment.Coordinates;
import environment.Environment;

public class ReducedState extends State {
    public ReducedState() {}

    public ReducedState(Coordinates prey) {
        this.preyC = prey;
        this.predC = new Coordinates(0, 0);
    }

    public Environment.action getTransitionAction(ReducedState rs_prime) {
        // TODO important
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
