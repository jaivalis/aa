package episode;

import environment.Algorithms.action;
import state.State;

public class EpisodeStep {

	private State s;
	private action a;
	private double r;
	private State s_prime;

	public EpisodeStep(State s, action a, double r, State s_prime) {
		this.s = s;
		this.a = a;
		this.r = r;
		this.s_prime = s_prime;
	}
	
	public State getS() {
		return s;
	}
	public action getA() {
		return a;
	}
	public double getR() {
		return r;
	}
	public State getS_prime() {
		return s_prime;
	}
	
}
