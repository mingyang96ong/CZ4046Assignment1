package Resources;
public class BrownSquare extends State {
	
	// Constructor set the reward of BrownSquare to -1
	public BrownSquare() {
		super.reward = -1;
	}
	
	//Implements the print function to show the GridWorld representation
	public void print() {
		System.out.print("| -1 ");
	}
}
