package aa2013;


public class GLoop {

	private static final int DIM = 11;
	
	public static void main(String[] args) {
		int runs = 1, time = 0;
		Environment env = new Environment(DIM);
		
		env.policyEvaluation();
		
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
