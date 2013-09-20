package environment.environment.state;

import actor.Actor;
import actor.Predator;
import actor.Prey;
import environment.Coordinates;

public class CompleteState extends State {

    public CompleteState() {}

	/**
	 * Constructor method.
	 * @param i; prey x coordinate.
	 * @param j; prey y coordinate.
	 * @param k; predator x coordinate.
	 * @param l; predator y coordinate.
	 */
	public CompleteState(int i, int j, int k, int l) {
		this.preyC = new Coordinates(i, j);
		this.predC = new Coordinates(k, l);
	}

    public CompleteState(Coordinates preyC, Coordinates predC) {
        this.preyC = preyC;
        this.predC = predC;
    }
	
	public CompleteState(Prey p, Predator pp) {
		this.preyC = p.getCoordinates();
		this.predC = pp.getCoordinates();
	}

//	/**
//	 * returns the new State that occurs after predator takes action a.
//	 */
//	public State getNextState(action a) {
//		State nextState = new State();
//		nextState.setPrey(this.preyC);
//		nextState.setPredator(this.predC.getShifted(a));
//		return nextState;
//	}

    public void setPredator(Coordinates c) {
		this.predC = c;
	}

    public void setActor(Actor a) {
        if (a instanceof Prey) {
            this.preyC = a.getCoordinates();
//			this.prey = (Prey) a;
        } else if (a instanceof Predator) {
            this.predC = a.getCoordinates();
//			this.predator = (Predator) a;
        }
    }

    public Coordinates getPredatorCoordinates() { return this.predC; }
}
