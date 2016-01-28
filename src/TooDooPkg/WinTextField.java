package TooDooPkg;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinTextField {

	private static double WINDOW_SIZE_WIDTH = 250.0;
	private static double WINDOW_SIZE_HEIGHT = 150.0;
	
	private static String sText = "";
	
	public static String display(String title, String message, double dParrentPosX, double dParrentPosY, double dParrentWidth, double dParrentHeight){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);	// Prevents user from interacting with the other window while this one is open.
		window.setTitle(title);
		window.setWidth(WINDOW_SIZE_WIDTH);
		window.setHeight(WINDOW_SIZE_HEIGHT);
		window.setX(dParrentPosX + (dParrentWidth / 2) - (window.getWidth() / 2));
		window.setY(dParrentPosY + (dParrentHeight / 2) - (window.getHeight() / 2));
		Label label = new Label();
		label.setText(message);
		
		TextField tfText = new TextField();
		
		
		Button btnEnter = new Button("Enter");
		Button btnCancel = new Button("Cancel");
		
		
		btnEnter.setOnAction(e -> {
			sText = tfText.getText();
			window.close();
		});
		btnEnter.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				sText = tfText.getText();
				window.close();
			}
			else if(e.getCode() == KeyCode.ESCAPE){
				window.close();
			}
		});
		
		
		btnCancel.setOnAction(e -> {
			window.close();
		});
		btnCancel.setOnKeyReleased(e -> {
			if((e.getCode() == KeyCode.ENTER) || (e.getCode() == KeyCode.ESCAPE)){
				window.close();
			}
		});
		
		
		tfText.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				sText = tfText.getText();
				window.close();
			}
			else if(e.getCode() == KeyCode.ESCAPE){
				window.close();
			}
		});
		
		
		HBox hboxButtons = new HBox(24);
		VBox vboxText = new VBox();
		VBox vboxMain = new VBox(16);
		
		vboxText.getChildren().addAll(label, tfText);
		
		hboxButtons.getChildren().addAll(btnEnter, btnCancel);
		hboxButtons.setAlignment(Pos.CENTER);
		
		vboxMain.getChildren().addAll(vboxText, hboxButtons);
		
		Scene scene = new Scene(vboxMain);
		window.setScene(scene);
		window.showAndWait();
		
		return sText;
	}
}
