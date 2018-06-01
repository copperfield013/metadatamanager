package cn.sowell.datacenter.model.node.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.abc.mapping.node.NodeType;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.dao.BasicItemNodeDao;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;
import cn.sowell.datacenter.model.node.service.BasicItemNodeService;

@Service
public class BasicItemNodeServiceImpl implements BasicItemNodeService {

	private static final String Integer = null;
	@Resource
	BasicItemNodeDao basicItemNodeDao;
	
	@Resource
	SessionFactory sFactory;
	
	@Override
	public List<BasicItemNode> queryList(BasicItemNodeCriteria criteria, PageInfo pageInfo) {
		return basicItemNodeDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(BasicItemNode basicItemNode) {
		basicItemNodeDao.insert(basicItemNode);
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
	public void delete(Integer id, String isDelChil) {
		BasicItemNode btn = basicItemNodeDao.get(BasicItemNode.class, id);
		NodeType nodeType = NodeType.getNodeType(btn.getType());
		switch (nodeType) {
			case ATTRGROUP:
				if (!"true".equals(isDelChil)) {
					//只删除分组， 不删除孩子
					//获取所有孩子， 给孩子更改父亲， 父亲就是分组的父亲
					List<BasicItemNode> childByPid = basicItemNodeDao.getChildByPid(String.valueOf(btn.getId()));
					for (BasicItemNode basicItemNode : childByPid) {
						basicItemNode.setParentId(btn.getParentId());
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
	public String nodeSort(BasicItemNode current, Integer beforeId, Integer afterId) {
		//第一个孩子， 没有前驱， 没有后继
		
		if (beforeId == null && afterId == null) {//没有前驱， 没有后继, 父亲的第一个孩子
				current.setOrder(100);
		} else if (beforeId == null && afterId != null) {//没有前驱， 但是有后继
			BasicItemNode afterNode = basicItemNodeDao.get(BasicItemNode.class, afterId);
			Integer order = (afterNode.getOrder() + 1) / 2;
			current.setOrder(order);
		}else if (beforeId != null && afterId == null) {//没有后继，但是有前驱
			BasicItemNode beforeNode = basicItemNodeDao.get(BasicItemNode.class, beforeId);
			Integer order = beforeNode.getOrder() + 200;
			current.setOrder(order);
		} else if (beforeId != null && afterId != null) {
			BasicItemNode beforeNode = basicItemNodeDao.get(BasicItemNode.class, beforeId);
			BasicItemNode afterNode = basicItemNodeDao.get(BasicItemNode.class, afterId);
			Integer order = (beforeNode.getOrder() + afterNode.getOrder()) / 2;
			current.setOrder(500);
		}
		if (current.getId() == null) {
			basicItemNodeDao.insert(current);
		} else {
			basicItemNodeDao.update(current);
		}
		
		return null;
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
}
