package cn.sowell.datacenter.model.node.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.dao.BasicItemNodeDao;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;

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
					
					List<BasicItemNode> childByPid = basicItemNodeDao.getChildByPid(String.valueOf(btn.getId()));
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
			Integer order = (afterNode.getOrder() + 1) / 2;
			current.setOrder(order);
		}else if (!beforeId.isEmpty() && afterId.isEmpty()) {//没有后继，但是有前驱
			BasicItemNode beforeNode = basicItemNodeDao.get(BasicItemNode.class, Integer.parseInt(beforeId));
			Integer order = beforeNode.getOrder() + 200;
			current.setOrder(order);
		} else if (!beforeId.isEmpty() && !afterId.isEmpty()) {
			BasicItemNode beforeNode = basicItemNodeDao.get(BasicItemNode.class, Integer.parseInt(beforeId));
			BasicItemNode afterNode = basicItemNodeDao.get(BasicItemNode.class, Integer.parseInt(afterId));
			Integer order = (beforeNode.getOrder() + afterNode.getOrder()) / 2;
			current.setOrder(order);
		}
		
		try {
			basicItemNodeDao.update(current);
		} catch (DataIntegrityViolationException e) {
			System.out.println("1111111");
			excuExtend(current.getParentId());
		} 
		
	}

	@Override
	public void excuExtend(String parentId) {
		Integer count = 100;
		StringBuffer sb = new StringBuffer();
			sb.append("UPDATE t_c_basic_item_node  SET c_order = CASE id ");
			List<BasicItemNode> btNode = basicItemNodeDao.getChildByPid(parentId);
			String ids = "";
			
			for (BasicItemNode bt : btNode) {
				ids+= bt.getId() + ",";
				
				sb.append(" WHEN ");
				sb.append(bt.getId());
				sb.append(" THEN ");
				sb.append(count);
				count = count + 100;
			}
			ids = ids.substring(0, ids.length()-1);
			sb.append(" END ");
			sb.append("  WHERE id IN ("+ids+") ");
			
			basicItemNodeDao.executeSql(sb.toString());
	}

	@Override
	public List<BasicItemNode> getChildNode(String nodeId) {
		return basicItemNodeDao.getChildByPid(nodeId);
	}
}
