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
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMappingAlias;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryParentItem;

@Repository
public class DictionaryMappingAliasDaoImpl implements DictionaryMappingAliasDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List queryList(DictionaryMappingAliasCriteria criteria, PageInfo pageInfo) {
		
		String sql = "SELECT 	b.id itemId, 	b.parent_id,	b.c_order,	b.c_name,	b.c_en_name,	b.c_status,"
				+ "	c.id aliasId,	c.mapping_id,	c.basic_item_id,	c.alias_name,	c.priority_level, d.c_name parent_name FROM"
				+ "	t_sc_cascadedict_basic_item b"
				+ " LEFT OUTER JOIN ( "
				+ " SELECT * from t_sc_cascadedict_mapping_alias  a "
				+ " WHERE mapping_id ='"+criteria.getMappingId()+"' ) c ON b.id= c.basic_item_id "
				+ " LEFT OUTER JOIN t_sc_cascadedict_basic_item d 	on b.parent_id=d.id "
				+ "WHERE  b.id != 0 ";
		DeferedParamQuery dQuery = new DeferedParamQuery(sql);
		
		if(TextUtils.hasText(criteria.getBtItemParentName())){
			dQuery.appendCondition(" and d.c_name like :parentName")
					.setParam("parentName", "%" + criteria.getBtItemParentName() + "%");
		}
		
		if(TextUtils.hasText(criteria.getBasicItemName())){
			dQuery.appendCondition(" and b.c_name like :bitemName")
					.setParam("bitemName", "%" + criteria.getBasicItemName() + "%");
		}
		
		if(TextUtils.hasText(criteria.getAliasName())){
			dQuery.appendCondition(" and c.alias_name like :aliasName")
					.setParam("aliasName", "%" + criteria.getAliasName() + "%");
		}
		
		dQuery.appendCondition(" ORDER BY b.c_update_time desc");
		
		Query countQuery = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createSQLQuery(sFactory.getCurrentSession(), false, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		
		return new ArrayList();
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
