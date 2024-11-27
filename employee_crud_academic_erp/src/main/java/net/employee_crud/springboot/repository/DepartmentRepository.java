package net.employee_crud.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.employee_crud.springboot.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {

}
