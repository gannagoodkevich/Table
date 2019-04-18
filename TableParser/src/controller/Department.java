package controller;

import java.util.ArrayList;
import java.util.List;

public class Department {
	private String title;
	private List<Lecturer> lecturers = new ArrayList<Lecturer>();
	
	Department(String title){
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Lecturer getlecturer(int index) {
		return lecturers.get(index);
	}
	
	public void addLecturer(Lecturer lecturer) {
		lecturers.add(lecturer);
	}
	
	public int getLenght() {
		return lecturers.size();
	}
}
