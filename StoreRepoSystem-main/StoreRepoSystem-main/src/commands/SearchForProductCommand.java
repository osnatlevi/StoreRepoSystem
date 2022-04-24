package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import model.Store;

public class SearchForProductCommand implements Command{

	private String catalogNum;
	private Store theStore;
	
	public SearchForProductCommand(Store theStore, String catalogNum) {
		this.catalogNum = catalogNum;
		this.theStore = theStore;
	}
	@Override
	public void Execute() {
		theStore.SearchForProduct(catalogNum);
	}

}
