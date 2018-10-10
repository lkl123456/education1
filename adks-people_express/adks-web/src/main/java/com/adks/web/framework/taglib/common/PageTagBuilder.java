package com.adks.web.framework.taglib.common;

import com.adks.dubbo.commons.Page;

/**
 * @author: hqt
 * @see:
 * @date:Sep 19, 2012
 */
public class PageTagBuilder {

	public String build(Page page) {
		
		StringBuffer sb = new StringBuffer();
		if(page.getCurrentPage() == 0){
			page.setCurrentPage(1);
		}
		String currentPage = page.getCurrentPage()+"";
		int maxPage = page.getTotalPage();
		int startPage = getStartPage(currentPage);
		int endPage = getEndPage(currentPage,maxPage);
		sb.append("<div class=\"table_left\">共 <b class=\"red\">"+page.getTotalRecords()+"</b> 条数据，共 <b class=\"red\">"+page.getTotalPage()+"</b> 页</div>");
//		sb.append(  "<span>共" + page.getTotalRecords() + "条</span>");
//		sb.append(  "<span>共" + page.getTotalPage() + "页</span>");
		sb.append("<div class=\"table_right\">");
		sb.append(goPage(page));
		
		sb.append(  getLastPageStr( currentPage, maxPage, page ) );
		sb.append(  getFrontPageStr(currentPage, maxPage, page ) );
		for (int i = endPage; i >= startPage ; i--) {
			 
			if (currentPage.equals( new Integer(i).toString()))
			{
				sb.append( currentPageStr( new Integer(i).toString() , page ));
			}
			else{
				sb.append( anchorPageStr( new Integer(i).toString(), i, page ) ) ;
			}
		}
		sb.append(  getBackPageStr( currentPage, page ) );
		sb.append(  getFirstPageStr(currentPage, page) );
		//sb.append(  goPage(page));
		sb.append("</div>");
		sb.append(getHiddenInput(page));// modify  -- add hidden filed to set currentpage value
		return sb.toString();
	}
	
	private String currentPageStr(String currntPage, Page page){

		String html =  "<a href=\"javascript:void(0);\" class=\"dq\" >"+currntPage+"</a>";
		return html+"";
	}
	
	private String getLastPageStr(String currentPage2, int maxPage, Page page) {
		  int currentPage_i = new Integer(currentPage2).intValue();
		  
		if ( currentPage_i >= maxPage )
			return nullLastPageStr("尾页");
		else
				
		   return lastPageStr("尾页", maxPage, page);
	}

	
	private String getFrontPageStr(String currentPage2, int maxPage, Page page) {
		  int currentPage_i = new Integer(currentPage2).intValue();
		  
			if ( currentPage_i >= maxPage )
				return nullNextPageStr("下一页");
			else
					
			   return nextPageStr("下一页",currentPage_i + 1, page );
		 
	}

	private String getBackPageStr( String currentPage2, Page page) {
		
       int currentPage_i = new Integer(currentPage2).intValue();
		
		if ( currentPage_i <= 1 )
			return nullPrevPageStr("上一页");
		else
				
		   return prevPageStr("上一页",currentPage_i -1, page );
	}

	private String getFirstPageStr( String currentPage2, Page page) {
		int currentPage_i = new Integer(currentPage2).intValue();
		
		if ( currentPage_i <= 1 )
			return nullFirstPageStr("首页");
		else
				
		   return firstPageStr("首页" ,1, page);
	}


	private String anchorPageStr(String string, int i, Page page) {
		
		String html = "<a href=\"" + "javascript:" +buildJS(i, page) + "\" >" + string
		+ "</a>";

		 
		return  html + ""; 
	}

//	private String textPageStr(String string) {
//		
//		String html =  "<a href=\"javascript:void(0);\" >"+string+"</a>" ;
//		
//		return html + "";
//	}
	
	private String lastPageStr(String string, int i, Page page) {
		String html = "<a href=\"" + "javascript:" +buildJS(i, page) + "\" class=\"my\" ></a>";
		return  html + ""; 
	}
	
	private String nullLastPageStr(String string) {
		String html = "<a href=\"" + "javascript:void(0);\" class=\"my_hover\" ></a>";
		return  html + ""; 
	}
	
	private String nextPageStr(String string, int i, Page page) {
		String html = "<a href=\"" + "javascript:" +buildJS(i, page) + "\" class=\"xyy\" ></a>";
		return  html + ""; 
	}
	
