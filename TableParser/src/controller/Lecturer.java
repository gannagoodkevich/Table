package controller;

public class Lecturer {
	FullName name;
	String faculty;
	String department;
	String degree;
	String degreeName;
	String year;

	Lecturer(FullName name) {
		this.setName(name);
	}

	Lecturer(FullName name, String faculty) {
		this.setName(name);
		this.setFaculty(faculty);
	}

	Lecturer(FullName name, String faculty, String department) {
		this.setName(name);
		this.setFaculty(faculty);
		this.setDepartment(department);
	}

	Lecturer(FullName name, String faculty, String department, String degreeName) {
		this.setName(name);
		this.setFaculty(faculty);
		this.setDepartment(department);
		this.setDegreeName(degreeName);
	}
	
	Lecturer(FullName name, String faculty, String department, String degreeName, String degree) {
		this.setName(name);
		this.setFaculty(faculty);
		this.setDepartment(department);
		this.setDegreeName(degreeName);
		this.setDegree(degree);
	}
	
	Lecturer(FullName name, String faculty, String department, String degreeName, String degree, String year) {
		this.setName(name);
		this.setFaculty(faculty);
		this.setDepartment(department);
		this.setDegreeName(degreeName);
		this.setDegree(degree);
		this.setYear(year);
	}
	
	public void setName(FullName name) {
		this.name = name;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public FullName getName() {
		return this.name;
	}

	public String getFaculty() {
		return this.faculty;
	}

	public String getDepartment() {
		return this.department;
	}
	
	public String getDegreeName() {
		return this.degreeName;
	}
	
	public String getDegree() {
		return this.degree;
	}
	
	public String getYear() {
		return this.year;
	}
}
