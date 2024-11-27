package net.employee_crud.springboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.employee_crud.springboot.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
//	 Employee findByEmailId(String email_Id);
	boolean existsByEmailId(String email_Id);
}
