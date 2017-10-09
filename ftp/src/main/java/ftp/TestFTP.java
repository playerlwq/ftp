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
	     * ��ȡFTPClient���� 
	     * @param ftpHost FTP���������� 
	     * @param ftpPassword FTP ��¼���� 
	     * @param ftpUserName FTP��¼�û��� 
	     * @param ftpPort FTP�˿� Ĭ��Ϊ21 
	     * @return 
	     *//*  
	    public static FTPClient getFTPClient(String ftpHost, String ftpPassword,  
	            String ftpUserName, int ftpPort) {  
	        FTPClient ftpClient = null;  
	        try {  
	            ftpClient = new FTPClient();  
	            ftpClient.connect(ftpHost, ftpPort);// ����FTP������  
	            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
	            ftpClient.login(ftpUserName, ftpPassword);// ��½FTP������  
	            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
	                logger.info("δ���ӵ�FTP���û������������");  
	                ftpClient.disconnect();  
	            } else {  
	                logger.info("FTP���ӳɹ���");  
	            }  
	        } catch (SocketException e) {  
	            e.printStackTrace();  
	            logger.info("FTP��IP��ַ���ܴ�������ȷ���á�");  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            logger.info("FTP�Ķ˿ڴ���,����ȷ���á�");  
	        }  
	        return ftpClient;  
	    }  */
	    
	    public static void main(String[] args) {
			
	    	ChannelSftp connect = connect("47.93.226.82", 22, "root", "lxq520..");
	    	try {
//				connect.get("/root/docker_run.sh", "D://docker_run.sh"); //�ļ����� 
//	    		connect.rename("/root/docker_run.sh", "/root/docker_run_bak.sh"); //�ļ�������
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
					//dst �������ļ�����
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
	     * ����sftp������ 
	     *  
	     * @param host ���� 
	     * @param port �˿� 
	     * @param username �û��� 
	     * @param password ���� 
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
