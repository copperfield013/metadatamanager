package cn.sowell.datacenter.model.dictionary.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;

public interface DictionaryBasicItemDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<DictionaryBasicItem> queryList(DictionaryBasicItemCriteria criteria, PageInfo pageInfo);

	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(Object obj);

	/**
	 * 从数据库中查找对应的pojo对象
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

	/**
	 * 更具ParentId查询数据
	 * @param parentId
	 * @return
	 */
	List<DictionaryBasicItem> getDictBasicItemByParent(Integer parentId);
	
	/**
	 * 获取c_code 生成规则
	 * @param parentId
	 * @return
	 */
	public Integer getCode(Integer parentId);

	DictionaryBasicItem getBasicItemByParentAndCode(Integer dictParentId, Integer dictionaryCode);

	List<DictionaryBasicItem> queryAllList();
}
