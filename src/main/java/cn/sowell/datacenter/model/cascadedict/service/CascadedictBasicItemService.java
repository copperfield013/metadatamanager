package cn.sowell.datacenter.model.cascadedict.service;

import java.util.List;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.cascadedict.criteria.CascadedictBasicItemCriteria;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;

public interface CascadedictBasicItemService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<CascadedictBasicItem> queryList(CascadedictBasicItemCriteria criteria, PageInfo pageInfo) throws Exception;

	/**
	 * 创建一个CascadedictBasicItem对象
	 * @param CascadedictBasicItem
	 */
	void create(CascadedictBasicItem basicItem) throws Exception;
	
	
	/**
	 * 保存或者是修改CascadedictBasicItem对象
	 * @param CascadedictBasicItem
	 */
	void saveOrUpdate(CascadedictBasicItem basicItem) throws Exception;


	CascadedictBasicItem getOne(Integer id) throws Exception;

	/**
	 * 更新一个CascadedictBasicItem对象
	 * @param demo
	 */
	void update(CascadedictBasicItem basicItem) throws Exception;

	/**
	 * 从数据库中删除一个CascadedictBasicItem对象
	 * @param id
	 */
	void delete(Integer id) throws Exception;
	
	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(CascadedictBasicItem creteria) throws Exception;

	/**
	 * 根据实体code查询子数据
	 * @param parentId
	 * @return
	 */
	List<CascadedictBasicItem> getChildByParentId(Integer entityCode) throws Exception;
	
	/**
	 * 获取所有父亲， 不管是一级父亲还是二级父亲,只要你这个父亲下面有孩子就获取
	 * @return
	 */
	List <CascadedictBasicItem> getParentAll() throws Exception;

	/**
	 * 获取级联属性枚举值的方法
	 * @return
	 */
	List<CascadedictBasicItem> getCascaseDictPitem() throws Exception;
	
}
