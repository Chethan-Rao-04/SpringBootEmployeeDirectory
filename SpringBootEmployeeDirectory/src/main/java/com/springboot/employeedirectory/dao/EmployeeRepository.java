package com.springboot.employeedirectory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.employeedirectory.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


	public List<Employee> findAllByOrderByLastNameAsc();
	
	// search by name
	public List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);

}
