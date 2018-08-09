package cn.sowell.datacenter.model.cascadedict.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.cascadedict.criteria.CascadedictBasicItemCriteria;
import cn.sowell.datacenter.model.cascadedict.dao.CascadedictBasicItemDao;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;

@Repository
public class CascadedictBasicItemDaoImpl implements CascadedictBasicItemDao {

	private static final Type Integer = null;
	@Resource
	SessionFactory sFactory;

	@Override
	public List<CascadedictBasicItem> queryList(CascadedictBasicItemCriteria criteria, PageInfo pageInfo) throws Exception{
		String hql = "from CascadedictBasicItem b WHERE 1=1 and b.id != 0";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		
		if(TextUtils.hasText(criteria.getName())){
			dQuery.appendCondition(" and b.name like :name")
					.setParam("name", "%" + criteria.getName() + "%");
		}
		
		dQuery.appendCondition(" ORDER BY updateTime DESC");
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), false, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), false, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		return new ArrayList<CascadedictBasicItem>();
	}
	
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
	public void delete(Object pojo) throws Exception{
		sFactory.getCurrentSession().delete(pojo);
	}

	@Override
	public void delete(java.lang.Integer id) throws Exception{
		String sql = "DELETE FROM t_sc_cascadedict_basic_item where id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public List<CascadedictBasicItem> getChildByParentId(java.lang.Integer parentId) throws Exception{
		String hql = "from CascadedictBasicItem where parentId=:parentId ORDER BY order";
		return sFactory.getCurrentSession().createQuery(hql).setParameter("parentId", parentId).list();
	}
	
}
