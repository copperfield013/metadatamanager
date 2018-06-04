package cn.sowell.datacenter.model.dictionary.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.google.common.hash.HashCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

@ApiModel(value="basicItem对象", description="basicItem对象信息")
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
	
	 @ApiModelProperty(value="主键code", name="code")
	  @Id
	  @Column(name="c_code")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private String code;//单独生成规则
	  
	 @ApiModelProperty(value="中文名称", name="cnName")
	  @Column(name="c_cn_name")
	  private String cnName;
	  
	 @ApiModelProperty(value="英文名称", name="enName")
	  @Column(name="c_en_name")
	  private String enName;
	  
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
	  private String DataForm;//数据格式-目前没用到
	  
	 @ApiModelProperty(value="枚举id", name="dictParentId")
	  @Column(name="c_dict_parent_id")
	  private Integer dictParentId; //对应t_c_dictionary_parent_item
	  
	  @Column(name="c_dictionary_index")
	  private String dictionaryIndex;//暂无用
	  
	  @ApiModelProperty(value="item所在表名", name="tableName")
	  @Column(name="c_table_name")
	  private String tableName;//item所在的表
	  
	  @ApiModelProperty(value="自关联父id", name="parent")
	  @Column(name="c_parent")
	  private String parent;//自关联code
	  
	  @ApiModelProperty(value="所属表 描述", name="tableNameDescription")
	  @Column(name="c_table_name_description")
	  private String tableNameDescription;//所属表 描述
	  
	  @ApiModelProperty(value="状态-只增不删", name="usingState")
	  @Column(name="c_using_state")
	  private Integer usingState;//状态-只增不删

	  @ApiModelProperty(value="分组字段", name="groupName")
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
		if ("二进制型".equals(this.dataType)) {
			return "文件型";
		} else {
			return dataType;
		}
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