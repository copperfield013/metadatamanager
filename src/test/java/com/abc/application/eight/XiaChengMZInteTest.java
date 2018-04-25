package com.abc.application.eight;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.FusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.PanelFactory;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class XiaChengMZInteTest {
	private static Logger logger = Logger.getLogger(XiaChengMZInteTest.class);
	//private ExecutorService pool = Executors.newFixedThreadPool(20);
	protected String mapperName = "xiachengMZ";
	protected String writeMappingName = "xiachengMZ";
	protected String dictionaryMappingName="default_dm";
	protected String familyDoctorMapper = "familydoctor";
	protected String educationhistoryMapper = "educationhistory";
	protected String familyInfomationMapper = "familyInfomation";
	protected String familyMemberMapper = "familyMember";
	protected String filename = "E:\\数据\\下城\\下城区\\朝晖街道\\大家苑社区2017-10-26人口数据test.xlsx";
	protected String sheetName = "民政局人口信息记录";
	protected String excelExtName = "xlsx";

	@Test
	public void readData() {
		long startTime = System.currentTimeMillis();
		Workbook wb = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			if ("xlsx".equalsIgnoreCase(excelExtName)) {
				wb = new XSSFWorkbook(fis);
			} else {
				wb = new HSSFWorkbook(fis);
			}
			Sheet sheet = wb.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);

			//executeFull(sheet, headerRow, mapperName, 1, false);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug((float) (endTime - startTime) / 1000);
	}

	/*protected void executeFull(Sheet sheet, Row headerRow, String mapperName,
			int number, boolean needDetails) {
		if (number < 1) {
			number = 1;
		}
		Row row ;
		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
			row = sheet.getRow(i + number);
			Entity entity = createHousingEntity(mapperName, headerRow, row);
			// 添加家庭信息
			Entity relatetion = createPeopleEntity(mapperName, headerRow, row);
			entity.putRelationEntity("使用关系", "房屋居住人", relatetion);

			FusionContext context = new FusionContext();
			context.setMappingName(writeMappingName);
			context.setSource(FusionContext.SOURCE_COMMON);
			context.setDictionaryMappingName(dictionaryMappingName);
			Integration integration = PanelFactory.getIntegration();
			logger.debug(entity.toJson());
			String code = integration.integrate(entity, context);

			logger.debug(code);
			if (needDetails) {
				Discoverer discoverer = PanelFactory.getDiscoverer(context);
				Entity result = discoverer.discover(code);
				logger.debug(code + " : " + result.toJson());
			}
		}
		

	}*/

	private Entity createHousingEntity(String mappingName, Row headerRow,
			Row row) {
		Entity entity = new Entity(mappingName);
		StringBuffer addresses = new StringBuffer();
		Cell cellH;
		Cell cellV;
		// 构建房屋地址
		for (int i = 0; i < 4; i++) {
			cellV = row.getCell(i);
			addresses.append(cellV.getStringCellValue());
		}
		addresses.append(row.getCell(6).getStringCellValue());
		addresses.append(row.getCell(7).getStringCellValue() + "单元");
		addresses.append(row.getCell(8).getStringCellValue() + "室");
		entity.putValue("地址", addresses.toString());
		// 网格
		cellH = headerRow.getCell(5);
		cellV = row.getCell(5);
		entity.putValue(cellH.getStringCellValue(),
				getValue(cellV));
		// 产权证号
		cellH = headerRow.getCell(11);
		cellV = row.getCell(11);
		entity.putValue(cellH.getStringCellValue(),
				getValue(cellV));
		// 入住时间
		cellH = headerRow.getCell(14);
		cellV = row.getCell(14);
		entity.putValue(cellH.getStringCellValue(),
				getValue(cellV));

		return entity;
	}

	private Entity createPeopleEntity(String mappingName, Row headerRow, Row row) {
		Entity entity = new Entity(mappingName);
		Cell cellH;
		Cell cellV;
		cellH = headerRow.getCell(9);
		cellV = row.getCell(9);
		entity.putValue(cellH.getStringCellValue(),
				getValue(cellV));

		int length = headerRow.getPhysicalNumberOfCells();
		for (int i = 15; i < length; i++) {
			Cell cell = row.getCell(i);
			entity.putValue(headerRow.getCell(i).getStringCellValue(),
					getValue(cell));
		}

		return entity;
	}

	public String getValue(Cell cell) {
		String result = null;
		if(cell==null){
			return result;
		}
		
		if (cell.getCellTypeEnum() == CellType.NUMERIC) {
			result = String.valueOf(cell.getNumericCellValue());
		} else if (cell.getCellTypeEnum() == CellType.ERROR) {
			logger.warn("ERROR Type cell");
		} else {

			result = cell.getStringCellValue();
		}

		return result;
	}

}
