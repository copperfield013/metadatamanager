package cn.sowell.datacenter.admin.controller.buildproject;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.sowell.copframe.dto.page.PageInfo;
import cn.sowell.datacenter.admin.controller.AdminConstants;
import cn.sowell.datacenter.model.buildproject.service.BuildProjectService;
import cn.sowell.datacenter.model.dictionary.pojo.BasicChange;
import cn.sowell.datacenter.model.dictionary.pojo.BasicItemCodeGenerator;
import cn.sowell.datacenter.model.dictionary.service.BasicChangeService;

@Controller
@RequestMapping(AdminConstants.URI_BASE + "/buildproject")
public class BuildProjectController {
    
	@Resource
	BuildProjectService buildProjectService;
	
	@Resource
	BasicChangeService basicChangeService;
	
    @RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView list(PageInfo pageInfo){
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName(AdminConstants.JSP_BASE + "/buildproject/list.jsp");
		return mv;
	}
    
    /**
     * 	下载规则项目
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/downloadProject")
    public ResponseEntity<byte[]> downloadProject(String entityCodes)throws Exception {
	   
    	String[] split = entityCodes.split(",");
    	List<String> asList = Arrays.asList(split);
    	File buildProject = buildProjectService.buildProject(asList);
	   
	   byte[] readFileToByteArray = FileUtils.readFileToByteArray(buildProject);
	   	buildProject.delete();
	   	buildProjectService.initializeProject();
		HttpHeaders headers = new HttpHeaders();  
	    String downloadFileName = new String(buildProject.getName().getBytes("UTF-8"),"iso-8859-1");//设置编码
	    headers.setContentDispositionFormData("attachment", downloadFileName);
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    
       return new ResponseEntity<byte[]>(readFileToByteArray,    
               headers, HttpStatus.OK);  
    }
    
    
    /**
     * 	下载枚举文件
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/downloadEnumFile")
    public ResponseEntity<byte[]> downloadEnumFile()throws Exception {
    	
    	File file = File.createTempFile("EnumKeyValue", ".java");
    	String fileName = "EnumKeyValue.java";
    	buildProjectService.createEnumFile(file, "EnumKeyValue");
    	byte[] readFileToByteArray = FileUtils.readFileToByteArray(file);
	   	if (file.exists() && file.isFile()) {
	    	//在程序退出时删除临时文件
	   		file.delete();
	      }
	   	
		HttpHeaders headers = new HttpHeaders();  
	    String downloadFileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");//设置编码
	    headers.setContentDispositionFormData("attachment", downloadFileName);
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    
       return new ResponseEntity<byte[]>(readFileToByteArray,    
               headers, HttpStatus.OK);  
    }
    
    /**
     * 	下载关系文件
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/downloadRelationFile")
    public ResponseEntity<byte[]> downloadRelationFile()throws Exception {
    	
    	File file = File.createTempFile("RelationType", ".java");
    	String fileName = "RelationType.java";
    	buildProjectService.createRelationFile(file, "RelationType");
    	byte[] readFileToByteArray = FileUtils.readFileToByteArray(file);
	   	if (file.exists() && file.isFile()) {
	    	//在程序退出时删除临时文件
	   		file.delete();
	      }
	   	
		HttpHeaders headers = new HttpHeaders();  
	    String downloadFileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
	    headers.setContentDispositionFormData("attachment", downloadFileName);
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    
       return new ResponseEntity<byte[]>(readFileToByteArray,    
               headers, HttpStatus.OK);  
    }
    
    
    /**
     * 	下载Item文件
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/downloadItemFile")
    public ResponseEntity<byte[]> downloadItemFile(String entityCode)throws Exception {
    	
    	String entityPrefix = new BasicItemCodeGenerator().getprefix(entityCode);
    	File file = File.createTempFile(entityCode, "Item.java");
    	String fileName = entityCode + "Item.java";
    	buildProjectService.createItemFile(file,entityCode+"Item" , entityCode, entityPrefix);
    	byte[] readFileToByteArray = FileUtils.readFileToByteArray(file);
	   	if (file.exists() && file.isFile()) {
	    	//在程序退出时删除临时文件
	   		file.delete();
	      }
	   	
		HttpHeaders headers = new HttpHeaders();  
	    String downloadFileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
	    headers.setContentDispositionFormData("attachment", downloadFileName);
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    
       return new ResponseEntity<byte[]>(readFileToByteArray,    
               headers, HttpStatus.OK);  
    }
    
    
    
    // 获取所有改变的t_sc_basic_change数据
    @ResponseBody
	@RequestMapping("/getBasicChangeList")
	public String getBasicChangeList(String refTypeCode){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jobj = new JSONObject(map);
		try {
			List<BasicChange> basicChangeList = (List<BasicChange>)basicChangeService.queryList();
			
			map.put("code", 200);
			map.put("msg", "操作成功");
			map.put("basicChangeList", basicChangeList);
			return jobj.toString();
		} catch (Exception e) {
			
			map.put("code", 400);
			map.put("msg", "操作失败");
			return jobj.toString();
		}
	}
    
    @ResponseBody
   	@RequestMapping("/getBasicChange")
   	public String getBasicChange(String code){
   		Map<String, Object> map = new HashMap<String, Object>();
   		JSONObject jobj = new JSONObject(map);
   		try {
   			BasicChange basicChange = basicChangeService.getOne(code);
   			
   			map.put("code", 200);
   			map.put("msg", "操作成功");
   			map.put("basicChange", basicChange);
   			return jobj.toString();
   		} catch (Exception e) {
   			map.put("code", 400);
   			map.put("msg", "操作失败");
   			return jobj.toString();
   		}
   	}
	
}
	
