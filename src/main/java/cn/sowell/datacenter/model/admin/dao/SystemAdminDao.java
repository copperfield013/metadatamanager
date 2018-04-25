package cn.sowell.datacenter.model.admin.dao;

import cn.sowell.datacenter.model.system.pojo.SystemAdmin;


public interface SystemAdminDao {

	
	SystemAdmin getSystemAdminByUserId(long userId);
}
