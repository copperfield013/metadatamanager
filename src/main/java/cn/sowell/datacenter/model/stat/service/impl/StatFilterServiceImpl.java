package cn.sowell.datacenter.model.stat.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.sowell.datacenter.model.stat.dao.StatFilterDao;
import cn.sowell.datacenter.model.stat.dao.StatFilterDao;
import cn.sowell.datacenter.model.stat.pojo.StatFilter;
import cn.sowell.datacenter.model.stat.service.StatFilterService;

@Service
public class StatFilterServiceImpl implements StatFilterService {
	
	@Resource
	StatFilterDao statFilterDao;
	
	@Override
	public List queryList(StatFilter criteria) throws Exception {
		return statFilterDao.queryList(criteria);
	}

	@Override
	public void insert(Object obj) throws Exception {
		statFilterDao.insert(obj);
	}

	@Override
	public <T> T get(Class<T> clazz, Integer id) throws Exception {
		// TODO Auto-generated method stub
		return (T) statFilterDao.get(StatFilter.class, id);
	}

	@Override
	public void update(Object obj) throws Exception {
		// TODO Auto-generated method stub
		statFilterDao.update(obj);
	}

	@Override
	public void delete(Object pojo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) throws Exception {
		statFilterDao.delete(id);
	}

	@Override
	public void saveOrUpdate(StatFilter creteria)	throws Exception {
		
		if (creteria.getId() != null) {
			statFilterDao.update(creteria);
		} else {
			statFilterDao.insert(creteria);
		}
		
	}

	@Override
	public StatFilter getOneByBieCode(String bieCode) throws Exception {
		return statFilterDao.getOneByBieCode(bieCode);
	}

	
	
}
