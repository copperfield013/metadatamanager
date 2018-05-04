package cn.sowell.datacenter.model.dictionary.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryMappingAliasCriteria;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryMappingAliasDao;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMappingAlias;

@Repository
public class DictionaryMappingAliasDaoImpl implements DictionaryMappingAliasDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List<DictionaryMappingAlias> queryList(DictionaryMappingAliasCriteria criteria, PageInfo pageInfo) {
		String hql = "from DictionaryMappingAlias b";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		if(TextUtils.hasText(criteria.getAliasName())){
			dQuery.appendCondition(" and b.aliasName like :aliasName")
					.setParam("aliasName", "%" + criteria.getAliasName() + "%");
		}
		
		if(criteria.getBasicItemId() != null){
			dQuery.appendCondition(" and b.basicItemId =:basicItemId")
					.setParam("basicItemId", criteria.getBasicItemId() );
		}
		
		if(criteria.getMappingId() != null){
			dQuery.appendCondition(" and b.mappingId =:mappingId")
					.setParam("mappingId", criteria.getMappingId());
		}
		
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), true, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), true, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		
		return new ArrayList<DictionaryMappingAlias>();
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
	public void update(Object pojo) {
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void delete(Object pojo) {
		sFactory.getCurrentSession().delete(pojo);
	}

}
