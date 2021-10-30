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
 * 
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
	private final Color[] colors= {Color.GREY, Color.GREEN,Color.YELLOW,Color.YELLOWGREEN,Color.BLUE,Color.RED,Color.DARKRED,Color.ORANGE};

	/* Creates a schedule using list of CalendarEvent objects! Pretty neat.
	 * returns a GridPane which you can then render onto scene!
	 * 
	 */
	private GridPane createSchedule(CalendarEvent[] data) {
		Canvas schedule=new Canvas(800,600);
		
		
		GridPane layout=new GridPane();
		layout.setVgap(10);
		layout.setHgap(10);
		
		
		Label titleText=new Label("Class Schedule:");//add title to scene
		Font font=new Font(30);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFont(font);
		layout.add(titleText, 0, 0);
		
		GraphicsContext gc = schedule.getGraphicsContext2D();//gc for chart lines
		gc.setStroke(Color.GREY);
		
		font=new Font(7);
		gc.setFont(font);
		
		gc.setTextBaseline(VPos.CENTER);//sets text for drawing y labels
		gc.setTextAlign(TextAlignment.RIGHT);
		
		for(int i=0;i<25;i++) {//draws chart lines for every hour
			gc.setStroke(Color.GREY);
			gc.strokeLine(50,i*(550/23),800,i*(550/23));
			gc.setStroke(Color.BLACK);
			//draw labels!
			if(i==12) {
				gc.strokeText("12:00 PM" ,50,i*(550/23)+5,50);
			}
			else if(i==24) {
				gc.strokeText("12:00 AM" ,50,i*(550/23)+5,50);
			}
			else if(i<13) {
				gc.strokeText(i+":00 AM" ,50,i*(550/23)+5,50);
			}
			else {
				gc.strokeText((i-12)+":00 PM" ,50,i*(550/23)+5,50);
			}
			
		}
		
		gc.setStroke(Color.BLACK);//sets text alignment for x labels
		gc.setTextBaseline(VPos.TOP);
		gc.setTextAlign(TextAlignment.CENTER);
		font=new Font(15);
		gc.setFont(font);
		
		for(int i=0;i<8;i++) {//draws chart lines for every week
			gc.strokeLine(i*(750/7)+50, 0, i*(750/7)+50, 550);
			
			//draws x labels
			if(i==0) {
				gc.strokeText("Monday",((i*(750/7)+50) + ((i+1)*(750/7)+50)) /2,555,107);
			}
			else if(i==1) {
				gc.strokeText("Tuesday",((i*(750/7)+50) + ((i+1)*(750/7)+50)) /2,555,107);
			}
			else if(i==2) {
				gc.strokeText("Wednesday",((i*(750/7)+50) + ((i+1)*(750/7)+50)) /2,555,107);
			}
			else if(i==3) {
				gc.strokeText("Thursday",((i*(750/7)+50) + ((i+1)*(750/7)+50)) /2,555,107);
			}
			else if(i==4) {
				gc.strokeText("Friday",((i*(750/7)+50) + ((i+1)*(750/7)+50)) /2,555,107);
			}
			else if(i==5) {
				gc.strokeText("Saturday",((i*(750/7)+50) + ((i+1)*(750/7)+50)) /2,555,107);
			}
			else if(i==6) {
				gc.strokeText("Sunday",((i*(750/7)+50) + ((i+1)*(750/7)+50)) /2,555,107);
			}
		}
		
		
		
		int x;
		int y;
		int duration;
		int start;
		
		int colorCount=0;
		//width=107
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		
		Font classFont=new Font(10);//font for class titles
		font=new Font(7);//font for class details
		
		for(CalendarEvent i:data) {//add calendar events to schedule
			if(colorCount>=colors.length) {//switch colors for each event
				colorCount=0;
			}
			gc.setFill(colors[colorCount]);
			
			start=(i.getStartTime())*550/1440;
			duration=(i.getEndTime())*550/1440-start;
			System.out.println(i.getStartTime());
			System.out.println(i.getEndTime());
			
			
			
			for(String day:i.getDays()) {
				if(day.equals("M")) {//draws a box for each event
					x=50;
				}
				else if(day.equals("Tu")) {
					x=157;
				}
				else if(day.equals("W")) {
					x=264;
				}
				else if(day.equals("Th")) {
					x=371;
				}
				else if(day.equals("F")) {
					x=478;
				}
				else if(day.equals("Sa")) {
					x=585;
				}
				else{
					x=692;
				}
				gc.setStroke(Color.BLACK);
				gc.fillRoundRect(x+5,start,107,duration,10,10);
				
				gc.setStroke(Color.DARKGREY);
				
				gc.setFont(classFont);
				gc.strokeText(i.getClassID(),x+5, start, 107);
				
				gc.setFont(font);
				gc.strokeText(i.getClassName(),x+5, start+10, 107);
				gc.strokeText(i.getStartTimeString()+" - "+i.getEndTimeString(),x+5, start+17, 107);
			}
			colorCount++;
		}
		
		layout.add(schedule, 0, 1);
		return layout;
		
	}
	/*
	 * 
	 * 
	 * 
	 * MAIN METHOD OVER HERE \/  \/
	 *runs the code! 
	 * 
	 * 
	 * 
	 * 
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
				for(CalendarEvent c:courses) {
					System.out.println(c);
					System.out.println();
				}
				GridPane schedule=createSchedule(courses);
				
				Scene scheduleScene= new Scene(schedule,1100,800,Color.WHITE);
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
				int count = 0;
				String course = "";
				for (int i = 0; i < list.length; i++) {
					if (i == 0) {
						Course newCourse = new Course(list[i], list[i+1], list[i+5], list[i+6], list[i+8] + " " + list[i+9], list[i+10]);
						newCourse.coursePrint();
					}else if(i == count  + 11) {
						Course newCourse = new Course(list[i], list[i+1], list[i+5], list[i+6], list[i+8] + " " + list[i+9], list[i+10]);
						newCourse.coursePrint();
						count  = count + 11;
					}
					
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}