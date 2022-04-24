package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import model.Product;
import model.Store;

public class AddProductCommand implements Command {

	private Store theStore;
	private Product theProduct;

	public AddProductCommand(Store theStore, Product theProduct) {
		this.theStore = theStore;
		this.theProduct = theProduct;
	}

	@Override
	public void Execute() {
		theStore.addProductToModel(theProduct);

	}

}
