package Resources;

public class TransitionModel {
	private static TransitionModel TM = null;
	private static final String[] actions = {"Up", "Down", "Left", "Right"};
	private TransitionModel() {
		
	}
	
	public static TransitionModel getTransitionModel() {
		if (TM == null) {
			TM = new TransitionModel();
		}
		return TM;
	}
	
	public String[] getPossibleActions(String action) {
		switch (action) {
		case "Up":
		case "Down":
			return new String[] {"Left", "Right"};
		default:
			return new String[] {"Up", "Down"};
		}
	}
	public String[] getActions() {
		return actions;
	}
}
