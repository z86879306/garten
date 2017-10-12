package com.garten.util.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.garten.vo.smallcontrol.CardNoDetail;
import com.garten.vo.smallcontrol.OrderAll;


public class ExcelUtil {
	
	//导出订单列表
	public static void exportOrderExcel(List<OrderAll> orderList,ServletOutputStream outputStream){
		try {
			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 6);
			//1.2、头标题样式
			HSSFCellStyle style1 = createcellStyle(workbook, (short)16);
			//1.3、列标题样式
			HSSFCellStyle style2 = createcellStyle(workbook, (short)13);
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("订单列表");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			sheet.setDefaultColumnWidth(20);
			//3、创建行
			//3.1、创建头标题行；并且设置头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell = row1.createCell(0);
			//加载样式
			cell.setCellStyle(style1);
			cell.setCellValue("订单列表");
			//3.2、创建列标题行；并且设置列标
			HSSFRow row = sheet.createRow(1);

			String titles[]= {"订单编号","下单时间","手机号","订单金额","商品详情","消费者","支付方式"};
			for(int i=0;i<titles.length;i++){
				HSSFCell cell2 = row.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			//4、操作单元格；将用户列表写入excel
			if(orderList!=null){
				for(int i=0;i<orderList.size();i++){
					HSSFRow row2 = sheet.createRow(i+2);
					HSSFCell cell0 = row2.createCell(0);
					cell0.setCellValue(orderList.get(i).getOrderNumber().toString());
					HSSFCell cell1 = row2.createCell(1);
					cell1.setCellValue(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(orderList.get(i).getOrderTime()*1000)));
					HSSFCell cell2 = row2.createCell(2);
					cell2.setCellValue(orderList.get(i).getPhoneNumber());
					HSSFCell cell3 = row2.createCell(3);
					cell3.setCellValue(orderList.get(i).getOrderMoney().doubleValue());
					HSSFCell cell4 = row2.createCell(4);
					cell4.setCellValue(orderList.get(i).getOrderDetail());
					HSSFCell cell5 = row2.createCell(5);
					cell5.setCellValue(orderList.get(i).getName());
					HSSFCell cell6 = row2.createCell(6);
					cell6.setCellValue(orderList.get(i).getPayType()==0?"支付宝":"微信");
				}
			}
			//5、输出
			//
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//导出考勤卡(幼儿园端）
	public static  void exportAttendanceNoExcel(List<CardNoDetail> list ,ServletOutputStream outputStream){
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 3);
			//1.2、头标题样式
			HSSFCellStyle style1 = createcellStyle(workbook, (short)16);
			//1.3、列标题样式
			HSSFCellStyle style2 = createcellStyle(workbook, (short)13);
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("考勤卡列表");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			sheet.setDefaultColumnWidth(20);
			//3、创建行
			//3.1、创建头标题行；并且设置头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell = row1.createCell(0);
			//加载样式
			cell.setCellStyle(style1);
			cell.setCellValue("考勤卡列表");
			//3.2、创建列标题行；并且设置列标
			HSSFRow row = sheet.createRow(1);

			String titles[]= {"类型","持卡人姓名","考勤卡号1","考勤卡号2","考勤卡号3"};
			for(int i=0;i<titles.length;i++){
				HSSFCell cell2 = row.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			//4、操作单元格；将用户列表写入excel
			if(list!=null){
				for(int i=0;i<list.size();i++){
					HSSFRow row2 = sheet.createRow(i+2);
					HSSFCell cell0 = row2.createCell(0);
					cell0.setCellValue(list.get(i).getJob());
					HSSFCell cell1 = row2.createCell(1);
					cell1.setCellValue(list.get(i).getName());
					HSSFCell cell2 = row2.createCell(2);
					cell2.setCellValue(list.get(i).getCardNo1());
					HSSFCell cell3 = row2.createCell(3);
					cell3.setCellValue(list.get(i).getCardNo2());
					HSSFCell cell4 = row2.createCell(4);
					cell4.setCellValue(list.get(i).getCardNo3());
				}
			}
			//5、输出
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static HSSFCellStyle createcellStyle(HSSFWorkbook workbook,short fontSize){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//1.21 字体样式
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗字体
		font.setFontHeightInPoints(fontSize);
		//加载字体
		style.setFont(font);
		return style;
	}
}
