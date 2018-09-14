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
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrCriteria;
import cn.sowell.datacenter.model.dictionary.dao.TowlevelattrDao;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;

@Repository
public class TowlevelattrDaoImpl implements TowlevelattrDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List<Towlevelattr> queryList(TowlevelattrCriteria criteria, PageInfo pageInfo) {
		String hql = "from Towlevelattr b";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		/*if(TextUtils.hasText(criteria.getName())){
			dQuery.appendCondition(" and b.aliasName like :aliasName")
					.setParam("aliasName", "%" + criteria.getName() + "%");
		}*/
		
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), true, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), true, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		
		return new ArrayList<Towlevelattr>();
	}
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, String code) {
		return sFactory.getCurrentSession().get(clazz, code);
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
	public List getListByMappingId(String mappingId) {
		
		String sql = "SELECT w.c_code, t.c_cn_name, t.c_using_state, w.c_dictionary_code, 	d.c_name FROM `t_sc_twolevel_attr` w "
				+ " inner join t_sc_basic_item t "
				+ " on w.c_code=t.c_code  "
				+ " left join t_sc_cascadedict_basic_item d on d.id=w.c_dictionary_code"
				+ " WHERE w.c_mapping_id=:mappingId";
		
		List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter("mappingId", mappingId).list();
		return list;
	}

}
