package com.sd.spcrm.ftp.Execute;
import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import com.sd.spcrm.ftp.util.ConfigReaderUtil;


public class ZipToFile  extends  Thread{
	
	private static String Scrom_Path ;//scrom zip视频文件存放位置
	private static String Un_Zip_Path;//解压的文件路径
	private static final int Queue_Szie = 100;
	private final BlockingQueue<File> Queue = new ArrayBlockingQueue<File>(Queue_Szie);
	private static int Un_Zip_ThreadNum;//解压文件线程数，服务器上可做修改
	private static boolean isDaemon = true;
	private static int initialDelay;
	private static int delay;
	public static File jsFile;//原js文件
	private static Logger log = Logger.getLogger("ZipToFile.class");
	private ZipToFile(){
		
	}
	static{
		Scrom_Path = ConfigReaderUtil.getScromPath();
		Un_Zip_Path = ConfigReaderUtil.getUnZipPath();
		Un_Zip_ThreadNum = ConfigReaderUtil.getUnZipThreadNum();
		initialDelay = ConfigReaderUtil.getInitialDelay();
		delay = ConfigReaderUtil.getDelay();
	}
	
 
	public static void execute(ServletContext context ) {
		log.info("============tomcate启动运行=====================");
		jsFile = new File(context.getRealPath("/")+"js","kernel.js");
		ScheduledThreadPoolExecutor executor =  new  ScheduledThreadPoolExecutor(Un_Zip_ThreadNum +1);
		executor.scheduleWithFixedDelay(new ZipToFile(), initialDelay, delay, TimeUnit.SECONDS); 
	}
	
	@Override
	public void run() {
		this.setDaemon(isDaemon);
		int threadNum = Un_Zip_ThreadNum +1;
		CountDownLatch latch=new CountDownLatch(threadNum); 
		long start = System.currentTimeMillis();
		new Thread(new FileEnumerationTask(Queue,new File(Scrom_Path),latch)).start();
		for(int i = 0;i<Un_Zip_ThreadNum;i++){
			new Thread(new UnZipFileTask(Queue,Un_Zip_Path,latch,"UnZipFileTask"+i)).start();
		}
		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		log.info("===================use: "+(end-start)+"ms");
	}
	
}

