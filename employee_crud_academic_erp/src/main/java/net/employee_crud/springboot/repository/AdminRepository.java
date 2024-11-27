package net.employee_crud.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.employee_crud.springboot.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByEmail(String email);
    Optional <Admin> findOneByEmailAndPassword(String email,String password);
}