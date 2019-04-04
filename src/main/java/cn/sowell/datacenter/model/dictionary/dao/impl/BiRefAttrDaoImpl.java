package cn.sowell.datacenter.model.dictionary.dao.impl;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import cn.sowell.datacenter.model.dictionary.dao.BiRefAttrDao;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;

@Repository
public class BiRefAttrDaoImpl implements BiRefAttrDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public Object get(String id) {
		String sql = "SELECT * FROM `t_sc_bi_ref_attr` where c_code=:id";
		return  sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("id", id)
				.uniqueResult();
	}
	
	@Override
	public BiRefAttr getBiRefAttr(String code) {
		return sFactory.getCurrentSession().get(BiRefAttr.class, code);
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
