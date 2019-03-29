package cn.sowell.datacenter.model.dictionary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abc.util.RelationType;

@Entity
@Table(name = "t_sc_record_relation_type")
public class RecordRelationType {

	@Id
	@Column(name = "type_code")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String typeCode;// 主键规则待定

	@Column(name = "name")
	private String name;

	@Column(name = "left_record_type")
	private String leftRecordType;

	@Column(name = "right_record_type")
	private String rightRecordType;

	@Column(name = "reverse_code")
	private String reverseCode;
	
	@Column(name="using_state")
	private Integer usingState;
	
	@Column(name="relation_type")
	private String relationType;
	
	@Column(name="giant")
	private Integer giant; //  关系是巨型的
	
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getName() {
		return name;
	}

	public String getLeftRecordType() {
		return leftRecordType;
	}

	public String getRightRecordType() {
		return rightRecordType;
	}

	public String getReverseCode() {
		return reverseCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLeftRecordType(String leftRecordType) {
		this.leftRecordType = leftRecordType;
	}

	public void setRightRecordType(String rightRecordType) {
		this.rightRecordType = rightRecordType;
	}

	public void setReverseCode(String reverseCode) {
		this.reverseCode = reverseCode;
	}

	/**
	 * @return the usingState
	 */
	public Integer getUsingState() {
		return usingState;
	}

	/**
	 * @param usingState the usingState to set
	 */
	public void setUsingState(Integer usingState) {
		this.usingState = usingState;
	}

	public String getRelationType() {
		return RelationType.getRelationType(Integer.parseInt(relationType)).getCName();
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public Integer getGiant() {
		return giant;
	}

	public void setGiant(Integer giant) {
		this.giant = giant;
	}

}