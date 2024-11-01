package software.students;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class DataSet {
	
	private List<Student> students = new ArrayList<Student>();
	private final static DataSet INSTANCE = new DataSet();
	private int studentsCount = 0;
	private LocalDate filter1, filter2;
	private boolean filterPresent = false;
	
	private DataSet() {}
	
	public static DataSet getInstance() {
		return INSTANCE;
	}
	
	public void addStudent(Student e) {
		students.add(e);
		++studentsCount;
	}
	
	public List<Student> getList() {
		return this.students;
	}
	
	public Student findInstanceByID(int id) {
		if(id > studentsCount)
			return null;
		for (Student student : students) {
			if (student.getId() == id) {
				return student;
			}
		}
		return null;
	}
	public void removeStudent(int id) {
		Student s = findInstanceByID(id);
		if(s == null)
			return;
		students.remove(s);
	}
	
	public void createFilter(LocalDate filter1, LocalDate filter2) {
		filterPresent = true;
		if(filter1 == null) {
			filter1 = LocalDate.of(1900, 1, 1);
		}
		if(filter2 == null ) {
			filter2 = LocalDate.of(2100, 1, 1);
		}
		this.filter1 = filter1;
		this.filter2 = filter2;
	}
	
	public LocalDate getFilter1() {
		return filter1;
	}
	
	public LocalDate getFilter2() {
		return filter2;
	}
	
	public boolean isDataFiltered() {
		return filterPresent;
	}
	
	public void deleteList() {
		students = new ArrayList<Student>();
		studentsCount = 0;
		removeFilter();
	}
	
	public void removeFilter() {
		filterPresent = false;
	}
	
}