package environment;

import policy.LearnedPolicy;

public class ValueIterationResult {

	private int numIterations;
	private LearnedPolicy pi;

	public ValueIterationResult(int numIterations, LearnedPolicy pi) {
		this.numIterations = numIterations;
		this.pi = pi;
	}

	public int getNumIterations() {
		return numIterations;
	}

	public LearnedPolicy getPi() {
		return pi;
	}
	
	public String toString() {
		return "<iterations:"+this.numIterations+",pi:"+this.pi+">";
	}
}
