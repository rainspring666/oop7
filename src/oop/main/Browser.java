package oop.main;

import java.io.IOException;
import java.sql.SQLException;

//rose   浏览用户的  实现下载文件  和  文件列表   修改密码
public class Browser extends User{
	//构造函数
	public Browser(String name, String password, String role) {
		super(name,password,role);
	}
	
	public void showMenu() {
		int ch = 0;
		while(true) {
			
			System.out.println("*******档案浏览员菜单*******");
			System.out.println("\t1.下载文件\n\t2.文件列表\n\t3.修改密码\n\t4.退      出");
			System.out.println("***************************");
			System.out.print("请选择序号：");
			
			try{//对来自Tools 类的输入异常经行处理
				ch = Integer.parseInt(Tools.input());
			} catch (NumberFormatException e) {
				System.out.println("【异常处理】警告！请输入序号 来自Tools类的异常已被处理\n提示！返回上一级...");
				ch = 4;//出现异常 自动返回上一级菜单
			}
			switch(ch) {//缺少必要的容错机制 
			case 1:
				try {//对来自User 类的IOException异常进行处理
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
			case 2:
				try {//对来自User 类的SQLException异常进行处理
					showFileList();
				} catch (SQLException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				}break;
			case 3:changeSelfInfo2();break;//调用本类函数
			case 4:return;
			default :System.out.println("警告！请输入正确的数字序号");
			}
		}
		
	}
	public void changeSelfInfo2() {
		String password = null;
		System.out.print("请输入新的密码：");
		password = Tools.input();
		//调用父类的修改函数实现密码修改
		try {//对来自User changeSelfInfo类抛出的IOException异常进行处理
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


}
