import java.util.List;

public class StochasticLab15 {
	final static String fileName = "CS2004 TSP Data (2016-2017)/TSP_48.txt";
	final static String sep = " ";

	public static void main(String[] Args, int iter) {
	
		a(iter);
	}

	public static StochasticHC a(int iter) {
		// Reading matrix which is distance between city
		double[][] cityDistance = TSP.ReadArrayFile(fileName, sep);
		List<Integer> optimalTour = TSP.ReadIntegerFile("CS2004 TSP Data (2016-2017)/TSP_48_OPT.txt");
		StochasticHC sol = new StochasticHC(cityDistance.length);
		double bestFitnessEff = 0;
		double optimalFitness = sol.fitness(optimalTour.size(), optimalTour, cityDistance);
		double optimalFitnessEff = 0;
		// MST fitness which will be the lowerbound
		double MSTFitness = sol.getMSTFitness(cityDistance);
		double MSTFitnessEff = 0;
		double bestFitness = 0;

		for (int i = 0; i < iter; i++) {
			 //System.out.println("Iteration number: " + i);
			StochasticHC oldsol = new StochasticHC(sol.getSol());
			// TSP fitness
			double oldTSPFitness = oldsol.fitness(oldsol.tour.size(), oldsol.tour, cityDistance);
			double oldFitness = oldsol.getTotalFitness(MSTFitness, oldTSPFitness);
			 //System.out.println("Old Fitness: "+oldTSPFitness+ "%");

			// Changing the tour to generate a different fitness
			sol.smallChange(sol.tour);

			double newTSPFitness = sol.fitness(sol.tour.size(), sol.tour, cityDistance);
			double newFitness = sol.getTotalFitness(MSTFitness, newTSPFitness);
			optimalFitnessEff = sol.getTotalFitness(optimalFitness, newTSPFitness);

			 //System.out.println("New Fitness: "+newTSPFitness+ "%");

			if (newTSPFitness > oldTSPFitness) {
				boolean prob = SHCProb(newTSPFitness, oldTSPFitness);
				//System.out.println(prob);
				if (prob == true) {
					 System.out.println("ACEPTING WORSE SOLUTION");
					sol = new StochasticHC(sol.getSol());
					optimalFitnessEff = sol.getTotalFitness(optimalFitness, newTSPFitness);
					MSTFitnessEff = sol.getTotalFitness(MSTFitness, newTSPFitness);

				} else {
					sol = new StochasticHC(oldsol.getSol());
					optimalFitnessEff = oldsol.getTotalFitness(optimalFitness, oldTSPFitness);
					MSTFitnessEff = oldsol.getTotalFitness(MSTFitness, oldTSPFitness);

				}
				
			} else {
				sol = new StochasticHC(sol.getSol());
				optimalFitnessEff = sol.getTotalFitness(optimalFitness, newTSPFitness);
				MSTFitnessEff = sol.getTotalFitness(MSTFitness, newTSPFitness);
				bestFitness = newTSPFitness;
				bestFitnessEff = newFitness;
				

			}
		}
		System.out.println("Optimal Fitness efficiency:" + optimalFitnessEff);
		System.out.println("MST fitness efficiency: " + MSTFitnessEff);
		return sol;

	}

	public static boolean SHCProb(double newFitness, double oldFitness) {
		double T = 3;
		// Probability on accepting worse fitness to escape local optimum
		// Aiming for around 75%, T needs to be readjusted
		double probAccept =  1/(1 + Math.exp((newFitness - oldFitness) /T));
		System.out.println(probAccept);
		double randomProb = CS2004.UR(0, 1);

		if (probAccept > randomProb) {
			return true;
		}
		return false;

	}
}
