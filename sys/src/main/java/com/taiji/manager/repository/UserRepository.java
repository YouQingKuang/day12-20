package com.taiji.manager.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.taiji.manager.entity.Role;
import com.taiji.manager.entity.User;


public interface UserRepository extends JpaRepository<User, String>,JpaSpecificationExecutor<User>,PagingAndSortingRepository<User,String> {
	
	@Transactional
	@Modifying
	@Query("update User u set u.userName=:username where u.userNum=:userNum ")
	void updateUser(@Param("username") String username,
					@Param("userNum") String userNum);
	
}
