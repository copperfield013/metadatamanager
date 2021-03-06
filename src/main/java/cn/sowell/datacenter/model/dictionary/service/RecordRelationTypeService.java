package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.RecordRelationTypeCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;

public interface RecordRelationTypeService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<RecordRelationType> queryList(RecordRelationTypeCriteria criteria, PageInfo pageInfo);

	/**
	 * 根据实体id， 求出实体所有的关系
	 * @param recordType
	 * @return
	 */
	List<RecordRelationType> getEntityRelaByBitemId(String recordType);
	
	/**
	 * 根据左实体id， 和关系类型， 求出本实体中特定关系类型的关系
	 * @param recordType
	 * @return
	 */
	List<RecordRelationType> getRelaByType(String leftRecordType, String relationType);
	
	/**
	 * 创建一个RecordRelationType对象
	 * @param RecordRelationType
	 */
	void create(RecordRelationType basicItem);

	RecordRelationType getRecordRelationType(String id);

	/**
	 * 更新一个RecordRelationType对象
	 * @param demo
	 */
	void update(RecordRelationType basicItem);

	/**
	 * 从数据库中删除一个RecordRelationType对象
	 * @param id
	 */
	void delete(String id);

	/**
	 *保存关系
	 * @param lefRrecordType
	 * @param rightRrecordType
	 */
	void saveRelation(RecordRelationType lefRrecordType, RecordRelationType rightRrecordType, String symmetry) throws Exception;
	
	/**
	 * 根据左实体id， 和右实体id， 求出左右实体共同的关系
	 * @param recordType
	 * @return
	 */
	List<RecordRelationType> getEntityRelaByBitemId(String leftRecordType, String rightRecordType);

	void saveStatus(String typeCode, boolean isPastDue) throws Exception;
}
