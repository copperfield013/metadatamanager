package cn.sowell.datacenter.model.dictionary.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.node.pojo.BasicItemNodeGenerator;

@Repository
public class BasicItemDaoImpl implements BasicItemDao {
	public static String DataBaseName;
	
	@Resource
	SessionFactory sFactory;
	
	@Override
	public List<BasicItem> queryList(BasicItemCriteria criteria) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CAST((reverse( - ( - reverse( substring_index( b.c_code, '_', 1 ) ) ) ) ) as SIGNED) as c_order, b.* FROM t_c_basic_item b inner JOIN t_c_onelevel_item o on b.c_code=o.c_code WHERE 1=1 ");
		
		if(criteria.getUsingState() != null && criteria.getUsingState().SIZE > 0){
			sb.append(" AND b.c_using_state=:usingState");
		}
		if(TextUtils.hasText(criteria.getParent())){
			sb.append(" AND b.c_parent=:parent");
		}
		if(TextUtils.hasText(criteria.getCnName())){
			sb.append(" AND b.c_cn_name like :cnName");
		}
		if(TextUtils.hasText(criteria.getOneLevelItem().getDataType())){
			sb.append(" AND o.c_data_type=:dataType");
		}
		sb.append(" ORDER BY c_order ASC");
		
		SQLQuery query = sFactory.getCurrentSession().createSQLQuery(sb.toString())
				.addEntity(BasicItem.class);
		
		if(criteria.getUsingState() != null && criteria.getUsingState().SIZE > 0){
			query.setParameter("usingState", criteria.getUsingState());
		}
		if(TextUtils.hasText(criteria.getParent())){
			query.setParameter("parent", criteria.getParent());
		}
		if(TextUtils.hasText(criteria.getCnName())){
			query.setParameter("cnName", criteria.getCnName());
		}
		if(TextUtils.hasText(criteria.getOneLevelItem().getDataType())){
			query.setParameter("dataType", criteria.getOneLevelItem().getDataType());
		}
		
