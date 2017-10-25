package oop.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class DocServer {
	private ServerSocket server;
//	private Socket connection;
	private int counter = 0;
//	public PrintWriter outputToClient;
	
	private FileInputStream fis;
	private FileOutputStream fos;
	public DocServer() {
		try{
			server = new ServerSocket(1680); 
			try {
			while(true) {
				System.out.println("Server is Waiting for connection\n");
                Socket socket = server.accept();
                counter++;
                new SeverThread(socket,"Thread "+counter);//当有请求时，启一个线程处理
				}
			}catch (EOFException e) {//捕获到异常就关闭连接
				System.out.println( "\nServer terminated connection" );
			}finally {

				server.close();
				
			}
		}catch (IOException e){
			e.printStackTrace();	
			System.out.println("\nCatch IOException In Main\n");
		}
	}
	
	public static void main(String args[]) {
//		DataProcessing.connectDataBase();//服务器开启数据库
		DocServer docServer = new DocServer();
	}
	
	//线程类
	class SeverThread extends Thread{
		
		private String ThreadName;
		private Socket connection;
		private String client_message = null;
		private String server_message = null;
		
		private ObjectOutputStream outputToClient; 
		private ObjectInputStream inputFromClient; 
		private String upFilePath = "E:\\OOPFile\\uploadfile\\";//本地文件夹路径
		
		public SeverThread(Socket socket, String name)throws IOException {
	    	connection = socket; 	    	
	    	ThreadName=name;
	    	System.out.println(ThreadName + " received from: " + connection.getInetAddress().getHostName());
	    	
	    	getStreams(); 		
             
            start();
        }
		public void run(){
	    	try {
				try {
					processConnection();
				} catch (ClassNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			} 
	    }	
		private void waitForConnection() throws IOException {
			System.out.println("Server is Waiting for connection\n");
			connection = server.accept();
			System.out.println("Connection " + counter + " received from: " +
			         connection.getInetAddress().getHostName());
		}
		//获取IO流
		private void getStreams() throws IOException{
			outputToClient = new ObjectOutputStream(connection.getOutputStream());
			outputToClient.flush(); 
			
			inputFromClient = new ObjectInputStream(connection.getInputStream());

			System.out.println("\nGot I/O streams\n");
			
		}
		//链接建立后的信息传输操作
		private  void processConnection() throws IOException, ClassNotFoundException {
			client_message = "Connection Successful";
			do {
				//读取来自CLient的信息  if语句实现判断
					client_message = (String) inputFromClient.readObject();
			
				//用户登陆
				if(client_message.equals("CLIENT_LOGIN")) {
					String name = (String) inputFromClient.readObject();
					System.out.println("CLIENT>>>　SERVER_LOGIN");
					System.out.println(name);
					sendMessageToClient("SERVER_LOGIN");//发回判断结果
				}
				//文件上传
				//用户自己信息的修改
				if(client_message.equals("CLIENT_SELF_MOD")) {
					System.out.println("CLIENT>>>　CLIENT_SELF_MOD\n");
					sendMessageToClient("SERVER_SELF_MOD");//发回判断结果
				}
				if(client_message.equals("CLIENT_USER_ADD")) {
					System.out.println("CLIENT>>>　CLIENT_USER_ADD\n");
					sendMessageToClient("SERVER_USER_ADD");//发回判断结果
				}
				if(client_message.equals("CLIENT_USER_DEL")) {
					System.out.println("CLIENT>>>　CLIENT_USER_DEL\n");
					sendMessageToClient("SERVER_USER_DEL");//发回判断结果
				}
				if(client_message.equals("CLIENT_USER_CHA")) {
					System.out.println("CLIENT>>>　CLIENT_USER_CHA\n");
					sendMessageToClient("SERVER_USER_CHA");//发回判断结果
				}
				if(client_message.equals("CLIENT_FILE_UP")) {
					String ID =(String) inputFromClient.readObject();
					String filename = (String) inputFromClient.readObject();
					System.out.println("CLIENT>>>　CLIENT_FILE_UP\n");
					receiveFile(ID,filename);
					
				}
				if(client_message.equals("CLIENT_FILE_DOWN")){
					System.out.println("CLIENT>>>　CLIENT_FILE_DOWN\n");
					String fileName = (String) inputFromClient.readObject();
					File downfile = new File(upFilePath+"\\" +fileName);
					sendFile(downfile);
					
				}
				
				
			}while(!client_message.equals( "CLIENT>>> TERMINATE" ));
		}
		//发送信息给Client
		public void sendMessageToClient(String message1) {
			
			try {
				outputToClient.writeObject(message1);
				outputToClient.flush();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		//关闭连接
		private  void closeConnection() {
			System.out.println("\nTerminating connection\n");
			try {
				outputToClient.close();
				inputFromClient.close();
				connection.close();
				sendMessageToClient("SERVER>>> TERMINATE");
			}catch(IOException e) {
				e.printStackTrace();
				System.out.println("\nCatch IOException When Server Close SeverConnection\n");
			}
		}
		
		//获取文件及文件信息
		
		private void sendFile(File file) throws IOException{		
			fis =new FileInputStream(file);
			System.out.println(file.getPath());
			System.out.println(file.length());
	                     
	        //文件长度
			outputToClient.writeLong(file.length());
//			outputToClient.write((int) file.length());
			outputToClient.flush();
	         
	        //传输文件
	        byte[] buffer =new byte[1024];            
	        while(true){
	        	int read = 0;  
	        	if (fis != null) {  
	        		read = fis.read(buffer);                   
	        	}  
	        	if (read == -1)   
	        		break;            
	        	outputToClient.write(buffer,0, read); 
	        	outputToClient.flush();
	        }       
	        fis.close();
		}
			
		private void receiveFile(String ID, String filename) throws IOException{			
			long fileLength = inputFromClient.readLong();
			File dir = new File(upFilePath);
			dir.mkdirs();
			File file=new File(dir,filename);
			
			fos =new FileOutputStream(file);
	         
	        byte[] buffer =new byte[1024];
	        int transLen =0;
	        System.out.println("----开始接收文件<" + filename +">,文件大小为<" + fileLength +">----");
	        while(transLen<fileLength){
	            int read =0;
	            read = inputFromClient.read(buffer);
	            System.out.println(read);
	            if(read == -1)
	                break;
	            transLen += read;
	            System.out.println("接收文件进度" +100 * transLen/fileLength +"%..."+ transLen);
	            fos.write(buffer,0, read);
	            fos.flush();
	        }
	        fos.close();
	        System.out.println("----接收文件<" + filename +">成功-------");				        
		}

		
	}
	


}
