package controller;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import commands.AddProductCommand;
import commands.Command;
import commands.GetProfitInfoCommand;
import commands.PrintAllCommand;
import commands.RemoveAllCommand;
import commands.RemoveCommand;
import commands.SearchForProductCommand;
import commands.SendMSGCommand;
import commands.UndoCommand;
import commands.showSubscribersCommand;
import listeners.ModelToViewListener;
import listeners.ViewToModelListener;
import memento.Memento;
import model.Customer;
import model.Product;
import model.Store;
import view.MainView;

public class Controller implements ModelToViewListener, ViewToModelListener {
	private Store theModel;
	private MainView theView;
	private Command theCommand;
	private Memento m;

	public Controller(Store theModel, MainView theView) {
		this.theModel = theModel;
		this.theView = theView;

		theModel.registerListener(this);
		theView.registerListener(this);
		this.theView.setReadStatus(theModel.getReadingFromFile());
	}

	@Override
	public void startProgram(String mapSort) {
		theModel.openStore(mapSort);
	}

	@Override
	public void addProduct(String catalNum, String productName, String importPrice, String retailPrice, String custName,
			String phoneNumber, Boolean subscribtion) throws Exception {
		int storePrice = 0, custPrice = 0;
		if (catalNum.isEmpty()) {
			throw new Exception("Catalog Number Cannot Be Empty");
		}

		if (productName.isEmpty())
			productName = "";

		if (!(importPrice.isEmpty())) {
			storePrice = Integer.parseInt(importPrice);
		}

		if (!(importPrice.isEmpty())) {
			custPrice = Integer.parseInt(retailPrice);
		}

		if (custName.isEmpty()) {
			custName = "";
		}

		if (phoneNumber.isEmpty()) {
			phoneNumber = "No Phone Number";
		} else if (!(phoneNumber.matches("[0-9]+") && phoneNumber.length() > 2)) {
			throw new Exception("Phone Number Can Not Contain AlphaBet");
		}

		Customer theCustomer = new Customer(custName, phoneNumber, subscribtion);
		Product theProduct = new Product(catalNum, productName, storePrice, custPrice, theCustomer);

		m = theModel.createStoreSave();

		theCommand = new AddProductCommand(theModel, theProduct);
		theCommand.Execute();

	}

	@Override
	public void searchForProduct(String catalogNum) throws Exception {
		if (catalogNum.isEmpty())
			throw new Exception("Catalog Number Cannot Be Empty");
		theCommand = new SearchForProductCommand(theModel, catalogNum);
		theCommand.Execute();
	}

	public void sendMSG(String msg) {
		theCommand = new SendMSGCommand(theModel, msg);
		theCommand.Execute();
	}

	@Override
	public void notifyNewProduct(String notification) {
		theView.addNotification(notification);
	}

	@Override
	public void errorAlert(String msg) {
		theView.alertInvalidInput(msg);
	}

	@Override
	public void notifyProductFound(Product p) {
		theView.showProductInfo(p.getProductName(), p.getStorePrice(), p.getCustPrice(), p.getCustomer().getCustName(),
				p.getCustomer().getPhoneNumber(), p.getCustomer().getSubscribedStatus());

	}

	@Override
	public void getProfitInfo() {
		theCommand = new GetProfitInfoCommand(theModel);
		theCommand.Execute();
	}

	@Override
	public void deliverProfitPPInfo(String catalogNum, int profitPerProduct) {
		theView.updateProfitTable(catalogNum, profitPerProduct);

	}

	@Override
	public void notifyTotalProfit(String notification) {
		theView.addNotification(notification);
	}

	@Override
	public void showSubscribers() {
		theCommand = new showSubscribersCommand(theModel);
		theCommand.Execute();
	}

	@Override
	public void showSubscriber(String custName, String phoneNumber) {
		theView.addSubscriberToView(custName, phoneNumber);
	}

	@Override
	public void undoLastChange() {
		Command restoreCommand = new UndoCommand(theModel, m);
		restoreCommand.Execute();
	}

	@Override
	public void removeProduct(String catNum) {
		theCommand = new RemoveCommand(theModel, catNum);
		theCommand.Execute();

	}

	@Override
	public void removeAll() {
		theCommand = new RemoveAllCommand(theModel);
		theCommand.Execute();

	}

	@Override
	public void fireDataRemoved(String msg) {
		theView.notifyDataRemoved(msg);

	}

	@Override
	public void getAllProducts() {
		theCommand = new PrintAllCommand(theModel);
		theCommand.Execute();

	}

	@Override
	public void fireProductToView(Product p) {
		theView.addProductToView(p.toString());

	}

}
