package policy;

import statespace.StateSpace;
import environment.Q;

public class QPolicy extends PredatorPolicy {
	protected Q q = null;

	public QPolicy(StateSpace ss) {
		super(ss);
		// TODO Auto-generated constructor stub
	}
    public void setQ(Q q) {
    	this.q = q;
    }

}
