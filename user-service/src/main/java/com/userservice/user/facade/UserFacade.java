package com.userservice.user.facade;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.userservice.pojo.ResponseTemplateVO;
import com.userservice.pojo.UserResource;
import com.userservice.user.controller.UserController;
import com.userservice.user.entity.User;
import com.userservice.user.service.UserService;

@Component
public class UserFacade {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	public boolean addUser(UserResource userResource) {

		logger.debug("addUser method inside facade.");

		User user = new User();
		user.setFirst_name(userResource.getFirstName());
		user.setLast_name(userResource.getLastName());
		user.setEmail(userResource.getEmail());
		user.setDepartment_id(userResource.getDepartmentId());

		boolean error = userService.saveUser(user);

		return error;
	}

	public List<UserResource> getAllUsers(String firstName, String lastName) {
		logger.debug("getAllUsers method inside facade.");

		List<User> users = userService.getAllUsers(firstName, lastName);

		List<UserResource> list = new ArrayList<>();
		for (User user : users) {
			list.add(convertDTOtoResource(user));
		}

		return list;
	}

	private UserResource convertDTOtoResource(User user) {

		UserResource res = new UserResource();

		res.setFirstName(user.getFirst_name());
		res.setLastName(user.getLast_name());
		res.setDepartmentId(user.getDepartment_id());
		res.setEmail(user.getEmail());

		return res;
	}

	public List<UserResource> getUserByDepartmentId(Long department_id) {
		logger.debug("getUserByDepartmentId method inside facade.");

		List<User> users = userService.getUserByDepartmentId(department_id);

		List<UserResource> userResList = new ArrayList<UserResource>();

		for (User user : users) {
			UserResource userRes = convertDTOtoResource(user);
			userResList.add(userRes);
		}
		return userResList;
	}

	public boolean updateUser(UserResource userResource) {
		logger.debug("updateUser method inside facade.");

		User user = new User();
		user.setFirst_name(userResource.getFirstName());
		user.setLast_name(userResource.getLastName());
		user.setEmail(userResource.getEmail());
		user.setDepartment_id(userResource.getDepartmentId());
		user.setUser_id(userResource.getUserId());

		boolean error = userService.updateUser(user);

		return error;
	}

	public ResponseTemplateVO getUserWithDepartment(Long userId) {
		logger.debug("getUserWithDepartment method inside facade.");

		ResponseTemplateVO vo = userService.getUserWithDepartment(userId);
		return vo;
	}

}
