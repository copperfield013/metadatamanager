package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemAliasCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItemAlias;

public interface DictionaryBasicItemAliasService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<DictionaryBasicItemAlias> queryList(DictionaryBasicItemAliasCriteria criteria, PageInfo pageInfo);

	/**
	 * 创建一个DictionaryBasicItemAlias对象
	 * @param DictionaryBasicItemAlias
	 */
	void create(DictionaryBasicItemAlias basicItem);

	DictionaryBasicItemAlias getDictionaryBasicItemAlias(Long id);

	/**
	 * 更新一个DictionaryBasicItemAlias对象
	 * @param demo
	 */
	void update(DictionaryBasicItemAlias basicItem);

	/**
	 * 从数据库中删除一个DictionaryBasicItemAlias对象
	 * @param id
	 */
	void delete(Long id);
}
