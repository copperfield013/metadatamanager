package cn.sowell.datacenter.model.dictionary.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrMultiattrMappingCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;

public interface TowlevelattrMultiattrMappingService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<TowlevelattrMultiattrMapping> queryList(TowlevelattrMultiattrMappingCriteria criteria, PageInfo pageInfo);

	/**
	 * 创建一个TowlevelattrMultiattrMapping对象
	 * @param TowlevelattrMultiattrMapping
	 */
	void create(TowlevelattrMultiattrMapping criteria);

	TowlevelattrMultiattrMapping getTowlevelattrMultiattrMapping(Long id);

	/**
	 * 更新一个TowlevelattrMultiattrMapping对象
	 * @param demo
	 */
	void update(TowlevelattrMultiattrMapping criteria);

	/**
	 * 从数据库中删除一个TowlevelattrMultiattrMapping对象
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 根据 重复类型的id   relatedMultiattribute
	 */
	TowlevelattrMultiattrMapping getOneByRelaMulAttr(String relatedMultiattribute);

	void saveOrUpdate(TowlevelattrMultiattrMapping criteria);
	
	/**
	 * 获取二级属性的孩子
	 * @param id
	 * @return
	 */
	List<Towlevelattr> getListByMappingId(String id);
}