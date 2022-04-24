package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import memento.Memento;
import model.Store;

public class UndoCommand implements Command{

	private Store theStore;
	private Memento m;
	
	public UndoCommand(Store theStore, Memento m) {
		this.theStore = theStore;
		this.m = m;
	}

	@Override
	public void Execute() {
		theStore.undo(m);		
	}

}
