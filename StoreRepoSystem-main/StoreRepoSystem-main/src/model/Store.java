package model;

// OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import comperators.DescOrder;
import listeners.ModelToViewListener;
import memento.Memento;

public class Store extends Thread {

	private Vector<ModelToViewListener> listeners;
	private HashMap<String, Customer> subscribers;
	private Map<String, Product> storeMap;
	private StorePromoDep thePromoDep;
	private RandomAccessFile raf;
	private boolean readingFromFile;
	private boolean fireReadFromFile;
	private String mapSort;

	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Constructor $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	public Store(RandomAccessFile raf) {
		listeners = new Vector<ModelToViewListener>();
		this.thePromoDep = StorePromoDep.getInstance();
		this.raf = raf;
		try {
			if (raf.length() != 0) {
				this.readingFromFile = true;
				this.fireReadFromFile = true;
				readFromFile(true);
			} else {
				this.readingFromFile = false;
				this.fireReadFromFile = false;
			}

		} catch (IOException e) {
			System.out.println("Failed Reading From File");
		}
	}
	
	public void registerListener(ModelToViewListener newListener) {
		listeners.add(newListener);
	}

	public void openStore(String mapSort) {
		this.mapSort = mapSort;
		if (mapSort.compareTo("Asc") == 0) {
			storeMap = new TreeMap<String, Product>();
		} else if (mapSort.compareTo("Des") == 0) {
			storeMap = new TreeMap<String, Product>(new DescOrder());
		} else {
			storeMap = new LinkedHashMap<String, Product>();
		}
		subscribers = new HashMap<String,Customer>();
	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Getters & Setters $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public boolean getReadingFromFile() {
		return fireReadFromFile;
	}

	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Add Product $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public void addProductToModel(Product theProduct) {
		
		if (storeMap.containsKey(theProduct.getCatalogNum())) {
			storeMap.replace(theProduct.getCatalogNum(), theProduct);
			if (theProduct.getCustomer().getSubscribedStatus())
				subscribers.replace(theProduct.getCatalogNum(), theProduct.getCustomer());
			//TODO System.out.println("Value Updated:" + storeMap.get(theProduct.getCatalogNum()));
		} else {
			storeMap.put(theProduct.getCatalogNum(), theProduct);
			// Adding A Customer To Subscription List
			if ((theProduct.getCustomer().getSubscribedStatus()))
				subscribers.put(theProduct.getCatalogNum(), theProduct.getCustomer());
			//TODO System.out.println("New Entry Added:" + storeMap.get(theProduct.getCatalogNum()));
		}
		

		if (!readingFromFile) {
			notifyViewNewProductAdded();
			saveToFile();
		}
	}
	
	private void notifyViewNewProductAdded() {
		for (ModelToViewListener l : listeners) {
			l.notifyNewProduct("New Product has Been Added");
		}

	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Memento Util. $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public Memento createStoreSave() {	
		Memento m = new Memento(subscribers, storeMap, this.mapSort);
		return m;		
	}
	
	public void undo(Memento m) {
		openStore(mapSort);
		storeMap.putAll(m.getStoreMap());
		this.subscribers = new HashMap<String, Customer>(m.getSubscribers());
		saveToFile();
		fireDataRemoved("Undo Function Successed");
	}

	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Printers $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public void printMap() {
		for(Map.Entry<String, Product> entry : storeMap.entrySet()) {
			Product p = entry.getValue();
			System.out.println(p);
		}
	}

	public void printMapInView() {
		for(Map.Entry<String, Product> entry : storeMap.entrySet()) {
			Product p = entry.getValue();
		for (ModelToViewListener l : listeners) {
			l.fireProductToView(p);
		}
		}
	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Search Util. $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public Product SearchForProduct(String catNum) {
		Product theProduct = storeMap.get(catNum);
		returnSearchResultToView(theProduct);
		return theProduct;
	}

	private void returnSearchResultToView(Product theProduct) {
		for (ModelToViewListener l : listeners) {
			l.notifyProductFound(theProduct);
		}

	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Messenger Util. $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public void distributeNewMSG(Customer customer, String msg) {
			Customer c = thePromoDep.sendMSG(customer, msg);
			sendSubscribersToView(c);
	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Profits $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public void getProfits() {
		int totalProfit = 0;
		Iterator<String> it = storeMap.keySet().iterator();
		while (it.hasNext()) {
			Product p = storeMap.get(it.next());
			int profitPerProduct = p.getCustPrice() - p.getStorePrice();
			totalProfit += profitPerProduct;
			for (ModelToViewListener l : listeners)
				l.deliverProfitPPInfo(p.getCatalogNum(), profitPerProduct);
		}
		for (ModelToViewListener l : listeners)
			l.deliverProfitPPInfo("                     - Total:", totalProfit);

	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Subscribers Util. $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public void sendSubscribersToView(Customer c) {
		for (ModelToViewListener l : listeners) {
			l.showSubscriber(c.getCustName(), c.getPhoneNumber());
		}
	}
	
	public HashMap<String,Customer> getSubscribers() {
		return subscribers;
	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Remove Util. $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	public void removeProductFromFile(String catNum) {
		boolean found = false;
		try {
			Iterator<Product> fI = iterator();
			raf.seek(5);
			Product p;
			while (fI.hasNext()) {
				p = (Product) fI.next();
				if (p.getCatalogNum().compareTo(catNum) == 0) {
					fI.remove();
					fireDataRemoved("Product Remove Successed");
					found = true;
					break;
				}

			}
			raf.seek(5);
			if (!found)
				fireDataRemoved("Product not Found");

		} catch (IOException e) {
			fireDataRemoved(e.getMessage());
		}
		readFromFile(false);

	}

	public void removeAllProductsFromFile() {
		try {
			Iterator<Product> fI = iterator();
			raf.seek(5);
			while (fI.hasNext()) {
				fI.next();
				fI.remove();
				raf.seek(5);
			}
			raf.seek(5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		readFromFile(false);
		fireDataRemoved("All Products Remove Successed");
	}

	private void fireDataRemoved(String msg) {
		for (ModelToViewListener l : listeners) {
			l.fireDataRemoved(msg);
		}
		
	}

	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Save To File And Read To File Functions $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	public void saveToFile() {
		try {
			raf.setLength(0);
			raf.writeUTF(mapSort);
			for(Map.Entry<String, Product> entry : storeMap.entrySet()) {
				Product p = entry.getValue();

				p.saveProductToFile(raf);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readFromFile(boolean startup) {

		try {
			this.readingFromFile = true;
			String mSort = this.mapSort;
			if(startup) {
				mSort = raf.readUTF();
			}
			openStore(mSort);
			raf.seek(5);
			Iterator<Product> fI = iterator();
			
			while (fI.hasNext()) {
				Product theProduct = (Product) fI.next();
				addProductToModel(theProduct);
			}
			this.readingFromFile = false;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ File Iterator!!! $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	public Iterator<Product> iterator(){
		return new FileIterator();
	}
	
	private class FileIterator implements Iterator<Product> {
	private long posBeforeNext = 5;	
	
	@Override
	public boolean hasNext() {
		try {
			if (raf.getFilePointer() < raf.length())
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Product next() {
		try {
			posBeforeNext = raf.getFilePointer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Product(raf);
	}
	
	@Override
	public void remove() {

		try {
			if(raf.getFilePointer() == raf.length()) {
				raf.setLength(raf.length() - (raf.getFilePointer()-posBeforeNext));
				return;
			}
			raf.seek(posBeforeNext);
			this.next();
			byte bites[] = new byte[(int)(raf.length()-raf.getFilePointer())];
			raf.read(bites);
			raf.setLength(posBeforeNext);
			raf.seek(posBeforeNext);
			raf.write(bites);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

}