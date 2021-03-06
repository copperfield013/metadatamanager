package cn.sowell.datacenter.model.dictionary.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.abc.util.AttributeParter;
import com.abc.model.enun.ValueType;

import cn.sowell.copframe.dao.deferedQuery.ColumnMapResultTransformer;
import cn.sowell.copframe.dao.deferedQuery.SimpleMapWrapper;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItemCodeGenerator;
import cn.sowell.datacenter.model.dictionary.pojo.CascadeAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;

@Repository
public class BasicItemDaoImpl implements BasicItemDao {
	public static String DataBaseName;
	
	@Resource
	SessionFactory sFactory;
	
	@Override
	public List<BasicItem> queryList(BasicItemCriteria criteria, String dataType) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CAST((reverse( - ( - reverse( substring_index( b.c_code, '_', 1 ) ) ) ) ) as SIGNED) as c_order, b.* FROM t_sc_basic_item b inner JOIN t_sc_bi_onelevel o on b.c_code=o.c_code WHERE 1=1 ");
		
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
		BasicItem bt = (BasicItem)pojo;
		sFactory.getCurrentSession().delete(bt);
		if(bt.getOneLevelItem() != null) {
			sFactory.getCurrentSession().delete(bt.getOneLevelItem());
		}
	}

	@Override
	public List<BasicItem> getDataByPId(String parent, String dataType) {
		
		List<BasicItem> btList = new ArrayList<BasicItem>();
		
		String sql = "SELECT t.c_cn_name, t.c_en_name,t.c_parent, t.c_using_state, t.c_description, o.* FROM t_sc_basic_item t left join t_sc_bi_onelevel o ON t.c_code = o.c_code "
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
		 Query query = sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("parent", parent);
		 query.setResultTransformer(new ColumnMapResultTransformer<byte[]>() {
				private static final long serialVersionUID = -392302880551548725L;
				
				@Override
				protected byte[] build(SimpleMapWrapper mapWrapper) {
					BasicItem bt = new BasicItem();
					bt.setCode(mapWrapper.getString("c_code"));
					bt.setCnName(mapWrapper.getString("c_cn_name"));
					bt.setEnName(mapWrapper.getString("c_en_name"));
					bt.setParent(mapWrapper.getString("c_parent"));
					bt.setUsingState(Integer.valueOf(mapWrapper.getString("c_using_state")));
					bt.setDescription(mapWrapper.getString("c_description"));
					
					OneLevelItem ot = new OneLevelItem();
					ot.setCode(mapWrapper.getString("c_code"));
					ot.setDataType(mapWrapper.getString("c_data_type"));
					ot.setDataRange(mapWrapper.getString("c_data_range"));
					ot.setDataTypeCode(mapWrapper.getString("c_data_type_code"));
					ot.setDataForm(mapWrapper.getString("c_data_form"));
					ot.setDictParentId(Integer.valueOf(mapWrapper.getString("c_dict_parent_id")));
					ot.setDictionaryIndex(mapWrapper.getString("c_dictionary_index"));
					ot.setTableName(mapWrapper.getString("c_table_name"));
					ot.setTableNameDescription(mapWrapper.getString("c_table_name_description"));
					ot.setGroupName(mapWrapper.getString("c_group_name"));
					ot.setRefType(mapWrapper.getString("c_ref_type"));
					ot.setNeedHistory(Integer.valueOf(mapWrapper.getString("c_need_history")));
					
					bt.setOneLevelItem(ot);
					btList.add(bt);
					return null;
				}
			});
		 query.list();
		return btList;
	}
	
	@Override
	public List<BasicItem> getChilByPid(String parent) {
		String sql = "SELECT * FROM t_sc_basic_item WHERE c_parent=:parent ORDER BY  CAST((reverse( - ( - reverse( substring_index( c_code, '_', 1 ) ) ) )) as SIGNED) ASC";
		List<BasicItem> list = sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("parent", parent).list();
		return list;
	}

	@Override
	public List getAttrByPidGroupName(String parent, String groupName, String dataType) {
		
		List<BasicItem> btList = new ArrayList<BasicItem>();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t.c_cn_name, t.c_en_name,t.c_parent, t.c_using_state, t.c_description, o.*  FROM t_sc_basic_item t ")
		.append(" INNER JOIN t_sc_bi_onelevel o ON t.c_code = o.c_code  ")
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
			 Query query = sFactory.getCurrentSession()
					.createSQLQuery(sql)
					.setParameter("parent", parent)
					.setParameter("groupName", groupName);
			
			query.setResultTransformer(new ColumnMapResultTransformer<byte[]>() {
				private static final long serialVersionUID = -392302880551548725L;
				
				@Override
				protected byte[] build(SimpleMapWrapper mapWrapper) {
					BasicItem bt = new BasicItem();
					bt.setCode(mapWrapper.getString("c_code"));
					bt.setCnName(mapWrapper.getString("c_cn_name"));
					bt.setEnName(mapWrapper.getString("c_en_name"));
					bt.setParent(mapWrapper.getString("c_parent"));
					bt.setUsingState(Integer.valueOf(mapWrapper.getString("c_using_state")));
					bt.setDescription(mapWrapper.getString("c_description"));
					
					OneLevelItem ot = new OneLevelItem();
					ot.setCode(mapWrapper.getString("c_code"));
					ot.setDataType(mapWrapper.getString("c_data_type"));
					ot.setDataRange(mapWrapper.getString("c_data_range"));
					ot.setDataTypeCode(mapWrapper.getString("c_data_type_code"));
					ot.setDataForm(mapWrapper.getString("c_data_form"));
					ot.setDictParentId(Integer.valueOf(mapWrapper.getString("c_dict_parent_id")));
					ot.setDictionaryIndex(mapWrapper.getString("c_dictionary_index"));
					ot.setTableName(mapWrapper.getString("c_table_name"));
					ot.setTableNameDescription(mapWrapper.getString("c_table_name_description"));
					ot.setGroupName(mapWrapper.getString("c_group_name"));
					ot.setRefType(mapWrapper.getString("c_ref_type"));
					ot.setNeedHistory(Integer.valueOf(mapWrapper.getString("c_need_history")));
					
					bt.setOneLevelItem(ot);
					btList.add(bt);
					return null;
				}
			});
			
			query.list();
			return btList;
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
	
	@Override
	public List queryCreTab() {
		getDataBaseName();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.c_code, ")
			.append("concat(\"create table \", a.c_table_name,\"( `id`  bigint(20) NOT NULL AUTO_INCREMENT, ")
			.append("`ABP0001`  varchar(32) DEFAULT NULL ,PRIMARY KEY (`id`),KEY `abp_index` (`ABP0001`) USING BTREE)\")  valstr ")
			.append("FROM ")
			.append("(SELECT ")
			.append(" c_code,  c_table_name ")
			.append("FROM ")
			.append("t_sc_bi_onelevel ")
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
			.append(" WHEN '"+ValueType.BYTES.getIndex()+"' THEN 'varchar(32)' ")
			.append(" WHEN '"+ValueType.ENUMTYPE.getIndex()+"' THEN 'varchar(32)'")
			.append(" WHEN '"+ValueType.REFERENCE.getIndex()+"' THEN 'varchar(32)'")
			.append(" WHEN '"+ValueType.SUBCASTYPE.getIndex()+"' THEN 'varchar(32)'")
			.append(" WHEN '"+ValueType.PASSWORD.getIndex()+"' THEN concat('varchar(',if(a.c_data_range  is null,\"32\",a.c_data_range),')') ")
			.append(" END, ")
			.append("'  default NULL ') ")
			.append("FROM ")
			.append("(SELECT  *  FROM  t_sc_bi_onelevel WHERE ")
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
		 .append(" WHEN '8' THEN    'varchar(32)'  ")
		 .append("  WHEN '11' THEN    'varchar(32)'  ")
		 .append("  WHEN '14' THEN    'varchar(32)'  ")
		 .append(" WHEN '"+ValueType.SUBCASTYPE.getIndex()+"' THEN 'varchar(32)'")
		 .append(" WHEN '"+ValueType.PASSWORD.getIndex()+"' THEN concat('varchar(',if(a.c_data_range  is null,\"32\",a.c_data_range),')') ")
		 .append(" END col_type  ")
		 .append(" FROM ")
		 .append("     t_sc_bi_onelevel a  ")
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
			.append("(SELECT concat('t_',c_code,'_r1') tablename  FROM  t_sc_bi_onelevel    WHERE  c_data_type='10') a  ")
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
			.append(" CREATE FUNCTION GetALLRelated', c_code, '(pCodes VARCHAR(4000),pRelationType  VARCHAR(20),pDepth int) ")
			.append(" RETURNS text CHARSET utf8 ")
			.append(" BEGIN")
			.append("\r\n")//预期结果.
			.append("  DECLARE vResult text;")
			.append("\r\n")//  -- 临时结果
			.append("  DECLARE vDepth int;")
			.append(" DECLARE vTemp text;")
			.append("  SET vTemp = pCodes; SET vResult = \"\"; set vDepth=0; ")
			.append(" if pDepth<0  then ")
			.append(" set vdepth=-1; ")
			.append(" end if;")
			.append(" WHILE vTemp is not null and vdepth<=pDepth")
			.append(" DO  SELECT GROUP_CONCAT( distinct  ABC0913) INTO vTemp  FROM ', tablename, ' WHERE FIND_IN_SET(ABP0001,vTemp) and not FIND_IN_SET(ABC0913,vResult) and ")
			.append(" ABC0914=pRelationType; ")
			.append(" if vTemp is not null then  ")
			.append(" SET vResult = concat(vResult,\",\", vTemp);  ")
			.append(" end if;")
			.append(" if pDepth>=0  then  ")
			.append(" set vdepth=vdepth+1; ")
			.append(" end if;")
			.append(" END WHILE;")
			.append(" RETURN substr(vResult,2);")
			.append(" END' ) ")
			.append(" FROM")
			.append(" ( SELECT concat( 't_', c_code, '_r1' ) tablename, c_code, concat('GetALLRelated', c_code) funName FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
			.append(" LEFT JOIN ( SELECT SPECIFIC_NAME FROM information_schema.ROUTINES t WHERE t.ROUTINE_SCHEMA = '"+DataBaseName+"' ) b ON a.funName = b.SPECIFIC_NAME ")
			.append(" WHERE b.SPECIFIC_NAME IS NULL");
		
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
				+ "	inner join t_sc_bi_onelevel o"
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
				+ "	inner join t_sc_bi_onelevel o on t.c_code=o.c_code"
				+ " WHERE	t.c_parent =:entityId "
				+ " AND o.c_data_type != '9' "
				+ " AND o.c_data_type != '16' "
				+ " AND o.c_data_type != '18' "
				+ " AND o.c_data_type != '"+ValueType.CASCADETYPE.getIndex()+"' "
				+ " AND t.c_using_state = '1' "
				+ "UNION "
				+ " SELECT 	t.c_code CODE, 	t.c_cn_name NAME, c.c_data_type dataType "
				+ " FROM 	t_sc_basic_item t "
				+ " INNER JOIN t_sc_bi_twolevel_attr w on w.c_code=t.c_code "
				+ "	left JOIN t_sc_bi_twolevelattr_multiattr_mapping m 	on w.c_mapping_id=m.id"
				+ "	left JOIN t_sc_bi_onelevel c on m.c_value_attr=c.c_code "
				+ " WHERE 	t.c_parent =:entityId ";
		 List list = sFactory.getCurrentSession().createSQLQuery(sql).setParameter("entityId", entityId).list();
		return list;
	}

	@Override
	public List<BasicItem> getEntityList(String leftRecordType) {
		String sql = "SELECT 	t.* FROM 	t_sc_basic_item t "
				+ "	inner join t_sc_bi_onelevel o 	on t.c_code=o.c_code "
				+ "WHERE 	t.c_using_state = '1' "
				+ "	AND o.c_data_type = '10' "
				+ "	AND t.c_code IN ( SELECT right_record_type FROM t_sc_record_relation_type WHERE left_record_type =:leftRecordType )";
		return  sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("leftRecordType", leftRecordType).list();
	}

	@Override
	public List getCascadeAttrChild(String code) {
		String sql = "SELECT 	b.c_code,	b.c_cas_code,	a.c_cn_name,	b.c_level,	a.c_using_state "
				+ " FROM 	t_sc_basic_item a 	LEFT JOIN t_sc_bi_cascade_attr b ON a.c_code = b.c_cas_code "
				+ " WHERE 	b.c_cas_code  IN ( SELECT c_cas_code FROM `t_sc_bi_cascade_attr` WHERE c_code =:code) "
				+ "	AND b.c_code=:code order by b.c_level asc";
		return sFactory.getCurrentSession().createSQLQuery(sql).setParameter("code", code).list();
	}

	@Override
	public void saveCascaseAttrChild(CascadeAttr cascadeAttr) throws Exception {
		sFactory.getCurrentSession().save(cascadeAttr);
	}

	@Override
	public void delCascaseAttrChild(String code, String casCode) throws Exception {
		String sql = " DELETE FROM t_sc_bi_cascade_attr WHERE c_code=:code and c_cas_code=:casCode";
		 sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("code", code)
				.setParameter("casCode", casCode).executeUpdate();
	}
	
	@Override
	public List getAppointTypeAttr(String parentCode, ValueType valueType) {
		String sql = "SELECT a.c_code, a.c_cn_name, b.c_data_type FROM t_sc_basic_item a "
				+ " inner join t_sc_bi_onelevel b on a.c_code=b.c_code "
				+ "WHERE a.c_parent=:parentCode AND b.c_data_type=:dataType";
		return sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("parentCode", parentCode)
				.setParameter("dataType", valueType.getIndex()).list();
	}

	@Override
	public BigInteger canAddChildCount(String code) throws Exception {
		String sql = " SELECT (SELECT LENGTH(c_cas_pid)-LENGTH(REPLACE(c_cas_pid, '.', '')) as count  "
				+ " FROM `t_sc_cascadedict_basic_item` "
				+ " WHERE c_cas_pid LIKE (SELECT concat(c_cas_pid, '.', id, '%') FROM t_sc_cascadedict_basic_item "
						+ " WHERE id=(SELECT c_dict_parent_id FROM t_sc_bi_onelevel "
						+ " WHERE c_code=:code)) ORDER BY LENGTH(c_cas_pid) DESC LIMIT 1)-"
						+ " (SELECT count(*) FROM t_sc_bi_cascade_attr where c_code=:code)";
		return (BigInteger) sFactory.getCurrentSession().createSQLQuery(sql).setParameter("code", code).uniqueResult();
	}

	@Override
	public List getCascadeAttrChildPojo(String code, String casCode) {
		String sql =" SELECT c_code, c_cas_code, c_level FROM `t_sc_bi_cascade_attr` WHERE c_code=:code  and c_cas_code !=:casCode order by c_level asc";
		return sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("code", code)
				.setParameter("casCode", casCode).list();
	}

	@Override
	public void updateCasCadeLevel(String code, String casCade, int level) throws Exception {
		String sql = "UPDATE t_sc_bi_cascade_attr set c_level=:level  WHERE c_code=:code AND c_cas_code=:casCade";
		sFactory.getCurrentSession().createSQLQuery(sql)
		.setParameter("code", code)
		.setParameter("casCade", casCade)
		.setParameter("level", level).executeUpdate();
	}

	@Override
	public BasicItem getLableObj(String code) throws Exception {
		
		List<BasicItem> btList = new ArrayList<BasicItem>();
		String sql = "SELECT a.c_cn_name, a.c_en_name,a.c_parent, a.c_using_state, a.c_description, b.*  FROM t_sc_basic_item  a "
				+ "inner join t_sc_bi_onelevel  b "
				+ "on a.c_code=b.c_code "
				+ "WHERE c_parent=:code "
				+ "AND b.c_data_type=:dataType";
		  Query query = sFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("code", code)
				.setParameter("dataType", ValueType.LABLETYPE.getIndex());
		  
		  query.setResultTransformer(new ColumnMapResultTransformer<byte[]>() {
				private static final long serialVersionUID = -392302880551548725L;
				
				@Override
				protected byte[] build(SimpleMapWrapper mapWrapper) {
					BasicItem bt = new BasicItem();
					bt.setCode(mapWrapper.getString("c_code"));
					bt.setCnName(mapWrapper.getString("c_cn_name"));
					bt.setEnName(mapWrapper.getString("c_en_name"));
					bt.setParent(mapWrapper.getString("c_parent"));
					bt.setUsingState(Integer.valueOf(mapWrapper.getString("c_using_state")));
					bt.setDescription(mapWrapper.getString("c_description"));
					
					OneLevelItem ot = new OneLevelItem();
					ot.setCode(mapWrapper.getString("c_code"));
					ot.setDataType(mapWrapper.getString("c_data_type"));
					ot.setDataRange(mapWrapper.getString("c_data_range"));
					ot.setDataTypeCode(mapWrapper.getString("c_data_type_code"));
					ot.setDataForm(mapWrapper.getString("c_data_form"));
					ot.setDictParentId(Integer.valueOf(mapWrapper.getString("c_dict_parent_id")));
					ot.setDictionaryIndex(mapWrapper.getString("c_dictionary_index"));
					ot.setTableName(mapWrapper.getString("c_table_name"));
					ot.setTableNameDescription(mapWrapper.getString("c_table_name_description"));
					ot.setGroupName(mapWrapper.getString("c_group_name"));
					ot.setRefType(mapWrapper.getString("c_ref_type"));
					ot.setNeedHistory(Integer.valueOf(mapWrapper.getString("c_need_history")));
					
					bt.setOneLevelItem(ot);
					btList.add(bt);
					return null;
				}
			});
		  
		  query.uniqueResult();
		  
		  return btList.get(0);
	}

	@Override
	public List getAllEntity() {
		String sql = "SELECT a.c_code, a.c_cn_name FROM t_sc_basic_item a "
				+ "LEFT JOIN t_sc_bi_onelevel b "
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
		.append(" 	(SELECT CONCAT('t_', b.c_parent,'_', a.c_code) tablename, a.c_code code FROM t_sc_bi_onelevel a")
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
		.append(AttributeParter.getLeafEditTimeName(""))
		.append(" datetime ,PRIMARY KEY (`id`))\") ")
		.append(" FROM  ")
		.append(" (SELECT concat('t_',c_code,'_m') tablename, c_code  FROM  t_sc_bi_onelevel    WHERE  c_data_type='10') a  ")
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
		.append(" ( SELECT c_table_name tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '1401' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE	b.table_name IS NULL");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public BasicItem getGroup(String parrentCode) {
		String sql = " SELECT a.* FROM t_sc_basic_item a" + 
				" inner join t_sc_bi_onelevel b on a.c_code=b.c_code" + 
				" WHERE c_parent=:parrentCode AND b.c_data_type='16'";
		return (BasicItem) sFactory.getCurrentSession().createSQLQuery(sql).addEntity(BasicItem.class).setParameter("parrentCode", parrentCode).uniqueResult();
	}

	@Override
	public List queryCreEntityFileTbaF1() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT")
		.append(" concat( \"CREATE TABLE \", a.tablename, \"(\",")
		.append(" \" `\",a.c_code,\"_FP` varchar(32) NOT NULL COMMENT 'code',\",")
		.append(" \"`\",a.c_code,\"_F2` datetime(3) DEFAULT NULL COMMENT 'insert_time',\",")
		.append(" \"`\",a.c_code,\"_F3` mediumblob DEFAULT NULL COMMENT 'content',\",")
		.append(" \"PRIMARY KEY (`\",a.c_code,\"_FP`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC\" ) ")
		.append(" FROM")
		.append(" ( SELECT concat( 't_', c_code, '_f1' ) tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE")
		.append(" b.table_name IS NULL");
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreEntityFileTbaF2() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" concat( \"CREATE TABLE \", a.tablename, ")
		.append(" \"(`id` bigint(20) NOT NULL AUTO_INCREMENT,\",")
		.append(" \"`ABP0001` varchar(32) NOT NULL COMMENT 'record_code',\",")
		.append(" \"`ABP0002` varchar(32) DEFAULT NULL COMMENT 'leaf_Code',\" ,")
		.append(" \"`\",a.c_code,\"_FP` varchar(32) NOT NULL COMMENT 'bytes_code',\",")
		.append(" \"`\",a.c_code,\"_F4` varchar(32) NOT NULL COMMENT 'abcattr',\",")
		.append(" \"`\",a.c_code,\"_F5` datetime(3) NOT NULL COMMENT 'insert_time',\",")
		.append(" \"PRIMARY KEY (`id`, `\", a.c_code,\"_F5`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC\" ) ")
		.append(" FROM")
		.append(" ( SELECT concat( 't_', c_code, '_f2' ) tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE")
		.append(" b.table_name IS NULL");
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreEntityFileTbaF3() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" concat( \"CREATE TABLE \", a.tablename, ")
		.append(" \"(`id` bigint(20) NOT NULL AUTO_INCREMENT,\",")
		.append(" \"`\",a.c_code,\"_HP` varchar(32) NOT NULL COMMENT 'history_code',\",")
		.append(" \"`ABP0001` varchar(32) NOT NULL,\",")
		.append(" \"`ABP0002` varchar(32) DEFAULT NULL,\" ,")
		.append(" \"`\",a.c_code,\"_FP` varchar(32) NOT NULL COMMENT 'bytes_code',\",")
		.append(" \"`\",a.c_code,\"_F4` varchar(32) NOT NULL COMMENT 'abcattr',\",")
		.append(" \"`\",a.c_code,\"_F5` datetime(3) NOT NULL COMMENT 'insert_time',\",")
		.append(" \"PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC\" ) ")
		.append(" FROM")
		.append(" ( SELECT concat( 't_', c_code, '_f3' ) tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE	b.table_name IS NULL");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreEntityTabH1() {
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" concat( \"CREATE TABLE \", a.tablename, \"(\",")
		.append(" \" `\",a.c_code,\"_HP` varchar(32) NOT NULL,\",")
		.append(" \"`ABP0001` varchar(32) DEFAULT NULL,\",")
		.append(" \"`\",a.c_code,\"_H1` datetime(3) DEFAULT NULL,\",")
		.append(" \"`\",a.c_code,\"_H2` varchar(32) DEFAULT NULL,\",")
		.append(" \"`\",a.c_code,\"_H3` varchar(320) DEFAULT NULL,\",")
		.append(" \"`\",a.c_code,\"_H4` int(2) DEFAULT 2 COMMENT '1 total,2 increment',\",")
		.append(" \"`\",a.c_code,\"_H5` int(2) DEFAULT 0,\",")
		.append(" \"`\",a.c_code,\"_H6` blob NOT NULL,\",")
		.append(" \"PRIMARY KEY (`\",a.c_code,\"_HP`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC\" ) ")
		.append(" FROM")
		.append(" ( SELECT concat( 't_', c_code, '_h1' ) tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append("WHERE	b.table_name IS NULL");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreEntityTabH2() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" concat( \"CREATE TABLE \", a.tablename, \"(\",")
		.append(" \" `\",a.c_code,\"_HP` varchar(32) NOT NULL,\",")
		.append(" \"`ABP0001` varchar(32) DEFAULT NULL,\",")
		.append(" \"`\",a.c_code,\"_H1` datetime(3) NOT NULL,\",")
		.append(" \"`\",a.c_code,\"_H2` varchar(32) DEFAULT NULL COMMENT 'usergroup_id',\",")
		.append(" \"`\",a.c_code,\"_H3` varchar(320) DEFAULT NULL  COMMENT 'descripation',\",")
		.append(" \"`\",a.c_code,\"_H4` int(2) DEFAULT 2 COMMENT '1 total,2 increment',\",")
		.append(" \"`\",a.c_code,\"_H5` int(2) DEFAULT 0  COMMENT 'source',\",")
		.append(" \"`\",a.c_code,\"_H6` blob NOT NULL  COMMENT 'content',\",")
		.append(" \"PRIMARY KEY (`\",a.c_code,\"_HP`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC\" ) ")
		.append(" FROM")
		.append(" ( SELECT concat( 't_', c_code, '_h2' ) tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE	b.table_name IS NULL");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreRepeatTabIndex() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" CONCAT(\" ALTER TABLE \",a.tablename,\" ADD INDEX index_p(\",c_code,\"_P);\")")
		.append(" FROM")
		.append(" ( SELECT c_table_name tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '9' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.statistics  t WHERE t.table_schema = '"+DataBaseName+"'	AND INDEX_NAME='index_p') b ")
		.append(" ON a.tablename = b.table_name ")
		.append(" WHERE	b.table_name is NULL");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreEntityTabD1() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" concat( \"CREATE TABLE \", a.tablename, \"(\",")
		.append("	\" `ABP0001` varchar(32) NOT NULL COMMENT 'code',\",")
		.append("	\"`\",a.c_code,\"_DT` datetime(3) DEFAULT NULL COMMENT 'insert_time',\",")
		.append("	\"`\",a.c_code,\"_D1` varchar(32) DEFAULT NULL COMMENT 'userid',\",")
		.append("	\"`\",a.c_code,\"_D2` varchar(255) DEFAULT NULL COMMENT 'reason',\",")
		.append("	 \"PRIMARY KEY (`ABP0001`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC\" ) ")
		.append(" FROM")
		.append(" ( SELECT concat( 't_', c_code, '_d1' ) tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE	b.table_name IS NULL");
		
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreEntityTabc1() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" concat( \"CREATE TABLE \", a.tablename, \"(\",")
		.append(" \"`ABP0001` varchar(32) NOT NULL,\",")
		.append(" \"`\",a.c_code,\"_CT` datetime(3) NOT NULL,\",")
		.append(" \"`\",a.c_code,\"_C1` mediumblob NOT NULL,\",")
		.append(" \"PRIMARY KEY (`ABP0001`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC\" ) ")
		.append(" FROM")
		.append(" ( SELECT concat( 't_', c_code, '_c1' ) tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE	b.table_name IS NULL");
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}

	@Override
	public List queryCreEntityTabc2() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" concat( \"CREATE TABLE \", a.tablename, \"(\",")
		.append(" \"`ABP0001` varchar(32) NOT NULL,\",")
		.append(" \"`\",a.c_code,\"_CT` datetime(3) NOT NULL,\",")
		.append(" \"`\",a.c_code,\"_C1` mediumblob NOT NULL,\",")
		.append(" \"PRIMARY KEY (`ABP0001`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC\" ) ")
		.append(" FROM")
		.append(" ( SELECT concat( 't_', c_code, '_c2' ) tablename, c_code FROM t_sc_bi_onelevel WHERE c_data_type = '10' ) a")
		.append(" LEFT JOIN ( SELECT table_name FROM information_schema.TABLES t WHERE t.table_schema = '"+DataBaseName+"' ) b ON a.tablename = b.table_name ")
		.append(" WHERE	b.table_name IS NULL");
		return sFactory.getCurrentSession().createSQLQuery(sb.toString()).list();
	}
}
