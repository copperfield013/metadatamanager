package cn.sowell.datacenter.model.dictionary.dao.impl;

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
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryBasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;

@Repository
public class DictionaryBasicItemDaoImpl implements DictionaryBasicItemDao {

	private static final Type Integer = null;
	@Resource
	SessionFactory sFactory;

	@Override
	public List<DictionaryBasicItem> queryList(DictionaryBasicItemCriteria criteria, PageInfo pageInfo) {
		String hql = "from DictionaryBasicItem b";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		if(TextUtils.hasText(criteria.getName())){
			dQuery.appendCondition(" and b.name like :name")
					.setParam("name", "%" + criteria.getName() + "%");
		}
		
		if(criteria.getParentId() != null && criteria.getParentId().SIZE >0){
			dQuery.appendCondition(" and b.parentId = :parentId")
					.setParam("parentId", criteria.getParentId(), StandardBasicTypes.INTEGER);
		}
		
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), true, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), true, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		return new ArrayList<DictionaryBasicItem>();
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

	@Override
	public List<DictionaryBasicItem> getDictBasicItemByParent(Integer parentId) {
		Session session = sFactory.getCurrentSession();
		String hql = "from DictionaryBasicItem where parentId=?";
		List<DictionaryBasicItem> dictBasicItemList = session.createQuery(hql).setParameter(0, parentId).list();
        
		
		return dictBasicItemList;
	}
	
	/**
	 * 获取c_code 生成规则
	 * @param parentId
	 * @return
	 */
	public Integer getCode(Integer parentId) {
		Session session = sFactory.getCurrentSession();
		String hql = "from DictionaryBasicItem where parentId=? ORDER BY c_code DESC";
		List<DictionaryBasicItem> dictBasicItemList = session.createQuery(hql).setParameter(0, parentId).list();
		
		if (dictBasicItemList.isEmpty() && dictBasicItemList.size()==0) {
			return 1;
		}
		
		Integer code = dictBasicItemList.get(0).getCode();
		return code+1;
	} 
	
}
