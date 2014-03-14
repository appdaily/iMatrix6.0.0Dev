package com.norteksoft.acs.base.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.norteksoft.acs.entity.authorization.BranchAuthority;
import com.norteksoft.acs.entity.authorization.BusinessSystem;
import com.norteksoft.acs.entity.authorization.Role;
import com.norteksoft.acs.entity.organization.Department;
import com.norteksoft.acs.entity.organization.User;
import com.norteksoft.acs.entity.organization.Workgroup;
import com.norteksoft.acs.service.authorization.BranchAuthorityManager;
import com.norteksoft.acs.service.authorization.RoleManager;
import com.norteksoft.acs.service.authorization.StandardRoleManager;
import com.norteksoft.acs.service.organization.DepartmentManager;
import com.norteksoft.product.util.ContextUtils;
import com.norteksoft.product.util.JsTreeUtils;

public class ExportRole {
	
	private static final Log logger = LogFactory.getLog(ExportRole.class);
	
	public static void exportRole(OutputStream fileOut, List<BusinessSystem> systems, boolean isBranchAdmin){
		HSSFWorkbook wb;
	    try
	    {
			wb = new HSSFWorkbook();
	    	HSSFSheet sheet=wb.createSheet("role-user");
	        
	        HSSFFont boldFont = wb.createFont();
	        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	
	        HSSFCellStyle boldStyle = wb.createCellStyle();
	        boldStyle.setFont(boldFont);
	      
	        HSSFRow row = sheet.createRow(0);
	        HSSFCell cell0 = row.createCell(0);
	        cell0.setCellValue("系统");
	        cell0.setCellStyle(boldStyle);
	        HSSFCell cell1 = row.createCell(1);
	        cell1.setCellValue("角色");
	        cell1.setCellStyle(boldStyle);
	        HSSFCell cell2 = row.createCell(2);
	        cell2.setCellValue("用户/部门/工作组");
	        cell2.setCellStyle(boldStyle);
	        DepartmentManager departmentManager=(DepartmentManager)ContextUtils.getBean("departmentManager");
	        boolean containBranch=departmentManager.containBranches();
	        //系统-角色-用户/部门/工作组
	        if(isBranchAdmin){//分支机构管理员
	        	//所管理的分支机构
	        	BranchAuthorityManager branchAuthorityManager=(BranchAuthorityManager)ContextUtils.getBean("branchAuthorityManager");
				List<BranchAuthority> branchAuthoritys=branchAuthorityManager.getBranchByUser(ContextUtils.getUserId());
				Set<Long> branchesSet=new HashSet<Long>();
				for(BranchAuthority ba:branchAuthoritys){
					branchesSet.add(ba.getBranchesId());
					getSubBranches(ba.getBranchesId(),branchesSet);
				}
				for(BusinessSystem bs:systems){
					RoleManager roleManager=(RoleManager)ContextUtils.getBean("roleManager");
					List<Role> roles=roleManager.getRoleList(bs.getId(),branchesSet);
					fillCell(bs,roles,sheet,containBranch);
				}
	        }else{
	        	for(BusinessSystem bs:systems){
		        	StandardRoleManager standardRoleManager=(StandardRoleManager)ContextUtils.getBean("standardRoleManager");
		        	List<Role> roles = standardRoleManager.getRolesBySystemId(bs.getId());
		        	fillCell(bs,roles,sheet,containBranch);
		        }
	        }
	     
	        wb.write(fileOut);
	    }catch(IOException exception){
	    	logger.debug(exception.getStackTrace());
		} 
	}
	
	private static void getSubBranches(Long departmentId, Set<Long> branchesSet) {
		DepartmentManager departmentManager=(DepartmentManager)ContextUtils.getBean("departmentManager");
		List<Department> subDeptments=departmentManager.getSubDeptments(departmentId);
		for(Department d:subDeptments){
			if(d.getBranch()){
				branchesSet.add(d.getId());
			}
			getSubBranches(d.getId(), branchesSet);
		}
	}

	private static void fillCell(BusinessSystem businessSystem,List<Role> roles,HSSFSheet sheet,boolean containBranch){
		for(Role role:roles){
			RoleManager roleManager = (RoleManager)ContextUtils.getBean("roleManager");
			DepartmentManager departmentManager = (DepartmentManager)ContextUtils.getBean("departmentManager");
			
			List<User> users = roleManager.getCheckedUsersByRole(role.getId());
			List<Department> departments = departmentManager.getDepartmentsInRole(role.getId());
			List<Workgroup> workgroups = roleManager.getCheckedWorkgroupByRole(role.getId());
			
			for(User user : users){
				HSSFRow rowi = sheet.createRow(sheet.getLastRowNum()+1);
				HSSFCell celli0 = rowi.createCell(0);
				celli0.setCellValue(businessSystem.getName());
		        HSSFCell celli1 = rowi.createCell(1);
		        celli1.setCellValue(role.getName()+(containBranch?"("+role.getSubCompanyName()+")":""));
		        HSSFCell celli2 = rowi.createCell(2);
		        celli2.setCellValue(user.getName()+(containBranch?"("+user.getSubCompanyName()+")":""));
			}
			
			for(Department department : departments){
				HSSFRow rowi = sheet.createRow(sheet.getLastRowNum()+1);
				HSSFCell celli0 = rowi.createCell(0);
				celli0.setCellValue(businessSystem.getName());
		        HSSFCell celli1 = rowi.createCell(1);
		       celli1.setCellValue(role.getName()+(containBranch?"("+role.getSubCompanyName()+")":""));
		        HSSFCell celli2 = rowi.createCell(2);
		        celli2.setCellValue(department.getName()+(containBranch&&!department.getBranch()?"("+department.getSubCompanyName()+")":""));
			}
			
			for(Workgroup workgroup : workgroups){
				HSSFRow rowi = sheet.createRow(sheet.getLastRowNum()+1);
				HSSFCell celli0 = rowi.createCell(0);
				celli0.setCellValue(businessSystem.getName());
		        HSSFCell celli1 = rowi.createCell(1);
		       celli1.setCellValue(role.getName()+(containBranch?"("+role.getSubCompanyName()+")":""));
		        HSSFCell celli2 = rowi.createCell(2);
		        celli2.setCellValue(workgroup.getName()+(containBranch?"("+workgroup.getSubCompanyName()+")":""));
			}
			
		}
	}

}
