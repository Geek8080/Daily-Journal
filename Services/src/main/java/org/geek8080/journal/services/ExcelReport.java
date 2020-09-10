package org.geek8080.journal.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.geek8080.journal.entities.Diary;
import org.geek8080.journal.entities.Page;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReport {

	private static final Logger LOGGER = LogManager.getLogger(ExcelReport.class);

	private static String columns[] = {"S.No.", "Creation Time", "Title", "Subtitle", "Body"};

	public static void generateExcelReport(String location, String fileName, Diary diary){
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Diary Entries");
		CreationHelper creationHelper = workbook.getCreationHelper();

		XSSFFont headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 18);
		headerFont.setColor(IndexedColors.DARK_BLUE.index);
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		for(int i = 0;i<columns.length;i++){
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		HashMap<Integer, Page> pages = diary.getPages();

		CellStyle bodyCellStyle = workbook.createCellStyle();
		bodyCellStyle.setWrapText(true);
		bodyCellStyle.setShrinkToFit(true);

		int rowNum = 1;

		for(Map.Entry<Integer, Page> pageEntry: pages.entrySet()){
			Page page = pageEntry.getValue();
			Row row = sheet.createRow(rowNum);
			row.createCell(0).setCellValue(rowNum++);
			row.createCell(1).setCellValue(page.getCreationTimeString());
			row.createCell(2).setCellValue(page.getTitle());
			String subTitle = page.getSubTitle();
			subTitle = (subTitle == null || subTitle.equalsIgnoreCase("null") || subTitle.equalsIgnoreCase(""))?"":subTitle;
			row.createCell(3).setCellValue((subTitle));
			row.createCell(4).setCellValue(page.getBody().toString());
			row.getCell(3).setCellStyle(bodyCellStyle);
			row.getCell(4).setCellStyle(bodyCellStyle);
		}

		for(int i = 0;i< columns.length;i++){
			//if(i != 3)
				sheet.autoSizeColumn(i);
		}

		sheet.setColumnWidth(3, 12000);
		sheet.setColumnWidth(4, 25000);

		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(location + fileName + ".xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			LOGGER.fatal("Error while writing Excel Report file.", e);
		} catch (IOException e) {
			LOGGER.fatal("Error while writing Excel Report file.", e);
		}

	}
}
