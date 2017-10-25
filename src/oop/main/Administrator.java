package oop.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

//kate
//系统管理员类  继承User基本类 
//实现抽象方法public abstract void showMenu();
// 最高权限  对用户的操作  删除 新增 修改 列出用户 下载文件 文件列表修改密码
public class Administrator extends User{
	//构造函数
	public Administrator(String name, String password, String role) {
		super(name,password,role);
	}
	
	public void showMenu() {
		int ch = 0;
		while(true) {
			System.out.println("*******档案管理员菜单*******");
			System.out.println("\t1.修改用户\n\t2.删除用户\n\t3.新增用户\n\t"
					+ "4.列出用户\n\t5.下载文件\n\t6.文件列表\n\t7.更改密钥\n\t8.退   出");
			System.out.println("***************************");
			System.out.print("请选择序号：");
			try{//对来自Tools 类的输入异常经行处理
				ch = Integer.parseInt(Tools.input());
			} catch (NumberFormatException e) {
				System.out.println("【异常处理】警告！请输入序号 来自Tools类的异常已被处理\n提示！返回上一级...");
				ch = 8;//不能继续处理  通过菜单返回
			}
			
			switch(ch) {
			case 1:try {//针对本 类的changeUserInfo 函数抛出的SQL异常做出处理
					changeUserInfo();
				} catch (SQLException e1) {
//					e1.printStackTrace();
					System.out.println(e1.toString());
				}break;
			case 2:try {//针对本 类的delUser 函数抛出的SQL异常做出处理
					delUser();
				} catch (SQLException e1) {
					System.out.println(e1.toString());
//					e1.printStackTrace();
				}break;
			case 3:try {//针对本 类的addUser 函数抛出的SQL异常做出处理
					addUser();
				} catch (SQLException e1) {
					System.out.println(e1.toString());
//					e1.printStackTrace();
				}break;
			case 4:listUser();break;
		
			case 5:try {//针对USer 类的down 函数抛出的IO异常做出处理
				if(downloadFile() == true)
					System.out.println("提示！文件下载成功");
				else
				    System.out.println("提示！未找到此文件，下载失败");
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println(e.toString());
			} catch (SQLException e) {
//				e.printStackTrace();
				System.out.println(e.toString());
			}break;
			case 6:try {//针对USer 类的show 函数抛出的SQL异常做出处理
					showFileList();
				} catch (SQLException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				}break;
			case 7:changeSelfInfo2();break;
			case 8:return;
			default :System.out.println("警告！请输入正确的数字序号");
			}
		}
		
	}
	
	public void changeSelfInfo2() {
		String password = null;
		System.out.print("请输入新的密码：");
		password = Tools.input();
		//调用父类的修改函数实现密码修改
		try {//针对来自User 类的 change 函数抛出的SQL异常做出处理不再抛出
			if(changeSelfInfo(password)) {
				System.out.println("提示！修改成功");
			} else {
				System.out.println("警告！修改失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	//独有的操作方法 
	//针对来自DataProcessing 类的 search 函数抛出的SQL异常继续向上抛出
	public void changeUserInfo() throws SQLException{
		String name = null;
		String password = null;
		String role = null;
		System.out.println("******修改用户******");
		System.out.print("请输入用户名：");
		name = Tools.input();
		System.out.print("请输入密码：");
		password = Tools.input();
		System.out.print("请输入用户属性：");
		role = Tools.input();
		//判断是否有此用户  有则删除  无则报错
		if(role.equals("operator")||role.equals("browser")||role.equals("administrator")) {
			if(DataProcessing.search(name) != null) {
				//将新的信息保存 更新 
				if(DataProcessing.update(name,password,role) == true) {
					System.out.println("提示！用户信息修改成功");
				}
			} else {
				System.out.println("警告！修改信息失败，可能未找到此用户");
			}
		}else{
			System.out.println("警告！用户属性输入错误");
		}
		return ;
	}
	//删除用户
	//针对来自DataProcessing 类的 search 函数抛出的SQL异常继续向上抛出
	public void delUser() throws SQLException{
		String name = null;
		System.out.println("******删除用户******");
		System.out.print("请输入用户名：");
		name = Tools.input();
		//删除是否成功
		if(DataProcessing.search(name) != null) {
			DataProcessing.delete(name);//删除
			System.out.println("提示！成功删除此用户");
		} else {
			System.out.println("警告！删除失败，可能未找到此用户");
		}
		return ;
	}
	//列出所有用户
	public void listUser() {
		System.out.println("******列出用户******");
		System.out.println("所有用户信息如下");
		Enumeration<User> e = DataProcessing.users.elements();
		User user;//输出用户信息
		while(e.hasMoreElements()) {
			user = e.nextElement();
			System.out.println("Name:"+user.getName()+"\t Password:"+user.getPassword()+"\tRole:"+user.getRole());
		}
		return ;
	}
	//新增用户
	//针对来自DataProcessing 类的 search 函数抛出的SQL异常继续向上抛出
	public void addUser() throws SQLException{
		String name = null;
		String password = null;
		String role = null;
		System.out.println("*****新增用户******");
		System.out.print("请输入用户名：");
		name = Tools.input();
		System.out.print("请输入密码：");
		password = Tools.input();
		System.out.print("请输入用户属性：");
		role = Tools.input();
		//对role关键字判断
		if(role.equals("operator")||role.equals("browser")||role.equals("administrator")) {
			//判断是否已经有过此用户
			if(DataProcessing.search(name) == null) {
				//判断用户信息是否新增成功
				if(DataProcessing.insert(name, password, role) == true)
					System.out.println("提示！新增用户信息成功");
				else 
					System.out.println("警告！新增用户信息失败");
			}else
				System.out.println("警告！此用户已存在，新增用户失败");
		} else 
			System.out.println("警告！用户属性输入错误");
		
		return ;
	}
	

}