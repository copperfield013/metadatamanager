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
import cn.sowell.datacenter.model.dictionary.criteria.RecordRelationTypeCriteria;
import cn.sowell.datacenter.model.dictionary.dao.RecordRelationTypeDao;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;

@Repository
public class RecordRelationTypeDaoImpl implements RecordRelationTypeDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List<RecordRelationType> queryList(RecordRelationTypeCriteria criteria, PageInfo pageInfo) {
		String hql = "from RecordRelationType b";
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
		
		return new ArrayList<RecordRelationType>();
	}
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, String id) {
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
	public List<RecordRelationType> getEntityRelaByBitemId(String recordType) {
		String hql = "FROM RecordRelationType where rightRecordType =:rightRecordType";
		List list = sFactory.getCurrentSession().createQuery(hql).setParameter("rightRecordType", recordType).list();
		return list;
	}

	@Override
	public String getRecordRelaCode(String entityCode) {
		String sql = "SELECT SUBSTR(type_code, locate('R',type_code)+1) aa from t_c_record_relation_type WHERE type_code like '%R%' AND left_record_type=? ORDER BY  CAST(aa as SIGNED ) DESC";
		List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter(0, entityCode).list();
		if (list.isEmpty()) {
			return entityCode+ "R001";
		}
		
		String relaCode = (String) list.get(0);
		int code = Integer.parseInt(relaCode) + 1;
        String format = String.format("%03d", code);  
		
		return entityCode+"R"+format;
	}

}
