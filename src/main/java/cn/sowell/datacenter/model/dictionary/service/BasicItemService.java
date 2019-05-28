package cn.sowell.datacenter.model.dictionary.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.abc.model.enun.ValueType;

import cn.sowell.copframe.dto.ajax.AjaxPageResponse;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.pojo.CascadeAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.utils.Message;

public interface BasicItemService {

	/**
	 * 根据条件对象查询分页
	 * @param criteria
	 * @param pageInfo
	 * @param dataType 不需要的属性类型
	 * @return
	 */
	List<BasicItem> queryList(BasicItemCriteria criteria, String dataType);

	/**
	 * 创建一个BasicItem对象
	 * @param CascadedictSubsection
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
	void delete(BasicItem basicItem) throws Exception;;
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
	/**
	 * 
	 * @param obj
	 * @param flag
	 * @param comm
	 * @param cascadedict   生成实体，选择一个字典标签
	 * @throws Exception
	 */
	 BasicItem saveOrUpdate(BasicItem obj, Integer cascadedict, BiRefAttr biRefAttr, AggregateAttr aggregateAttr)  throws Exception ;

	/**
	 * 根据实体code， 分组code， 获取分组下的属性
	 * @param parent  父亲id
	 * @param groupName   分组名称
	 * @param dataType 获取分组下指定类型的属性
	 * @return
	 */
	List getAttrByPidGroupName(String parent, String groupName, String dataType);
	
	/**
	 * 查询当前数据需要生成的表、字段， 并生成
	 */
	void createTabCol();
	
	/**
	 * 根据父亲的id获取孩子
	 * @param parent
	 * @return
	 */
	List<BasicItem> getDataByPId(String parent, String dataType);

	List<CascadedictBasicItem> getDictCode(Long id) throws Exception;

	void createTowLevel(Towlevelattr criteria, String name) throws Exception;

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
	CascadeAttr saveCascaseAttrChild(String code, String cnName, String description) throws Exception;
	
	/**
	 * 保存级联属性的孩子
	 * @param code
	 * @param casCode
	 * @throws Exception
	 */
	CascadeAttr saveCascaseAttrChild(String code, String casCode) throws Exception;

	/**
	 * 删除级联属性的孩子
	 * @param code
	 * @param casCode
	 */
	void deleteCascaseAttrChild(String code, String casCode) throws Exception;

	
	/**
	 * 	获取指定类型的属性
	 * @param parentCode   父code
	 * @param valueType    指定的类型
	 * @return
	 */
	List getAppointTypeAttr(String parentCode, ValueType valueType);

	/**
	 * 级联属性还可以添加几个孩子
	 * @param code
	 */
	BigInteger canAddChildCount(String code)  throws Exception;
	
	/**
	 * 根据实体code， 获取本实体下对应的标签
	 * @param code
	 * @return
	 * @throws Exception
	 */
	BasicItem getLableObj(String code) throws Exception;

	List getAllEntity();

	/**
	 * 根据实体id， 获取统计实体下， 唯一的一个分组
	 * @param parrentCode
	 * @return
	 */
	BasicItem getGroup(String parrentCode);
	
	
	/**
	 * 操作实体前的检查操作
	 * @param code 
	 * @return
	 * @throws Exception
	 */
	Message check(String code) throws Exception;
	
	/**
	 * 获取属性所对应的实体code， 例如： XFJDE001
	 * @return
	 */
	String getEntityCode(String parentEntityCode);
	
	/**
	 * 创建标签对象
	 * @param obj
	 * @param cascadedict
	 * @return
	 * @throws Exception
	 */
	public BasicItem createLable(BasicItem obj, Integer cascadedict) throws Exception;
	
	
	/**
	 * 创建实体编辑时间的属性
	 * @param obj
	 * @return
	 */
	public BasicItem createRecordEditeTime(BasicItem obj);
	
	/**
	 * 生成文件的伴生属性
	 * @param obj
	 */
	public void fileAssociatProper(BasicItem obj);
}
