package model;

//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 

import java.io.IOException;
import java.io.RandomAccessFile;

public class Product {
	private String catalogNum;
	private String productName;
	private int storePrice;
	private int custPrice;
	private Customer customer;
	
	// -------------------------------------------------------- Constructors --------------------------------------------------

	public Product(String catalogNum, String productName, int storePrice, int custPrice, Customer customer) {
		this.catalogNum = catalogNum;
		this.productName = productName;
		this.storePrice = storePrice;
		this.custPrice = custPrice;
		this.customer = customer;
	}

	public Product(RandomAccessFile raf) {
		try {

			this.catalogNum = raf.readUTF();
			this.productName = raf.readUTF();
			this.storePrice = raf.readInt();
			this.custPrice = raf.readInt();
			this.customer = new Customer(raf);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public Product(Product p) {
			this.catalogNum = p.getCatalogNum();
			this.productName = p.getProductName();
			this.storePrice = p.getStorePrice();
			this.custPrice = p.getCustPrice();
			this.customer = new Customer(p.getCustomer());
	}
	
	// -------------------------------------------------------- Getters & Setters --------------------------------------------------

	public String getCatalogNum() {
		return catalogNum;
	}

	public String getProductName() {
		return productName;
	}

	public int getStorePrice() {
		return storePrice;
	}

	public int getCustPrice() {
		return custPrice;
	}

	public Customer getCustomer() {
		return customer;
	}

	// -------------------------------------------------------- Save Product To File Function --------------------------------------------------
	
	public void saveProductToFile(RandomAccessFile raf) {
		try {
			raf.writeUTF(catalogNum);
			raf.writeUTF(productName);
			raf.writeInt(storePrice);
			raf.writeInt(custPrice);
			raf.writeUTF(customer.getCustName());
			raf.writeUTF(customer.getPhoneNumber());
			raf.writeBoolean(customer.getSubscribedStatus());
		} catch (IOException e) {

		}

	}
	
	// -------------------------------------------------------- To String --------------------------------------------------
	@Override
	public String toString() {
		return "** Product Catalog Number: " + catalogNum + " | " + productName + " | Import Price: " + storePrice
				+ " | Retail Price: " + custPrice + "\n"+ customer;
	}

	
}
