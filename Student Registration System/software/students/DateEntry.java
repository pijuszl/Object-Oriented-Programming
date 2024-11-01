package software.students;

import java.time.LocalDate;

public class DateEntry {
	private LocalDate date;
	private boolean attendance = false;
	
	public DateEntry(LocalDate date, boolean attendance) {
		this.date = date;
		this.attendance = attendance;
	}
	public DateEntry(LocalDate date) {
		this.date = date;
		this.attendance = false;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public boolean getAttendance() {
		return attendance;
	}
	
	public void setAttendance(boolean value) {
		this.attendance = value;
	}
	
	public boolean isFiltered(LocalDate filter1, LocalDate filter2) {
		if(date.isAfter(filter1) || date.isEqual(filter1)) {
			if(date.isBefore(filter2) || date.isEqual(filter2)) {
				return false;
			}
		}
		return true;
	}
	
}
