package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.dictionary.Constants;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;

@Service
public class BasicItemServiceImpl implements BasicItemService {

	@Resource
	BasicItemDao basicItemDao;
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	
	@Override
	public List<BasicItem> queryList(BasicItemCriteria criteria) {
		return basicItemDao.queryList(criteria);
	}

	@Override
	public void create(BasicItem basicItem) {
		basicItemDao.insert(basicItem);
	}

	@Override
	public BasicItem getBasicItem(String id) {
		return basicItemDao.get(BasicItem.class, id);
	}
	
	public JSONObject getAttrByPid(String parentId) {
		//多值属性存放
		List moreList = new ArrayList(); 
		//按分组存放普通属性 
		List attrList = new ArrayList(); 
		//根据parentId获取下面所有的孩子   包括普通属性和多值属性
		List<BasicItem> chilAll = basicItemDao.getDataByPId(parentId);
		
		for(BasicItem bt : chilAll) {
			if ("重复类型".equals(bt.getDataType())) {
				moreList.add(bt);
				List<BasicItem> childList = basicItemDao.getDataByPId(bt.getParent() + "_"+ bt.getCode());
				bt.setChildList(childList);
			} else if ("分组类型".equals(bt.getDataType())) {//分组数据
				attrList.add(bt);
				List childList = basicItemDao.getAttrByPidGroupName(bt.getParent(), bt.getCnName());
				bt.setChildList(childList);
			}
		}
		
		//实体关系管理
		List<RecordRelationType> relationList = recordRelationTypeService.getEntityRelaByBitemId(parentId);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("commonProper", attrList);//普通属性
		jsonMap.put("moreProper", moreList);//多值属性
		jsonMap.put("entityRela", relationList);//实体关系
		
		JSONObject obj = new JSONObject(jsonMap);
		return obj;
	}
	
	@Override
	public void update(BasicItem basicItem) {
		basicItemDao.update(basicItem);
	}

	@Override
	public void delete(String id) {
		BasicItem basicItem = basicItemDao.get(BasicItem.class, id);
		basicItemDao.delete(basicItem);
	}
	
	@Override
	public void savePastDue(BasicItem basicItem, Integer status) {
		//不管是什么类型， 自身状态都要改变
		pastDue(basicItem, status);
		//记录类型
		if (Constants.DATA_TYPE_MAP.get("record").equals(basicItem.getDataType())) {
			List<BasicItem> basicItemList = basicItemDao.getDataByPId(basicItem.getCode());
			for(BasicItem bItem : basicItemList) {
				pastDue(bItem, status);
				
				if (Constants.DATA_TYPE_MAP.get("repeat").equals(bItem.getDataType())) {//重复类型
					repeatPastDue(bItem, status);
				} 
			}
		} else if (Constants.DATA_TYPE_MAP.get("repeat").equals(basicItem.getDataType())) {//重复类型
			repeatPastDue(basicItem, status);
		} 
	}

	/**
	 * 执行单个数据的过期
	 * @param basicItem
	 */
	private void pastDue(BasicItem basicItem, Integer status) {
		basicItem.setUsingState(status);
		basicItemDao.update(basicItem);
	}
	
	/**
	 * 重复类型下面的数据过期
	 * @param basicItem
	 */
	private void repeatPastDue(BasicItem basicItem, Integer status) {
		List<BasicItem> basicItemList = basicItemDao.getDataByPId(basicItem.getParent() + "_"+ basicItem.getCode());
		for(BasicItem bItem : basicItemList) {
			pastDue(bItem, status);
		}
	}

	@Override
	public void saveOrUpdate(Object obj) {
		basicItemDao.saveOrUpdate(obj);
	}

	@Override
	public List getAttrByPidGroupName(String parent, String groupName) {
		return basicItemDao.getAttrByPidGroupName(parent, groupName);
	}

	@Override
	public void createTabCol() {
		
		List queryCreTab = basicItemDao.queryCreTab();
		
		for (Object object : queryCreTab) {
			basicItemDao.excuteBySql(object.toString());
		}
		List queryNewAddCol = basicItemDao.queryNewAddCol();
		for (Object object : queryNewAddCol) {
			basicItemDao.excuteBySql(object.toString());
		}
		List queryCreRelaTab = basicItemDao.queryCreRelaTab();
		for (Object object : queryCreRelaTab) {
			basicItemDao.excuteBySql(object.toString());
		}
	}

}
