package cn.sowell.datacenter.model.comm.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.sowell.datacenter.model.comm.dao.CommDao;
import cn.sowell.datacenter.model.comm.service.CommService;

@Service
public class CommServiceImpl implements CommService{

	@Resource
	CommDao commDao;
	
	@Override
	public void insert(Object pojo) {
		commDao.insert(pojo);
	}
	
	@Override
	public <T> T get(Class<T> clazz, String id) {
		return commDao.get(clazz, id);
	}
	
	@Override
	public void update(Object pojo) {
		commDao.update(pojo);
	}
	
	@Override
	public void delete(Object pojo) {
		commDao.delete(pojo);
	}


}
