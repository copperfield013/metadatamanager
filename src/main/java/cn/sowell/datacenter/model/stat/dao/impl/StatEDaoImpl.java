package cn.sowell.datacenter.model.stat.dao.impl;

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
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.stat.dao.StatEDao;
import cn.sowell.datacenter.model.stat.pojo.StatE;

@Repository
public class StatEDaoImpl implements StatEDao {

	@Resource
	SessionFactory sFactory;
	
	@Override
	public List queryList(StatE criteria) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT e.bie_code,	e.source_code,d.c_cn_name sourceName, b.*, o.c_data_type, o.c_dictionary_index, o.c_need_history")
		.append(" FROM t_sc_stat_e e")
		.append(" JOIN t_sc_basic_item b on b.c_code=e.bie_code ")
		.append(" JOIN t_sc_onelevel_item o on b.c_code=o.c_code")
		.append(" JOIN t_sc_basic_item d on d.c_code=e.source_code");
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public void insert(Object obj) throws Exception {
		sFactory.getCurrentSession().save(obj);
	}

	@Override
	public <T> T get(Class<T> clazz, Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object obj) throws Exception {
		sFactory.getCurrentSession().update(obj);
	}

	@Override
	public void delete(Object pojo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) throws Exception {
		String sql = "DELETE FROM t_sc_stat_e WHERE bie_code=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}
	
}
