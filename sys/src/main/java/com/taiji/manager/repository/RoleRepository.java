package com.taiji.manager.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.taiji.manager.entity.Role;


public interface RoleRepository extends JpaRepository<Role, String>,JpaSpecificationExecutor<Role>,PagingAndSortingRepository<Role,String>{
	
	@Transactional
	@Modifying
	@Query("update Role r set r.roleDesc=:roleDesc where r.roleName=:roleName")
	void updateRole(@Param("roleDesc") String roleDesc,
					@Param("roleName") String roleName);
	
}
