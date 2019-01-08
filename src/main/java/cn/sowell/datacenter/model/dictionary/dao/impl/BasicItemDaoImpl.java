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

import com.abc.util.AttributeParter;
import com.abc.util.ValueType;

import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.CascadeAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.node.pojo.BasicItemNodeGenerator;

@Repository
public class BasicItemDaoImpl implements BasicItemDao {
	public static String DataBaseName;
	
	@Resource
	SessionFactory sFactory;
	
	@Override
	public List<BasicItem> queryList(BasicItemCriteria criteria, String dataType) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CAST((reverse( - ( - reverse( substring_index( b.c_code, '_', 1 ) ) ) ) ) as SIGNED) as c_order, b.* FROM t_sc_basic_item b inner JOIN t_sc_onelevel_item o on b.c_code=o.c_code WHERE 1=1 ");
		
		if(criteria.getUsingState() != null && criteria.getUsingState().SIZE > 0){
			sb.append(" AND b.c_using_state=:usingState");
		}
		if(TextUtils.hasText(criteria.getParent())){
			sb.append(" AND b.c_parent=:parent");
		}
		if(TextUtils.hasText(criteria.getCnName())){
			sb.append(" AND b.c_cn_name like :cnName");
		}
		if(criteria.getOneLevelItem() !=null && TextUtils.hasText(criteria.getOneLevelItem().getDataType())){
			sb.append(" AND o.c_data_type=:dataType");
		}
		
