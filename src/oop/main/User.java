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
	
	private String name;//user类数据成员
	private String password;
	private String role;
	
	String upFilePath = "E:\\OOPFile\\uploadfile\\";//本地文件夹路径
	String downFilePath = "E:\\OOPFile\\downloadfile\\";
	
	User() {}//空的构造方法
	User(String name, String password, String role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	//其他操作
	public abstract void showMenu();//抽象方法 输出菜单
	
	public void showFileList() throws SQLException{
		System.out.println("****文件列表****");//其他功能有待实现
//		System.out.println("文件列表如下：");
//		if(Math.random() >0.5)
//			throw new SQLException("警告！文件列表数据库错误  User 48");
		
		Enumeration<Doc> e = DataProcessing.docs.elements();
		Doc docs;//输出文档信息
		while(e.hasMoreElements()) {
			docs = e.nextElement();
			System.out.println("ID:"+docs.getID()+"\tCreator:"+docs.getCreator()+"\tTimestamp:"+docs.getTimestamp()+"\tDescription:"+docs.getDescription()
			+"\tFileName:"+docs.getFilename());
		}
	}
	
	public boolean downloadFile() throws SQLException,IOException,FileNotFoundException{
		System.out.println("****文件下载****");
//		System.out.println("文件下载...(此功能尚未具体实现)");
//		if(Math.random() > 0.5)
//			throw new IOException("警告！文件下载错误User54");
		String ID = null;
		System.out.print("请输入档案号：");
		ID = Tools.input();
		//基本的文件处理操作
		byte[] buffer = new byte[1024];
		Doc doc = DataProcessing.searchDoc(ID);
		if(doc == null) 
			return false;
		File tempFile = new File(upFilePath+doc.getFilename());
		@SuppressWarnings("unused")
		String fileName = tempFile.getName();
		//下载文件源目录。。下载文件目标目录
		BufferedInputStream inFile = new BufferedInputStream(new FileInputStream(tempFile));
		BufferedOutputStream targetFile = new BufferedOutputStream(new FileOutputStream(new File(downFilePath+doc.getFilename())));
		
		while(true) {
			int byteRead = inFile.read(buffer);//从文件中读取
			if(byteRead == -1) 
				break;
			targetFile.write(buffer,0,byteRead);
		}
		inFile.close();
		targetFile.close();
		return true;
	}
	
	public boolean changeSelfInfo(String password) throws SQLException {//修改用户信息  传递参数为密码
		//写用户信息进存储
		if (DataProcessing.update(name, password, role)){
			this.password=password;
			return true;
		}else
			return false;
		
	}
	public void exitSystem() {//系统主菜单退出的函数
		System.out.println("****系统退出，谢谢使用****\n****Designed by 彭玉全****");
		System.exit(0);
	}
	//一些对私有数据成员的操作
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
