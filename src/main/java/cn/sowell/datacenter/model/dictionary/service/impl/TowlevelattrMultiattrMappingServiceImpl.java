package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrMultiattrMappingCriteria;
import cn.sowell.datacenter.model.dictionary.dao.TowlevelattrMultiattrMappingDao;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrMultiattrMappingService;

@Service
public class TowlevelattrMultiattrMappingServiceImpl implements TowlevelattrMultiattrMappingService {

	@Resource
	TowlevelattrMultiattrMappingDao tmmd;
	
	@Override
	public List<TowlevelattrMultiattrMapping> queryList(TowlevelattrMultiattrMappingCriteria criteria, PageInfo pageInfo) {
		return tmmd.queryList(criteria, pageInfo);
	}

	@Override
	public void create(TowlevelattrMultiattrMapping dictParentItem) {
		tmmd.insert(dictParentItem);
	}

	@Override
	public TowlevelattrMultiattrMapping getTowlevelattrMultiattrMapping(Long id) {
		return tmmd.get(TowlevelattrMultiattrMapping.class, id);
	}

	@Override
	public void update(TowlevelattrMultiattrMapping dictParentItem) {
		tmmd.update(dictParentItem);
	}

	@Override
	public void delete(Long id) {
		TowlevelattrMultiattrMapping dictParentItem = tmmd.get(TowlevelattrMultiattrMapping.class, id);
		tmmd.delete(dictParentItem);
	}

	@Override
	public TowlevelattrMultiattrMapping getOneByRelaMulAttr(String relatedMultiattribute) {
		return tmmd.getOneByRelaMulAttr(relatedMultiattribute);
	}

}
