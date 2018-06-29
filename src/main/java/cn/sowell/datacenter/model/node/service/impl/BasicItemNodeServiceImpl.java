package cn.sowell.datacenter.model.node.service.impl;

import java.math.BigInteger;
import java.util.List;

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
	public void excuExtend(String parentId) {
		List<String> noteSort = basicItemNodeDao.getNoteSort(parentId);
		
		for (String sql : noteSort) {
			basicItemNodeDao.executeSql(sql);
		}
	}

	@Override
	public List<BasicItemNode> getChildNode(String nodeId) {
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

	/*@Override
	public String refresh(String name) {
		
		Session currentSession = null;
		Transaction bx = null;
		try {
			currentSession = sFactory.getCurrentSession();
			bx = currentSession.beginTransaction();
			String compquerSql = "SELECT id FROM v_dictionary_composite_remove WHERE module=:name";
			List<BigInteger> compquerList = currentSession.createSQLQuery(compquerSql).setParameter("name", name).list();
			
			if (!compquerList.isEmpty()) {
				String compDelSql = "delete from t_dictionary_composite where id in(";
				for (BigInteger str : compquerList) {
					compDelSql += str + ",";
				}
				compDelSql= compDelSql.substring(0, compDelSql.length()-1);
				compDelSql += ")";
				
				currentSession.createSQLQuery(compDelSql).executeUpdate();
			}
			
			String compUpdateSql = "UPDATE t_dictionary_composite t"
					+ " inner join ("
					+ " SELECT * FROM v_dictionary_composite_update WHERE module=:name "
					+ ") c "
					+ "on t.id=c.id "
					+ "SET t.c_is_array=c.is_array;";
			String compInsertSql = "Insert into t_dictionary_composite (c_name, c_title, c_module, c_is_array) "
					+ " select name,title,module, is_array from v_dictionary_composite_add ";
			
			currentSession.createSQLQuery(compUpdateSql).setParameter("name", name).executeUpdate();
			currentSession.createSQLQuery(compInsertSql).executeUpdate();
			
			String fielquerSql = "SELECT id FROM v_dictionary_field_remove WHERE module=:name";
			List<BigInteger> fielquerlist = currentSession.createSQLQuery(fielquerSql).setParameter("name", name).list();
			
			if (!fielquerlist.isEmpty()) {
				String fielDelSql ="delete from t_dictionary_field where id in(";
				
				for (BigInteger str : fielquerlist) {
					fielDelSql += str + ",";
				}
				fielDelSql= fielDelSql.substring(0, fielDelSql.length()-1);
				fielDelSql += ")";
				
				currentSession.createSQLQuery(fielDelSql).executeUpdate();
			}
			String fielUpdateSql = "UPDATE t_dictionary_field t "
					+ "inner join (SELECT * FROM v_dictionary_field_update WHERE module=:name"
					+ ") c on t.id=c.id SET t.c_type=c.ttype, t.c_abc_type=c.abc_type, t.optgroup_id=c.optgroup_id";
			String fielInsertSql ="Insert into t_dictionary_field (composite_id, c_full_key, c_title, c_type, c_abc_type,optgroup_id ) "
					+ "select comp_id,full_key, title, ttype, abc_type, optgroup_id from v_dictionary_field_add";
			
			
			currentSession.createSQLQuery(fielUpdateSql).setParameter("name", name).executeUpdate();
			currentSession.createSQLQuery(fielInsertSql).executeUpdate();
			
			bx.commit();
			return "ok";
		} catch (Exception e) {
			bx.rollback();
			return "error";
		}
	}*/

	@Override
	public BasicItemNode getAbc(String name) {
		return basicItemNodeDao.getAbc(name);
	}

	
	@Override
	public String getRelaNodeChil(String parentId, String id, Integer type) {
		BasicItemNode relaNodeChil = basicItemNodeDao.getRelaNodeChil(parentId, id, type);
		if (relaNodeChil != null) {
			return "true";
		}
		
		return "false";
	}
}
