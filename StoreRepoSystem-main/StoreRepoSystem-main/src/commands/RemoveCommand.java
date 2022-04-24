package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import model.Store;

public class RemoveCommand implements Command {
	String catNum;
	Store theStore;

	public RemoveCommand(Store theStore, String catNum) {
		this.theStore = theStore;
		this.catNum = catNum;
	}

	@Override
	public void Execute() {
		theStore.removeProductFromFile(catNum);

	}

}