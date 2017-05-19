package com.zhang.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来操作ftp的综合类。<br/>
 * 主要依赖jar包commons-net-3.1.jar。
 * 
 */
public class FtpUtil {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// ftp 地址
	private String host;
	// ftp端口
	private int port = 21;
	// 用户名
	private String userName;
	// 密码
	private String password;

	
	public FtpUtil(String host, String userName, String password) {
		this.host = host;
		this.userName = userName;
		this.password = password;
	}
	
	public FtpUtil(String host, int port, String userName, String password) {
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	
	/**
	 * 上传文件到FTP
	 * @param filePath	本地文件路径，包含文件名
	 * @param remoteDir	上传到FTP的目录名称，没有文件名，默认上传FTP的文件名称就是本地文件名
	 */
	public void upload(String filePath, String remoteDir) {
		File file = new File(filePath);
		if (!file.exists()) {
			logger.error("上传的文件不存在：" + filePath);
			return;
		}

		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(host, port);
			ftp.login(userName, password);
			ftp.changeWorkingDirectory(remoteDir);

			FileInputStream inputStream = new FileInputStream(file);
			ftp.storeFile(file.getName(), inputStream);

			inputStream.close();
			ftp.logout();

		} catch (Exception e) {
			logger.error("ftp文件上传错误！", e);
		}
	}

	
	
	/**
	 * 从FTP服务器下载指定文件名的文件。
	 * 
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return 成功下载返回true，否则返回false。
	 * 
	 */
	public boolean download(String remotePath, String fileName, String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(userName, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			FTPFile ff;
			for (int i = 0; i < fs.length; i++) {
				ff = fs[i];
				if (null != ff && null != ff.getName()
						&& ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			ftp.logout();
			
			return true;
		} catch (IOException e) {
			logger.error("", e);
			return false;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
	}

	
	
	/**
	 * 从FTP服务器列出指定文件夹下文件名列表。
	 * 
	 * @param remotePath FTP服务器上的相对路径
	 * @return List<String> 文件名列表，如果出现异常返回null。
	 * 
	 */
	public List<String> getFileNameList(String remotePath) throws IOException {
		// 目录列表记录
		List<String> fileNames = new ArrayList<String>();
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(userName, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return null;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile file : fs) {
				fileNames.add(file.getName());
			}
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return fileNames;
	}

}