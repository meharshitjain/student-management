package com.userservice.user.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.pojo.Department;
import com.userservice.pojo.ResponseTemplateVO;
import com.userservice.user.entity.User;
import com.userservice.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	public boolean saveUser(User user) {
		logger.info("Inside saveUser method of UserService.");

		Department department = restTemplate.getForObject("http://DEPARTMENTS/departments/" + user.getDepartment_id(),
				Department.class);

		if (department == null) {
			return true;
		}

		userRepository.save(user);

		return false;
	}

	public List<User> getAllUsers(String firstName, String lastName) {

		logger.info("Inside getAllUsers method of UserService.");

		List<User> users;
		if (firstName != null || lastName != null) {
			// Search with provided criteria
			users = userRepository.searchUsers(firstName, lastName);
		} else {
			// Fetch all users
			users = userRepository.findAll();
		}
		return users;
	}

	public List<User> getUserByDepartmentId(Long department_id) {
		logger.info("Inside getUserByDepartmentId method of UserService.");

		List<User> users = userRepository.getUserByDepartmentId(department_id);
		return users;
	}

	public boolean updateUser(User user) {
		logger.info("Inside updateUser method of UserService.");
		
		logger.info("user.getUser_id() : " + user.getUser_id() + 
				"user.getFirst_name() : " + user.getFirst_name() + 
				"user.getLast_name() : " +user.getLast_name() + 
				"user.getEmail() : " + user.getEmail() + 
				"user.getDepartment_id() : " + user.getDepartment_id() );

		try {
			userRepository.updateUser(user.getUser_id(), user.getFirst_name(), user.getLast_name(), user.getEmail(),
					user.getDepartment_id());
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public ResponseTemplateVO getUserWithDepartment(Long user_id) {
		logger.info("Inside saveUser method of UserService.");
		ResponseTemplateVO vo = new ResponseTemplateVO();
		Optional<User> users = userRepository.findById(user_id);

		User user = users.get();

		Department department = restTemplate.getForObject("http://DEPARTMENTS/departments/" + user.getDepartment_id(),
				Department.class);
		vo.setUser(user);
		vo.setDepartment(department);

		return vo;
	}

}
