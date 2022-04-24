package observer;

//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 

import model.Customer;

public interface Reciever {

	Customer receiveMSG(Sender s, String msg);

}
