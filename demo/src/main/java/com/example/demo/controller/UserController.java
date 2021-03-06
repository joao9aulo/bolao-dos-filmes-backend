package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.exception.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public List<User> getAllUsers() {
	    return userRepository.findAll();
	}

	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
	    return userRepository.save(user);
	}

	@GetMapping("/users/{id}")
	public User getNoteById(@PathVariable(value = "id") Long userId) {
	    return userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", userId));
	}

	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable(value = "id") Long userId,
	                                        @Valid @RequestBody User userDetails) {

		User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		user.setEmail(userDetails.getEmail());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());

	    User updatedUser = userRepository.save(user);
	    return updatedUser;
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long userId) {
	    User note = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", userId));

	    userRepository.delete(note);

	    return ResponseEntity.ok().build();
	}
}