package statespace;

import action.PreyAction;
import environment.Coordinates;
import environment.Environment;
import environment.Environment.action;
import environment.ProbableTransitions;
import environment.Util;
import policy.Policy;
import state.CompleteState;
import state.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompleteStateSpace extends StateSpace{
	private CompleteState[/*preyX*/][/*preyY*/][/*predX*/][/*predY*/] states;
	private int iter_pos = 0;
	
	public CompleteStateSpace() {
		this.states = new CompleteState[Util.DIM][Util.DIM][Util.DIM][Util.DIM];
		this.initStates(); 
	}

    @Override
	protected void initStates() {
		for(int i = 0; i < Util.DIM; i++) {
			for(int j = 0; j < Util.DIM; j++) {
				for(int k = 0; k < Util.DIM; k++) {
					for(int l = 0; l < Util.DIM; l++) {
						states[i][j][k][l] = new CompleteState(i, j, k, l);
					}
				}
			}
		}
	}

    @Override
	public CompleteState getState(Coordinates preyC, Coordinates predC) {
        return this.states[preyC.getX()][preyC.getY()][predC.getX()][predC.getY()];
    }

    @Override
    public action getTransitionAction(State s, State s_prime) {
        CompleteState cs = (CompleteState) s;
        CompleteState cs_prime = (CompleteState) s_prime;
        Coordinates c = cs.getPredatorCoordinates();
        Coordinates c_prime = cs_prime.getPredatorCoordinates();
        return c.getTransitionAction(c_prime);
    }

	/**
	 * Returns all the possible adjacent states to state s.
	 * @param s
	 * @return
	 */
	public ArrayList<State> getNeighbors(State s) {
		ArrayList<State> ret = new ArrayList<State>();
		for (action a : action.values()) {
			ret.add(this.getNextState(s, a));
		}
		return ret;
	}

    @Override
	public void initializeStateValues(double d) {
		for (State s : this) { s.setStateValue(0.0); }
	}
	
	public void printActions(Policy policy) {
		//TODO
	}

    @Override
    public State getNextState(State s, action a) {
        CompleteState rs = (CompleteState) s;
        Coordinates predNew = rs.getPredatorCoordinates();
        predNew = predNew.getShifted(a);
        return this.getState(s.getPreyCoordinates(), predNew);
    }

    @Override
    public ProbableTransitions getProbableTransitions(State s, action a) {
        CompleteState cs = (CompleteState) s;
        ProbableTransitions ret = new ProbableTransitions();
        // the first two index are for the predator
        Coordinates predatorNewPos = cs.getPredatorCoordinates().getShifted(a);
        // the last two indexes are for the prey
        Coordinates preyCurrPos = s.getPreyCoordinates();

        // where could the prey be going?
        for(Environment.action act : Environment.action.values()) {
            PreyAction tmp = new PreyAction();
            double p = tmp.getActionProbability(act);
            Coordinates preyPossiblePos = preyCurrPos.getShifted(act);
            ret.add(this.getState(predatorNewPos, preyPossiblePos), p);
        }
        return ret;
    }

    /******************************* Iterator Related ************************************/
	@Override
	public Iterator iterator() {
		this.resetIterator();
		return this;
	}

	private void resetIterator() { this.iter_pos = 0; }
	
	@Override
	public boolean hasNext() { return this.iter_pos < (Math.pow(Util.DIM, 4)); }

	@Override
	public CompleteState next() {
		if(this.hasNext()){
			int tmp = this.iter_pos;
			int l = tmp % Util.DIM;
			tmp = (int)(tmp / Util.DIM);
			int k = tmp % Util.DIM;
			tmp = (int)(tmp / Util.DIM);
			int j = tmp % Util.DIM;
			tmp = (int)(tmp / Util.DIM);
			int i = tmp % Util.DIM;
			this.iter_pos++;
			return this.states[i][j][k][l];
		}
		throw new NoSuchElementException();
	}

	@Override
	public void remove() { }
	/******************************* Iterator Related ************************************/
	
	public void print2dSliceGivenPredator(Coordinates predC) { // untested, please FIXME!!!
		int k = predC.getX();
		int l = predC.getY();
		for(int i = 0; i < Util.DIM; i++) {
			for(int j = 0; j < Util.DIM; j++) {
				State currState = this.states[i][j][k][l];
				System.out.println(currState.getStateValue()+" ");// please format me! FIXME
			}
			System.out.println(); // just new line
		}
	}

	public void print2dSliceGivenPrey(Coordinates preyC) { // untested, please FIXME!!!
		int i = preyC.getX();
		int j = preyC.getY();
		for(int k = 0; k < Util.DIM; k++) {
			for(int l = 0; l < Util.DIM; l++) {
				State currState = this.states[i][j][k][l];
				System.out.println(currState.getStateValue()+" "); // please format me! FIXME
			}
			System.out.println(); // just new line
		}
	}

}
