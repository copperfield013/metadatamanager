package cn.sowell.datacenter.model.cascadedict.service;

import java.util.List;

import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsection;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsectionChild;

public interface CascadedictSubsectionService {


	/**
	 * 根据父id，  获取subSelection的数据
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<CascadedictSubsection> getSubSelectByParentId(String parentId) throws Exception;
	
	/**
	 * 根据父subSelectionId，  获取subChild
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<CascadedictSubsectionChild> getSubChildByPid(String subsectionId) throws Exception;
	
	/**
	 * 创建一个CascadedictSubsection对象
	 * @param CascadedictSubsection
	 */
	void create(CascadedictSubsection subsection) throws Exception;

	CascadedictSubsection getOne(String id) throws Exception;

	/**
	 * 更新一个CascadedictSubsection对象
	 * @param demo
	 */
	void update(CascadedictSubsection subsection) throws Exception;

	/**
	 * 从数据库中删除一个CascadedictSubsection对象
	 * @param id
	 */
	void deleteByObj(Object id) throws Exception;
	
	/**
	 * 从数据库中删除一个CascadedictSubsection对象
	 * @param id
	 */
	void deleteById(String id) throws Exception;
	
	/**
	 * 从数据库中删除一个CascadedictSubsectionChild对象
	 * @param id
	 */
	void delSubChildById(String id) throws Exception;
	
	/**
	 * 保存或者是修改CascadedictSubsection对象
	 * @param CascadedictSubsection
	 */
	void saveOrUpdate(CascadedictSubsection subsection) throws Exception;
	
	/**
	 * 保存或者是修改CascadedictSubsectionChild对象
	 * @param CascadedictSubsection
	 */
	void saveOrUpSubChild(CascadedictSubsectionChild subChild) throws Exception;
}
