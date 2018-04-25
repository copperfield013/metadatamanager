package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryParentItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryParentItem;

public interface DictionaryParentItemService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<DictionaryParentItem> queryList(DictionaryParentItemCriteria criteria, PageInfo pageInfo);

	/**
	 * 创建一个DictionaryParentItem对象
	 * @param DictionaryParentItem
	 */
	void create(DictionaryParentItem basicItem);

	DictionaryParentItem getDictionaryParentItem(Integer id);

	/**
	 * 更新一个DictionaryParentItem对象
	 * @param demo
	 */
	void update(DictionaryParentItem basicItem);

	/**
	 * 从数据库中删除一个DictionaryParentItem对象
	 * @param id
	 */
	void delete(Integer id);
	List<DictionaryParentItem> allList();
}
