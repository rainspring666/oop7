package oop.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

public abstract class User {
	
	private String name;//user�����ݳ�Ա
	private String password;
	private String role;
	
	String upFilePath = "E:\\OOPFile\\uploadfile\\";//�����ļ���·��
	String downFilePath = "E:\\OOPFile\\downloadfile\\";
	
	User() {}//�յĹ��췽��
	User(String name, String password, String role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	//��������
	public abstract void showMenu();//���󷽷� ����˵�
	
	public void showFileList() throws SQLException{
		System.out.println("****�ļ��б�****");//���������д�ʵ��
//		System.out.println("�ļ��б����£�");
//		if(Math.random() >0.5)
//			throw new SQLException("���棡�ļ��б����ݿ����  User 48");
		
		Enumeration<Doc> e = DataProcessing.docs.elements();
		Doc docs;//����ĵ���Ϣ
		while(e.hasMoreElements()) {
			docs = e.nextElement();
			System.out.println("ID:"+docs.getID()+"\tCreator:"+docs.getCreator()+"\tTimestamp:"+docs.getTimestamp()+"\tDescription:"+docs.getDescription()
			+"\tFileName:"+docs.getFilename());
		}
	}
	
	public boolean downloadFile() throws SQLException,IOException,FileNotFoundException{
		System.out.println("****�ļ�����****");
//		System.out.println("�ļ�����...(�˹�����δ����ʵ��)");
//		if(Math.random() > 0.5)
//			throw new IOException("���棡�ļ����ش���User54");
		String ID = null;
		System.out.print("�����뵵���ţ�");
		ID = Tools.input();
		//�������ļ��������
		byte[] buffer = new byte[1024];
		Doc doc = DataProcessing.searchDoc(ID);
		if(doc == null) 
			return false;
		File tempFile = new File(upFilePath+doc.getFilename());
		@SuppressWarnings("unused")
		String fileName = tempFile.getName();
		//�����ļ�ԴĿ¼���������ļ�Ŀ��Ŀ¼
		BufferedInputStream inFile = new BufferedInputStream(new FileInputStream(tempFile));
		BufferedOutputStream targetFile = new BufferedOutputStream(new FileOutputStream(new File(downFilePath+doc.getFilename())));
		
		while(true) {
			int byteRead = inFile.read(buffer);//���ļ��ж�ȡ
			if(byteRead == -1) 
				break;
			targetFile.write(buffer,0,byteRead);
		}
		inFile.close();
		targetFile.close();
		return true;
	}
	
	public boolean changeSelfInfo(String password) throws SQLException {//�޸��û���Ϣ  ���ݲ���Ϊ����
		//д�û���Ϣ���洢
		if (DataProcessing.update(name, password, role)){
			this.password=password;
			return true;
		}else
			return false;
		
	}
	public void exitSystem() {//ϵͳ���˵��˳��ĺ���
		System.out.println("****ϵͳ�˳���ллʹ��****\n****Designed by ����ȫ****");
		System.exit(0);
	}
	//һЩ��˽�����ݳ�Ա�Ĳ���
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
		

}
