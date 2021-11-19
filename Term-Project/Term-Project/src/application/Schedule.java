package application;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Schedule{
	//Canvas schedule=new Canvas(800,600);
	private final Color[] defaultEventColors= {Color.GREY, Color.GREEN,Color.YELLOW,Color.YELLOWGREEN,Color.RED,Color.DARKRED,Color.ORANGE};
	private GridPane scheduleGridPane;
	
	/*
	 * Creates a Schedule with list of CalendarEvents. A schedule creates a gridpane,
	 * a  gridpane which has a Canvas in the bottom, representing schedule as a grid
	 * 
	 * @param data -- list of events which will be plotted on schedule
	 * @param background -- a Color object meant to set the background
	 * @param gridBackground -- a color object meant to set backgound of grid with calendar events
	 * @param width -- represents width of schedule
	 * @param height -- represents height of schedule
	 * 
	 */
	public Schedule(CalendarEvent[] data,Color background,Color gridBackground,double width,double height) {
			Canvas schedule=new Canvas(width,height);
			
			GridPane layout=new GridPane();
			//fill gridpane background
			layout.setBackground(new Background(new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));
			
			layout.setVgap(10);
			layout.setHgap(10);
			layout.setAlignment(Pos.BASELINE_CENTER);
			
			
			Label titleText=new Label("Class Schedule:");//add title to scene
			//Font font=new Font(30);
			Font font =new Font(height*.10);
			titleText.setTextAlignment(TextAlignment.CENTER);
			titleText.setFont(font);
			layout.add(titleText, 0, 0);
			
			GraphicsContext gc = schedule.getGraphicsContext2D();//gc for chart lines
			
			gc.setFill(background);
			gc.fillRect(0, 0, width, height);//paint background of canvas
			
			gc.setFill(gridBackground);//paint background of chart
			gc.fillRect(.08*width,0,width,.92*height);
			gc.setStroke(Color.GREY);
			
			font=new Font(.012*height);
			gc.setFont(font);
			
			gc.setTextBaseline(VPos.CENTER);//sets text for drawing y labels
			gc.setTextAlign(TextAlignment.RIGHT);
			
			for(int i=0;i<25;i++) {//draws chart lines for every hour
				gc.setStroke(Color.GREY);
				gc.strokeLine(.08*width, i*((height*.92)/24), width, i*((height*.92)/24));
				gc.setStroke(Color.BLACK);
				//draw labels!
				if(i==0) {
					gc.strokeText("12:00 AM", .08*width,i*((height*.92)/24)+5,50);
				}
				if(i==12) {
					gc.strokeText("12:00 PM" ,.08*width,i*((height*.92)/24)+5,50);
				}
				else if(i==24) {
					gc.strokeText("12:00 AM" ,.08*width,i*((height*.92)/24)+5,50);
				}
				else if(i<13) {
					gc.strokeText(i+":00 AM" ,.08*width,i*((height*.92)/24)+5,50);
				}
				else {
					gc.strokeText((i-12)+":00 PM" ,.08*width,i*((height*.92)/24)+5,50);
				}
				
			}
			
			gc.setStroke(Color.BLACK);//sets text alignment for x labels
			gc.setTextBaseline(VPos.TOP);
			gc.setTextAlign(TextAlignment.CENTER);
			font=new Font(15);
			gc.setFont(font);
			
			for(int i=0;i<8;i++) {//draws chart lines for every week
				gc.strokeLine(i*(width*.92/7)+.08*width, 0, i*(width*.92/7)+.08*width, height*.92);
				
				//draws x labels
				if(i==0) {
					gc.strokeText("Monday",((i*(width*.92/7)+width*.08) + ((i+1)*(width*.92/7)+width*.08)) /2,height*.93,107);
				}
				else if(i==1) {
					gc.strokeText("Tuesday",((i*(width*.92/7)+width*.08) + ((i+1)*(width*.92/7)+width*.08)) /2,height*.93,107);
				}
				else if(i==2) {
					gc.strokeText("Wednesday",((i*(width*.92/7)+width*.08) + ((i+1)*(width*.92/7)+width*.08)) /2,height*.93,107);
				}
				else if(i==3) {
					gc.strokeText("Thursday",((i*(width*.92/7)+width*.08) + ((i+1)*(width*.92/7)+width*.08)) /2,height*.93,107);
				}
				else if(i==4) {
					gc.strokeText("Friday",((i*(width*.92/7)+width*.08) + ((i+1)*(width*.92/7)+width*.08)) /2,height*.93,107);
				}
				else if(i==5) {
					gc.strokeText("Saturday",((i*(width*.92/7)+width*.08) + ((i+1)*(width*.92/7)+width*.08)) /2,height*.93,107);
				}
				else if(i==6) {
					gc.strokeText("Sunday",((i*(width*.92/7)+width*.08) + ((i+1)*(width*.92/7)+width*.08)) /2,height*.93,107);
				}
			}
			
			
			
			double x;
			double y;
			double duration;
			double start;
			
			int colorCount=0;
			//width=107
			gc.setTextAlign(TextAlignment.LEFT);
			gc.setTextBaseline(VPos.TOP);
			
			Font classFont=new Font(10);//font for class titles
			font=new Font(7);//font for class details
			
			for(CalendarEvent i:data) {//add calendar events to schedule
				if(colorCount>=defaultEventColors.length) {//switch colors for each event
					colorCount=0;
				}
				gc.setFill(defaultEventColors[colorCount]);
				
				start=(i.getStartTime())*(height*.92)/1440;
				duration=(i.getEndTime())*(height*.92)/1440-start;

				
				
				
				for(String day:i.getDays()) {
					
					if(day.equals("M")) {//draws a box for each event
						x=.08*width;
					}
					else if(day.equals("Tu")) {
						x=.08*width+(.92*width)/7;
					}
					else if(day.equals("W")) {
						x=.08*width+2*(.92*width)/7;
					}
					else if(day.equals("Th")) {
						x=.08*width+3*(.92*width)/7;
					}
					else if(day.equals("F")) {
						x=.08*width+4*(.92*width)/7;
					}
					else if(day.equals("Sa")) {
						x=.08*width+5*(.92*width)/7;
					}
					else{
						x=.08*width+6*(.92*width)/7;
					}
					/*
					gc.setStroke(Color.BLACK);
					gc.fillRoundRect(x+5,start,107,duration,10,10);
					
					gc.setStroke(Color.DARKGREY);
					
					gc.setFont(classFont);
					gc.strokeText(i.getClassID(),x+5, start, 107);
					
					gc.setFont(font);
					gc.strokeText(i.getClassName(),x+5, start+10, 107);
					gc.strokeText(i.getStartTimeString()+" - "+i.getEndTimeString(),x+5, start+17, 107);
					*/
					/*
					 * parameters go:
					 * 
					 * text to be inseted,
					 * 
					 * top left x,
					 * 
					 * top lefty,
					 * 
					 * width of textbox,
					 * 
					 * height of textbox,
					 * 
					 * desired font,
					 * 
					 * Desired title font size,
					 * 
					 * Desired body font size
					 */
					TextBox textEvent=new TextBox(i.toString(), x, start, (.92*width/7), duration, "Arial", 10, 8);
					textEvent.fillBox(gc);
					gc.setStroke(Color.BLACK);
					
				}
				colorCount++;
			}
			
			layout.add(schedule, 0, 1);
			scheduleGridPane=layout;
	}
	Schedule(CalendarEvent[] data,double width,Double height){
		this(data,Color.LIGHTBLUE,Color.DARKGREEN,width,height);
	}
	/*
	 * returns GridPane object which can be rendered on a scene
	 * 
	 */
	public GridPane getSchedule() {
		return scheduleGridPane;
	}
			
}
