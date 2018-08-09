package cn.sowell.datacenter.model.dataservice.dao.impl;

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
import cn.sowell.datacenter.model.dataservice.criteria.ServiceBizzDataCriteria;
import cn.sowell.datacenter.model.dataservice.dao.ServiceBizzDataDao;
import cn.sowell.datacenter.model.dataservice.pojo.ServiceBizzData;

@Repository
public class ServiceBizzDataDaoImpl implements ServiceBizzDataDao {

	@Resource
	SessionFactory sFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceBizzData> queryList(ServiceBizzDataCriteria criteria, PageInfo pageInfo) {
		String hql = "from ServiceBizzData b";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		
		if(TextUtils.hasText(criteria.getName())){
			dQuery.appendCondition(" and b.name like :name")
					.setParam("name", "%" + criteria.getName() + "%");
		}
		
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), true, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), false, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		
		return new ArrayList<ServiceBizzData>();
	}
	
	@Override
	public void insert(ServiceBizzData pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, Integer id) {
		return sFactory.getCurrentSession().get(clazz, id);
	}
	
	@Override
	public void update(ServiceBizzData pojo) {
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM t_sb_service_bizzdata WHERE id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public void delete(ServiceBizzData obj) {
		sFactory.getCurrentSession().delete(obj);
	}

}
