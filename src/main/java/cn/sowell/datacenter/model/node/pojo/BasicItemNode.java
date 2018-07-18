package cn.sowell.datacenter.model.node.pojo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.abc.mapping.node.NodeType;

import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.utils.FileManager;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_c_basic_item_node")
public class BasicItemNode {
	
	@ApiModelProperty(value="主键")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty(value = "类型")
	@Column(name = "type")
	private Integer type;

	@ApiModelProperty(value = "名称")
	@Column(name = "name")
	private String name;

	 @ApiModelProperty(value = "映射名称")
	@Column(name = "abcattr")
	private String abcattr;
	
	@Column(name="abcattr_code")
	private String abcattrCode;

	 @ApiModelProperty(value = "数据类型")
	@Column(name = "data_type")
	private String dataType;

	@Column(name = "subdomain")
	private String subdomain;

	@ApiModelProperty(value = "读，写， 并等")
	@Column(name = "opt")
	private String opt;

	@ApiModelProperty(value = "排序字段")
	@Column(name = "c_order")
	private Integer order;

	@ApiModelProperty(value = "父id")
	@Column(name = "parent_id")
	private String parentId;
	
	@ApiModelProperty(value = "controlType")
	@Column(name="c_control_type")
	private String controlType;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="parentId")
	private List<BasicItemNode> btNodeList = new ArrayList<BasicItemNode>();
	
	/**
	 * @return the btNodeList
	 */
	public List<BasicItemNode> getBtNodeList() {
		return btNodeList;
	}

	/**
	 * @param btNodeList the btNodeList to set
	 */
	public void setBtNodeList(List<BasicItemNode> btNodeList) {
		this.btNodeList = btNodeList;
	}

	public Integer getId() {
		return id;
	}

	public Integer getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getAbcattr() {
		return abcattr;
	}

	public String getDataType() {
		return dataType;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public String getOpt() {
		return opt;
	}

	public Integer getOrder() {
		return order;
	}

	public String getParentId() {
		return parentId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAbcattr(String abcattr) {
		this.abcattr = abcattr;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAbcattrCode() {
		return abcattrCode;
	}

	public void setAbcattrCode(String abcattrCode) {
		this.abcattrCode = abcattrCode;
	}
	
	/**
	 * @return the controlType
	 */
	public String getControlType() {
		return controlType;
	}

	/**
	 * @param controlType the controlType to set
	 */
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	/**
	 * 生成配置文件
	 * @param file
	 * @throws IOException
	 */
	public void getConfigFile(File file) throws IOException {
		String prefix = "  ";
		String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		FileManager.writeFileContent(file, head);
		head = "<ABC name=\""+this.getName()+"\" abcattr=\""+this.getAbcattr()+"\""+"\r\n"
				+ "	 class=\"\" xmlns=\"http://www.w3school.com.cn\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
		FileManager.writeFileContent(file, head);
		
		List<BasicItemNode> childNode = this.getBtNodeList();
		for (BasicItemNode basicItemNode : childNode) {
			createChild(basicItemNode, file, prefix);
		}
		
		String endStr = "</ABC>";
		FileManager.writeFileContent(file, endStr);
	}

	/**
	 * 创建ABC
	 * @param file
	 * @param bn
	 * @throws IOException
	 */
	private void createAbc(File file, BasicItemNode bn, String prefix) throws IOException {
		String str = "";
		str = prefix + "<ABC name=\""+bn.getName()+"\" abcattr=\""+bn.getAbcattr()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		
		//获取ABC的所有直系孩子
		List<BasicItemNode> childNode = bn.getBtNodeList();
		for (BasicItemNode basicItemNode : childNode) {
			createChild(basicItemNode, file, prefix);
		}
		String endStr = prefix + "</ABC>";
		FileManager.writeFileContent(file, endStr);
	}
	
	/**
	 * 根据本身type, 进行分流操作
	 * @param basicItemNode
	 * @throws IOException 
	 */
	private void createChild(BasicItemNode basicItemNode, File file, String prefix) throws IOException {
		prefix += "   ";
		NodeType nodeType = NodeType.getNodeType(basicItemNode.getType());
		switch (nodeType) {
		case ABC://只可能是关系下的ABC了
			createAbc(file, basicItemNode, prefix);
			break;
		case ATTRIBUTE:
			createAttribute(basicItemNode, file, prefix);
			break;
		case LABEL:
			createLabel(basicItemNode, file, prefix);
			break;
		case MULTIATTRIBUTE:
			createMultiattribute(basicItemNode, file, prefix);
			break;
		case RELATION:
			createRelation(basicItemNode, file, prefix);
			break;
		case ATTRGROUP:
			createAttrgroup(basicItemNode, file,prefix);
			break;
		case NONO:
			break;
		default:
			break;
		}
	}

	/**
	 * 生成关系
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createRelation(BasicItemNode basicItemNode, File file, String prefix) throws IOException {
		
		String str = prefix + "<relation name=\""+basicItemNode.getName()+"\" ops=\""+basicItemNode.getOpt()+"\"> ";
		FileManager.writeFileContent(file, str);
		
		List<BasicItemNode> childNode = basicItemNode.getBtNodeList();		
		for (BasicItemNode bn2 : childNode) {
			createChild(bn2, file, prefix);
		}
		str = prefix + "</relation>";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 创建属性组
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createAttrgroup(BasicItemNode basicItemNode, File file, String prefix) throws IOException {
		String str = prefix + "<attrgroup name=\""+basicItemNode.getName()+"\" ops=\""+basicItemNode.getOpt()+"\">";
		FileManager.writeFileContent(file, str);
		
		List<BasicItemNode> childNode = basicItemNode.getBtNodeList();
		for (BasicItemNode bn2 : childNode) {
			createChild(bn2, file, prefix);
		}
		
		str = prefix + "</attrgroup>";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成多值属性
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createMultiattribute(BasicItemNode basicItemNode, File file, String prefix) throws IOException {
		String str = prefix + "<multiattribute name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getAbcattr()+"\" ops=\""+basicItemNode.getOpt()+"\"> ";
		FileManager.writeFileContent(file, str);
		List<BasicItemNode> childNode = basicItemNode.getBtNodeList();	
		for (BasicItemNode bn2 : childNode) {
			createChild(bn2, file, prefix);
		}
		str = prefix + "</multiattribute>";	
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成LABEL
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createLabel(BasicItemNode basicItemNode, File file, String prefix) throws IOException {
		String str = prefix + "<label name=\""+basicItemNode.getName()+"\" subdomain=\""+basicItemNode.getSubdomain()+"\"	ops=\""+basicItemNode.getOpt()+"\" />";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成普通属性
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createAttribute(BasicItemNode basicItemNode, File file, String prefix) throws IOException {
		String str = prefix + "<attribute name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getAbcattr()+"\"  datatype=\""+basicItemNode.getDataType()+"\" ops=\""+basicItemNode.getOpt()+"\" />";
		FileManager.writeFileContent(file, str);
	}
	
}