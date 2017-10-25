package oop.main;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;


public class DocClient {
	private Socket client;
	private String chatServerHost;//
//	private static PrintWriter outputToServer;
//	public static  BufferedReader inputFromServer;
	public String client_message = "";
	private static String server_message = null;
	private static FileInputStream fis;
	private static FileOutputStream fos;
//	private static ObjectOutputStream output;//帮助读写文件
//	private static ObjectInputStream input;
	private static ObjectOutputStream outputToServer;//帮助读写文件
	private static ObjectInputStream inputFromServer;
	
	public DocClient(String host) {
		try{
	         connectToServer(); ;//创建一个链接
	         getStreams(); //获取输入输出刘
	         processConnection(); //链接建立后的信息交流
	      }catch ( EOFException eofException ){
	    	  System.out.println( "\nClient Terminated Connection" );
	      }catch ( IOException e ){
	         e.printStackTrace();
	      } catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
//		finally{
//	         closeConnection();
//	      } 
	}
	
	//链接Server
	private void connectToServer() throws IOException {
		System.out.println( "Attempting connection\n" );
		client = new Socket( InetAddress.getByName( chatServerHost ), 1680 );
		System.out.println( "Connected to: " + 
		         client.getInetAddress().getHostName() );
		
	}
	//获取输入输出流
	private void getStreams() throws IOException {
		outputToServer = new ObjectOutputStream(client.getOutputStream());
		outputToServer.flush(); 

		inputFromServer = new ObjectInputStream(client.getInputStream());

		System.out.println("\nGot I/O streams\n");
	} 
	//链接建立后的信息交流
	private void processConnection() throws IOException, ClassNotFoundException {
		 //对来自服务器的信息判断就像服务器一样
//		do{
			server_message = (String) inputFromServer.readObject();
			//服务器登录提示
	         if(server_message.equals("SERVER_LOGIN")) {
	        	 System.out.println("SERVER>>> SERVER_LOGIN\n");
	         }
	         //用户信息修改
	         if(server_message.equals("SERVER_SELF_MOD")) {
	        	 System.out.println("SERVER>>> SERVER_SELF_MOD\n");
	         }
	         //服务器添加用户
	         if(server_message.equals("SERVER_USER_ADD")) {
	        	 System.out.println("SERVER>>> SERVER_USER_ADD\n");
	         }
	         if(server_message.equals("SERVER_USER_DEL")) {
	        	 System.out.println("SERVER>>> SERVER_USER_DEL\n");
	         }
	         if(server_message.equals("SERVER_USER_CHA")) {
	        	 System.out.println("SERVER>>> SERVER_USER_CHA\n");
	         }
	         
	         
	         
//	      } while ( !client_message.equals( "SERVER>>> TERMINATE" ) );
	}
	//发送信息到Server
	public static void sendMessageToServer(String message) {

//		outputToServer.println(message);
		try {
			outputToServer.writeObject(message);
			outputToServer.flush();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//关闭连接
	private void closeConnection() {
		System.out.println( "\nClosing connection\n" );
		try {
			outputToServer.close();
			inputFromServer.close();
			client.close();
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("\nCatch IOException When Client Close SeverConnection\n");
		}
	}
	//*******************************文件操作*****************************
	public static boolean file_up(String ID, String description, File file) throws IOException, ClassNotFoundException{

	//发送消息	
		sendMessageToServer("CLIENT_FILE_UP");
		sendMessageToServer(ID);
		sendMessageToServer(file.getName());
		//发送文件
		sendFile(file); 
		//接收消息
		return true;
		       
	}
	
	public static boolean file_down(File file) throws IOException, ClassNotFoundException{
		
		DocClient.sendMessageToServer("CLIENT_FILE_DOWN");
		DocClient.sendMessageToServer(file.getName());
		System.out.println(file.getPath());
		
		receiveFile(file);
		return true;
	}
	
	
	
	//发送文件
	private static void sendFile(File file) throws IOException{		
		fis =new FileInputStream(file);
                     
        //文件长度
		outputToServer.writeLong(file.length());
		outputToServer.flush();
         
        //传输文件
        byte[] buffer =new byte[1024];            
        while(true){
        	int read = 0;  
        	if (fis != null) {  
        		read = fis.read(buffer);                   
        	}  
        	if (read == -1)   
        		break;            
        	outputToServer.write(buffer,0, read); 
        	outputToServer.flush();
        }       
        fis.close();
	}
	
	//接受文件
	private static void receiveFile(File file) throws IOException{
		long fileLength = inputFromServer.readLong();
		System.out.println(fileLength);
		System.out.println(file.getPath());
	
		
		fos = new FileOutputStream(file);
		String filename=file.getName(); 
		
	  byte[] buffer =new byte[1024];
      int transLen =0;
      System.out.println("----开始接收文件<" + filename +">,文件大小为<" + fileLength +">----");
      while(transLen<fileLength){
          int read =0;
          read = inputFromServer.read(buffer);
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
