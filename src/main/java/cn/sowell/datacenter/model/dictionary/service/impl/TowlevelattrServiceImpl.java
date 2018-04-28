package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrCriteria;
import cn.sowell.datacenter.model.dictionary.dao.TowlevelattrDao;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrService;

@Service
public class TowlevelattrServiceImpl implements TowlevelattrService {

	@Resource
	TowlevelattrDao towlevelattrDao;
	
	@Override
	public List<Towlevelattr> queryList(TowlevelattrCriteria criteria, PageInfo pageInfo) {
		return towlevelattrDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(Towlevelattr dictParentItem) {
		towlevelattrDao.insert(dictParentItem);
	}

	@Override
	public Towlevelattr getTowlevelattr(Long id) {
		return towlevelattrDao.get(Towlevelattr.class, id);
	}

	@Override
	public void update(Towlevelattr dictParentItem) {
		towlevelattrDao.update(dictParentItem);
	}

	@Override
	public void delete(Long id) {
		Towlevelattr dictParentItem = towlevelattrDao.get(Towlevelattr.class, id);
		towlevelattrDao.delete(dictParentItem);
	}

	@Override
	public List<Towlevelattr> getListByMappingId(String id) {
		return towlevelattrDao.getListByMappingId(id);
	}

}
