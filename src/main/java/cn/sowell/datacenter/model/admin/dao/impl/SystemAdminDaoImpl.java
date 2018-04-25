package cn.sowell.datacenter.model.admin.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.datacenter.model.admin.dao.SystemAdminDao;
import cn.sowell.datacenter.model.system.pojo.SystemAdmin;

@Repository
public class SystemAdminDaoImpl implements SystemAdminDao{

	@Resource
	SessionFactory sFactory;
	@Override
	public SystemAdmin getSystemAdminByUserId(long userId) {
		String hql = "from SystemAdmin a where a.userId = :userId";
		Query query = sFactory.getCurrentSession().createQuery(hql);
		query.setLong("userId", userId);
		query.setMaxResults(1);
		return (SystemAdmin) query.uniqueResult();
	}
	
	
}
