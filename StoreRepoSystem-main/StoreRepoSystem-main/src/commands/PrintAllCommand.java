package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import model.Store;

public class PrintAllCommand implements Command{

	private Store theStore;

	public PrintAllCommand(Store theStore) {
		this.theStore = theStore;

	}

	@Override
	public void Execute() {
		theStore.printMapInView();

	}

}
