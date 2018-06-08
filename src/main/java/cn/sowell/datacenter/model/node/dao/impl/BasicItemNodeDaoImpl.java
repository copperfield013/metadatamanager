package cn.sowell.datacenter.model.node.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.abc.mapping.node.NodeType;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.dao.BasicItemNodeDao;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;

@Repository
public class BasicItemNodeDaoImpl implements BasicItemNodeDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List<BasicItemNode> queryList(BasicItemNodeCriteria criteria, PageInfo pageInfo) {
		String hql = "from BasicItemNode b WHERE b.type = '1' AND b.parentId is NULL";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		
		if(TextUtils.hasText(criteria.getName())){
			dQuery.appendCondition(" and b.name like :name")
					.setParam("name", "%" + criteria.getName() + "%");
		}
		
		if(TextUtils.hasText(criteria.getAbcattr())){
			dQuery.appendCondition(" and b.abcattr like :abcattr")
					.setParam("abcattr", "%" + criteria.getAbcattr() + "%");
		}
		
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), false, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), false, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		
		return new ArrayList<BasicItemNode>();
	}
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, Integer id) {
		return sFactory.getCurrentSession().get(clazz, id);
	}
	
	@Override
	public void update(BasicItemNode pojo) {
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM t_c_basic_item_node WHERE id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public void delete(BasicItemNode bt) {
		sFactory.getCurrentSession().delete(bt);
	}

	@Override
	public List<BasicItemNode> getChildByPid(String parentId) {
		String hql = " FROM BasicItemNode WHERE parentId=:parentId";
		return	sFactory.getCurrentSession().createQuery(hql).setParameter("parentId", parentId).list();
	}

	@Override
	public void executeSql(String sql) {
		sFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public Integer getOrder(BasicItemNode basicItemNode) {
		List<BasicItemNode> list = null;
		Integer order;
		if (NodeType.ABC.getName().equals(basicItemNode.getType())) {//是一个实体
			String hql = " FROM BasicItemNode WHERE type=:type AND parentId is null ORDER BY order DESC";
			list = sFactory.getCurrentSession().createQuery(hql).setParameter("type", 1).list();
		} else {
			String hql = " FROM BasicItemNode WHERE parentId=:parentId  ORDER BY order DESC";
			list = sFactory.getCurrentSession().createQuery(hql).setParameter("parentId", basicItemNode.getParentId()).list();
		}
		
		if (list.isEmpty()) {
			order = 100;
		} else {
			order = (Integer) list.get(0).getOrder() + 100;
		}
		
		return order;
	}
}
