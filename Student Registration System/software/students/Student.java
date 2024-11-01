package software.students;

import java.time.LocalDate;
import java.util.ArrayList;

public class Student {
private static int lastId = 0;
	
	private int id;
	private String name;
	private String surname;
	private int year;
	private int group;
	private String studyProgramme;
	private ArrayList<DateEntry> attendenceValues;
	
	public Student(String name, String surname, String studyProgramme, int year, int group) {
		this.id = nextId();
		this.name = name;
		this.surname = surname;
		this.year = year;
		this.group = group;
		this.studyProgramme = studyProgramme;
		this.attendenceValues = new ArrayList<DateEntry>();
	}
	
	private static int nextId() {
		return ++lastId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public int getId() {
		return id;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getStudyProgramme() {
		return studyProgramme;
	}
	
	public int getGroup() {
		return group;
	}
	
	public ArrayList<DateEntry> getAttendanceValues() {
		return attendenceValues;
	}
	
	public void setName(String value) {
		name = value;
	}
	
	public void setSurname(String value) {
		surname = value;
	}
	
	public void setYear(int value) {
		year = value;
	}
	
	public void setStudyProgramme(String value) {
		studyProgramme = value;
	}
	
	public void setGroup(int value) {
		group = value;
	}
	
	public void setAttendanceValues(LocalDate date, boolean value) {
		DateEntry inst = null;
		if(findDate(date) != null) {
			inst = findDate(date);
			inst.setAttendance(value);
		}
		else {
			attendenceValues.add(new DateEntry(date, value));
		}
	}
	
	public DateEntry findDate(LocalDate date) {
		for (DateEntry eDate : attendenceValues) {
			if(eDate.getDate() != null) {
				if (eDate.getDate().equals(date)) {
					return eDate;
				}
			}
		}
		return null;
	}
}
