package cn.sowell.datacenter.model.dataservice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dataservice.criteria.ServiceBizzDataCriteria;
import cn.sowell.datacenter.model.dataservice.dao.ServiceBizzDataDao;
import cn.sowell.datacenter.model.dataservice.pojo.ServiceBizzData;
import cn.sowell.datacenter.model.dataservice.service.ServiceBizzDataService;

@Service
public class ServiceBizzDataServiceImpl implements ServiceBizzDataService {

	@Resource
	ServiceBizzDataDao serviceBizzDataDao;

	@Override
	public List<ServiceBizzData> queryList(ServiceBizzDataCriteria criteria, PageInfo pageInfo) {
		return serviceBizzDataDao.queryList(criteria, pageInfo);
	}

	@Override
	public ServiceBizzData getOne(Integer id) {
		return serviceBizzDataDao.get(ServiceBizzData.class, id);
	}

	@Override
	public void update(ServiceBizzData sd) {
		serviceBizzDataDao.update(sd);
		
	}

	@Override
	public void delete(Integer id) {
		serviceBizzDataDao.delete(id);
	}

	@Override
	public void insert(ServiceBizzData sd) {
		serviceBizzDataDao.insert(sd);
	}
}
