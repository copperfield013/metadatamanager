package com.abc.application.eight;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.FusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.panel.Discoverer;
import com.abc.panel.PanelFactory;
import com.abc.query.criteria.Criteria;
import com.abc.query.criteria.CriteriaFactory;
import com.abc.query.criteria.InequalQueryCriteria;
import com.abc.query.criteria.LikeQueryCriteria;
import com.abc.query.criteria.QueryCriteria;
import com.abc.query.entity.impl.EntitySortedPagedQuery;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SelectPeopleCrossTest {
	private static Logger logger = Logger
			.getLogger(SelectPeopleCrossTest.class);

	protected String mapperName = "baseinfoImport";
	protected String familyDoctorMapper = "familydoctor";
	protected String dictionaryMappingName = "default_dm";

	@Test
	public void select() {
		FusionContext context = new FusionContext();
		context.setMappingName(mapperName);
		context.setSource(FusionContext.SOURCE_COMMON);
		context.setDictionaryMappingName(dictionaryMappingName);
		List<Criteria> criterias = new ArrayList<Criteria>();
		CriteriaFactory criteriaFactory = new CriteriaFactory(context);
		LikeQueryCriteria like = criteriaFactory
				.createLikeQueryCriteria("姓名","张");
		criterias.add(like);
		QueryCriteria common = criteriaFactory.createQueryCriteria("性别","男");
		criterias.add(common);
		InequalQueryCriteria inequal = criteriaFactory
				.createInequalQueryCriteria("身份证号码","");
		criterias.add(inequal);

		//select(criterias, "出生日期");

	}

	@Test
	public void selectNoQueryCondition() {

		//select(null, null);

	}

	/*public void select(List<Criteria> criterias, String colName) {
		long startTime = System.currentTimeMillis();
		try {
			FusionContext context = new FusionContext();
			context.setMappingName(mapperName);
			context.setDictionaryMappingName(dictionaryMappingName);
			Discoverer discoverer = PanelFactory.getDiscoverer(context);

			EntitySortedPagedQuery sortedPagedQuery = discoverer.discover(
					criterias, colName);
			sortedPagedQuery.setPageSize(30);

			for (int i = 1; i < 2; i++) {
				logger.debug("第" + i + "页,共" + sortedPagedQuery.getAllCount()
						+ "条数据,每页" + sortedPagedQuery.getPageSize() + "条");
				// abcNode.selectAliasAsTitle();
				for (Entity entity : sortedPagedQuery.visit(i)) {
					// people.addMapping(abcNode);
					logger.debug(entity.toJson());
				}
			}
			long endTime = System.currentTimeMillis();// 记录结束时间
			logger.debug((float) (endTime - startTime) / 1000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
}
