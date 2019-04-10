package com.zhsq.biz.timertask;

import java.util.Collection;

import com.abc.application.BizFusionContext;
import com.abc.application.BizNoBusy;
import com.abc.application.FusionContext;
import com.abc.auth.constant.AuthConstant;
import com.abc.panel.Integration;
import com.abc.panel.PanelFactory;

public class LoadEntityToWorkMemory {
	public static void loadEntity(String entityType, Collection<String>  codes, BizNoBusy bizNoBusy) {
		Integration integration=PanelFactory.getIntegration();
		BizFusionContext context = new BizFusionContext();
		context.putBizMap(entityType, bizNoBusy);
		context.setUserCode(AuthConstant.SUPERCODE);
		context.setSource(FusionContext.SOURCE_COMMON);
		if(codes!=null) {
			for(String code :codes) {
				integration.integrate(context,code);
			}
		}
	}
	
}
