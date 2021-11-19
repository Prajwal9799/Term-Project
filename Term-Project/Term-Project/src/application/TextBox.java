package application;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.*;

public class TextBox {
	/*
	 * makes a textbox that shrinks to a minimum font, if it still can't fit everything within a line
	 *String text takes a string paragraph, splits it by linespaces. First line is the title of text. Rest is body. If body and title fit at set font, title will shrink to min font. if most of body can fit, will take lines out until it fits 
	 */
	private String[] t;
	
	private String font;
	
	double titleSize;
	double bodySize;
	
	double w;
	double h;
	
	double y;
	double x;
	
	/*
	 * creates a textbox which can be drawn on a canvas using draw(GraphicsController)
	 * 
	 * If TextBox has longer width than specefied width, creates a line break.
	 * 
	 * If TextBox has longer height than specefied height after width is accounted for,
	 * takes away lines until just title fits, if still too big, shrinks font
	 * 
	 * 
	 * 
	 * 
	 * @param text is text to be drawn
	 * @param xcoord -- is x coord of upper left corner of TextBox
	 * @param ycoord -- is y coord of upper left corner of TextBox
	 * @param width -- is width of TextBox
	 * @param height -- is height of TextBox
	 * @param fontName -- is name of font to be used-- "arial", "times new roman", etc.
	 * @param maxTitle -- is max font size of title before being resized
	 * @param maxBody -- max font size of body
	 */
	public TextBox(String text,double xCoord,double yCoord,double width,double height,String fontName,double maxTitle,double maxBody) {
		t=text.split("\n");
		Text l;
		Font f;
		
		w=width;
		h=height;
		
		font=fontName;
		
		titleSize=maxTitle;
		bodySize=maxBody;
		
		
		
		x=xCoord;
		y=yCoord;
		
		formatText(maxTitle,maxBody);
		
	}
	public TextBox(String text,double width,double height,String fontName, double maxTitle, double maxBody) {
		this(text,0,0,width,height,fontName,maxTitle,maxBody);
	}
	public TextBox(String text,double width,double height,double maxTitle,double maxBody) {
		this(text,0,0,width,height,"Arial",maxTitle,maxBody);
	}
	public TextBox(String text,double xCoord,double yCoord,double width,double height,double maxTitle,double maxBody) {
		this(text,xCoord,yCoord,width,height,"Arial",maxTitle,maxBody);
	}
	
	/*
	 * draws textbox, which will render on whatever GraphicsController -> Canvas
	 * that it is put on
	 * 
	 * @param gc -- GraphicsController being drawn on
	 */
	public void strokeBox(GraphicsContext gc) {
		Font titleFont=new Font(font,titleSize);
		
		Font bodyFont=new Font(font,bodySize);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.CENTER);
		double titleVSpacing=titleSize*.4;
		double bodyVSpacing=bodySize*.1;
		for(int i=0;i<t.length;i++) {
			
			if(i==0) {
				gc.setFont(titleFont);
				gc.strokeText(t[i], 1+x, titleVSpacing+y);
				
			}
			else {
				gc.setFont(bodyFont);
				gc.strokeText(t[i], 1+x, bodyVSpacing+y+(i-1)*(bodySize+2)+titleSize+2);
			}
		}
	}
	/*
	 * fills a rounded rectangle around textbox before drawing it
	 * @pram gc -- GraphicsController that textbox is being drawn on
	 */
	public void fillBox(GraphicsContext gc) {
		gc.fillRoundRect(x,y,w,h,10,10);
		strokeBox(gc);
	}
	/*Formats text box. If any lines greater than specified length,
	 * makes a line breaks until this is no longer the case.
	 * Then takes lines out if too big of height.
	 * In last case shrinks title font
	 * 
	 * @param maxTitle is the max specified titleFont
	 * @param maxBody is the specfied max body font
	 */
	public void formatText(double maxTitle,double maxBody) {
		boolean widthFormatted=false;
		Font titleFont=new Font(font,maxTitle);
		Font bodyFont=new Font(font,maxBody);
		
		Text title=new Text();
		Text body=new Text();
		
		title.setFont(titleFont);
		body.setFont(bodyFont);

	
		int i=0;
		int listLength=t.length;
		while(i<listLength) {
			if(i==0) {//formats title by moving down a line until text is able to fit width of title
				title.setText(t[i]);
				if(title.getLayoutBounds().getWidth()>w) {//if title greater than set width, move down a line
					t=newLine(t,i);
					title.setText(t[i]);
					while(title.getLayoutBounds().getWidth()>w) {//continue moving chars down while title is still longer than set width
						t=charDownLine(t,i);
						title.setText(t[i]);
					}
				}
			}
			else {
				body.setText(t[i]);
				if(body.getLayoutBounds().getWidth()>w) {//if line greater than set width make new line break
					t=newLine(t,i);
					body.setText(t[i]);
					while(body.getLayoutBounds().getWidth()>w) {//continue moving chars down while line is longer than width
						t=charDownLine(t,i);
						body.setText(t[i]);
					}
				}
			}
			listLength=t.length;
			i++;
		}
		
		
		while(getTextHeight()>h) {//while estimated height is still greater than specified height, pops lines until it has to shrink title size down to match respected height
			if(t.length>1) {
				t=popLine(t);
			}
			else {
				titleSize--;
			}
		}
		
	}
	
	/*
	 * gets a rough estimate of textbox height while accounting for spacing
	 */
	private double getTextHeight() {
		double titleVSpacing=titleSize*.4;
		double bodyVSpacing=bodySize*.1;
		return (titleVSpacing+titleSize + (t.length-1)*(bodySize) + (t.length-1*bodyVSpacing));
	}
	
	public String getLine(int line) {
		return t[line];
	}
	/*
	 * removes last line from list of strings representing lines
	 */
	public String[] popLine(String[]s) {
		String[] newS=new String[s.length-1];
		for(int i=0;i<newS.length;i++) {
			newS[i]=s[i];
		}
		return newS;
	}
	/*takes a list of strings, and moves a char down from one line to the next. DOES NOT WORK WHEN CREATING NEW LINES
	 * @param s is a list of strings
	 * @param line is line which char is moving down from
	 */
	public String[] charDownLine(String[] s, int line) {
		String[]newS=new String[s.length];
		char c;
		for(int i=0;i<newS.length;i++) {
			if(i<line) {
				newS[i]=s[i];
			}
			else if(i==line) {
				c=s[i].charAt(s[i].length()-1);
				newS[i]=s[i].substring(0,s[i].length()-1);
				newS[i+1]=c+s[i+1];
			}
			else if(i>line+1) {
				newS[i]=s[i];
			}
		}
		return newS;
	}
	
	
	/*
	 * moves char from end of specified line to a new line beneath it, in a list of lines of string
	 * @param s is list of strings
	 * @param line is line which is moving down from
	 */
	public String[] newLine(String[] s,int line) {
		String[]newS=new String[s.length+1];
		char c;
		for(int i=0;i<s.length;i++) {
			if(i<line) {
				newS[i]=s[i];
			}
			if(i==line) {
				c=s[i].charAt(s[i].length()-1);
				newS[i]=s[i].substring(0,s[i].length()-1);
				newS[i+1]=""+c;
			}
			if(i>line) {
				newS[i+1]=s[i];
			}
		}
		return newS;
	}
	
 
}
