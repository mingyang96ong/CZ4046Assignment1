package Resources;
public class GreenSquare extends State {
	
	// Constructor set the reward of GreenSquare to -1
	public GreenSquare() {
		super.reward = 1;
	}
	
	//Implements the print function to show the GridWorld representation
	public void print() {
		System.out.print("| +1 ");
	}
}
