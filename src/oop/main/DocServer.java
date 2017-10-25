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
                new SeverThread(socket,"Thread "+counter);//��������ʱ����һ���̴߳���
				}
			}catch (EOFException e) {//�����쳣�͹ر�����
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
//		DataProcessing.connectDataBase();//�������������ݿ�
		DocServer docServer = new DocServer();
	}
	
	//�߳���
	class SeverThread extends Thread{
		
		private String ThreadName;
		private Socket connection;
		private String client_message = null;
		private String server_message = null;
		
		private ObjectOutputStream outputToClient; 
		private ObjectInputStream inputFromClient; 
		private String upFilePath = "E:\\OOPFile\\uploadfile\\";//�����ļ���·��
		
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
					// TODO �Զ����ɵ� catch ��
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
		//��ȡIO��
		private void getStreams() throws IOException{
			outputToClient = new ObjectOutputStream(connection.getOutputStream());
			outputToClient.flush(); 
			
			inputFromClient = new ObjectInputStream(connection.getInputStream());

			System.out.println("\nGot I/O streams\n");
			
		}
		//���ӽ��������Ϣ�������
		private  void processConnection() throws IOException, ClassNotFoundException {
			client_message = "Connection Successful";
			do {
				//��ȡ����CLient����Ϣ  if���ʵ���ж�
					client_message = (String) inputFromClient.readObject();
			
				//�û���½
				if(client_message.equals("CLIENT_LOGIN")) {
					String name = (String) inputFromClient.readObject();
					System.out.println("CLIENT>>>��SERVER_LOGIN");
					System.out.println(name);
					sendMessageToClient("SERVER_LOGIN");//�����жϽ��
				}
				//�ļ��ϴ�
				//�û��Լ���Ϣ���޸�
				if(client_message.equals("CLIENT_SELF_MOD")) {
					System.out.println("CLIENT>>>��CLIENT_SELF_MOD\n");
					sendMessageToClient("SERVER_SELF_MOD");//�����жϽ��
				}
				if(client_message.equals("CLIENT_USER_ADD")) {
					System.out.println("CLIENT>>>��CLIENT_USER_ADD\n");
					sendMessageToClient("SERVER_USER_ADD");//�����жϽ��
				}
				if(client_message.equals("CLIENT_USER_DEL")) {
					System.out.println("CLIENT>>>��CLIENT_USER_DEL\n");
					sendMessageToClient("SERVER_USER_DEL");//�����жϽ��
				}
				if(client_message.equals("CLIENT_USER_CHA")) {
					System.out.println("CLIENT>>>��CLIENT_USER_CHA\n");
					sendMessageToClient("SERVER_USER_CHA");//�����жϽ��
				}
				if(client_message.equals("CLIENT_FILE_UP")) {
					String ID =(String) inputFromClient.readObject();
					String filename = (String) inputFromClient.readObject();
					System.out.println("CLIENT>>>��CLIENT_FILE_UP\n");
					receiveFile(ID,filename);
					
				}
				if(client_message.equals("CLIENT_FILE_DOWN")){
					System.out.println("CLIENT>>>��CLIENT_FILE_DOWN\n");
					String fileName = (String) inputFromClient.readObject();
					File downfile = new File(upFilePath+"\\" +fileName);
					sendFile(downfile);
					
				}
				
				
			}while(!client_message.equals( "CLIENT>>> TERMINATE" ));
		}
		//������Ϣ��Client
		public void sendMessageToClient(String message1) {
			
			try {
				outputToClient.writeObject(message1);
				outputToClient.flush();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		//�ر�����
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
		
		//��ȡ�ļ����ļ���Ϣ
		
		private void sendFile(File file) throws IOException{		
			fis =new FileInputStream(file);
			System.out.println(file.getPath());
			System.out.println(file.length());
	                     
	        //�ļ�����
			outputToClient.writeLong(file.length());
//			outputToClient.write((int) file.length());
			outputToClient.flush();
	         
	        //�����ļ�
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
	        System.out.println("----��ʼ�����ļ�<" + filename +">,�ļ���СΪ<" + fileLength +">----");
	        while(transLen<fileLength){
	            int read =0;
	            read = inputFromClient.read(buffer);
	            System.out.println(read);
	            if(read == -1)
	                break;
	            transLen += read;
	            System.out.println("�����ļ�����" +100 * transLen/fileLength +"%..."+ transLen);
	            fos.write(buffer,0, read);
	            fos.flush();
	        }
	        fos.close();
	        System.out.println("----�����ļ�<" + filename +">�ɹ�-------");				        
		}

		
	}
	


}
