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
@Table(name = "t_sc_bi_agg_expression")
public class StatExpression {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "type")
	private Integer type;

	@Column(name = "function_type")
	private Integer functionType;
	
	@Column(name = "parameter0")
	private String parameter0;
	
	@Column(name = "parameter1")
	private Integer parameter1;
	
	@Column(name = "parameter2")
	private Integer parameter2;
	
	@Column(name = "parameter3")
	private Integer parameter3;
	
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

	public Integer getFunctionType() {
		return functionType;
	}

	public void setFunctionType(Integer functionType) {
		this.functionType = functionType;
	}

	public String getParameter0() {
		return parameter0;
	}

	public void setParameter0(String parameter0) {
		this.parameter0 = parameter0;
	}

	public Integer getParameter1() {
		return parameter1;
	}

	public void setParameter1(Integer parameter1) {
		this.parameter1 = parameter1;
	}

	public Integer getParameter2() {
		return parameter2;
	}

	public void setParameter2(Integer parameter2) {
		this.parameter2 = parameter2;
	}

	public Integer getParameter3() {
		return parameter3;
	}

	public void setParameter3(Integer parameter3) {
		this.parameter3 = parameter3;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
	