package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryMappingCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMapping;

public interface DictionaryMappingService {

	/**
	 * 根据条件对象查询分页
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
	 * 创建一个DictionaryMapping对象
	 * @param DictionaryMapping
	 */
	void create(DictionaryMapping criteria);

	DictionaryMapping getOne(Integer id);

	/**
	 * 更新一个DictionaryMapping对象
	 * @param demo
	 */
	void update(DictionaryMapping criteria);

	/**
	 * 从数据库中删除一个DictionaryMapping对象
	 * @param id
	 */
	void delete(Integer id);
}
