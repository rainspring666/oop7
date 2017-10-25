package oop.main;

import java.sql.SQLException;

public class LoginUser{
	
	//对来自loginFrame 的用户名和密码判断  分析角色
	public static String judgeUser(String name, String password) {
		//检索获取用户
		User user = null;
		try {
			user = DataProcessing.search(name, password);
		} catch (SQLException e) {
			// 异常处理 后续可以加一个Frame显示错误信息
			e.printStackTrace();
			return "SQLException";
		}
		//判断用户  返回用户属性  在LoginFrame中处理结果
		if(user == null) {
			return null;
		} else {
			if(user.getRole().equals("operator")) {
				return "operator";
			} else if(user.getRole().equals("browser")) {
				return "browser";
			} else
				return "administrator";
		}
	}

}
