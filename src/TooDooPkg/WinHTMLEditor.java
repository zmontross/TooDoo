package TooDooPkg;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinHTMLEditor {

	private static final int WINDOW_BORDER_PADDING = 10;
	private static final int WINDOW_SIZE_WIDTH = 625;
	private static final int WINDOW_SIZE_HEIGHT = 600;
	private static final int LAYOUT_HORIZONTAL_PADDING = 24;
	private static final int LAYOUT_VERTICAL_PADDING = 10;
	
	private static final String HTML_CONTENTEDITABLE_TRUE = "contenteditable=\"true\"";
	private static final String HTML_CONTENTEDITABLE_FALSE = "contenteditable=\"false\"";
	
	private static String sText = "";
	
	public static String display(String sExistingHTMLText, double dParrentPosX, double dParrentPosY, double dParrentWidth, double dParrentHeight){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);	// Prevents user from interacting with the other window while this one is open.
		window.setTitle("HTML Editor");
		window.setWidth(WINDOW_SIZE_WIDTH + WINDOW_BORDER_PADDING);
		window.setHeight(WINDOW_SIZE_HEIGHT);
		window.setX(dParrentPosX + (dParrentWidth / 2) - (WINDOW_SIZE_WIDTH / 2));
		window.setY(dParrentPosY);
		
		HTMLEditor htmlEditor = new HTMLEditor();
		htmlEditor.setPrefWidth(WINDOW_SIZE_WIDTH);
		htmlEditor.setHtmlText(sExistingHTMLText);
		
		// Create two buttons
		Button btnEnter = new Button("Enter");
		Button btnCancel = new Button("Cancel");
		Button btnViewRawHTML = new Button("View Raw HTML");
		
		
		btnEnter.setOnAction(e -> {
			
			sText = htmlEditor.getHtmlText();
			
			if(sText.contains(HTML_CONTENTEDITABLE_TRUE)){
				sText = sText.replace(HTML_CONTENTEDITABLE_TRUE, HTML_CONTENTEDITABLE_FALSE);
			}
			
			window.close();
		});
		btnEnter.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				sText = htmlEditor.getHtmlText();
				
				if(sText.contains(HTML_CONTENTEDITABLE_TRUE)){
					sText = sText.replace(HTML_CONTENTEDITABLE_TRUE, HTML_CONTENTEDITABLE_FALSE);
				}
				
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
		
		
		btnViewRawHTML.setOnAction(e ->{
			WinHTMLRawViewer.display(htmlEditor.getHtmlText(), window.getX(), window.getY(), window.getWidth(), window.getHeight());
		});
		btnViewRawHTML.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				WinHTMLRawViewer.display(htmlEditor.getHtmlText(), window.getX(), window.getY(), window.getWidth(), window.getHeight());
				window.close();
			}
			else if(e.getCode() == KeyCode.ESCAPE){
				window.close();
			}
		});
		
		
		htmlEditor.setOnKeyReleased(e -> {
			if(e.isControlDown() && (e.getCode() == KeyCode.ENTER)){
				btnEnter.requestFocus();
			}
			else if(e.getCode() == KeyCode.ESCAPE){
				window.close();
			}
		});
		
		
		HBox hboxButtons = new HBox(LAYOUT_HORIZONTAL_PADDING);
		hboxButtons.setAlignment(Pos.CENTER);
		VBox vboxMain = new VBox(LAYOUT_VERTICAL_PADDING);
		
		hboxButtons.getChildren().addAll(btnEnter, btnViewRawHTML, btnCancel);
		vboxMain.getChildren().addAll(htmlEditor, hboxButtons);
		
		Scene scene = new Scene(vboxMain);
		window.setScene(scene);
		window.showAndWait();
		
		return sText;
	}
}
