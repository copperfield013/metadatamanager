package cn.sowell.datacenter.model.cascadedict.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 级联字典的code生成
 * @author so-well
 *
 */
@Entity
@Table(name = "t_sc_cascadedict_code_generator")
public class CascadedictCodeGenerator {
	
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
	
	/*public String getFormat() {
		return String.format("%03d", this.id); 
	}*/
}