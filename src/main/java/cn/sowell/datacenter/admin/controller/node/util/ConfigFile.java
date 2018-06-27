package cn.sowell.datacenter.admin.controller.node.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.abc.mapping.node.NodeType;

import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;
import cn.sowell.datacenter.utils.FileManager;

public class ConfigFile {
	
	BasicItemNodeService basicItemNodeService;
	
	public ConfigFile(BasicItemNodeService basicItemNodeService) {
		this.basicItemNodeService = basicItemNodeService;
	}

		/**
		 * 创建ABC
		 * @param file
		 * @param bn
		 * @throws IOException
		 */
		public void createAbc(File file, BasicItemNode bn) throws IOException {
			String str = "";
			if (NodeType.ABC.equals(NodeType.getNodeType(bn.getType())) && bn.getParentId() == null) {
				str = "<ABC name=\""+bn.getName()+"\" abcattr=\""+bn.getAbcattr()+"\""+"\r\n"
						+ "	 class=\"\" xmlns=\"http://www.w3school.com.cn\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
			} else {
				str = "<ABC name=\""+bn.getName()+"\" abcattr=\""+bn.getAbcattr()+"\">"+"\r\n";
			}
			
			FileManager.writeFileContent(file, str);
			
			//获取ABC的所有直系孩子
			List<BasicItemNode> childNode = basicItemNodeService.getChildNode(String.valueOf(bn.getId()));
			for (BasicItemNode basicItemNode : childNode) {
				createChild(basicItemNode, file);
			}
			String endStr = "</ABC>";
			FileManager.writeFileContent(file, endStr);
		}
		
		/**
		 * 根据本身type, 进行分流操作
		 * @param basicItemNode
		 * @throws IOException 
		 */
		public void createChild(BasicItemNode basicItemNode, File file) throws IOException {
			NodeType nodeType = NodeType.getNodeType(basicItemNode.getType());
			switch (nodeType) {
			case ABC://只可能是关系下的ABC了
				createAbc(file, basicItemNode);
				break;
			case ATTRIBUTE:
				createAttribute(basicItemNode, file);
				break;
			case LABEL:
				createLabel(basicItemNode, file);
				break;
			case MULTIATTRIBUTE:
				createMultiattribute(basicItemNode, file);
				break;
			case RELATION:
				createRelation(basicItemNode, file);
				break;
			case ATTRGROUP:
				createAttrgroup(basicItemNode, file);
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
		private void createRelation(BasicItemNode basicItemNode, File file) throws IOException {
			
			String str = "<relation name=\""+basicItemNode.getName()+"\" ops=\""+basicItemNode.getOpt()+"\"> ";
			FileManager.writeFileContent(file, str);
			
			List<BasicItemNode> childNode = basicItemNodeService.getChildNode(String.valueOf(basicItemNode.getId()));		
			for (BasicItemNode bn2 : childNode) {
				createChild(bn2, file);
			}
			str = "</relation>";
			FileManager.writeFileContent(file, str);
		}

		/**
		 * 创建属性组
		 * @param basicItemNode
		 * @param file
		 * @throws IOException 
		 */
		private void createAttrgroup(BasicItemNode basicItemNode, File file) throws IOException {
			String str = "<attrgroup name=\""+basicItemNode.getName()+"\" ops=\""+basicItemNode.getOpt()+"\">";
			FileManager.writeFileContent(file, str);
			
			List<BasicItemNode> childNode = basicItemNodeService.getChildNode(String.valueOf(basicItemNode.getId()));		
			for (BasicItemNode bn2 : childNode) {
				createChild(bn2, file);
			}
			
			str = "</attrgroup>";
			FileManager.writeFileContent(file, str);
		}

		/**
		 * 生成多值属性
		 * @param basicItemNode
		 * @param file
		 * @throws IOException 
		 */
		private void createMultiattribute(BasicItemNode basicItemNode, File file) throws IOException {
			String str = "<multiattribute name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getAbcattr()+"\" ops=\""+basicItemNode.getOpt()+"\"> ";
			FileManager.writeFileContent(file, str);
			List<BasicItemNode> childNode = basicItemNodeService.getChildNode(String.valueOf(basicItemNode.getId()));		
			for (BasicItemNode bn2 : childNode) {
				createChild(bn2, file);
			}
			str = "</multiattribute>";	
			FileManager.writeFileContent(file, str);
		}

		/**
		 * 生成LABEL
		 * @param basicItemNode
		 * @param file
		 * @throws IOException 
		 */
		private void createLabel(BasicItemNode basicItemNode, File file) throws IOException {
			String str = "<label name=\""+basicItemNode.getName()+"\" subdomain=\""+basicItemNode.getSubdomain()+"\"	ops=\""+basicItemNode.getOpt()+"\" />";
			FileManager.writeFileContent(file, str);
		}

		/**
		 * 生成普通属性
		 * @param basicItemNode
		 * @param file
		 * @throws IOException 
		 */
		private void createAttribute(BasicItemNode basicItemNode, File file) throws IOException {
			String str = "<attribute name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getAbcattr()+"\"  datatype=\""+basicItemNode.getDataType()+"\" ops=\""+basicItemNode.getOpt()+"\" />";
			FileManager.writeFileContent(file, str);
		}
}
