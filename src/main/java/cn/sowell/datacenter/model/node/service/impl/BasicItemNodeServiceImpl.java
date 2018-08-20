package cn.sowell.datacenter.model.node.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.dao.BasicItemNodeDao;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;
import cn.sowell.datacenter.utils.FileManager;

import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;

@Service
public class BasicItemNodeServiceImpl implements BasicItemNodeService {

	@Resource
	BasicItemNodeDao basicItemNodeDao;
	
	@Resource
	SessionFactory sFactory;
	
	@Override
	public List<BasicItemNode> queryList(BasicItemNodeCriteria criteria, PageInfo pageInfo) {
		return basicItemNodeDao.queryList(criteria, pageInfo);
	}

	@Override
	public void saveOrUpdate(BasicItemNode basicItemNode) {
		//判断是添加，还是修改， 生成排序的值
		NodeOpsType nodeOpsType = NodeOpsType.getNodeOpsType(Integer.parseInt(basicItemNode.getOpt()));
		
		String opt = nodeOpsType.getName();
		basicItemNode.setOpt(opt);
		if (basicItemNode.getId() == null) {//添加
			//需要生成排序的值
			//排序值的生成规则的书写
			Integer order = basicItemNodeDao.getOrder(basicItemNode);
			basicItemNode.setOrder(order);
			basicItemNodeDao.insert(basicItemNode);
		} else {//修改
			
			BasicItemNode btNode = basicItemNodeDao.get(BasicItemNode.class, basicItemNode.getId());
			basicItemNode.setOrder(btNode.getOrder());
			sFactory.getCurrentSession().evict(btNode);
			basicItemNodeDao.update(basicItemNode);
		}
	}

	@Override
	public BasicItemNode getOne(Integer id) {
		return basicItemNodeDao.get(BasicItemNode.class, id);
	}

	@Override
	public void update(BasicItemNode basicItemNode) {
		basicItemNodeDao.update(basicItemNode);
	}

	@Override
	public void delete(Integer id, boolean isDelChil) {
		BasicItemNode btn = basicItemNodeDao.get(BasicItemNode.class, id);
		NodeType nodeType = NodeType.getNodeType(btn.getType());
		switch (nodeType) {
			case ATTRGROUP:
				if (!(true == isDelChil)) {
					//只删除分组， 不删除孩子
					//获取所有孩子， 给孩子更改父亲， 父亲就是分组的父亲
					
					List<BasicItemNode> pchil = basicItemNodeDao.getChildByPid(btn.getParentId());
					BasicItemNode btend = pchil.get(pchil.size()-1);
					int order = btend.getOrder();
					
					List<BasicItemNode> childByPid = basicItemNodeDao.getChildByPid(btn.getId());
					for (BasicItemNode basicItemNode : childByPid) {
						basicItemNode.setParentId(btn.getParentId());
						//给孩子换父亲， 并把父亲的所有孩子重新排序
						order += 100;
						basicItemNode.setOrder(order);
						
						basicItemNodeDao.update(basicItemNode);
					}
				}
			case ABC:
			case ATTRIBUTE:
			case LABEL:
			case MULTIATTRIBUTE:
			case CASATTRIBUTE:
			case RELATION:
				basicItemNodeDao.delete(btn);
				break;
			case NONO:
				break;
		}
	}

	
	@Override
	public void nodeSort(BasicItemNode current, String beforeId, String afterId) {
			//第一个孩子， 没有前驱， 没有后继
		if (beforeId.isEmpty()&& afterId.isEmpty()) {//没有前驱， 没有后继, 父亲的第一个孩子
				current.setOrder(100);
		} else if (beforeId.isEmpty()&& !afterId.isEmpty()) {//没有前驱， 但是有后继
			BasicItemNode afterNode = basicItemNodeDao.get(BasicItemNode.class, Integer.parseInt(afterId));
			Integer order = afterNode.getOrder() +100;
			current.setOrder(order);
		}else if (!beforeId.isEmpty() && afterId.isEmpty()) {//没有后继，但是有前驱
			BasicItemNode beforeNode = basicItemNodeDao.get(BasicItemNode.class, Integer.parseInt(beforeId));
			Integer order = beforeNode.getOrder()/2;
			current.setOrder(order);
		} else if (!beforeId.isEmpty() && !afterId.isEmpty()) {
			BasicItemNode beforeNode = basicItemNodeDao.get(BasicItemNode.class, Integer.parseInt(beforeId));
			BasicItemNode afterNode = basicItemNodeDao.get(BasicItemNode.class, Integer.parseInt(afterId));
			Integer order = (beforeNode.getOrder() + afterNode.getOrder()) / 2;
			current.setOrder(order);
		}
		
		basicItemNodeDao.update(current);
	}

	@Override
	public void excuExtend(Integer parentId) {
		List<String> noteSort = basicItemNodeDao.getNoteSort(parentId);
		
		for (String sql : noteSort) {
			basicItemNodeDao.executeSql(sql);
		}
	}

	@Override
	public List<BasicItemNode> getChildNode(Integer nodeId) {
		return basicItemNodeDao.getChildByPid(nodeId);
	}

	@Override
	public boolean check(BasicItemNode basicItemNode) {
		
		List<String> nameList = null;
		if (basicItemNode.getId() == null) {
			nameList = basicItemNodeDao.getNameByPid(basicItemNode);
		} else {
			nameList = basicItemNodeDao.getNameByPid(basicItemNode);
			BasicItemNode btItemNode = basicItemNodeDao.get(BasicItemNode.class, basicItemNode.getId());
			nameList.remove(btItemNode.getName());
		}
		
		return nameList.contains(basicItemNode.getName());
	}

