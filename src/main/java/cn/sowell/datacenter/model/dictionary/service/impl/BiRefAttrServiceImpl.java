package cn.sowell.datacenter.model.dictionary.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.sowell.datacenter.model.dictionary.dao.BiRefAttrDao;
import cn.sowell.datacenter.model.dictionary.pojo.BiRefAttr;
import cn.sowell.datacenter.model.dictionary.service.BiRefAttrService;

@Service
public class BiRefAttrServiceImpl implements BiRefAttrService {

	@Resource
	BiRefAttrDao biRefAttrDao;
	
	
	@Override
	public void create(BiRefAttr biRefAttr) {
		biRefAttrDao.insert(biRefAttr);
	}

	@Override
	public BiRefAttr getOne(String code) {
		return biRefAttrDao.getBiRefAttr(code);
	}

	@Override
	public void update(BiRefAttr biRefAttr) {
		biRefAttrDao.update(biRefAttr);
	}

	@Override
	public void delete(Object pojo) {
		biRefAttrDao.delete(pojo);
	}

	@Override
	public void saveOrUpdate(BiRefAttr biRefAttr) {
		Object refAttr = biRefAttrDao.get(biRefAttr.getCode());
		if (refAttr != null) {
			this.update(biRefAttr);
		} else {
			this.create(biRefAttr);
		}
	}

}