		if(dataType !="" && dataType != null){
			sb.append(" AND o.c_data_type!="+dataType+"");
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
		if(criteria.getOneLevelItem() !=null && TextUtils.hasText(criteria.getOneLevelItem().getDataType())){
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
	public List<BasicItem> getDataByPId(String parent, String dataType) {
		String sql = "SELECT * FROM t_sc_basic_item t left join t_sc_onelevel_item o ON t.c_code = o.c_code "
				+ "WHERE t.c_parent=:parent "
				+ "AND t.c_code not like '%_P' "
				+ "AND t.c_code not like '%_ED' "
				+ "AND t.c_code not like '%_SF' "
				+ "AND t.c_code not like '%_SK' "
				+ "AND t.c_code not like '%_N' "
				+ "AND o.c_data_type !="+ValueType.LABLETYPE.getIndex()+" ";
				if (dataType!="" && dataType!=null) {
					sql+="AND o.c_data_type="+dataType+"";
				}
				sql+= " ORDER BY  "
				+ "CAST((reverse( - ( - reverse( substring_index( t.c_code, '_', 1 ) ) ) )) as SIGNED) ASC";
		List<BasicItem> list = sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("parent", parent).list();
		return list;
	}
	
	@Override
	public List<BasicItem> getChilByPid(String parent) {
		String sql = "SELECT * FROM t_sc_basic_item WHERE c_parent=:parent ORDER BY  CAST((reverse( - ( - reverse( substring_index( c_code, '_', 1 ) ) ) )) as SIGNED) ASC";
		List<BasicItem> list = sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("parent", parent).list();
		return list;
	}

	@Override
	public List getAttrByPidGroupName(String parent, String groupName, String dataType) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM t_sc_basic_item t ")
		.append(" INNER JOIN t_sc_onelevel_item o ON t.c_code = o.c_code  ")
		.append(" WHERE ")
		.append(" 	t.c_parent =:parent  ")
		.append(" 	AND o.c_group_name =:groupName  ")
		.append(" 	AND t.c_code NOT LIKE '%_P'  ")
		.append(" AND t.c_code NOT LIKE '%_ED' ")
		.append(" 	AND t.c_code NOT LIKE '%_SF'  ")
		.append(" 	AND t.c_code NOT LIKE '%_SK'  ")
		.append(" 	AND t.c_code NOT LIKE '%_N'  ");
		if (dataType != "" && dataType !=null) {
			sb.append(" 	AND o.c_data_type="+dataType+" ");
		}
		sb.append(" ORDER BY")
		.append(" CAST( ( reverse( - ( - reverse( substring_index( t.c_code, '_', 1 ) ) ) ) ) AS SIGNED ) ASC ");
		
		String sql = sb.toString();
		try {
			List<BasicItem> list = sFactory.getCurrentSession()
					.createSQLQuery(sql)
					.addEntity(BasicItem.class)
					.setParameter("parent", parent)
					.setParameter("groupName", groupName)
					.list();
			return list;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveOrUpdate(Object obj, String flag)  throws Exception {
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
			Object[] basicItemFix = getBasicItemFix();
			return btNg.getEntityCode((String)basicItemFix[1]);
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
			Object[] basicItemFix = getBasicItemFix();
			return btNg.getAttrCode((String)basicItemFix[2]);
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
			.append("t_sc_onelevel_item ")
			.append("WHERE ")
			.append(" c_data_type!='"+ValueType.LABLETYPE.getIndex()+"' and ")
			.append(" c_data_type!='"+ValueType.ENUMTYPE_MULTI.getIndex()+"' and ")
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
			.append(" WHEN '"+ValueType.STRING.getIndex()+"' THEN concat('varchar(',if(a.c_data_range  is null,\"32\",a.c_data_range),')') ")
			.append(" WHEN '"+ValueType.STRING_PREENUM.getIndex()+"' THEN concat('varchar(',if(a.c_data_range  is null,\"32\",a.c_data_range),')') ")
			.append(" WHEN '"+ValueType.INT.getIndex()+"' THEN concat('int(',if(a.c_data_range is null ,\"11\",a.c_data_range),')') ")
			.append(" WHEN '"+ValueType.NUMBER.getIndex()+"' THEN  concat('double(',if(a.c_data_range is null ,\"10,2\",a.c_data_range),')') ")
			.append(" WHEN '"+ValueType.CASCADETYPE.getIndex()+"' THEN concat('varchar(',if(a.c_data_range  is null,\"32\",a.c_data_range),')') ")
			.append(" WHEN '"+ValueType.DATE.getIndex()+"' THEN 'date' ")
			.append(" WHEN '"+ValueType.DATETIME.getIndex()+"' THEN 'datetime' ")
			.append(" WHEN '"+ValueType.BYTES.getIndex()+"' THEN 'MediumBlob' ")
			.append(" WHEN '"+ValueType.ENUMTYPE.getIndex()+"' THEN 'varchar(32)'")
			.append(" WHEN '"+ValueType.REFERENCE.getIndex()+"' THEN 'varchar(32)'")
			.append(" WHEN '"+ValueType.SUBCASTYPE.getIndex()+"' THEN 'varchar(32)'")
			.append(" WHEN '"+ValueType.PASSWORD.getIndex()+"' THEN concat('varchar(',if(a.c_data_range  is null,\"32\",a.c_data_range),')') ")
			.append(" END, ")
			.append("'  default NULL ') ")
			.append("FROM ")
			.append("(SELECT  *  FROM  t_sc_onelevel_item WHERE ")
			.append(" c_data_type != '"+ValueType.RECORD.getIndex()+"' ")
			.append("AND c_data_type != '"+ValueType.REPEAT.getIndex()+"'")
			.append("AND c_data_type != '"+ValueType.LABLETYPE.getIndex()+"'")
			.append("AND c_data_type != '"+ValueType.ENUMTYPE_MULTI.getIndex()+"'")
			.append(" AND c_data_type != '"+ValueType.GROUP.getIndex()+"') a ")
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
		 .append("  WHEN '5' THEN    CONCAT( 'varchar(', IF ( a.c_data_range IS NULL, '32', a.c_data_range ), ')' )  ")
		 .append("  WHEN '52' THEN    CONCAT( 'varchar(', IF ( a.c_data_range IS NULL, '32', a.c_data_range ), ')' )  ")
		 .append("  WHEN '1' THEN    CONCAT( 'int(', IF ( a.c_data_range IS NULL, '11', a.c_data_range ), ')' )  ")
		 .append(" WHEN '15' THEN    CONCAT( 'double(', IF ( a.c_data_range IS NULL, '10,2', a.c_data_range ), ')' )  ")
		 .append("  WHEN '17' THEN    CONCAT( 'varchar(', IF ( a.c_data_range IS NULL, '32', a.c_data_range ), ')' )  ")
		 .append("  WHEN '6' THEN     'date'  ")
		 .append("  WHEN '7' THEN     'datetime'  ")
		 .append(" WHEN '8' THEN    'mediumblob'  ")
		 .append("  WHEN '11' THEN    'varchar(32)'  ")
		 .append("  WHEN '14' THEN    'varchar(32)'  ")
		 .append(" WHEN '"+ValueType.SUBCASTYPE.getIndex()+"' THEN 'varchar(32)'")
		 .append(" WHEN '"+ValueType.PASSWORD.getIndex()+"' THEN concat('varchar(',if(a.c_data_range  is null,\"32\",a.c_data_range),')') ")
		 .append(" END col_type  ")
		 .append(" FROM ")
		 .append("     t_sc_onelevel_item a  ")
		 .append(" WHERE  ")
		 .append("  a.c_data_type != '16' ")
		 .append(" AND a.c_data_type != '10' ")
		 .append("AND c_data_type != '"+ValueType.LABLETYPE.getIndex()+"'")
		 .append("AND c_data_type != '"+ValueType.ENUMTYPE_MULTI.getIndex()+"'")
		 .append(" AND a.c_data_type != '9' ")
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
			.append("(SELECT concat('t_',c_code,'_r1') tablename  FROM  t_sc_onelevel_item    WHERE  c_data_type='10') a  ")
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
			.append("    t_sc_onelevel_item ")
			.append("    WHERE")
			.append("   c_data_type='10') a")
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
		.append("       t_sc_onelevel_item  ")
		.append("     WHERE ")
		.append("     c_data_type='10') a ")
		.append("       LEFT JOIN  ")
		.append("     (SELECT  ")
		.append("      table_name ")
		.append("     FROM ")
		.append("      information_schema.tables t ")
		.append("   WHERE ")
		.append("   t.table_schema = '"+DataBaseName+"') b ON a.tablename = b.table_name ")
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
				+ " SELECT	a.c_cn_name FROM t_sc_basic_item a "
				+ "	inner join t_sc_onelevel_item o"
				+ "	on a.c_code=o.c_code "
				+ " WHERE 	a.c_parent = :entityId "
				+ " 	AND o.c_data_type != '16' "
				+ "	AND o.c_data_type != '9' 	) b "
				+ "WHERE	b.c_cn_name =:name";
		
		
		  List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter("entityId", entityId).setParameter("name", name).list();
		  return (BigInteger) list.get(0);
	}

	@Override
	public List getComm(String entityId) {
		String sql = "SELECT 	t.c_code CODE,	t.c_cn_name NAME, o.c_data_type dataType  FROM 	t_sc_basic_item t "
				+ "	inner join t_sc_onelevel_item o on t.c_code=o.c_code"
				+ " WHERE	t.c_parent =:entityId "
				+ " AND o.c_data_type != '9' "
				+ " AND o.c_data_type != '16' "
				+ " AND o.c_data_type != '18' "
				+ " AND o.c_data_type != '"+ValueType.CASCADETYPE.getIndex()+"' "
				+ " AND t.c_using_state = '1' "
				+ "UNION "
				+ " SELECT 	t.c_code CODE, 	t.c_cn_name NAME, c.c_data_type dataType "
				+ " FROM 	t_sc_basic_item t "
				+ " INNER JOIN t_sc_twolevel_attr w on w.c_code=t.c_code "
				+ "	left JOIN t_sc_twolevelattr_multiattr_mapping m 	on w.c_mapping_id=m.id"
				+ "	left JOIN t_sc_onelevel_item c on m.c_value_attr=c.c_code "
				+ " WHERE 	t.c_parent =:entityId ";
		 List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter("entityId", entityId).list();
		return list;
	}

	@Override
	public List<BasicItem> getEntityList(String leftRecordType) {
		String sql = "SELECT 	t.* FROM 	t_sc_basic_item t "
				+ "	inner join t_sc_onelevel_item o 	on t.c_code=o.c_code "
				+ "WHERE 	t.c_using_state = '1' "
				+ "	AND o.c_data_type = '10' "
				+ "	AND t.c_code IN ( SELECT right_record_type FROM t_sc_record_relation_type WHERE left_record_type =:leftRecordType )";
		return  sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("leftRecordType", leftRecordType).list();
	}

	@Override
	public Object[] getBasicItemFix() throws Exception {
		String sql = "SELECT id, recordtype, attribute, relation, using_state FROM `t_sc_basic_item_fix` WHERE using_state='1' ORDER BY id desc";
		List list = sFactory.getCurrentSession().createSQLQuery(sql).list();
		
		if (list.isEmpty()) {
			throw new Exception("t_sc_basic_item_fix不能没有数据");
		}
		
		return (Object[]) list.get(0);
	}

	@Override
	public List getCascadeAttrChild(String code) {
		String sql = "SELECT 	b.c_code,	b.c_cas_code,	a.c_cn_name,	b.c_level,	a.c_using_state "
				+ " FROM 	t_sc_basic_item a 	LEFT JOIN t_sc_cascade_attr b ON a.c_code = b.c_cas_code "
				+ " WHERE 	b.c_cas_code  IN ( SELECT c_cas_code FROM `t_sc_cascade_attr` WHERE c_code =:code) "
				+ "	AND b.c_code=:code order by b.c_level asc";
		return sFactory.getCurrentSession().createSQLQuery(sql).setParameter("code", code).list();
	}

	@Override
	public void saveCascaseAttrChild(CascadeAttr cascadeAttr) throws Exception {
		sFactory.getCurrentSession().save(cascadeAttr);
	}

	@Override
	public void delCascaseAttrChild(String code, String casCode) throws Exception {
		String sql = " DELETE FROM t_sc_cascade_attr WHERE c_code=:code and c_cas_code=:casCode";
		 sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("code", code)
				.setParameter("casCode", casCode).executeUpdate();
	}

	@Override
	public List getGroupCascaseAttr(String entityId) {
		String sql = "SELECT a.c_code, a.c_cn_name, b.c_data_type FROM t_sc_basic_item a "
				+ " inner join t_sc_onelevel_item b on a.c_code=b.c_code "
				+ "WHERE a.c_parent=:entityId AND b.c_data_type=:dataType";
		return sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("entityId", entityId)
				.setParameter("dataType", ValueType.CASCADETYPE.getIndex()).list();
	}

	@Override
	public List getMoreCascaseAttr(String parentId) {
		String sql = "SELECT a.c_code, a.c_cn_name, b.c_data_type FROM t_sc_basic_item a "
				+ " inner join t_sc_onelevel_item b on a.c_code=b.c_code "
				+ " WHERE a.c_parent=:parentId AND b.c_data_type=:dataType";
		return sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("parentId", parentId)
				.setParameter("dataType", ValueType.CASCADETYPE.getIndex()).list();
	}

	@Override
	public BigInteger canAddChildCount(String code) throws Exception {
		String sql = " SELECT (SELECT LENGTH(c_cas_pid)-LENGTH(REPLACE(c_cas_pid, '.', '')) as count  "
				+ " FROM `t_sc_cascadedict_basic_item` "
				+ " WHERE c_cas_pid LIKE (SELECT concat(c_cas_pid, '.', id, '%') FROM t_sc_cascadedict_basic_item "
						+ " WHERE id=(SELECT c_dict_parent_id FROM t_sc_onelevel_item "
						+ " WHERE c_code=:code)) ORDER BY LENGTH(c_cas_pid) DESC LIMIT 1)-"
						+ " (SELECT count(*) FROM t_sc_cascade_attr where c_code=:code)";
		return (BigInteger) sFactory.getCurrentSession().createSQLQuery(sql).setParameter("code", code).uniqueResult();
	}

	@Override
	public List getCascadeAttrChildPojo(String code, String casCode) {
		String sql =" SELECT c_code, c_cas_code, c_level FROM `t_sc_cascade_attr` WHERE c_code=:code  and c_cas_code !=:casCode order by c_level asc";
		return sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("code", code)
				.setParameter("casCode", casCode).list();
	}

	@Override
	public void updateCasCadeLevel(String code, String casCade, int level) throws Exception {
		String sql = "UPDATE t_sc_cascade_attr set c_level=:level  WHERE c_code=:code AND c_cas_code=:casCade";
		sFactory.getCurrentSession().createSQLQuery(sql)
		.setParameter("code", code)
		.setParameter("casCade", casCade)
		.setParameter("level", level).executeUpdate();
	}

	@Override
	public BasicItem getLableObj(String code) throws Exception {
		String sql = "SELECT * FROM t_sc_basic_item  a "
				+ "inner join t_sc_onelevel_item  b "
				+ "on a.c_code=b.c_code "
				+ "WHERE c_parent=:code "
				+ "AND b.c_data_type=:dataType";
		return (BasicItem) sFactory.getCurrentSession().createSQLQuery(sql)
				.addEntity(BasicItem.class)
				.setParameter("code", code)
				.setParameter("dataType", ValueType.LABLETYPE.getIndex()).uniqueResult();
	}

	@Override
	public List getAllEntity() {
		String sql = "SELECT a.c_code, a.c_cn_name FROM t_sc_basic_item a "
				+ "LEFT JOIN t_sc_onelevel_item b "
				+ "ON a.c_code=b.c_code WHERE b.c_data_type=10 and a.c_using_state=1";
		return sFactory.getCurrentSession().createSQLQuery(sql).list();
	}

	@Override
	public List queryCreLable() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ")
		.append("  concat(\"create table \", a.tablename,\"( `id`  bigint(20) NOT NULL AUTO_INCREMENT,  ")
		.append(" `ABP0001`  varchar(32) Not NULL ,`\", a.code, \""+AttributeParter.getLeafKeyName("")+"`  varchar(32) DEFAULT NULL, `\" , a.code, \""+AttributeParter.getLeafEditTimeName("")+"`  varchar(32) DEFAULT NULL, `\" ,a.code,\""+AttributeParter.getLabelValueName("")+"` ")
		.append(" varchar(32) DEFAULT NULL,PRIMARY KEY (`id`))\")  ")
		.append(" FROM ")
		.append(" 	(SELECT CONCAT('t_', b.c_parent,'_', a.c_code) tablename, a.c_code code FROM t_sc_onelevel_item a")
		.append(" 	inner join t_sc_basic_item b ")
		.append(" on a.c_code=b.c_code")
		.append(" WHERE c_data_type ="+ValueType.LABLETYPE.getIndex()+") a  ")
		.append("  LEFT JOIN (SELECT table_name FROM information_schema.tables t  WHERE  ")
		.append("  t.table_schema = '"+DataBaseName+"') b ON a.tablename = b.table_name ")
		.append(" 	WHERE ")
		.append(" b.table_name IS NULL ");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreEntityEditTimeTab() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  ")
		.append("  concat(\"create table \", a.tablename,\"( `id`  bigint(20) NOT NULL AUTO_INCREMENT, ")
		.append(" `ABP0001`  varchar(32) Not NULL , \", a.c_code,  \"")
		.append(AttributeParter.getLeafEditTimeName("")+"'")
		.append(" datetime ,PRIMARY KEY (`id`))\") ")
		.append(" FROM  ")
		.append(" (SELECT concat('t_',c_code,'_m') tablename, c_code  FROM  t_sc_onelevel_item    WHERE  c_data_type='10') a  ")
		.append("  LEFT JOIN (SELECT table_name FROM information_schema.tables t  WHERE   ")
		.append("  t.table_schema = '"+DataBaseName+"') b ON a.tablename = b.table_name   ")
		.append(" WHERE ")
		.append(" b.table_name IS NULL ");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryEnumMuliCreTab() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT ")
		.append(" concat( \"create table \", a.tablename, \"( `id`  bigint(20) NOT NULL AUTO_INCREMENT, `ABP0001`  varchar(32) Not NULL ,\",")
		.append(" a.c_code,\"_V  varchar(32) DEFAULT NULL ,\",")
		.append(" a.c_code,\"_ED datetime ,\",")
		.append(" a.c_code,\"_P  varchar(32) , \",")
		.append(" \"PRIMARY KEY (`id`))\" ) ")
		.append(" FROM")
		.append(" ( SELECT c_table_name tablename, c_code FROM t_sc_onelevel_item WHERE c_data_type = '1401' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE	b.table_name IS NULL");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public BasicItem getGroup(String parrentCode) {
		String sql = " SELECT a.* FROM t_sc_basic_item a" + 
				" inner join t_sc_onelevel_item b on a.c_code=b.c_code" + 
				" WHERE c_parent=:parrentCode AND b.c_data_type='16'";
		return (BasicItem) sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("parrentCode", parrentCode).uniqueResult();
	}


}
