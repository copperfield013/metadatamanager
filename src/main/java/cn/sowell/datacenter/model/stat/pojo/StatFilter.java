package cn.sowell.datacenter.model.stat.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_sc_bi_stat_filter")
public class StatFilter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "filters_id")
	private Integer filtersId;

	@Column(name = "bie_code")
	private String bieCode;

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

	public String getBieCode() {
		return bieCode;
	}

	public void setBieCode(String bieCode) {
		this.bieCode = bieCode;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
	