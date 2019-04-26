package cn.sowell.datacenter.model.stat.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.abc.exface.dto.AggregateExpressionDTO;
import com.abc.model.AggregateExpression;
import com.abc.model.IAggregateExpressionDAO;

import cn.sowell.datacenter.model.stat.dao.StatExpressionDao;
import cn.sowell.datacenter.model.stat.pojo.StatExpression;
import cn.sowell.datacenter.model.stat.service.StatExpressionService;

@Service
public class StatExpressionServiceImpl implements StatExpressionService {
	
	@Resource
	StatExpressionDao statExpressionDao;
	
	@Override
	public List queryList(StatExpression criteria) throws Exception {
		return statExpressionDao.queryList(criteria);
	}

	@Override
	public void insert(Object obj) throws Exception {
		statExpressionDao.insert(obj);
	}

	@Override
	public <T> T get(Class<T> clazz, Integer id) throws Exception {
		// TODO Auto-generated method stub
		return (T) statExpressionDao.get(StatExpression.class, id);
	}

	@Override
	public void update(Object obj) throws Exception {
		// TODO Auto-generated method stub
		statExpressionDao.update(obj);
	}

	@Override
	public void delete(Object pojo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) throws Exception {
		statExpressionDao.delete(id);
	}

	@Override
	public void saveOrUpdate(StatExpression creteria)	throws Exception {
		
		if (creteria.getId() != null) {
			statExpressionDao.update(creteria);
		} else {
			statExpressionDao.insert(creteria);
		}
		
	}

	@Override
	public String getExpressionStr(Integer expressionId) {

		AggregateExpression aggregateExpression=new AggregateExpression(expressionId,new IAggregateExpressionDAO() {

			@Override
			public AggregateExpressionDTO query(Integer id) {
				AggregateExpressionDTO a= null;
				try {
					StatExpression statExpression = statExpressionDao.get(StatExpression.class, id);
					a= new AggregateExpressionDTO();
					a.setId(id);
					a.setType(statExpression.getType());
					a.setFunction_type(statExpression.getFunctionType());
					a.setParameter0(statExpression.getParameter0());
					a.setParameter1(statExpression.getParameter1());
					a.setParameter2(statExpression.getParameter2());
					a.setParameter3(statExpression.getParameter3());
					a.setC_order(statExpression.getOrder());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return a;
			}
			
		});
		String expression = aggregateExpression.getExpression();
		return expression;
	}

	
	
}
