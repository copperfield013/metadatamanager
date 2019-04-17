package cn.sowell.datacenter.model.node.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.abc.model.enun.ValueType;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItem;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItemCodeGenerator;
import cn.sowell.datacenter.model.node.dao.BinFilterBodyDao;
import cn.sowell.datacenter.model.node.pojo.BinFilter;
import cn.sowell.datacenter.model.node.pojo.BinFilterBody;
import cn.sowell.datacenter.model.node.service.BinFilterBodyService;

@Service
public class BinFilterBodyServiceImpl implements BinFilterBodyService {

	@Resource
	SessionFactory sFactory;
	
	@Resource
	BinFilterBodyDao binFilterBodyDao;

	@Override
	public List<BinFilterBody> queryList(BinFilterBody criteria, PageInfo pageInfo)  throws Exception{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(Object obj)  throws Exception{
		binFilterBodyDao.update(obj);
	}

	@Override
	public void insert(Object obj)  throws Exception{
		binFilterBodyDao.insert(obj);
		
	}

	@Override
	public void delete(Integer id)  throws Exception{
		binFilterBodyDao.delete(id);
	}

	@Override
	public void copyNode(Integer nodeId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BinFilterBody getBinFilterBody(Integer id) throws Exception {
		return binFilterBodyDao.get(BinFilterBody.class, id);
	}


	@Override
	public BinFilter getBinFilterByNodeId(Integer parentNodeId) throws Exception {
		return binFilterBodyDao.getBinFilterByNodeId(parentNodeId);
	}


	@Override
	public List<BinFilterBody> getFilterBodyChild(Integer parentId) throws Exception {
		return binFilterBodyDao.getFilterBodyChild(parentId);
	}
	
}
