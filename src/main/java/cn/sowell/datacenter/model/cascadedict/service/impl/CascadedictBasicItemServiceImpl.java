package cn.sowell.datacenter.model.cascadedict.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.cascadedict.criteria.CascadedictBasicItemCriteria;
import cn.sowell.datacenter.model.cascadedict.dao.CascadedictBasicItemDao;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;

@Service
public class CascadedictBasicItemServiceImpl implements CascadedictBasicItemService {

	@Resource
	CascadedictBasicItemDao dictionaryBasicItemDao;
	
	@Override
	public List<CascadedictBasicItem> queryList(CascadedictBasicItemCriteria criteria, PageInfo pageInfo) throws Exception{
		return dictionaryBasicItemDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(CascadedictBasicItem dictParentItem) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dictParentItem.setUpdateTime(df.format(new Date()));
		dictionaryBasicItemDao.insert(dictParentItem);
	}

	@Override
	public CascadedictBasicItem getOne(Integer id) throws Exception{
		return dictionaryBasicItemDao.get(CascadedictBasicItem.class, id);
	}

	@Override
	public void update(CascadedictBasicItem dictParentItem) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dictParentItem.setUpdateTime(df.format(new Date()));
		dictionaryBasicItemDao.update(dictParentItem);
	}

	@Override
	public void delete(Integer id) throws Exception{
		dictionaryBasicItemDao.delete(id);
	}
	
	@Override
	public void delete(CascadedictBasicItem creteria) throws Exception{
		dictionaryBasicItemDao.delete(creteria);
	}

	@Override
	public List<CascadedictBasicItem> getChildByParentId(Integer parentId) throws Exception{
		return dictionaryBasicItemDao.getChildByParentId(parentId);
	}

	@Override
	public void saveOrUpdate(CascadedictBasicItem basicItem) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		basicItem.setUpdateTime(df.format(new Date()));
		if (basicItem.getId() == null) {
			dictionaryBasicItemDao.insert(basicItem);
		} else {
			dictionaryBasicItemDao.update(basicItem);
		}
		
	}

	@Override
	public List<CascadedictBasicItem> getParentAll() {
		return dictionaryBasicItemDao.getParentAll();
	}

}
