package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sc_cascadedict_mapping")
public class DictionaryMapping {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="c_name")
	private String name;
	
	@Column(name="c_describe")
	private String describe;
	
	

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}