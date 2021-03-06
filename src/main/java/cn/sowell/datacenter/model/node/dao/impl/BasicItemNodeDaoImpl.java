package cn.sowell.datacenter.model.node.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.abc.model.enun.NodeType;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.node.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.node.dao.BasicItemNodeDao;
import cn.sowell.datacenter.model.node.pojo.BasicItemNode;

@Repository
public class BasicItemNodeDaoImpl implements BasicItemNodeDao {

	@Resource
	SessionFactory sFactory;

	@Override
	public List<BasicItemNode> queryList(BasicItemNodeCriteria criteria, PageInfo pageInfo) {
		String hql = "from BasicItemNode b WHERE b.type = '1' AND b.parentId is NULL";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		
		if(TextUtils.hasText(criteria.getName())){
			dQuery.appendCondition(" and b.name like :name")
					.setParam("name", "%" + criteria.getName() + "%");
		}
		
		/*if(TextUtils.hasText(criteria.getAbcattr())){
			dQuery.appendCondition(" and b.abcattr like :abcattr")
					.setParam("abcattr", "%" + criteria.getAbcattr() + "%");
		}*/
		
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), false, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		pageInfo.setCount(count);
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), false, null);
			QueryUtils.setPagingParamWithCriteria(query , pageInfo);
			return query.list();
		}
		
		return new ArrayList<BasicItemNode>();
	}
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, Integer id) {
		return sFactory.getCurrentSession().get(clazz, id);
	}
	
	@Override
	public void update(BasicItemNode pojo) {
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM t_sc_basic_item_node WHERE id=:id";
		sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).executeUpdate();
	}

	@Override
	public void delete(BasicItemNode bt) {
		sFactory.getCurrentSession().delete(bt);
	}

	@Override
	public List<BasicItemNodeCriteria> getChildByPid(Integer parentId) {
		String sql = "SELECT a.id,a.type, a.name, a.abcattr_code abcattrCode, a.rel_abcnode_id relAbcnodeId, "
				+ " a.data_type dataType, a.subdomain, a.opt, a.c_order 'order',"
				+ " a.parent_id parentId,   b.c_code basicItemCode, b.c_cn_name basicItemCnName "
				+ " FROM `t_sc_basic_item_node` a "
				+ "LEFT JOIN t_sc_basic_item b on a.abcattr_code=b.c_code "
				+ " WHERE parent_id=:parentId ORDER BY a.c_order ASC";
		
		 List list = sFactory.getCurrentSession().createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.INTEGER)
				 .addScalar("type", StandardBasicTypes.INTEGER)
				 .addScalar("name", StandardBasicTypes.STRING)
				 .addScalar("abcattrCode", StandardBasicTypes.STRING)
				 .addScalar("dataType", StandardBasicTypes.STRING)
				 .addScalar("subdomain", StandardBasicTypes.STRING)
				 .addScalar("opt", StandardBasicTypes.STRING)
				 .addScalar("order", StandardBasicTypes.INTEGER)
				 .addScalar("parentId", StandardBasicTypes.INTEGER)
				 .addScalar("basicItemCode", StandardBasicTypes.STRING)
				 .addScalar("basicItemCnName", StandardBasicTypes.STRING)
				 .addScalar("relAbcnodeId", StandardBasicTypes.INTEGER)
				.setParameter("parentId", parentId)
				.setResultTransformer(Transformers.aliasToBean(BasicItemNodeCriteria.class))
				.list();
		 
		 return list;
	}
	
	
	@Override
	public List<BasicItemNode> getChildByParent(Integer parentId) {
		String sql = "SELECT * FROM `t_sc_basic_item_node` "
				+ " WHERE parent_id=:parentId ORDER BY c_order ASC";
		
		return sFactory.getCurrentSession().createSQLQuery(sql)
				.addEntity(BasicItemNode.class)
				.setParameter("parentId", parentId)
				.list();
	}
	
	@Override
	public List<BasicItemNode> getChildByParent(Integer parentId, String abcattrCode) {
		StringBuffer sb = new StringBuffer(100);
		sb.append(" SELECT * FROM `t_sc_basic_item_node` ")
		.append(" WHERE abcattr_code=:abcattrCode AND parent_id=:parentId");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString())
				.addEntity(BasicItemNode.class)
				.setParameter("parentId", parentId)
				.setParameter("abcattrCode", abcattrCode)
				.list();
	}

	@Override
	public void executeSql(String sql) {
		sFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public Integer getOrder(BasicItemNode basicItemNode) {
		List<BasicItemNode> list = null;
		Integer order;
		if (NodeType.ABC.equals(NodeType.getNodeType(basicItemNode.getType()))) {//是一个实体
			String hql = " FROM BasicItemNode WHERE type=:type AND parentId is null ORDER BY order DESC";
			list = sFactory.getCurrentSession().createQuery(hql).setParameter("type", 1).list();
		} else {
			String hql = " FROM BasicItemNode WHERE parentId=:parentId  ORDER BY order DESC";
			list = sFactory.getCurrentSession().createQuery(hql).setParameter("parentId", basicItemNode.getParentId()).list();
		}
		
		if (list.isEmpty()) {
			order = 100;
		} else {
			order = (Integer) list.get(0).getOrder() + 100;
		}
		
		return order;
	}

	@Override
	public List<String> getNameByPid(BasicItemNode basicItemNode) {
		if (NodeType.ABC.equals(NodeType.getNodeType(basicItemNode.getType())) && basicItemNode.getParentId() == null) {//是一个实体
			String sql = "SELECT name from t_sc_basic_item_node	WHERE parent_id is null AND type=1";
			return sFactory.getCurrentSession().createSQLQuery(sql).list();
		}else {
			BasicItemNode pNode = get(BasicItemNode.class, basicItemNode.getParentId());
			if (NodeType.ATTRGROUP.equals(NodeType.getNodeType(pNode.getType()))) {
				String sql = "SELECT name from t_sc_basic_item_node"
						+ "	WHERE parent_id=:parentId "
						+ "	UNION"
						+ "	SELECT name from t_sc_basic_item_node"
						+ "	WHERE parent_id in("
						+ "	SELECT id from t_sc_basic_item_node"
						+ "	WHERE parent_id=:parentId AND type =6	)";
				return sFactory.getCurrentSession().createSQLQuery(sql).setParameter("parentId", pNode.getParentId()).list();
			} else {
				String sql = "SELECT name from t_sc_basic_item_node"
						+ "	WHERE parent_id=:parentId "
						+ "	UNION"
						+ "	SELECT name from t_sc_basic_item_node"
						+ "	WHERE parent_id in("
						+ "	SELECT id from t_sc_basic_item_node"
						+ "	WHERE parent_id=:parentId AND type =6	)";
				
				return sFactory.getCurrentSession().createSQLQuery(sql).setParameter("parentId", basicItemNode.getParentId()).list();
			
			}
			
			}
		
	}

	@Override
	public List<BasicItemNode> getAllAbc() {
		String hql = " FROM BasicItemNode WHERE parentId is null ORDER BY order ASC";
		return	sFactory.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public List<BasicItemNode> getAttribute(String abcId) {
		String sql = " SELECT * FROM t_sc_basic_item_node WHERE parent_id=:abcId AND type=2"
				+ " UNION "
				+ " SELECT * FROM t_sc_basic_item_node a WHERE parent_id in ( "
				+ "	SELECT id FROM t_sc_basic_item_node WHERE parent_id=:abcId AND type=6 "
				+ " ) AND a.type=2";
		return sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItemNode.class).setParameter("abcId", abcId).list();
	}

	@Override
	public List<String> getNoteSort(Integer parentId) {
		String sql = "SELECT     CONCAT(\"UPDATE t_sc_basic_item_node SET c_order=\",(@i\\:=@i - 1)*100,\" WHERE id=\", b.id)"
				+ " FROM    t_sc_basic_item_node b,    (SELECT   @i\\:=(SELECT  SUM(ct)    FROM   (SELECT "
				+ "  (MAX(c_order) DIV 100)+1 ct   FROM     t_sc_basic_item_node b    WHERE    b.parent_id=:parentId UNION SELECT "
				+ "     COUNT(*) ct     FROM     t_sc_basic_item_node b      WHERE   b.parent_id=:parentId) a)    ) t2   "
				+ "WHERE     b.parent_id=:parentId   ORDER BY b.c_order DESC";
	
		return sFactory.getCurrentSession().createSQLQuery(sql).setParameter("parentId", parentId).list();
	}

	@Override
	public BasicItemNode getAbc(Long id) {
		String sql = "SELECT * FROM t_sc_basic_item_node WHERE id=:id AND type=1 AND parent_id is null";
		return (BasicItemNode) sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItemNode.class).setParameter("id", id).uniqueResult();
	}

	@Override
	public BasicItemNode getRelaNodeChil(Integer parentId, String id, Integer type) {
	String sql = "SELECT * FROM t_sc_basic_item_node WHERE parent_id=:parentId AND type=:type AND id !=:id";
				
	return (BasicItemNode) sFactory.getCurrentSession().createSQLQuery(sql)
			.addEntity(BasicItemNode.class)
			.setParameter("parentId", parentId)
			.setParameter("type", type)
			.setParameter("id", id).uniqueResult();
	}

	@Override
	public List<BasicItemNode> getAllData()  throws Exception{
		String hql = "From BasicItemNode";
		List list = sFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}

	@Override
	public List getChildOptList(Integer id) {
		String sql = "SELECT DISTINCT opt FROM t_sc_basic_item_node WHERE  parent_id=:id";
		return sFactory.getCurrentSession().createSQLQuery(sql).setParameter("id", id).list();
	}

}