		return query.list();
	}
	
	@Override
	public void insert(Object pojo) {
		sFactory.getCurrentSession().save(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, String id) {
		return sFactory.getCurrentSession().get(clazz, id);
	}
	
	@Override
	public void update(Object pojo) {
		sFactory.getCurrentSession().update(pojo);
	}
	
	@Override
	public void delete(Object pojo) {
		sFactory.getCurrentSession().delete(pojo);
	}

	@Override
	public List<BasicItem> getDataByPId(String parent) {
		String sql = "SELECT * FROM t_c_basic_item WHERE c_parent=:parent AND c_code not like '%_P' AND c_code not like '%__ED' ORDER BY  CAST((reverse( - ( - reverse( substring_index( c_code, '_', 1 ) ) ) )) as SIGNED) ASC";
		List<BasicItem> list = sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("parent", parent).list();
		return list;
	}
	
	@Override
	public List<BasicItem> getChilByPid(String parent) {
		String sql = "SELECT * FROM t_c_basic_item WHERE c_parent=:parent ORDER BY  CAST((reverse( - ( - reverse( substring_index( c_code, '_', 1 ) ) ) )) as SIGNED) ASC";
		List<BasicItem> list = sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("parent", parent).list();
		return list;
	}

	@Override
	public List getAttrByPidGroupName(String parent, String groupName) {
		String sql = "SELECT * FROM	t_c_basic_item t	INNER JOIN t_c_onelevel_item o	on t.c_code=o.c_code  WHERE	t.c_parent =:parent 	AND o.c_group_name =:groupName 	AND t.c_code NOT LIKE '%_P'  ORDER BY  CAST((reverse( - ( - reverse( substring_index(t.c_code, '_', 1 ) ) ) )) as SIGNED) ASC";
		List<BasicItem> list = sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("parent", parent).setParameter("groupName", groupName).list();
		return list;
	}

	@Override
	public void saveOrUpdate(Object obj, String flag) {
		if ("add".equals(flag)) {
			sFactory.getCurrentSession().save(obj);
		} else {
			try {
				sFactory.getCurrentSession().update(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	//实体code  生成规则
	public String getEntityCode() {
		try {
			BasicItemNodeGenerator btNg = new BasicItemNodeGenerator();
			sFactory.getCurrentSession().save(btNg);
			
			String format = String.format("%03d", btNg.getId()); 
			return new BasicItemNodeGenerator().getProperty("model.entity.prefix")+format;
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//其他code， 生成规则
	public String getAttrCode() {
		try {
			BasicItemNodeGenerator btNg = new BasicItemNodeGenerator();
			
			sFactory.getCurrentSession().save(btNg);
			String format = String.format("%03d", btNg.getId()); 
			return new BasicItemNodeGenerator().getProperty("model.attr.prefix") + format;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@Override
	public List queryCreTab() {
		getDataBaseName();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.c_code, ")
			.append("concat(\"create table \", a.c_table_name,\"( `id`  bigint(20) NOT NULL AUTO_INCREMENT, ")
			.append("`ABP0001`  varchar(32) DEFAULT NULL ,PRIMARY KEY (`id`))\")  valstr ")
			.append("FROM ")
			.append("(SELECT ")
			.append(" c_code,  c_table_name ")
			.append("FROM ")
			.append("t_c_onelevel_item ")
			.append("WHERE ")
			.append("c_table_name IS NOT NULL ")
			.append(" GROUP BY c_table_name) a ")
			.append("LEFT JOIN ")
			.append("(SELECT  ")
			.append(" table_name ")
			.append("FROM ")
			.append("information_schema.tables t ")
			.append("WHERE ")
			.append("t.table_schema = '")
			.append(DataBaseName)//这里获取数据库的名字
			.append("') b ON a.c_table_name = b.table_name ")
			.append("WHERE b.table_name IS NULL");
		List list = sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
		return list;
	}

	 @Override
	public List queryNewAddCol() {
		 getDataBaseName();
		 StringBuffer sb = new StringBuffer();
			sb.append("SELECT a.c_code, ")
			.append("CONCAT('alter table ', ")
			.append(" a.c_table_name, ")
			.append("' add ', ")
			.append("a.c_code,' ', ")
			.append(" CASE a.c_data_type ")
			.append(" WHEN '字符型' THEN concat('varchar(',if(a.c_data_range='枚举' ,\"32\",a.c_data_range),')') ")
			.append(" WHEN '数字型' THEN concat('int(',if(a.c_data_range is null ,\"11\",a.c_data_range),')') ")
			.append(" WHEN '数字型小数' THEN  concat('double(',if(a.c_data_range is null ,\"10,2\",a.c_data_range),')') ")
			.append(" WHEN '日期型' THEN 'date' ")
			.append(" WHEN '时间型' THEN 'datetime' ")
			.append(" WHEN '二进制型' THEN 'MediumBlob' ")
			.append(" WHEN '引用类型' THEN 'varchar(32)'")
			.append(" END, ")
			.append("'  default NULL ') ")
			.append("FROM ")
			.append("(SELECT  *  FROM  t_c_onelevel_item WHERE ")
			.append(" c_data_type != '记录类型' ")
			.append("AND c_data_type != '重复类型'")
			.append(" AND c_data_type != '分组类型') a ")
			.append(" LEFT JOIN ")
			.append(" (SELECT  ")
			.append(" col.column_name  FROM  information_schema.columns col  WHERE ")
			//.append("  (column_name LIKE 'SW%' or column_name LIKE 'ABC%')  and ")
			.append(" table_schema = '"+DataBaseName+"') b ON a.c_code = b.column_name ")
			.append("WHERE ")
			.append(" b.column_name IS NULL and c_table_name is not null and a.c_data_range is not null  order by a.c_code ");
		 
			List list = sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
		return list;
	}
	 
	@Override
	public List queryEditCol() {
		 getDataBaseName();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT  ")
		 .append("  a.c_code, ")
		 .append("  CONCAT( 'alter table ', a.c_table_name, ' CHANGE COLUMN ', a.c_code, ' ', a.c_code, ' ', a.col_type, '  default NULL ;' )  ")
		 .append(" FROM     (  SELECT ")
		 .append("     aa.c_table_name,     aa.c_code,     aa.col_type  ")
		 .append(" FROM     (  SELECT ")
		 .append("    a.c_code,     a.c_table_name, ")
		 .append(" CASE   a.c_data_type  ")
		 .append("  WHEN '字符型' THEN    CONCAT( 'varchar(', IF ( a.c_data_range = '枚举', '32', a.c_data_range ), ')' )  ")
		 .append("  WHEN '数字型' THEN    CONCAT( 'int(', IF ( a.c_data_range IS NULL, '11', a.c_data_range ), ')' )  ")
		 .append(" WHEN '数字型小数' THEN    CONCAT( 'double(', IF ( a.c_data_range IS NULL, '10,2', a.c_data_range ), ')' )  ")
		 .append("  WHEN '日期型' THEN     'date'  ")
		 .append("  WHEN '时间型' THEN     'datetime'  ")
		 .append(" WHEN '二进制型' THEN    'mediumblob'  ")
		 .append("  WHEN '引用类型' THEN    'varchar(32)'  ")
		 .append(" END col_type  ")
		 .append(" FROM ")
		 .append("     t_c_onelevel_item a  ")
		 .append(" WHERE  ")
		 .append("  a.c_data_type != '分组类型' ")
		 .append(" AND a.c_data_type != '记录类型' ")
		 .append(" AND a.c_data_type != '重复类型' ")
		 .append("     ) aa ")
		 .append("    LEFT JOIN ( SELECT col.Column_name, col.column_type, col.table_name FROM information_schema.COLUMNS col WHERE table_schema = '"+DataBaseName+"' ) bb  ")
		 .append(" 		ON aa.c_code = Column_name  ")
		 .append("  AND aa.col_type = bb.column_type ")
		 .append("   AND aa.c_table_name = bb.table_name ")
		 .append(" WHERE ")
		 .append("   bb.column_type IS NULL  ")
		 .append("     ) a ");
		 List list = sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
		 return list;
	}
	 
	@Override
	public List queryCreRelaTab() {
		getDataBaseName();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ")
			.append(" concat(\"create table \", a.tablename,\"( `id`  bigint(20) NOT NULL AUTO_INCREMENT, ")
			.append("`ABP0001`  varchar(32) Not NULL ,`ABC0913`  varchar(32) DEFAULT NULL ,`ABC0914`  varchar(32) DEFAULT NULL,PRIMARY KEY (`id`))\")  ")
			.append("FROM ")
			.append("(SELECT concat('t_',c_code,'_r1') tablename  FROM  t_c_onelevel_item    WHERE  c_data_type='记录类型') a  ")
			.append(" LEFT JOIN (SELECT table_name FROM information_schema.tables t  WHERE  ")
			.append(" t.table_schema = '"+DataBaseName+"') b ON a.tablename = b.table_name  ")
			.append("WHERE ")
			.append("b.table_name IS NULL ");
		
		List list = sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
		return list;
	}
	
	@Override
	public List queryCreRelaTabFun() {
		getDataBaseName();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ")
			.append("  concat( ")
			.append(" '")
			.append(" CREATE FUNCTION GetALLRelated',c_code,'(pCodes VARCHAR(4000),pRelationType  VARCHAR(20))")
			.append(" RETURNS VARCHAR(4000)")
			.append(" BEGIN")
			.append("\r\n")//预期结果.
			.append("  DECLARE vResult VARCHAR(4000);")
			.append("\r\n")//  -- 临时结果
			.append("  DECLARE vTemp VARCHAR(4000);")
			.append("  SET vTemp = pCodes;")
			.append(" SET vResult = \"\";")
			.append("\r\n")//-- 循环处理。
			.append("  WHILE vTemp is not null  DO")
			.append("  SELECT GROUP_CONCAT( distinct  ABC0913) INTO vTemp  FROM ', tablename,' WHERE FIND_IN_SET(ABP0001,vTemp) and not FIND_IN_SET(ABC0913,vResult) and ABC0914=pRelationType;")
			.append("   if vTemp is not null then")
			.append("   SET vResult = concat(vResult,\",\", vTemp);")
			.append("   end if;")
			.append("  END WHILE;")
			.append("\r\n")//-- 返回结果
			.append("  RETURN substr(vResult,2);")
			.append("END;  '")
			.append("\r\n")
			.append(")")
			.append(" FROM")
			.append("     (SELECT ")
			.append("   concat('t_',c_code,'_r1') tablename,c_code")
			.append("    FROM")
			.append("    t_c_onelevel_item ")
			.append("    WHERE")
			.append("   c_data_type='记录类型') a")
			.append("    LEFT JOIN")
			.append(" (SELECT ")
			.append(" table_name")
			.append(" FROM")
			.append(" information_schema.tables t")
			.append(" WHERE")
			.append("    t.table_schema = '"+DataBaseName+"') b ON a.tablename = b.table_name")
			.append(" WHERE")
			.append(" b.table_name IS NULL");
		
		List list = sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
		return list;
	}

	@Override
	public List queryCreateIndexTbl() {
		getDataBaseName();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  ")
		.append("    concat(\"create table \", a.tablename,\"( `id`  bigint(20) NOT NULL AUTO_INCREMENT, ")
		.append("  `ABP0001`  varchar(32) Not NULL ,`ABC0916`  varchar(512) DEFAULT NULL ,`ABC0917`  int(11) DEFAULT NULL,`ABC0918`  bigint(20) DEFAULT  ")
		.append(" NULL,PRIMARY KEY (`id`));\") ")
		.append(" FROM ")
		.append("     (SELECT  ")
		.append("   concat('t_',c_code,'_idx1') tablename ")
		.append("     FROM ")
		.append("       t_c_onelevel_item  ")
		.append("     WHERE ")
		.append("     c_data_type='记录类型') a ")
		.append("       LEFT JOIN  ")
		.append("     (SELECT  ")
		.append("      table_name ")
		.append("     FROM ")
		.append("      information_schema.tables t ")
		.append("   WHERE ")
		.append("   t.table_schema = 'abctest') b ON a.tablename = b.table_name ")
		.append(" WHERE ")
		.append("   b.table_name IS NULL  ");
		
		List list = sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
		return list;
	}
	
	@Override
	public void excuteBySql(String sql) {
		sFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	/**
	 * 获取当前链接的数据库名字
	 */
	private void getDataBaseName() {
		Session ss = sFactory.getCurrentSession();
		ss.doWork(new Work(){
			@Override
			public void execute(Connection connection) throws SQLException {
				String catalog = connection.getCatalog();
				DataBaseName = catalog;
			}
		});
	}

	@Override
	public List queryEntityTable(String entityCode) {
		getDataBaseName();
		String sql = "SELECT table_name  FROM information_schema.tables t WHERE t.table_schema = '"+DataBaseName+"' AND t.TABLE_NAME like 't_"+entityCode+"%'";
		List list = sFactory.getCurrentSession().createSQLQuery(sql).list();
		return list;
	}

	@Override
	public List queryEntityCol(String entityCode) {
		getDataBaseName();
		String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name LIKE 't_"+entityCode+"_%' and table_schema = '"+DataBaseName+"'";
		List list = sFactory.getCurrentSession().createSQLQuery(sql).list();
		return list;
	}

	@Override
	public BigInteger getTwoSameCount(String name, String entityId) {
		String sql = "SELECT	COUNT( * ) FROM	("
				+ " SELECT	a.c_cn_name FROM t_c_basic_item a "
				+ "	inner join t_c_onelevel_item o"
				+ "	on a.c_code=o.c_code "
				+ " WHERE 	a.c_parent = :entityId "
				+ " 	AND o.c_data_type != '分组类型' "
				+ "	AND o.c_data_type != '重复类型' 	) b "
				+ "WHERE	b.c_cn_name =:name";
		
		
		  List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter("entityId", entityId).setParameter("name", name).list();
		  return (BigInteger) list.get(0);
	}

	@Override
	public List getComm(String entityId) {
		String sql = "SELECT 	t.c_code CODE,	t.c_cn_name NAME FROM 	t_c_basic_item t "
				+ "	inner join t_c_onelevel_item o "
				+ "	on t.c_code=o.c_code"
				+ " WHERE	t.c_parent =:entityId "
				+ "	AND o.c_data_type != '重复类型' "
				+ "	AND o.c_data_type != '分组类型' "
				+ "	AND t.c_using_state = '1' "
				+ "	AND t.c_code NOT LIKE '%_P'  "
				+ "	UNION "
				+ "	SELECT 	t.c_code CODE, 	t.c_cn_name NAME "
				+ " FROM 	t_c_basic_item t "
				+ "	INNER JOIN t_c_towlevelattr w on w.c_code=t.c_code "
				+ " WHERE 	t.c_parent =:entityId ";
		 List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter("entityId", entityId).list();
		return list;
	}

	@Override
	public List<BasicItem> getEntityList(String leftRecordType) {
		String sql = "SELECT 	* FROM 	t_c_basic_item t "
				+ "	inner join t_c_onelevel_item o 	on t.c_code=o.c_code "
				+ "WHERE 	t.c_using_state = '1' "
				+ "	AND o.c_data_type = '记录类型' "
				+ "	AND t.c_code IN ( SELECT right_record_type FROM t_c_record_relation_type WHERE left_record_type =: leftRecordType )";
		return  sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("leftRecordType", leftRecordType).list();
	}


}
