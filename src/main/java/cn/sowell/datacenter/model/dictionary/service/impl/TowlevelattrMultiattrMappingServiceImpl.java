package cn.sowell.datacenter.model.dictionary.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.criteria.TowlevelattrMultiattrMappingCriteria;
import cn.sowell.datacenter.model.dictionary.dao.TowlevelattrMultiattrMappingDao;
import cn.sowell.datacenter.model.dictionary.pojo.Towlevelattr;
import cn.sowell.datacenter.model.dictionary.pojo.TowlevelattrMultiattrMapping;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrMultiattrMappingService;
import cn.sowell.datacenter.model.dictionary.service.TowlevelattrService;

@Service
public class TowlevelattrMultiattrMappingServiceImpl implements TowlevelattrMultiattrMappingService {

	@Resource
	TowlevelattrMultiattrMappingDao tmmd;
	@Resource
	SessionFactory sFactory;
	@Resource
	TowlevelattrService towlevelattrService;
	
	@Override
	public List<TowlevelattrMultiattrMapping> queryList(TowlevelattrMultiattrMappingCriteria criteria, PageInfo pageInfo) {
		return tmmd.queryList(criteria, pageInfo);
	}

	@Override
	public void create(TowlevelattrMultiattrMapping dictParentItem) {
		tmmd.insert(dictParentItem);
	}

	@Override
	public TowlevelattrMultiattrMapping getTowlevelattrMultiattrMapping(Long id) {
		return tmmd.get(TowlevelattrMultiattrMapping.class, id);
	}

	@Override
	public void update(TowlevelattrMultiattrMapping dictParentItem) {
		tmmd.update(dictParentItem);
	}

	@Override
	public void delete(Long id) {
		TowlevelattrMultiattrMapping dictParentItem = tmmd.get(TowlevelattrMultiattrMapping.class, id);
		tmmd.delete(dictParentItem);
	}

	@Override
	public TowlevelattrMultiattrMapping getOneByRelaMulAttr(String relatedMultiattribute) {
		return tmmd.getOneByRelaMulAttr(relatedMultiattribute);
	}

	@Override
	public void saveOrUpdate(TowlevelattrMultiattrMapping criteria) {
		if (criteria.getId() != null) {
			//二级属性
			TowlevelattrMultiattrMapping oneByRelaMulAttr = tmmd.get(TowlevelattrMultiattrMapping.class, criteria.getId());
			if (!criteria.getDictionaryAttr().equals(oneByRelaMulAttr.getDictionaryAttr()) || !criteria.getValueAttr().equals(oneByRelaMulAttr.getValueAttr())) {
				//这里我就要修改二级属性和二级属性的孩子状态为错误
				criteria.setUsingState(-1);
				//二级属性的孩子设置为错误
				List<Towlevelattr> listByMappingId = towlevelattrService.getListByMappingId(String.valueOf(oneByRelaMulAttr.getId()));
				Iterator<Towlevelattr> iterator = listByMappingId.iterator();
				while (iterator.hasNext()) {
					Towlevelattr next = iterator.next();
					next.setUsingState(-1);
					towlevelattrService.update(next);
				}
			}
			sFactory.getCurrentSession().evict(oneByRelaMulAttr);//session 关联两个相同id的对象， 解除一个
			
			tmmd.update(criteria);
		} else {
			tmmd.insert(criteria);
		}
	}

}
