package com.example.test.model;

import javax.persistence.Entity;

public class Employee {

	private String id;
	private String name;
	private int age;
	private int experience;
	private String doj;
	public Employee(String id, String name, int age, int experience, String doj) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.experience = experience;
		this.doj = doj;
	}
	public Employee() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", experience=" + experience + ", doj=" + doj
				+ "]";
	}
	
	
}
