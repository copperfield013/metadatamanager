package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryParentItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryParentItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryParentItem;
import cn.sowell.datacenter.model.dictionary.service.DictionaryParentItemService;

@Service
public class DictionaryParentItemServiceImpl implements DictionaryParentItemService {

	@Resource
	DictionaryParentItemDao dictionaryParentItemDao;
	
	@Override
	public List<DictionaryParentItem> queryList(DictionaryParentItemCriteria criteria, PageInfo pageInfo) {
		return dictionaryParentItemDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(DictionaryParentItem dictParentItem) {
		dictionaryParentItemDao.insert(dictParentItem);
	}

	@Override
	public DictionaryParentItem getDictionaryParentItem(Integer id) {
		return dictionaryParentItemDao.get(DictionaryParentItem.class, id);
	}

	@Override
	public void update(DictionaryParentItem dictParentItem) {
		dictionaryParentItemDao.update(dictParentItem);
	}

	@Override
	public void delete(Integer id) {
		DictionaryParentItem dictParentItem = dictionaryParentItemDao.get(DictionaryParentItem.class, id);
		dictionaryParentItemDao.delete(dictParentItem);
	}

	@Override
	public List<DictionaryParentItem> allList() {
		return dictionaryParentItemDao.allList();
	}

}
