package com.garten.util.excel;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtils {
	public static <T> void Export_(String[] titles, String[] fields,
			List<T> list, ServletOutputStream os) {

		try {
			// 1 创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 1.1 创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0,
					titles.length - 1);
			// 1.2 头标题标签
			HSSFCellStyle style0 = createCellStyle(workbook, (short) 16);
			style0.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style0.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
			// style0.setFillBackgroundColor(HSSFColor.GREY_80_PERCENT.index);
			// 1.3 列标题标签
			HSSFCellStyle style1 = createCellStyle(workbook, (short) 13);

			// 2 创建工作表
			Sheet sheet = workbook.createSheet();
			// 2.1 加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			// 2.2 设置默认列宽
			sheet.setDefaultColumnWidth(20);
			sheet.setDefaultRowHeightInPoints(20);

			// 3 创建行
			// 3.1 创建头标题行
			Row row0 = sheet.createRow(0);
			Cell cell0 = row0.createCell(0);
			cell0.setCellStyle(style0);
			cell0.setCellValue("导出列表");

			// 3.2 创建列标题行
			Row row1 = sheet.createRow(1);
			for (int i = 0; i < titles.length; i++) {
				Cell cell1 = row1.createCell(i);
				cell1.setCellStyle(style1);
				cell1.setCellValue(titles[i]);
			}

			HSSFCellStyle style2 = workbook
					.createCellStyle();
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			//style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			/*style2.setFillForegroundColor((i % 2 == 0) ? HSSFColor.GREY_25_PERCENT.index
					: HSSFColor.WHITE.index);*/
			// 4 操作单元格
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Row row2 = sheet.createRow(i + 2);
					Class clazz = list.get(i).getClass();
					Method[] methods = clazz.getMethods();
					for (int j = 0; j < fields.length; j++) {
						String regex = "^((get)|(is))(" + fields[j] + ")$";
						// 循环匹配field对应的get/is方法
						for (int k = 0; k < methods.length; k++) {
							String methodName = methods[k].getName().toLowerCase();
							if (methodName.matches(regex)) {
								// 调用方法
								Object value = methods[k].invoke(list.get(i),
										new Object[] {});
								// 设置单元格样式
								
								// 把匹配到的方法的返回值设置到单元格
								Cell cell2 = row2.createCell(j);
								cell2.setCellStyle(style2);
								try {
									// 。。。
									cell2.setCellValue((Boolean) value ? "是"
											: "否");
								} catch (Exception e) {
									if(null!=value){
										cell2.setCellValue(value.toString());
									}
								}
								continue;
							}
						}
					}
				}
				// 5 输出
				workbook.write(os);
				workbook.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short s) {
		HSSFCellStyle style = workbook.createCellStyle();
		// 居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 字体
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints(s);
		style.setFont(font);
		return style;
	}
}
