package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.RecordRelationTypeCriteria;
import cn.sowell.datacenter.model.dictionary.dao.RecordRelationTypeDao;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;

@Service
public class RecordRelationTypeServiceImpl implements RecordRelationTypeService {

	@Resource
	RecordRelationTypeDao dictionaryParentItemDao;
	
	@Override
	public List<RecordRelationType> queryList(RecordRelationTypeCriteria criteria, PageInfo pageInfo) {
		return dictionaryParentItemDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(RecordRelationType dictParentItem) {
		dictionaryParentItemDao.insert(dictParentItem);
	}

	@Override
	public RecordRelationType getRecordRelationType(Integer id) {
		return dictionaryParentItemDao.get(RecordRelationType.class, id);
	}

	@Override
	public void update(RecordRelationType dictParentItem) {
		dictionaryParentItemDao.update(dictParentItem);
	}

	@Override
	public void delete(Integer id) {
		RecordRelationType dictParentItem = dictionaryParentItemDao.get(RecordRelationType.class, id);
		dictionaryParentItemDao.delete(dictParentItem);
	}

	@Override
	public List<RecordRelationType> getEntityRelaByBitemId(String recordType) {
		return dictionaryParentItemDao.getEntityRelaByBitemId(recordType);
	}

	@Override
	public void saveRelation(RecordRelationType lefRrecordType, RecordRelationType rightRrecordType) {
		//左右关系相同则生成一条记录
		if (lefRrecordType.getName().trim().equals(rightRrecordType.getName().trim())) {
			lefRrecordType.setReverseCode(lefRrecordType.getTypeCode());
			dictionaryParentItemDao.insert(lefRrecordType);
		} else {
			dictionaryParentItemDao.insert(lefRrecordType);
			dictionaryParentItemDao.insert(rightRrecordType);
		}
		
	}

}
