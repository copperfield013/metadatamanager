package cn.sowell.datacenter.model.stat.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.sowell.datacenter.admin.controller.dictionary.BasicItemContext;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.stat.dao.StatDfDao;
import cn.sowell.datacenter.model.stat.pojo.StatDf;
import cn.sowell.datacenter.model.stat.service.StatDfService;

@Service
public class StatDfServiceImpl implements StatDfService {
	
	@Resource
	StatDfDao statDfDao;
	
	@Resource
	BasicItemService basicItemService;

	@Override
	public List queryList(StatDf criteria) throws Exception {
		return statDfDao.queryList(criteria);
	}

	@Override
	public void insert(Object obj) throws Exception {
		statDfDao.insert(obj);
	}

	@Override
	public <T> T get(Class<T> clazz, Integer id) throws Exception {
		return (T) statDfDao.get(StatDf.class, id);
	}

	@Override
	public void update(Object obj) throws Exception {
		// TODO Auto-generated method stub
		statDfDao.update(obj);
	}

	@Override
	public void delete(Object pojo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) throws Exception {
		StatDf statDf = statDfDao.get(StatDf.class, id);
		BasicItem basicItem = basicItemService.getBasicItem(statDf.getBiCode());
		basicItemService.delete(basicItem);
		statDfDao.delete(id);
	}

	@Override
	public List getStatDfList(String bieCode) {
		return statDfDao.getStatDfList(bieCode);
	}

	@Override
	public void saveOrUpdate(BasicItem basicItem, OneLevelItem oneLevelItem, Integer cascadedict, StatDf creteria, BiRefAttr biRefAttr)
			throws Exception {
		BasicItem group = basicItemService.getGroup(basicItem.getParent());
		oneLevelItem.setGroupName(group.getCode());
		BasicItem saveBasicItem = new BasicItemContext().saveBasicItem(basicItemService, basicItem, oneLevelItem, cascadedict, biRefAttr);
		
		creteria.setBiCode(saveBasicItem.getCode());
		if (creteria.getId() == null) {
			statDfDao.insert(creteria);
		} else {
			statDfDao.update(creteria);
		}
	}
	
}
