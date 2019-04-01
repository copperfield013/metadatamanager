package cn.sowell.datacenter.model.cascadedict.dao.impl;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.datacenter.model.cascadedict.dao.CascadedictSubsectionDao;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsection;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsectionChild;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItemCodeGenerator;

@Repository
public class CascadedictSubsectionDaoImpl implements CascadedictSubsectionDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, Integer id) throws Exception{
		return sFactory.getCurrentSession().get(clazz, id);
	}
	
	@Override
	public void update(Object pojo) throws Exception{
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void deleteByObj(Object pojo) throws Exception{
		sFactory.getCurrentSession().delete(pojo);
	}

	@Override
	public String createId() {
		return null;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		String sql = "DELETE FROM t_sc_cascadedict_subsection where id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public List<CascadedictSubsection> getSubSelectByParentId(Integer parentId) throws Exception {
		String hql = "from CascadedictSubsection where parentId=:parentId ORDER BY order";
		return sFactory.getCurrentSession().createQuery(hql).setParameter("parentId", parentId).list();
	}

	@Override
	public void delSubChildById(Integer id) throws Exception {
		String sql = "DELETE FROM t_sc_cascadedict_subsection_child where id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public List<CascadedictSubsectionChild> getSubChildByPid(Integer subsectionId) throws Exception {
		String hql = "from CascadedictSubsectionChild where subsectionId=:subsectionId ORDER BY order";
		return sFactory.getCurrentSession().createQuery(hql).setParameter("subsectionId", subsectionId).list();
	
	}

	
}
