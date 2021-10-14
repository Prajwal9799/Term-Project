package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane grid = new GridPane();
			grid.setVgap(5);
			grid.setHgap(5);
			System.out.println("Comment added by me");
			Text title = new Text("SCHEDULE VISUALIZER");
			title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
			grid.add(title, 50, 10);
			TextArea text = new TextArea();
			text.setMinSize(600, 300);
			grid.add(text, 50,20);

			Button submit = new Button("SUBMIT");
			grid.add(submit, 50, 30);
			Scene scene = new Scene(grid,1100,600, Color.SILVER);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			submit.setOnAction((event) -> {
				String getText = text.getText();
				String[] list = getText.split("\\n");
				int count = 12;
				String course = "";
				for (int i = 12; i < list.length; i++) {
					if (count == 12 || count == 23 || count == 34 || count == 45) {
						course += list[i] + "\n";
						course += list[i+1] + "\n";
						course += list[i+5] + "\n";
						course += list[i+6] + "\n";
						course += "\n";
					}
					count++;
				}
				Alert alert = new Alert(Alert.AlertType.NONE);
				alert.setContentText(course);
				alert.show();
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}