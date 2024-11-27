package net.employee_crud.springboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="department")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name = "department_name")
	private String department_name;
	
	@Column(name = "department_capacity")
	private long department_capacity;
		
	public Department(){
		
	}
	
	public Department(String department_name, long department_capacity) {
		super();
		this.department_name = department_name;
		this.department_capacity = department_capacity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public long getDepartment_capacity() {
		return department_capacity;
	}

	public void setDepartment_capacity(long department_capacity) {
		this.department_capacity = department_capacity;
	}
	
	
}
