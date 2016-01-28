package TooDooPkg;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinHTMLRawViewer {

	private static final int WINDOW_SIZE_WIDTH = 625;
	private static final int WINDOW_SIZE_HEIGHT = 300;
	private static final int LAYOUT_VERTICAL_PADDING = 10;
	
	
	public static void display(String sInputHTML, double dParrentPosX, double dParrentPosY, double dParrentWidth, double dParrentHeight){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);	// Prevents user from interacting with the other window while this one is open.
		window.setTitle("Raw HTML (View-only)");
		window.setWidth(WINDOW_SIZE_WIDTH);
		window.setHeight(WINDOW_SIZE_HEIGHT);
		window.setX(dParrentPosX);
		window.setY(dParrentPosY);
		
		Label label = new Label("Cannot edit. Text-wrapping active.\nParameter \"contenteditable\" is set to false when the text is saved.");
		label.setWrapText(true);
		
		Button btnClose = new Button("Close");
		btnClose.setOnAction(e -> {
			window.close();
		});
		btnClose.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ESCAPE){
				window.close();
			}
		});
		
		TextArea taText = new TextArea();		
		taText.setEditable(false);
		taText.setWrapText(true);
		
		Tooltip tooltip = new Tooltip("View-only.");
		Tooltip.install(taText, tooltip);
		
		taText.setText(sInputHTML); 
		
		
		
		taText.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ESCAPE){
				window.close();
			}
		});
		
		
		
		
		VBox vboxMain = new VBox(LAYOUT_VERTICAL_PADDING);
		vboxMain.getChildren().addAll(label, taText, btnClose);
		vboxMain.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(vboxMain);
		window.setScene(scene);
		window.showAndWait();
	}
}