	private String nullNextPageStr(String string) {
		String html = "<a href=\"" + "javascript:void(0);\" class=\"xyy_hover\" ></a>";
		return  html + ""; 
	}
	
	private String prevPageStr(String string, int i, Page page) {
		String html = "<a href=\"" + "javascript:" +buildJS(i, page) + "\" class=\"syy\" ></a>";
		return  html + ""; 
	}
	
	private String nullPrevPageStr(String string) {
		String html = "<a href=\"" + "javascript:void(0);\" class=\"syy_hover\" ></a>";
		return  html + ""; 
	}
	
	private String firstPageStr(String string, int i, Page page) {
		String html = "<a href=\"" + "javascript:" +buildJS(i, page) + "\" class=\"sy\" ></a>";
		return  html + ""; 
	}
	
	private String nullFirstPageStr(String string) {
		String html = "<a href=\"" + "javascript:void(0);\" class=\"sy_hover\" ></a>";
		return  html + ""; 
	}

	private int getEndPage(String currentPage2,int maxPage) {
		int pageNum = 4;//BaseConfigHolder.getCurrnetMiddlePage() * 2 - 1;   // 每个页面中显示分页信息的数量
		int returnValue = 1;
		int startPage = getStartPage(currentPage2);
		returnValue = startPage + pageNum ;
		
		if (returnValue > maxPage )
			return maxPage ;
		
		return returnValue;
		
		
	}

	private int getStartPage(String currentPage2) {
		
		int middlePage = 2;//BaseConfigHolder.getCurrnetMiddlePage() - 1;  // 当前页在中间的位置。
		int returnValue = 1;
		int currPageNum = 1;
		
		try{
			currPageNum = new Integer(currentPage2).intValue();
		}
		catch(Exception e)
		{
			currPageNum = 1 ;
		}
		
		returnValue = currPageNum -middlePage ;
		if (returnValue < 1 )
			return 1 ;
		
		
		return returnValue;
		 
	}
	
	private String goPage(Page bean){
		StringBuffer sb = new StringBuffer();
		String readOnly = "";
		String butclick ="";
		if(bean.getTotalPage()<=1){
			readOnly = " readonly ";
		}else{
			butclick = "onclick=\"gopageSubmit(this)\"";
		}
		sb.append("<input type=\"button\" "+butclick+" class=\"butt\" value=\"GO\" /><span>页</span><input type=\"text\" value=\""+bean.getCurrentPage()+"\" "+readOnly+" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\" maxLength=\"4\" style=\"text-align:center;\" id=\"pagenumtext\" /><span>转到</span>");
//		sb.append("到<input type=\"text\"");
//		sb.append(" name=\"pageOrder\"");
//		sb.append(" id=\"pageOrder\"");
//		sb.append(" size=\"1\"");
//		sb.append(readOnly);
//		sb.append(" maxlength=\"3\"");
//		sb.append(" />页");
//		
//		sb.append("<input type='button'");
//		sb.append(" name='button'");
//
//		sb.append(" onclick=\"javascript:checkThenSubmit(\'" + "page" + "\',\'" + bean.getUrl()
//				+ "\',\'" + "formSearch" + "\',document.getElementById(\'pageOrder\').value,\'" + bean.getTotalPage() + "\')\"");
//		sb.append(" value='跳转'");
//		sb.append(" />");
//		sb.append("</form>");
		return sb.toString();
	}
	
	private String getHiddenInput(Page bean) {
		StringBuffer sb = new StringBuffer();
		/*sb.append("<input type='hidden'");
		sb.append(" value='" + bean.getCurrentPage() + "'");
		sb.append(" name='"+ "page" +".currentPage'");
		sb.append(" id='"+ "page" +".currentPage'");
		sb.append(" />");*/
		
		
		sb.append("<input type='hidden'");
		sb.append(" value='" + bean.getCurrentPage() + "'");
		sb.append(" name='currentPage'");
		sb.append(" id='currentPage'");
		sb.append(" />");
		
		sb.append("<input type='hidden'");
		sb.append(" value='" + bean.getTotalPage() + "'");
		sb.append(" name='totalPage'");
		sb.append(" id='totalpage'");
		sb.append(" />");
		
		String pageSize = bean.getPageSize()+"";
		if(pageSize != null)
			sb.append("<input type='hidden' name='pageSize' value='"+ pageSize +"' id='page.pageSize'/>");
		
		return sb.toString();
	}

	private String buildJS(int i, Page page) {

		return "pageSubmit('" + "page" + "','" + page.getUrl()
				+ "','" + "formSearch" + "','" + i
				+ "')";
	}

}
