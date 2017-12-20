//package com.taiji.manager;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.taiji.manager.entity.User;
//import com.taiji.manager.dto.DeptDto;
//import com.taiji.manager.service.DeptService;
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootConfiguration()
//public class InitExcel {
//	  @Autowired
//		private static DeptService deptService;
//   public static void main(String[] args) throws Exception {
//   //   String path = "/home/chixue/下载/智慧云SBU人员通讯录0811.xlsx";
//	     String path = "/home/chixue/下载/通讯录.xlsx";
//	     initDept(print(path));
//     }
//   
//   @Test
//	public void sendQA(){
//	   String path = "/home/chixue/下载/通讯录.xlsx";
//	    try {
//			print(path);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//   }
// 
//  @Autowired
//public   Map  print(String path) throws IOException{
//	  File file = new File(path);
//	    XSSFWorkbook xssfWorkbook = null;
//		try {
//			xssfWorkbook = new XSSFWorkbook(file);
//		} catch (InvalidFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
//
//	    int rowstart = xssfSheet.getFirstRowNum();
//	    int rowEnd = xssfSheet.getLastRowNum();
//	    Map deptUserMap=new HashMap();
//	    List dept=new ArrayList();
//	    for(int i=rowstart+1;i<=rowEnd;i++)
//	    {
//	    	User user=new User();
//	        XSSFRow row = xssfSheet.getRow(i);
//	        if(null == row) continue;
//	        int cellStart = row.getFirstCellNum();
//	        int cellEnd = row.getLastCellNum();
//	        String deptName="";
//	        for(int k=cellStart;k<=cellEnd;k++)
//	        {
//	            XSSFCell cell = row.getCell(k);
//	            if(null==cell) continue;
//	            String str="";
//	        
//	            switch (cell.getCellType())
//	            {
//	                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
//	                	str=cell.getNumericCellValue()+"";
//	             //       System.out.print(cell.getNumericCellValue()      + "   ");
//	                    break;
//	                case HSSFCell.CELL_TYPE_STRING: // 字符串\
//	                	str=cell.getStringCellValue()+"";
//	                //    System.out.print(cell.getStringCellValue() + "   ");
//	                    break;
//	                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
//	                	str=cell.getBooleanCellValue()+"";
//	                    System.out.println(cell.getBooleanCellValue()  + "   ");
//	                    break;
//	                case HSSFCell.CELL_TYPE_FORMULA: // 公式
//	                	str=cell.getCellFormula()+"";
//	                //    System.out.print(cell.getCellFormula() + "   ");
//	                    break;
//	                case HSSFCell.CELL_TYPE_BLANK: // 空值
//	                //    System.out.println(" ");
//	                    break;
//	                case HSSFCell.CELL_TYPE_ERROR: // 故障
//	                    System.out.println(" ");
//	                    break;
//	                default:
//	                 //   System.out.print("未知类型   ");
//	                    break;
//	            }
//	            if(k==1){
//	            	user.setUserName(str);
//	            	user.setLoginName(ConverPinYin.getPinYin(str));
//	            }else  if(k==0){
//	            	user.setPassword(str);
//	            }else  if(k==8){
//	            	user.setEmail(str);
//	            }else  if(k==9){
//	            	user.setPosition(str);
//	            }else  if(k==7){
//	            	user.setPhoneNum(str);
//	            }else  if(k==2){
//	            	user.setSex(str);
//	            }else  if(k==5){
//	            	deptName=str;
//	            	dept.add(str);
//	            }else  if(k==6){
//	            	String key=deptName+"_"+str;
//	           // 	key=str!=null?deptNamestr:deptName;
//	               	List list=new ArrayList();
//	            	if(deptUserMap.get(key)!=null){
//	            		list=(List)deptUserMap.get(key);
//	            	}
//	            	list.add(user.getUserName());
//	            	deptUserMap.put(key, list);
//	       
//	            }
//	        }
//	    }
//	    dept=removeDuplicate(dept);
//	 System.out.print(dept.isEmpty());
//	    	return deptUserMap;
//     }
///**
//   * 去掉重复
//   * @param list
//   * @return
//   */
//  public  List<String> removeDuplicate(List<String> list) {        
//		 HashSet<String> h = new  HashSet<String>(list);        
//		 list.clear();        
//		 list.addAll(h);        
//		 return list;     
//  }  
//  
//  
//  
//  
//  public static  void initDept(Map deptUserMap){
//      Set set = deptUserMap.entrySet();         
//      Iterator item = set.iterator();         
//      List<DeptDto> deptList=new ArrayList();
//      //初始化一级目录菜单
//      Map parentDept=new HashMap();
// 
//      while(item.hasNext()){      
//           Map.Entry<String, List> entry1=(Map.Entry<String, List>)item.next();    
//         
//           String deptName=entry1.getKey();
//           DeptDto dto=new DeptDto();
//          //dto.setDeptName(entry1.getKey());
//           
//           List list=entry1.getValue();
//           Iterator it1 = list.iterator();
//           while(it1.hasNext()){
//              System.out.println(it1.next());
//           }
//      }  
//  }
//}