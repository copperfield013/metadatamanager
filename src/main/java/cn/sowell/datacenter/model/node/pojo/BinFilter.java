package cn.sowell.datacenter.model.node.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sc_bin_filter")
public class BinFilter {
	
	
	public BinFilter() {
		
	}
	
	public BinFilter(Integer filtersId, Integer parentNodeId) {
		this.id= id;
		this.filtersId = filtersId;
		this.parentNodeId = parentNodeId;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "filters_id")
	private Integer filtersId;
	
	@Column(name = "parent_node_id")
	private Integer parentNodeId;
	
	@Column(name = "c_order")
	private Integer order;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFiltersId() {
		return filtersId;
	}

	public void setFiltersId(Integer filtersId) {
		this.filtersId = filtersId;
	}

	public Integer getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(Integer parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
