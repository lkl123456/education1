package com.sd.spcrm.utils;
import org.apache.log4j.Logger;  

public class LogRecordAdks {  
    
	private static Logger info = Logger.getLogger("InfoLogger");  
    
	private static Logger error = Logger.getLogger("ErrorLogger");  
    
    public LogRecordAdks(){}  
      
    /** 
     * 一般情况记录到/logs/info/infoLog.txt
     */  
    public static void info(String infomation){  
       
    	info.info(infomation);  
        
    }  
      
    /** 
     * 错误信息记录到/logs/error/errorLog.txt 
     */  
    public static void error(String errorinfomation){  
       
    	error.error(errorinfomation);
        
    }  
} 

