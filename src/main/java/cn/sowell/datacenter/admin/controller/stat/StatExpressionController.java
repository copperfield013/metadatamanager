package cn.sowell.datacenter.admin.controller.stat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc.model.enun.AggregateFunctionType;
import com.alibaba.fastjson.JSONObject;

import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.stat.pojo.StatDf;
import cn.sowell.datacenter.model.stat.pojo.StatExpression;
import cn.sowell.datacenter.model.stat.service.StatExpressionService;

@Controller
@RequestMapping(AdminConstants.URI_STAT + "/statExpression")
public class StatExpressionController {
	
	@Resource
	StatExpressionService statExpressionService;
	
	Logger logger = Logger.getLogger(StatExpressionController.class);
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder binder) {
		System.out.println("执行了InitBinder方法");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/do_add")
	public String doAdd(StatExpression creteria){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			
			statExpressionService.saveOrUpdate(creteria);
			
			map.put("creteria", creteria);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}  catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}

	@ResponseBody
	@RequestMapping("/do_delete")
	public String doDelte(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			statExpressionService.delete(id);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
	
	/**
	 * 获取getSQLFunctionType
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSQLFunctionType")
	public String getSQLFunctionType(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			AggregateFunctionType[] values = AggregateFunctionType.values();
				List list = new ArrayList();
			for (AggregateFunctionType sqlFunctionType : values) {
				List newList = new ArrayList();
				newList.add(sqlFunctionType.getName());
				newList.add(sqlFunctionType.getIndex());
				newList.add(sqlFunctionType.getParaSize());
				list.add(newList);
			}
			
			map.put("sqlFunctionType", list);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/getStatExpression")
	public String getStatExpression(Integer expressionId){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			 StatExpression statExpression = statExpressionService.get(StatExpression.class, expressionId);
			map.put("statExpression", statExpression);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}  catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
	
	/**
	 * 获取function的孩子
	 * @param expressionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getStatExpChild")
	public String getStatExpChild(Integer expressionId){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			
			List<StatExpression> statExpList = new ArrayList<StatExpression>();
			
			StatExpression statExpression = statExpressionService.get(StatExpression.class, expressionId);
				 
			if (statExpression.getParameter1() != null) {
				StatExpression statExpression1 = statExpressionService.get(StatExpression.class, statExpression.getParameter1());
				statExpList.add(statExpression1);
			}
			if (statExpression.getParameter2() != null) {
				StatExpression statExpression2 = statExpressionService.get(StatExpression.class, statExpression.getParameter2());
				statExpList.add(statExpression2);
			}
			if (statExpression.getParameter3() != null) {
				StatExpression statExpression3 = statExpressionService.get(StatExpression.class, statExpression.getParameter3());
				statExpList.add(statExpression3);
			}
			
			map.put("statExpList", statExpList);
			map.put("code", 200);
			map.put("msg", "success");
			return jobj.toString();
		}  catch (Exception e) {
			logger.error("操作失败", e);
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
	
}
