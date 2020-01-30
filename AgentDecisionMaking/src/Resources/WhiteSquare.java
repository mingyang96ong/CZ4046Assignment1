package Resources;

public class WhiteSquare extends State{
	
	// Constructor set the reward of WhiteSquare to -0.04
	public WhiteSquare() {
		super.reward = -0.04;
	}
	
	//Implements the print function to show the GridWorld representation
	public void print() {
		System.out.print("|    ");
	}
}
