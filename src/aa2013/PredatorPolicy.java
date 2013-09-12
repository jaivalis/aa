package aa2013;

import java.util.HashMap;

public class PredatorPolicy extends Policy {
	
	public PredatorPolicy(Grid g) {
		this.stateActionMapping = new HashMap<State, PossibleActions>();
		
		for (int i = 0; i < g.getDim(); i++) {
			for (int j = 0; j < g.getDim(); j++) {
				Coordinates pos = new Coordinates(i,j);
				this.stateActionMapping.put(g.getState(pos), new PredatorAction());
			}
		}
	}
	
	/**
	 * policy improvement as seen in http://webdocs.cs.ualberta.ca/~sutton/book/ebook/node43.html
	 */
	public void updateProbabilities() {
		
	}
}
