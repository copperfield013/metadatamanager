package cn.sowell.datacenter.model.stat.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import cn.sowell.datacenter.model.stat.dao.StatDfDao;
import cn.sowell.datacenter.model.stat.pojo.StatDf;

@Repository
public class StatDfDaoImpl implements StatDfDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public List queryList(StatDf criteria) throws Exception {
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
		String sql = "DELETE FROM t_sc_stat_df WHERE id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public List getStatDfList(String bieCode) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT d.id statDfId, d.type, d.bi_code,  b.c_cn_name, b.c_en_name, b.c_parent, b.c_using_state, o.*")
		.append(" FROM `t_sc_basic_item` b ")
		.append(" join t_sc_stat_df d on b.c_code=d.bi_code ")
		.append("  join t_sc_bi_onelevel o on  b.c_code=o.c_code")
		.append(" WHERE b.c_parent=:bieCode");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).setParameter("bieCode", bieCode).list();
	}
	
}
