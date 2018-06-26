package cn.sowell.datacenter.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

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
	
	/**
	 * 检测ip是否联通
	 * 
	 * @param url
	 * @return
	 */
	public static String getWsdlResult(String url){
		HttpURLConnection conn = null;
		
		String flag = "false";
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            int code = conn.getResponseCode();
           if (code == 200) {
        	   flag = "true";
           }
        }catch (Exception e){
        	e.printStackTrace();
        }
        return flag;
	}
}
