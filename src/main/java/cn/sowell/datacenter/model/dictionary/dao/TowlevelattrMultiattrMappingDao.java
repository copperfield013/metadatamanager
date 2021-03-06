package cn.sowell.datacenter.model.dictionary.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrMultiattrMappingCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;

public interface TowlevelattrMultiattrMappingDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<TowlevelattrMultiattrMapping> queryList(TowlevelattrMultiattrMappingCriteria criteria, PageInfo pageInfo);

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
	<T> T get(Class<T> clazz, Long id);

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
	 * 根据 重复类型的id   relatedMultiattribute
	 */
	TowlevelattrMultiattrMapping getOneByRelaMulAttr(String relatedMultiattribute);
}
