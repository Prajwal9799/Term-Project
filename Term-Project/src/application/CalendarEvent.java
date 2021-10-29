package application;

public class CalendarEvent {
	private String room;
	private String teacher;
	private int startTime; //stores time as minutes since midnight
	private int endTime;
	private String className;//ie: Intro to Software Development
	private String classID;//ie: CSC0-305
	private String[] days;
	CalendarEvent(String data){
		String[] d=data.split("\n");
		if(d.length!=10) {
			System.out.println("ERROR, invalid schedule");
			room="";
			startTime=0;
			endTime=0;
			className="";
			classID="";
			days= new String[0];
		}
		else {
			room=d[0]+" "+d[1];
			teacher=d[2];
			classID=d[3];
			className=d[4];
			days=d[8].split(" ");
			//splits time up into minutes
			String time=d[9];
			String[]times=time.split(" - ");//splits time into 0:00AM and 0:00PM
			String time1=times[0];
			String time2=times[1];
			boolean pm1=false;//booleans regarding whether start/end time are am/pm
			boolean pm2=false;
			if(time1.substring(time1.length()-2,time1.length()).equals("PM")) {
				pm1=true;
			}
			if(time2.substring(time2.length()-2,time2.length()).equals("PM")) {
				pm2=true;
			}
			time1=time1.substring(0,time1.length()-2);//get rid of AM/pm
			time2=time2.substring(0,time2.length()-2);
			String[] timeArray1=time1.split(":");//split time into hours and minutes;
			String[] timeArray2=time2.split(":");
			
			int time1Hours=Integer.parseInt(timeArray1[0]);//converts into int
			if(time1Hours==12) {
				time1Hours=0;
			}
			int time1Minutes=Integer.parseInt(timeArray1[1]);
			startTime=time1Hours*60+time1Minutes;
			
			int time2Hours=Integer.parseInt(timeArray2[0]);
			if(time2Hours==12) {
				time2Hours=0;
			}
			int time2Minutes=Integer.parseInt(timeArray2[1]);
			endTime=time2Hours*60+time2Minutes;
			
			if(pm1) {
				startTime=startTime+12*60;
			}
			if(pm2) {
				endTime=endTime+12*60;
			}
			
		}
	}
	public int getStartHours() {
		return startTime/60;
	}
	public int getStartMinutes() {
		return startTime%60;
	}
	public int getEndHours() {
		return endTime/60;
	}
	public int getEndMinutes() {
		return endTime%60;
	}
	public String getRoom() {
		return room;
	}
	public String getTeacher() {
		return teacher;
	}
	public int getStartTime() {
		return startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public String getClassID() {
		return classID;
	}
	public String getClassName() {
		return className;
	}
	public String[] getDays() {
		return days;
	}
	public String getStartTimeString() {
		boolean pm=false;
		int hours=getStartHours();
		int minutes=getStartMinutes();
		String additionalZero="";//if minutes is less than 10, adds zero before digit. IE: 12:5-->12:05
		if (minutes<10) {
			additionalZero="0";
		}
		if (hours>=12) {
			pm=true;
			if(hours>12) {
				hours=hours-12;
			}
		}
		if(hours==0) {
			hours=12;
		}
		if(pm){//make sure hours isnt 0
			return hours+":"+additionalZero+minutes+" PM";
		}
		else {
			return hours+":"+additionalZero+minutes+" AM";
		}
	}
	public String getEndTimeString() {
		boolean pm=false;
		int hours=getEndHours();
		int minutes=getEndMinutes();
		String additionalZero="";//if minutes is less than 10, adds zero before digit. IE: 12:5-->12:05
		if (minutes<10) {
			additionalZero="0";
		}
		if (hours>=12) {
			pm=true;
			if(hours>12) {
				hours=hours-12;
			}
		}
		if(hours==0) {//make sure hours isn't 0
			hours=12;
		}
		if(pm){
			return hours+":"+additionalZero+minutes+" PM";
		}
		else {
			return hours+":"+additionalZero+minutes+" AM";
		}
	}
	public boolean equals(CalendarEvent e) {
		if(e==null) {
			if (room.toString().equals("")) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(room.toString().equals(e.toString())) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	public String toString(){
		String d="";
		for(String i:days) {
			d=d+i+" ";
		}
		return room+"\n"+teacher+"\n"+classID+"\n"+className+"\n"+d+"\n"+getStartTimeString()+" -- "+getEndTimeString();
	}
}
