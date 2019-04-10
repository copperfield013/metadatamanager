package com.zhsq.biz.common;

import java.util.List;

import com.abc.complexus.RecordComplexus;
import com.abc.fuse.identity.query.IdentityQuery;
import com.abc.fuse.identity.query.impl.DeniedIdentityQuery;
import com.abc.fuse.identity.query.impl.RecordCodeIdentityQuery;
import com.abc.rrc.query.queryrecord.criteria.Criteria;




public abstract class AbstractIdentityQuery  implements IdentityQuery{

	public List<Criteria> getCriteriaList(String recordCode,RecordComplexus complexus) {
		
		RecordCodeIdentityQuery recordCodeOnlyIQ = new RecordCodeIdentityQuery();
		List<Criteria> result = recordCodeOnlyIQ.getCriteriaList(recordCode,complexus);
		if (result.size() > 0) {
			return result;
		}

		List<Criteria> criteriaList = bizCriteriaList(recordCode,complexus);

		if (criteriaList==null || criteriaList.size() == 0) {
			DeniedIdentityQuery noElementForIQ = new DeniedIdentityQuery();
			criteriaList=noElementForIQ.getCriteriaList(recordCode,complexus);
		}
		return criteriaList;
	}

	protected abstract  List<Criteria> bizCriteriaList(String recordCode,RecordComplexus complexus);

}
