package controller;

public class FullName {
	private String name;
	private String surname;
	private String secondName;

	FullName(String name) {
		this.name = name;
	}

	FullName(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	FullName(String name, String surname, String secondName) {
		this.name = name;
		this.surname = surname;
		this.secondName = secondName;
	}
	
	public void setName(String name){
		this.name = name;		
	}
	
	public void setSurname(String surname){
		this.surname = surname;		
	}
	
	public void setSecondName(String secondname){
		this.secondName = secondname;		
	}
	
	public String getName(){
		return this.name;		
	}
	
	public String getSurname(){
		 return this.surname;		
	}
	
	public String getSecondName(){
		return this.secondName;		
	} 

}
