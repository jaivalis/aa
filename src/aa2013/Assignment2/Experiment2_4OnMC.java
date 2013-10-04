package aa2013.Assignment2;

import environment.Algorithms;
import environment.Util;
import policy.MCEpsilonGreedyPolicy;
import policy.QEpsilonGreedyPolicy;
import statespace.ReducedStateSpace;
import statespace.StateSpace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Experiment2_4OnMC {
    public static void main(String[] args) {
    	Experiment2_4OnMC.debug();
    	//Experiment2_4OnMC.averages();
    }
    
    public static void debug() {
        StateSpace ss = new ReducedStateSpace();
        Algorithms algos = new Algorithms(ss);
        MCEpsilonGreedyPolicy pi = new MCEpsilonGreedyPolicy(algos.getStateSpace()); // Predator learn
        int initialQValue = 15;
        int episodeCount = 100;
        pi = algos.monteCarloOnPolicy(pi, initialQValue, episodeCount);
        pi.printMaxActionsGrid();
    }
    
    public static void averages() {
        StateSpace ss = new ReducedStateSpace();
        Algorithms algos = new Algorithms(ss);

        double simulations = 100;  // many simulations ensure higher precision.
        MCEpsilonGreedyPolicy egp = new MCEpsilonGreedyPolicy(algos.getStateSpace()); // Predator learn

        double savedEpsilon = Util.epsilon;
        long timestamp = (new Date()).getTime();
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("experiment2_4OnMC_results_"+timestamp+".csv", true)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        out.println("gamma,episodeCount,averageRounds");
        for (float gamma = 0; gamma <= 0.9; gamma += 0.1) {
            for (float initialQValue = 30; initialQValue >= -15; initialQValue -= 5) {
                for(int episodeCount = 0; episodeCount < Util.EPISODE_COUNT; episodeCount++) {
                    // 1. train
                    Util.epsilon = savedEpsilon; // we need a stochastic epsilon policy for the learning, for exploration
                    MCEpsilonGreedyPolicy mco = algos.monteCarloOnPolicy(egp, initialQValue, episodeCount);
                    Util.epsilon = 0.0; // now it has already learned, so we can use a stochastic policy
                    algos.getPredator().setPolicy(mco);

                    // 2. simulate & output results
                    double averageRounds = algos.getSimulationAverageRounds(simulations);
                    String str = gamma + "," + episodeCount + "," + averageRounds;
                    System.out.println(str);
                    out.println(str);
                    out.flush();
                }
            }
        }
    }
}
