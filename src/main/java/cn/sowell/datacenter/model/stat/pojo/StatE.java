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
@Table(name = "t_sc_stat_e")
public class StatE {
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "bie_code")
	private String bieCode;

	@Column(name = "source_code")
	private String sourceCode;

	public String getBieCode() {
		return bieCode;
	}

	public void setBieCode(String bieCode) {
		this.bieCode = bieCode;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
}
	