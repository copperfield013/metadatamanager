package cn.sowell.datacenter.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class WebServiceUtil {
	
	public static String getWsdlResult(String url , String method, String params){
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
		Client client = clientFactory.createClient(url);
		Object[] result=new Object[0];
		try {
			 result = client.invoke(method, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  result[0].toString();
	}
}
