package com.garten.util.page;  
  
import java.util.*;  
  
public class DividePage {  
  
    private int pageSize;// 每页显示的条数  
    private int recordCount; // 记录的总条数  
    private int currentPage;// 当前页  
    private int pageCount;// 总页数  
  
    public DividePage(int pageSize, int recordCount, int currentPage) {  
        this.pageSize = pageSize;  
        this.recordCount = recordCount;  
        this.setCurrentPage(currentPage);  
  
    }  
  
    public int getPageSize() {  
        return pageSize;  
    }  
  
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
  
    public int getRecordCount() {  
        return recordCount;  
    }  
  
    public void setRecordCount(int recordCount) {  
        this.recordCount = recordCount;  
    }  
  
    public int getCurrentPage() {  
        return currentPage;  
    }  
  
    /** 
     * 设置定位在当前页 
     *  
     * @param currentPage 
     */  
    public void setCurrentPage(int currentPage) {  
        int activePage = currentPage <= 0 ? 1 : currentPage;  
        activePage = activePage > getPageCount() ? getPageCount()  
                : activePage;  
        this.currentPage = activePage;  
    }  
  
    /** 
     * 获得总页数 
     *  
     * @return 
     */  
    public int getPageCount() {  
        pageCount = recordCount / pageSize;  
        int mod = recordCount % pageSize;  
        if (mod != 0) {  
            pageCount++;  
        }  
        return recordCount == 0 ? 1 : pageCount;  
    }  
  
    public int getFromIndex() {  
        return (currentPage - 1) * pageSize;  
    }  
  
    public int getToIndex() {  
  
        return Math.min(recordCount, currentPage * pageSize);  
    }  
  
    public void setPageCount(int pageCount) {  
        this.pageCount = pageCount;  
    }  
}  