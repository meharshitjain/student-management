package com.userservice.user.service;

import java.util.List;

import com.userservice.pojo.ResponseTemplateVO;
import com.userservice.user.entity.User;

public interface UserService {

	public boolean saveUser(User user);

	public List<User> getAllUsers(String firstName, String lastName);
	
	public List<User> getUserByDepartmentId(Long department_id);
	
	public boolean updateUser(User user);

	public ResponseTemplateVO getUserWithDepartment(Long userId);
}
