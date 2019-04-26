package cn.sowell.datacenter.model.stat.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import cn.sowell.datacenter.model.stat.dao.StatFilterDao;
import cn.sowell.datacenter.model.stat.pojo.StatFilter;

@Repository
public class StatFilterDaoImpl implements StatFilterDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public List queryList(StatFilter criteria) throws Exception {
		return new ArrayList();
	}

	@Override
	public void insert(Object obj) throws Exception {
		sFactory.getCurrentSession().save(obj);
	}

	@Override
	public <T> T get(Class<T> clazz, Integer id) throws Exception {
		return sFactory.getCurrentSession().get(clazz, id);
	}

	@Override
	public void update(Object obj) throws Exception {
		sFactory.getCurrentSession().update(obj);
	}

	@Override
	public void delete(Object pojo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) throws Exception {
		String sql = "DELETE FROM t_sc_bi_stat_filter WHERE id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public StatFilter getOneByBieCode(String bieCode) throws Exception {
		String hql = "FROM StatFilter WHERE bieCode=:bieCode";
		return (StatFilter) sFactory.getCurrentSession().createQuery(hql).setParameter("bieCode", bieCode).uniqueResult();
	}

	
}
