package episode;

import java.util.ArrayList;
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

}
