package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryMappingAliasCriteria;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryMappingAliasDao;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryMappingAlias;
import cn.sowell.datacenter.model.dictionary.service.DictionaryMappingAliasService;

@Service
public class DictionaryMappingAliasServiceImpl implements DictionaryMappingAliasService {

	@Resource
	DictionaryMappingAliasDao dictMappingAlias;
	
	@Override
	public List queryList(DictionaryMappingAliasCriteria criteria, PageInfo pageInfo) {
		return dictMappingAlias.queryList(criteria, pageInfo);
	}

	@Override
	public void create(DictionaryMappingAlias dictParentItem) {
		dictMappingAlias.insert(dictParentItem);
	}

	@Override
	public DictionaryMappingAlias getOne(Integer id) {
		return dictMappingAlias.get(DictionaryMappingAlias.class, id);
	}

	@Override
	public void update(DictionaryMappingAlias dictParentItem) {
		dictMappingAlias.update(dictParentItem);
	}

	@Override
	public void delete(Integer id) {
		DictionaryMappingAlias dictParentItem = dictMappingAlias.get(DictionaryMappingAlias.class, id);
		dictMappingAlias.delete(dictParentItem);
	}

}
