package cn.sowell.datacenter.model.buildproject.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.sowell.datacenter.model.buildproject.dao.BuildProjectDao;

/**
 * 	构建项目接口
 * @author so-well
 *
 */
@Repository
public class BuildProjectDaoImpl implements BuildProjectDao {
	
	@Resource
	SessionFactory sFactory;
	
	/**
	   * 	获取枚举数据
	 * @return
	 */
	 public List<String> getEnumData() {
		 StringBuffer sb = new StringBuffer(100);
		 sb.append("SELECT ")
		 .append("  CONCAT('public static final Integer ENUM_',")
		 .append(" replace(replace(replace(replace(b.c_name, '（', '_'), '）', '_'), '(', '_'), ')', '_'), ")
		 .append(" '_', ")
		 .append(" replace(replace(replace(replace(a.c_name, '（', '_'), '）', '_'), '(', '_'), ')', '_'), ")
		 .append(" '=', a.id, ';')")
		 .append(" FROM")
		 .append(" t_sc_cascadedict_basic_item a")
		 .append(" LEFT JOIN")
		 .append("  t_sc_cascadedict_basic_item b ON a.parent_id = b.id")
		 .append(" WHERE")
		 .append(" a.parent_id != 0")
		 .append(" ORDER BY b.id , a.id");
		 
		 return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	 }
	
	/**
	 * 	获取关系数据
	 * @return
	 */
	 public List<String> getRelationData() {
		 StringBuffer sb = new StringBuffer(200);
		 sb.append(" SELECT ")
		 .append(" CONCAT('public static final String RR_',")
		 .append("  b.c_cn_name, '_', a.name, '_', c.c_cn_name, '=\"', a.type_code, '\";')")
		 .append(" FROM")
		 .append(" t_sc_record_relation_type a")
		 .append("  LEFT JOIN")
		 .append(" t_sc_basic_item b ON a.left_record_type = b.c_code")
		 .append(" LEFT JOIN")
		 .append(" t_sc_basic_item c ON a.right_record_type = c.c_code order by a.type_code");
		
		 return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	 }
	
	 /**
	  * 	获取实体的item 属性字段
	  * @param entityCode    实体code
	  * @param entityPrefix   实体前缀
	  * @return
	  */
	 public List<String> getItemData(String entityCode, String entityPrefix) {
		 
		 String multEntityPre = entityCode + "_" + entityPrefix;
		 
		 StringBuffer sb = new StringBuffer(400);
		 sb.append(" SELECT ")
		 .append(" CONCAT('public static final String ',")
		 .append("  replace(replace(d.c_cn_name,'）', '_'), '（', '_'), '=\"', d.c_code, '\";') as item")
		 .append(" FROM   t_sc_basic_item d")
		 .append(" WHERE   d.c_code LIKE '%"+entityPrefix+"%' and d.c_parent='"+entityCode+"' ")
		 .append(" UNION ")
		 .append(" SELECT ")
		 .append("  CONCAT('public static final String ',")
		 .append("   replace(replace(b.c_cn_name,'）', '_'), '（', '_'),'_', replace(replace(a.c_cn_name,'）', '_'), '（', '_'), '=\"',a.c_code, '\";') as item")
		 .append(" FROM    t_sc_basic_item a")
		 .append(" inner join t_sc_basic_item b on b.c_code = substring_index(a.c_parent, '_', -1)")
		 .append(" WHERE ")
		 .append(" a.c_code LIKE '%"+entityPrefix+"%' and a.c_parent  like '"+multEntityPre+"%' and a.c_cn_name not like '多值属性%' ");
		 
		 return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	 }
	 
	 /**
	  * 建立项目
	  * @return
	  */
	 public  String buildProject() {
		 
		 
		 return null;
	 }
}
