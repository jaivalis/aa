package aa2013;

import environment.Environment;


public class GLoop {
	private static final int DIM = 11;
	
	public static void main(String[] args) {
		int runs = 1, time = 0;
		Environment env = new Environment();
		
		/* Task 2 */
		env.policyEvaluation(true);
		/* Task 3 */
//		env.policyIteration();
		/* Task 4 */
//		env.valueIterationGammas();
		
//		while (runs > 0) {
//			env.nextRound();
//			env.printCoordinates();
//			
//			time++;
//			if (env.isEpisodeOver()) {
//				env = new Environment(DIM);
//				runs--;
////				time = 0;
//			}
//		}
		
		System.out.println("total time = " + time);
//		System.out.println("average time = " + time / 100);
	}

}
