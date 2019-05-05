package controller;

import java.util.ArrayList;
import java.util.List;

import model.Lecturer;
import model.Uni;

public class SearchController {

	public List<String[]> listenerSearchByFaculty(Uni uni, String faculty, String degreeeName) {
		List<String[]> rowList = new ArrayList<String[]>();
		for (int indexOfCurrentDepartment = 0; indexOfCurrentDepartment < uni
				.getFacultyByName(faculty).getLenght(); indexOfCurrentDepartment++) {
			if (uni.getFacultyByName(faculty).getDepartment(indexOfCurrentDepartment)
					.getLectureByDegreeName(degreeeName) != null) {
				for (Lecturer lecturer : uni.getFacultyByName(faculty)
						.getDepartment(indexOfCurrentDepartment)
						.getLectureByDegreeName(degreeeName)) {
					rowList.add(new String[] {
							uni.getFacultyByName(faculty).getTitle(),
							uni.getFacultyByName(faculty)
									.getDepartment(indexOfCurrentDepartment).getTitle(),
							lecturer.getName() + " " + lecturer.getSurname() + " " + lecturer.getSecondName(),
							lecturer.getDegreeName(), lecturer.getDegree(), lecturer.getYear() });
				}
			}

		}
		return rowList;
	}

public List<String[]> listenerSearchByName(Uni uni, String department, String name) {
		List<String[]> rowList = new ArrayList<String[]>();
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			if (uni.getFaculty(indexOfCurrentFaculty)
					.getDepartmentByName(department) != null) {
				if (uni.getFaculty(indexOfCurrentFaculty).getDepartmentByName(department)
						.getLectureByName(name) != null) {
					for (Lecturer lecturer : uni.getFaculty(indexOfCurrentFaculty)
							.getDepartmentByName(department)
							.getLectureByName(name)) {
						rowList.add(new String[] { uni.getFaculty(indexOfCurrentFaculty).getTitle(),
								uni.getFaculty(indexOfCurrentFaculty)
										.getDepartmentByName(department).getTitle(),
								lecturer.getName() + " " + lecturer.getSurname() + " " + lecturer.getSecondName(),
								lecturer.getDegreeName(), lecturer.getDegree(), lecturer.getYear() });
					}
				}
			}
		}
		
		return rowList;
	}


public List<String[]> listenerSearchByYear(Uni uni, String year1, String year2) {
	List<String[]> rowList = new ArrayList<String[]>();
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			for (int indexOfCurrentDepartment = 0; indexOfCurrentDepartment < uni.getFaculty(indexOfCurrentFaculty)
					.getLenght(); indexOfCurrentDepartment++) {
				if (uni.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
						.getLectureByYear(year1, year2) != null) {
					for (Lecturer lecturer : uni.getFaculty(indexOfCurrentFaculty)
							.getDepartment(indexOfCurrentDepartment).getLectureByYear(year1, year2)) {
						rowList.add(new String[] { uni.getFaculty(indexOfCurrentFaculty).getTitle(),
								uni.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
										.getTitle(),
								lecturer.getName() + " " + lecturer.getSurname() + " " + lecturer.getSecondName(),
								lecturer.getDegreeName(), lecturer.getDegree(), lecturer.getYear() });
					}
				}
			}
		}
		return rowList;
}
	
}
