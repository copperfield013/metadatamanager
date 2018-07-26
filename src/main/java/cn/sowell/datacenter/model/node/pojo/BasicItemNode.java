package cn.sowell.datacenter.model.node.pojo;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_c_basic_item_node")
public class BasicItemNode {
	
	@ApiModelProperty(value="主键")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty(value = "类型")
	@Column(name = "type")
	private Integer type;

	@ApiModelProperty(value = "名称")
	@Column(name = "name")
	private String name;

	/*@ApiModelProperty(value = "映射名称")
	@Column(name = "abcattr")
	private String abcattr;*/
	
	/*@Column(name="abcattr_code")
	private String abcattrCode;*/

	@ApiModelProperty(value = "数据类型")
	@Column(name = "data_type")
	private String dataType;

	@Column(name = "subdomain")
	private String subdomain;

	@ApiModelProperty(value = "读，写， 并等")
	@Column(name = "opt")
	private String opt;

	@ApiModelProperty(value = "排序字段")
	@Column(name = "c_order")
	private Integer order;

	@ApiModelProperty(value = "父id")
	@Column(name = "parent_id")
	private Integer parentId;
	
	 @Fetch(value=FetchMode.SELECT)
	 @NotFound(action=NotFoundAction.IGNORE)
	 @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
	 @JoinColumn(name = "abcattr_code")
	 private BasicItem basicItem;
	
	/*@OneToMany(fetch=FetchType.LAZY, mappedBy="parentId")
	private List<BasicItemNode> btNodeList = new ArrayList<BasicItemNode>();*/

	public Integer getId() {
		return id;
	}

	public Integer getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDataType() {
		return dataType;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public String getOpt() {
		return opt;
	}

	public Integer getOrder() {
		return order;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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