package cn.sowell.datacenter.model.node.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.node.dao.BinFilterBodyDao;
import cn.sowell.datacenter.model.node.pojo.BinFilter;
import cn.sowell.datacenter.model.node.pojo.BinFilterBody;

@Repository
public class BinFilterBodyDaoImpl implements BinFilterBodyDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public void insert(Object pojo) throws Exception{
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
	public void delete(Integer id) throws Exception{
		String sql = "DELETE FROM t_sc_bin_filter_body WHERE id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public void delete(BinFilterBody bt) throws Exception{
		sFactory.getCurrentSession().delete(bt);
	}

	@Override
	public List<BinFilterBody> queryList(BinFilterBody criteria, PageInfo pageInfo) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(Object obj) throws Exception{
		sFactory.getCurrentSession().saveOrUpdate(obj);
	}

	@Override
	public BinFilter getBinFilterByNodeId(Integer parentNodeId) throws Exception {
		String hql = "FROM BinFilter WHERE parentNodeId=:parentNodeId";
		return (BinFilter) sFactory.getCurrentSession().createQuery(hql)
				.setParameter("parentNodeId", parentNodeId).uniqueResult();
	}

	@Override
	public List<BinFilterBody> getFilterBodyChild(Integer parentId) throws Exception {
		String hql = "FROM BinFilterBody WHERE parentId=:parentId";
		return  sFactory.getCurrentSession().createQuery(hql)
				.setParameter("parentId", parentId).list();
	}
}
