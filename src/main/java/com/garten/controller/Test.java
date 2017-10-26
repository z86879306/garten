package com.garten.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;

import com.garten.Thread.HuanXinThread;
import com.garten.service.BigcontrolService;
import com.garten.util.LyParam;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.myutil.MyUtil;
import com.mysql.fabric.xmlrpc.base.Array;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;





/**
 * 用户Controller层
 * @author Administrator
 *
 */

public class Test {
	
	@Autowired
	private static BigcontrolService bigcontrolService;
	
	public static void main(String[] args) throws ParseException, UnsupportedEncodingException, APIConnectionException, APIRequestException {

		System.out.println(LyParam.PRINCIPAL_QX);
		
	}
	public static String test1(){
		Long lon=57600l;
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sp.format(lon);
		
	}
	public static void test2() throws ParseException{
		SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月dd");
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Long lon=sp.parse("1970年1月1日").getTime()/1000;
		System.err.println(lon);
		System.err.println(format.format(1498838400000l));

		
	}
	public static void t11111(String str1 ,String str2){
		System.out.println(str1+str2);
	}

	private static int test3(String chineseNumber){
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] chArr = new char[]{'十','百','千','万','亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                        case 0:
                            temp *= 10;
                            break;
                        case 1:
                            temp *= 100;
                            break;
                        case 2:
                            temp *= 1000;
                            break;
                        case 3:
                            temp *= 10000;
                            break;
                        case 4:
                            temp *= 100000000;
                            break;
                        default:
                            break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }
	
	public static String  test4(int d) {
		String[] str = { " ", "一", "二", "三", "四", "五", "六", "七", "八", "九" };  
		String ss[] = new String[] { "", "十", "百", "千", "万", "十", "百", "千", "亿" }; 
		String s = String.valueOf(d);  
		StringBuffer sb = new StringBuffer();    
		for (int i = 0; i < s.length(); i++) {       
			String index = String.valueOf(s.charAt(i));  
			sb = sb.append(str[Integer.parseInt(index)]);  
			}       
		String sss = String.valueOf(sb); 
		int i = 0;     
		for (int j = sss.length(); j > 0; j--) { 
			sb = sb.insert(j, ss[i++]); 
			}     
		return sb.toString();
		}    
	//获取一年中是周末的天
	 public static void  test5() {
	        Calendar calendar =Calendar.getInstance(); //当前日期
	        int currentyear = calendar.get(Calendar.YEAR);
	        int nextyear = 1+calendar.get(Calendar.YEAR);
	        Calendar cstart =Calendar.getInstance(); 
	        Calendar cend =Calendar.getInstance(); 
	        cstart.set(currentyear, 0, 1);//2010-1-1
	        cend.set(nextyear, 0, 1);//2011-1-1
	        calendar.add(Calendar.DAY_OF_MONTH,-calendar.get(Calendar.DAY_OF_WEEK)); //周六
	        Calendar d = (Calendar)calendar.clone();
	        //向前
	        for(;calendar.before(cend)&&calendar.after(cstart);calendar.add(Calendar.DAY_OF_YEAR, -7)){
	            printf(calendar);
	        }
	        //向后
	        for(;d.before(cend)&&d.after(cstart);d.add(Calendar.DAY_OF_YEAR, 7)) {
	            printf(d);
	        }
	    }
	  public static void printf(Calendar calendar)
	    {
	        System.out.println(calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+calendar.get(Calendar.DATE));
	         
	    }
	  public static void test6( ) throws ParseException {
		  DateFormat df = new SimpleDateFormat("yyyy-M-d");
	        Date date = df.parse("2016-1-1");
	        int day = date.getDay(); 
	        int startSatOffset = 6-day;
	        if(day==0){
	            System.out.println("此年的第一天是星期天");
	        }
	        for(int i=0;i<365/7;i++){
	            Date satday = df.parse("2010-1-"+(1+startSatOffset+i*7));
	            Date sunday = df.parse("2010-1-"+(1+startSatOffset+(i*7+1)));
	            System.out.println("第"+(i+1)+"个星期末是:"+df.format(satday)+"和"+df.format(sunday));
	        }	         
	    }
	  
	  public static void test7(){
		  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		  int year = 2017;
	        Calendar calendar = new GregorianCalendar(year, 0, 1);
	        int i = 1;
	        while (calendar.get(Calendar.YEAR) < year + 1) {
	            calendar.set(Calendar.WEEK_OF_YEAR, i++);
	            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	            if (calendar.get(Calendar.YEAR) == year) {
	                System.out.println(calendar.getTime().getTime());
	            }
	            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
	            if (calendar.get(Calendar.YEAR) == year) {
	                System.out.println(calendar.getTime().getTime());
	            }
	        }
	    }
	 
	  public static boolean isEqeal(Integer intt){
		  boolean flag = (0==intt);
		  return flag;
		  
	  }
}
