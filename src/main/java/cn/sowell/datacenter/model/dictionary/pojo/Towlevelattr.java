package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_c_towlevelattr")
public class Towlevelattr {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "c_name")
	private String name;

	@Column(name = "c_mapping_id")
	private String mappingId;

	@Column(name = "c_dictionary_code")
	private String dictionaryCode;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMappingId() {
		return mappingId;
	}

	public String getDictionaryCode() {
		return dictionaryCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}
	
}
