package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.abc.model.enun.ValueType;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.dictionary.Constants;
import cn.sowell.datacenter.model.dictionary.criteria.RecordRelationTypeCriteria;
import cn.sowell.datacenter.model.dictionary.dao.RecordRelationTypeDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicChange;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.service.BasicChangeService;
import cn.sowell.datacenter.model.dictionary.service.BasicItemCodeGeneratorService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;

@Service
public class RecordRelationTypeServiceImpl implements RecordRelationTypeService {

	@Resource
	RecordRelationTypeDao dictionaryParentItemDao;
	
	@Resource
	BasicItemCodeGeneratorService btCodeGenerService;
	
	@Resource
	BasicChangeService basicChangeService;
	
	@Override
	public List<RecordRelationType> queryList(RecordRelationTypeCriteria criteria, PageInfo pageInfo) {
		return dictionaryParentItemDao.queryList(criteria, pageInfo);
	}

	@Override
	public void create(RecordRelationType dictParentItem) {
		dictionaryParentItemDao.insert(dictParentItem);
		
		BasicChange basicChange = new BasicChange(BasicChange.RECORDRELATION);
		basicChangeService.create(basicChange);
	}

	@Override
	public RecordRelationType getRecordRelationType(String id) {
		return dictionaryParentItemDao.get(RecordRelationType.class, id);
	}

	@Override
	public void update(RecordRelationType dictParentItem) {
		dictionaryParentItemDao.update(dictParentItem);
		BasicChange basicChange = new BasicChange(BasicChange.RECORDRELATION);
		basicChangeService.create(basicChange);
	}

	@Override
	public void delete(String id) {
		RecordRelationType criteria = dictionaryParentItemDao.get(RecordRelationType.class, id);
		dictionaryParentItemDao.delete(criteria);
		if (!criteria.getTypeCode().equals(criteria.getReverseCode())) {
			RecordRelationType reverseObj = dictionaryParentItemDao.get(RecordRelationType.class, criteria.getReverseCode());
			dictionaryParentItemDao.delete(reverseObj);
		}
	}

	@Override
	public List<RecordRelationType> getEntityRelaByBitemId(String recordType) {
		return dictionaryParentItemDao.getEntityRelaByBitemId(recordType);
	}

	@Override
	public void saveRelation(RecordRelationType lefRrecordType, RecordRelationType rightRrecordType, String symmetry) throws Exception {
		
			if ("symmetry".equals(symmetry)) {//添加对称关系
				
				
				String recordRelaCode = btCodeGenerService.getRelaCode(lefRrecordType.getLeftRecordType());
				
				lefRrecordType.setTypeCode(recordRelaCode);
				lefRrecordType.setReverseCode(recordRelaCode);
				lefRrecordType.setUsingState(Constants.USING_STATE_MAP.get("using"));
				dictionaryParentItemDao.insert(lefRrecordType);
			} else {//添加不对称关系
				String LeftRecordRelaCode = "";
				String rightRecordRelaCode = "";
				
				if (lefRrecordType.getLeftRecordType().equals(rightRrecordType.getLeftRecordType())) {//实体相同的情况
					LeftRecordRelaCode = btCodeGenerService.getRelaCode(lefRrecordType.getLeftRecordType());
					
					int indexOf = LeftRecordRelaCode.indexOf("R");
					String codeStr = LeftRecordRelaCode.substring(indexOf+1);
					int code = Integer.parseInt(codeStr) + 1;
			        String format = String.format("%03d", code);  
					
			        rightRecordRelaCode = lefRrecordType.getLeftRecordType() + "R" + format;
				} else {//实体不同的情况
					LeftRecordRelaCode = btCodeGenerService.getRelaCode(lefRrecordType.getLeftRecordType());
					rightRecordRelaCode = btCodeGenerService.getRelaCode(rightRrecordType.getLeftRecordType());
					
				}
				
				lefRrecordType.setTypeCode(LeftRecordRelaCode);
				lefRrecordType.setReverseCode(rightRecordRelaCode);
				rightRrecordType.setTypeCode(rightRecordRelaCode);
				rightRrecordType.setReverseCode(LeftRecordRelaCode);
				
				lefRrecordType.setUsingState(Constants.USING_STATE_MAP.get("using"));
				rightRrecordType.setUsingState(Constants.USING_STATE_MAP.get("using"));
				
				dictionaryParentItemDao.insert(lefRrecordType);
				dictionaryParentItemDao.insert(rightRrecordType);
			}
			
			BasicChange basicChange = new BasicChange(BasicChange.RECORDRELATION);
			basicChangeService.create(basicChange);
		
	}

	@Override
	public List<RecordRelationType> getEntityRelaByBitemId(String leftRecordType, String rightRecordType) {
		return dictionaryParentItemDao.getEntityRelaByBitemId(leftRecordType, rightRecordType);
	}

	@Override
	public void saveStatus(String typeCode, boolean isPastDue) throws Exception {
		
		RecordRelationType leftRlea = dictionaryParentItemDao.get(RecordRelationType.class, typeCode);
		RecordRelationType rightRlea = dictionaryParentItemDao.get(RecordRelationType.class, leftRlea.getReverseCode());
		if (isPastDue) {
			//过期实体
			leftRlea.setUsingState(Constants.USING_STATE_MAP.get("pastDue"));
			rightRlea.setUsingState(Constants.USING_STATE_MAP.get("pastDue"));
		} else {
			//解除过期
			leftRlea.setUsingState(Constants.USING_STATE_MAP.get("using"));
			rightRlea.setUsingState(Constants.USING_STATE_MAP.get("using"));
		}
		
		if (leftRlea.equals(rightRlea)) {
			dictionaryParentItemDao.update(leftRlea);
		} else {
			dictionaryParentItemDao.update(leftRlea);
			dictionaryParentItemDao.update(rightRlea);
		}
	}

	@Override
	public List<RecordRelationType> getRelaByType(String leftRecordType, String relationType) {
		return dictionaryParentItemDao.getRelaByType(leftRecordType, relationType);
	}

}
