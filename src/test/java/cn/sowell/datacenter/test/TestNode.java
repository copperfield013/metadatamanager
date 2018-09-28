package cn.sowell.datacenter.test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.util.HSSFColor.WHITE;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.mapping.ValueTypeMapping;
import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;
import com.abc.util.ValueType;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;

@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestNode {
	private static final String BasicItem = null;
	@Resource
	BasicItemNodeService basicItemNodeService;
	@Resource
	BasicItemService basicItemService;
	
	@Test
	public void fun1() {
		
		Integer parentId =2522;
		BasicItemNode one = basicItemNodeService.getOne(parentId);
		one.setName("new名字");
		one.setId(null);
		basicItemNodeService.insert(one);
		
		copyNode(parentId, one.getId());
	}
	
	
	private void copyNode(Integer parentId, Integer newParenId) {
		List<BasicItemNode> childByParent = basicItemNodeService.getChildByParent(parentId);
		
		for (BasicItemNode basicItemNode : childByParent) {
			
			Integer pid = basicItemNode.getId();
			//这些都是要复制的
			//此处复制写着信息
			basicItemNode.setId(null);
			basicItemNode.setParentId(newParenId);
			basicItemNodeService.insert(basicItemNode);
			
			copyNode(pid, basicItemNode.getId());
		}
	}
	
	
	@Test
	public void fun2() {
		long start = System.currentTimeMillis();
		ValueType valueTypeByCName = ValueType.getValueType(1);
		
		Collection<ValueType> canTransType = ValueTypeMapping.getCanTransType(valueTypeByCName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dataType", canTransType);
		JSONObject jobj = new JSONObject(map);
long end = System.currentTimeMillis();
		
		System.out.println(end-start);
	}
	
	@Test
	public void createConfigFile() {
		
		String entityId = "XFJDE431";
		
		//生成配置文件的根节点
		BasicItem basicItem = basicItemService.getBasicItem(entityId);
		String name = "【" +basicItem.getCnName() + "】自动生成" + System.currentTimeMillis();
		BasicItemNode pbtn = createBasicItemNode(NodeType.ABC.getCode(), name, ValueType.STRING.getName(), null, NodeOpsType.WRITE.getIndex()+"", null, basicItem);
		basicItemNodeService.saveOrUpdate(pbtn);
		
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
			basicItemNodeService.saveOrUpdate(groupBtn);
			
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
			basicItemNodeService.saveOrUpdate(moreBtn);
			
			List childMoreList = moreBt.getChildList();
			Iterator childMoreIter = childMoreList.iterator();
			
			while (childMoreIter.hasNext()) {
				BasicItem childBt = (BasicItem)childMoreIter.next();
				createAttribute(moreBtn, childBt);
			}
		}
	}

	/**
	 * @param parrentBtn
	 * @param childBt
	 * @throws NumberFormatException
	 */
	private void createAttribute(BasicItemNode parrentBtn, BasicItem childBt) throws NumberFormatException {
		String dataType = childBt.getOneLevelItem().getDataType();
		//级联属性
		if(ValueType.CASCADETYPE.equals(ValueType.getValueType(Integer.parseInt(dataType)))) {
			BasicItemNode casAttr = createBasicItemNode(NodeType.CASATTRIBUTE.getCode(), childBt.getCnName(), ValueType.STRING.getName(), null, NodeOpsType.WRITE.getIndex()+"", parrentBtn.getId(), childBt);
			basicItemNodeService.saveOrUpdate(casAttr);
		} else {//普通属性
			BasicItemNode attr = createBasicItemNode(NodeType.ATTRIBUTE.getCode(), childBt.getCnName(), ValueType.STRING.getName(), null, NodeOpsType.WRITE.getIndex()+"", parrentBtn.getId(), childBt);
			basicItemNodeService.saveOrUpdate(attr);
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
