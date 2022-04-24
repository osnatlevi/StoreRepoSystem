package model;

//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 

import observer.Reciever;
import observer.Sender;

public class StorePromoDep implements Sender {

	private static StorePromoDep StorePromoDep_instance = null;

	private StorePromoDep() {
		
	}

	// -------------------------------------------------------- Singleton Unit --------------------------------------------------

	public static StorePromoDep getInstance() {
		if (StorePromoDep_instance == null) {
			StorePromoDep_instance = new StorePromoDep();
		}
		return StorePromoDep_instance;
	}

	@Override
	public Customer sendMSG(Reciever r, String msg) {
		Customer c = r.receiveMSG(this, msg);
		return c;
	}

}
