package cn.sowell.datacenter.model.dictionary.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_c_basic_item")
public class BasicItem {
	
	public BasicItem() {
		
	}
	
	public BasicItem(String code, String cnName, String dataType, Integer usingState, String groupName, String parent) {
		this.code = code;
		this.cnName = cnName;
		this.dataType = dataType;
		this.usingState = usingState;
		this.groupName = groupName;
		this.parent = parent;
	}
	
	  @Id
	  @Column(name="c_code")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private String code;//单独生成规则
	  
	  @Column(name="c_cn_name")
	  private String cnName;
	  
	  @Column(name="c_en_name")
	  private String enName;
	  
	  @Column(name="c_data_type")
	  private String dataType;//数据类型 枚举
	  
	  @Column(name="c_data_range")
	  private String dataRange;//数据的长度
	  
	  @Column(name="c_data_type_code")
	  private String dataTypeCode;//数据类型编码
	  
	  @Column(name="c_data_form")
	  private String DataForm;//数据格式-目前没用到
	  
	  @Column(name="c_dict_parent_id")
	  private Integer dictParentId; //对应t_c_dictionary_parent_item
	  
	  @Column(name="c_dictionary_index")
	  private String dictionaryIndex;//暂无用
	  
	  @Column(name="c_table_name")
	  private String tableName;//item所在的表
	  
	  @Column(name="c_parent")
	  private String parent;//自关联code
	  
	  @Column(name="c_table_name_description")
	  private String tableNameDescription;//所属表 描述
	  
	  @Column(name="c_using_state")
	  private Integer usingState;//状态-只增不删

	  @Column(name="c_group_name")
	  private String groupName;//分组字段
	  
	 @Transient  
	 List<BasicItem> childList = null;
	 
	 @Transient  
	 private Long twoLevelAttr;
	 
	public String getCode() {
		return code;
	}

	public List getChildList() {
		return childList;
	}

	public void setChildList(List childList) {
		this.childList = childList;
	}

	public String getCnName() {
		return cnName;
	}

	public String getEnName() {
		return enName;
	}

	public String getDataType() {
		return dataType;
	}

	public String getDataRange() {
		return dataRange;
	}

	public String getDataTypeCode() {
		return dataTypeCode;
	}

	public String getDataForm() {
		return DataForm;
	}

	public Integer getDictParentId() {
		return dictParentId;
	}

	public String getDictionaryIndex() {
		return dictionaryIndex;
	}

	public String getTableName() {
		return tableName;
	}

	public String getParent() {
		return parent;
	}

	public String getTableNameDescription() {
		return tableNameDescription;
	}

	public Integer getUsingState() {
		return usingState;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setDataRange(String dataRange) {
		this.dataRange = dataRange;
	}

	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}

	public void setDataForm(String dataForm) {
		DataForm = dataForm;
	}

	public void setDictParentId(Integer dictParentId) {
		this.dictParentId = dictParentId;
	}

	public void setDictionaryIndex(String dictionaryIndex) {
		this.dictionaryIndex = dictionaryIndex;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setTableNameDescription(String tableNameDescription) {
		this.tableNameDescription = tableNameDescription;
	}

	public void setUsingState(Integer usingState) {
		this.usingState = usingState;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getTwoLevelAttr() {
		return twoLevelAttr;
	}

	public void setTwoLevelAttr(Long twoLevelAttr) {
		this.twoLevelAttr = twoLevelAttr;
	}
}