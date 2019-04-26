package cn.sowell.datacenter.model.stat.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_sc_bi_stat_df")
public class StatDf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "type")
	private Integer type;
	
	@Column(name = "bi_code")
	private String biCode;
	
	@Column(name = "expression_id")
	private Integer expressionId;
	
	@Column(name = "c_order")
	private Integer order;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBiCode() {
		return biCode;
	}

	public void setBiCode(String biCode) {
		this.biCode = biCode;
	}

	public Integer getExpressionId() {
		return expressionId;
	}

	public void setExpressionId(Integer expressionId) {
		this.expressionId = expressionId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
	