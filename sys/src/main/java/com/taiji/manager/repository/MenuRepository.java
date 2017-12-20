package com.taiji.manager.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.taiji.manager.entity.Menu;
import com.taiji.manager.entity.Role;

public interface MenuRepository extends JpaRepository<Menu, String>,JpaSpecificationExecutor<Menu> ,PagingAndSortingRepository<Menu,String>{
	
	@Transactional
	@Modifying
	@Query("update Menu m set m.url=:url where m.id=:id")
	void updateMenu(@Param("url") String url,
					@Param("id") String id);
	
	
}
