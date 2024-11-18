package com.userservice.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.pojo.MessageResponse;
import com.userservice.pojo.ResponseTemplateVO;
import com.userservice.pojo.UserResource;
import com.userservice.user.facade.UserFacade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserFacade userFacade;

	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PostMapping(value = "/addUser", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addUser(@RequestBody UserResource userResource) {

		logger.info("Inside addUser method of UserController.");

		try {
			// Call facade method to add user
			boolean error = userFacade.addUser(userResource);

			if (error) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Department Id does not exist!"));
			}

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be added!"));
		}

		return ResponseEntity.ok(new MessageResponse("User successfully added!"));
	}

	// GET - users with filters
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUsers(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName, HttpServletRequest request) {

		logger.info("Inside getUsers method of UserController.");

		// Call facade method to get users with optional search criteria
		List<UserResource> users = userFacade.getAllUsers(firstName, lastName);
		return ResponseEntity.ok(users);
	}

	// GET - users with path variable
	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@GetMapping(value = "/department/{department_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUserByDepartmentId(@PathVariable(value = "department_id", required = true) Long user_id,
			HttpServletRequest request) {

		logger.info("Inside getUserByDepartmentId method of UserController.");

		// Call facade method to get books with optional search criteria
		List<UserResource> users = userFacade.getUserByDepartmentId(user_id);

		return ResponseEntity.ok(users);
	}

	@CrossOrigin(origins = "*", exposedHeaders = "**")
	@PutMapping(value = "/updateUser", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> updateUser(@RequestBody UserResource userResource) {

		logger.info("Inside updateUser method of UserController.");

		try {
			// Call facade method to update user
			boolean error = userFacade.updateUser(userResource);

			if (error) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User Id does not exist!"));
			}

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be updated!"));
		}

		return ResponseEntity.ok(new MessageResponse("User successfully updated!"));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserWithDepartment(@PathVariable Long userId) {
		logger.info("Inside getUserWithDepartment method of UserController.");

		ResponseTemplateVO vo = new ResponseTemplateVO();
		try {
			vo = userFacade.getUserWithDepartment(userId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Data could not be retrived!"));
		}

		return ResponseEntity.ok(vo);
	}

}
