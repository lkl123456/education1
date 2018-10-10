package com.adks.commons.zip;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class FileEnumerationTask  implements Runnable{
	private static final String  Zip_Type = "zip";
	public static final File File_Dummy = new File("");
	private BlockingQueue<File> queue; 
	private File startingDirectory;
	private CountDownLatch latch;
	private static Logger log = Logger.getLogger("FileEnumerationTask.class");
	public FileEnumerationTask(BlockingQueue<File> queue,File startingDirectory,CountDownLatch latch){
		this.queue = queue;
		this.startingDirectory = startingDirectory;
		this.latch = latch;
	}
	
	public void run() {
		try {
			enumerate();
			this.latch.countDown();
		} catch (Exception e) {
		}
		
	}
	
	public void enumerate()throws InterruptedException{  
        File[] files = this.startingDirectory.listFiles(new ZipFilter());
        if(this.queue.size()>0){
        	this.queue.clear();
        }
        for (File file : files) { 
        	this.queue.put(file);  
        }
        log.info("========================= total fiel size: "+this.queue.size());
        this.queue.add(File_Dummy);
    } 
	
	
	class ZipFilter implements FileFilter{
    	public boolean accept(File pathname) {
    		boolean result = false;
    		String name = pathname.getName();
    		int i = name.lastIndexOf(".");
    		if(i>0 && i<name.length()-1){
    			if(Zip_Type.equals(name.substring(i+1).toLowerCase())){
    				result = true;
    			}else{
    				result = false;
    			};
    		}
    		return result;
    	}
    }

}
