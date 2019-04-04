package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *  引用属性  对应的对象
 * @author so-well
 *
 */
@Entity
@Table(name = "t_sc_bi_ref_attr")
public class BiRefAttr {
	
	  @Id
	  @Column(name="c_code")
	  @GenericGenerator(name = "system-uuid", strategy = "uuid")
	  private String code;//单独生成规则   存放t_sc_basic_item中引用属性的code

	  @Column(name="c_ref_code_recognition")
	  private String refCodeRecognition;
	
	  @Column(name="c_ref_code_show")
	  private String refCodeShow;
	  
	  @Column(name="c_can_add_new_ref")
	  private String canAddNewRef;
	  

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefCodeRecognition() {
		return refCodeRecognition;
	}

	public void setRefCodeRecognition(String refCodeRecognition) {
		this.refCodeRecognition = refCodeRecognition;
	}

	public String getRefCodeShow() {
		return refCodeShow;
	}

	public void setRefCodeShow(String refCodeShow) {
		this.refCodeShow = refCodeShow;
	}

	public String getCanAddNewRef() {
		return canAddNewRef;
	}

	public void setCanAddNewRef(String canAddNewRef) {
		this.canAddNewRef = canAddNewRef;
	}
}
