package environment;

import actor.Predator;
import actor.Prey;
import state.State;
import statespace.StateSpace;

import java.util.ArrayList;

public class Episode {

    private State currentState;
    private int roundNumber;
    private ArrayList<Predator> predators;
    private Prey prey;
    private StateSpace stateSpace;

    public Episode(StateSpace stateSpace, ArrayList<Predator> predators, Prey prey, boolean randomInitialState) {
        this.stateSpace = stateSpace;
        this.roundNumber = 0;
        this.predators = predators;
        this.prey = prey;
        if (randomInitialState) {
            this.currentState = this.stateSpace.getRandomState();
        }
    }

    public void nextStep() {
        for (Predator pr : this.predators) {
            pr.move(currentState);

            // update currentState.
            this.currentState = this.stateSpace.getState(this.prey.getCoordinates(), pr.getCoordinates());
            this.collisionDetection();
        }
        this.prey.move(currentState);
        this.collisionDetection();

        this.currentState =
                this.stateSpace.getState(this.prey.getCoordinates(), this.predators.get(0).getCoordinates());
    }

    private void collisionDetection() {
        for (Predator p : this.predators) {
            if (p.getCoordinates() == this.prey.getCoordinates()) {
                this.prey.setAlive(false);
            }
        }
    }

    public boolean hasNextTurn() { return this.prey.getAlive(); }

//    private State getRandomState() { return this.stateSpace.getRandomState(); }
}
