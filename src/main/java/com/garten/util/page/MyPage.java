package com.garten.util.page;  
  
import java.util.*;

import com.garten.util.myutil.MyUtil;

  
public class MyPage {  
  
  //每页10条 app端用
    public static Map<String, Object> listPage(List<?> list,Integer pageNo){
    	 ////当前页,当前页是第一页，打开手机进来的时候是第一页 : 如果接收到一个值（这个值是传递过来的），则转化成一个整型数据，原因是这个页码是要变化的，而且还要累加  
        int currentPage=pageNo==null?1:pageNo;
         DividePage pUtil=new DividePage(10,list.size(), currentPage);//  
         pUtil.getPageCount();
         
         List<?> subList=list.subList/* 从哪开始到哪结束*/ (pUtil.getFromIndex(), pUtil.getToIndex());//在总集合中截取集合，模拟分页  
    	Map<String,Object> result=MyUtil.putMapParams("list", subList, "pageCount",   pUtil.getPageCount());
         return result;
    }
    //每页16条数据 控制端中
    public static Map<String, Object> listPage16(List<?> list,Integer pageNo){
   	 ////当前页,当前页是第一页，打开手机进来的时候是第一页 : 如果接收到一个值（这个值是传递过来的），则转化成一个整型数据，原因是这个页码是要变化的，而且还要累加  
       int currentPage=pageNo==null?1:pageNo;
        DividePage pUtil=new DividePage(16,list.size(), currentPage);//  
        pUtil.getPageCount();
        
        List<?> subList=list.subList/* 从哪开始到哪结束*/ (pUtil.getFromIndex(), pUtil.getToIndex());//在总集合中截取集合，模拟分页  
   	Map<String,Object> result=MyUtil.putMapParams("list", subList, "pageCount",   pUtil.getPageCount());
        return result;
   }
}