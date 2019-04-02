package cn.sowell.datacenter.model.dictionary.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemCodeGeneratorDao;

@Repository
public class BasicItemCodeGeneratorDaoImpl implements BasicItemCodeGeneratorDao {

	@Resource
	SessionFactory sFactory;
	
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	/**
	 * 	从数据库加载获取实体和属性前缀
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getBasicItemFixByDB() throws Exception {
		String sql = "SELECT prefix FROM `t_sc_basic_item_fix` WHERE using_state='1' ORDER BY id desc";
		List list = sFactory.getCurrentSession().createSQLQuery(sql).list();
		if (list.isEmpty()) {
			throw new Exception("t_sc_basic_item_fix不能没有数据");
		}
		return (String) list.get(0);
	}
}
