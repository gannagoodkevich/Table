package controller;


import java.util.ArrayList;
import java.util.List;

import model.Uni;

public class UniversityController {

	public List<String[]> getUniversity(Uni university) {
		List<String[]> rowList = new ArrayList<String[]>();

		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < university
				.getLenght(); indexOfCurrentFaculty++) {
			for (int indexOfCurrentDepartment = 0; indexOfCurrentDepartment < university
					.getFaculty(indexOfCurrentFaculty).getLenght(); indexOfCurrentDepartment++) {
				for (int indexOfCurrentLecturer = 0; indexOfCurrentLecturer < university
						.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
						.getLenght(); indexOfCurrentLecturer++) {
					rowList.add(new String[] { university.getFaculty(indexOfCurrentFaculty).getTitle(),
							university.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
									.getTitle(),
							university.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
									.getlecturer(indexOfCurrentLecturer).getName()
									+ " "
									+ university.getFaculty(indexOfCurrentFaculty)
											.getDepartment(indexOfCurrentDepartment)
											.getlecturer(indexOfCurrentLecturer).getSurname()
									+ " "
									+ university.getFaculty(indexOfCurrentFaculty)
											.getDepartment(indexOfCurrentDepartment)
											.getlecturer(indexOfCurrentLecturer).getSecondName(),
							university.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
									.getlecturer(indexOfCurrentLecturer).getDegreeName(),
							university.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
									.getlecturer(indexOfCurrentLecturer).getDegree(),
							university.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
									.getlecturer(indexOfCurrentLecturer).getYear() });
				}
			}
		}
		return rowList;
	}
	
	
}
