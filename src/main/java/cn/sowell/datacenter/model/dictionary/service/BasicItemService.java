package cn.sowell.datacenter.model.dictionary.service;

import java.math.BigInteger;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.demo.criteria.DemoCriteria;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
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
	 * 判断是过期还是正常
	 * @param basicItem
	 * @param statusStr
	 */
	void saveUsingStatus(BasicItem basicItem, String statusStr);
	
	/**
	 * 根据实体id， 获取多值属性， 普通属性的json数据和本实体的关系
	 * @param parentId
	 * @return
	 */
	public JSONObject getAttrByPid(String parentId);
	
	void saveOrUpdate(BasicItem obj, String flag, String comm);

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

	List<DictionaryBasicItem> getDictCode(Long id);

	void createTowLevel(Towlevelattr criteria);

	/**
	 * 根据cnName和实体id， 查询二级属性中是否有相同的名字， 有则返回数字大于0无则返回0
	 * @param cnName
	 * @param entityId
	 */
	BigInteger geSameCount(String cnName, String entityId);

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
}
