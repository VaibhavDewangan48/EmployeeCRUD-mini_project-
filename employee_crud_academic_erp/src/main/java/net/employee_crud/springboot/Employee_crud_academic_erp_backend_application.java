package net.employee_crud.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaRepositories(basePackages = "net.employee_crud.springboot.repository")
@EntityScan(basePackages = "net.employee_crud.springboot.model")
@EnableJpaAuditing
public class Employee_crud_academic_erp_backend_application {

	public static void main(String[] args) {
		SpringApplication.run(Employee_crud_academic_erp_backend_application.class, args);
	}	
}


