package TooDooPkg;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinConfirmBox {
	
	
	private static boolean answer;
	
	/*
	 * public static boolean display(String title, String message)
	 * @param title Text that will appear on the window's border.
	 * @param message A message that will appear in the window.
	 * 
	 * */
	
	
	private static final double WINDOW_SIZE_WIDTH = 250.00;
	private static final double WINDOW_SIZE_HEIGHT = 150.00;
	
	public static boolean display(String title, String message, double dParrentPosX, double dParrentPosY, double dParrentWidth, double dParrentHeight){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);	// Prevents user from interacting with the other window while this one is open.
		window.setTitle(title);
		window.setWidth(WINDOW_SIZE_WIDTH);
		window.setHeight(WINDOW_SIZE_HEIGHT);
		window.setResizable(false);
		window.setX(dParrentPosX + (dParrentWidth / 2) - (window.getWidth() / 2));
		window.setY(dParrentPosY + (dParrentHeight / 2) - (window.getHeight() / 2));
		Label label = new Label();
		label.setText(message);
		
		
		
		
		// Create two buttons
		Button yesBtn = new Button("Yes");
		Button noBtn = new Button("No");
		
		
		yesBtn.setOnAction(e -> eventYes(window));
		yesBtn.setOnKeyReleased(e -> eventYes(window, e));
		noBtn.setOnAction(e -> eventNo(window));
		noBtn.setOnKeyReleased(e -> eventNo(window, e));
		
		
		VBox layout = new VBox(10);
		HBox btnLayout = new HBox(24);
		
		btnLayout.getChildren().addAll(yesBtn, noBtn);
		btnLayout.setAlignment(Pos.CENTER);
		
		layout.getChildren().addAll(label, btnLayout);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	} // END
	
	
	private static void eventYes(Stage window){
		answer = true;
		window.close();
	} // END
	
	
	private static void eventYes(Stage window, KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			answer = true;
			window.close();
		}
		else if(event.getCode() == KeyCode.ESCAPE){
			answer = false;
			window.close();
		}
	} // END
	
	
	private static void eventNo(Stage window){
		answer = false;
		window.close();
	} // END
	
	
	private static void eventNo(Stage window, KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			answer = false;
			window.close();
		}
		else if(event.getCode() == KeyCode.ESCAPE){
			answer = false;
			window.close();
		}
	} // END
	
}



