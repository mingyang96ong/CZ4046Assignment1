package PolicyMethods;
import Resources.GridWorld;
import Resources.TransitionModel;

public class ValueIteration {
	
	public static final double discountFactor = 0.99;
	public static TransitionModel TM = TransitionModel.getTransitionModel();
	public static void main(String args[]) {
		
		// String arr[row][col] represent a NxN square grid for the GridWorld
		// "W" = White Square, "G" = Green Square, "B" = Brown Square, "WALL" = Wall Square, default = White Square
		String [][]arr= {{"G", "WALL", "G", "W", "W","G"},
				 {"W", "B", "W", "G", "W", "B"},
				 {"W", "W", "B", "W", "G", "W"},
				 {"W", "W", "W", "B", "W", "G"},
				 {"W", "WALL", "WALL", "WALL", "B", "W"},
				 {"W", "W", "W", "W", "W", "W"}
		};
		
		
		// Since the GridWorld has a 6x6 square world, so 6 is pass into the instantiation of GridWorld's row and col
		GridWorld GW = new GridWorld(arr, 6, 6);
		
		// This will stop the ValueIteration after we found the difference between the utility is less than epsilon
		double epsilon = 0.001;
		
		// This is the max difference between old and new utility of all states
		double maxChange = 9999;// This is initialised to 9999 for it to enter the while loop
		
		
		// Print the grid world that is generated to see the GridWorld in a grid format for better view
		GW.printGridWorld();
		
		//initialize all the utility of each states to 0
		GW.utilityInitialisation(0);
		
		// Create an iteration value to compute the number of iteration for executing value iteration
		int iteration = 0;
		
		// Create a GridWorld object to hold the old GridWorld object
		GridWorld oldGW = new GridWorld (arr, 6, 6);
		
		// While the max difference between old and new utility of all state is more than epsilon and iteration is less than 50
		while ((maxChange >= (epsilon*(1-discountFactor)/discountFactor)) && iteration < 50) {
			
			// This is to keep the previous utility values of each state in the GridWorld
			oldGW = GW.copyGW(arr);
			
			// This is to reset the maxChange after every Value Iteration process
			maxChange = 0;
			
			// Iterate through all the state 
			for (int row = 0; row < GW.getRow(); row++) {
				for (int col = 0; col < GW.getCol(); col++) {
					
					// If the grid is a wall, skip it to save computation time
					if (GW.getGridIsWall(row, col)) {
						continue;
					}

					// This will get the updatedUtility computation
					evaluateUtility(GW, oldGW, row, col);
					
					// If the difference between the old utility and current utility is greater than the max difference
					if (Math.abs(oldGW.getGridUtility(row, col) - GW.getGridUtility(row, col)) > maxChange) {
						
						// Set this to be the new largest utility difference as maxChange
						maxChange = Math.abs(oldGW.getGridUtility(row, col) - GW.getGridUtility(row, col));
					}
				}
			}
			
			// Increment the iteration value
			iteration++;
			
			GW.printGridUtility(iteration);
		}
		
		// Print the grid world that is generated to see the GridWorld in a grid format again
		GW.printGridWorld();
		
		// Print the policy in the grid world and you can observe the direction in the grid format
		GW.printGridPolicy();
		
	}
	
	// Unlike from the evaluateUtility function in policyIteration, it will check for all possible action
		public static void evaluateUtility(GridWorld GW, GridWorld oldGW, int row, int col) {
			
			// Initialise a Double object to hold the utility value which would be computed
			double tmputility = 0;
			double currentutility = GW.getGridUtility(row, col);
			boolean changed = false;
			for (String action :  TM.getActions()) {
				
				// This is to get the possible actions i.e if the policy is Up, it will return Left and Right
				// as there is 0.1 chance of going to those directions
				String possibleActions[] = TM.getPossibleActions(action);
				
				// This is to get the state of the agent after executing the policy's action and return row as position[0] and col as position[1]
				int position[] = GW.canMoveToGrid(row, col, action);
				
				// These two positions array represent the two other state after executing the possible Actions
				int position2[] = GW.canMoveToGrid(row, col, possibleActions[0]);
				int position3[] = GW.canMoveToGrid(row, col, possibleActions[1]);
				
				// This compute the utility function of state with row and col with a recursive call
				tmputility = GW.getGridReward(row, col) + 
						discountFactor *(0.8 * oldGW.getGridUtility(position[0],position[1])
								+ 0.1 * oldGW.getGridUtility(position2[0], position2[1])  +
								0.1 * oldGW.getGridUtility(position3[0], position3[1]));
				if (tmputility > currentutility) {
					GW.updateGridPolicy(row, col, action);
					currentutility = tmputility;
					changed = true;
				}
			}
			if (changed) {
				GW.updateGridUtility(row, col, currentutility);
			}
		}
}
