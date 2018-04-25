package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemAliasCriteria;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryBasicItemAliasDao;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItemAlias;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemAliasService;

@Service
public class DictionaryBasicItemAliasServiceImpl implements DictionaryBasicItemAliasService {

	@Resource
	DictionaryBasicItemAliasDao dictiBasicItemAliasDao;
	
	@Override
	public List<DictionaryBasicItemAlias> queryList(DictionaryBasicItemAliasCriteria criteria, PageInfo pageInfo) {
		return dictiBasicItemAliasDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(DictionaryBasicItemAlias dictParentItem) {
		dictiBasicItemAliasDao.insert(dictParentItem);
	}

	@Override
	public DictionaryBasicItemAlias getDictionaryBasicItemAlias(Long id) {
		return dictiBasicItemAliasDao.get(DictionaryBasicItemAlias.class, id);
	}

	@Override
	public void update(DictionaryBasicItemAlias dictParentItem) {
		dictiBasicItemAliasDao.update(dictParentItem);
	}

	@Override
	public void delete(Long id) {
		DictionaryBasicItemAlias dictParentItem = dictiBasicItemAliasDao.get(DictionaryBasicItemAlias.class, id);
		dictiBasicItemAliasDao.delete(dictParentItem);
	}

}
