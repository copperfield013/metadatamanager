package cn.sowell.datacenter.model.dictionary.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrMultiattrMappingCriteria;
import cn.sowell.datacenter.model.dictionary.dao.TowlevelattrMultiattrMappingDao;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;

@Repository
public class TowlevelattrMultiattrMappingDaoImpl implements TowlevelattrMultiattrMappingDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List<TowlevelattrMultiattrMapping> queryList(TowlevelattrMultiattrMappingCriteria criteria, PageInfo pageInfo) {
		String hql = "from TowlevelattrMultiattrMapping b";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		if(TextUtils.hasText(criteria.getName())){
			dQuery.appendCondition(" and b.name like :name")
					.setParam("name", "%" + criteria.getName() + "%");
		}
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), true, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), true, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		
		return new ArrayList<TowlevelattrMultiattrMapping>();
	}
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, Long id) {
		return sFactory.getCurrentSession().get(clazz, id);
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
	public TowlevelattrMultiattrMapping getOneByRelaMulAttr(String relatedMultiattribute) {
		String hql = "from TowlevelattrMultiattrMapping where relatedMultiattribute=:relatedMultiattribute";
		return (TowlevelattrMultiattrMapping) sFactory.getCurrentSession().createQuery(hql).setParameter("relatedMultiattribute", relatedMultiattribute).uniqueResult();
	}

}
