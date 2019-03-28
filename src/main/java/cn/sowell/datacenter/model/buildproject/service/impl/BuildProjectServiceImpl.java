package cn.sowell.datacenter.model.buildproject.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.datacenter.model.buildproject.dao.BuildProjectDao;
import cn.sowell.datacenter.model.buildproject.service.BuildProjectService;
import cn.sowell.datacenter.utils.FileManager;
import cn.sowell.datacenter.utils.ZipCompress;

@Service
public class BuildProjectServiceImpl implements BuildProjectService {
	@Resource
	BuildProjectDao buildProjectDao;
	
	
	 /**
	  * 建立项目
	  * @return
	  */
	 public String buildProject(List<String> entityCodes) {
		 
		  String  enumFileName = "EnumKeyValue";
		  String  relationTypeFileName = "RelationType";
		  String  itemFileName = "Item";
		  String  fileNmaeSuffix = ".java";
		 
		 //获取类路径   D:\eclipse-workspace\metadatamanager
		String property = System.getProperty("user.dir");
		File file = new File(property);
		File parentFile = file.getParentFile();
		String constantPath = parentFile.getPath() + "\\baseproject\\src\\main\\java\\com\\zhsq\\biz\\constant";
		String itemPath = constantPath + "\\item"; 
		//获取枚举数据  生成文件， 写入文件夹
		 try {
			//往文件写入枚举数据
			File enumKeyFile = FileManager.createFile(constantPath, enumFileName + fileNmaeSuffix);
			createEnumFile(enumKeyFile, enumFileName);
			//往文件写入关系数据
			File relationTypeFile = FileManager.createFile(constantPath, relationTypeFileName + fileNmaeSuffix);
			createRelationFile(relationTypeFile, relationTypeFileName);
			//往文件写入item数据
			for (String entityCode : entityCodes) {
				String[] entityPrefix = entityCode.split("E");//之后抽取出去
				File itemFile = FileManager.createFile(itemPath, entityCode+ itemFileName + fileNmaeSuffix);
				createItemFile(itemFile,entityCode+ itemFileName , entityCode, entityPrefix[0]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 return null;
	 }

	@Override
	public void createEnumFile(File file, String fileName) {
		try {
			FileManager.writeFileContent(file, "package com.zhsq.biz.constant;");
			FileManager.writeFileContent(file, "public class "+fileName+" {");
			List<String> enumData = buildProjectDao.getEnumData();
			for (String data : enumData) {
				FileManager.writeFileContent(file, data);
			}
			FileManager.writeFileContent(file, "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createRelationFile(File file, String fileName) {
		try {
			FileManager.writeFileContent(file, "package com.zhsq.biz.constant;");
			FileManager.writeFileContent(file, "public class "+fileName+" {");
			List<String> relationData = buildProjectDao.getRelationData();
			for (String data : relationData) {
				FileManager.writeFileContent(file, data);
			}
			FileManager.writeFileContent(file, "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createItemFile(File file, String fileName, String entityCode, String entityPrefix) {
		try {
			FileManager.writeFileContent(file, "package com.zhsq.biz.constant.item;");
			FileManager.writeFileContent(file, "public class "+fileName+" {");
			List<String> itemData = buildProjectDao.getItemData(entityCode, entityPrefix);
			
			for (String data : itemData) {
				FileManager.writeFileContent(file, data);
			}
			FileManager.writeFileContent(file, "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String zipCompress() {
		ZipCompress zipCom = new ZipCompress("F:\\zip\\baseProject.zip","F:\\社区信息收集");
        try
        {
            zipCom.zip();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
		return null;
	}
	
}
