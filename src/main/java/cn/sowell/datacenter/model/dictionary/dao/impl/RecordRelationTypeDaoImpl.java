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
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.node.pojo.BasicItemNodeGenerator;

@Repository
public class RecordRelationTypeDaoImpl implements RecordRelationTypeDao {

	@Resource
	SessionFactory sFactory;
	
	@Resource
	BasicItemService basicItemService;

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
		String sql = "SELECT t1.type_code, t1.name,t3.c_cn_name as left_record_type,"
				+ "  t4.c_cn_name as right_record_type, t2.name as reverse_code, t1.using_state "
				+ " FROM t_sc_record_relation_type t1"
				+ " LEFT JOIN t_sc_record_relation_type t2"
				+ " ON t1.reverse_code=t2.type_code"
				+ " LEFT JOIN t_sc_basic_item t3"
				+ " ON t1.left_record_type=t3.c_code"
				+ " LEFT JOIN t_sc_basic_item t4 "
				+ " ON t1.right_record_type=t4.c_code"
				+ "  WHERE t1.left_record_type=:leftRecordType";
		List list = sFactory.getCurrentSession().createSQLQuery(sql).addEntity(RecordRelationType.class).setParameter("leftRecordType", recordType).list();
		return list;
	}
	
	@Override
	public List<RecordRelationType> getEntityRelaByBitemId(String leftRecordType, String rightRecordType) {
		String hql = "FROM RecordRelationType where rightRecordType =:rightRecordType AND leftRecordType=:leftRecordType AND usingState=1";
		List list = sFactory.getCurrentSession().createQuery(hql).setParameter("leftRecordType", leftRecordType).setParameter("rightRecordType", rightRecordType).list();
		return list;
	}

	@Override
	public String getRecordRelaCode(String entityCode) throws Exception {
		BasicItemNodeGenerator btNg = new BasicItemNodeGenerator();
		sFactory.getCurrentSession().save(btNg);
		Object[] basicItemFix = basicItemService.getBasicItemFix();
		return btNg.getRelaCode(entityCode, (String)basicItemFix[3]);
	}

}
