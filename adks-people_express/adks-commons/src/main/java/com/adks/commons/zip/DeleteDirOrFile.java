package com.adks.commons.zip;

import java.io.File;
/**
 * 
 * ClassName DeleteDir
 * @Description:
 * @author xrl
 * @Date 2017年3月31日
 */
public class DeleteDirOrFile{
    /**
     * 
     * @Title deleteDir
     * @Description：递归删除目录下的所有文件及子目录下所有文件（删除上传到服务器的解压完的三分屏文件）
     * @author xrl
     * @Date 2017年3月24日
     * @param dir  将要删除的文件目录
     * @return
     */
    public synchronized boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {  //递归删除目录中的子目录下
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    /**
	 * 
	 * @Title doDeleteEmptyDir
	 * @Description:删除文件
	 * @author xrl
	 * @Date 2017年3月24日
	 * @param dir  将要删除的目录路径
	 */
    public synchronized boolean deleteEmptyDir(String dir) {
    	File file=new File(dir);
    	boolean success=false;
    	if(file.exists()){
    		success = (file).delete();
    	}
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
        return success;
    }
    
}
