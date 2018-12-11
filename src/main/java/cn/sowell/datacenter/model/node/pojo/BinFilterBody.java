package cn.sowell.datacenter.model.node.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sc_bin_filter_body")
public class BinFilterBody {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "node_type")
	private Integer dataType;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "abcattr_code")
	private String abcattrCode;
	
	@Column(name = "filter_type")
	private Integer filterType;
	
	@Column(name = "subdomain")
	private String subdomain;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "parent_id")
	private Integer parentId;
	
	@Column(name = "c_order")
	private Integer order;
	
	@Column(name = "opt")
	private Integer opt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbcattrCode() {
		return abcattrCode;
	}

	public void setAbcattrCode(String abcattrCode) {
		this.abcattrCode = abcattrCode;
	}

	public Integer getFilterType() {
		return filterType;
	}

	public void setFilterType(Integer filterType) {
		this.filterType = filterType;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getOpt() {
		return opt;
	}

	public void setOpt(Integer opt) {
		this.opt = opt;
	}

}
