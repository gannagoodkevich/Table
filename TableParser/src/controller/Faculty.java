package controller;

import java.util.ArrayList;
import java.util.List;

public class Faculty {
	private String title;
	private List<Department> departments = new ArrayList<Department>();
	
	Faculty(String title){
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Department getDepartment(int index) {
		return departments.get(index);
	}
	
	public void addDepartment(Department department) {
		departments.add(department);
	}

	public int getLenght() {
		return departments.size();
	}
}
