package cn.sowell.datacenter.model.stat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.dictionary.BasicItemContext;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.stat.dao.StatEDao;
import cn.sowell.datacenter.model.stat.pojo.StatE;
import cn.sowell.datacenter.model.stat.service.StatEService;

@Service
public class StatEServiceImpl implements StatEService {
	
	@Resource
	StatEDao statEDao;
	
	@Resource
	BasicItemService basicItemService;

	@Override
	public List queryList(StatE criteria) throws Exception {
		return statEDao.queryList(criteria);
	}

	@Override
	public void insert(BasicItem basicItem, OneLevelItem oneLevelItem, Integer cascadedict,StatE obj) throws Exception {
		BasicItem saveBasicItem = new BasicItemContext().saveBasicItem(basicItemService, basicItem, oneLevelItem, cascadedict);
		
		//默认生成一个分组
		BasicItem group = new BasicItem();
		group.setCnName("基本信息");
		group.setParent(saveBasicItem.getCode());
		group.setUsingState(1);
		group.getOneLevelItem().setDataType("16");
		group.getOneLevelItem().setDictParentId(0);
		group.getOneLevelItem().setNeedHistory(1);
		
		 new BasicItemContext().saveBasicItem(basicItemService, group, group.getOneLevelItem(), null);
		
		obj.setBieCode(saveBasicItem.getCode());
		statEDao.insert(obj);
	}

	@Override
	public <T> T get(Class<T> clazz, Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object obj) throws Exception {
		// TODO Auto-generated method stub
		statEDao.update(obj);
	}

	@Override
	public void delete(String id, String entityId) throws Exception {
		BasicItem basicItem = basicItemService.getBasicItem(entityId);
		basicItemService.delete(basicItem);
		statEDao.delete(id);
	}
	
	
}
