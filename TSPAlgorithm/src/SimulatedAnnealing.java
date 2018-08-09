import java.util.ArrayList;
import java.util.List;

public class SimulatedAnnealing {
	List<Integer> tour = new ArrayList<Integer>();

	public SimulatedAnnealing(int n) {
		RandPerm(n);
	}

	public SimulatedAnnealing(List<Integer> t) {
		tour.addAll(t);
		// System.out.println("dsa "+tour);
	}

	public List<Integer> RandPerm(int n) {
		// int[]P = new int[n];
		List<Integer> P = new ArrayList<Integer>();

		for (int i = 0; i < n; i++) {
			P.add(i);
		}
		List<Integer> T = new ArrayList<Integer>();
		while (P.size() > 0) {
			int i = CS2004.UI(0, P.size() - 1);
			T.add(P.get(i));
			tour.add(P.get(i));

			P.remove(i);
		}
		
		return T;
	}

	public double fitness(int N, List<Integer> Tour, double[][] DistanceMatrix) {
		double s = 0;

		for (int i = 0; i < N - 1; i++) {
			int a = Tour.get(i);
			// System.out.println("A"+a);
			int b = Tour.get(i + 1);
			// System.out.println("B"+b);
			s += DistanceMatrix[a][b];
			// System.out.println(DistanceMatrix[a][b]);
			// System.out.println(s);
		}
		int end_city = Tour.get(N - 1);
		int start_city = Tour.get(0);

		s += DistanceMatrix[end_city][start_city];

		// System.out.println("s"+s);

		return s;
	}

	public void smallChange(List<Integer> T) {
		int j;
		int i = j = 0;
		// System.out.println("Before"+ T);

		while (i == j) {
			i = CS2004.UI(0, T.size() - 1);
			j = CS2004.UI(0, T.size() - 1);
			// System.out.println("I "+i+"J "+j);
		}
		int temp = T.get(i);
		T.set(i, T.get(j));
		T.set(j, temp);
		// System.out.println("Changed"+ T);
	}

	public List<Integer> getSol() {
		return (tour);

	}
	
	public double PR(double newFitness, double oldFitness, double temp) {
		// Need changing THINK IT NEEDS TO BE REVERSED FOR MINIMISATION 
		double p = Math.exp(-(Math.abs(newFitness-oldFitness))/temp);
		
		//System.out.println("PPPPPPP"+p);
		return p;
	}

	public double getMSTFitness(double[][] cityDistance) {
		// MST fitness
		double[][] mst = MST.PrimsMST(cityDistance);
		// From MST function
		double MSTFitness = MST.weightValues();
		return MSTFitness;
	}
	public double getTotalFitness(double MSTFitness, double TSPFitness){
		double fitness = (MSTFitness/TSPFitness) *100;
		
		return fitness;
		}

}
