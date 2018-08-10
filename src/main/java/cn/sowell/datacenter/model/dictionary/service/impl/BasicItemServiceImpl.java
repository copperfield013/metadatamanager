package cn.sowell.datacenter.model.dictionary.service.impl;

import java.math.BigInteger;
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

import com.abc.util.AttributeParter;
import com.abc.util.ValueType;

import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictBasicItemService;
import cn.sowell.datacenter.model.dictionary.criteria.BasicItemCriteria;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.OneLevelItem;
import cn.sowell.datacenter.model.dictionary.pojo.RecordRelationType;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;
import cn.sowell.datacenter.model.dictionary.service.BasicItemService;
import cn.sowell.datacenter.model.dictionary.service.RecordRelationTypeService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrMultiattrMappingService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrService;
import cn.sowell.datacenter.model.node.pojo.BasicItemNodeGenerator;

@Service
public class BasicItemServiceImpl implements BasicItemService {

	@Resource
	BasicItemDao basicItemDao;
	@Resource
	RecordRelationTypeService recordRelationTypeService;
	@Resource
	TowlevelattrMultiattrMappingService tmms;
	
	@Resource
	TowlevelattrService towlevelattrService;
	@Resource
	CascadedictBasicItemService cascadedictBasicItemService;
	
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
	
	public Map<String, List> getAttrByPid(String parentId) {
		//多值属性存放
		List moreList = new ArrayList(); 
		//按分组存放普通属性 
		List attrList = new ArrayList(); 
		//根据parentId获取下面所有的孩子   包括普通属性和多值属性
		List<BasicItem> chilAll = basicItemDao.getDataByPId(parentId);
		
		Iterator<BasicItem> iterator = chilAll.iterator();
		while (iterator.hasNext()) {
			BasicItem bt = iterator.next();
			
			OneLevelItem oneLevelItem = bt.getOneLevelItem();
			if (oneLevelItem != null) {
				if (String.valueOf(ValueType.REPEAT.getIndex()).equals(oneLevelItem.getDataType())) {
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
				} else if (String.valueOf(ValueType.GROUP.getIndex()).equals(oneLevelItem.getDataType())) {//分组数据
					attrList.add(bt);
					List childList = basicItemDao.getAttrByPidGroupName(bt.getParent(), bt.getCode());
					bt.setChildList(childList);
				}
			}
		}
		
		//实体关系管理
		List<RecordRelationType> relationList = recordRelationTypeService.getEntityRelaByBitemId(parentId);
		
		Map<String, List> jsonMap = new HashMap<String, List>();
		jsonMap.put("commonProper", attrList);//普通属性
		jsonMap.put("moreProper", moreList);//多值属性
		jsonMap.put("entityRela", relationList);//实体关系
		return jsonMap;
	}
	
	@Override
	public void update(BasicItem basicItem) {
		basicItemDao.update(basicItem);
		
	}

