package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;

public interface DictionaryBasicItemService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<DictionaryBasicItem> queryList(DictionaryBasicItemCriteria criteria, PageInfo pageInfo);

	/**
	 * 创建一个DictionaryBasicItem对象
	 * @param DictionaryBasicItem
	 */
	void create(DictionaryBasicItem basicItem);

	DictionaryBasicItem getDictionaryBasicItem(Integer id);

	/**
	 * 更新一个DictionaryBasicItem对象
	 * @param demo
	 */
	void update(DictionaryBasicItem basicItem);

	/**
	 * 从数据库中删除一个DictionaryBasicItem对象
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 根据父id 查询孩子
	 * @param id
	 * @return
	 */
	List<DictionaryBasicItem> getDictBasicItemByParent(Integer parentId);
}
