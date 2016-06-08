package org.octopus.dashboard.shared.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelHandleTest {
	public static void main(String args[]) throws IOException {
		String tempFilePath = ExcelHandle.class.getResource("/test.xlsx").getPath();
		List<String> dataListCell = new ArrayList<String>();
		dataListCell.add("names");
		dataListCell.add("ages");
		dataListCell.add("sexs");
		dataListCell.add("deses");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("names", "names");
		map.put("ages", 22);
		map.put("sexs", "男");
		map.put("deses", "测试");
		dataList.add(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("names", "names1");
		map1.put("ages", 23);
		map1.put("sexs", "男");
		map1.put("deses", "测试1");
		dataList.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("names", "names2");
		map2.put("ages", 24);
		map2.put("sexs", "女");
		map2.put("deses", "测试2");
		dataList.add(map2);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("names", "names3");
		map3.put("ages", 25);
		map3.put("sexs", "男");
		map3.put("deses", "测试3");
		dataList.add(map3);

		ExcelHandle handle = new ExcelHandle();
		handle.writeListData(tempFilePath, dataListCell, dataList, 0);

		List<String> dataCell = new ArrayList<String>();
		dataCell.add("name");
		dataCell.add("age");
		dataCell.add("sex");
		dataCell.add("des");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("name", "name");
		dataMap.put("age", 11);
		dataMap.put("sex", "女");
		dataMap.put("des", "测试");

		handle.writeData(tempFilePath, dataCell, dataMap, 0);

		File file = new File("c:/data.xlsx");
		OutputStream os = new FileOutputStream(file);

		handle.writeAndClose(tempFilePath, os);

		os.flush();
		os.close();

		System.out.println("读取写入的数据----------------------------------%%%");
		System.out.println("name:" + handle.getValue(tempFilePath, "name", 0, file));
		System.out.println("age:" + handle.getValue(tempFilePath, "age", 0, file));
		System.out.println("sex:" + handle.getValue(tempFilePath, "sex", 0, file));
		System.out.println("des:" + handle.getValue(tempFilePath, "des", 0, file));
		System.out.println("读取写入的列表数据----------------------------------%%%");
		List<Map<String, Object>> list = handle.getListValue(tempFilePath, dataListCell, 0, file);
		for (Map<String, Object> data : list) {
			for (String key : data.keySet()) {
				System.out.print(key + ":" + data.get(key) + "--");
			}
			System.out.println("");
		}

		handle.readClose(tempFilePath);
	}

}
