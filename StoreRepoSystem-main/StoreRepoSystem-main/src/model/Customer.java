package model;

//OOP Final Project - Dorel Saig 

import java.io.IOException;
import java.io.RandomAccessFile;

import observer.Reciever;
import observer.Sender;

public class Customer implements Reciever {

	private String custName;
	private String phoneNumber;
	private boolean subscribed;

	// -------------------------------------------------------- Constructors --------------------------------------------------

	public Customer(String custName, String phoneNumber, boolean subscribed) {
		this.custName = custName;
		this.phoneNumber = phoneNumber;
		this.subscribed = subscribed;
	}

	public Customer(RandomAccessFile raf) {
		try {
			this.custName = raf.readUTF();
			this.phoneNumber = raf.readUTF();
			this.subscribed = raf.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Customer(Customer c) {
		this.custName = c.getCustName();
		this.phoneNumber = c.getPhoneNumber();
		this.subscribed = c.getSubscribedStatus();
	}
	
	// -------------------------------------------------------- Getters & Setters --------------------------------------------------

	public String getCustName() {
		return custName;
	}

	public boolean getSubscribedStatus() {
		return subscribed;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	// -------------------------------------------------------- Messenger Util --------------------------------------------------

	@Override
	public Customer receiveMSG(Sender s, String msg) {
		return this;
	}

	// -------------------------------------------------------- To String --------------------------------------------------

	@Override
	public String toString() {
		return "--> Customer Name: " + custName + " | Phone: " + phoneNumber + " | Subscribtion " + subscribed;
	}

}
