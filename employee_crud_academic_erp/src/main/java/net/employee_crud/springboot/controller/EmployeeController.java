package net.employee_crud.springboot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import net.employee_crud.springboot.helper.JWTHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
//import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.employee_crud.springboot.exception.ResourceNotFounudException;
import net.employee_crud.springboot.model.Employee;
import net.employee_crud.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeRepository employeeRepository;

	private final JWTHelper jwtHelper;

	private boolean isAuthorized(String token) {
		String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
		String email = jwtHelper.extractEmail(jwtToken);
		return jwtHelper.validateToken(jwtToken, email);
	}
	
	@Value("${upload.path}") // Set the upload path in application.properties
	private String uploadPath;
	
	//get all employees api
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){

		if(!isAuthorized(token))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(employeeRepository.findAll());
	}
	
	@PostMapping(value = "/employees", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> createEmployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
	        @RequestPart("file") MultipartFile file,
	        @RequestPart("firstName") String firstName,
	        @RequestPart("lastName") String lastName,
	        @RequestPart("emailId") String emailId,
	        @RequestPart("department") String department) {
		if(!isAuthorized(token))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	    // Check if the email ID is already in use
	    if (employeeRepository.existsByEmailId(emailId)) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Email ID already exists.");
	    }

	    // If not, proceed with creating the employee
	    Employee employee = new Employee();
	    try {
	        employee.setFirstName(firstName);
	        employee.setLastName(lastName);
	        employee.setEmailId(emailId);
	        employee.setDepartment(department);

	        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	        Path filePath = Paths.get(uploadPath, fileName);
	        file.transferTo(filePath);

	        employee.setimageString(fileName);
	        Employee savedEmployee = employeeRepository.save(employee);

	        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the exception appropriately (e.g., log, return an error response)
	        System.out.println("Error processing file upload. File name: " + file.getOriginalFilename());
	        System.out.println("Error message: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file upload");
	    }
	}
	
	//get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeebyId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable Long id) {
		if(!isAuthorized(token))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFounudException("Employee Not exist with id: " + id));
		return ResponseEntity.ok(employee);
	}
	
	//update employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable Long id, @RequestPart(value = "file", required = false) MultipartFile file,
	                                               @RequestPart("firstName") String firstName,
	                                               @RequestPart("lastName") String lastName,
	                                               @RequestPart("emailId") String emailId,
	                                               @RequestPart("department") String department) {
	    try {
			if(!isAuthorized(token))
			{
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
	        Employee employee = employeeRepository.findById(id)
	        		.orElseThrow(()-> new ResourceNotFounudException("Employee Not exist with id: " + id));

	        employee.setFirstName(firstName);
	        employee.setLastName(lastName);
	        employee.setEmailId(emailId);
	        employee.setDepartment(department);

	        if (file != null) {
	            // Handle file upload
	            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	            Path filePath = Paths.get(uploadPath, fileName);
	            file.transferTo(filePath);

	            // Delete the old image if it exists
	            if (employee.getimageString() != null && !employee.getimageString().isEmpty()) {
	                Files.deleteIfExists(Paths.get(uploadPath, employee.getimageString()));
	            }

	            employee.setimageString(fileName);
	        }

	        Employee updatedEmployee = employeeRepository.save(employee);
	        return ResponseEntity.ok(updatedEmployee);
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the exception appropriately (e.g., log, return an error response)
	        System.out.println("Error processing file upload. File name: " + file.getOriginalFilename());
	        System.out.println("Error message: " + e.getMessage());
	        throw new RuntimeException("Error processing the file upload");
	    }
	}
	
	//Delete Employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>>deleteEmployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable long id){
		if(!isAuthorized(token))
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFounudException("Employee Not exist with id: " + id));
		
		employeeRepository.delete(employee);
		Map<String,Boolean> ans = new HashMap<>();
		ans.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(ans);
	}
	
	@GetMapping("/employees/image/{id}")
	public String getImageById(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFounudException("Employee Not exist with id: " + id));
		return employee.getimageString();
	}
	
	//Upload image
	@PostMapping("/employees/upload")
	public ResponseEntity<String> handleFileUpload( @RequestPart("image") MultipartFile file){
		try {
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		
		Path filePath = Paths.get(uploadPath, fileName);
		file.transferTo(filePath);
		return ResponseEntity.ok("File uploaded successfully");
		}
		catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
	}
	
	//get all images	
	@GetMapping("/images/{imageString}")
	public ResponseEntity<Resource> getImage(@PathVariable String imageString) {
	    try {
	        String imagePath = "/home/vaibhav/Desktop/vaibhav/" + imageString;
	        Resource resource = new FileSystemResource(imagePath);

	        if (resource.exists() && resource.isReadable()) {
	            return ResponseEntity.ok()
	                    .contentType(MediaType.IMAGE_JPEG)
	                    .body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}
	
}
