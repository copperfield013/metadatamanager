package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

/**
 *    ValueType.REPEAT 和 ValueType.ENUMTYPE_MULTI
 *    	重复类型删除策略和枚举类型多选
 * @author so-well
 *
 */
public class ReferenceDelStrategy implements BasicItemDelStrategy {
	
	@Override
	public void delete(BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem) {
		BiRefAttr biRefAttr = new BiRefAttr();
		biRefAttr.setCode(basicItem.getCode());
		biRefAttrService.delete(biRefAttr);
	}
}
