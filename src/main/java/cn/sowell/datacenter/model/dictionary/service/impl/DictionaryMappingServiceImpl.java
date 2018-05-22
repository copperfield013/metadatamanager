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
	DictionaryMappingDao dictMappingDao;
	
	@Override
	public List<DictionaryMapping> queryList(DictionaryMappingCriteria criteria, PageInfo pageInfo) {
		return dictMappingDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(DictionaryMapping dictParentItem) {
		dictMappingDao.insert(dictParentItem);
	}

	@Override
	public DictionaryMapping getOne(Integer id) {
		return dictMappingDao.get(DictionaryMapping.class, id);
	}

	@Override
	public void update(DictionaryMapping dictParentItem) {
		dictMappingDao.update(dictParentItem);
	}

	@Override
	public void delete(Integer id) {
		DictionaryMapping dictionaryMapping = dictMappingDao.get(DictionaryMapping.class, id);
		dictMappingDao.delete(dictionaryMapping);
	}

	@Override
	public List<DictionaryMapping> queryListAll() {
		return dictMappingDao.queryListAll();
	}

}
