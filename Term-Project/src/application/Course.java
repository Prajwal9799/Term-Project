package application;

public class Course {
	private String courseName;
	private String courseTitle;
	private String weekDays;
	private String time;
	private String location;
	private String instructor;

	public Course(String courseName, String courseTitle, String weekDays, String time, String location, String instructor) {
		this.courseName = courseName;
		this.courseTitle = courseTitle;
		this.weekDays = weekDays;
		this.time = time;
		this.location = location;
		this.instructor = instructor;
	}

	public void coursePrint() {
		String course = "";
		course += courseName + "\n";
		course += courseTitle + "\n";
		course += weekDays + "\n";
		course += time + "\n";
		course += location + "\n";
		course += instructor + "\n";
		course += "\n";
		System.out.println(course);
	}

}
