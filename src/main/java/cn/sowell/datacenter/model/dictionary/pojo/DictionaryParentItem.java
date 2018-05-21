package cn.sowell.datacenter.model.dictionary.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_c_dictionary_parent_item")
public class DictionaryParentItem {

	@Id
	@Column(name = "id")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private Integer id;

	@Column(name = "c_name")
	private String name;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	@JoinColumn(name="parent_id")
	private List<DictionaryBasicItem> dictBasicItemList;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
