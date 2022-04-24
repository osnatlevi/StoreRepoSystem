package view;

//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 

import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.ViewToModelListener;

public class MainView {

	private Vector<ViewToModelListener> allListeners;

	private BorderPane bpRoot;
	private Label Caption;
	private TextField[] tf;
	private Button addProductMenuBtn, searchProductMenuBtn, sendMessageToCustMenuBtn, showProfitsMenuBtn,
			printAllProductMenuBtn, printAllSubscribersMenuBtn, removeProductMenuBtn, removeAllMenuBtn;
	private Button addProductBtn, undoLastChange, searchProductBtn, sendMessageToCustBtn, removeProductBtn;
	private Button startProgram;
	private RadioButton selected;
	private HBox rdoButtons;
	private VBox leftVbox;
	private Text[] infofield;
	private GridPane profitView;
	private ScrollPane profitScrollPane;
	private ScrollPane printAllScrollPane;
	private VBox allProductsVBox;
	private int gPRow;
	private VBox sendMessageVbox;
	private Text subscriberDescription;
	private VBox subscribersVbox;
	private VBox topVbox;

	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Constructor & Startup $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	public MainView(Stage primaryStage) {
		primaryStage.setTitle("Store Application");
		Caption = new Label("Store Application - Main");
		allListeners = new Vector<ViewToModelListener>();
		start(primaryStage);
	}

