package com.zhang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zh
 *  
 */
public class ZipUtil {

	/**
	 * 将多个文件压缩
	 * @param zipName 生成的zip名字 		如   ys.zip
	 * @param zipPath 生成的zip目录名 		如   E:\\
	 * @param list    需要打包的文件列表                  如
	 *  List<File> list = new ArrayList<File>();
	 *	list.add(new File("E:\\企业基本信息_20150820205319.xlsx"));
	 *	list.add(new File("E:\\企业基本信息_20150820202522.xlsx"));
	 */
	public  static void createZip(String zipName, String zipPath,List<File> list) {
	    File dir = new File(zipPath);
	    File zipFile = new File(zipPath+zipName);
	    InputStream input = null;
        try {
	        //ZipOutputStream:此类为以 ZIP 文件格式写入文件实现输出流过滤器。包括对已压缩和未压缩条目的支持。
		    ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		    if(dir.isDirectory()) {
		        for (int i = 0; i < list.size(); ++i) {
		            input = new FileInputStream(list.get(i));
		            zipOut.putNextEntry(new ZipEntry( dir.getName()+ list.get(i).getName()));
		            /*putNextEntry(ZipEntry e):
					      开始写入新的 ZIP 文件条目并将流定位到条目数据的开始处。如果仍处于活动状态，则关闭当前条目。
					       如果没有为条目指定压缩方法，则使用默认的压缩方法；如果没有为条目设置修改时间，则使用当前时间。 
		              ZipEntry(String name) 
		                                          使用指定名称创建新的 ZIP 条目。
		              ZipEntry:此类用于表示 ZIP 文件条目。 (文件条目：个人理解为zip压缩文件中的每一个文件)*/
		            int temp = 0;
		            while ((temp = input.read()) != -1) {
		                zipOut.write(temp);
		            }
		            input.close();
		        }
		    }
		    zipOut.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
 	}
}
