package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryMappingAliasCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMappingAlias;

public interface DictionaryMappingAliasService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List queryList(DictionaryMappingAliasCriteria criteria, PageInfo pageInfo);

	/**
	 * 创建一个DictionaryMappingAlias对象
	 * @param DictionaryMappingAlias
	 */
	void create(DictionaryMappingAlias basicItem);

	DictionaryMappingAlias getOne(Integer id);

	/**
	 * 更新一个DictionaryMappingAlias对象
	 * @param demo
	 */
	void update(DictionaryMappingAlias basicItem);

	/**
	 * 从数据库中删除一个DictionaryMappingAlias对象
	 * @param id
	 */
	void delete(Integer id);
}
