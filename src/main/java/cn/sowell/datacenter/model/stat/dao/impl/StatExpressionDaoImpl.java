package cn.sowell.datacenter.model.stat.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import cn.sowell.datacenter.model.stat.dao.StatExpressionDao;
import cn.sowell.datacenter.model.stat.pojo.StatExpression;

@Repository
public class StatExpressionDaoImpl implements StatExpressionDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public List queryList(StatExpression criteria) throws Exception {
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
		String sql = "DELETE FROM t_sc_stat_expression WHERE id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	
}
