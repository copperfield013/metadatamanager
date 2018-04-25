package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_c_dictionary_basic_item_alias")
public class DictionaryBasicItemAlias {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "basic_item_id")
	private String basicItemId;
	
	@Column(name = "alias_name")
	private String aliasName;
	
	@Column(name = "priority_level")
	private Integer priorityLevel;

	public Long getId() {
		return id;
	}

	public String getBasicItemId() {
		return basicItemId;
	}

	public String getAliasName() {
		return aliasName;
	}

	public Integer getPriorityLevel() {
		return priorityLevel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBasicItemId(String basicItemId) {
		this.basicItemId = basicItemId;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
}