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
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryParentItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryParentItemDao;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryParentItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryParentItem;

@Repository
public class DictionaryParentItemDaoImpl implements DictionaryParentItemDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List<DictionaryParentItem> queryList(DictionaryParentItemCriteria criteria, PageInfo pageInfo) {
		String hql = "from DictionaryParentItem b";
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
		
		return new ArrayList<DictionaryParentItem>();
	}
	
	@Override
	public List<DictionaryParentItem> allList() {
		String hql = "from DictionaryParentItem";
		List<DictionaryParentItem> list = sFactory.getCurrentSession().createQuery(hql).list();
		return list;
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
