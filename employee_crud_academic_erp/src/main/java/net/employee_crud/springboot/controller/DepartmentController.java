package net.employee_crud.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.employee_crud.springboot.exception.ResourceNotFounudException;
import net.employee_crud.springboot.model.Department;

import net.employee_crud.springboot.repository.DepartmentRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class DepartmentController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	//get all Department api
	@GetMapping("/departments")
	public List<Department>getAllDepartment(){
		return departmentRepository.findAll();
	}
	
	//get department by id rest api
	@GetMapping("/departments/{id}")
	public ResponseEntity<Department> getDepartmentId(@PathVariable Long id) {
		
		Department department = departmentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFounudException("Department Not exist with id: " + id));
		return ResponseEntity.ok(department);
	}
	
	//Update department rest api
	@PutMapping("/departments/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable Long id,@RequestBody Department department_details){
		Department department = departmentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFounudException("Department Not exist with id: " + id));
		
		department.setDepartment_capacity(department_details.getDepartment_capacity());
		department.setDepartment_name(department_details.getDepartment_name());
		
		
		Department updated_Department = departmentRepository.save(department);
		return ResponseEntity.ok(updated_Department);
	}
	
	@PutMapping("/departments/{id}/decreaseCapacity")
    public ResponseEntity<Department> decreaseDepartmentCapacity(@PathVariable Long id) {
        Department old_department_details = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFounudException("Department Not exist with id: " + id));
        
        old_department_details.setDepartment_capacity(old_department_details.getDepartment_capacity()-1);

        Department updated_Department = departmentRepository.save(old_department_details);
        return ResponseEntity.ok(updated_Department);
    }
	
	@PutMapping("/departments/{id}/increaseCapacity")
    public ResponseEntity<Department> increaseDepartmentCapacity(@PathVariable Long id) {
        Department old_department_details = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFounudException("Department Not exist with id: " + id));
        
        old_department_details.setDepartment_capacity(old_department_details.getDepartment_capacity()+1);

        Department updated_Department = departmentRepository.save(old_department_details);
        return ResponseEntity.ok(updated_Department);
    }
}
