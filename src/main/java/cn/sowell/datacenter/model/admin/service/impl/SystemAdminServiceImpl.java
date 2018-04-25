package cn.sowell.datacenter.model.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.datacenter.model.admin.dao.SystemAdminDao;
import cn.sowell.datacenter.model.admin.service.SystemAdminService;
import cn.sowell.datacenter.model.system.pojo.SystemAdmin;

@Service
public class SystemAdminServiceImpl implements SystemAdminService{

	@Resource
	SystemAdminDao aDao;
	
	
	@Override
	public SystemAdmin getSystemAdminByUserId(long userId) {
		return aDao.getSystemAdminByUserId(userId);
	}
	
}	
