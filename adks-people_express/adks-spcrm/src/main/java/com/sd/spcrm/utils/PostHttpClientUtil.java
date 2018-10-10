package com.sd.spcrm.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.adks.dubbo.api.data.course.CourseJson;
import com.adks.dubbo.api.data.course.HeartBeat;
import com.alibaba.fastjson.JSON;
import com.sd.spcrm.webconfig.WebConfigSpcrm;

public class PostHttpClientUtil {
	/** 
     * 默认编码为 UTF-8 
     */
	private static final String HTTP_CONTENT_CHARSET = "UTF-8"; 
	
    public static HttpClient httpClient = null;
    
    private static MultiThreadedHttpConnectionManager connectionManager = null;

	private static int connectionTimeOut = 30000;

	private static int socketTimeOut = 30000;

	private static int maxConnectionPerHost = 100;

	private static int maxTotalConnections = 100;
	
    static {  
    	connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
		connectionManager.getParams().setSoTimeout(socketTimeOut);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
		connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
		
		httpClient = new HttpClient(connectionManager);  
    }
    
    
    /**
	 * HttpClient
	 * 
	 * POST方式提交数据
	 * 
	 * @param url
	 * 			待请求的URL
	 * @param courseMap
	 * 			要提交的数据
	 * @return
	 * 			响应结果
	 * @throws IOException
	 * 			IO异常
	 */
    public static void sendCourseJsonMap(String url, Map<String, CourseJson> courseJsonMap) {  
    	
    	PostMethod postMethod = new PostMethod(url);  
        postMethod.addRequestHeader(new Header("Connection", "close"));  
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,HTTP_CONTENT_CHARSET);//编码设置
        
        try {
        	String str =JSON.toJSONString(courseJsonMap);
        	String sign = MD5EncryptSpcrm.encrypt(str + ":"+WebConfigSpcrm.studyKey);
        	postMethod.addRequestHeader("sign", sign);

        	//将map数据转换成json放入RequestEntity
        	RequestEntity requestEntity=new StringRequestEntity(str,"application/json",HTTP_CONTENT_CHARSET);
        	
        	postMethod.setRequestEntity(requestEntity);
        	
        	int statusCode = httpClient.executeMethod(postMethod);
            
        	//返回状态码不是200 马上断开连接  
            if (statusCode != HttpStatus.SC_OK) {
            	postMethod.abort();
			}   
        }catch(HttpException e){
        	
        	LogRecordAdks.error("HttpClient POST方式提交数据，发生致命的异常，可能是协议不对或者返回的内容有问题："+e);
        	e.printStackTrace();
		}catch(IOException e){
			
			LogRecordAdks.error("HttpClient POST方式提交数据，发生网络异常："+e);
			e.printStackTrace();
		}finally{
			
			if(postMethod != null){
				
				postMethod.releaseConnection();
				
				postMethod = null;
			}
        }
        
    }
    
    /**
	 * HttpClient
	 * 
	 * POST方式提交数据
	 * 
	 * @param url
	 * 			待请求的URL
	 * @param heartBeat
	 * 			要提交的数据
	 * @return
	 * 			响应结果
	 * @throws IOException
	 * 			IO异常
	 */
    public static void sendHeartPost(String url, HeartBeat heartBeat) {  
    	
    	PostMethod postMethod = new PostMethod(url);  
        postMethod.addRequestHeader(new Header("Connection", "close"));  
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,HTTP_CONTENT_CHARSET);//编码设置
        
        try {
        	String str =JSON.toJSONString(heartBeat);
        	String sign = MD5EncryptSpcrm.encrypt(str + ":"+WebConfigSpcrm.studyKey);
        	postMethod.addRequestHeader("sign", sign);
        	
        	//将heartBeat数据转换成json放入RequestEntity
        	RequestEntity requestEntity=new StringRequestEntity(JSON.toJSONString(heartBeat),"application/json",HTTP_CONTENT_CHARSET);
        	
        	postMethod.setRequestEntity(requestEntity);
        	
        	int statusCode = httpClient.executeMethod(postMethod);
            
        	//返回状态码不是200 马上断开连接  
            if (statusCode != HttpStatus.SC_OK) {
            	
            	postMethod.abort();
			}    
        }catch(HttpException e){
        	
        	LogRecordAdks.error("sendHeartPost，发生致命的异常，可能是协议不对或者返回的内容有问题："+e);
        	e.printStackTrace();
		}catch(IOException e){
			
			LogRecordAdks.error("sendHeartPost，发生网络异常："+e);
			e.printStackTrace();
		}finally{
			
			if(postMethod != null){
				
				postMethod.releaseConnection();
				
				postMethod = null;
			}
        }
        
    }
    
}
