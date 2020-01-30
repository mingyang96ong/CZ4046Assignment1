package Resources;

public class GridWorld {
	
	// Grid represents all the states in the world
	private State[][] Grid;
	
	// This represents the row and col of the grid world
	private int row;
	private int col;
	
	// This constructor takes in the GridWorld design(arr) and the number of rows
	public GridWorld(String [][] arr, int row, int col) {
		
		// Initialise the size of the grid world
		Grid = new State[row][col];
		
		// This try block is to prevent the program to crash when their world is invalid as it is not a square GridWorld
		try {
			
			// Iterate through all the grids
			for (int r = 0; r < row; r++) {
				for (int c = 0; c < col; c++) {
					
					// Test the String which is the selection of the SquareColour in arr
					switch (arr[r][c].toUpperCase()) {
					
					// If it is "G", set the grid as GreenSquare
					case "G":
						Grid[r][c] = new GreenSquare();
						break;
						
					// If it is "W", set the grid as WhiteSquare
					case "W":
						Grid[r][c] = new WhiteSquare();
						break;
					
					// If it is "WALL", set the grid as WallSquare
					case "WALL":
						Grid[r][c] = new WallSquare();
						break;
						
					// If it is "B", set the grid as BrownSquare
					case "B":
						Grid[r][c] = new BrownSquare();
						break;
					
					// If it is an invalid entry, it set the grid as WhiteSquare
					default:
						Grid[r][c] = new WhiteSquare();
						break;
					}
				}
			}
			
			// Store the number of row and col after successfully created the user-defined world
			this.row = row;
			this.col = col;
		}
		catch(IndexOutOfBoundsException e) {
			
			// Inform the user the world is invalid
			System.out.println("Your provided world is invalid. This GridWorld is using a default");
			
			// Set the grid to the default 6x6 GridWorld and this.row to 6
			defaultGrid();
			
		}
	}
	
	public void defaultGrid() {
		
		// Instantiation of the default gridworld for each grid
		Grid[0][0] = new GreenSquare();
		Grid[0][1] = new WallSquare();
		Grid[0][2] = new GreenSquare();
		Grid[0][3] = new WhiteSquare();
		Grid[0][4] = new WhiteSquare();
		Grid[0][5] = new GreenSquare();
		Grid[1][0] = new WhiteSquare();
		Grid[1][1] = new BrownSquare();
		Grid[1][2] = new WhiteSquare();
		Grid[1][3] = new GreenSquare();
		Grid[1][4] = new WhiteSquare();
		Grid[1][5] = new BrownSquare();
		Grid[2][0] = new WhiteSquare();
		Grid[2][1] = new WhiteSquare();
		Grid[2][2] = new BrownSquare();
		Grid[2][3] = new WhiteSquare();
		Grid[2][4] = new GreenSquare();
		Grid[2][5] = new WhiteSquare();
		Grid[3][0] = new WhiteSquare();
		Grid[3][1] = new WhiteSquare();
		Grid[3][2] = new WhiteSquare();
		Grid[3][3] = new BrownSquare();
		Grid[3][4] = new GreenSquare();
		Grid[3][5] = new WhiteSquare();
		Grid[4][0] = new WhiteSquare();
		Grid[4][1] = new WhiteSquare();
		Grid[4][2] = new WhiteSquare();
		Grid[4][3] = new BrownSquare();
		Grid[4][4] = new WhiteSquare();
		Grid[4][5] = new GreenSquare();
		Grid[5][0] = new WhiteSquare();
		Grid[5][1] = new WallSquare();
		Grid[5][2] = new WallSquare();
		Grid[5][3] = new WallSquare();
		Grid[5][4] = new BrownSquare();
		Grid[5][5] = new WhiteSquare();
		
		
		// Since this default GridWorld is a 6x6 world, this.row is set to 6 and this.col is set to 6
		this.row = 6;
		this.col = 6;
	}
	
	// Print the reward of all states in the GridWorld in grid format
	public void printGridWorld() {
		
		// Inform user that the generated GridWorld is printed
		System.out.println("Printing GridWorld generated...");
		
		// Prints the border of the world
		printBorder();
		
		// Iterate through all the states/grids
		for (int row = 0; row < this.row; row++) {
			for (int col = 0; col < this.col; col++) {
				
				// Print each of the colour of each square or if it is a wall
				Grid[row][col].print();
			}
			
			// Close the world on the end of each row
			System.out.println("|");
			
			// Print the border to separate each row
			printBorder();
		}
		
		// Print new line to separate the world
		System.out.println();
	}
	
