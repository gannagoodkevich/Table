package model;

import java.util.ArrayList;
import java.util.List;

public class Department {
	private String title;
	private List<Lecturer> lecturers = new ArrayList<Lecturer>();

	public Department(String title) {
		this.title = title;
	}

	Department(Department dep) {
		this.title = dep.title;
		this.lecturers = dep.lecturers;
	}

	public String getTitle() {
		return this.title;
	}

	public Lecturer getlecturer(int index) {
		return lecturers.get(index);
	}

	public Lecturer getLectureByInfo(String name, String surname, String secondName, String degree, String degreeT,
			String year) {
		Lecturer lec = null;
		for (Lecturer lecturer : lecturers) {
			if (name.equals(lecturer.getName()) && surname.equals(lecturer.getSurname())
					&& secondName.equals(lecturer.getSecondName()) && degree.equals(lecturer.getDegree())
					&& degreeT.equals(lecturer.getDegreeName()) && year.equals(lecturer.getYear())) {
				lec = new Lecturer(lecturer);
			}
		}
		return lec;
	}

	public List<Lecturer> getLectureByName(String name) {
		List<Lecturer> lec = new ArrayList<Lecturer>();
		for (Lecturer lecturer : lecturers) {
			if (name.equals(lecturer.getName())) {
				lec.add(lecturer);
			}
		}
		return lec;
	}

	public List<Lecturer> getLectureByYear(String year1, String year2) {
		List<Lecturer> lec = new ArrayList<Lecturer>();
		int y1 = Integer.parseInt(year1);
		int y2 = Integer.parseInt(year2);
		for (Lecturer lecturer : lecturers) {
			if (Integer.parseInt(lecturer.getYear()) >= y1 && Integer.parseInt(lecturer.getYear()) <= y2) {
				lec.add(lecturer);
				
			}
		}
		return lec;
	}

	public List<Lecturer> getLectureByDegreeName(String name) {
		List<Lecturer> lec = new ArrayList<Lecturer>();
		for (Lecturer lecturer : lecturers) {
			if (name.equals(lecturer.getDegreeName())) {
				lec.add(lecturer);
			}
		}
		return lec;
	}

	public void addLecturer(Lecturer lecturer) {
		lecturers.add(lecturer);
	}

	public void deleteLecture(Lecturer lecturer) {
		lecturers.remove(lecturer);
	}
	
	public int getLenght() {
		return lecturers.size();
	}
}
