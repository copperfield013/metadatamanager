package cn.sowell.datacenter.model.comm.dao.impl;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.datacenter.model.comm.dao.CommDao;

@Repository
public class CommDaoImpl implements CommDao{

	@Resource
	SessionFactory sFactory;
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, String id) {
		return sFactory.getCurrentSession().get(clazz, id);
	}
	
	@Override
	public void update(Object pojo) {
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void delete(Object pojo) {
		sFactory.getCurrentSession().delete(pojo);
	}

}
