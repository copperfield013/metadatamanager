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
import cn.sowell.datacenter.admin.controller.dictionary.Constants;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

@Repository
public class BasicItemDaoImpl implements BasicItemDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List<BasicItem> queryList(BasicItemCriteria criteria) {
		String hql = "from BasicItem b";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		if(TextUtils.hasText(criteria.getCnName())){
			dQuery.appendCondition(" and b.cnName like :cnName")
					.setParam("cnName", "%" + criteria.getCnName() + "%");
		}
		if(TextUtils.hasText(criteria.getDataType())){
			dQuery.appendCondition(" and b.dataType = :dataType")
					.setParam("dataType", criteria.getDataType());
		}
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), true, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), true, null);
			return query.list();
		}
		return new ArrayList<BasicItem>();
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
	public List<BasicItem> getDataByPId(String parent) {
		String sql = "from BasicItem WHERE parent=:parent";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("parent", parent).list();
		return list;
	}

	@Override
	public List getAttrByPidGroupName(String parent, String groupName) {
		String sql = "select new cn.sowell.datacenter.model.dictionary.pojo.BasicItem(code, cnName, dataType, usingState, groupName, parent) from BasicItem WHERE parent=:parent AND groupName=:groupName";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("parent", parent).setParameter("groupName", groupName).list();
		return list;
	}

	@Override
	public void saveOrUpdate(Object obj) {
		sFactory.getCurrentSession().saveOrUpdate(obj);
	}

}
