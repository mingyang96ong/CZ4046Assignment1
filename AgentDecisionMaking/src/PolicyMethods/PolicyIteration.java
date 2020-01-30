package PolicyMethods;
import Resources.GridWorld;
import Resources.TransitionModel;

public class PolicyIteration {
	public static final double discountFactor = 0.99;
	public static TransitionModel TM = TransitionModel.getTransitionModel();
	public static void main(String[] args) {
		
		// String arr[row][col] represent a NxN square grid for the GridWorld
		// "W" = White Square, "G" = Green Square, "B" = Brown Square, "WALL" = Wall Square, default = White Square
		String [][]arr= {{"G", "WALL", "G", "W", "W", "G"},
						 {"W", "B", "W", "G", "W", "B"},
						 {"W", "W", "B", "W", "G", "W"},
						 {"W", "W", "W", "B", "W", "G"},
						 {"W", "WALL", "WALL", "WALL", "B", "W"},
						 {"W", "W", "W", "W", "W", "W"}
		};
		
		// Since the GridWorld has a 6x6 square world, so 6 is pass into the instantiation of GridWorld's row and col
		GridWorld GW = new GridWorld(arr, 6, 6);
		
		// This will imply the solution is not found as the policy is still improved
		boolean solutionNotFound = true;
		
		// Print the grid world that is generated to see the GridWorld in a grid format for better view
		GW.printGridWorld();
		
		// Initialisation of each state to "Up"
		// Possible actions are "Up", "Down", "Left", "Right"
		GW.policyInitialisation("Up");
		GW.utilityInitialisation(0);
		
		int iteration = 0;
		// While the solution is not found
		while (solutionNotFound) { // add in a and condition with iteration less than x can limit iterations to x
			++iteration;
			// Evaluate the current policy
			evaluatePolicy(GW);
			
			// Execute policyImprovement and it will improve the policy of state and 
			// return true if the policy is update, else return false if the policy is not updated and the solution must have been found
			solutionNotFound = policyImprovement(GW);
			
			// This is used for getting utility estimates for each state
			//GW.printGridUtility(iteration);
		}
		
		// This is used for getting utility estimates for each state
		GW.printGridUtility(iteration);
		
		// Print the grid world that is generated to see the GridWorld in a grid format again
		GW.printGridWorld();
		
		// Print the policy in the grid world and you can observe the direction in the grid format
		GW.printGridPolicy();
		
	}
	
	public static void evaluatePolicy(GridWorld GW) {
		
		double tmputility = 0;
		
		// Iterate through all the states
		for (int  row = 0; row < GW.getRow(); row++) {
			for (int col = 0; col < GW.getCol(); col++) {
				
				// If the grid is a wall, skip it to save computation time
				if (GW.getGridIsWall(row, col)) {
					continue;
				}
				else {
					
					// Compute the utility function of the current grid based on its current policy
					tmputility = evaluateUtility(GW, row, col);

					// Update the computed utility function 
					GW.updateGridUtility(row, col, tmputility);
				}
			}
		}
	}
	
	
	public static double evaluateUtility(GridWorld GW, int row, int col) {
		// This is to get the possible actions i.e if the policy is Up, it will return Left and Right
		// as there is 0.1 chance of going to those directions
		String possibleActions[] = TM.getPossibleActions(GW.getGridPolicy(row, col));
		
		// This is to get the state of the agent after executing the policy's action and return row as position[0] and col as position[1]
		int position[] = GW.canMoveToGrid(row, col, GW.getGridPolicy(row, col));
		
		// These two positions array represent the two other state after executing the possible Actions
		int position2[] = GW.canMoveToGrid(row, col, possibleActions[0]);
		int position3[] = GW.canMoveToGrid(row, col, possibleActions[1]);
		
		double tmputility = 0;
		// This compute the utility function of state with row and col with a recursive call
		tmputility = GW.getGridReward(row, col) + 
				discountFactor *(0.8 * GW.getGridUtility(position[0], position[1]) 
						+ 0.1 * GW.getGridUtility(position2[0], position2[1])
						+ 0.1 * GW.getGridUtility(position3[0], position3[1]));
		return tmputility;
	}
	
	// Returns true if policy of any state is changed and update to the action that gives the best expected utility
	public static boolean policyImprovement(GridWorld GW){
		
		// Initialise MAXEU and EU as the highest expected utility of an action and expected utility of an particular action respectively
		double MAXEU, EU = 0;
		
		// policyUpdate will imply policy is improved when true and policy is unchanged when false
		boolean policyUpdated = false;
		
		// Iterate through all the states
		for (int row = 0; row < GW.getRow() && policyUpdated == false; row++) {
			for (int col = 0; col < GW.getCol() && policyUpdated == false; col++) {
				// If the grid is a wall, skip it
				if(GW.getGridIsWall(row, col)) {
					continue;
				}
				
				// EU will get the expected utility of the current policy
				EU = getExpectedUtility(GW, row, col, GW.getGridPolicy(row, col));
				
				// Set the current expected utility of this policy as MAXEU
				MAXEU = EU;
				
				// Iterate through other actions
				for (String s: TM.getActions()) {
					
					// If the action is the policy, skip it
					if (s.compareTo(GW.getGridPolicy(row, col)) == 0) {
						continue;
					}
					
					// Compute the expected utility of the action, s for the current state
					EU = getExpectedUtility(GW, row, col, s);
					
					// If the expected utility of this action is larger than the policy or the MAXEU(if it has already changed)
					if (EU > MAXEU) {
						
						// Update the grid's policy to be this action
						GW.updateGridPolicy(row, col, s);
						
						// Set this action's expected utility to MAXEU
						MAXEU = EU;
						
						// Since we updated the policy, policyUpdated will be set to True implying that policy is updated
						policyUpdated = true;
					}
				}
			}
			
			// Print the grid world that is generated to see the GridWorld in a grid format again
			GW.printGridWorld();
			
			// Print the policy in the grid world and you can observe the direction in the grid format
			GW.printGridPolicy();
		}
		
		// It will return policyUpdated to indicate if the policy is improved or not, so it know if it needs to execute evaluatePolicy again
		return policyUpdated;
	}
	public static double getExpectedUtility(GridWorld GW, int row, int col, String action) {
		
		// utility, utility2 and utility3 represents the utility of the state after executing action and other possible actions. 
		double utility, utility2, utility3, EU;
		
		// This is to get the possible actions i.e if the policy is Up, it will return Left and Right
		// as there is 0.1 chance of going to those directions
		String possibleActions[] = TM.getPossibleActions(action);
		
		// This is to get the state of the agent after executing the policy's action and return row as position[0] and col as position[1]
		int position[] = GW.canMoveToGrid(row, col, action);
		
		// These two positions array represent the two other state after executing the possible Actions
		int position2[] = GW.canMoveToGrid(row, col, possibleActions[0]);
		int position3[] = GW.canMoveToGrid(row, col, possibleActions[1]);
		
		// Get the utility of the grid based on the position array that we have
		utility = GW.getGridUtility(position[0], position[1]);
		utility2 = GW.getGridUtility(position2[0], position2[1]);
		utility3 = GW.getGridUtility(position3[0], position3[1]);
		
		// Compute expected utility with utility, utility2, utility3
		// Knowing that utility is the state after executing action(passed in by parameter) it has a probability of 0.8
		EU = 0.8 * utility + 0.1 * utility2 + 0.1 * utility3;
		
		// Return the expected utility to the caller
		return EU;
	}
}
