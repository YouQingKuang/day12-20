package com.taiji.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.taiji.manager.dto.UserDto;
import com.taiji.manager.entity.User;
import com.taiji.manager.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//	private User user;
	
	public void save(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setId(UUID.randomUUID().toString());
		userRepository.save(user);
	}

	public void delete(String id) {
		userRepository.delete(id);
	}
	
	public Map getPage(int page,int pageSize,HashMap<String,String> orderMaps,HashMap<String,String> filters) {
		Page<User> pageContent;
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > 100)
			pageSize = 100;
		
		List<Order> orders = new ArrayList<Order>();
		if(orderMaps!=null) {
			for (String key : orderMaps.keySet()) {
				if("DESC".equalsIgnoreCase(orderMaps.get(key))) {
					orders.add(new Order(Direction.DESC,key));
				}else {
					orders.add(new Order(Direction.ASC, key));
				}
			}
		}
		
		PageRequest pageable;
		if(orders.size()>0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		}else {
			pageable = new PageRequest(page, pageSize);
		}
		
		if(filters!=null) {
			Specification<User> spec = new Specification<User>() {

				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					for (String key : filters.keySet()) {
						String value = filters.get(key);
						if("enabled".equalsIgnoreCase(key)) {
							if("true".equalsIgnoreCase(value)) {
								pl.add(cb.equal(root.get(key), true));
							}else if("false".equalsIgnoreCase(value)) {
								pl.add(cb.equal(root.get(key), false));
							}
						} else if("userName".equalsIgnoreCase(key)) {
							if(value.length()>0) {
								System.out.println("------------------");
								pl.add(cb.like(root.<String> get(key), value+"%"));
								System.out.println("pl"+pl);
							}
						} else if("user".equalsIgnoreCase(key)) {
							if(value.length()>0) {
								System.out.println("=================");
								pl.add(cb.like(root.get(key), value));
							}
						}
					}
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			System.out.println("spec"+spec);
			pageContent = userRepository.findAll(spec, pageable);
			System.out.println(pageContent);
		}else {
			pageContent = userRepository.findAll(pageable);
		}
		Map map = new HashMap();
		map.put("total", pageContent.getTotalElements());
		map.put("users", userPageDto(pageContent));
		return map;
	}
	

	
	public Map getPage(final Map searchParams,String salt) {
		System.out.println("-----getPage-----");
		
		Map map = new HashMap();
		int page=0;
		int pageSize=3;
		
		Page<User> pageList;
		if (searchParams != null && searchParams.size()>0 && searchParams.get("page")!=null) {
			page = Integer.parseInt(searchParams.get("page").toString()) -1;
		}
		if (searchParams != null && searchParams.size()>0 && searchParams.get("pageSize")!=null) {
			page = Integer.parseInt(searchParams.get("pageSize").toString());
		}
			
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > 100)
			pageSize = 100;

		List<Map> orderMaps =  (List<Map>)searchParams.get("sort");
		List<Order> orders = new ArrayList<Order>();
		
		if (orderMaps != null) {
			
			for (Map m : orderMaps) {
				if( m.get("field")==null ) 
					continue;
					
					String field = m.get("field").toString();
					if(StringUtils.isEmpty(field)) {
						String dir = m.get("dir").toString();
						
						if ("DESC".equalsIgnoreCase( dir )) {
							orders.add(new Order(Direction.DESC, field));
						} else {
							orders.add(new Order(Direction.ASC, field));
						}
					}
				}
			}
			
		PageRequest pageable;
		if (orders.size() > 0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		} else {
//			Sort s = new Sort(Direction.ASC,"userIndex");
			pageable = new PageRequest(page, pageSize);
		}

		Map filter = (Map)searchParams.get("filter");
		
		if (filter != null) {
			final List<Map> filters = (List<Map>)filter.get("filters");
			
			Specification<User> spec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> p1 = new ArrayList<Predicate>();
					for(Map f:filters) {
						String field = f.get("field").toString().trim();
						String value = f.get("value").toString().trim();
						if(value!=null && value.length()>0) {
							if("loginName".equalsIgnoreCase(field)) {
								p1.add(cb.equal(root.<String> get(field), value));
							}if("userName".equalsIgnoreCase(field)) {
								p1.add(cb.equal(root.<String> get(field), value));
							}if("userNum".equalsIgnoreCase(field)) {
								p1.add(cb.equal(root.<String> get(field), value));
							}
						}
					}
//					p1.add(cb.equal(root.<Integer> get("flag"), 1));
//					p1.add(cb.equal(root.<Integer> get("state"), 1));
					return cb.and(p1.toArray(new Predicate[0]));
				}
			};
			pageList = userRepository.findAll(spec, pageable);
			System.out.println(pageList);
		} else {
			pageList = userRepository.findAll(pageable);
		}
		
		map.put("total", pageList.getTotalElements());
		map.put("users", userPageDto(pageList));
		return map;
	}

	private List<UserDto> userPageDto(Page<User> pageContent) {
		List<User> userList = pageContent.getContent();
		System.out.println("userList"+userList);
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (User user : userList) {
			UserDto userDto = entityToDto(user);
			userDtoList.add(userDto);
			System.out.println("USER"+user);
		}
		return userDtoList;
	}

	private UserDto entityToDto(User user) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

	public UserDto findById(String id) {
		User findOne = userRepository.findOne(id);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(findOne, userDto);
		return userDto;
	}


}