	@Override
	public void delete(BasicItem basicItem) {
		//这里删除伴生属性
		if (String.valueOf(ValueType.REPEAT.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
			BasicItem btp = basicItemDao.get(BasicItem.class,  AttributeParter.getRepeatKeyName(basicItem.getCode()));
			BasicItem btEd = basicItemDao.get(BasicItem.class,  AttributeParter.getRepeatEditTimeName(basicItem.getCode()));
			
			if (btp != null) {
				basicItemDao.delete(btp);
			} 
			if (btEd != null) {
				basicItemDao.delete(btEd);
			}
		} else if (String.valueOf(ValueType.BYTES.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {//删除文件型伴生类
            BasicItem btKey =basicItemDao.get(BasicItem.class, AttributeParter.getBytesKeyName(basicItem.getCode()));
            BasicItem btSuffix = basicItemDao.get(BasicItem.class, AttributeParter.getBytesSuffixName(basicItem.getCode()));
            BasicItem btKBSize = basicItemDao.get(BasicItem.class, AttributeParter.getBytesKBSizeName(basicItem.getCode()));
            BasicItem btName = basicItemDao.get(BasicItem.class, AttributeParter.getBytesNameName(basicItem.getCode()));
            if (btKey != null) {
                 basicItemDao.delete(btKey);
            }
            if (btSuffix != null) {
                basicItemDao.delete(btSuffix);                      
            }
            if (btKBSize != null) {
                basicItemDao.delete(btKBSize);
            }
            if (btName != null) {
                basicItemDao.delete(btName);
            }
            
        }
		
		basicItemDao.delete(basicItem);
	}
	
	public void saveUsingStatus(BasicItem basicItem, String statusStr) throws Exception {
		Integer status = 0;
		if ("2".equals(statusStr)) {//变为新增或回复原来的状态
			if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
				List tableList = basicItemDao.queryEntityTable(basicItem.getCode());
				if (tableList.isEmpty()) {
					status = 0;
					savePastDue(basicItem, status);//全部标记为新增
				} else {//如果查到表， 只把当前实体标记为再用并把每个表对应的分组活重复类型标记为再用
					Object[] basicItemFix = basicItemDao.getBasicItemFix();
					String ibt = (String) basicItemFix[2];
					status = 1;
					pastDue(basicItem, status);
					//更改分组或重复类型
					Iterator iterator = tableList.iterator();
					while (iterator.hasNext()) {
						String next = (String)iterator.next();
						String[] str = next.split("_");
						if (str[2].startsWith(ibt.toLowerCase())) {
							BasicItem item = basicItemDao.get(BasicItem.class, str[2]);
							status = 1;
							pastDue(item, status);
						}
					}
					
					//查询当前实体对应的所有表中存在的所有字段
					List colList = basicItemDao.queryEntityCol(basicItem.getCode());
					List<BasicItem> chilList = basicItemDao.getDataByPId(basicItem.getCode());
					
					Iterator<BasicItem> iterator2 = chilList.iterator();
					
					while (iterator2.hasNext()) {
						BasicItem next = iterator2.next();
						OneLevelItem oneLevelItem = next.getOneLevelItem();
						if (oneLevelItem != null) {
							if (String.valueOf(ValueType.REPEAT.getIndex()).equals(next.getOneLevelItem().getDataType())) {
								List<BasicItem> moreChilList = basicItemDao.getChilByPid(next.getParent() + "_" + next.getCode());
								
								Iterator<BasicItem> iterator3 = moreChilList.iterator();
								while (iterator3.hasNext()) {
									BasicItem next2 = iterator3.next();
									if (colList.contains(next2.getCode())) {
										status = 1;
										pastDue(next2, status);
									} else {//不包含为新增
										status = 0;
										pastDue(next2, status);
									}
								}
								
							} else if (!String.valueOf(ValueType.GROUP.getIndex()).equals(next.getOneLevelItem().getDataType())){//不是重复类型也不能是分组类型
								if (colList.contains(next.getCode())) {
									status = 1;
									pastDue(next, status);
								} else {//不包含为新增
									status = 0;
									pastDue(next, status);
								}
							}
						}
						
						
					}
				}
			} else {//普通属性和多值属性下的属性
				//分组或者是重复类型
				BasicItem btGroup = basicItemDao.get(BasicItem.class, basicItem.getOneLevelItem().getGroupName());
				BasicItem entity = basicItemDao.get(BasicItem.class, btGroup.getParent());
				//查询当前实体对应的所有表中存在的所有字段
				List colList = basicItemDao.queryEntityCol(entity.getCode());
				
				if (colList.contains(basicItem.getCode())) {//含有，已生成数据库字段， 再用状态
					status = 1;
					pastDue(basicItem, status);
				} else {//不包含为新增
					status = 0;
					pastDue(basicItem, status);
				}
				
			}
			
			
			
			
		} else {//过期就是全部过期
			status = 2;
			savePastDue(basicItem, status);//全部过期
		}
	}

	/**
	 * 改变状态值为过期or正常， 传过来实体：则实体下面所有的数据项都过期or正常
	 * 传过来重复类型： 则重复类型下面所有的都过期or正常
	 * 传过来普通属性 ： 则普通属性过期or正常
	 */
	private void savePastDue(BasicItem basicItem, Integer status) {
		//不管是什么类型， 自身状态都要改变
		pastDue(basicItem, status);
		//记录类型
		if (String.valueOf(ValueType.RECORD.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {
			List<BasicItem> basicItemList = basicItemDao.getDataByPId(basicItem.getCode());
			for(BasicItem bItem : basicItemList) {
				pastDue(bItem, status);
				OneLevelItem oneLevelItem = bItem.getOneLevelItem();
				if (oneLevelItem != null) {
					if (String.valueOf(ValueType.REPEAT.getIndex()).equals(oneLevelItem.getDataType())) {//重复类型
						repeatPastDue(bItem, status);
					} 
				}
				
			}
		} else if (String.valueOf(ValueType.REPEAT.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {//重复类型
			repeatPastDue(basicItem, status);
		} 
	}

	/**
	 * 执行单个数据的过期
	 * @param basicItem
	 */
	private void pastDue(BasicItem basicItem, Integer status) {
		if (basicItem != null) {
			basicItem.setUsingState(status);
			basicItemDao.update(basicItem);
		}
	}
	
	/**
	 * 重复类型下面的数据过期
	 * @param basicItem
	 */
	private void repeatPastDue(BasicItem basicItem, Integer status) {
		if (basicItem != null) {
			List<BasicItem> basicItemList = basicItemDao.getDataByPId(basicItem.getParent() + "_"+ basicItem.getCode());
			for(BasicItem bItem : basicItemList) {
				pastDue(bItem, status);
			}
		}
		
	}

	@Override
	public void saveOrUpdate(BasicItem obj, String flag, String comm) throws Exception {
		//生成code 规则：实体code IBTE0001 开始  其他code规则 IBT00001开始
		if ("add".equals(flag)) {
			String dataType = obj.getOneLevelItem().getDataType();
			if (String.valueOf(ValueType.RECORD.getIndex()).equals(dataType)) {
				String entityCode = basicItemDao.getEntityCode();
				obj.setCode(entityCode);
				obj.getOneLevelItem().setCode(entityCode);
			} else {
				String attrCode = basicItemDao.getAttrCode();
				obj.setCode(attrCode);
				obj.getOneLevelItem().setCode(attrCode);
			}
		}
		
		if (String.valueOf(ValueType.REPEAT.getIndex()).equals(obj.getOneLevelItem().getDataType())) {
			obj.getOneLevelItem().setTableName("t_" + obj.getParent() +"_"+ obj.getCode() +"_"+ obj.getCode());
		}
		
			//如果是文件类型， 默认生成四个半生记录
			if ("add".equals(flag)) {
				if (String.valueOf(ValueType.BYTES.getIndex()).equals(obj.getOneLevelItem().getDataType())) {
					fileAssociatProper(obj);//生成文件伴生属性
				} 
			} else {//如果是编辑， 
				BasicItem basicItem = basicItemDao.get(BasicItem.class, obj.getCode());
				if (String.valueOf(ValueType.BYTES.getIndex()).equals(basicItem.getOneLevelItem().getDataType())) {//之前是文件型
					BasicItem btKey =basicItemDao.get(BasicItem.class, AttributeParter.getBytesKeyName(obj.getCode()));
					BasicItem btSuffix = basicItemDao.get(BasicItem.class, AttributeParter.getBytesSuffixName(obj.getCode()));
					BasicItem btKBSize = basicItemDao.get(BasicItem.class, AttributeParter.getBytesKBSizeName(obj.getCode()));
				    BasicItem btName = basicItemDao.get(BasicItem.class, AttributeParter.getBytesNameName(obj.getCode()));
					    
					if (!String.valueOf(ValueType.BYTES.getIndex()).equals(obj.getOneLevelItem().getDataType())) {
						if (btKey != null) {
                            basicItemDao.delete(btKey);
                       }
                       if (btSuffix != null) {
                           basicItemDao.delete(btSuffix);                      
                       }
                       if (btKBSize != null) {
                           basicItemDao.delete(btKBSize);
                       }
                       if (btName != null) {
                           basicItemDao.delete(btName);
                       }
					} else {
						btKey.setCnName(AttributeParter.getBytesKeyCNName(obj.getCnName()));
						btKey.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
						btKey.setEnName(obj.getEnName());
						btKey.setParent(obj.getParent());
						btKey.setUsingState(0);
						btKey.getOneLevelItem().setGroupName(obj.getOneLevelItem().getGroupName());
						btKey.getOneLevelItem().setDictParentId(0);
						
						btSuffix.setCnName(AttributeParter.getBytesSuffixCNName(obj.getCnName()));
						btSuffix.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
						btSuffix.setEnName(obj.getEnName());
						btSuffix.setParent(obj.getParent());
						btSuffix.setUsingState(0);
						btSuffix.getOneLevelItem().setGroupName(obj.getOneLevelItem().getGroupName());
						btSuffix.getOneLevelItem().setDictParentId(0);
						
						btKBSize.setCnName(AttributeParter.getBytesKBSizeCNName(obj.getCnName()));
						btKBSize.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
						btKBSize.setEnName(obj.getEnName());
						btKBSize.setParent(obj.getParent());
						btKBSize.setUsingState(0);
						btKBSize.getOneLevelItem().setGroupName(obj.getOneLevelItem().getGroupName());
						btKBSize.getOneLevelItem().setDictParentId(0);
						
						btName.setCnName(AttributeParter.getBytesNameCNName(obj.getCnName()));
						btName.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
						btName.setEnName(obj.getEnName());
						btName.setParent(obj.getParent());
						btName.setUsingState(0);
						btName.getOneLevelItem().setGroupName(obj.getOneLevelItem().getGroupName());
						btName.getOneLevelItem().setDictParentId(0);
						
						basicItemDao.update(btKey);
						basicItemDao.update(btSuffix);
						basicItemDao.update(btKBSize);
						basicItemDao.update(btName);
					}
				} else {//之前不是文件型
					if (String.valueOf(ValueType.BYTES.getIndex()).equals(obj.getOneLevelItem().getDataType())) {//现在是文件型
						fileAssociatProper(obj);//生成文件伴生属性
					}
				}
				
				sFactory.getCurrentSession().evict(basicItem);//session 关联两个相同id的对象， 解除一个	
			}
		
		
		//保存更改
		basicItemDao.saveOrUpdate(obj, flag);
		//basicItemDao.saveOrUpdate(oneLevelItem, flag);
		
		//如果是重复类型， 默认生成两个孩子， 
		if (String.valueOf(ValueType.REPEAT.getIndex()).equals(obj.getOneLevelItem().getDataType()) && "add".equals(flag)) {
			BasicItem childOne = new BasicItem();//多值属性编辑时间
				childOne.setCode(AttributeParter.getRepeatEditTimeName(obj.getCode()));
				   childOne.getOneLevelItem().setCode(AttributeParter.getRepeatEditTimeName(obj.getCode()));
				childOne.setCnName(AttributeParter.getRepeatEditTimeCNName(""));
				childOne.getOneLevelItem().setDataType(String.valueOf(ValueType.DATETIME.getIndex()));
				childOne.getOneLevelItem().setDataRange(ValueType.DATETIME.getName());
				childOne.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
				childOne.setParent(obj.getParent()+ "_" + obj.getCode());
				childOne.setUsingState(0);
				childOne.getOneLevelItem().setDictParentId(0);
				childOne.getOneLevelItem().setGroupName(obj.getCode());
			BasicItem childTwo = new BasicItem();//多值属性唯一编码
				childTwo.setCode(AttributeParter.getRepeatKeyName(obj.getCode()));
				     childTwo.getOneLevelItem().setCode(AttributeParter.getRepeatKeyName(obj.getCode()));
				childTwo.setCnName(AttributeParter.getRepeatKeyCNName(""));
				childTwo.getOneLevelItem().setDataType(String.valueOf(ValueType.STRING.getIndex()));
				childTwo.getOneLevelItem().setDataRange("32");
				childTwo.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
				childTwo.setParent(obj.getParent()+ "_" + obj.getCode());
				childTwo.setUsingState(0);
				childTwo.getOneLevelItem().setGroupName(obj.getCode());
				childTwo.getOneLevelItem().setDictParentId(0);
			basicItemDao.insert(childOne);
			basicItemDao.insert(childTwo);
		}
		
		
	}

	/**
	 * 生成文件的伴生属性
	 * @param obj
	 */
	public void fileAssociatProper(BasicItem obj) {
		BasicItem btKey = new BasicItem();
		btKey.setCode(AttributeParter.getBytesKeyName(obj.getCode()));
		btKey.getOneLevelItem().setCode(AttributeParter.getBytesKeyName(obj.getCode()));
		btKey.setCnName(AttributeParter.getBytesKeyCNName(obj.getCnName()));
		btKey.setEnName(obj.getEnName());
		btKey.getOneLevelItem().setDataType(String.valueOf(ValueType.STRING.getIndex()));
		btKey.getOneLevelItem().setDataRange("32");
		btKey.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
		btKey.setParent(obj.getParent());
		btKey.setUsingState(0);
		btKey.getOneLevelItem().setGroupName(obj.getOneLevelItem().getGroupName());
		btKey.getOneLevelItem().setDictParentId(0);
   
		BasicItem btSuffix = new BasicItem();
		btSuffix.setCode(AttributeParter.getBytesSuffixName(obj.getCode()));
		btSuffix.getOneLevelItem().setCode(AttributeParter.getBytesSuffixName(obj.getCode()));
		btSuffix.setCnName(AttributeParter.getBytesSuffixCNName(obj.getCnName()));
		btSuffix.setEnName(obj.getEnName());
		btSuffix.getOneLevelItem().setDataType(String.valueOf(ValueType.STRING.getIndex()));
		btSuffix.getOneLevelItem().setDataRange("32");
		btSuffix.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
		btSuffix.setParent(obj.getParent());
		btSuffix.setUsingState(0);
		btSuffix.getOneLevelItem().setGroupName(obj.getOneLevelItem().getGroupName());
		btSuffix.getOneLevelItem().setDictParentId(0);
   
		BasicItem btKBSize = new BasicItem();
		btKBSize.setCode(AttributeParter.getBytesKBSizeName(obj.getCode()));
		btKBSize.getOneLevelItem().setCode(AttributeParter.getBytesKBSizeName(obj.getCode()));
		btKBSize.setCnName(AttributeParter.getBytesKBSizeCNName(obj.getCnName()));
		btKBSize.setEnName(obj.getEnName());
		btKBSize.getOneLevelItem().setDataType(String.valueOf(ValueType.NUMBER.getIndex()));
		btKBSize.getOneLevelItem().setDataRange("10,2");
		btKBSize.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
		btKBSize.setParent(obj.getParent());
		btKBSize.setUsingState(0);
		btKBSize.getOneLevelItem().setGroupName(obj.getOneLevelItem().getGroupName());
		btKBSize.getOneLevelItem().setDictParentId(0);
		
		BasicItem btName = new BasicItem();
		btName.setCode(AttributeParter.getBytesNameName(obj.getCode()));
		btName.getOneLevelItem().setCode(AttributeParter.getBytesNameName(obj.getCode()));
		btName.setCnName(AttributeParter.getBytesNameCNName(obj.getCnName()));
		btName.setEnName(obj.getEnName());
		btName.getOneLevelItem().setDataType(String.valueOf(ValueType.STRING.getIndex()));
		btName.getOneLevelItem().setDataRange("128");
		btName.getOneLevelItem().setTableName(obj.getOneLevelItem().getTableName());
		btName.setParent(obj.getParent());
		btName.setUsingState(0);
		btName.getOneLevelItem().setGroupName(obj.getOneLevelItem().getGroupName());
		btName.getOneLevelItem().setDictParentId(0);
		
		basicItemDao.insert(btKey);
		basicItemDao.insert(btSuffix);
		basicItemDao.insert(btKBSize);
		basicItemDao.insert(btName);
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
						if (String.valueOf(ValueType.REPEAT.getIndex()).equals(bt.getOneLevelItem().getDataType())) {
							bt.setUsingState(1);
							openSession.update(bt);	
							BasicItem btParent = basicItemDao.get(BasicItem.class, bt.getParent());
							btParent.setUsingState(1);
							openSession.update(btParent);	
						} else {//  重复类型下面的孩子或者是分组类型下边的孩子
							//找到这个分组或重复类型
							BasicItem btGroup = basicItemDao.get(BasicItem.class, bt.getOneLevelItem().getGroupName());
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
						if (String.valueOf(ValueType.REPEAT.getIndex()).equals(bt.getOneLevelItem().getDataType())) {
							bt.setUsingState(-1);
							openSession.update(bt);	
							BasicItem btParent = basicItemDao.get(BasicItem.class, bt.getParent());
							btParent.setUsingState(-1);
							openSession.update(btParent);	
						} else {//  重复类型下面的孩子或者是分组类型下边的孩子
							//找到这个分组或重复类型
							BasicItem btGroup = basicItemDao.get(BasicItem.class, bt.getOneLevelItem().getGroupName());
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
			
		//创建关系表函数及关系表
		List queryCreRelaTabFun = basicItemDao.queryCreRelaTabFun();
		for (Object object : queryCreRelaTabFun) {
			try {
				basicItemDao.excuteBySql(object.toString());
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		//创建关系表
		List queryCreRelaTab = basicItemDao.queryCreRelaTab();
		for (Object object : queryCreRelaTab) {
			try {
				basicItemDao.excuteBySql(object.toString());
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		//创建索引表
		 List queryCreateIndexTbl = basicItemDao.queryCreateIndexTbl();
		for (Object object : queryCreateIndexTbl) {
			try {
				basicItemDao.excuteBySql(object.toString());
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		//以上程序执行完比， 应确保只有状态为1  和-1， 下面程序把所有状态为0的改为1
		basicItemDao.excuteBySql("UPDATE t_sc_basic_item SET c_using_state=1 WHERE c_using_state=0");
		}

	@Override
	public List<BasicItem> getDataByPId(String parent) {
		return basicItemDao.getDataByPId(parent);
	}

	@Override
	public List<CascadedictBasicItem> getDictCode(Long id) throws Exception {
		
		TowlevelattrMultiattrMapping mapping = tmms.getOne(id);
		BasicItem basicItem = basicItemDao.get(BasicItem.class, mapping.getDictionaryAttr());
		Integer dictParentId = basicItem.getOneLevelItem().getDictParentId();
		
		List<CascadedictBasicItem> dictBItemList = cascadedictBasicItemService.getChildByParentId(dictParentId);
		
		List<Object[]> twoLevelList = towlevelattrService.getListByMappingId(Long.toString(id));
		
		for (Object[] two : twoLevelList) {
			Iterator<CascadedictBasicItem> iterator = dictBItemList.iterator();
	        while (iterator.hasNext()) {
	        	CascadedictBasicItem dBitem = iterator.next();
	             if (Integer.parseInt((String) two[3]) == dBitem.getId()) {
	            	 dictBItemList.remove(dBitem);
	            	 break;
	             }
	        }
		}
		
		return dictBItemList;
	}

	@Override
	public void createTowLevel(Towlevelattr criteria, String name) {
		
		BasicItem bt = new BasicItem();
		
		bt.setUsingState(1);
		bt.setCnName(name);
		
		String attrCode = basicItemDao.getAttrCode();
		criteria.setCode(attrCode);
		bt.setCode(attrCode);
		
		TowlevelattrMultiattrMapping towlevle = tmms.getOne(Long.parseLong(criteria.getMappingId()));
		String related= towlevle.getRelatedMultiattribute();
		BasicItem basicItem = basicItemDao.get(BasicItem.class, related);
		bt.setParent(basicItem.getParent());
		bt.setOneLevelItem(null);
		basicItemDao.insert(bt);
		towlevelattrService.create(criteria);
	}

	@Override
	public BigInteger getTwoSameCount(String name, String entityId) {
		return basicItemDao.getTwoSameCount(name, entityId);
	}

	@Override
	public List getComm(String entityId) {
		return basicItemDao.getComm(entityId);
	}

	@Override
	public List<BasicItem> getEntityList(String leftRecordType) {
		return basicItemDao.getEntityList(leftRecordType);
	}

	@Override
	public void twoDeleteItem(BasicItem basicItem) {
		basicItemDao.delete(basicItem);
	}

	@Override
	public Object[] getBasicItemFix() throws Exception {
		return basicItemDao.getBasicItemFix();
	}

}
