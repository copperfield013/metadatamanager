package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_sc_cascade_attr")
public class CascadeAttr {
	
	  @Id
	  @Column(name="c_code")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private String code;//单独生成规则
	  
	  @Column(name="c_cas_code")
	  private String casCode;
	 
	  @Column(name="c_level")
	  private String level;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the casCode
	 */
	public String getCasCode() {
		return casCode;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param casCode the casCode to set
	 */
	public void setCasCode(String casCode) {
		this.casCode = casCode;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	  
}