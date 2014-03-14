package com.norteksoft.portal.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.norteksoft.acs.entity.authorization.BusinessSystem;
import com.norteksoft.acs.service.authorization.BusinessSystemManager;
import com.norteksoft.portal.base.enumeration.StaticVariable;
import com.norteksoft.portal.entity.Widget;
import com.norteksoft.product.orm.Page;
import com.norteksoft.product.orm.hibernate.HibernateDao;
import com.norteksoft.product.util.ContextUtils;


@Repository
public class WidgetDao extends HibernateDao<Widget, Long>{
	@Autowired
	private BusinessSystemManager businessSystemManager;
	
	public List<Widget> getWidgets(){
		return this.find("FROM Widget w WHERE w.acquiescent=? and w.companyId=?",true,ContextUtils.getCompanyId());
	} 
	
	public Widget getWidgetByName(String code){
		return this.findUnique("FROM Widget w WHERE w.systemCode=? AND w.name=?",code,StaticVariable.NOTICE);
	}
	
	public Widget getWidgetBySystemCode(String code){
		return this.findUnique("FROM Widget w WHERE w.systemCode=?",code);
	}
	
	public Widget getWidgetByCode(String code){
		 List<Widget> ws= this.find("FROM Widget w WHERE w.code=? ",code);
		 if(ws.size()>0)return ws.get(0);
		 return null;
	}
	
	public Widget getWidgetByNames(String widgetName) throws Exception{
		return this.findUnique("FROM Widget t WHERE t.name=?", widgetName);
	}
	
	public Widget getWidgetById(Long widgetId){
		 return this.findUnique("FROM Widget t WHERE t.id=?", widgetId);
	}
	
	//根据系统id得到小窗体,不在任何公司中
	public List<Widget> getWidgetsBySystemCode(String systemCode){
		 return this.find("FROM Widget w WHERE w.systemCode=? ",systemCode);
	}
	//获得有权限的小窗体集合
	public Set<Widget> getAuthWidgets(){
		Set<Widget> widgetList = new HashSet<Widget>();
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct w FROM Widget w,WidgetRole wr,RoleUser ru ")
		.append(" where w.id=wr.widgetId and wr.roleId=ru.role.id and ru.deleted=false and w.systemCode in ")
		.append("(select bs.code from BusinessSystem bs where bs.id in ")
		.append("(select si.product.systemId from SubscriberItem si  join si.subsciber s  where  s.tenantId=? and si.invalidDate>?) and bs.deleted=false)")
		.append(" and ru.user.deleted=false and ru.user.id=? and w.companyId=?");
		List<Widget> userWidgets = this.find(hql.toString(),ContextUtils.getCompanyId(),new Date(),ContextUtils.getUserId(),ContextUtils.getCompanyId());
		widgetList.addAll(userWidgets);
		hql = new StringBuilder();
		hql.append("select distinct w FROM Widget w,WidgetRole wr,DepartmentUser du join du.department d  ")
		.append(" join d.roleDepartments rd")
		.append(" where w.id=wr.widgetId and wr.roleId=rd.role.id and du.deleted=false and d.deleted=false and rd.deleted=false and w.systemCode in ")
		.append("(select bs.code from BusinessSystem bs where bs.id in ")
		.append("(select si.product.systemId from SubscriberItem si  join si.subsciber s  where  s.tenantId=? and si.invalidDate>?) and bs.deleted=false)")
		.append(" and du.user.deleted=false and du.user.id=? and w.companyId=?");
		List<Widget> deptRoleWidgets = this.find(hql.toString(),ContextUtils.getCompanyId(),new Date(),ContextUtils.getUserId(),ContextUtils.getCompanyId());
		widgetList.addAll(deptRoleWidgets);
		hql = new StringBuilder();
		hql.append("select distinct w FROM Widget w,WidgetRole wr,User u ")
		.append(" where w.id=wr.widgetId and ")
		.append(" u.subCompanyId is not null and u.subCompanyId in ")
		.append("(select d.id from Department d join d.roleDepartments rd join rd.role r where d.company.id=? and d.branch=true and r.deleted=false and rd.deleted=false and d.deleted=false) ")
		.append(" and u.deleted=false ")
		.append(" and w.systemCode in ")
		.append("(select bs.code from BusinessSystem bs where bs.id in ")
		.append("(select si.product.systemId from SubscriberItem si  join si.subsciber s  where  s.tenantId=? and si.invalidDate>?))")
		.append(" and u.id=? and w.companyId=?");
		List<Widget> subCompanyRoleWidgets = this.find(hql.toString(),ContextUtils.getCompanyId(),ContextUtils.getCompanyId(),new Date(),ContextUtils.getUserId(),ContextUtils.getCompanyId());
		widgetList.addAll(subCompanyRoleWidgets);
		hql = new StringBuilder();
		hql.append("select distinct w FROM Widget w,WidgetRole wr,WorkgroupUser wu join wu.workgroup wg  ")
		.append(" join wg.roleWorkgroups rw")
		.append(" where w.id=wr.widgetId and wr.roleId=rw.role.id and wu.deleted=false and wg.deleted=false and rw.deleted=false and w.systemCode in ")
		.append("(select bs.code from BusinessSystem bs where bs.id in ")
		.append("(select si.product.systemId from SubscriberItem si  join si.subsciber s  where  s.tenantId=? and si.invalidDate>?) and bs.deleted=false)")
		.append(" and wu.user.deleted=false and wu.user.id=? and w.companyId=?");
		List<Widget> wgRoleWidgets = this.find(hql.toString(),ContextUtils.getCompanyId(),new Date(),ContextUtils.getUserId(),ContextUtils.getCompanyId());
		widgetList.addAll(wgRoleWidgets);
		return widgetList;
	}
	//获得不受权限控制的小窗体集合
	public List<Widget> getNoAuthWidgets(){
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct w FROM Widget w ")
		.append(" where w.systemCode in ")
		.append("(select bs.code from BusinessSystem bs where bs.id in ")
		.append("(select si.product.systemId from SubscriberItem si  join si.subsciber s  where  s.tenantId=? and si.invalidDate>?))")
		.append(" and w.id not in (select wr.widgetId from WidgetRole wr) and w.companyId=?");
		return this.find(hql.toString(),ContextUtils.getCompanyId(),new Date(),ContextUtils.getCompanyId());
	}
 
