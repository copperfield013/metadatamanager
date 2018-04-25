package cn.sowell.datacenter.model.admin.dao;

import cn.sowell.datacenter.model.admin.pojo.AdminUser;

public interface AdminUserDao {

	AdminUser getUser(String username);

}
