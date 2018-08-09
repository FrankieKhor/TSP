import java.util.ArrayList;
import java.util.List;

public class RandomMutationHC {
	List<Integer> tour = new ArrayList<Integer>();
	List<Integer> bestTour = new ArrayList<Integer>(tour);
	int fc = 0;
	public RandomMutationHC(int n){
		RandPerm(n);
	}

	public RandomMutationHC(List<Integer>t){
		tour.addAll(t);
		
		//System.out.println("dsa "+tour);
	}

	//Representation is a list of permutation 
	public List<Integer> RandPerm(int n){
		//int[]P = new int[n];
		List<Integer> P = new ArrayList<Integer>();
		for(int i =0;i<n;i++){
			P.add(i);
		}
		List<Integer> T = new ArrayList<Integer>();
		while(P.size()>0){
			int i = CS2004.UI(0, P.size()-1);	
			//adds each int to a new list to get a random permutation
			T.add(P.get(i));
			tour.add(P.get(i));
			P.remove(i);
			
		}
			
		return T;
	}

	public double fitness(int N, List<Integer> Tour,double[][]DistanceMatrix){
		double s = 0;
		for(int i=0;i<N-1;i++){
			int a = Tour.get(i);
			//System.out.println("A"+a);
			int b = Tour.get(i+1);
			//System.out.println("B"+b);
			s += DistanceMatrix[a][b];
			//System.out.println(DistanceMatrix[a][b]);
			//System.out.println(s);
		}
		int end_city = Tour.get(N-1);
		int start_city = Tour.get(0);
		s += DistanceMatrix[end_city][start_city];
		
		return s;
	}
	public double getTotalFitness(double MSTFitness, double TSPFitness){
		double fitness = (MSTFitness/TSPFitness) *100;
		fc++;
		return fitness;
		}

	public List<Integer> smallChange(List<Integer> T ){
		int j;
		int i= j=0;
		//System.out.println("Before"+ T);

		while(i==j){
			i = CS2004.UI(0, T.size()-1);
			j = CS2004.UI(0, T.size()-1);
			//System.out.println("I "+i+"J "+j);
		}
		int temp = T.get(i);
		T.set(i, T.get(j));
		T.set(j, temp);
		//System.out.println("Changed"+ T);
		return T;
	}

	public List<Integer> getSol(){
		return (tour);

	}
	public double getMSTFitness(double [][]cityDistance){
		//MST fitness
		double [][]mst = MST.PrimsMST(cityDistance);
		//From MST function
		double MSTFitness =MST.weightValues();
		return MSTFitness;
	}
	public double runRRHC(int nfc,double MSTFitness, double [][]a){
		double bestFitness = 0;

		//System.out.println("bewTSP"+tspFitness);
		int count =0;
		double temp =0;

		//System.out.println("hay"+tour);
		while(getFC()<nfc){
			smallChange(tour);
			double tspFitness = fitness(tour.size(), tour, a);
			//System.out.println("TSP"+tspFitness);
			//System.out.println("Changed"+tour);
			double fitness =getTotalFitness(MSTFitness,tspFitness);
			//System.out.println("FITNESS "+fitness + " BestFitness"+bestFitness);
			if (count ==0){
				bestFitness =fitness;
				bestTour();	
				temp = fitness ;

			}
			else if(fitness<bestFitness){	

			
				bestFitness = fitness; 
				if(temp >fitness){
				temp = fitness;
				bestTour();
				}

			}
			

			count++;
		}

		tour = bestTour;
		//System.out.println("BT"+bestTour);
		//System.out.println("Best fitness"+bestFitness);
		return bestFitness; 
	}
	public int getFC(){
		return fc;
	}
	
	
public List<Integer>bestTour(){
		bestTour = new ArrayList<Integer>(tour);
		//System.out.println("bestTour"+bestTour);
		return bestTour;
	}
}
