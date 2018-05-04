package cn.sowell.datacenter.model.dictionary.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryMappingCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMapping;

public interface DictionaryMappingDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<DictionaryMapping> queryList(DictionaryMappingCriteria criteria, PageInfo pageInfo);

	/**
	 * 查询所有的
	 * @param criteria
	 * @return
	 */
	List<DictionaryMapping> queryListAll();
	
	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(Object obj);

	/**
	 * 从数据库中查找对应的pajo对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	<T> T get(Class<T> clazz, Integer id);

	/**
	 * 更新一个pojo对象
	 * @param demo
	 */
	void update(Object obj);

	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(Object pojo);
}
