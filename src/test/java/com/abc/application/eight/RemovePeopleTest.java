package com.abc.application.eight;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.RemovedFusionContext;
import com.abc.panel.PanelFactory;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RemovePeopleTest {
	private static Logger logger = Logger.getLogger(RemovePeopleTest.class);

	@Test
	public void removePeople() {
		RemovedFusionContext appInfo = new RemovedFusionContext(
				"53e2409b7f0148358e1256168539b4b3", null, "test");
		logger.debug(PanelFactory.getIntegration().remove(appInfo));
	}
}
