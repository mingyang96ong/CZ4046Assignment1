package Resources;

public class WallSquare extends State {
	
	// Constructor set the variable isWall of WallSquare to true
	public WallSquare() {
		super.isWall = true;
	}
	
	// Return 0 if called as Wall do not have reward
	public double getReward() {
		return 0;
	}
	
	// Return null since it is invalid
	public String[] getActions() {
		return null;
	}
	
	// Print "|Wall" for the grid world representation
	public void print() {
		System.out.print("|Wall");
	}
	
	// Print "|Wall" for the policy representation in the grid format
	public void printPolicy() {
		print();
	}
}
