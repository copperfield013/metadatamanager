package cn.sowell.datacenter.model.node.pojo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasicItem 实体和属性规则生成
 * @author so-well
 *
 */
@Entity
@Table(name = "t_sc_basic_item_code_generator")
public class BasicItemNodeGenerator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFormat() {
		return String.format("%03d", this.id); 
	}
	
	
	/**
	 * 输入前缀，组合需要的code
	 * @param prefix
	 * @return
	 */
	public String getPrefixCode(String prefix) {
		return prefix + getFormat();
	}
	
	/**
	 * 输入前缀组合实体的code
	 * @param prefix
	 * @return
	 */
	public String getEntityCode(String prefix){
		return prefix+getFormat();
	}
	
	/**
	 * 输入前缀，组合分组属性，多值，普通属性的code
	 * @param prefix
	 * @return
	 */
	public String getAttrCode(String prefix) {
		return prefix + getFormat();
	}
	
	/**
	 * 输入实体code 和 中缀，组合关系code
	 * @param entityCode
	 * @param infix
	 * @return
	 */
	public String getRelaCode(String entityCode, String  infix) {
		return entityCode + infix +getFormat();
	}

}