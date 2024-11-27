package net.employee_crud.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.employee_crud.springboot.dto.Login;
import net.employee_crud.springboot.dto.LoginMessage;
import net.employee_crud.springboot.helper.JWTHelper;
import net.employee_crud.springboot.repository.AdminRepository;
import net.employee_crud.springboot.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTHelper jwtHelper;

    public void saveAdmin(Admin adminData) {
        String encodedPassword=this.passwordEncoder.encode(adminData.getPassword());
        adminData.setPassword((encodedPassword));
        adminRepository.save(adminData);
    }
    public String loginEmployee(Login login) {
        Admin admin = adminRepository.findByEmail(login.getEmail());
        if(!passwordEncoder.matches(login.getPassword(), admin.getPassword()))
        {
            return "Wrong Password or email";
        }

        return jwtHelper.generateToken(login.getEmail());
    }
}