package com.adks.commons.zip;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class UnZipFileTask extends Thread {
	private static final int Buffer= 1024 ;//缓存大小
	private BlockingQueue<File> queue;
	private String un_zip_path; 
	private CountDownLatch latch;
	private String name;
	private static Logger log = Logger.getLogger("UnZipFileTask.class");
	public UnZipFileTask(BlockingQueue<File> queue,String un_zip_path,CountDownLatch latch,String name) {
		this.queue = queue;
		this.un_zip_path = un_zip_path;
		this.latch = latch;
		this.name = name;
		super.setName(this.name);
	}

	public void run() {
		try {
			upZipFile();
			this.latch.countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 
	 * 解压缩
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void upZipFile( ) throws Exception {
		boolean done = false;
		byte[] buf = new byte[Buffer];
		while(!done){
			File ze = queue.take();
			if(ze == FileEnumerationTask.File_Dummy){
				queue.put(ze);
				done = true;
			}else{
				ZipFile zfile = null;
				List<File> jsList = new ArrayList<File>();
				boolean result = true;
				try{
					zfile = new ZipFile(ze);
					String zfileStr = zfile.getName().substring(0,zfile.getName().lastIndexOf("."));
					String[] tempZipFileStr = zfileStr.split("\\"+File.separator);
					String fileStr = "";
					if(tempZipFileStr.length>1){
						fileStr = tempZipFileStr[tempZipFileStr.length -1]; 
					}
					File createZFile = new File(zfileStr);
					if(!createZFile.exists()){
						createZFile.mkdir();
					}
					Enumeration zList=zfile.entries();
					ZipEntry zipEntry=null;
					while(zList.hasMoreElements()){
						OutputStream os  = null;
						InputStream is = null;
						try{
							zipEntry=(ZipEntry)zList.nextElement();		
							if(zipEntry.isDirectory()){
								File f=new File(this.un_zip_path+File.separator+fileStr+File.separator+zipEntry.getName());
								f.mkdir();
								continue;
							}
							File  zipFileRealPath = getRealFileName(this.un_zip_path+File.separator+fileStr,zipEntry.getName());
							String[] pathStr = zipEntry.getName().split("/");
							if("kernel.js".equals(pathStr[pathStr.length-1])){
								jsList.add(zipFileRealPath.getParentFile());
							}else{
								os=new BufferedOutputStream(new FileOutputStream(zipFileRealPath));
								is=new BufferedInputStream(zfile.getInputStream(zipEntry));
								int readLen=0;
								while ((readLen=is.read(buf, 0, Buffer))!=-1) {
									os.write(buf, 0, readLen);
								}
							}
						}catch (Exception e) {
							result = false;
							e.printStackTrace();
						}finally{
							if(null != os){
								is.close();
							}
							if(null != is){
								os.close();
							} 
						}
					}
					if(jsList.size()>0){
						for(int i = 0;i<jsList.size();i++){
							result = replaceFile( ZipToFile.jsFile,jsList.get(i),"kernel.js");
							if(!result){
								break;
							}
						}
					}
				}catch (Exception e) {
					result = false;
					e.printStackTrace();
				}finally{
					if(null != zfile){
						zfile.close();
					}
					if(result){
						delZipFile(ze);
					}else{
						log.info("***********************exception "+ ze.getName());
					}
				}
			}
		}
	}
	
	/**
	 * 替换课件文件夹下所有的文件kernel.js
	 * 
	 * @return
	 */
	private static boolean replaceFile (File srcFile, File destDir, String newFileName) {
		long copySizes = 0;
		if (!srcFile.exists()) {
			log.info("源文件不存在");
			copySizes = -1;
		} else if (!destDir.exists()) {
			log.info("目标目录不存在");
			copySizes = -1;
		} else if (newFileName == null) {
			log.info("文件名为null");
			copySizes = -1;
		} else {
			try {
				FileChannel fcin = new FileInputStream(srcFile).getChannel();
				FileChannel fcout = new FileOutputStream(new File(destDir,newFileName)).getChannel();
				long size = fcin.size();
				fcin.transferTo(0, fcin.size(), fcout);
				fcin.close();
				fcout.close();
				copySizes = size;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return copySizes>0?true:false;
	}
	

	/** 
	 * 给定根目录，返回相对路径的完整路径 
	 * @param baseDir 根目录
	 * @param absFileName 相对路径名，来自于ZipEntry中的name 
	 * @return java.io.File 实际文件名
	 */
	private static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
			if (!ret.exists())
				ret.mkdirs();
			ret = new File(ret, dirs[dirs.length - 1]);
			return ret;
		}
		ret = new File(baseDir,absFileName);
		return ret;
	}
	
	/**
	 * 删除原有的zip文件
	 */
	private static void delZipFile(File zipFile){
		zipFile.delete();
	}

}