	private void start(Stage primaryStage) {
		bpRoot = new BorderPane();
		bpRoot.setPadding(new Insets(40));
		bpRoot.setBackground(setBackgroundColor(Color.LIGHTGRAY));

		//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Top BorderPane Editor $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		
		Caption.setUnderline(true);
		Caption.setFont(Font.font("Eras Demi ITC", FontWeight.EXTRA_BOLD, 40));
		DropShadow CaptionShad = new DropShadow(20, Color.LIGHTSKYBLUE);
		Caption.setEffect(CaptionShad);

		topVbox = new VBox();
		topVbox.setPrefWidth(200);
		topVbox.setSpacing(10);
		topVbox.setBackground(setBackgroundColor(Color.LIGHTGRAY));
		topVbox.setAlignment(Pos.CENTER);

		ToggleGroup tglSaveOptions = new ToggleGroup();
		RadioButton rdoDescAlpha = new RadioButton("Des");
		RadioButton rdoAscAlpha = new RadioButton("Asc");
		RadioButton rdoByInput = new RadioButton("Ord");
		rdoDescAlpha.setToggleGroup(tglSaveOptions);
		rdoAscAlpha.setToggleGroup(tglSaveOptions);
		rdoByInput.setToggleGroup(tglSaveOptions);
		rdoDescAlpha.setFont(Font.font("Eras Demi ITC", FontWeight.BOLD, 20));
		rdoAscAlpha.setFont(Font.font("Eras Demi ITC", FontWeight.BOLD, 20));
		rdoByInput.setFont(Font.font("Eras Demi ITC", FontWeight.BOLD, 20));

		rdoButtons = new HBox();
		rdoButtons.setSpacing(10);
		rdoButtons.setPadding(new Insets(10));
		rdoButtons.setAlignment(Pos.CENTER);
		rdoButtons.getChildren().addAll(rdoDescAlpha, rdoAscAlpha, rdoByInput);

		topVbox.getChildren().add(Caption);
		BorderPane.setAlignment(Caption, Pos.CENTER);
		bpRoot.setTop(topVbox);

		//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Left BorderPane Editor $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

		leftVbox = new VBox();
		leftVbox.setPrefWidth(300);
		leftVbox.setSpacing(52);
		leftVbox.setBackground(setBackgroundColor(Color.LIGHTGRAY));
		leftVbox.setAlignment(Pos.CENTER_LEFT);

		addProductMenuBtn = new Button("Add New Product");
		searchProductMenuBtn = new Button("Search For Product");
		sendMessageToCustMenuBtn = new Button("Send Message");
		showProfitsMenuBtn = new Button("Show Store's Profits");
		printAllProductMenuBtn = new Button("Print All Products");
		printAllSubscribersMenuBtn = new Button("Print Subscribers List");
		removeProductMenuBtn = new Button("Remove Product From File System");
		removeAllMenuBtn = new Button("Remove All Products From File System");

		leftVbox.getChildren().addAll(addProductMenuBtn, searchProductMenuBtn, sendMessageToCustMenuBtn,
				showProfitsMenuBtn, printAllProductMenuBtn, printAllSubscribersMenuBtn, removeProductMenuBtn,
				removeAllMenuBtn);

		// ########################################## AddProductPane ######################################	
		
		addProductBtn = new Button("Add Product");
		undoLastChange = new Button("Undo The Last Add");
		undoLastChange.setDisable(true);

		HBox buttonsHbox = new HBox(10);

		HBox addProductHBox = new HBox(10);
		VBox productsCapVb = new VBox();
		productsCapVb.setPrefWidth(300);
		productsCapVb.setSpacing(52);
		productsCapVb.setBackground(setBackgroundColor(Color.LIGHTGRAY));

		VBox productsTFieldsVb = new VBox();
		productsTFieldsVb.setPrefWidth(300);
		productsTFieldsVb.setSpacing(42);
		productsTFieldsVb.setBackground(setBackgroundColor(Color.LIGHTGRAY));

		Text[] t = new Text[6];
		tf = new TextField[6];
		for (int i = 0; i < t.length; i++) {
			t[i] = new Text();
			tf[i] = new TextField();
		}

		t[0].setText("Catalog Number:");
		tf[0].setPromptText("Catalog #");
		t[1].setText("Product Name:");
		tf[1].setPromptText("Product Name");
		t[2].setText("Product's Import Price");
		tf[2].setPromptText("Product's Import Price");
		t[3].setText("Product Retail Price");
		tf[3].setPromptText("Product Retail Price");
		t[4].setText("Customer Name");
		tf[4].setPromptText("Customer Name");
		t[5].setText("Customer Phone Number");
		tf[5].setPromptText("Customer Phone #");

		CheckBox subscribeChk = new CheckBox("Subscribe For Promotions?");

		for (int i = 0; i < 6; i++) {
			productsCapVb.getChildren().add(t[i]);
			productsTFieldsVb.getChildren().add(tf[i]);
		}

		productsCapVb.getChildren().add(subscribeChk);
		addProductHBox.getChildren().addAll(productsCapVb, productsTFieldsVb);
		buttonsHbox.getChildren().addAll(addProductBtn, undoLastChange);
		buttonsHbox.setAlignment(Pos.BOTTOM_CENTER);

		addProductMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				bpRoot.setCenter(addProductHBox);
				bpRoot.setRight(buttonsHbox);
				for (int i =0; i<tf.length; i++) {
					tf[i].clear();
				}
				BorderPane.setAlignment(addProductBtn, Pos.BOTTOM_RIGHT);
			}
		});

		addProductBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (ViewToModelListener l : allListeners) {
					try {
						l.addProduct(tf[0].getText(), tf[1].getText(), tf[2].getText(), tf[3].getText(),
								tf[4].getText(), tf[5].getText(), subscribeChk.isSelected());
						undoLastChange.setDisable(false);
					} catch (NumberFormatException e) {
						alertInvalidInput("Price is not a valid Integer");
					} catch (Exception e) {
						alertInvalidInput(e.getMessage());
					}
				}

			}
		});

		undoLastChange.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (ViewToModelListener l : allListeners) {
					l.undoLastChange();
				}
			}
		});

		// ########################################## SearchProductPane ######################################	
		
		searchProductBtn = new Button("Search Product");

		HBox searchProductHBox = new HBox(10);
		VBox searchProductsCapVb = new VBox();
		searchProductsCapVb.setPrefWidth(300);
		searchProductsCapVb.setSpacing(54);
		searchProductsCapVb.setBackground(setBackgroundColor(Color.LIGHTGRAY));
		searchProductsCapVb.setAlignment(Pos.CENTER_LEFT);

		TextField catalogTF = new TextField();

		VBox productsInfoVb = new VBox();
		productsInfoVb.setPrefWidth(300);
		productsInfoVb.setSpacing(53);
		productsInfoVb.setBackground(setBackgroundColor(Color.LIGHTGRAY));
		productsInfoVb.setAlignment(Pos.CENTER_LEFT);

		productsInfoVb.getChildren().add(catalogTF);
		Text[] caps = new Text[7];
		infofield = new Text[6];
		for (int i = 0; i < caps.length; i++) {
			caps[i] = new Text();
		}
		for (int i = 0; i < infofield.length; i++) {
			infofield[i] = new Text("");
		}

		caps[0].setText("Catalog Number:");
		catalogTF.setPromptText("Catalog # To Search");
		caps[1].setText("Product Name:");
		caps[2].setText("Product's Import Price");
		caps[3].setText("Product Retail Price");
		caps[4].setText("Customer Name");
		caps[5].setText("Customer Phone Number");
		caps[6].setText("Subscribed?");

		for (int i = 0; i < caps.length; i++) {
			searchProductsCapVb.getChildren().add(caps[i]);
		}
		for (int i = 0; i < infofield.length; i++) {
			productsInfoVb.getChildren().add(infofield[i]);
		}

		searchProductHBox.getChildren().addAll(searchProductsCapVb, productsInfoVb);

		searchProductMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				catalogTF.clear();
				bpRoot.setCenter(searchProductHBox);
				bpRoot.setRight(searchProductBtn);
				BorderPane.setAlignment(searchProductBtn, Pos.BOTTOM_RIGHT);
			}
		});

		searchProductBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (ViewToModelListener l : allListeners) {
					try {
						l.searchForProduct(catalogTF.getText());
					} catch (NullPointerException e) {
						for (Text t : infofield)
							t.setText(null);
						alertInvalidInput("Product Not Found");
					} catch (Exception e) {
						for (Text t : infofield)
							t.setText(null);
						alertInvalidInput(e.getMessage());
					}
				}

			}
		});
		
		// ########################################## Send Message ######################################
		
		sendMessageToCustBtn = new Button("Send Message");

		sendMessageVbox = new VBox(10);
		HBox sendMessageHbox = new HBox(10);
		Text sendMessageTxt = new Text("Description: ");
		TextField messageTxtField = new TextField();
		messageTxtField.setPromptText("Right Your Message Here");
		messageTxtField.setMinSize(400, 60);

		sendMessageHbox.getChildren().addAll(sendMessageTxt, messageTxtField);
		sendMessageVbox.getChildren().add(sendMessageHbox);
		sendMessageToCustMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				messageTxtField.clear();
				bpRoot.setCenter(sendMessageVbox);
				bpRoot.setRight(sendMessageToCustBtn);
				BorderPane.setAlignment(sendMessageToCustBtn, Pos.BOTTOM_RIGHT);
			}
		});

		sendMessageToCustBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				subscribersVbox = new VBox(10);
				Text msgCaption = new Text("We sent The Message To The Following: \nNotice: Red Colored Couldn't Recieve The Message ");
				msgCaption.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
				messageTxtField.clear();
				subscribersVbox.getChildren().add(msgCaption);
				for (ViewToModelListener l : allListeners) {
					l.sendMSG(messageTxtField.getText());
				}

			}
		});
		
		// ########################################## Store Profit Pane ######################################	

		showProfitsMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				profitScrollPane = new ScrollPane();
				profitView = new GridPane();
				profitView.setHgap(15);
				// profitView.setBackground(setBackgroundColor(Color.NAVAJOWHITE));

				Text GPcatNumCaption = new Text("Catalog Number");
				GPcatNumCaption.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
				GPcatNumCaption.setUnderline(true);
				profitView.add(GPcatNumCaption, 0, 0);

				Text GPProfitCaption = new Text("Profit");
				GPProfitCaption.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
				GPProfitCaption.setUnderline(true);
				profitView.add(GPProfitCaption, 2, 0);

				gPRow = 0;

				for (ViewToModelListener l : allListeners) {
					l.getProfitInfo();
				}

				profitScrollPane.setContent(profitView);
				bpRoot.setRight(null);
				bpRoot.setCenter(profitScrollPane);
			}
		});

		// ########################################## Print All Products ######################################	
		
		printAllProductMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				printAllScrollPane = new ScrollPane();
				allProductsVBox = new VBox(10);

				Text allProductsCap = new Text("\nAll Products In Store:");
				allProductsCap.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
				allProductsCap.setUnderline(true);
				allProductsVBox.getChildren().add(allProductsCap);

				for (ViewToModelListener l : allListeners) {
					l.getAllProducts();
				}

				printAllScrollPane.setContent(allProductsVBox);
				bpRoot.setRight(null);
				bpRoot.setCenter(printAllScrollPane);

			}
		});

		// ########################################## Print Subscribers List ######################################	
		
		printAllSubscribersMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				subscribersVbox = new VBox(10);
				bpRoot.setRight(null);
				bpRoot.setCenter(subscribersVbox);
				for (ViewToModelListener l : allListeners) {
					l.showSubscribers();
				}

			}
		});
		
		// ########################################## Remove Product Pane ######################################	
		
		removeProductBtn = new Button("Remove Product");

		HBox removeHBox = new HBox(10);
		Text removeText = new Text("Catalog Num To Remove: ");
		TextField removeTF = new TextField();

		removeHBox.getChildren().addAll(removeText, removeTF);

		removeProductMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				removeTF.clear();
				bpRoot.setCenter(removeHBox);
				bpRoot.setRight(removeProductBtn);
				BorderPane.setAlignment(searchProductBtn, Pos.BOTTOM_RIGHT);

			}

		});

		removeProductBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (ViewToModelListener l : allListeners) {
					try {
						l.removeProduct(removeTF.getText());
					} catch (Exception e) {
						alertInvalidInput(e.getMessage());
					}
				}

			}
		});

		// ########################################## Remove All Products  ######################################	
		
		removeAllMenuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (ViewToModelListener l : allListeners)
					l.removeAll();

			}
		});

		startProgram = new Button("Start Program");
		startProgram.setMaxSize(200, 100);
		startProgram.setMinSize(200, 100);

		topVbox.getChildren().add(rdoButtons);
		bpRoot.setCenter(startProgram);

		startProgram.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					selected = (RadioButton) tglSaveOptions.getSelectedToggle();
					if (selected == null)
						throw new Exception("No Input Order Has Been Selected");

					startProgram.setVisible(false);
					rdoButtons.setVisible(false);
					bpRoot.setLeft(leftVbox);

					for (ViewToModelListener l : allListeners)
						l.startProgram(selected.getText());
				} catch (Exception e) {
					alertCannotBeEmpty(e.getMessage());
				}
			}
		});

		// Scene Initial
		primaryStage.setScene(new Scene(bpRoot, 1100, 800));
		primaryStage.show();
	}

	// -------------------------------------------------------- View Switchers --------------------------------------------

	public void showProductInfo(String productName, int storePrice, int custPrice, String custName, String phoneNumber,
			boolean subscribedStatus) {
		infofield[0].setText(productName);
		infofield[1].setText(Integer.toString(storePrice));
		infofield[2].setText(Integer.toString(custPrice));
		infofield[3].setText(custName);
		infofield[4].setText(phoneNumber);
		if (subscribedStatus)
			infofield[5].setText("Subscriber");
		else
			infofield[5].setText("Not A Subscriber");
	}

	public void updateProfitTable(String catalogNum, int profitPerProduct) {
		Text cataText = new Text(catalogNum);
		Text ProfiText = new Text(Integer.toString(profitPerProduct));
		cataText.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 16));
		ProfiText.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 16));
		profitView.add(cataText, 0, ++gPRow);
		profitView.add(ProfiText, 2, gPRow);

	}

	public void addSubscriberToView(String custName, String phoneNumber) {
		ScrollPane subsScroll = new ScrollPane();
		subscriberDescription = new Text("Customer Name: " + custName + ", Phone Number: " + phoneNumber);
		if(phoneNumber.compareTo("No Phone Number")==0) {
			subscriberDescription.setStroke(Color.DARKRED);
			subscriberDescription.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 14));
		}else 
			subscriberDescription.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 14));
		subscribersVbox.getChildren().add(subscriberDescription);
		subsScroll.setContent(subscribersVbox);
		bpRoot.setCenter(subsScroll);
	}

	public void addProductToView(String pDescription) {
		Text sTemp = new Text(pDescription);
		sTemp.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		allProductsVBox.getChildren().add(sTemp);

	}

	// -------------------------------------------------------- Alerts ----------------------------------------------------
	
	protected void alertCannotBeEmpty(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initStyle(StageStyle.TRANSPARENT);
		alert.setHeaderText(message);
		alert.setContentText("Please Fill The Designated Fields");
		alert.showAndWait();
	}

	public void alertInvalidInput(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initStyle(StageStyle.TRANSPARENT);
		alert.setHeaderText(message);
		alert.setContentText("Please Try Another Input");
		alert.showAndWait();
	}

	public void addNotification(String notification) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.TRANSPARENT);
		alert.setHeaderText(notification);
		alert.showAndWait();
		for (int i =0; i<tf.length; i++) {
			tf[i].clear();
		}
		
	}

	public void notifyDataRemoved(String msg) {
		bpRoot.setCenter(null);
		bpRoot.setLeft(leftVbox);
		bpRoot.setRight(null);
		undoLastChange.setDisable(true);
		addNotification(msg);


	}

	// -------------------------------------------------------- System ----------------------------------------------------

	public void registerListener(ViewToModelListener newListener) {
		allListeners.add(newListener);
	}

	private Background setBackgroundColor(Color color) {
		Background theBackground = new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
		return theBackground;

	}

	public void setReadStatus(boolean readingFromFile) {
		if (readingFromFile) {
			bpRoot.setCenter(null);
			bpRoot.setLeft(leftVbox);
			topVbox.getChildren().remove(rdoButtons);
		}

	}

}
