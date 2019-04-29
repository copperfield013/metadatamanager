package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.datacenter.model.dictionary.dao.AggregateAttrDao;
import cn.sowell.datacenter.model.dictionary.pojo.AggregateAttr;
import cn.sowell.datacenter.model.dictionary.service.AggregateAttrService;

@Service
public class AggregateAttrServiceImpl implements AggregateAttrService {

	@Resource
	AggregateAttrDao aggregateAttrDao;

	@Override
	public List queryList() {
		return aggregateAttrDao.queryList();
	}

	@Override
	public void create(AggregateAttr aggregateAttr) {
		aggregateAttrDao.insert(aggregateAttr);
	}

	@Override
	public void update(AggregateAttr aggregateAttr) {
		aggregateAttrDao.update(aggregateAttr);
	}

	@Override
	public void delete(String code) {
		AggregateAttr aggregateAttr = aggregateAttrDao.get(AggregateAttr.class, code);
		if (aggregateAttr != null) {
			aggregateAttrDao.delete(aggregateAttr);
		}
	}

	@Override
	public AggregateAttr getOne(String code) {
		return aggregateAttrDao.get(AggregateAttr.class, code);
	}

	@Override
	public void saveOrUpdate(AggregateAttr aggregateAttr) {
		Object aggregateAttr2 = aggregateAttrDao.getAggregateAttr(aggregateAttr.getCode());
		if (aggregateAttr2 == null) {
			this.create(aggregateAttr);
		} else {
			this.update(aggregateAttr);
		}
	}
	


}
