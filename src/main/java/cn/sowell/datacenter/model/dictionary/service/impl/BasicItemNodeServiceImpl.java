package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemNodeCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemNodeDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItemNode;
import cn.sowell.datacenter.model.dictionary.service.BasicItemNodeService;

@Service
public class BasicItemNodeServiceImpl implements BasicItemNodeService {

	@Resource
	BasicItemNodeDao basicItemNodeDao;
	
	@Override
	public List<BasicItemNode> queryList(BasicItemNodeCriteria criteria, PageInfo pageInfo) {
		return basicItemNodeDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(BasicItemNode dictParentItem) {
		basicItemNodeDao.insert(dictParentItem);
	}

	@Override
	public BasicItemNode getOne(Integer id) {
		return basicItemNodeDao.get(BasicItemNode.class, id);
	}

	@Override
	public void update(BasicItemNode dictParentItem) {
		basicItemNodeDao.update(dictParentItem);
	}

	@Override
	public void delete(Integer id) {
		BasicItemNode dictParentItem = basicItemNodeDao.get(BasicItemNode.class, id);
		basicItemNodeDao.delete(dictParentItem);
	}

}
