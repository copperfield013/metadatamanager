package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_sc_onelevel_item")
public class OneLevelItem {
	
	 @ApiModelProperty(value="主键code", name="code")
	  @Id
	  @Column(name="c_code")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private String code;//单独生成规则
	  
	 @ApiModelProperty(value="数据类型", name="dataType")
	  @Column(name="c_data_type")
	  private String dataType;//数据类型 枚举
	 
	 @ApiModelProperty(value="数据长度", name="dataRange")
	  @Column(name="c_data_range")
	  private String dataRange;//数据的长度
	  
	 @ApiModelProperty(value="数据类型编码", name="dataTypeCode")
	  @Column(name="c_data_type_code")
	  private String dataTypeCode;//数据类型编码
	  
	 @ApiModelProperty(value="数据格式", name="DataForm")
	  @Column(name="c_data_form")
	  private String dataForm;//数据格式-目前没用到
	  
	 @ApiModelProperty(value="枚举id", name="dictParentId")
	  @Column(name="c_dict_parent_id")
	  private Integer dictParentId; //对应t_c_dictionary_parent_item
	  
	  @Column(name="c_dictionary_index")
	  private String dictionaryIndex;//暂无用
	  
	  @ApiModelProperty(value="item所在表名", name="tableName")
	  @Column(name="c_table_name")
	  private String tableName;//item所在的表
	  
	@ApiModelProperty(value="所属表 描述", name="tableNameDescription")
	  @Column(name="c_table_name_description")
	  private String tableNameDescription;//所属表 描述
  
	 @ApiModelProperty(value="分组字段", name="groupName")
	  @Column(name="c_group_name")
	  private String groupName;//分组字段
	  
	  @ApiModelProperty(value="描述", name="description")
	  @Column(name="c_description")
	  private String description;
	  
	  @ApiModelProperty(value="引用类型关联实体code", name="refType")
	  @Column(name="c_ref_type")
	  private String refType;
	  
	  @OneToOne(mappedBy = "oneLevelItem")
	  private BasicItem basicItem;

	  
	  @ApiModelProperty(value="是否记录历史", name="needHistory")
	  @Column(name="c_need_history")
	  private Integer needHistory;
	  
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public void setDataForm(String dataForm) {
		dataForm = dataForm;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}


	/**
	 * @return the dataRange
	 */
	public String getDataRange() {
		return dataRange;
	}


	/**
	 * @return the dataTypeCode
	 */
	public String getDataTypeCode() {
		return dataTypeCode;
	}


	/**
	 * @return the dataForm
	 */
	public String getDataForm() {
		return dataForm;
	}


	/**
	 * @return the dictParentId
	 */
	public Integer getDictParentId() {
		return dictParentId;
	}


	/**
	 * @return the dictionaryIndex
	 */
	public String getDictionaryIndex() {
		return dictionaryIndex;
	}


	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}


	/**
	 * @return the tableNameDescription
	 */
	public String getTableNameDescription() {
		return tableNameDescription;
	}


	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @return the refType
	 */
	public String getRefType() {
		return refType;
	}


	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	/**
	 * @param dataRange the dataRange to set
	 */
	public void setDataRange(String dataRange) {
		this.dataRange = dataRange;
	}


	/**
	 * @param dataTypeCode the dataTypeCode to set
	 */
	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}


	/**
	 * @param dictParentId the dictParentId to set
	 */
	public void setDictParentId(Integer dictParentId) {
		this.dictParentId = dictParentId;
	}


	/**
	 * @param dictionaryIndex the dictionaryIndex to set
	 */
	public void setDictionaryIndex(String dictionaryIndex) {
		this.dictionaryIndex = dictionaryIndex;
	}


	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	/**
	 * @param tableNameDescription the tableNameDescription to set
	 */
	public void setTableNameDescription(String tableNameDescription) {
		this.tableNameDescription = tableNameDescription;
	}


	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @param refType the refType to set
	 */
	public void setRefType(String refType) {
		this.refType = refType;
	}


	/**
	 * @return the needHistory
	 */
	public Integer getNeedHistory() {
		return needHistory;
	}


	/**
	 * @param needHistory the needHistory to set
	 */
	public void setNeedHistory(Integer needHistory) {
		this.needHistory = needHistory;
	}

}