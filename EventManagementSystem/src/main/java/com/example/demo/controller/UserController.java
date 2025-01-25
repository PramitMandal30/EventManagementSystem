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

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	String message = "User not found with id: ";

	private UserService userService;
	private EventService eventService;

	public UserController(UserService userService, EventService eventService) {
		this.userService = userService;
		this.eventService = eventService;
	}

	// get all
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAll());
	}

	// get by Id
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) throws UserNotFoundException {
		User user = userService.getById(id);
		if (user == null) {
			throw new UserNotFoundException(message + id);
		}
		return ResponseEntity.ok(user);
	}

	// add
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
		userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	// update
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid User user)
			throws UserNotFoundException {
		User userOptional = userService.getById(id);
		if (userOptional == null) {
			throw new UserNotFoundException(message + id);
		}
		user.setId(id);
		userService.update(user);
		return ResponseEntity.ok(user);
	}

	// delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) throws UserNotFoundException {
		User user = userService.getById(id);
		if (user == null) {
			throw new UserNotFoundException(message + id);
		}
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// Register a user to an event
	@PostMapping("/{userId}/register-event/{eventId}")
	public ResponseEntity<String> registerUserToEvent(@PathVariable int userId, @PathVariable int eventId) {
		User user = userService.getById(userId);
		Event event = eventService.getById(eventId);
		user.getRegisteredEvents().add(event);
		userService.save(user);
		return ResponseEntity.ok("User registered to event successfully");
	}

	// Fetch all users registered to an event
	@GetMapping("/events/{eventId}/users")
	public ResponseEntity<List<User>> getEventUsers(@PathVariable int eventId) {
		Event event = eventService.getById(eventId);
		return ResponseEntity.ok(event.getRegisteredUsers());
	}

}
