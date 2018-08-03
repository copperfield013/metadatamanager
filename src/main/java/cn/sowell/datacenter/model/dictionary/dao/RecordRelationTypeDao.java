package cn.sowell.datacenter.model.dictionary.dao;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.RecordRelationTypeCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;

public interface RecordRelationTypeDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<RecordRelationType> queryList(RecordRelationTypeCriteria criteria, PageInfo pageInfo);

	/**
	 * 根据实体id， 求出实体的关系
	 * @param recordType
	 * @return
	 */
	List<RecordRelationType> getEntityRelaByBitemId(String recordType);
	
	/**
	 * 根据左实体id， 和右实体id， 求出左右实体共同的关系
	 * @param recordType
	 * @return
	 */
	List<RecordRelationType> getEntityRelaByBitemId(String leftRecordType, String rightRecordType);
	
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
	<T> T get(Class<T> clazz, String id);

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
	 * 生成关系code的规则
	 */
	String getRecordRelaCode(String entityCode) throws Exception;
}
