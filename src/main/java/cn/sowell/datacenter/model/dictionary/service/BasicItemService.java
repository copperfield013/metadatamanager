package cn.sowell.datacenter.model.dictionary.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.CascadeAttr;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;

public interface BasicItemService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @return
	 */
	List<BasicItem> queryList(BasicItemCriteria criteria);

	/**
	 * 创建一个BasicItem对象
	 * @param BasicItem
	 */
	void create(BasicItem basicItem);

	
	BasicItem getBasicItem(String id);

	/**
	 * 更新一个BasicItem对象
	 * @param demo
	 */
	void update(BasicItem basicItem);

	/**
	 * 从数据库中删除一个BasicItem对象
	 * @param id
	 */
	void delete(BasicItem basicItem);
	/**
	 * 从数据库中删除一个BasicItem对象
	 * @param basicItem
	 */
	void twoDeleteItem(BasicItem basicItem);
	/**
	 * 判断是过期还是正常
	 * @param basicItem
	 * @param statusStr
	 */
	void saveUsingStatus(BasicItem basicItem, String statusStr) throws Exception;
	
	/**
	 * 根据实体id， 获取多值属性， 普通属性的json数据和本实体的关系
	 * @param parentId
	 * @return
	 */
	 Map<String, List> getAttrByPid(String parentId);
	
	void saveOrUpdate(BasicItem obj, String flag, String comm)  throws Exception ;

	/**
	 * 
	 * @param parent  父亲id
	 * @param groupName   分组名称
	 * @return
	 */
	List getAttrByPidGroupName(String parent, String groupName);
	
	/**
	 * 查询当前数据需要生成的表、字段， 并生成
	 */
	void createTabCol();
	
	/**
	 * 根据父亲的id获取孩子
	 * @param parent
	 * @return
	 */
	List<BasicItem> getDataByPId(String parent);

	List<CascadedictBasicItem> getDictCode(Long id) throws Exception;

	void createTowLevel(Towlevelattr criteria, String name);

	/**
	 * 根据name和实体id， 查询普通属性中是否有相同的名字， 有则返回数字大于0无则返回0
	 * @param cnName
	 * @param entityId
	 */
	BigInteger getTwoSameCount(String name, String entityId);
	
	/**
	 * 根据实体id， 获取当前实体下的所有普通属性， 
		//还有当前实体下的所有二级属性
	 * @param entityId
	 * @return
	 */
	public List getComm(String entityId);
	
	/**
	 * 根据左实体id， 获取与左实体有关系的右实体
	 * @param leftRecordType 左实体id
	 * @return
	 */
	public List<BasicItem> getEntityList(String leftRecordType);
	
	/**
	 * 获取实体的前缀，属性的前缀， 关系的前缀
	 * @return
	 */
	public Object[] getBasicItemFix() throws Exception;

	/**
	 * 这里获取级联属性的孩子
	 * @param code
	 * @return
	 */
	List getCascadeAttrChild(String code);

	/**
	 * 保存级联属性的孩子
	 * @param code
	 * @param casCode
	 * @param level
	 * @throws Exception
	 */
	void saveCascaseAttrChild(CascadeAttr cascadeAttr) throws Exception;

	/**
	 * 删除级联属性的孩子
	 * @param code
	 * @param casCode
	 */
	void delCascaseAttrChild(String code, String casCode) throws Exception;

	/**
	 * 根据实体id， 获取所有分组里面的级联属性
	 * @param entityId
	 * @return
	 */
	List getGroupCascaseAttr(String entityId);

	/**
	 * 根据多值属性的id，获取多值属性下的级联属性
	 * @param string
	 * @return
	 */
	List getMoreCascaseAttr(String parentId);

	/**
	 * 级联属性还可以添加几个孩子
	 * @param code
	 */
	BigInteger canAddChildCount(String code)  throws Exception;
}
