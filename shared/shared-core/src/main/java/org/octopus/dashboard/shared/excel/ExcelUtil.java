package org.octopus.dashboard.shared.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExcelUtil {
	/************************************ XSSF *********************************************/

	/**
	 * 
	 * @param keyMap
	 * @param key
	 *            cell identity
	 * @return 0:column 1£ºrow
	 */
	public static int[] getPos(HashMap keyMap, String key) {
		int[] ret = new int[0];

		String val = (String) keyMap.get(key);

		if (val == null || val.length() == 0)
			return ret;

		String pos[] = val.split(",");

		if (pos.length == 1 || pos.length == 2) {
			ret = new int[pos.length];
			for (int i0 = 0; i0 < pos.length; i0++) {
				if (pos[i0] != null && pos[i0].trim().length() > 0) {
					ret[i0] = Integer.parseInt(pos[i0].trim());
				} else {
					ret[i0] = 0;
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param sheet
	 * @param rowNo
	 * @param cellNo
	 * @return
	 * @throws IOException
	 */
	public static String getCellValue(Sheet sheet, int rowNo, int cellNo) {
		String cellValue = null;
		Row row = sheet.getRow(rowNo);
		Cell cell = row.getCell(cellNo);
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				DecimalFormat df = new DecimalFormat("0");
				cellValue = getCutDotStr(df.format(cell.getNumericCellValue()));
			} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				cellValue = cell.getStringCellValue();
			}
			if (cellValue != null) {
				cellValue = cellValue.trim();
			}
		} else {
			cellValue = null;
		}
		return cellValue;
	}

	/**
	 * 
	 * @param srcString
	 * @return
	 */
	private static String getCutDotStr(String srcString) {
		String newString = "";
		if (srcString != null && srcString.endsWith(".0")) {
			newString = srcString.substring(0, srcString.length() - 2);
		} else {
			newString = srcString;
		}
		return newString;
	}

	/**
	 * 
	 * @param
	 * @throws IOException
	 */
	public static HashMap[] getTemplateFile(String templateFileName) throws IOException {
		FileInputStream fis = new FileInputStream(templateFileName);

		Workbook wbPartModule = null;
		if (templateFileName.endsWith(".xlsx")) {
			wbPartModule = new XSSFWorkbook(fis);
		} else if (templateFileName.endsWith(".xls")) {
			wbPartModule = new HSSFWorkbook(fis);
		}
		int numOfSheet = wbPartModule.getNumberOfSheets();
		HashMap[] templateMap = new HashMap[numOfSheet];
		for (int i = 0; i < numOfSheet; i++) {
			Sheet sheet = wbPartModule.getSheetAt(i);
			templateMap[i] = new HashMap();
			readSheet(templateMap[i], sheet);
		}
		fis.close();
		return templateMap;
	}

	/**
	 * 
	 * @param keyMap
	 * @param sheet
	 */

	private static void readSheet(HashMap keyMap, Sheet sheet) {
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();

		for (int j = firstRowNum; j <= lastRowNum; j++) {
			Row rowIn = sheet.getRow(j);
			if (rowIn == null) {
				continue;
			}
			int firstCellNum = rowIn.getFirstCellNum();
			int lastCellNum = rowIn.getLastCellNum();
			for (int k = firstCellNum; k <= lastCellNum; k++) {
				// Cell cellIn = rowIn.getCell((short) k);
				Cell cellIn = rowIn.getCell(k);
				if (cellIn == null) {
					continue;
				}

				int cellType = cellIn.getCellType();
				if (Cell.CELL_TYPE_STRING != cellType) {
					continue;
				}
				String cellValue = cellIn.getStringCellValue();
				if (cellValue == null) {
					continue;
				}
				cellValue = cellValue.trim();
				if (cellValue.length() > 2 && cellValue.substring(0, 2).equals("<%")) {
					String key = cellValue.substring(2, cellValue.length());
					String keyPos = Integer.toString(k) + "," + Integer.toString(j);
					keyMap.put(key, keyPos);
					keyMap.put(key + "CellStyle", cellIn.getCellStyle());
				} else if (cellValue.length() > 3 && cellValue.substring(0, 3).equals("<!%")) {
					String key = cellValue.substring(3, cellValue.length());
					keyMap.put("STARTCELL", Integer.toString(j));
					keyMap.put(key, Integer.toString(k));
					keyMap.put(key + "CellStyle", cellIn.getCellStyle());
				}
			}
		}
	}

	/**
	 * 
	 * @param keyMap
	 * @param key
	 * @return
	 */
	public static CellStyle getStyle(HashMap keyMap, String key, Workbook wb) {
		CellStyle cellStyle = null;

		cellStyle = (CellStyle) keyMap.get(key + "CellStyle");
		cellStyle.setWrapText(true);
		CellStyle newStyle = wb.createCellStyle();
		newStyle.cloneStyleFrom(cellStyle);
		return newStyle;
	}

	/**
	 * Excel
	 * 
	 * @param sheet
	 * @param row
	 * @param cell
	 * @param value
	 * @param cellStyle
	 */
	public static void setValue(Sheet sheet, int row, int cell, Object value, CellStyle cellStyle) {
		Row rowIn = sheet.getRow(row);
		if (rowIn == null) {
			rowIn = sheet.createRow(row);
		}
		Cell cellIn = rowIn.getCell(cell);
		if (cellIn == null) {
			cellIn = rowIn.createCell(cell);
		}
		if (cellStyle != null) {

			// CellStyle newStyle = wb.createCellStyle();
			// newStyle.cloneStyleFrom(cellStyle);
			cellIn.setCellStyle(cellStyle);
		}

		if (value == null) {
			cellIn.setCellValue("");
		} else {
			if (isCellDateFormatted(cellStyle)) {
				cellIn.setCellValue((Date) value);
			} else {
				cellIn.setCellValue(new XSSFRichTextString(value.toString()));
			}
		}
	}

	/**
	 * 
	 * @param cellStyle
	 * @return
	 */
	public static boolean isCellDateFormatted(CellStyle cellStyle) {
		if (cellStyle == null) {
			return false;
		}
		int i = cellStyle.getDataFormat();
		String f = cellStyle.getDataFormatString();

		return org.apache.poi.ss.usermodel.DateUtil.isADateFormat(i, f);
	}

	public static void createCell(Workbook wbModule, Sheet sheet, HashMap pos, int startCell, Object value,
			String cellStyle) {
		int[] excelPos = getPos(pos, cellStyle);
		setValue(sheet, startCell, excelPos[0], value, getStyle(pos, cellStyle, wbModule));
	}
	/************************************ XSSF *******************************************/
}
