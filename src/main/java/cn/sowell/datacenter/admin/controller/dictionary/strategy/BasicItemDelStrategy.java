package cn.sowell.datacenter.admin.controller.dictionary.strategy;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

public interface BasicItemDelStrategy {
	public void delete(BasicItemDao basicItemDao, BiRefAttrService biRefAttrService, BasicItem basicItem);
}
