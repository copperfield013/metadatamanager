package cn.sowell.datacenter.model.stat.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.cascadedict.criteria.CascadedictBasicItemCriteria;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.stat.pojo.StatDf;
import cn.sowell.datacenter.model.stat.pojo.StatE;

public interface StatEService {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List queryList(StatE criteria) throws Exception;

	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(BasicItem basicItem, OneLevelItem oneLevelItem, Integer cascadedict,StatE obj, BiRefAttr biRefAttr) throws Exception;

	/**
	 * 从数据库中查找对应的pojo对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	<T> T get(Class<T> clazz, Integer id) throws Exception;

	/**
	 * 更新一个pojo对象
	 * @param demo
	 */
	void update(Object obj) throws Exception;

	
	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(String id, String entityId) throws Exception;
	
}
