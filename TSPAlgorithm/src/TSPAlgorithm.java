
import java.util.List;

/*Never accepts a worse solution thus can get stuck in a local optimum easily */
public class TSPAlgorithm {
	final static String fileName = "CS2004 TSP Data (2016-2017)/TSP_48.txt";
	final static String sep = " ";
	static // 1 - RMHC,2 - RRHC, 3 - SHC, 4 - SA
	int algorithm = 1;
	
	public static void main(String[] Args) {
		
		// run for 100,000 iterations or more
		int iter = 1000;
		switch (algorithm) {
		case 1:
			System.out.println("Running Random Mutation Hill Climber Algorithm...");
			a(iter);
			break;
		case 2:
			System.out.println("Running Random Restart Hill Climber Algorithm...");
			a(100);
			break;
		case 3:
			System.out.println("Running Stochastic Hill Climber Algorithm...");
			StochasticLab15.main(null, iter);
			break;
		case 4:
			System.out.println("Running Simulated Annealing Algorithm...");
			SimulatedAnnealingLab15.main(null, iter);
			break;
		}

	}

	public static RandomMutationHC a(int iter) {
		// Reading matrix which is distance between city
		double[][] cityDistance = TSP.ReadArrayFile(fileName, sep);
		// optimal route
		List<Integer> optimalTour = TSP.ReadIntegerFile("CS2004 TSP Data (2016-2017)/TSP_48_OPT.txt");
		// Create an object of RandomMutationHC with the length to generate
		RandomMutationHC sol = new RandomMutationHC(cityDistance.length);
		double bestFitnessEff = 0;
		// optimal route fitness
		double optimalFitness = sol.fitness(optimalTour.size(), optimalTour, cityDistance);
		double optimalFitnessEff = 0;
		// MST fitness which will be the lowerbound
		double MSTFitness = sol.getMSTFitness(cityDistance);
		double MSTFitnessEff = 0;
		double bestFitness = 0;

		for (int i = 0; i < iter; i++) {
			// System.out.println("Iteration number: " + i);
			RandomMutationHC oldsol = new RandomMutationHC(sol.getSol());
			// TSP fitness
			double oldTSPFitness = oldsol.fitness(oldsol.tour.size(), oldsol.tour, cityDistance);
			double oldFitness = oldsol.getTotalFitness(MSTFitness, oldTSPFitness);
			// System.out.println("Old Fitness: "+oldFitness+ "%");

			// Changing the tour to generate a different fitness
			sol.smallChange(sol.tour);

			double newTSPFitness = sol.fitness(sol.tour.size(), sol.tour, cityDistance);
			double newFitness;
			if (algorithm == 2) {
				newFitness = sol.runRRHC(100, MSTFitness, cityDistance);
			} else {
				newFitness = sol.getTotalFitness(MSTFitness, newTSPFitness);
			}

			// System.out.println("New Fitness: "+newFitness+ "%");
			if (newTSPFitness > oldTSPFitness) {
				sol = new RandomMutationHC(oldsol.getSol());
				
					optimalFitnessEff = oldsol.getTotalFitness(optimalFitness, oldTSPFitness);
					MSTFitnessEff = oldsol.getTotalFitness(MSTFitness, oldTSPFitness);
					bestFitness = oldTSPFitness;
					bestFitnessEff = oldFitness;
				
			} else {

				sol = new RandomMutationHC(sol.getSol());
				
					optimalFitnessEff = sol.getTotalFitness(optimalFitness, newTSPFitness);
					MSTFitnessEff = sol.getTotalFitness(MSTFitness, newTSPFitness);
					bestFitness = newTSPFitness;

					bestFitnessEff = newFitness;
			}
			

		}
		System.out.println(MSTFitness);
		System.out.println("Optimal Fitness efficiency:" + optimalFitnessEff);
		System.out.println("MST fitness efficiency: " + MSTFitnessEff);

		System.out.println(MSTFitness+ "  " + optimalFitness);
		return sol;

	}

}