	private void printBorder() {
		
		// Basically it just print the world
		for (int i = 0; i < 6*(col-1); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	public void printGridPolicy() {
		
		// Inform the user the policy of each state will be printed in the grid format
		System.out.println("Printing GridWorld with Policy");
		printBorder();
		for (int row = 0; row < this.row; row++) {
			for(int col = 0; col < this.col; col++) {
				
				// Print the policy of state by calling printPolicy function provided by the State class
				Grid[row][col].printPolicy();
			}
			System.out.println("|");
			printBorder();
		}
		System.out.println();
	}
	
	public void printGridUtility(int iteration) {
		
		System.out.println("Iteration: " + iteration);
		
		// Printing of the utility of the states with the optimal policy
		System.out.println("Reference utilities of states:");
		System.out.println("Coordinates are in (col, row) format with the top left corner"
				+ " being (0,0).");
		
		// Iterate through all the states
		for (int col = 0; col < getCol(); col++) {
			for (int row = 0; row < getRow(); row++) {
				
				// Print only if the grid/state is not a wall
				if (!getGridIsWall(row, col)) {
					System.out.println("("+col+","+row+"):"+getGridUtility(row, col)+ " Policy: " + getGridPolicy(row, col));
				}
			}
		}
		
	}
	
	// Returns the row of the GridWorld
	public int getRow() {
		return row;
	}
	
	// Returns of col of the GridWorld
	public int getCol() {
		return col;
	}
	
	// Returns the reward of a particular grid in the GridWorld
	public double getGridReward(int row, int col) {
		return Grid[row][col].getReward();
	}
	
	// Returns true if a particular grid in the GridWorld is a wall, else return false if that grid is not
	public boolean getGridIsWall(int row, int col) {
		return Grid[row][col].isWall();
	}
	
	// Update the utility of a particular grid in the GridWorld
	public void updateGridUtility(int row, int col, double u){
		Grid[row][col].updateUtility(u);
	}
	
	// Returns the utility of a particular grid in the GridWorld
	public double getGridUtility(int row, int col) {
		return Grid[row][col].getUtility();
	}
	
	// Returns the policy of a particular grid in the GridWorld
	public String getGridPolicy(int row, int col) {
		return Grid[row][col].getPolicy();
	}
	
	// Update the policy of a particular grid in the GridWorld
	public void updateGridPolicy(int row, int col, String s) {
		Grid[row][col].setPolicy(s);
	}
	
	// Return true if the agent in a particular grid in the GridWorld can move in the direction of action else return false if it cannot
	public boolean canMoveToGridDirection(int row, int col, String action) {
		switch (action) {
		case "Up":
			
			// Check if the agent move out of the GridWorld and if the Grid the agent is moving to is a wall
			if (row-1 < 0 || getGridIsWall(row-1, col)) {
				return false;
			}
			
			return true;
			
		case "Down":
			
			// Check if the agent move out of the GridWorld and if the Grid the agent is moving to is a wall
			if (row+1 >= this.row || getGridIsWall(row+1, col)) {
				return false;
			}
			
			return true;
			
		case "Left":
			
			// Check if the agent move out of the GridWorld and if the Grid the agent is moving to is a wall
			if (col-1 < 0 || getGridIsWall(row, col-1)) {
				return false;
			}
			
			return true;
			
		case "Right":
			
			// Check if the agent move out of the GridWorld and if the Grid the agent is moving to is a wall
			if (col+1 >= this.col || getGridIsWall(row, col+1)) {
				return false;
			}
			return true;
			
		default:
			// Serves as a debugging check
			System.out.println("Something wrong in GridWorld canMoveToGridDirection.");
			return false;
		}
	}
	
	// Returns the position of the a grid after the agent executes action at grid[row][col]
	public int[] canMoveToGrid(int row, int col, String action) {
		
		int position[] = new int[2];
		
		// If the grid[row][col] is a wall, the agent will just return its row and col back
		if (getGridIsWall(row, col)) {
			position[0] = row;
			position[1] = col;
			return position;
		}
		
		// If the agent can move in the direction of that action
		if (canMoveToGridDirection(row, col, action)) {
			
			// Moving up is equivalent to row-1
			switch (action) {
			case "Up":
				
				position[0] = row - 1;
				position[1] = col;
				break;
				
			// Moving down is equivalent to row+1
			case "Down":
				
				position[0] = row + 1;
				position[1] = col;
				break;
				
			// Moving left is equivalent to col-1
			case "Left":
				
				position[0] = row;
				position[1] = col - 1;
				break;
				
			// Moving right is equivalent to col+1
			case "Right":
				
				position[0] = row;
				position[1] = col + 1;
				break;
				
			default:

			// There is some error but prevent the system crash it will set position as the same grid
				position[0] = row;
				position[1] = col;
				
			// Inform the user that there is some error in the code
				System.out.println("Something wrong in GridWorld can" +
				"MoveToGrid");
			}
		}
		else {
			// Agent cannot move in the direction hence it remained on the same grid
			position[0] = row;
			position[1] = col;
		}
		
		// Return the position array (which is the next position of the next grid after executing an action at a particular grid)
		return position;
	}
	
	// Initialisation of the policy of all the state as the String s
	public void policyInitialisation(String s) {
		for (int row = 0; row < this.row; row++) {
			for (int col = 0; col < this.col; col++) {
				Grid[row][col].setPolicy(s);
			}
		}
	}
	
	// Return GridWorld object that is deep copy of the original GridWorld object caller
	public GridWorld copyGW(String[][] arr) {
		
		// Instantiation of new GridWorld object
		GridWorld tmp = new GridWorld(arr, this.getRow(), this.getCol());
		
		// Iterate through the row and col of the GridWorld
		for (int row = 0; row < tmp.getRow(); row++) {
			for (int col = 0; col < tmp.getCol(); col++) {
				
				// If it is the wall, it does not have a reward or policy, hence skip it
				if (tmp.getGridIsWall(row, col)) {
					continue;
				}
				
				// Update the utility of particular grid of the new GridWorld with the utility of the corresponding grid of the GridWorld caller
				tmp.updateGridUtility(row, col, this.getGridUtility(row, col));
				
				// Update the policy of particular grid of the new GridWorld with the policy of the corresponding grid of the GridWorld caller
				tmp.updateGridPolicy(row, col, this.getGridPolicy(row, col));
			}
		}
		return tmp;
	}
	
	// Inilialisation of the utility value of all states
	public void utilityInitialisation(double u) {

		for (int row = 0; row < this.row; row++) {
			for (int col = 0; col < this.col; col++) {
				
				updateGridUtility(row, col, u);
			}
		}
	}
}
