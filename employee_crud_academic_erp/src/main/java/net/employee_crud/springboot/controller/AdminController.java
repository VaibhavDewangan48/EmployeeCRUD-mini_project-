package net.employee_crud.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.employee_crud.springboot.dto.Login;
import net.employee_crud.springboot.dto.LoginMessage;
import net.employee_crud.springboot.model.Admin;
import net.employee_crud.springboot.repository.AdminRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("employee/register")
    public ResponseEntity<String> RegisterAdmin(@RequestBody Admin adminData) {
        adminService.saveAdmin(adminData);
        return new ResponseEntity<>("Admin created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/employee/login")
    public ResponseEntity<?> loginEmployee(@RequestBody Login login){
        String token = adminService.loginEmployee(login);
        return ResponseEntity.ok(token);
    }

}