package cn.sowell.datacenter.model.dictionary.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc.model.enun.ValueType;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.CascadeAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;

public interface BasicItemDao {
	/**
	 * 从数据库中根据条件分页查询列表
	 * @param criteria
	 * @param pageInfo
	 * @param dataType 不需要的数据类型
	 * @return
	 */
	List<BasicItem> queryList(BasicItemCriteria criteria, String dataType);

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
	 * 查询除了多值属性唯一编码和多值属性编辑时间
	 * @param parent
	 * @return
	 */
	List<BasicItem> getDataByPId(String parent, String dataType);

	/**
	 * 
	 * @param parent  父亲id
	 * @param groupName   分组名称
	 * @param dataType 可选，填入数字，怎获取分组下的所有的这个属性
	 * @return
	 */
	List getAttrByPidGroupName(String parent, String groupName, String dataType);
	
	void saveOrUpdate(Object obj, String flag)  throws Exception ;
	
	/**
	 * -- 查询需要创建的表
	 */
	List queryCreTab();
	
	/**
	 * -- 查询需要创建的枚举类型多选表
	 */
	List queryEnumMuliCreTab();
	
	/**
	 * -- 要新增的字段  
	 * @return
	 */
	List queryNewAddCol();
	
	/**
	 * 查询需要更新的字段语句
	 * 页面修改的属性， 需要重新  更新实体存储
	 * @return
	 */
	List queryEditCol();
	
	List queryCreRelaTabFun();
	
	/**
	 *  创建关系表
	 * @return
	 */
	List queryCreRelaTab();
	
	/**
	 * 创建实体对应的实体编辑时间表
	 * t_abce001_m
	 * @return
	 */
	List queryCreEntityEditTimeTab();
	
	
	/**
	 * 给多值属性表， 添加索引
	 * @return
	 */
	List queryCreRepeatTabIndex();
	
	/**
	 * 创建实体文件表F1
	 * @return
	 */
	List queryCreEntityFileTbaF1();
	
	/**
	 * 创建实体文件表F2
	 * @return
	 */
	List queryCreEntityFileTbaF2();
	
	/**
	 * 创建实体文件表F3
	 * @return
	 */
	List queryCreEntityFileTbaF3();
	
	/**
	 * 创建实体历史表H1
	 * @return
	 */
	List queryCreEntityTabH1();
	/**
	 * 创建实体历史表H2
	 * @return
	 */
	List queryCreEntityTabH2();
	
	
	/**
	 * 执行传进来的sql语句
	 * @param sql
	 */
	void excuteBySql(String sql);
	
	/**
	 * ##查询出当前实体生成的表，，
	 *  如果为null, 证明没生成表， 则标记为新增， 否则， 修改实体为再用
	 * @param entityCode
	 * @return
	 */
	public List queryEntityTable(String entityCode);
	
	/**
	 * ## 查询当前实体已经生成的字段
	 * @return
	 */
	public List queryEntityCol(String entityCode);
	
	
	/**
	 *  * 根据父id获取所有孩子
	 * @param parent
	 * @return
	 */
	public List<BasicItem> getChilByPid(String parent);

	/**
	 * 根据name和实体id， 查询普通属性中是否有相同的名字， 有则返回数字大于0无则返回0
	 * @param name
	 * @param entityId
	 * @return
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
	 * 获取级联属性的孩子
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
 * @throws Exception
 */
	void delCascaseAttrChild(String code, String casCode) throws Exception;
	
	/**
	 *  获取指定类型的属性
	 * @param parentCode   父code
	 * @param valueType    指定的类型
	 * @return
	 */
	List getAppointTypeAttr(String parentCode, ValueType valueType);
	
	/**
	 * 级联属性添加孩子的数量
	 * @param code
	 * @return
	 */
	BigInteger canAddChildCount(String code) throws Exception;

	/**
	 * 返回级联属性的孩子， 放进对象中, level 升序排列--必须升序
	 * @param code
	 * @return
	 */
	List getCascadeAttrChildPojo(String code, String casCode) throws Exception;
	
	void updateCasCadeLevel(String code, String casCade, int level) throws Exception;

	/**
	 * 根据实体code， 获取本实体下对应的标签
	 * @param code
	 * @return
	 * @throws Exception
	 */
	BasicItem getLableObj(String code) throws Exception;

	/**
	 * 获取所有实体
	 * @return
	 */
	List getAllEntity();
	/**
	 * 查询当前实体对应的标签的表生成语句
	 * @return
	 */
	public List queryCreLable();
	
	/**
	 * 根据实体id， 获取统计实体下， 唯一的一个分组
	 * @param parrentCode
	 * @return
	 */
	BasicItem getGroup(String parrentCode);
}
