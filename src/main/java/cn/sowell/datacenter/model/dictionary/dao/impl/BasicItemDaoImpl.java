package cn.sowell.datacenter.model.dictionary.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import cn.sowell.copframe.dao.deferedQuery.DeferedParamQuery;
import cn.sowell.copframe.dao.deferedQuery.sqlFunc.WrapForCountFunction;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;

@Repository
public class BasicItemDaoImpl implements BasicItemDao {
	public static String DataBaseName;
	
	@Resource
	SessionFactory sFactory;

	@Override
	public List<BasicItem> queryList(BasicItemCriteria criteria) {
		String hql = "from BasicItem b";
		DeferedParamQuery dQuery = new DeferedParamQuery(hql);
		if(TextUtils.hasText(criteria.getCnName())){
			dQuery.appendCondition(" and b.cnName like :cnName")
					.setParam("cnName", "%" + criteria.getCnName() + "%");
		}
		if(TextUtils.hasText(criteria.getDataType())){
			dQuery.appendCondition(" and b.dataType = :dataType")
					.setParam("dataType", criteria.getDataType());
		}
		Query countQuery = dQuery.createQuery(sFactory.getCurrentSession(), true, new WrapForCountFunction());
		Integer count = FormatUtils.toInteger(countQuery.uniqueResult());
		if(count > 0){
			Query query = dQuery.createQuery(sFactory.getCurrentSession(), true, null);
			return query.list();
		}
		return new ArrayList<BasicItem>();
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
		String sql = "from BasicItem WHERE parent=:parent AND code not LIKE '%_P' AND code not like '%__ED'";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("parent", parent).list();
		return list;
	}
	
	@Override
	public List<BasicItem> getChilByPid(String parent) {
		String sql = "from BasicItem WHERE parent=:parent";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("parent", parent).list();
		return list;
	}

	@Override
	public List getAttrByPidGroupName(String parent, String groupName) {
		String sql = "from BasicItem WHERE parent=:parent AND groupName=:groupName";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("parent", parent).setParameter("groupName", groupName).list();
		return list;
	}

	@Override
	public void saveOrUpdate(BasicItem obj, String flag) {
		if ("add".equals(flag)) {
			sFactory.getCurrentSession().save(obj);
		} else {
			sFactory.getCurrentSession().update(obj);
		}
		
	}
	
	//实体code  生成规则
	public String getEntityCode() {
		String sql = "SELECT SUBSTR(c_code, 5) aa from t_c_basic_item WHERE c_data_type='记录类型' AND c_code like 'IBT%' AND c_code not like '%_ED' AND c_code not like '%_P' ORDER BY  CAST(aa as SIGNED ) DESC";
		List list = sFactory.getCurrentSession().createSQLQuery(sql).list();

		if (list.isEmpty()) {
			return "IBTE001";
		}
		
		String entityCode = (String) list.get(0);
		int code = Integer.parseInt(entityCode) + 1;
		String entityHead = "IBTE";
        String format = String.format("%03d", code);  
		
		return entityHead+format;
	}
	