	public Widget getWidget(String systemCode,String url){
		 List<Widget> ws= this.find("FROM Widget w WHERE w.systemCode=? and w.url=? ",systemCode,url);
		 if(ws.size()>0)return ws.get(0);
		 return null;
	}
	 
	//根据系统id得到小窗体
	public void  getWidgetsBySystemCode(Page<Widget> widgetPage,String systemCode){
		 this.searchPageByHql(widgetPage,"from Widget w where w.systemCode=? and  w.companyId=?",systemCode,ContextUtils.getCompanyId());
	}
	 
	//根据系统id得到小窗体
	public List<Widget> getWidgetsBySystem(String systemIds,Long companyId){
		 StringBuilder hql=new StringBuilder("from Widget m where m.companyId=? ");
		 if(StringUtils.isNotEmpty(systemIds)&&systemIds.charAt(systemIds.length()-1)==',')systemIds=systemIds.substring(0,systemIds.length()-1);
		 Object[] values=new Object[1];
			if(StringUtils.isNotEmpty(systemIds)){
				hql.append(" and ");
				values=new Object[1+systemIds.split(",").length];
			}
			values[0]=companyId;
			if(StringUtils.isNotEmpty(systemIds)){
				String[] sysIds=systemIds.split(",");
				for(int i=0;i<sysIds.length;i++){
					if(StringUtils.isNotEmpty(sysIds[i])){
						if(i==0)hql.append("(");
						hql.append(" m.systemCode=? ");
						if(i<sysIds.length-1){
							hql.append(" or ");
						}
						if(i==sysIds.length-1)hql.append(")");
						BusinessSystem system=businessSystemManager.getBusiness(Long.parseLong(sysIds[i]));
						values[1+i]=system.getCode();
					}
				}
			}
		return find(hql.toString(), values);
	}
	
	//获得所有系统的小窗体
	public List<Widget> getDefaultWidgets(Long companyId){
		 List<BusinessSystem> systems=businessSystemManager.getAllSystems();
		 StringBuilder hql=new StringBuilder("from Widget m where m.companyId=? and (");
		 Object[] values=new Object[1+systems.size()];
		 values[0]=companyId;
		 for(int i=0;i<systems.size();i++){
			 hql.append(" m.systemCode=? or");
			 values[1+i]=systems.get(i).getCode();
		 }
		 hql.append(")");
		 hql.replace(hql.lastIndexOf("or"), hql.indexOf(")"), "");
		 
		 return find(hql.toString(), values);
	}
	
}
