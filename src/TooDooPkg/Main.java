
package TooDooPkg;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import TooDooPkg.WinConfirmBox;
import TooDooPkg.Item;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent>{

	private int two = 2;
	
	private static final int WINDOW_SIZE_WIDTH = 800;
	private static final int WINDOW_SIZE_HEIGHT = 600;
	
	private static final String APPTITLE = "TooDoo";
	private static final String CONTENT_DEFAULT_TEXT_NOITEMS = "<html dir=\"ltr\"><head></head><body contenteditable=\"false\"><p><font face=\"Segoe UI\">Use the Add button to create items.<br /><br />Select an item to view its content.</font></p></body></html>";
	private static final String CONTENT_DEFAULT_TEXT_ITEMS = "<html dir=\"ltr\"><head></head><body contenteditable=\"false\"><p><font face=\"Segoe UI\">Select an item to view its content.</font></p></body></html>";
	
	private static final String FILENAME_DEFAULT = "TooDoo.ser";
	private static final String FILELOCATION_DEFAULT = System.getProperty("user.home")+ System.getProperty("file.separator");
	private static final String FILEPATH_DEFAULT = FILELOCATION_DEFAULT + FILENAME_DEFAULT;
	
	@SuppressWarnings("unused")
	private static final String dummyTitles[] ={"Water the owl.", "Eat the cheese.", "Frim the fram.", "Herd the cats.", "Mow the lawn.", "Pop some caps.", "X all the Y", "Do up the house."};
	
	Stage stageWindow;
	Button btnUp, btnDown, btnAdd, btnRemove, btnSaveContent, btnExit;
	Label labelAppTitle, labelDescription;
	ListView<Item> lvList = new ListView<Item>();
	
	
	WebView webContent = new WebView();
	WebEngine webEng = webContent.getEngine();
	
	//############### Main ###############
	public static void main(String[] args) {
		launch(args);	// Start JavaFX GUI. Calls start() method.
    }

	
	

	
	//############### start - JavaFX prerequisite ###############
	public void start(Stage primaryStage) throws Exception {
		//##### Setup - window #####
		stageWindow = primaryStage;
		stageWindow.setTitle(APPTITLE);
		stageWindow.setWidth(WINDOW_SIZE_WIDTH);
		stageWindow.setHeight(WINDOW_SIZE_HEIGHT);
		stageWindow.setResizable(false);
		stageWindow.setOnCloseRequest(e -> {
			e.consume();					// Alerts system that we will handle the event.
			eventCloseProgram();					// Call our custom eventCloseProgram() method.
		});
		
		
		
		//##### Setup - Button - Creation #####
		btnAdd = new Button("Add");
		btnRemove = new Button("Remove");
		btnExit = new Button("Exit");
		btnUp = new Button(" ^ ");
		btnDown = new Button(" v ");
		
		//##### Setup - Button - Event-Handling #####
		btnAdd.setOnAction(e -> eventAddItem());
		btnAdd.setOnKeyReleased(e -> eventAddItem(e));
		btnRemove.setOnAction(e -> eventRemoveItem());
		btnRemove.setOnKeyReleased(e -> eventRemoveItem(e));
		btnExit.setOnAction(e -> eventCloseProgram());
		btnExit.setOnKeyReleased(e -> eventCloseProgram(e));
		btnUp.setOnAction(e -> eventListItemMoveUp());
		btnUp.setOnKeyReleased(e -> eventListItemMoveUp(e));
		btnDown.setOnAction(e -> eventListItemMoveDown());
		btnDown.setOnKeyReleased(e -> eventListItemMoveDown(e));
		
		//##### Setup - WebViewer/WebEngine #####
		webContent.setPrefWidth(WINDOW_SIZE_WIDTH / 2);
		webContent.setOnMouseClicked(e -> eventUpdateContent(e));
		webContent.setOnKeyReleased(e -> eventUpdateContent(e));
		if(lvList.getItems().isEmpty()){
			webEng.loadContent(CONTENT_DEFAULT_TEXT_NOITEMS);
		}
		else{
			webEng.loadContent(CONTENT_DEFAULT_TEXT_ITEMS);
		}
		
		//##### Setup - Item list #####
		lvList.setPrefWidth(WINDOW_SIZE_WIDTH / 2);
		lvList.setOnMouseClicked(e -> eventUpdateListItems(e));
		lvList.setOnKeyReleased(e -> eventUpdateListItems(e));
		
		//##### Setup - Node Tooltips #####
		
		Tooltip ttipLvList = new Tooltip("Click \"Add\" to create a new entry.\nDouble-click any entry to modify.");
		Tooltip ttipWebContent = new Tooltip("Select an entry in the list to view its contents.\nDouble-click to modify currently-selected item's content.");
		
		ttipLvList.setWrapText(true);
		ttipWebContent.setWrapText(true);
		
		Tooltip.install(lvList, ttipLvList);
		Tooltip.install(webContent, ttipWebContent);
				
		
		
		//####################################################################################################
		
		//##### Setup - GUI Elements #####

		HBox hboxUPLEFT = new HBox();
		hboxUPLEFT.getChildren().addAll(btnUp, btnDown, btnAdd, btnRemove);
		hboxUPLEFT.setAlignment(Pos.TOP_CENTER);
		
		HBox hboxUPRIGHT = new HBox();
		hboxUPRIGHT.getChildren().addAll(btnExit);
		hboxUPRIGHT.setAlignment(Pos.TOP_RIGHT);
		
		VBox vboxLeft = new VBox();
		vboxLeft.getChildren().addAll(hboxUPLEFT, lvList);
		vboxLeft.setAlignment(Pos.TOP_LEFT);
		vboxLeft.setPrefWidth(WINDOW_SIZE_WIDTH / 2);
		
		VBox vboxRight = new VBox();
		vboxRight.getChildren().addAll(hboxUPRIGHT, webContent);
		vboxRight.setAlignment(Pos.TOP_LEFT);
		vboxRight.setPrefWidth(WINDOW_SIZE_WIDTH / 2);
		
		HBox hboxMain = new HBox();
		hboxMain.getChildren().addAll(vboxLeft, vboxRight);
		
		//####################################################################################################
		
		Scene scene = new Scene(hboxMain, WINDOW_SIZE_WIDTH, WINDOW_SIZE_HEIGHT);
		stageWindow.setScene(scene);
		stageWindow.show();
		
		
		
		generateListItems(lvList);
		
	} // END
	
	
	private void eventAddItem(){
		addItem();
	} // END
	
	
	private void eventAddItem(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			addItem();
		}
		else if(event.getCode() == KeyCode.ESCAPE){
			closeProgram();
		}
	} // END
	
	
	private void addItem(){
		lvList.getItems().add(new Item());
		lvList.getSelectionModel().selectLast();
		// Change title of selected ListView Item via WinTextBox popup window.
		String strTemp = WinTextField.display("Change Title", "Please enter a new title:", stageWindow.getX(), stageWindow.getY(), stageWindow.getWidth(), stageWindow.getHeight());
		if(strTemp.length() > 0){
			lvList.getSelectionModel().getSelectedItem().setTitle(strTemp);
			// Update the ListView's displayed item.
			lvList.getItems().set(lvList.getSelectionModel().getSelectedIndex(), lvList.getSelectionModel().getSelectedItem());
		}
	}
	
	
	private void eventRemoveItem(){
		removeItem();
	} // END
	
	
	private void eventRemoveItem(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			removeItem();
		}
		else if(event.getCode() == KeyCode.ESCAPE){
			closeProgram();
		}
	} // END
	
	
	private void removeItem(){
		boolean answer = WinConfirmBox.display("Confirmation", "Really remove this item?", stageWindow.getX(), stageWindow.getY(), stageWindow.getWidth(), stageWindow.getHeight());
		if(answer){
			lvList.getItems().remove(lvList.getSelectionModel().getSelectedIndex());
		}
	} // END
	
	
	private void eventCloseProgram(){
		closeProgram();
	} // END
	
	
	private void eventCloseProgram(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			closeProgram();
		}
	} // END
	
	
	private void closeProgram(){
		boolean answer = WinConfirmBox.display("Confirmation", "Are you sure you wish to exit?", stageWindow.getX(), stageWindow.getY(), stageWindow.getWidth(), stageWindow.getHeight());
		if(answer){
			stageWindow.close();
			try {
				ArrayList<Item> tempItems = new ArrayList<Item>();
				
				for(int i=0; i<lvList.getItems().size(); i++){
					tempItems.add(lvList.getItems().get(i));
				}
				SerializationUtil.serialize(tempItems, FILEPATH_DEFAULT);
				System.out.println("File saved successfully!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} // END
	
	
	private void eventUpdateListItems(MouseEvent event){		
		if(event.getClickCount() > 1){
			updateListItems();
		}
		else{
			if(lvList.getSelectionModel().getSelectedIndex() >= 0){
				webEng.loadContent(lvList.getSelectionModel().getSelectedItem().getContent());
			}
			else{
				if(lvList.getItems().isEmpty()){
					webEng.loadContent(CONTENT_DEFAULT_TEXT_NOITEMS);
				}
				else{
					webEng.loadContent(CONTENT_DEFAULT_TEXT_ITEMS);
				}
			}
		} 
		
	} // END
	
	
	private void eventUpdateListItems(KeyEvent event){		
		if(event.getCode() == KeyCode.ENTER){
			updateListItems();
		}
		else if((event.getCode() == KeyCode.UP) || (event.getCode() == KeyCode.DOWN)){
			if(lvList.getSelectionModel().getSelectedIndex() >= 0){
				webEng.loadContent(lvList.getSelectionModel().getSelectedItem().getContent());
			}
		}
		else if(event.getCode() == KeyCode.ESCAPE){
			closeProgram();
		}
		else if((event.getCode() == KeyCode.DELETE) || (event.getCode() == KeyCode.BACK_SPACE)){
			if(lvList.getSelectionModel().getSelectedIndex() >= 0){
				removeItem();
			}
			
		}
	} // END
	
	
	private void updateListItems(){
		if(lvList.getSelectionModel().getSelectedIndex() >= 0){
			// Change title of selected ListView Item via WinTextBox popup window.
			String strTemp = WinTextField.display("Change Title", "Please enter a new title:", stageWindow.getX(), stageWindow.getY(), stageWindow.getWidth(), stageWindow.getHeight());
			if(strTemp.length() > 0){
				lvList.getSelectionModel().getSelectedItem().setTitle(strTemp);
				// Update the ListView's displayed item.
				lvList.getItems().set(lvList.getSelectionModel().getSelectedIndex(), lvList.getSelectionModel().getSelectedItem());
			}
			
		}
	} // END

	
	private void eventListItemMoveUp(){
		listItemMoveUp();
	} // END
	
	
	private void eventListItemMoveUp(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			listItemMoveUp();
		}
		else if(event.getCode() == KeyCode.ESCAPE){
			closeProgram();
		}
	} // END
	
	
	private void listItemMoveUp(){
		if(lvList.getSelectionModel().getSelectedIndex() > 0){
			Item tempItem = lvList.getItems().set(lvList.getSelectionModel().getSelectedIndex() - 1, lvList.getSelectionModel().getSelectedItem());
			lvList.getItems().set(lvList.getSelectionModel().getSelectedIndex(), tempItem);
			// Update the selection index
			lvList.getSelectionModel().select(lvList.getSelectionModel().getSelectedIndex() - 1);
		}
	} // END
	
	
	private void eventListItemMoveDown(){
		listItemMoveDown();
	} // END
	
	
	private void eventListItemMoveDown(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			listItemMoveDown();
		}
		else if(event.getCode() == KeyCode.ESCAPE){
			closeProgram();
		}
	} // END
	
	
	private void listItemMoveDown(){
		if((lvList.getSelectionModel().getSelectedIndex() < (lvList.getItems().size() - 1)) && (lvList.getSelectionModel().getSelectedIndex() >= 0)){
			Item tempItem = lvList.getItems().set(lvList.getSelectionModel().getSelectedIndex() + 1, lvList.getSelectionModel().getSelectedItem());
			lvList.getItems().set(lvList.getSelectionModel().getSelectedIndex(), tempItem);
			// Update the selection index
			lvList.getSelectionModel().select(lvList.getSelectionModel().getSelectedIndex() + 1);
		}
	} // END

	
	private void eventUpdateContent(MouseEvent event){
		if(event.getClickCount() > 1){
			updateContent();
		}
	} // END
	
	
	private void eventUpdateContent(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			updateContent();
		}
		else if(event.getCode() == KeyCode.ESCAPE){
			closeProgram();
		}
	} // END
	
	
	private void updateContent(){
		if(lvList.getSelectionModel().getSelectedIndex() >= 0){
			String tempStr = WinHTMLEditor.display(lvList.getSelectionModel().getSelectedItem().getContent(), stageWindow.getX(), stageWindow.getY(), stageWindow.getWidth(), stageWindow.getHeight());
			
			if(tempStr.length() > 0){
				lvList.getSelectionModel().getSelectedItem().setContent(tempStr);
			}

			webEng.loadContent(lvList.getSelectionModel().getSelectedItem().getContent());
		}
	} // END
	
	
	
	/*
	 * ############################################################################################################ 
	 * */
	
	@SuppressWarnings("unchecked")
	private void generateListItems(ListView<Item> list){
		//for(int i=0; i<(dummyTitles.length - 1); i++){
		//	list.getItems().add(new Item(dummyTitles[i], dummyTitles[i]));
		//}
		
		File tempFile = new File(FILEPATH_DEFAULT);
		
		if(tempFile.exists()){
			try {
				ArrayList<Item> tempItems = new ArrayList<Item>();
				tempItems = (ArrayList<Item>)SerializationUtil.deserialize(FILEPATH_DEFAULT);
				for(int i=0; i<tempItems.size(); i++){
					list.getItems().add(tempItems.get(i));
					System.out.println(list.getItems().get(i).getTitle());
				}
				System.out.println("File loaded successfully!");
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("File not found!");
			System.out.println("Filepath checked:\n\t" + FILEPATH_DEFAULT);
		}
		

		
		
	} // END
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	} // END

}
