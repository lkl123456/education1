package com.adks.commons.ftp;


import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;


/**
 * FTP传输进度  监听
 * @author Administrator
 *
 */
public class FTPProcess implements CopyStreamListener {
	private long fileSize;  //传输的文件大小
	private long startTime; //开始传输时的时间
	public static  long processNum;
	
	public static long getProcessNum() {
		return processNum;
	}

	public static void setProcessNum(long processNum) {
		FTPProcess.processNum = processNum;
	}

	public FTPProcess(long fileSize,long startTime){
		this.fileSize = fileSize;
		this.startTime = startTime;
	}
	
	@Override
	public void bytesTransferred(CopyStreamEvent event) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 本次传输了多少字节
	 * @param totalBytesTransferred 到目前为止已经传输了多少字节
	 * @param bytesTransferred 本次传输的字节数
	 * @param streamSize The number of bytes in the stream being copied
	 */
	//每次传输 bytesTransferred 字节时，就会调用 new FTPProcess(fileSize,startTime) 这个对象的 bytesTransferred 方法
	//另外，如果FTP传输速度特别慢，请设置一下该参数，就会大大提高传输速度 ftpClient.setBufferSize(100000);
	@Override
	public void bytesTransferred(long totalBytesTransferred,int bytesTransferred, long streamSize) {
		 	long end_time = System.currentTimeMillis();
	        long time = (end_time - startTime) / 1000; // 耗时多长时间
	        long speed; //速度
	        if (0 == time){
	            speed = 0;
	        } else {
	            speed = totalBytesTransferred/1024/time;
	        }
	        System.out.printf("\r    %d%%   %d  %dKB/s  %d   %ds",
	                totalBytesTransferred * 100 /fileSize,totalBytesTransferred,speed,fileSize,time);
	        System.out.println("进度："+totalBytesTransferred * 100 /fileSize+"%");
	        System.out.println("速度："+speed+"KB/s");
	        System.out.println("已耗时："+time+"s");
	        processNum = totalBytesTransferred * 100 /fileSize;
	}

}
