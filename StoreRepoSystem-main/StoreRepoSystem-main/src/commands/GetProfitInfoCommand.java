package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import model.Store;

public class GetProfitInfoCommand implements Command{
	private Store theStore;
	
	public GetProfitInfoCommand(Store theStore) {
		this.theStore = theStore;
	}
	
	@Override
	public void Execute() {
		theStore.getProfits();
		
	}

}
