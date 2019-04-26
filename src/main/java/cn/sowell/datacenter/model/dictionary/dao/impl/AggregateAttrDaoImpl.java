package cn.sowell.datacenter.model.dictionary.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.datacenter.model.dictionary.dao.AggregateAttrDao;

@Repository
public class AggregateAttrDaoImpl implements AggregateAttrDao {

	@Resource
	SessionFactory sFactory;

	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public void update(Object pojo) {
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void delete(Object pojo) {
		sFactory.getCurrentSession().delete(pojo);
	}

	@Override
	public List queryList() {
		String hql = "FROM AggregateAttr";
		return sFactory.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public <T> T get(Class<T> clazz, String code) {
		return sFactory.getCurrentSession().get(clazz, code);
	}

}
