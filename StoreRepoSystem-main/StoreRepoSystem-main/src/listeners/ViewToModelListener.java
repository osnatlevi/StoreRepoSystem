package listeners;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


public interface ViewToModelListener {

	void startProgram(String mapSort);

	void addProduct(String catalNum, String name, String importPrice, String retailPrice, String customerName,
			String custPhone, Boolean subscribtion) throws Exception;

	void searchForProduct(String catalNum) throws Exception;

	void sendMSG(String msg);

	void getProfitInfo();

	void showSubscribers();

	void undoLastChange();

	void removeProduct(String text);

	void removeAll();

	void getAllProducts();

}
