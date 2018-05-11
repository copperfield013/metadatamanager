package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.DictionaryBasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.DictionaryBasicItemService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrMultiattrMappingService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrService;

@Service
public class BasicItemServiceImpl implements BasicItemService {

	@Resource
	BasicItemDao basicItemDao;
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	@Resource
	TowlevelattrMultiattrMappingService tmms;
	
	@Resource
	DictionaryBasicItemService dictionaryBasicItemService;
	
	@Resource
	TowlevelattrService towlevelattrService;
	
	@Resource
	SessionFactory sFactory;
	
	
	
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
				//bt 是重复类型， 在这里我要判断有没有二级属性
				TowlevelattrMultiattrMapping oneByRelaMulAttr = tmms.getOneByRelaMulAttr(bt.getCode());
				
				if (oneByRelaMulAttr != null) {
					bt.setTwoLevelAttr(oneByRelaMulAttr.getId());
				} else {
					bt.setTwoLevelAttr(null);
				}
				
				moreList.add(bt);
				List<BasicItem> childList = basicItemDao.getDataByPId(bt.getParent() + "_"+ bt.getCode());
				bt.setChildList(childList);
			} else if ("分组类型".equals(bt.getDataType())) {//分组数据
				attrList.add(bt);
				List childList = basicItemDao.getAttrByPidGroupName(bt.getParent(), bt.getCode());
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
	public void delete(BasicItem basicItem) {
		basicItemDao.delete(basicItem);
	}
	
	@Override
	public void savePastDue(BasicItem basicItem, Integer status) {
		//不管是什么类型， 自身状态都要改变
		pastDue(basicItem, status);
		//记录类型
		if ("记录类型".equals(basicItem.getDataType())) {
			List<BasicItem> basicItemList = basicItemDao.getDataByPId(basicItem.getCode());
			for(BasicItem bItem : basicItemList) {
				pastDue(bItem, status);
				
				if ("重复类型".equals(bItem.getDataType())) {//重复类型
					repeatPastDue(bItem, status);
				} 
			}
		} else if ("重复类型".equals(basicItem.getDataType())) {//重复类型
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
	public void saveOrUpdate(BasicItem obj, String flag) {
		basicItemDao.saveOrUpdate(obj, flag);
	}

	@Override
	public List getAttrByPidGroupName(String parent, String groupName) {
		return basicItemDao.getAttrByPidGroupName(parent, groupName);
	}

	@Override
	public void createTabCol() {
		
		//查询需要创建的表
		List queryCreTab = basicItemDao.queryCreTab();
			Iterator iterator = queryCreTab.iterator();
			
			while (iterator.hasNext()) {
				Object[] cur = (Object[]) iterator.next();
				String value = (String) cur[1];
				try {
					basicItemDao.excuteBySql(value);
				} catch (Exception e) {
					Session openSession = sFactory.openSession();
					 Transaction tx = openSession.beginTransaction();
					String code = (String) cur[0];
					BasicItem bt = basicItemDao.get(BasicItem.class, code);
					if ("重复类型".equals(bt.getDataType())) {
						bt.setUsingState(-1);
						openSession.update(bt);				
					} else {//  重复类型下面的孩子或者是分组类型下边的孩子
						//他们的区别在于父亲不同， 
						BasicItem btParent = basicItemDao.get(BasicItem.class, bt.getParent());
						if ("记录类型".equals(btParent.getDataType())) {//bt是分组类型的孩子
							//找到这个分组
							BasicItem btGroup = basicItemDao.get(BasicItem.class, bt.getGroupName());
							btGroup.setUsingState(-1);
							openSession.update(btGroup);
						} else { //bt是重复类型的孩子
							//找到这个重复类型  btParent
							btParent.setUsingState(-1);
							openSession.update(btParent);
						}
					}
						
					tx.commit();
					
					continue;
				}
			}
			
		//要新增的字段 
		List queryNewAddCol = basicItemDao.queryNewAddCol();
			Iterator iteratorA = queryNewAddCol.iterator();
			
			while (iteratorA.hasNext()) {
				Object[] cur = (Object[]) iteratorA.next();
				String value = (String) cur[1];
				String code = (String) cur[0];
				try {
					basicItemDao.excuteBySql(value);
					
					Session openSession = sFactory.openSession();
					Transaction tx = openSession.beginTransaction();
					BasicItem bt = basicItemDao.get(BasicItem.class, code);
					bt.setUsingState(1);
					openSession.update(bt);
					tx.commit();
				} catch (Exception e) {
					Session openSession = sFactory.openSession();
					Transaction tx = openSession.beginTransaction();
					BasicItem basicItem = basicItemDao.get(BasicItem.class, code);
					basicItem.setUsingState(-1);
					openSession.update(basicItem);
					tx.commit();
					continue;
				}
			}
			
		//查询需要更新的字段语句 
		List queryEditCol = basicItemDao.queryEditCol();
		Iterator iteratorE = queryEditCol.iterator();
		
		while (iteratorE.hasNext()) {
			Object[] cur = (Object[]) iteratorE.next();
			String value = (String) cur[1];
			String code = (String) cur[0];
			
			try {
				basicItemDao.excuteBySql(value);
				
				Session openSession = sFactory.openSession();
				Transaction tx = openSession.beginTransaction();
				BasicItem bt = basicItemDao.get(BasicItem.class, code);
				bt.setUsingState(1);
				openSession.update(bt);
				tx.commit();
			} catch (Exception e) {
				Session openSession = sFactory.openSession();
				Transaction tx = openSession.beginTransaction();
				BasicItem basicItem = basicItemDao.get(BasicItem.class, code);
				basicItem.setUsingState(-1);
				openSession.update(basicItem);
				tx.commit();
				
				continue;
			}
		}	
			
		//创建关系表
		List queryCreRelaTab = basicItemDao.queryCreRelaTab();
		for (Object object : queryCreRelaTab) {
			basicItemDao.excuteBySql(object.toString());
		}
		
	}

	@Override
	public List<BasicItem> getDataByPId(String parent) {
		return basicItemDao.getDataByPId(parent);
	}

	@Override
	public List<DictionaryBasicItem> getDictCode(Long id) {
		
		TowlevelattrMultiattrMapping mapping = tmms.getTowlevelattrMultiattrMapping(id);
		BasicItem basicItem = basicItemDao.get(BasicItem.class, mapping.getDictionaryAttr());
		Integer dictParentId = basicItem.getDictParentId();
		
		List<DictionaryBasicItem> dictBItemList = dictionaryBasicItemService.getDictBasicItemByParent(dictParentId);
		
		List<Towlevelattr> twoLevelList = towlevelattrService.getListByMappingId(Long.toString(id));
		
		for (Towlevelattr two : twoLevelList) {
			Iterator<DictionaryBasicItem> iterator = dictBItemList.iterator();
	        while (iterator.hasNext()) {
	        	DictionaryBasicItem dBitem = iterator.next();
	             if (Integer.parseInt(two.getDictionaryCode()) == dBitem.getCode()) {
	            	 dictBItemList.remove(dBitem);
	            	 break;
	             }
	        }
		}
		
		return dictBItemList;
	}

	@Override
	public void createTowLevel(Towlevelattr criteria) {
		towlevelattrService.create(criteria);
	}

}
