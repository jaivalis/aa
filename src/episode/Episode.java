package episode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import environment.Algorithms.action;
import state.State;

public class Episode implements Iterable<EpisodeStep> {
	private ArrayList<EpisodeStep> steps = new ArrayList<EpisodeStep>();
	
	public void addStep(State s, action a, double r, State s_prime){
		this.steps.add(new EpisodeStep(s, a, r, s_prime));
	}

	@Override
	public Iterator<EpisodeStep> iterator() {
		return this.steps.iterator();
	}
	
	public void refreshDiscounted(double gamma) {
		ArrayList<EpisodeStep> reverted = (ArrayList<EpisodeStep>) this.steps.clone();
		Collections.reverse(reverted);
		double prev_discounted = 0.0;
		for(EpisodeStep step : reverted) {
			double curr_discounted = step.getR() + gamma * prev_discounted;
			step.setDiscounted(curr_discounted);
			prev_discounted = curr_discounted;
		}
		
	}

}
