package main;

//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 

import java.io.RandomAccessFile;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Store;
import view.MainView;

public class ProgramStarter extends Application {

	public static void main(String[] args) {
		launch(args);

	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MainView theView = new MainView(primaryStage);
		Store theStore = new Store(new RandomAccessFile("products.txt", "rw"));
		Controller theController = new Controller(theStore, theView);
	}

}
