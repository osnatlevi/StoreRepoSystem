package memento;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import comperators.DescOrder;
import model.Customer;
import model.Product;

public class Memento {
	
	private HashMap<String,Customer> subscribers;
	private Map<String, Product> mapStore;
	private String mapSort;
	
	public Memento(HashMap<String, Customer> subscribers, Map<String, Product> storeMap, String mapSort) {
		this.mapSort = mapSort;
		this.subscribers = new HashMap<String, Customer>();
		if (mapSort.compareTo("Asc") == 0) {
			mapStore = new TreeMap<String, Product>();
		} else if (mapSort.compareTo("Des") == 0) {
			mapStore = new TreeMap<String, Product>(new DescOrder());
		} else {
			mapStore = new LinkedHashMap<String, Product>(storeMap);
		}
		
		for(Map.Entry<String, Product> entry : storeMap.entrySet()) {
			Product p = entry.getValue();
			mapStore.put(p.getCatalogNum(), p);
			if (p.getCustomer().getSubscribedStatus())
				this.subscribers.put(p.getCatalogNum(), p.getCustomer());
		}
		
	}

	public HashMap<String,Customer> getSubscribers() {
		return subscribers;
	}

	public Map<String, Product> getStoreMap() {
		return mapStore;
	}
	
	public String getMapSort() {
		return mapSort;
	}
	
	
	
	
	
}
