package cn.sowell.datacenter.model.dictionary.service.impl;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import cn.sowell.datacenter.model.dictionary.dao.BasicItemCodeGeneratorDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItemCodeGenerator;
import cn.sowell.datacenter.model.dictionary.service.BasicItemCodeGeneratorService;

@Service
public class BasicItemCodeGeneratorServiceImpl implements BasicItemCodeGeneratorService {
	@Resource
	BasicItemCodeGeneratorDao  btCodeGenDao;

	@Override
	public String getEntityCode() throws Exception {
		BasicItemCodeGenerator btNg = new BasicItemCodeGenerator();
		btCodeGenDao.insert(btNg);
		String basicItemFix = getBasicItemFix();
		return btNg.getEntityCode(basicItemFix);
	}

	@Override
	public String getAttrCode() throws Exception {
		BasicItemCodeGenerator btNg = new BasicItemCodeGenerator();
		btCodeGenDao.insert(btNg);
		String basicItemFix = getBasicItemFix();
		return btNg.getAttrCode(basicItemFix);
	}

	@Override
	public String getRelaCode(String entityCode) throws Exception {
		BasicItemCodeGenerator btNg = new BasicItemCodeGenerator();
		btCodeGenDao.insert(btNg);
		return btNg.getRelaCode(entityCode);
	}

	@Override
	public String getBasicItemFix() throws Exception {
		return btCodeGenDao.getBasicItemFix();
	}
	
}
