package com.lxfutbol.user.controller;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.user.dto.RegistryUserDto;
import com.lxfutbol.user.repository.UserEntity;
import com.lxfutbol.user.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	private UserEntity userSession;

	@PostMapping("/user/registry")
	public ResponseEntity<Object> registry(@RequestBody RegistryUserDto userRegistry) {
		try {
			userService.registry(userRegistry);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@GetMapping("/user/login")
	public ResponseEntity<UserEntity> login(@RequestParam String email, @RequestParam String passWord) {
		UserEntity user = null;
		try {
			user = userService.login(email, passWord);
			this.userSession = user;
		} catch (NoResultException ex) {
			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/userLogged")
	public UserEntity logged() {
		return this.userSession;
	}

	@GetMapping("/logout")
	public void logout() {
		this.userSession = null;
	}
}
