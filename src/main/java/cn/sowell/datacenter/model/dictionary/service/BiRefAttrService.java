package cn.sowell.datacenter.model.dictionary.service;

import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;

public interface BiRefAttrService {

	/**
	 * 创建一个BiRefAttr
	 * @param 
	 */
	void create(BiRefAttr biRefAttr);

	BiRefAttr getOne(String code);

	/**
	 * 更新一个BiRefAttr对象
	 * @param demo
	 */
	void update(BiRefAttr biRefAttr);

	/**
	 * 从数据库中删除一个BiRefAttr对象
	 * @param id
	 */
	void delete(Object pojo);
	
	void saveOrUpdate(BiRefAttr biRefAttr);
}
