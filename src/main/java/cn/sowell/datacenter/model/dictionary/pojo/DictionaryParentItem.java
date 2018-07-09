package cn.sowell.datacenter.model.dictionary.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "c_name")
	private String name;

	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE, mappedBy="parentId")
	private List<DictionaryBasicItem> dictBasicItemList;

	/**
	 * @return the dictBasicItemList
	 */
	public List<DictionaryBasicItem> getDictBasicItemList() {
		return dictBasicItemList;
	}

	/**
	 * @param dictBasicItemList the dictBasicItemList to set
	 */
	public void setDictBasicItemList(List<DictionaryBasicItem> dictBasicItemList) {
		this.dictBasicItemList = dictBasicItemList;
	}

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
