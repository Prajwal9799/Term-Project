/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * Use this as sample input:
CSC-305-02
Software Development
4.00000
08/30/21
12/10/21
Tu Th 
10:25AM - 12:05PM

OLIN
209
Khan Mohd, Tauheed 
CSC-371-01
Algorithms & Comp Theory
4.00000
08/30/21
12/10/21
M W F 
10:30AM - 11:45AM

OLIN
209
Jahan, Labiba 
DATA-331-01
Intro to MIS
4.00000
08/30/21
12/10/21
Tu Th 
8:30AM - 10:10AM

OLIN
109
Clayton, Terry W.
MATH-330-01
Prob & Stats
4.00000
08/30/21
12/10/21
M W F 
1:30PM - 2:45PM

OLIN
201
Bashar, A K M Raquib

OLM
201
Richard, John J.
PHIL-350
Philosophy of Everything Known on Earth Including Cats and Dogs Holy Freaking Cow This is a Long Title But I Gotta Test TextBox Am I Right? So Just Check Out This Poetry As I Write Out a Few More Lines of Text To Be Tested By This TextBox.
4.00
08/30/21
12/10/21
F Su
6:00AM - 9:00AM
 * 
 * 
 * 
 * Sources:
 * 
 * For Schedule fomatting with GridPane
 * https://stackoverflow.com/questions/40960788/how-to-make-buttons-span-mutiple-columns-rows-with-gridpane-javafx
 * 
 * Text formatting on Canvas--using FontMetrics
 * https://stackoverflow.com/questions/32237048/javafx-fontmetrics/32238954#32238954
 */
package application;
import application.CalendarEvent;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.canvas.*;

import javafx.scene.shape.Path;

public class Main extends Application {
	/*
	 * starts program. Gets primary input for creating calendar, creates it with data taken as list of CalendaEVents, and
	 * then displays it.
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane grid = new GridPane();
			grid.setVgap(5);
			grid.setHgap(5);
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
				String[] list = getText.split("\n\n");
				String course = "";
				CalendarEvent[] courses=new CalendarEvent[list.length];
				for (int i = 0; i < list.length; i++) {
					courses[i]=new CalendarEvent(list[i]);
				}
				//System.out.println(courses);
				
				Schedule schedule=new Schedule(courses,Color.BLUE,Color.LIGHTBLUE,1100.0,700.0);
				GridPane scheduleGridPane=schedule.getSchedule();
				
				Scene scheduleScene= new Scene(scheduleGridPane,1100,800,Color.WHITE);
				primaryStage.setScene(scheduleScene);
				primaryStage.show();
				/*
				Alert alert = new Alert(Alert.AlertType.NONE);
				alert.setContentText(course);
				alert.show();*/
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}