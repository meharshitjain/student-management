package com.userservice.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.userservice.user.entity.User;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u " + "WHERE (:first_name IS NULL OR u.first_name = :first_name) "
			+ "AND (:last_name IS NULL OR u.last_name = :last_name)")
	List<User> searchUsers(@Param("first_name") String first_name, @Param("last_name") String last_name);

	Optional<User> findById(Long user_id);

	@Query("SELECT u FROM User u WHERE department_id = :department_id")
	List<User> getUserByDepartmentId(@Param("department_id") Long user_id);

	@Modifying
	@Query(value = "UPDATE user_table SET first_name = ?2 , last_name = ?3, email = ?4, department_id = ?5 WHERE user_id = ?1", nativeQuery = true)
	void updateUser(Long user_id, String first_name, String last_name, String email, Long department_id);
}
