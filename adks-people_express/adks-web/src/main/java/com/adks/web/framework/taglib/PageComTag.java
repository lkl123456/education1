package com.adks.web.framework.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.adks.dubbo.commons.Page;
import com.adks.web.framework.taglib.common.PageTagBuilder;

public class PageComTag extends TagSupport {

	private static final long serialVersionUID = -6126621254327275631L;

	private PageContext pageContext; 

    private String defaultValue = "0"; 

    private String url;
    
    private String style; 

    private int count = -1; 

    private int pageSize = -1; 

    private String currentPage = "-1"; 
    
    @Override 
    public int doStartTag() throws JspException {
    	
    	Page page = (Page)pageContext.getRequest().getAttribute("page");
    	if(page != null && page.getCurrentPage() > page.getTotalPage()){
    		page.setCurrentPage(page.getTotalPage());
    	}
        //pageInit();
        
        if(pageContext.getRequest().getParameter("currentPage") != null ){
			currentPage = pageContext.getRequest().getParameter("currentPage");
		}
        
    	if(this.currentPage == null || this.currentPage.trim().equals(""))
			this.currentPage = "1";
        
    	if(this.pageSize == -1 || this.pageSize != (Integer)this.pageContext.getRequest().getAttribute("pageSize")){
     		//this.pageSize = (Integer)this.pageContext.getRequest().getAttribute("pageSize");
     	}
    	
    	PageTagBuilder builder = new PageTagBuilder();
    	
    	String html = builder.build(page);
    	
    	try {
			pageContext.getOut().print(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
        
    } 
    
//    private void pageInit(){
//    	
//        Page page = (Page)pageContext.getRequest().getAttribute("pager");
//        
//        /* 按这种方式会有问题
//        if(page != null){
//        	if(this.count == -1 || this.count != page.getTotalRows()){
//                this.count = page.getTotalRows();
//            }
//            if(this.pageSize == -1 || this.pageSize != page.getPageSize()){
//                this.pageSize = page.getPageSize();
//            }
//            if(this.curPage == -1 || this.curPage != page.getCurrentPage()){
//                this.curPage = page.getCurrentPage();
//            }
//        }else{
//        	 if(this.pageContext.getRequest().getAttribute("count") == null){
//         		return;
//         	}
//         	if(this.count == -1 || this.count != (Integer)this.pageContext.getRequest().getAttribute("count")){
//         		this.count = (Integer)this.pageContext.getRequest().getAttribute("count");
//         	}
//         	if(this.pageSize == -1 || this.pageSize != (Integer)this.pageContext.getRequest().getAttribute("pageSize")){
//         		this.pageSize = (Integer)this.pageContext.getRequest().getAttribute("pageSize");
//         	}
//         	if(this.curPage == -1 || this.curPage != (Integer)this.pageContext.getRequest().getAttribute("curPage")){
//         		this.curPage = (Integer)this.pageContext.getRequest().getAttribute("curPage");
//         	}
//        }
//        */
//    }

    @Override 
    public void setPageContext(PageContext arg0) { 
        this.pageContext = arg0; 
    } 

    public String getDefaultValue() { 
        return defaultValue; 
    } 

    public void setDefaultValue(String defaultValue) { 
        this.defaultValue = defaultValue; 
    } 

    public void setDefaultValue(int defaultValue) { 
        this.defaultValue = String.valueOf(defaultValue); 
    }

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	} 

   
} 

 
