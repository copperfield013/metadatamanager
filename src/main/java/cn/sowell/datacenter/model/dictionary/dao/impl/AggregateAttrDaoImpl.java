package cn.sowell.datacenter.model.dictionary.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.datacenter.model.dictionary.dao.AggregateAttrDao;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;

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
	
	@Override
	public Object getAggregateAttr(String code) {
		String sql = "SELECT * FROM `t_sc_bi_aggregate_attr` where c_code=:code";
		return  sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("code", code)
				.uniqueResult();
	}
	

}
