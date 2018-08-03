package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_sc_dictionary_basic_item")
public class DictionaryBasicItem {

	@ApiModelProperty(value="主键id")
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ApiModelProperty(value="父名称")
	@Column(name = "parent_name")
	private String parentName;
	
	@ApiModelProperty(value="父id")
	@Column(name = "parent_id")
	private Integer parentId;
	
	@ApiModelProperty(value="父亲下的孩子的排序code")
	@Column(name = "c_code")
	private Integer code;
	
	@ApiModelProperty(value="名称")
	@Column(name = "c_name")
	private String name;
	
	@ApiModelProperty(value="英文名称")
	@Column(name = "c_en_name")
	private String enName;
	
	@ApiModelProperty(value="状态")
	@Column(name = "c_status")
	private String status;

	public Integer getId() {
		return id;
	}

	public String getParentName() {
		return parentName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getEnName() {
		return enName;
	}

	public String getStatus() {
		return status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}