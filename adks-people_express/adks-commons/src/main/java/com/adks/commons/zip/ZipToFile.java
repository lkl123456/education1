package com.adks.commons.zip;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 
 * ClassName ZipToFile
 * @Description：三分屏课件解压   删除解压文件
 * @author xrl
 * @Date 2017年3月24日
 */
public class ZipToFile  extends  Thread{
	
	private static final int Queue_Szie = 100;
	private static final BlockingQueue<File> Queue = new ArrayBlockingQueue<File>(Queue_Szie);
	private static int Un_Zip_ThreadNum;//解压文件线程数，服务器上可做修改
	public static File jsFile;//原js文件
	private static Logger log = Logger.getLogger("ZipToFile.class");
	private ZipToFile(){
		
	}
	
	static{
		Un_Zip_ThreadNum =4;
	}
 
	/**
	 * 
	 * @Title zipToFile
	 * @Description:解压zip
	 * @author xrl
	 * @Date 2017年3月24日
	 * @param code   上传压缩包返回的Code
	 * @param Zip_Path   压缩文件地址
	 * @param Un_Zip_Path   解压文件地址
	 * @return
	 */
	public static synchronized boolean zipToFile(String code,String Zip_Path,String UnZip_Path){
		log.info("============tomcate启动运行=====================");
		//拼接压缩包地址
		String path=Zip_Path+code;
		System.out.println("==============ZipPath:"+path);
		File file=new File(path+".zip");
		boolean success=false;
		if(file.exists()){
			int threadNum = Un_Zip_ThreadNum +1;
			CountDownLatch latch=new CountDownLatch(threadNum); 
			long start = System.currentTimeMillis();
			new Thread(new FileEnumerationTask(Queue,new File(Zip_Path),latch)).start();
			for(int i = 0;i<Un_Zip_ThreadNum;i++){
				new Thread(new UnZipFileTask(Queue,UnZip_Path,latch,"UnZipFileTask"+i)).start();
			}
			try {
				latch.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			success=true;
			long end = System.currentTimeMillis();
			log.info("===================use: "+(end-start)+"ms");
		}else{
			log.info("===============================压缩文件不存在！");
		}
		
		return success;
	}
	
}

