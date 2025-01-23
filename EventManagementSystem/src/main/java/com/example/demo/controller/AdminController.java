package com.example.demo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Admin;
import com.example.demo.exception.AdminNotFoundException;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdminController {
	
	String message = "Admin not found with id: ";
	
	private AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	//get all 
	@GetMapping
	public ResponseEntity<List<Admin>> getAllAdmins(){
		return ResponseEntity.ok(adminService.getAll());
	}
	
	//get by Id
	@GetMapping("/{id}")
	public ResponseEntity<Admin> getAdminById(@PathVariable int id) throws AdminNotFoundException{
		Admin admin = adminService.getById(id);
		if(admin==null) {
			throw new AdminNotFoundException(message +id);
		}
		return ResponseEntity.ok(admin);
	}
	
	//add
	@PostMapping
	public ResponseEntity<Admin> saveAdmin(@RequestBody @Valid Admin admin) {
		adminService.save(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body(admin);
	}
	
	//update
	@PutMapping("/{id}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody @Valid Admin admin) throws AdminNotFoundException {
		Admin adminOptional = adminService.getById(id);
		if(adminOptional==null) {
			throw new AdminNotFoundException(message +id);
		}
        admin.setId(id);
        adminService.update(admin);
        return ResponseEntity.ok(admin);
    }
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable int id) throws AdminNotFoundException {
		Admin admin = adminService.getById(id);
		if(admin==null) {
			throw new AdminNotFoundException(message +id);
		}
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
	
}
