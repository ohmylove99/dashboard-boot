package org.octopus.dashboard.shared.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("rawtypes")
public class ExcelHandle {

	private Map<String, HashMap[]> tempFileMap = new HashMap<String, HashMap[]>();
	private Map<String, Map<String, Cell>> cellMap = new HashMap<String, Map<String, Cell>>();
	private Map<String, FileInputStream> tempStream = new HashMap<String, FileInputStream>();
	private Map<String, Workbook> tempWorkbook = new HashMap<String, Workbook>();
	private Map<String, Workbook> dataWorkbook = new HashMap<String, Workbook>();

	class Cell {
		private int column;
		private int line;
		private CellStyle cellStyle;

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}

		public int getLine() {
			return line;
		}

		public void setLine(int line) {
			this.line = line;
		}

		public CellStyle getCellStyle() {
			return cellStyle;
		}

		public void setCellStyle(CellStyle cellStyle) {
			this.cellStyle = cellStyle;
		}
	}

	public void writeListData(String tempFilePath, List<String> cellList, List<Map<String, Object>> dataList, int sheet)
			throws IOException {

		HashMap temp = getTemp(tempFilePath, sheet);

		Workbook temWorkbook = getTempWorkbook(tempFilePath);

		int startCell = Integer.parseInt((String) temp.get("STARTCELL"));

		Sheet wsheet = temWorkbook.getSheetAt(sheet);

		wsheet.removeRow(wsheet.getRow(startCell));
		if (dataList != null && dataList.size() > 0) {
			for (Map<String, Object> map : dataList) {
				for (String cell : cellList) {

					Cell c = getCell(cell, temp, temWorkbook, tempFilePath);

					ExcelUtil.setValue(wsheet, startCell, c.getColumn(), map.get(cell), c.getCellStyle());
				}
				startCell++;
			}
		}
	}

	public void writeData(String tempFilePath, List<String> cellList, Map<String, Object> dataMap, int sheet)
			throws IOException {

		HashMap tem = getTemp(tempFilePath, sheet);

		Workbook wbModule = getTempWorkbook(tempFilePath);

		Sheet wsheet = wbModule.getSheetAt(sheet);
		if (dataMap != null && dataMap.size() > 0) {
			for (String cell : cellList) {

				Cell c = getCell(cell, tem, wbModule, tempFilePath);
				ExcelUtil.setValue(wsheet, c.getLine(), c.getColumn(), dataMap.get(cell), c.getCellStyle());
			}
		}
	}

	public Object getValue(String tempFilePath, String cell, int sheet, File excelFile) throws IOException {

		HashMap tem = getTemp(tempFilePath, sheet);

		Workbook temWorkbook = getTempWorkbook(tempFilePath);

		Workbook dataWorkbook = getDataWorkbook(tempFilePath, excelFile);

		Cell c = getCell(cell, tem, temWorkbook, tempFilePath);

		Sheet dataSheet = dataWorkbook.getSheetAt(sheet);
		return ExcelUtil.getCellValue(dataSheet, c.getLine(), c.getColumn());
	}

	public List<Map<String, Object>> getListValue(String tempFilePath, List<String> cellList, int sheet, File excelFile)
			throws IOException {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		HashMap tem = getTemp(tempFilePath, sheet);

		int startCell = Integer.parseInt((String) tem.get("STARTCELL"));

		Workbook dataWorkbook = getDataWorkbook(tempFilePath, excelFile);

		Sheet dataSheet = dataWorkbook.getSheetAt(sheet);

		int lastLine = dataSheet.getLastRowNum();

		for (int i = startCell; i <= lastLine; i++) {
			dataList.add(getListLineValue(i, tempFilePath, cellList, sheet, excelFile));
		}
		return dataList;
	}

	public Map<String, Object> getListLineValue(int line, String tempFilePath, List<String> cellList, int sheet,
			File excelFile) throws IOException {
		Map<String, Object> lineMap = new HashMap<String, Object>();

		HashMap tem = getTemp(tempFilePath, sheet);

		Workbook temWorkbook = getTempWorkbook(tempFilePath);

		Workbook dataWorkbook = getDataWorkbook(tempFilePath, excelFile);

		Sheet dataSheet = dataWorkbook.getSheetAt(sheet);
		for (String cell : cellList) {

			Cell c = getCell(cell, tem, temWorkbook, tempFilePath);
			lineMap.put(cell, ExcelUtil.getCellValue(dataSheet, line, c.getColumn()));
		}
		return lineMap;
	}

	/**
	 *
	 * 
	 * @param tempFilePath
	 * @return
	 * @throws FileNotFoundException
	 */
	private FileInputStream getFileInputStream(String tempFilePath) throws FileNotFoundException {
		if (!tempStream.containsKey(tempFilePath)) {
			tempStream.put(tempFilePath, new FileInputStream(tempFilePath));
		}

		return tempStream.get(tempFilePath);
	}

	/**
	 *
	 * 
	 * @param tempFilePath
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private Workbook getTempWorkbook(String tempFilePath) throws FileNotFoundException, IOException {
		if (!tempWorkbook.containsKey(tempFilePath)) {
			if (tempFilePath.endsWith(".xlsx")) {
				tempWorkbook.put(tempFilePath, new XSSFWorkbook(getFileInputStream(tempFilePath)));
			} else if (tempFilePath.endsWith(".xls")) {
				tempWorkbook.put(tempFilePath, new HSSFWorkbook(getFileInputStream(tempFilePath)));
			}
		}
		return tempWorkbook.get(tempFilePath);
	}

	/**
	 * 
	 * 
	 * @param cell
	 * @param tem
	 * @param wbModule
	 * @param tempFilePath
	 * @return
	 */
	private Cell getCell(String cell, HashMap tem, Workbook wbModule, String tempFilePath) {
		if (!cellMap.get(tempFilePath).containsKey(cell)) {
			Cell c = new Cell();

			int[] pos = ExcelUtil.getPos(tem, cell);
			if (pos.length > 1) {
				c.setLine(pos[1]);
			}
			c.setColumn(pos[0]);
			c.setCellStyle((ExcelUtil.getStyle(tem, cell, wbModule)));
			cellMap.get(tempFilePath).put(cell, c);
		}
		return cellMap.get(tempFilePath).get(cell);
	}

	/**
	 * 
	 * 
	 * @param tempFilePath
	 * 
	 * @param sheet
	 * @return
	 * @throws IOException
	 */
	private HashMap getTemp(String tempFilePath, int sheet) throws IOException {
		if (!tempFileMap.containsKey(tempFilePath)) {
			tempFileMap.put(tempFilePath, ExcelUtil.getTemplateFile(tempFilePath));
			cellMap.put(tempFilePath, new HashMap<String, Cell>());
		}
		return tempFileMap.get(tempFilePath)[sheet];
	}

	/**
	 * 
	 * 
	 * @param tempFilePath
	 * 
	 * @param os
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void writeAndClose(String tempFilePath, OutputStream os) throws FileNotFoundException, IOException {
		if (getTempWorkbook(tempFilePath) != null) {
			getTempWorkbook(tempFilePath).write(os);
			tempWorkbook.remove(tempFilePath);
		}
		if (getFileInputStream(tempFilePath) != null) {
			getFileInputStream(tempFilePath).close();
			tempStream.remove(tempFilePath);
		}
	}

	/**
	 * 
	 * 
	 * @param tempFilePath
	 * @param excelFile
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private Workbook getDataWorkbook(String tempFilePath, File excelFile) throws FileNotFoundException, IOException {
		if (!dataWorkbook.containsKey(tempFilePath)) {
			if (tempFilePath.endsWith(".xlsx")) {
				dataWorkbook.put(tempFilePath, new XSSFWorkbook(new FileInputStream(excelFile)));
			} else if (tempFilePath.endsWith(".xls")) {
				dataWorkbook.put(tempFilePath, new HSSFWorkbook(new FileInputStream(excelFile)));
			}
		}
		return dataWorkbook.get(tempFilePath);
	}

	/**
	 *
	 * 
	 * @param tempFilePath
	 */
	public void readClose(String tempFilePath) {
		dataWorkbook.remove(tempFilePath);
	}

}