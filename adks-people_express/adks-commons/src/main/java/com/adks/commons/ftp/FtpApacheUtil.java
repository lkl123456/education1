package com.adks.commons.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.adks.commons.util.PropertiesFactory;



/*
 * 
 * @author hqt
 * @since Jun 28, 2013 10:43:57 AM
 * @see apache ftp  工具类 
 * @version 1.0
 */
public class FtpApacheUtil {
	
	private static FTPClient ftpClient;
	
	public static String ip;
	
	public static int port; 
	
	private static String ENCODING_UTF8 =  "utf8";
	
	private static String userName; 
	
	private static String passWord;
	
	public final int BUFFER_SIZE = 204800; 
	
	private Logger logger = LoggerFactory.getLogger(FtpApacheUtil.class);
	
	/*public FtpApacheUtil() throws Exception {
		
	}*/
	
	@SuppressWarnings("static-access")
	public FtpApacheUtil(){
		this.ip = PropertiesFactory.getProperty("config.properties", "ftp.ip.video");
		this.port = Integer.parseInt(PropertiesFactory.getProperty("config.properties", "ftp.port.video"));
		this.userName = PropertiesFactory.getProperty("config.properties", "ftp.username.video");
		this.passWord = PropertiesFactory.getProperty("config.properties", "ftp.password.video");
		try {
			setClient();
			login();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @author hqt
	 * @since 9:58:14 AM
	 * @see 
	 */
	public  void setClient() throws Exception {
		
		//ftpClient = FtpClientFactory.getFtpApacheClient();
		
		try{
			if(null == ftpClient){
				ftpClient = new FTPClient();
			}
			ftpClient.connect(ip, port);
		}catch (Exception e){
			logger.error("Failed to get connection :" + e.getMessage());
		}
		
		
	}
	//gygy，仅仅用户三分屏的上传
	public  void scormUploadSan(String mainPath,String id,File file) throws Exception {
		String[] tempip = ip.split(",");
		String[] tempport = (port+"").split(",");
		String[] tempun = userName.split(",");
		String[] temppw = passWord.split(",");
		try {
			if(tempip!=null && tempip.length>0){
				setScormClient(tempip[0],Integer.parseInt(tempport[0]));
				scormLogin(tempun[0],temppw[0]);
				//createDir(mainPath);ferscdf
				FileInputStream fis = new FileInputStream(file);
				ftpClient.changeWorkingDirectory(mainPath);
				ftpClient.storeFile(id+".zip" , fis);
				logout();
			}
		} catch (Exception e) {
			logger.error("One more ftp error : "+e.getMessage());
		}
	}
	/**
	 * @author LLJ
	 * @since 2013-9-8 上午11:44:17
	 * @see 由于scorm课件可能与文件服务器不在同一台服务器上，单独连接。
	 */
	public  void scormUpload(String mainPath,String id,File file) throws Exception {
		String[] tempip = ip.split(",");
		String[] tempport = (port+"").split(",");
		String[] tempun = userName.split(",");
		String[] temppw = passWord.split(",");
		/*String[] tempip = "120.27.9.213".split(",");
		String[] tempport = ("89"+"").split(",");
		String[] tempun = "ulms".split(",");
		String[] temppw = "mhdyzxkt".split(",");*/
		try {
			if(tempip!=null && tempip.length>0){
				for(int i=0;i<tempip.length;i++){
					setScormClient(tempip[i],Integer.parseInt(tempport[i]));
					scormLogin(tempun[i],temppw[i]);
					createDir(mainPath);
					uploadDirectory(mainPath,id,file);
					logout();
				}				
			}
		} catch (Exception e) {
			logger.error("One more ftp error : "+e.getMessage());
		}
	}
	
	public void setScormClient(String scormip1,int scormport1) throws Exception {
		
		//ftpClient = FtpClientFactory.getFtpApacheClient();
		
		try{
			if(null == ftpClient){
				ftpClient = new FTPClient();
				//ftpClient.setAutodetectUTF8(true);
			}
			ftpClient.connect(scormip1, scormport1);
		}catch (Exception e){
			logger.error("Failed to get connection :" + e.getMessage());
		}
		
		
	}
	
	public void login() throws Exception{
		
		try{
			if(null == ftpClient){
				logger.warn(" ftp ftpApacheClient not initialization!");
				return;
			}
			boolean blogin = ftpClient.login(userName, passWord);
			
			if (!blogin) {
				ftpClient.disconnect();
				ftpClient = null;
				logger.error(" ftp login defined! ");
				return;
			}
		}catch(IOException e){
			logger.error("ftp login :"+ e.getMessage());
		}
		//FtpClientFactory.apacheLogin();
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding("GBK");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setBufferSize(BUFFER_SIZE);
		//ftpClient.setSendBufferSize(BUFFER_SIZE);
	}
	
	public void logout(){
		
		try{
			if(null != ftpClient && ftpClient.isConnected()){
				ftpClient.logout();
			}
		}catch(IOException e){
			logger.error("ftp logout :"+ e.getMessage());
		}finally{
			
			if (ftpClient.isConnected()) {
                try {
                	ftpClient.disconnect();
                } catch (IOException ioe) {
                	ioe.printStackTrace();
                }    
            }
			
		}
		//return flag;
		//FtpClientFactory.apacheLogout();
		
	}
	
	/*
	 * 
	 * @author hqt
	 * @param String mainPath FTP服务器上的主保存目录  
     * @param String dir 上传到FTP服务器上的文件夹名 对于 scrom课件 为 id   
     * @param File file 输入文件  
	 * @since Jun 29, 2013 2:53:16 PM
	 * @return 成功返回true，否则返回false 
	 * @see 向FTP服务器上传文件夹
	 *
	 */
	public void uploadDirectory(String mainPath,String id,String fileFullPath) throws Exception {
		
		   File file = new File(fileFullPath);
		
	       if(file.isDirectory()){
	        	
	    	    //System.out.println("current workspace: "+ ftpClient.printWorkingDirectory());
	         	String dir1 = getDir(mainPath,id,null,file.getAbsolutePath());
                //System.out.println("$$$$$$$$$$$$$$$$$"+dir1 +"file Name :"+ file.getName() +"workdirectory:%%%%%%%%%%"+ftpClient.printWorkingDirectory());
		        ftpClient.makeDirectory(dir1); 
		        ftpClient.changeWorkingDirectory(dir1);  ftpClient.printWorkingDirectory();  
		        String[] files = file.list();
		        for (int i = 0; i < files.length; i++) {     
		                File file1 = new File(file.getPath()+"\\"+files[i] );      
		                if(file1.isDirectory()){
		                	uploadDirectory(mainPath,id,file1);  
		                	ftpClient.changeToParentDirectory();      
		                }else{     
		                    File file2 = new File(file.getPath()+"\\"+files[i]);      
		                    FileInputStream input = new FileInputStream(file2); 
		                    //String dir = getDirFromFile(mainPath,id,file2.getName(),file2.getAbsolutePath());
		                    String dir = getDir(mainPath,id,file2.getName(),file2.getAbsolutePath());
				        	ftpClient.makeDirectory(dir);                
				        	ftpClient.changeWorkingDirectory(dir);ftpClient.printWorkingDirectory();
				        	
				        	ftpClient.setCopyStreamListener(new FTPProcess(file.length(), System.currentTimeMillis()));
				        	
				        	ftpClient.storeFile( new String(file2.getName().getBytes("GBK"),"iso-8859-1"), input); 
		                    input.close();   
		                }      
		            }      
		        }else{
		            File file2 = new File(file.getPath());      
		            FileInputStream input = new FileInputStream(file2); 
		            String dir = getDir(mainPath,id,file2.getName(),file2.getAbsolutePath());
		        	ftpClient.makeDirectory(dir);                
		        	ftpClient.changeWorkingDirectory(dir);
		        	//System.out.println("workdirectory:"+dir+" file name :"+ file2.getName()+"workdirectory:%%%%%%%%%%"+ftpClient.printWorkingDirectory());
		            ftpClient.storeFile( new String(file2.getName().getBytes("GBK"),"iso-8859-1"), input);      
		            input.close();        
		        }
		        
	}
	
	/*
	 * 
	 * @author hqt
	 * @param String mainPath FTP服务器上的主保存目录  
     * @param String dir 上传到FTP服务器上的文件夹名 对于 scrom课件 为 id   
     * @param File file 输入文件  
	 * @since Jun 29, 2013 2:53:16 PM
	 * @return 成功返回true，否则返回false 
	 * @see 向FTP服务器上传文件夹
	 *
	 */
	public void uploadDirectory(String mainPath,String id,File file) throws Exception {
		
		
		
	       if(file.isDirectory()){
	        	
	    	    //System.out.println("current workspace: "+ ftpClient.printWorkingDirectory());
	         	String dir1 = getDir(mainPath,id,null,file.getAbsolutePath());
                //System.out.println("$$$$$$$$$$$$$$$$$"+dir1 +"file Name :"+ file.getName() +"workdirectory:%%%%%%%%%%"+ftpClient.printWorkingDirectory());
		        ftpClient.makeDirectory(dir1); 
		        ftpClient.changeWorkingDirectory(dir1);  ftpClient.printWorkingDirectory();  
		        String[] files = file.list();
		        for (int i = 0; i < files.length; i++) {     
		                File file1 = new File(file.getPath()+"\\"+files[i] );      
		                if(file1.isDirectory()){
		                	uploadDirectory(mainPath,id,file1);  
		                	ftpClient.changeToParentDirectory();      
		                }else{     
		                    File file2 = new File(file.getPath()+"\\"+files[i]);      
		                    FileInputStream input = new FileInputStream(file2); 
		                    //String dir = getDirFromFile(mainPath,id,file2.getName(),file2.getAbsolutePath());
		                    String dir = getDir(mainPath,id,file2.getName(),file2.getAbsolutePath());
				        	ftpClient.makeDirectory(dir);                
				        	ftpClient.changeWorkingDirectory(dir);ftpClient.printWorkingDirectory();
				        	System.out.println("workdirectory:"+dir+ "file name :"+file2.getName()+"workdirectory:$$$$$$$$" +ftpClient.printWorkingDirectory());
		                    ftpClient.storeFile( new String(file2.getName().getBytes("GBK"),"iso-8859-1"), input); 
		                    input.close();   
		                }      
		            }      
		        }else{
		            File file2 = new File(file.getPath());      
		            FileInputStream input = new FileInputStream(file2); 
		            String dir = getDir(mainPath,id,file2.getName(),file2.getAbsolutePath());
		        	ftpClient.makeDirectory(dir);                
		        	ftpClient.changeWorkingDirectory(dir);
		        	//System.out.println("workdirectory:"+dir+" file name :"+ file2.getName()+"workdirectory:%%%%%%%%%%"+ftpClient.printWorkingDirectory());
		            ftpClient.storeFile( new String(file2.getName().getBytes("GBK"),"iso-8859-1"), input);      
		            input.close();        
		        }
		        
	}
	
	

    /**
     * 
     * @author hqt
     * @since Jun 28, 2013 9:08:36 AM
     * @see 往ftp 上传单个文件
     */
    public boolean uploadFile( String path, String filename, InputStream input) {
    	
        boolean flag = false;    
        try {
        	
        	ftpClient.makeDirectory(path);
        	ftpClient.changeWorkingDirectory(path);
        	//System.out.println(ftpClient.printWorkingDirectory());
        	ftpClient.setCopyStreamListener(new FTPProcess(input.available(), System.currentTimeMillis()));
        	ftpClient.storeFile(new String(filename.getBytes("GBK"),"iso-8859-1"), input);
            input.close();
            ftpClient.logout();
            flag = true;
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()){
                try {
                	ftpClient.disconnect();
                } catch (IOException ioe) {
                	
                }    
            }
        }    
        return flag;    
    }
    
    /**
     * 
     * @author hqt
     * @since Jun 28, 2013 9:08:36 AM
     * @see 往ftp 上传单个文件
     */
    public boolean uploadFile( String path, String filename, String fileFullPath) {
    	
        boolean flag = false;    
        try {
        	FileInputStream input = new FileInputStream(fileFullPath);
        	ftpClient.makeDirectory(path);
        	ftpClient.changeWorkingDirectory(path);
        	ftpClient.setCopyStreamListener(new FTPProcess(new File(fileFullPath).length(), System.currentTimeMillis()));
        	ftpClient.storeFile(new String(filename.getBytes("GBK"),"iso-8859-1"), input);
            input.close();
            ftpClient.logout();
            flag = true;
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()){
                try {
                	ftpClient.disconnect();
                } catch (IOException ioe) {
                	ioe.printStackTrace();
                }
            }
        }
        return flag;
    }
    
