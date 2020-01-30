package Resources;

public abstract class State {
	
	protected double reward; // reward is the reward of the state
	protected boolean isWall = false; // isWall is true if the state is a wall, else it is false
	protected double utility = 0; // utility represents the utility of the state
	protected String policy; // policy is the policy(action) of the state
	
	// Returns the rewards of the state
	public double getReward() {
		return reward;
	};
	
	// Returns true if the state is a wall else return false
	public boolean isWall(){
		return isWall;
	}
	
	// Updates the utility of the state to the Double object, u passed in as a parameter
	public void updateUtility(double u) {
		utility = u;
	}
	
	// Returns the utility of the state
	public double getUtility() {
		return utility;
	}
	
	// Abstract method print for all the state to print their colour in the grid format
	public abstract void print();
	
	// Updates the policy of the state to the String object, s passed in as a parameter
	public void setPolicy(String s) {
		policy = s;
	}
	
	// Returns the policy of the state
	public String getPolicy() {
		return policy;
	}
	
	// Print the policy of the state in a grid format and the policy are represented by arrow signs
	public void printPolicy() {
		switch(policy) {
		case "Up":
			System.out.print("|  ^ ");
			break;
		case "Down":
			System.out.print("|  v ");
			break;
		case "Left":
			System.out.print("| <- ");
			break;
		case "Right":
			System.out.print("| -> ");
		}
	}
}
