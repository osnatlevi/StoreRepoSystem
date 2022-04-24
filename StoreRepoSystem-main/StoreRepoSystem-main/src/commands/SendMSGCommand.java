package commands;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import model.Customer;
import model.Store;

public class SendMSGCommand extends Thread implements Command {
	private Store theStore;
	private String msg;
	
	// -------------------------------------------------------- Constructors --------------------------------------------------

	public SendMSGCommand(Store theStore, String msg) {
		this.theStore = theStore;
		this.msg = msg;
	}
	
	// -------------------------------------------------------- Thread Init --------------------------------------------------

	Thread t1 = new Thread(new Runnable() {

		@Override
		public void run() {
			try {
				HashMap<String, Customer> list = theStore.getSubscribers();
				for (Map.Entry<String, Customer> entry : list.entrySet()) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							theStore.distributeNewMSG(entry.getValue(), msg);
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
		t1.start();
	}

}
