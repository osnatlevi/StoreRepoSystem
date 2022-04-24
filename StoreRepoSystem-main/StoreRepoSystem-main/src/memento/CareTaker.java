package memento;

//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 

public class CareTaker {

	private Memento saveStoreStorage;
	
	public void saveMemento(Memento m) {
		saveStoreStorage = m;
	}
	
	public Memento getMemento() {
		return saveStoreStorage;
	}
}
