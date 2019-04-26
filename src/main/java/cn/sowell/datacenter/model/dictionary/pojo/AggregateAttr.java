package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_sc_bi_aggregate_attr")
public class AggregateAttr {
	
	@Id
	@Column(name = "c_code")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String code;

	@Column(name = "type")
	private Integer type;
	
	//basicItem   实体的code， 多值属性的code， 关系code逗号分隔
	@Column(name="rel_code")
	private String relCode;

	//表达式id
	@Column(name = "expression_id")
	private Integer expressionId;

	//过滤条件id
	@Column(name = "filters_id")
	private Integer filtersId;

	@Column(name = "need_materialized")
	private Integer needMaterialized;
	
	@Column(name = "exe_opportunity")
	private Integer exeOpportunity;
	
	
	public AggregateAttr() {}

	public AggregateAttr(String code, Integer type, String relCode, Integer expressionId, Integer filtersId,
			Integer needMaterialized, Integer exeOpportunity) {
		this.code = code;
		this.type = type;
		this.relCode = relCode;
		this.expressionId = expressionId;
		this.filtersId = filtersId;
		this.needMaterialized = needMaterialized;
		this.exeOpportunity = exeOpportunity;
	}

	public String getRelCode() {
		return relCode;
	}

	public void setRelCode(String relCode) {
		this.relCode = relCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}
		
	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getExpressionId() {
		return expressionId;
	}

	public void setExpressionId(Integer expressionId) {
		this.expressionId = expressionId;
	}

	public Integer getFiltersId() {
		return filtersId;
	}

	public void setFiltersId(Integer filtersId) {
		this.filtersId = filtersId;
	}

	public Integer getNeedMaterialized() {
		return needMaterialized;
	}

	public void setNeedMaterialized(Integer needMaterialized) {
		this.needMaterialized = needMaterialized;
	}

	public Integer getExeOpportunity() {
		return exeOpportunity;
	}

	public void setExeOpportunity(Integer exeOpportunity) {
		this.exeOpportunity = exeOpportunity;
	}
	
}