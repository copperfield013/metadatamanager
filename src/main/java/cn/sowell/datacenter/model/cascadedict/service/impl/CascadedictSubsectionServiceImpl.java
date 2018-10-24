package cn.sowell.datacenter.model.cascadedict.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.datacenter.model.cascadedict.dao.CascadedictSubsectionDao;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictBasicItem;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictCodeGenerator;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsection;
import cn.sowell.datacenter.model.cascadedict.pojo.CascadedictSubsectionChild;
import cn.sowell.datacenter.model.cascadedict.service.CascadedictSubsectionService;
import cn.sowell.datacenter.model.node.pojo.BasicItemNodeGenerator;

@Service
public class CascadedictSubsectionServiceImpl implements CascadedictSubsectionService {

	@Resource
	CascadedictSubsectionDao cascadedictSubsection;
	
	@Override
	public void create(CascadedictSubsection subsection) throws Exception {
		cascadedictSubsection.insert(subsection);
	}

	@Override
	public CascadedictSubsection getOne(Integer id) throws Exception {
		return cascadedictSubsection.get(CascadedictSubsection.class, id);
	}

	@Override
	public void update(CascadedictSubsection subsection) throws Exception {
		cascadedictSubsection.update(subsection);
	}

	@Override
	public void deleteByObj(Object obj) throws Exception {
		
	}

	@Override
	public void saveOrUpdate(CascadedictSubsection subsection) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		subsection.setUpdateTime(df.format(new Date()));
		if (subsection.getId() == null || "".equals(subsection.getId())) {
			CascadedictCodeGenerator btNg = new CascadedictCodeGenerator();
			cascadedictSubsection.insert(btNg);
			Integer id = btNg.getId();
			subsection.setId(id);
			cascadedictSubsection.insert(subsection);
		} else {
			cascadedictSubsection.update(subsection);
		}
		
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		
		
		
		cascadedictSubsection.deleteById(id);
	}

	@Override
	public List<CascadedictSubsection> getSubSelectByParentId(Integer parentId) throws Exception {
		return cascadedictSubsection.getSubSelectByParentId(parentId);
	}
	
	@Override
	public List<CascadedictSubsectionChild> getSubChildByPid(Integer subsectionId) throws Exception {
		return cascadedictSubsection.getSubChildByPid(subsectionId);
	}

	@Override
	public void saveOrUpSubChild(CascadedictSubsectionChild subChild) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		subChild.setUpdateTime(df.format(new Date()));
		if (subChild.getId() == null || "".equals(subChild.getId())) {
			CascadedictCodeGenerator btNg = new CascadedictCodeGenerator();
			cascadedictSubsection.insert(btNg);
			Integer id = btNg.getId();
			subChild.setId(id);
			cascadedictSubsection.insert(subChild);
		} else {
			cascadedictSubsection.update(subChild);
		}
	}

	@Override
	public void delSubChildById(Integer id) throws Exception {
		cascadedictSubsection.delSubChildById(id);
	}

	
	
}