    /**
     * FTP上传单个文件测试
     */
    public static void testUpload() {
        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null ;
        try {
            ftpClient.connect("120.27.9.213",89 );
            ftpClient.login("ulms", "mhdyzxkt" );

            File srcFile = new File("F://tools//apache-tomcat-7.0.75//webapps//adks-admin//tempUploadDir//QS2093486437832.zip" );
            fis = new FileInputStream(srcFile);
            //设置上传目录
            
            ftpClient.changeWorkingDirectory("/");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile("QS2093486437832.zip" , fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！" , e);
        } finally {
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！" , e);
            }
        }
    }

    /**
     * FTP下载单个文件测试
     */
    public static void testDownload() {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null ;

        try {
            ftpClient.connect("192.168.1.137" );
            ftpClient.login("ftp" , "ftp" );

            String remoteFileName = "/admin/pic/3.gif" ;
            fos = new FileOutputStream("c:/down.gif" );

            ftpClient.setBufferSize(1024);
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(remoteFileName, fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！" , e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！" , e);
            }
        }
    } 
    
    public void scormLogin(String scormUserName1,String scormPassWord1) throws Exception{
		try{
			if(null == ftpClient){
				logger.warn(" ftp ftpApacheClient not initialization!");
				return;
			}
			boolean blogin = ftpClient.login(scormUserName1, scormPassWord1);
			if (!blogin) {
				ftpClient.disconnect();
				ftpClient = null;
				logger.error(" ftp login defined! ");
				return;
			}
		}catch(IOException e){
			logger.error("ftp login :"+ e.getMessage());
		}
		//FtpClientFactory.apacheLogin();
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		//ftpClient.setControlEncoding("GBK");
		ftpClient.setControlEncoding(ENCODING_UTF8);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setBufferSize(BUFFER_SIZE);
		ftpClient.setSendBufferSize(BUFFER_SIZE);
		
	}
	 
	 /*
	 * 
	 * @author hqt
	 * @param String mainPath 多级的目录 如: /photo/user
	 * @since Jun 29, 2013 3:06:41 PM
	 * @see 创建目录 
	 *
	 */
	public String createDir(String mainPath) throws Exception{
		 
 
		 StringTokenizer dirs = new StringTokenizer(mainPath, "/"); 
		 String pathName = "";
		 while (dirs.hasMoreElements()) {
			 
			 String dir = (String) dirs.nextElement();
			 ftpClient.makeDirectory(dir);                
			 ftpClient.changeWorkingDirectory(dir);
			 //System.out.println("current workspace: "+ ftpClient.printWorkingDirectory());
			 
		 }
		 return pathName;
	 }
	 
	 /*
	 * 
	 * @author hqt
	 * @since Jun 29, 2013 3:08:12 PM
	 * @see 获取 本级要上传的多级目录的层次
	 *
	 */
	public static String getDir(String mainPath,String id,String fileName,String path){

		path = path.replace("\\","/");
		 StringTokenizer dirs = new StringTokenizer(path, "/"); 
		 String pathName = "";
		 int t = 0;//计数器
		 int m = 0;//判断器
		 while (dirs.hasMoreElements()) {
			 
			 String dir = (String) dirs.nextElement();
			 
			 if(!StringUtils.isEmpty(dir) && dir.equals(id)){
				 m =t;
				 pathName = mainPath + "/"+id ;
			 }
			 //如果是文件夹路径过来，只返回文件夹的相关路径
			 if(t >m && m>0 && StringUtils.isEmpty(fileName) ){
				 pathName = pathName + "/" + dir + "/"; 
			 }
			 
			 //如果是文件路径过来，只返回文件夹的相关路径 ，要把文件名过滤掉
			 if(t >m && m>0 && !StringUtils.isEmpty(fileName)&& !fileName.equals(dir)){
				 pathName = pathName + "/" + dir + "/"; 
			 }
			 
			 t = t+1;
		 }
		 return pathName;
	 }
	 
	 @SuppressWarnings("deprecation")
	public void test1() throws Exception {
			// String strTemp = "";
			// InetAddress ia = InetAddress.getByName("192.168.0.193"); 
			FTPClient ftp = new FTPClient(); 
			ftp.connect("192.168.1.137",21 );
			boolean blogin = ftp.login("ftp", "ftp");
			if (!blogin) {
				System.out.println("测试");
				ftp.disconnect();
				ftp = null;
				return;
			}
			/*
			 *  boolean bMakeFlag = ftp.makeDirectory(new
			 * String("中共中央政治局常委".getBytes( "gb2312"), "iso-8859-1")); 
			 * //File file = new File("c:\\test.properties");
			 * ftp.storeFile("test.properties",new FileInputStream(file));
			 */
			System.out.println(ftp.getSystemName());
			FTPFile[] ftpFiles = ftp.listFiles();
			if (ftpFiles != null){
				for (int i = 0; i < ftpFiles.length; i++) {
					System.out.println(ftpFiles[i].getName()); 
					// System.out.println(ftpFiles[i].isFile());
					// if(ftpFiles[i].isFile()){
					//FTPFile ftpf = new FTPFile();
					 /*System.err.println(ftpf.hasPermission(FTPFile.GROUP_ACCESS,
					 FTPFile.EXECUTE_PERMISSION));
					 System.err.println("READ_PERMISSION="+ftpf.hasPermission(FTPFile.USER_ACCESS,
					 FTPFile.READ_PERMISSION));
					 System.err.println("EXECUTE_PERMISSION="+ftpf.hasPermission(FTPFile.USER_ACCESS,
					 FTPFile.EXECUTE_PERMISSION));
					 System.err.println("WRITE_PERMISSION="+ftpf.hasPermission(FTPFile.USER_ACCESS,
					 FTPFile.WRITE_PERMISSION));
					 System.err.println(ftpf.hasPermission(FTPFile.WORLD_ACCESS,
					 FTPFile.READ_PERMISSION));*/
				}
				// System.out.println(ftpFiles[i].getUser());
			}
		}
	 
	/*
	 * 
	 * @param args String[]
	 * 
	 */
	public static void main(String[] args) {
		
		testUpload();
		
	}
	
	
}
