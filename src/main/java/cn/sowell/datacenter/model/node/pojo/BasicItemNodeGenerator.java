package cn.sowell.datacenter.model.node.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * BasicItem 实体和属性规则生成
 * @author so-well
 *
 */
@Entity
@Table(name = "t_c_basic_item_code_generator")
public class BasicItemNodeGenerator {
	public static final String IBTE = "IBTE";
	public static final String IBT = "IBT";
	
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
}