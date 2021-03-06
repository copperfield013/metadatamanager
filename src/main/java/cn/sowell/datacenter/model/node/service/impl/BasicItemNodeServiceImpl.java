package cn.sowell.datacenter.model.node.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.dao.BasicItemNodeDao;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.pojo.BinFilter;
import cn.sowell.datacenter.model.node.pojo.BinFilterBody;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;
import cn.sowell.datacenter.model.node.service.BinFilterBodyService;
import cn.sowell.datacenter.utils.FileManager;

import com.abc.mapping.ValueTypeMapping;
import com.abc.model.enun.NodeOpsType;
import com.abc.model.enun.NodeType;
import com.abc.model.enun.ValueType;

@Service
public class BasicItemNodeServiceImpl implements BasicItemNodeService {

	@Resource
	BasicItemNodeDao basicItemNodeDao;
	
	@Resource
	SessionFactory sFactory;
	
	@Resource
	BinFilterBodyService binFilterBodyService;
	
	@Resource
	BasicItemService basicItemService;
	
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
					
					List<BasicItemNode> pchil = basicItemNodeDao.getChildByParent(btn.getParentId());
					BasicItemNode btend = pchil.get(pchil.size()-1);
					int order = btend.getOrder();
					
					List<BasicItemNode> childByPid = basicItemNodeDao.getChildByParent(btn.getId());
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
			case RABCNODE:
			case RATTRIBUTE:
			case MULTIATTRIBUTE:
			case CASATTRIBUTE:
			case RELATION:
			case REFATTRIBUTE:
			case RREFATTRIBUTE:
				basicItemNodeDao.delete(btn);
				break;
			case NONO:
				break;
		}
	}

	
	@Override
	public void nodeSort(BasicItemNode current, String beforeId, String afterId) {
		
		BasicItemNode paNode = basicItemNodeDao.get(BasicItemNode.class, current.getParentId());
		
			//第一个孩子， 没有前驱， 没有后继
		if (beforeId.isEmpty()&& afterId.isEmpty()) {//没有前驱， 没有后继, 父亲的第一个孩子
			Integer order = paNode.getOrder();
			
			if (order == null || "".equals(order)) {
				current.setOrder(100);
			} else {
				current.setOrder(order+100);
			}
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
		
		//当前对象排序完成， 如果是属性组， 则属性组的孩子， order值为属性的order一次增加100
		Integer type = current.getType();
		if (NodeType.ATTRGROUP.getCode() == type) {
			//获取属性组的孩子， 进行重新排序
			List<BasicItemNode> childList = basicItemNodeDao.getChildByParent(current.getId());
			
			Integer order = current.getOrder();//父亲的排序值
			for (int i = 0; i < childList.size(); i++) {
				order = order + 50;
				BasicItemNode basicItemNode = childList.get(i);
				basicItemNode.setOrder(order);
				basicItemNodeDao.update(basicItemNode);
			}
		}
		
	}

	@Override
	public void excuExtend(Integer parentId) {
		List<String> noteSort = basicItemNodeDao.getNoteSort(parentId);
		
		for (String sql : noteSort) {
			basicItemNodeDao.executeSql(sql);
		}
	}

	@Override
	public List<BasicItemNodeCriteria> getChildNode(Integer nodeId) {
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
		boolean contains = nameList.contains(basicItemNode.getName());
		
		List<BasicItemNode> childByParent = null;
		if (NodeType.MULTIATTRIBUTE.getCode() == basicItemNode.getType()) {
			basicItemNodeDao.getChildByParent(basicItemNode.getParentId(), basicItemNode.getBasicItem().getCode());
		}
		
		if (childByParent !=null) {
			contains = true;
		}
		
		return contains;
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
	public BasicItemNode getAbc(Long id) {
		return basicItemNodeDao.getAbc(id);
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
	public File getConfigFile(Integer nodeId) throws IOException {
		
		BasicItemNode btn = getOne(nodeId);
		
		String fileName = btn.getName();
    	File file = File.createTempFile(fileName, ".xml");
		
		String prefix = "  ";
		String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		FileManager.writeFileContent(file, head);
		head = "<"+NodeType.ABC.getName()+" name=\""+btn.getName()+"\" abcattr=\""+btn.getBasicItem().getCnName()+"\""+"\r\n"
				+ "	 class=\"\" xmlns=\"http://www.w3school.com.cn\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
		FileManager.writeFileContent(file, head);
		
		
		//ABC 配置文件
		List<BasicItemNodeCriteria> btNodeList = basicItemNodeDao.getChildByPid(btn.getId());
		for (BasicItemNodeCriteria basicItemNode : btNodeList) {
			createChild(basicItemNode, file, prefix);
		}
		
		//过滤条件filters
		try {
			BinFilter binFilter = binFilterBodyService.getBinFilterByNodeId(btn.getId());
			BinFilterBody binFilterBody = null;
			if (binFilter != null) {
				binFilterBody = binFilterBodyService.getBinFilterBody(binFilter.getFiltersId());
			
				String startFilters =  prefix + "<"+NodeType.FILTERS.getName()+" name=\""+binFilterBody.getName()+"\" >";
				FileManager.writeFileContent(file, startFilters);
				List<BinFilterBody> filterBodyChild = binFilterBodyService.getFilterBodyChild(binFilterBody.getId());
				
				for (BinFilterBody body : filterBodyChild) {
					createFiltersChild(body,file, prefix);
				}
				
				String endFilters =  prefix + "</"+NodeType.FILTERS.getName()+">";
				FileManager.writeFileContent(file, endFilters);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String endStr = "</"+NodeType.ABC.getName()+">";
		FileManager.writeFileContent(file, endStr);
		
		return file;
	}
	
	
	private void createFiltersChild(BinFilterBody binFilterBody,File file, String prefix) throws Exception {
		prefix += "   ";
		NodeType nodeType = NodeType.getNodeType(binFilterBody.getDataType());
		switch (nodeType) {
		case FILTERGROUP://只可能是关系下的ABC了
			createFilterGroup(binFilterBody, file, prefix, nodeType);
			break;
		case FILTER:
			createFilter(binFilterBody, file, prefix, nodeType);
			break;
		case RFILTER:
			createRFilter(binFilterBody, file, prefix, nodeType);
			break;
		case NONO:
			break;
		default:
			break;
		}
		
	}

	private void createFilter(BinFilterBody binFilterBody, File file, String prefix, NodeType nodeType) throws IOException {
		String str = "";
		str = prefix + "<"+nodeType.getName()+" name=\""+binFilterBody.getName()+"\" abcattr_code=\""+binFilterBody.getAbcattrCode()+"\"  filter_type=\""+binFilterBody.getFilterType()+"\" value=\""+binFilterBody.getValue()+"\" opt=\""+binFilterBody.getOpt()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		
		String endStr = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, endStr);
		
	}
	
	private void createRFilter(BinFilterBody binFilterBody, File file, String prefix, NodeType nodeType) throws Exception {
		
		String str = "";
		str = prefix + "<"+nodeType.getName()+" name=\""+binFilterBody.getName()+"\" subdomain=\""+binFilterBody.getSubdomain()+"\" opt=\""+binFilterBody.getOpt()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		
		List<BinFilterBody> filterBodyChild = binFilterBodyService.getFilterBodyChild(binFilterBody.getId());
		for (BinFilterBody body : filterBodyChild) {
			createFiltersChild(body, file, prefix);
		}
		
		String endStr = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, endStr);
	}

	private void createFilterGroup(BinFilterBody binFilterBody, File file, String prefix, NodeType nodeType) throws Exception {
		String str = "";
		str = prefix + "<"+nodeType.getName()+" name=\""+binFilterBody.getName()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		
		List<BinFilterBody> filterBodyChild = binFilterBodyService.getFilterBodyChild(binFilterBody.getId());
		for (BinFilterBody body : filterBodyChild) {
			createFiltersChild(body, file, prefix);
		}
		
		String endStr = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, endStr);
	}

	/**
	 * 创建ABC
	 * @param file
	 * @param bn
	 * @throws IOException
	 */
	private void createAbc(File file, BasicItemNodeCriteria bn, String prefix, NodeType nodeType) throws IOException {
		String str = "";
		str = prefix + "<"+nodeType.getName()+" name=\""+bn.getName()+"\" abcattr=\""+bn.getBasicItemCnName()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		
		//获取ABC的所有直系孩子
		List<BasicItemNodeCriteria> btNodeList = basicItemNodeDao.getChildByPid(bn.getId());
		for (BasicItemNodeCriteria basicItemNode : btNodeList) {
			createChild(basicItemNode, file, prefix);
		}
		String endStr = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, endStr);
	}
	
	/**
	 * 创建RABC
	 * @param file
	 * @param bn
	 * @throws IOException
	 */
	private void createRabcnode(File file, BasicItemNodeCriteria bn, String prefix, NodeType nodeType) throws IOException {
		String str = "";
		str = prefix + "<"+nodeType.getName()+" name=\""+bn.getName()+"\" relAbcnodeId=\""+bn.getRelAbcnodeId()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		String endStr = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, endStr);
	}
	
	
	private void createrRefattribute(File file, BasicItemNodeCriteria bn, String prefix, NodeType nodeType) throws IOException {
		String str = "";
		str = prefix + "<"+nodeType.getName()+" name=\""+bn.getName()+"\" relAbcnodeId=\""+bn.getRelAbcnodeId()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		String endStr = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, endStr);
	}

	private void createRefattribute(File file, BasicItemNodeCriteria bn, String prefix, NodeType nodeType) throws IOException {
		String str = "";
		str = prefix + "<"+nodeType.getName()+" name=\""+bn.getName()+"\" relAbcnodeId=\""+bn.getRelAbcnodeId()+"\">"+"\r\n";
		FileManager.writeFileContent(file, str);
		String endStr = prefix + "</"+nodeType.getName()+">";
		FileManager.writeFileContent(file, endStr);
	}
	
	/**
	 * 根据本身type, 进行分流操作
	 * @param basicItemNode
	 * @throws IOException 
	 */
	private void createChild(BasicItemNodeCriteria basicItemNode, File file, String prefix) throws IOException {
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
		case RATTRIBUTE:
			createRattribute(basicItemNode, file, prefix, nodeType);
			break;
		case RABCNODE:
			createRabcnode(file, basicItemNode, prefix, nodeType);
			break;
		case REFATTRIBUTE:
			createRefattribute(file, basicItemNode, prefix, nodeType);
			break;
		case RREFATTRIBUTE:
			createrRefattribute(file, basicItemNode, prefix, nodeType);
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
	private void createcaseAttr(BasicItemNodeCriteria basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getBasicItemCnName()+"\"  datatype=\""+basicItemNode.getDataType()+"\" ops=\""+basicItemNode.getOpt()+"\" />";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成关系
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createRelation(BasicItemNodeCriteria basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" ops=\""+basicItemNode.getOpt()+"\"> ";
		FileManager.writeFileContent(file, str);
		//配置文件的filters
		
		
		
		
		//配置文件孩子
		List<BasicItemNodeCriteria> btNodeList = basicItemNodeDao.getChildByPid(basicItemNode.getId());
		for (BasicItemNodeCriteria bn2 : btNodeList) {
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
	private void createAttrgroup(BasicItemNodeCriteria basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" ops=\""+basicItemNode.getOpt()+"\">";
		FileManager.writeFileContent(file, str);
		
		List<BasicItemNodeCriteria> btNodeList = basicItemNodeDao.getChildByPid(basicItemNode.getId());
		for (BasicItemNodeCriteria bn2 : btNodeList) {
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
	private void createMultiattribute(BasicItemNodeCriteria basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getBasicItemCnName()+"\" ops=\""+basicItemNode.getOpt()+"\"> ";
		FileManager.writeFileContent(file, str);
		List<BasicItemNodeCriteria> btNodeList = basicItemNodeDao.getChildByPid(basicItemNode.getId());
		for (BasicItemNodeCriteria bn2 : btNodeList) {
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
	private void createLabel(BasicItemNodeCriteria basicItemNode, File file, String prefix,NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" subdomain=\""+basicItemNode.getSubdomain()+"\"	ops=\""+basicItemNode.getOpt()+"\" />";
		FileManager.writeFileContent(file, str);
	}

	/**
	 * 生成普通属性
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createAttribute(BasicItemNodeCriteria basicItemNode, File file, String prefix, NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" abcattr=\""+basicItemNode.getBasicItemCnName()+"\"  datatype=\""+basicItemNode.getDataType()+"\" ops=\""+basicItemNode.getOpt()+"\" />";
		FileManager.writeFileContent(file, str);
	}
	
	/**
	 * 生成关系属性
	 * @param basicItemNode
	 * @param file
	 * @throws IOException 
	 */
	private void createRattribute(BasicItemNodeCriteria basicItemNode, File file, String prefix, NodeType nodeType) throws IOException {
		String str = prefix + "<"+nodeType.getName()+" name=\""+basicItemNode.getName()+"\" subdomain=\""+basicItemNode.getSubdomain()+"\" abcattr=\""+basicItemNode.getBasicItemCnName()+"\"  datatype=\""+basicItemNode.getDataType()+"\" ops=\""+basicItemNode.getOpt()+"\" />";
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

	@Override
	public List<BasicItemNode> getChildByParent(Integer parentId) {
		return basicItemNodeDao.getChildByParent(parentId);
	}

	@Override
	public void copyNode(Integer nodeId) throws Exception {
		BasicItemNode one = this.getOne(nodeId);
		
		BasicItemNode newbt = getNewBasicItemNode(one, null);
		newbt.setName(one.getName()+"copy"+new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()));
		this.insert(newbt);
		copyNode(nodeId, newbt.getId());
	}
	private BasicItemNode getNewBasicItemNode(BasicItemNode bt, Integer newParenId) {
		BasicItemNode newbt = new BasicItemNode();
		newbt.setBasicItem(bt.getBasicItem());
		newbt.setDataType(bt.getDataType());
		newbt.setName(bt.getName());
		newbt.setOpt(bt.getOpt());
		newbt.setOrder(bt.getOrder());
		newbt.setParentId(newParenId);
		newbt.setSubdomain(bt.getSubdomain());
		newbt.setType(bt.getType());
		return newbt;
	}
	
	private void copyNode(Integer parentId, Integer newParenId) {
		List<BasicItemNode> childByParent = this.getChildByParent(parentId);
		
		for (BasicItemNode basicItemNode : childByParent) {
			Integer pid = basicItemNode.getId();
			//这些都是要复制的
			//此处复制写着信息
			BasicItemNode newbt = getNewBasicItemNode(basicItemNode, newParenId);
			this.insert(newbt);
			copyNode(pid, newbt.getId());
		}
	}

	@Override
	public boolean createConfigFile(String entityId) {
		
		//生成配置文件的根节点
		BasicItem basicItem = basicItemService.getBasicItem(entityId);
		String name = "【" +basicItem.getCnName() + "】自动生成" + System.currentTimeMillis();
		BasicItemNode pbtn = createBasicItemNode(NodeType.ABC.getCode(), name, ValueType.STRING.getName(), null, NodeOpsType.WRITE.getIndex()+"", null, basicItem);
		saveOrUpdate(pbtn);
		
		//获取模型数据
		Map<String, List> attrByPid = basicItemService.getAttrByPid(entityId);
		List commonList = attrByPid.get("commonProper");//普通属性
		List moreList = attrByPid.get("moreProper");//多值属性
		List relaList = attrByPid.get("entityRela");//实体关系
		
		Iterator iterator = commonList.iterator();
		//生成属性组及其孩子节点
		while (iterator.hasNext()) {
			BasicItem bt = (BasicItem)iterator.next();//获取的是实体的普通分组
			//生成属性组btn
			BasicItemNode groupBtn = createBasicItemNode(NodeType.ATTRGROUP.getCode(), bt.getCnName(), ValueType.STRING.getName(), null, NodeOpsType.WRITE.getIndex()+"", pbtn.getId(), null);
			saveOrUpdate(groupBtn);
			
			List childList = bt.getChildList();//分组下的所有孩子
			Iterator childIter = childList.iterator();
			while (childIter.hasNext()) {
				BasicItem childBt = (BasicItem)childIter.next();
				createAttribute(groupBtn, childBt);
			}
		}
		
		//生成多值属性及其孩子节点
		Iterator moreIter = moreList.iterator();
		while (moreIter.hasNext()) {
			BasicItem moreBt = (BasicItem) moreIter.next();//获取的实体的多值类型
			BasicItemNode moreBtn = createBasicItemNode(NodeType.MULTIATTRIBUTE.getCode(), moreBt.getCnName(), ValueType.STRING.getName(), null, NodeOpsType.WRITE.getIndex()+"", pbtn.getId(), moreBt);
			saveOrUpdate(moreBtn);
			
			List childMoreList = moreBt.getChildList();
			Iterator childMoreIter = childMoreList.iterator();
			
			while (childMoreIter.hasNext()) {
				BasicItem childBt = (BasicItem)childMoreIter.next();
				createAttribute(moreBtn, childBt);
			}
		}
		
		return false;
	}
	
	 /**生成普通属性和级联属性
		 * @param parrentBtn
		 * @param childBt
		 * @throws NumberFormatException
		 */
		private void createAttribute(BasicItemNode parrentBtn, BasicItem childBt) throws NumberFormatException {
			String dataType = childBt.getOneLevelItem().getDataType();
			
			ValueType valueType = ValueType.getValueType(Integer.parseInt(dataType));

			Collection<ValueType> canTransType = ValueTypeMapping.getCanTransType(valueType);
			ValueType next = ValueType.STRING;
			if (!canTransType.isEmpty()) {
				 next = canTransType.iterator().next();
			}
			
			//级联属性
			if(ValueType.CASCADETYPE.equals(valueType)) {
				BasicItemNode casAttr = createBasicItemNode(NodeType.CASATTRIBUTE.getCode(), childBt.getCnName(), next.getName(), null, NodeOpsType.WRITE.getIndex()+"", parrentBtn.getId(), childBt);
				saveOrUpdate(casAttr);
			} else {//普通属性
				BasicItemNode attr = createBasicItemNode(NodeType.ATTRIBUTE.getCode(), childBt.getCnName(), next.getName(), null, NodeOpsType.WRITE.getIndex()+"", parrentBtn.getId(), childBt);
				saveOrUpdate(attr);
			}
		}
		
		//生成btn对象
		private BasicItemNode createBasicItemNode(Integer type, String name, String dataType, String subdomain, String opt, Integer parentId, BasicItem basicItem) {
			BasicItemNode btn = new BasicItemNode();
			btn.setType(type);
			btn.setName(name);
			btn.setDataType(dataType);
			btn.setSubdomain(subdomain);
			btn.setOpt(opt);
			btn.setParentId(parentId);
			btn.setBasicItem(basicItem);
			return btn;
		}
}
