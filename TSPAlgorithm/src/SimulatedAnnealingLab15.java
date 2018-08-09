import java.util.List;

public class SimulatedAnnealingLab15 {
	final static String fileName = "CS2004 TSP Data (2016-2017)/TSP_48.txt"; 
	final static String sep = " "; 
	
	public static void main(String[]Args,  int iter){
	
		//needs changing 
		double startTemp = 1000;
		double constant = 0.81;	
		//NEEDS TO BE BETWEEN 0.8-0.99

		double coolRate = 	(Math.sqrt(Math.pow(startTemp, constant)/startTemp));
				//startTemp/(1+(constant*startTemp));
		//double coolRate = Math.pow((.01/startTemp),1/iter);
				//startTemp/(1+(constant*startTemp));
				
		System.out.println(" "+coolRate);
		//double coolRate = 0.9;

		a(startTemp, iter, constant);
	}
	
	public static SimulatedAnnealing a (double temp, int iter, double coolingRate){
		//coolingRate = 0.8944;
		double[][] cityDistance = TSP.ReadArrayFile(fileName, sep);	
		List<Integer> optimalTour = TSP.ReadIntegerFile("CS2004 TSP Data (2016-2017)/TSP_48_OPT.txt");
		// Create an object of RandomMutationHC with the length to generate
		SimulatedAnnealing sol = new SimulatedAnnealing(cityDistance.length);
		double bestFitnessEff = 0;
		// optimal route fitness
		double optimalFitness = sol.fitness(optimalTour.size(), optimalTour, cityDistance);
		double optimalFitnessEff = 0;
		// MST fitness which will be the lowerbound
		double MSTFitness = sol.getMSTFitness(cityDistance);
		double MSTFitnessEff = 0;
		double bestFitness = 0;
	
		
		for(int i=0;i<iter-1;i++){
			SimulatedAnnealing oldsol = new SimulatedAnnealing(sol.getSol());
			//TSP fitness
			double oldTSPFitness = oldsol.fitness(oldsol.tour.size(), oldsol.tour, cityDistance);	
			double oldFitness = oldsol.getTotalFitness(MSTFitness, oldTSPFitness);
			// System.out.println("Old Fitness: "+oldTSPFitness+ "%");
			sol.smallChange(sol.tour);
			double newTSPFitness = sol.fitness(sol.tour.size(), sol.tour, cityDistance);	
			double newFitness = sol.getTotalFitness(MSTFitness, newTSPFitness);
			//System.out.println("New Fitness: "+newTSPFitness+ "%");

			
			if(newTSPFitness>oldTSPFitness){
				//Acceptance rate gets worse as temperature decreases
				double p = sol.PR(newFitness, oldFitness, temp);
				if(p<CS2004.UR(0, 1)){
					//reject change
					//System.out.println("REJC ");
					sol = new SimulatedAnnealing(oldsol.getSol());
					optimalFitnessEff = oldsol.getTotalFitness(optimalFitness, oldTSPFitness);
					MSTFitnessEff = oldsol.getTotalFitness(MSTFitness, oldTSPFitness);
					bestFitness = oldTSPFitness;
					bestFitnessEff = oldFitness;
				}
				else{
					//accepting worse change
					sol = new SimulatedAnnealing(sol.getSol());
					optimalFitnessEff = sol.getTotalFitness(optimalFitness, newTSPFitness);
					MSTFitnessEff = sol.getTotalFitness(MSTFitness, newTSPFitness);
					bestFitness = newTSPFitness;
					bestFitnessEff = newFitness;

				}
			
				
			}
			else{
				sol = new SimulatedAnnealing(sol.getSol());
				optimalFitnessEff = sol.getTotalFitness(optimalFitness, newTSPFitness);
				MSTFitnessEff = sol.getTotalFitness(MSTFitness, newTSPFitness);
				bestFitness = newTSPFitness;
				bestFitnessEff = newFitness;
			}
			//Check this
			temp *=coolingRate;
			
			//System.out.print("cool \n "+coolingRate);
			//System.out.print("Temp \n"+temp);
		}
		System.out.println("Optimal Fitness efficiency:" + optimalFitnessEff);
		System.out.println("MST fitness efficiency: " + MSTFitnessEff);

		System.out.println(MSTFitness+ "  " + optimalFitness);
		return sol;
	}
}
