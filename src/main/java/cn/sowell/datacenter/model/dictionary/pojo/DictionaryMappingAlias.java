package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sc_cascadedict_mapping_alias")
public class DictionaryMappingAlias {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "mapping_id")
	private Integer mappingId;

	@Column(name="basic_item_id")
	private Integer basicItemId;

	@Column(name = "alias_name")
	private String aliasName;

	@Column(name = "priority_level")
	private Integer priorityLevel;

	public Integer getId() {
		return id;
	}

	public Integer getMappingId() {
		return mappingId;
	}


	public String getAliasName() {
		return aliasName;
	}

	public Integer getPriorityLevel() {
		return priorityLevel;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public Integer getBasicItemId() {
		return basicItemId;
	}

	public void setBasicItemId(Integer basicItemId) {
		this.basicItemId = basicItemId;
	}
}