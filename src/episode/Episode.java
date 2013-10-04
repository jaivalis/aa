package episode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import policy.MCEpsilonGreedyPolicy;
import environment.Algorithms.action;
import state.State;

public class Episode implements Iterable<EpisodeStep> {
	private ArrayList<EpisodeStep> steps = new ArrayList<EpisodeStep>();
	
	public void addStep(State s, action a, double r, State s_prime){
		this.addStep(new EpisodeStep(s, a, r, s_prime));
	}

	public void addStep(EpisodeStep step){
		this.steps.add(step);
	}

	@Override
	public Iterator<EpisodeStep> iterator() {
		return this.steps.iterator();
	}

	private ArrayList<EpisodeStep> getReverted(){
		ArrayList<EpisodeStep> reverted = (ArrayList<EpisodeStep>) this.steps.clone();
		Collections.reverse(reverted);
		return reverted;
	}
	
	public void refreshDiscounted(double gamma) {
		double prev_discounted = 0.0;
		ArrayList<EpisodeStep> reverted = this.getReverted();
		for(EpisodeStep step : reverted) {
			double curr_discounted = step.getR() + gamma * prev_discounted;
			step.setDiscounted(curr_discounted);
			prev_discounted = curr_discounted;
		}
	}

	public Episode getTauized(MCEpsilonGreedyPolicy pi) {
		Episode tauized = new Episode();
		ArrayList<EpisodeStep> reverted = this.getReverted();
		ArrayList<EpisodeStep> tail = new ArrayList<EpisodeStep>();
		for(EpisodeStep step : reverted) {
			State s = step.getS();
			action a = step.getA();
			if(!pi.getAction(s).equals(a)){
				break;
			}
		}
		Collections.reverse(tail);
		for(EpisodeStep step : tail){
			tauized.addStep(step);
		}
		return tauized;
	}

}
