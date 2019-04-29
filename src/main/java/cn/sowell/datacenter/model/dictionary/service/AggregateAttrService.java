package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;

public interface AggregateAttrService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List queryList();

	/**
	 * 创建一个DictionaryMappingAlias对象
	 * @param DictionaryMappingAlias
	 */
	void create(AggregateAttr aggregateAttr);

	/**
	 * 更新一个DictionaryMappingAlias对象
	 * @param demo
	 */
	void update(AggregateAttr aggregateAttr);

	/**
	 * 从数据库中删除一个DictionaryMappingAlias对象
	 * @param id
	 */
	void delete(String code);
	
	AggregateAttr getOne(String code);
	
	void saveOrUpdate(AggregateAttr aggregateAttr);
}
