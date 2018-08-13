package cn.sowell.datacenter.model.dictionary.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_sc_twolevelattr_multiattr_mapping")
public class TowlevelattrMultiattrMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "c_name")
	private String name;

	@Column(name = "c_related_multiattribute")
	private String relatedMultiattribute;// 对应t_c_basic_item的code-重复类型

	@Column(name = "c_dictionary_attr")
	private String dictionaryAttr;// 重复类型包含的枚举类型的属性

	@Column(name = "c_value_attr")
	private String valueAttr;// 重复类型下面的其中一个属性

	@Column(name = "c_using_state")
	private Integer usingState;
	
	@Transient  
	 List childList = null;
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRelatedMultiattribute() {
		return relatedMultiattribute;
	}

	public String getDictionaryAttr() {
		return dictionaryAttr;
	}

	public String getValueAttr() {
		return valueAttr;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRelatedMultiattribute(String relatedMultiattribute) {
		this.relatedMultiattribute = relatedMultiattribute;
	}

	public void setDictionaryAttr(String dictionaryAttr) {
		this.dictionaryAttr = dictionaryAttr;
	}

	public void setValueAttr(String valueAttr) {
		this.valueAttr = valueAttr;
	}

	public List getChildList() {
		return childList;
	}

	public void setChildList(List childList) {
		this.childList = childList;
	}

	public Integer getUsingState() {
		return usingState;
	}

	public void setUsingState(Integer usingState) {
		this.usingState = usingState;
	}
	
}
