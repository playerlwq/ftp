package ftp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor; 

/**
 * 
 *   lwq ftp utils
 */
public class TestFTP {

	 private static Logger logger = Logger.getLogger(TestFTP.class);  
	  
     
     
	    /** 
	     * 获取FTPClient对象 
	     * @param ftpHost FTP主机服务器 
	     * @param ftpPassword FTP 登录密码 
	     * @param ftpUserName FTP登录用户名 
	     * @param ftpPort FTP端口 默认为21 
	     * @return 
	     *//*  
	    public static FTPClient getFTPClient(String ftpHost, String ftpPassword,  
	            String ftpUserName, int ftpPort) {  
	        FTPClient ftpClient = null;  
	        try {  
	            ftpClient = new FTPClient();  
	            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器  
	            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
	            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器  
	            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
	                logger.info("未连接到FTP，用户名或密码错误。");  
	                ftpClient.disconnect();  
	            } else {  
	                logger.info("FTP连接成功。");  
	            }  
	        } catch (SocketException e) {  
	            e.printStackTrace();  
	            logger.info("FTP的IP地址可能错误，请正确配置。");  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            logger.info("FTP的端口错误,请正确配置。");  
	        }  
	        return ftpClient;  
	    }  */
	    
	    public static void main(String[] args) {
			
	    	ChannelSftp connect = connect("47.93.226.82", 22, "root", "lxq520..");
	    	try {
//				connect.get("/root/docker_run.sh", "D://docker_run.sh"); //文件下载 
//	    		connect.rename("/root/docker_run.sh", "/root/docker_run_bak.sh"); //文件重命名
	    		try {
	    			SftpProgressMonitor monitor =new SftpProgressMonitor() {
						
						public void init(int op, String src, String dest, long max) {
							
							System.out.println("op "+op);
							System.out.println("src "+src);
							System.out.println("dest"+dest);
							System.out.println("max "+max);
						}
						
						public void end() {
							System.out.println("end ~!!");
						}
						
						public boolean count(long count) {
							return false;
						}
					};
					//dst 必须是文件名称
					connect.put(new FileInputStream(new File("F:\\alipar_demo\\readme.txt")), "/root/mydesc/readme.txt",monitor);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		
				System.out.println("success");
				connect.disconnect();
				connect.exit();
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	    	
		}
	    
	    /** 
	     * 连接sftp服务器 
	     *  
	     * @param host 主机 
	     * @param port 端口 
	     * @param username 用户名 
	     * @param password 密码 
	     * @return 
	     */  
	    public static ChannelSftp connect(String host, int port, String username,  
	            String password) {  
	        ChannelSftp sftp = null;  
	        try {  
	            JSch jsch = new JSch();  
	            jsch.getSession(username, host, port);  
	            Session sshSession = jsch.getSession(username, host, port);  
	            System.out.println("Session created.");  
	            sshSession.setPassword(password);  
	            Properties sshConfig = new Properties();  
	            sshConfig.put("StrictHostKeyChecking", "no");  
	            sshSession.setConfig(sshConfig);  
	            sshSession.connect();  
	            System.out.println("Session connected.");  
	            System.out.println("Opening Channel.");  
	            Channel channel = sshSession.openChannel("sftp");  
	            channel.connect();  
	            sftp = (ChannelSftp) channel;  
	            System.out.println("Connected to " + host + ".");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return sftp;  
	    }  
}
