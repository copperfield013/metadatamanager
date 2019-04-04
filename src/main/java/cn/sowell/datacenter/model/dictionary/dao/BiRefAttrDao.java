package cn.sowell.datacenter.model.dictionary.dao;

import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;

public interface BiRefAttrDao {

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
	Object get(String id);
	
	BiRefAttr getBiRefAttr(String code);
	
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