	@Override
	public List<BasicItemNode> getAllAbc() {
		return basicItemNodeDao.getAllAbc();
	}

	@Override
	public List<BasicItemNode> getAttribute(String abcId) {
		return basicItemNodeDao.getAttribute(abcId);
	}

	@Override
	public BasicItemNode getAbc(String name) {
		return basicItemNodeDao.getAbc(name);
	}

	
	@Override
	public String getRelaNodeChil(Integer parentId, String id, Integer type) {
		BasicItemNode relaNodeChil = basicItemNodeDao.getRelaNodeChil(parentId, id, type);
		if (relaNodeChil != null) {
			return "true";
		}
		
		return "false";
	}

	@Override
	public List<BasicItemNode> getAllData() throws Exception {
		// TODO Auto-generated method stub
		return basicItemNodeDao.getAllData();
	}

	@Override
	public void getConfigFile(File file, BasicItemNode btn) throws IOException {
		String prefix = "  ";
		String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		FileManager.writeFileContent(file, head);
		head = "<"+NodeType.ABC.getName()+" name=\""+btn.getName()+"\" abcattr=\""+btn.getBasicItem().getCnName()+"\""+"\r\n"
				+ "	 class=\"\" xmlns=\"http://www.w3school.com.cn\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
		FileManager.writeFileContent(file, head);
		
		List<BasicItemNode> btNodeList = basicItemNodeDao.getChildByPid(btn.getId());
		for (BasicItemNode basicItemNode : btNodeList) {
			createChild(basicItemNode, file, prefix);
		}
		
		String endStr = "</"+NodeType.ABC.getName()+">";
		FileManager.writeFileContent(file, endStr);
	}
	
	/**
	 * 创建ABC
	 * @param file
	 * @param bn
	 * @throws IOException
	 */
	private void createAbc(File file, BasicItemNode bn, String prefix, NodeType nodeType) throws IOException {
		String str = "";
		str = prefix + "<"+nodeType.getName()+" name=\""+bn.getName()+"\" abcattr=\""+bn.getBasicItem().getCnName()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		
		//获取ABC的所有直系孩子
		List<BasicItemNode> btNodeList = basicItemNodeDao.getChildByPid(bn.getId());
		for (BasicItemNode basicItemNode : btNodeList) {
			createChild(basicItemNode, file, prefix);
		}
		String endStr = prefix + "</"+nodeType.getName()+">";
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
			createAbc(file, basicItemNode, prefix, nodeType);
			break;
		case ATTRIBUTE:
			createAttribute(basicItemNode, file, prefix, nodeType);
			break;
		case LABEL:
			createLabel(basicItemNode, file, prefix, nodeType);
			break;
		case MULTIATTRIBUTE:
			createMultiattribute(basicItemNode, file, prefix, nodeType);
			break;
		case RELATION:
			createRelation(basicItemNode, file, prefix, nodeType);
			break;
		case ATTRGROUP:
			createAttrgroup(basicItemNode, file,prefix, nodeType);
			break;
		case CASATTRIBUTE:
			createcaseAttr(basicItemNode, file,prefix, nodeType);
			break;
		case NONO:
			break;
		default:
			break;
		}
	}

	/**
	 * 创建级联属性
	 * @param basicItemNode
	 * @param file
	 * @param prefix
	 * @throws IOException 
	 */
	private void createcaseAttr(BasicItemNode basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getBasicItem().getCnName()+"\"  datatype=\""+basicItemNode.getDataType()+"\" ops=\""+basicItemNode.getOpt()+"\" />";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成关系
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createRelation(BasicItemNode basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" ops=\""+basicItemNode.getOpt()+"\"> ";
		FileManager.writeFileContent(file, str);
		
		List<BasicItemNode> btNodeList = basicItemNodeDao.getChildByPid(basicItemNode.getId());
		for (BasicItemNode bn2 : btNodeList) {
			createChild(bn2, file, prefix);
		}
		str = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 创建属性组
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createAttrgroup(BasicItemNode basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" ops=\""+basicItemNode.getOpt()+"\">";
		FileManager.writeFileContent(file, str);
		
		List<BasicItemNode> btNodeList = basicItemNodeDao.getChildByPid(basicItemNode.getId());
		for (BasicItemNode bn2 : btNodeList) {
			createChild(bn2, file, prefix);
		}
		
		str = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成多值属性
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createMultiattribute(BasicItemNode basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getBasicItem().getCnName()+"\" ops=\""+basicItemNode.getOpt()+"\"> ";
		FileManager.writeFileContent(file, str);
		List<BasicItemNode> btNodeList = basicItemNodeDao.getChildByPid(basicItemNode.getId());
		for (BasicItemNode bn2 : btNodeList) {
			createChild(bn2, file, prefix);
		}
		str = prefix + "</"+nodeType.getName()+">";	
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成LABEL
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createLabel(BasicItemNode basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" subdomain=\""+basicItemNode.getSubdomain()+"\"	ops=\""+basicItemNode.getOpt()+"\" />";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成普通属性
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createAttribute(BasicItemNode basicItemNode, File file, String prefix, NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getBasicItem().getCnName()+"\"  datatype=\""+basicItemNode.getDataType()+"\" ops=\""+basicItemNode.getOpt()+"\" />";
		FileManager.writeFileContent(file, str);
	}

	@Override
	public void insert(BasicItemNode basicItem) {
		basicItemNodeDao.insert(basicItem);
	}

	@Override
	public List getChildOptList(Integer id) {
		return basicItemNodeDao.getChildOptList(id);
	}
}
