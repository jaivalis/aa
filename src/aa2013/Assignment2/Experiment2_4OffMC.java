package aa2013.Assignment2;

import environment.Algorithms;
import environment.Util;
import policy.EpsilonGreedyPolicy;
import statespace.ReducedStateSpace;
import statespace.StateSpace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Experiment2_4OffMC {
    public static void main(String[] args) {
        StateSpace ss = new ReducedStateSpace();
        Algorithms algos = new Algorithms(ss);
        
        double optimisticInitialQ = 15;
        double simulations = 100;  // many simulations ensure higher precision.
        EpsilonGreedyPolicy egp = new EpsilonGreedyPolicy(algos.getStateSpace()); // Predator learn

        double savedEpsilon = Util.epsilon;
        long timestamp = (new Date()).getTime();
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("experiment2_4OffMC_results_"+timestamp+".csv", true)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        out.println("gamma,alpha,averageRounds");
        for (double gamma = 0.1; gamma <= 0.9; gamma += 0.2) {
        	for (double alpha = 0.1; alpha <= 0.5; alpha += 0.1) {
                for(int episodeCount = 50; episodeCount < Util.EPISODE_COUNT; episodeCount += 50) {
                    // 1. train
                    Util.epsilon = savedEpsilon; // we need a stochastic epsilon policy for the learning, for exploration
                    //EpsilonGreedyPolicy mco = algos.monteCarloOffPolicy(egp, optimisticInitialQ, alpha, gamma, episodeCount);
                    Util.epsilon = 0.0; // now it has already learned, so we can use a stochastic policy
                    //algos.getPredator().setPolicy(mco);

                    // 2. simulate & output results
                    double averageRounds = algos.getSimulationAverageRounds(simulations);
                    String str = gamma + "," + alpha + "," + averageRounds;
                    System.out.println(str);
                    out.println(str);
                    out.flush();
                }
            }
        }
    }
}
