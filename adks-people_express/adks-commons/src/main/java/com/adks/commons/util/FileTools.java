package com.adks.commons.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class FileTools
{
  public static void removeFile(String filePath)
  {
    File path = new File(filePath);

    if (!(path.exists()))
      return;
    System.out.println("removing file " + path.getPath());
    if (path.isDirectory()) {
      File[] child = path.listFiles();
      if ((child != null) && (child.length != 0)) {
        for (int i = 0; i < child.length; ++i) {
          removeFile(child[i]);
          child[i].delete();
        }
      }
    }
    path.delete();
  }

  public static void removeFile(File path)
  {
    System.out.println("removing file " + path.getPath());
    if (!(path.exists()))
      return;
    if (path.isDirectory()) {
      File[] child = path.listFiles();
      if ((child != null) && (child.length != 0)) {
        for (int i = 0; i < child.length; ++i) {
          removeFile(child[i]);
        }
      }

      path.delete();
    }

    path.delete();
  }

  public static void removeSonFile(File path)
  {
    System.out.println("removing son file and directory of the path " + 
      path.getPath());
    if (!(path.exists()))
      return;
    if (path.isDirectory()) {
      File[] child = path.listFiles();
      if ((child != null) && (child.length != 0)) {
        for (int i = 0; i < child.length; ++i)
          removeFile(child[i]);
      }
    }
    else
    {
      path.delete();
    }
  }

  public static void copy(File source, File target)
  {
    File tarpath = new File(target, source.getName());
    if (source.isDirectory()) {
      tarpath.mkdir();
      File[] dir = source.listFiles();
      for (int i = 0; i < dir.length; ++i)
        copy(dir[i], tarpath);
    }
    else {
      try {
        InputStream is = new FileInputStream(source);
        OutputStream os = new FileOutputStream(tarpath);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = is.read(buf)) != -1) {
          os.write(buf, 0, len);
        }
        is.close();
        os.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void copySonFiles(File sourcedir, File targetdir)
  {
    if (!(targetdir.exists())) {
      targetdir.mkdirs();
    }
    if (!(sourcedir.isDirectory()))
      return;
    File[] dir = sourcedir.listFiles();
    for (int i = 0; i < dir.length; ++i)
      copy(dir[i], targetdir);
  }

 /* public static File getCMIDataDir()
  {
    String strTempCMIDir = ComUtil.delNUllString(Scorm12CourseImportServlet.DefaultCMIDataDir);
    File fCMIDataDir = null;

    if (strTempCMIDir.length() > 1) {
      if (URLTools.isLocalAbsoluteURL(strTempCMIDir))
      {
        strTempCMIDir = URLTools.getLocalAbsoluteURL(strTempCMIDir);
        System.out.println("存储CMI数据的本地绝对路径是：" + strTempCMIDir);

        fCMIDataDir = new File(strTempCMIDir);
        if (!(fCMIDataDir.exists())) {
          fCMIDataDir.mkdirs();
        }

        return fCMIDataDir;
      }

      String strWebAbsoluteURL = Scorm12CourseImportServlet.theWebRootPath + strTempCMIDir;

      fCMIDataDir = new File(strWebAbsoluteURL);
      if (!(fCMIDataDir.exists())) {
        fCMIDataDir.mkdirs();
      }

      return fCMIDataDir;
    }

    return fCMIDataDir;
  }*/
}