	//其他code， 生成规则
	public String getAttrCode() {
		String sql = "SELECT SUBSTR(c_code, 4) aa from t_c_basic_item WHERE c_data_type!='记录类型' AND c_code like 'IBT%' AND c_code not like '%_ED' AND c_code not like '%_P' ORDER BY  CAST(aa as SIGNED ) DESC";
		
		List list = sFactory.getCurrentSession().createSQLQuery(sql).list();

		if (list.isEmpty()) {
			return "IBT0001";
		}
		
		String entityCode = (String) list.get(0);
		int code = Integer.parseInt(entityCode) + 1;
		String entityHead = "IBT";
        String format = String.format("%04d", code);  
		return entityHead+format;
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
			.append("t_c_basic_item ")
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
		System.out.println(sb.toString());	
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
			.append(" WHEN '二进制型' THEN 'blob' ")
			.append(" END, ")
			.append("'  default NULL ') ")
			.append("FROM ")
			.append("(SELECT  *  FROM  t_c_basic_item WHERE ")
			.append(" c_data_type != '记录类型' ")
			.append("AND c_data_type != '重复类型'")
			.append(" AND c_data_type != '分组类型') a ")
			.append(" LEFT JOIN ")
			.append(" (SELECT  ")
			.append(" col.column_name  FROM  information_schema.columns col  WHERE ")
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
		 sb.append(" SELECT a.c_code,	CONCAT( ")
		 .append(" 'alter table ',	a.c_table_name,	' CHANGE COLUMN ',	a.c_code,	' ',")
		 .append(" a.c_code,	' ',	a.col_type,	'  default NULL ;'	)")
		 .append(" FROM	(		SELECT			aa.c_table_name,	aa.c_code,aa.col_type")
		 .append(" FROM	(	SELECT	a.c_code,	a.c_cn_name,	a.c_table_name,")
		 .append(" CASE a.c_data_type	")
		 .append(" WHEN '字符型' THEN	CONCAT(	'varchar(',	IF (a.c_data_range = '枚举',	'32',	a.c_data_range),	')'	)	")
		 .append(" WHEN '数字型' THEN	CONCAT(	'int(',	IF (	a.c_data_range IS NULL,		'11',	a.c_data_range),')'	)")
		 .append(" WHEN '数字型小数' THEN		CONCAT(	'double(',	IF (	a.c_data_range IS NULL,	'10,2',		a.c_data_range),')'	)")
		 .append(" WHEN '日期型' THEN	'date'")
		 .append(" WHEN '时间型' THEN	'datetime'")
		 .append(" WHEN '二进制型' THEN	'blob'")
		 .append(" END col_type	FROM	t_c_basic_item a")
		 .append(" WHERE	a.c_code LIKE 'IBT%'")
		 .append(" AND a.c_data_type != '分组类型'")
		 .append(" AND a.c_data_type != '记录类型'")
		 .append(" AND a.c_data_type != '重复类型'")
		 .append(" 	) aa")
		 .append(" LEFT JOIN (")
		 .append(" SELECT	col.Column_name,	col.column_type,col.table_name	FROM")
		 .append(" information_schema. COLUMNS col")
		 .append(" WHERE	table_schema = '"+DataBaseName+"'	) bb ON aa.c_code = Column_name")
		 .append(" AND aa.col_type = bb.column_type	AND aa.c_table_name = bb.table_name")
		 .append(" WHERE	bb.column_type IS NULL	) a");
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
			.append("(SELECT concat('t_',c_code,'_r1') tablename  FROM  t_c_basic_item    WHERE  c_data_type='记录类型') a  ")
			.append(" LEFT JOIN (SELECT table_name FROM information_schema.tables t  WHERE  ")
			.append(" t.table_schema = '"+DataBaseName+"') b ON a.tablename = b.table_name  ")
			.append("WHERE ")
			.append("b.table_name IS NULL ");
		
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
	public BigInteger geSameCount(String cnName, String entityId) {
		String sql = "SELECT COUNT(*) FROM ("
				+ "	SELECT c.c_name FROM t_c_towlevelattr c"
				+ "	WHERE c.c_mapping_id in("
				+ "			SELECT a.id FROM t_c_towlevelattr_multiattr_mapping a "
				+ "			WHERE a.c_related_multiattribute in("
				+ "				SELECT c_code FROM t_c_basic_item b"
				+ "				WHERE b.c_data_type = '重复类型' AND b.c_parent=:entityId"
				+ "		)	)) d WHERE d.c_name =:cnName";
		
		   List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter("entityId", entityId).setParameter("cnName", cnName).list();
		return (BigInteger) list.get(0);
	}

	@Override
	public BigInteger getTwoSameCount(String name, String entityId) {
		String sql = "SELECT COUNT(*) FROM (	SELECT a.c_cn_name FROM t_c_basic_item a"
				+ "	WHERE a.c_parent = :entityId "
				+ "	AND a.c_data_type != '分组类型' "
				+ "	AND a.c_data_type != '重复类型') b"
				+ " WHERE b.c_cn_name=:name";
		  List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter("entityId", entityId).setParameter("name", name).list();
		  return (BigInteger) list.get(0);
	}

}
