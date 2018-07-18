package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrCriteria;
import cn.sowell.datacenter.model.dictionary.dao.TowlevelattrDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrService;

@Service
public class TowlevelattrServiceImpl implements TowlevelattrService {

	@Resource
	TowlevelattrDao towlevelattrDao;
	
	@Resource
	BasicItemService basicItemService;
	
	@Override
	public List<Towlevelattr> queryList(TowlevelattrCriteria criteria, PageInfo pageInfo) {
		return towlevelattrDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(Towlevelattr dictParentItem) {
		try {
			towlevelattrDao.insert(dictParentItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Towlevelattr getTowlevelattr(String code) {
		return towlevelattrDao.get(Towlevelattr.class, code);
	}

	@Override
	public void update(Towlevelattr dictParentItem) {
		towlevelattrDao.update(dictParentItem);
	}

	@Override
	public void delete(String code) {
		Towlevelattr dictParentItem = towlevelattrDao.get(Towlevelattr.class, code);
		towlevelattrDao.delete(dictParentItem);
		
		BasicItem basicItem = basicItemService.getBasicItem(code);
		basicItemService.twoDeleteItem(basicItem);
	}

	@Override
	public List getListByMappingId(String id) {
		return towlevelattrDao.getListByMappingId(id);
	}

}
