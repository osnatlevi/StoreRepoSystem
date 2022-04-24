package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import model.Store;

public class RemoveAllCommand implements Command {

	private Store theStore;

	public RemoveAllCommand(Store theStore) {
		this.theStore = theStore;
	}

	@Override
	public void Execute() {
		theStore.removeAllProductsFromFile();
	}

}
