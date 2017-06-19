package com.garten.util.page;  
  
import java.util.*;

  
public class MyPage {  
  
  
    public static List<?> listPage(List<?> list,Integer pageNo){
    	 ////当前页,当前页是第一页，打开手机进来的时候是第一页 : 如果接收到一个值（这个值是传递过来的），则转化成一个整型数据，原因是这个页码是要变化的，而且还要累加  
        int currentPage=pageNo==null?1:pageNo;
         DividePage pUtil=new DividePage(3,list.size(), currentPage);//  
         List<?> subList=list.subList/* 从哪开始到哪结束*/ (pUtil.getFromIndex(), pUtil.getToIndex());//在总集合中截取集合，模拟分页  
    	return (List<?>) (null==subList?"没有用户":subList);
    }
}