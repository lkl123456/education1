package com.adks.commons.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理工具类
 * @author hqt
 * @since 2012-08-30
 */
public class FileUtil {

	//定义变量
	public static final int BUFFER_SIZE = 1024;
	
	/**
	 * 拷贝文件
	 * @param in 源文件，包括整个文件实体
	 * @param out 目的文件，该文件对象只有文件路径与文件名
	 * @throws IOException 
	 * @throws IOException 如果发生输入输出错误，如无源文件、路径错误等
	 */
	public static void copyFile(File in, File out) throws IOException {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[BUFFER_SIZE];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}
	
	/**
	 * 读取文件 返回字符串
	 * @param in strFileName 文件路径
	 * @param out 文件的字符串
	 * @throws IOException 
	 * @throws IOException 如果发生输入输出错误，如无源文件、路径错误等
	 */
	public static String readFile(String strFileName) {
	    String fileStr = "";
	    try {
	      BufferedReader in = new BufferedReader(new FileReader(strFileName));
	      in.readLine();
	      String s = in.readLine();
	      while (s != null) {
	        fileStr = fileStr + s;
	        s = in.readLine();
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	      return null;
	    }
	    return fileStr.length() == 0 ? null : fileStr;
	  }

	/**
	 * 将文件转换为字符串
	 * @param in 源文件，包括整个文件实体
	 * @throws IOException 如果发生输入输出错误，如无源文件、路径错误等
	 */
	public static String fileToStr(File in) throws IOException {
		FileInputStream fis = new FileInputStream(in);
		String outStr = null;
		byte[] buf = new byte[BUFFER_SIZE];
		@SuppressWarnings("unused")
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			outStr += new String(buf, "utf-8");
		}
		fis.close();
		return outStr;
	}

	/**
	 * 将文件转换为字符串
	 * @param in 源文件的文件名，包括文件的完整路径
	 * @throws IOException 如果发生输入输出错误，如无源文件、路径错误等
	 */
	public static String fileToStr(String in) throws IOException {
		return fileToStr(new File(in));
	}

	/**
	 * 将文件内容存放到集合中
	 * @param fileName 文件名
	 * @return 返回一个集合
	 * @throws IOException 如果读文件出错时
	 * 将文件的每一行做为集合的数据项
	 */
	@SuppressWarnings("rawtypes")
	public static List fileToList(String fileName) throws IOException {
		File file = new File(fileName);
		return fileToList(file);
	}

	/**
	 * 将文件内容存放到集合中
	 * @param file 文件对象
	 * @return 返回一个集合
	 * @throws IOException 如果读文件出错时
	 * 将文件的每一行做为集合的数据项
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List fileToList(File file) throws IOException {
		List list = new ArrayList();
		String read;
        FileReader fileread=new FileReader(file);
        BufferedReader bufread=new BufferedReader(fileread);
        while((read=bufread.readLine())!=null)
        {
          if(read.equals("")) continue;
          	list.add(read);
        }
        return list;

	}
	
	/*
	 * 
	 * @author hqt
	 * @since Jun 30, 2013 1:13:26 PM
	 * @param str /photo/user/1372568534984.jpg
	 * @see 
	 *
	 */
	public static String  getFileNameFromString(String str){
		
		String[] t = str.split("/");
		if(null != t && t.length>0){
			return t[t.length-1];
		}
		return null;
	}
	
/*	
	public static String replaceFile(String inFile, String resource, String target) throws IOException{
		String fileStr = fileToStr(inFile);
		String result = fileStr.replaceAll("("+resource+")", target);
		return result;
	}
	
	public static String replaceAndAddFile(String inFile, String resource, String target, String addContext) throws IOException{
		String fileStr = replaceFile(inFile, resource, target);
		return fileStr + addContext;
	}
	
	public static void StringToFile(String str, File out) throws IOException{
		FileOutputStream fos = new FileOutputStream(out);
		fos.write(str.getBytes("utf-8"));
		fos.close();
	}
	*/
	
	public static void main(String args[]){
		String s = "/photo/user/1372568534984.jpg";
		String t = getFileNameFromString(s);
		System.out.println(t);
		
	}
}