package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.abc.model.enun.ValueType;
import cn.sowell.datacenter.model.dictionary.dao.BasicItemCodeGeneratorDao;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItemCodeGenerator;
import cn.sowell.datacenter.model.dictionary.service.BasicItemCodeGeneratorService;

@Service
public class BasicItemCodeGeneratorServiceImpl implements BasicItemCodeGeneratorService {
	@Resource
	BasicItemCodeGeneratorDao  btCodeGenDao;
	
	private static Map<String, String> map = new HashMap<String, String>();

	@Override
	public String getRelaCode(String entityCode) throws Exception {
		BasicItemCodeGenerator btNg = new BasicItemCodeGenerator();
		btCodeGenDao.insert(btNg);
		return btNg.getRelaCode(entityCode);
	}

	@Override
	public String getBasicItemFix(ValueType dataType, String entityCode) throws Exception {
		//获取前缀信息
		String basicItemFix = null;
		if (ValueType.RECORD.equals(dataType) && (entityCode == null || entityCode == "")) {
			 basicItemFix = btCodeGenDao.getBasicItemFixByDB();
		} else {
			basicItemFix = map.get(entityCode);
			if (basicItemFix == null || basicItemFix == "") {
				int indexOf = entityCode.lastIndexOf(BasicItemCodeGenerator.ENTITYINFIX);
				basicItemFix = entityCode.substring(0, indexOf);
				map.put(entityCode, basicItemFix);
			}
		} 
		return basicItemFix;
	}

	@Override
	public String getBasicItemCode(ValueType dataType, String entityCode) throws Exception {
		String basicItemCode = null;
		BasicItemCodeGenerator btNg = new BasicItemCodeGenerator();
		btCodeGenDao.insert(btNg);

		String basicItemFix = getBasicItemFix(dataType, entityCode);
		
		switch (dataType) {
		case RECORD:
			basicItemCode =btNg.getEntityCode(basicItemFix);
			map.put(basicItemCode, basicItemFix);
			break;
		default:
			basicItemCode = btNg.getAttrCode(basicItemFix);
			break;
		}
		return basicItemCode;
	}

	
	
}
