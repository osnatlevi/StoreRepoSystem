package listeners;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import model.Product;

public interface ModelToViewListener {

	void notifyNewProduct(String string);

	void errorAlert(String string);

	void notifyProductFound(Product theProduct);

	void deliverProfitPPInfo(String catalogNum, int profitPerProduct);

	void notifyTotalProfit(String string);

	void showSubscriber(String custName, String phoneNumber);

	void fireDataRemoved(String msg);

	void fireProductToView(Product p);

}
