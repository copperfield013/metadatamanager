package cn.sowell.datacenter.model.node.pojo;

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

import com.abc.mapping.node.NodeType;

@Entity
@Table(name = "t_c_basic_item_node")
public class BasicItemNode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "type")
	private Integer type;

	@Column(name = "name")
	private String name;

	@Column(name = "abcattr")
	private String abcattr;

	@Column(name = "data_type")
	private String dataType;

	@Column(name = "subdomain")
	private String subdomain;

	@Column(name = "opt")
	private String opt;

	@Column(name = "c_order")
	private Integer order;

	@Column(name = "parent_id")
	private String parentId;
	
	public Integer getId() {
		return id;
	}

	public Integer getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getAbcattr() {
		return abcattr;
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

	public String getParentId() {
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

	public void setAbcattr(String abcattr) {
		this.abcattr = abcattr;
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

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}