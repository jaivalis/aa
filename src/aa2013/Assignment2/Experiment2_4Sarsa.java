package aa2013.Assignment2;

import environment.Algorithms;
import environment.Q;
import environment.Util;
import policy.QEpsilonGreedyPolicy;
import statespace.ReducedStateSpace;
import statespace.StateSpace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


public class Experiment2_4Sarsa {
    /**
     * Experiment on Sarsa:
     *      + different α learning rates.
     *      + different γ discount factors.
     *  - initialQValue is optimistically set to 15.0
     * Outputs a .csv format file.
     */
    public static void main(String[] args) {
        StateSpace ss = new ReducedStateSpace();
        Algorithms algos = new Algorithms(ss);

        double optimisticInitialQ = 15;
        double simulations = 100;  // many simulations ensure higher precision.
        QEpsilonGreedyPolicy egp = new QEpsilonGreedyPolicy(algos.getStateSpace()); // Predator learn

        long timestamp = (new Date()).getTime();
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("experiment2_4Sarsa_results_"+timestamp+".csv", true)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        out.println("gamma,alpha,episodeCount,averageRounds");
        for (double gamma = 0.1; gamma <= 1.0; gamma += 0.2) {
            for (double alpha = 0.1; alpha <= 0.6; alpha += 0.1) {
                for(int episodeCount = 50; episodeCount < Util.EPISODE_COUNT; episodeCount += 50) {
                    // 1. train
                    Q newQ = algos.sarsa(egp, optimisticInitialQ, alpha, gamma, episodeCount);
                    ((QEpsilonGreedyPolicy) algos.getPredator().getPolicy()).setQ(newQ);

                    // 2. simulate & output results
                    double averageRounds = algos.getSimulationAverageRounds(simulations);
                    String str = gamma+","+alpha+","+episodeCount+","+averageRounds;
                    System.out.println(str);
                    out.println(str);
                    out.flush();
                }
            }
        }
    }
}
