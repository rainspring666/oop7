package oop.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;


//jack   实现上传文件  下载文件  文件列表 修改密码
public class Operator extends User{
	//构造函数
	public Operator(String name, String password, String role) {
		super(name,password,role);
	}
	
	public void showMenu() {
		int ch = 0;
		while(true) {
			System.out.println("*******档案录入员菜单*******");
			System.out.println("\t1.上传文件\n\t2.下载文件\n\t3.文件列表\n\t4.修改密码\n\t5.退     出");
			System.out.println("***************************");
			System.out.print("请选择序号：");
			
			try{//对来自Tools 类的输入异常经行处理
				ch = Integer.parseInt(Tools.input());
			} catch (NumberFormatException e) {
				System.out.println("【异常处理】警告！请输入序号 来自Tools类的异常已被处理\n\t       提示！返回上一级...");
				ch = 5;//无法处理自动回到上一级菜单
			}
			switch(ch) {
			case 1:try {//对来自uploadFile的异常进行处理
					uploadFile();
				} catch (FileNotFoundException e) {
					System.out.println(e.toString());
					System.out.println("【异常处理】警告！未发现此文件");
				} catch (IOException e) {
					System.out.println(e.toString());
					System.out.println("【异常处理】警告！文件读写错误");
				} catch (SQLException e) {
					System.out.println(e.toString());
					System.out.println("【异常处理】警告！数据库连接异常");
				} break;
			case 2:try {//对来自User类的downloadFile 抛出的IO异常进行处理  不再抛出
					if(downloadFile() == true)
						System.out.println("提示！文件下载成功");
					else
					    System.out.println("提示！未找到此文件，下载失败");
				} catch (IOException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				} catch (SQLException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				}break;
			case 3:try {//对来自User类的showFileList 抛出的SQL异常进行处理  不再抛出
					showFileList();
				} catch (SQLException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				}break;
			case 4:changeSelfInfo2();break;
			case 5:return;
			default :System.out.println("警告！请输入正确的数字序号");
			}
		}
		
	}
	//新增帮助修改密码函数  不再使用父类修该密码函数
	public void changeSelfInfo2() {
		String password = null;
		System.out.print("请输入新的密码：");
		password = Tools.input();
		//调用父类的修改函数实现密码修改
		try {//对来自User类的changeSelfInfo 抛出的SQL异常进行处理  不再抛出
			if(changeSelfInfo(password)) {
				System.out.println("提示！修改成功");
			} else {
				System.out.println("警告！修改失败");
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	//文件上传   具体尚未实现
	public void uploadFile() throws IOException ,FileNotFoundException, SQLException{
		System.out.println("**********文件上传**********");
		String filePath = null;
//		int fileID = 0;
		String fileID= null;
		String fileDes = null;
		System.out.print("请输入包含路径的文件名：");
		filePath = Tools.input();
		System.out.print("请输入档案号：");
//		fileID = Integer.parseInt(Tools.input().trim());
		fileID = Tools.input();
		System.out.print("请输入文件描述：");
		fileDes = Tools.input();
//		//判断档案号是否重复
//		if(DataProcessing.searchDoc(fileID) != null)
//			return ;
		//提取文件name
		String fileName = null;
		int beginIndex = filePath.lastIndexOf("\\");
		int endIndex = filePath.length();
		try{
			fileName = filePath.substring(beginIndex+1, endIndex);
		}catch(StringIndexOutOfBoundsException e) {
			System.out.println("【异常处理】警告！请输入标准文件路径和文件名\n\t      返回上一级...");
		}
		
		//文件copy
		byte[] buffer = new byte[1024];
		//下载文件源目录。。下载文件目标目录       此处抛出异常
		BufferedInputStream inFile = new BufferedInputStream(new FileInputStream(new File(filePath)));
		BufferedOutputStream targetFile = new BufferedOutputStream(new FileOutputStream(new File(upFilePath+fileName)));
		while(true) {
			int byteRead = inFile.read(buffer);//从文件中读取
			if(byteRead == -1) 
				break;
			targetFile.write(buffer,0,byteRead);
		}
		inFile.close();
		targetFile.close();
		//调用数据库类函数
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		DataProcessing.insertDoc(fileID,this.getName(),timestamp,fileDes,fileName);
		
		System.out.println("提示！文件上传成功");
		return ;
	}

}
