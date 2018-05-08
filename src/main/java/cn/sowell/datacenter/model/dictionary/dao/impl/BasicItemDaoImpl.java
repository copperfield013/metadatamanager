package cn.sowell.datacenter.model.dictionary.dao.impl;

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
import cn.sowell.copframe.dao.utils.QueryUtils;
import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.copframe.utils.FormatUtils;
import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.datacenter.admin.controller.dictionary.Constants;
import cn.sowell.datacenter.model.demo.pojo.PlainDemo;
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
		String sql = "from BasicItem WHERE parent=:parent";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("parent", parent).list();
		return list;
	}

	@Override
	public List getAttrByPidGroupName(String parent, String groupName) {
		String sql = "select new cn.sowell.datacenter.model.dictionary.pojo.BasicItem(code, cnName, dataType, usingState, groupName, parent) from BasicItem WHERE parent=:parent AND groupName=:groupName";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("parent", parent).setParameter("groupName", groupName).list();
		return list;
	}

	@Override
	public void saveOrUpdate(BasicItem obj) {
		
		if (obj.getCode()== null || !obj.getCode().startsWith("T")) {
			//生成code 规则：实体code TE0001 开始  其他code规则 T00001开始
			String dataType = obj.getDataType();
			if ("记录类型".equals(dataType)) {
				obj.setCode(getEntityCode());
			} else {
				obj.setCode(getAttrCode());
			}
			
			sFactory.getCurrentSession().save(obj);
		} else {
			sFactory.getCurrentSession().update(obj);
		}
		
	}
	
	//实体code  生成规则
	private String getEntityCode() {
		String sql = "from BasicItem WHERE dataType=:dataType ORDER BY code DESC";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("dataType", "记录类型").list();

		if (list.isEmpty()) {
			return "TE0001";
		}
		
		String entityCode = list.get(0).getCode();
		String codeStr = entityCode.substring(2);
		int code = Integer.parseInt(codeStr) + 1;
		String entityHead = "TE";
        String format = String.format("%04d", code);  
		
		return entityHead+format;
	}
	
	//其他code， 生成规则
	private String getAttrCode() {
		String sql = "from BasicItem WHERE dataType!=:dataType ORDER BY code DESC";
		List<BasicItem> list = sFactory.getCurrentSession().createQuery(sql).setParameter("dataType", "记录类型").list();

		if (list.isEmpty()) {
			return "T00001";
		}
		
		String entityCode = list.get(0).getCode();
		String codeStr = entityCode.substring(2);
		int code = Integer.parseInt(codeStr) + 1;
		String entityHead = "T";
        String format = String.format("%05d", code);  
		return entityHead+format;
	}
	
	@Override
	public List queryCreTab() {
		getDataBaseName();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ")
			.append("concat(\"create table \", a.c_table_name,\"( `id`  bigint(20) NOT NULL AUTO_INCREMENT, ")
			.append("`ABP0001`  varchar(32) DEFAULT NULL ,PRIMARY KEY (`id`))\") ")
			.append("FROM ")
			.append("(SELECT ")
			.append("c_table_name ")
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
			sb.append("SELECT ")
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
}
