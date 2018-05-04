package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryMappingCriteria;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryMappingDao;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMapping;
import cn.sowell.datacenter.model.dictionary.service.DictionaryMappingService;

@Service
public class DictionaryMappingServiceImpl implements DictionaryMappingService {

	@Resource
	DictionaryMappingDao dictiBasicItemAliasDao;
	
	@Override
	public List<DictionaryMapping> queryList(DictionaryMappingCriteria criteria, PageInfo pageInfo) {
		return dictiBasicItemAliasDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(DictionaryMapping dictParentItem) {
		dictiBasicItemAliasDao.insert(dictParentItem);
	}

	@Override
	public DictionaryMapping getOne(Integer id) {
		return dictiBasicItemAliasDao.get(DictionaryMapping.class, id);
	}

	@Override
	public void update(DictionaryMapping dictParentItem) {
		dictiBasicItemAliasDao.update(dictParentItem);
	}

	@Override
	public void delete(Integer id) {
		DictionaryMapping dictParentItem = dictiBasicItemAliasDao.get(DictionaryMapping.class, id);
		dictiBasicItemAliasDao.delete(dictParentItem);
	}

	@Override
	public List<DictionaryMapping> queryListAll() {
		return dictiBasicItemAliasDao.queryListAll();
	}

}
