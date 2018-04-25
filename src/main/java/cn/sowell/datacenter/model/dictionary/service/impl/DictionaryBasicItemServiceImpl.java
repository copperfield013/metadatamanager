package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.DictionaryBasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.DictionaryBasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemService;

@Service
public class DictionaryBasicItemServiceImpl implements DictionaryBasicItemService {

	@Resource
	DictionaryBasicItemDao dictionaryBasicItemDao;
	
	@Override
	public List<DictionaryBasicItem> queryList(DictionaryBasicItemCriteria criteria, PageInfo pageInfo) {
		return dictionaryBasicItemDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(DictionaryBasicItem dictParentItem) {
		Integer code = dictionaryBasicItemDao.getCode(dictParentItem.getParentId());
		dictParentItem.setCode(code);
		
		dictionaryBasicItemDao.insert(dictParentItem);
	}

	@Override
	public DictionaryBasicItem getDictionaryBasicItem(Integer id) {
		return dictionaryBasicItemDao.get(DictionaryBasicItem.class, id);
	}

	@Override
	public void update(DictionaryBasicItem dictParentItem) {
		dictionaryBasicItemDao.update(dictParentItem);
	}

	@Override
	public void delete(Integer id) {
		DictionaryBasicItem dictParentItem = dictionaryBasicItemDao.get(DictionaryBasicItem.class, id);
		dictionaryBasicItemDao.delete(dictParentItem);
	}

	@Override
	public List<DictionaryBasicItem> getDictBasicItemByParent(Integer parentId) {
		return dictionaryBasicItemDao.getDictBasicItemByParent(parentId);
	}

}
