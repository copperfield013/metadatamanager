package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_sc_twolevel_attr")
public class Towlevelattr {

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "c_code")
	private String code;

	@Column(name = "c_mapping_id")
	private String mappingId;

	@Column(name = "c_dictionary_code")
	private String dictionaryCode;
	
	/* @OneToOne(mappedBy = "towlevelattr")
	 protected BasicItem basicItem;*/
	@Transient
	private BasicItem basicItem;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String getMappingId() {
		return mappingId;
	}

	public String getDictionaryCode() {
		return dictionaryCode;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}

	/**
	 * @return the basicItem
	 */
	public BasicItem getBasicItem() {
		return basicItem;
	}

	/**
	 * @param basicItem the basicItem to set
	 */
	public void setBasicItem(BasicItem basicItem) {
		this.basicItem = basicItem;
	}

}
