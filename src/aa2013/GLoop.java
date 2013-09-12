package aa2013;


public class GLoop {

	private static final int DIM = 11;
	
	public static void main(String[] args) {
		int runs = 1, time = 0;
		Board b = new Board(DIM);
		
//		b.policyEvaluation();
		
		while (runs > 0) {
			b.nextRound();
			b.printCoordinates();
			
			time++;
			if (b.isEpisodeOver()) {
				b = new Board(DIM);
				runs--;
//				time = 0;
			}
		}
		
//		System.out.println("total time = " + time);
//		System.out.println("average time = " + time / 100);
	}

}
