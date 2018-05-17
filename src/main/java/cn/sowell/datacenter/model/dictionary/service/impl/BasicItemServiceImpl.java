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
		
		Iterator<BasicItem> iterator = chilAll.iterator();
		while (iterator.hasNext()) {
			BasicItem bt = iterator.next();
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
		
		//更改枚举值， 二级属性和二级属性的孩子需要设置状态为错误
		if (!"add".equals(flag) ) {//必须是编辑， 并且字典类型不一样， 那么修改他的二级属性状态和孩子的状态
			BasicItem basicItem = basicItemDao.get(BasicItem.class, obj.getCode());
			//必须有二级属性
			TowlevelattrMultiattrMapping oneByRelaMulAttr = tmms.getOneByRelaMulAttr(obj.getGroupName());
			if (oneByRelaMulAttr != null && obj.getCode().equals(oneByRelaMulAttr.getDictionaryAttr())) {
				if ("枚举".equals(basicItem.getDataRange())) {
					if (!"枚举".equals(obj.getDataRange()) || !basicItem.getDictParentId().equals(obj.getDictParentId()) ) {
						//这里我就要修改二级属性和二级属性的孩子状态为错误
						oneByRelaMulAttr.setUsingState(-1);
						tmms.update(oneByRelaMulAttr);
						//二级属性的孩子设置为错误
						List<Towlevelattr> listByMappingId = towlevelattrService.getListByMappingId(String.valueOf(oneByRelaMulAttr.getId()));
						Iterator<Towlevelattr> iterator = listByMappingId.iterator();
						while (iterator.hasNext()) {
							Towlevelattr next = iterator.next();
							next.setUsingState(-1);
							towlevelattrService.update(next);
						}
						
					}
				}
			}
			sFactory.getCurrentSession().evict(basicItem);//session 关联两个相同id的对象， 解除一个
			
		}
		
		//生成code 规则：实体code TE0001 开始  其他code规则 T00001开始
		if ("add".equals(flag)) {
			//生成code 规则：实体code TE0001 开始  其他code规则 T00001开始
			String dataType = obj.getDataType();
			if ("记录类型".equals(dataType)) {
				obj.setCode(basicItemDao.getEntityCode());
			} else {
				obj.setCode(basicItemDao.getAttrCode());
			}
			
			if ("重复类型".equals(obj.getDataType())) {
				obj.setTableName("t_" + obj.getParent() +"_"+ obj.getCode() +"_"+ obj.getCode());
			}
		}
		
		//保存更改
		basicItemDao.saveOrUpdate(obj, flag);
		//如果是重复类型， 默认生成两个孩子， 
		if ("重复类型".equals(obj.getDataType()) && "add".equals(flag)) {
			BasicItem childOne = new BasicItem();//多值属性编辑时间
				childOne.setCode(obj.getCode() + "_ED");
				childOne.setCnName("多值属性编辑时间");
				childOne.setDataType("时间型");
				childOne.setDataRange("date");
				childOne.setTableName(obj.getTableName());
				childOne.setParent(obj.getParent()+ "_" + obj.getCode());
				childOne.setUsingState(0);
				childOne.setGroupName(obj.getCode());
			BasicItem childTwo = new BasicItem();//多值属性唯一编码
				childTwo.setCode(obj.getCode() + "_P");
				childTwo.setCnName("多值属性唯一编码");
				childTwo.setDataType("字符型");
				childTwo.setDataRange("32");
				childTwo.setTableName(obj.getTableName());
				childTwo.setParent(obj.getParent()+ "_" + obj.getCode());
				childTwo.setUsingState(0);
				childTwo.setGroupName(obj.getCode());
			basicItemDao.insert(childOne);
			basicItemDao.insert(childTwo);
		}
		
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
				String code = (String) cur[0];
				BasicItem bt = basicItemDao.get(BasicItem.class, code);
				try {
					basicItemDao.excuteBySql(value);
					
					Session openSession = null;
					Transaction tx = null;
					try {
						 openSession = sFactory.openSession();
						 tx = openSession.beginTransaction();
						if ("重复类型".equals(bt.getDataType())) {
							bt.setUsingState(1);
							openSession.update(bt);	
							BasicItem btParent = basicItemDao.get(BasicItem.class, bt.getParent());
							btParent.setUsingState(1);
							openSession.update(btParent);	
						} else {//  重复类型下面的孩子或者是分组类型下边的孩子
							//找到这个分组或重复类型
							BasicItem btGroup = basicItemDao.get(BasicItem.class, bt.getGroupName());
							btGroup.setUsingState(1);
							openSession.update(btGroup);
							//分组的父亲， 也就是记录类型
							BasicItem btParent = basicItemDao.get(BasicItem.class, btGroup.getParent());
							btParent.setUsingState(1);
							openSession.update(btParent);
						}
						tx.commit();
					} catch (Exception e) {
						tx.rollback();
					} finally {
						openSession.close();
					}
				} catch (Exception e) {
					Session openSession = null;
					Transaction tx = null;
					try {
						openSession = sFactory.openSession();
					 	tx = openSession.beginTransaction();
						if ("重复类型".equals(bt.getDataType())) {
							bt.setUsingState(-1);
							openSession.update(bt);	
							BasicItem btParent = basicItemDao.get(BasicItem.class, bt.getParent());
							btParent.setUsingState(-1);
							openSession.update(btParent);	
						} else {//  重复类型下面的孩子或者是分组类型下边的孩子
							//找到这个分组或重复类型
							BasicItem btGroup = basicItemDao.get(BasicItem.class, bt.getGroupName());
							btGroup.setUsingState(-1);
							openSession.update(btGroup);
							//分组的父亲， 也就是记录类型
							BasicItem btParent = basicItemDao.get(BasicItem.class, btGroup.getParent());
							btParent.setUsingState(-1);
							openSession.update(btParent);
						}
						tx.commit();
					} catch (Exception e1) {
						tx.rollback();
					} finally {
						openSession.close();
					}
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
					Session openSession = null;
					Transaction tx = null;
					try {
						openSession = sFactory.openSession();
						tx = openSession.beginTransaction();
						BasicItem bt = basicItemDao.get(BasicItem.class, code);
						bt.setUsingState(1);
						openSession.update(bt);
						tx.commit();
					} catch (Exception e) {
						tx.rollback();
					} finally {
						openSession.close();
					}
				} catch (Exception e) {
					Session openSession = null;
					Transaction tx = null;
					try {
						openSession = sFactory.openSession();
						tx = openSession.beginTransaction();
						BasicItem bt = basicItemDao.get(BasicItem.class, code);
						bt.setUsingState(-1);
						openSession.update(bt);
						tx.commit();
					} catch (Exception e1) {
						tx.rollback();
					} finally {
						openSession.close();
					}
					
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
				
				Session openSession = null;
				Transaction tx = null;
				try {
					 openSession = sFactory.openSession();
					 tx = openSession.beginTransaction();
					BasicItem bt = basicItemDao.get(BasicItem.class, code);
					bt.setUsingState(1);
					openSession.update(bt);
					tx.commit();
				} catch (Exception e) {
					tx.rollback();
				} finally {
					openSession.close();
				}
			} catch (Exception e) {
				Session openSession = null;
				Transaction tx = null;
				try {
					openSession = sFactory.openSession();
					tx = openSession.beginTransaction();
					BasicItem bt = basicItemDao.get(BasicItem.class, code);
					bt.setUsingState(-1);
					openSession.update(bt);
					tx.commit();
				} catch (Exception e1) {
					tx.rollback();
				} finally {
					openSession.close();
				}
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
