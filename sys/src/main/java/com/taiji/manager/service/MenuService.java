package com.taiji.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.manager.dto.MenuDto;
import com.taiji.manager.dto.TreeDto;
import com.taiji.manager.entity.Menu;
import com.taiji.manager.repository.MenuRepository;

@Service
public class MenuService {
	@Autowired
	private MenuRepository menuRepository;
	
//	@Autowired
//	private Menu menu;
	
	public void save(MenuDto menuDto) {
		Menu menu = new Menu();
		BeanUtils.copyProperties(menuDto,menu);
		menuRepository.save(menu);
	}

	public void delete(String id) {
		menuRepository.delete(id);
	}

	public void updateMenu(String url, String id) {
		menuRepository.updateMenu(url, id);
	}
	
	/**
	 * 查找所有数据，并格式化
	 * @param id
	 * @return
	 */
	public List<TreeDto> findAllList(String id) {
		List<TreeDto> allList = new ArrayList<TreeDto>();
		Menu findOne = menuRepository.findOne(id);
		
		TreeDto treeDto = toTreeDto(findOne);
		
		System.out.println(treeDto);
		allList.add(treeDto);
		
		return allList;
	}
	
	public TreeDto toTreeDto(Menu m) {
		TreeDto treeDto = new TreeDto();
		
		treeDto.setId(m.getId());
		treeDto.setText(m.getMenuName());
		treeDto.setUrl(m.getUrl());
		
		List<TreeDto> list = new ArrayList<>();
		for (Menu menu : m.getMenus()) {
			if(menu!=null) {
				TreeDto treeDto2 = toTreeDto(menu);
				list.add(treeDto2);
			}
		}
		treeDto.setNodes(list);
		
		return treeDto;
	}
	
//	/**
//	 * 建立父子关系
//	 * @param allList
//	 * @return
//	 */
//	private List<TreeDto> getAllTree(List<TreeDto> allList) {
//		List<TreeDto> retList = new ArrayList<TreeDto>();
//		Map<String, TreeDto> fartherMap = new HashMap<String, TreeDto>();
//		
//		for(TreeDto t:allList){
//			fartherMap.put(t.getId(), t);
//		}
//		
//		for(TreeDto t:allList){
//			if (t.getPid()!=null && "0".equals(t.getPid())) {
//				retList.add(t);
//			}else{
//				String fartherId = t.getPid();
//				TreeDto farther = fartherMap.get(fartherId);
//				farther.getNodes().add(t);
//			}
//		}
//		
//		return retList;
//	}
	
	
}
