package aa2013.Assignment1;

import environment.Algorithms;
import statespace.CompleteStateSpace;
import statespace.ReducedStateSpace;

public class MainA1 {
	public static void main(String[] args) {
		int time = 0;
		Algorithms env = new Algorithms(new ReducedStateSpace());
		
		/* Task 1 */
		env.simulate(10, "Task1Simulate");
		long startTime = System.currentTimeMillis();
		/* Task 2 */
//		env.policyEvaluation();
		/* Task 3 */
//		env.policyIteration();
		/* Task 4 */
		env.valueIterationGammas();
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + (endTime - startTime) + "ms");
		
		System.out.println("total time = " + time);
//		System.out.println("average time = " + time / 100);
	}
}