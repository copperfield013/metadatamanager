package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_c_dictionary_basic_item")
public class DictionaryBasicItem {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "parent_name")
	private String parentName;
	
	@Column(name = "parent_id")
	private Integer parentId;
	
	@Column(name = "c_code")
	private Integer code;
	
	@Column(name = "c_name")
	private String name;
	
	@Column(name = "c_en_name")
	private String enName;
	
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