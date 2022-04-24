package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import model.Customer;
import model.Store;

public class showSubscribersCommand extends Thread implements Command {

	private Store theStore;

	public showSubscribersCommand(Store theStore) {
		this.theStore = theStore;
	}

	Thread t = new Thread(new Runnable() {

		@Override
		public void run() {
			try {
				HashMap<String,Customer> list = theStore.getSubscribers();
				for(Map.Entry<String, Customer> entry : list.entrySet()){
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							theStore.sendSubscribersToView(entry.getValue());
						}
					});

				
					Thread.sleep(2000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});

	@Override
	public void Execute() {
		t.start();
	}

}
