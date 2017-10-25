package oop.main;

import java.sql.SQLException;

public class LoginUser{
	
	//������loginFrame ���û����������ж�  ������ɫ
	public static String judgeUser(String name, String password) {
		//������ȡ�û�
		User user = null;
		try {
			user = DataProcessing.search(name, password);
		} catch (SQLException e) {
			// �쳣���� �������Լ�һ��Frame��ʾ������Ϣ
			e.printStackTrace();
			return "SQLException";
		}
		//�ж��û�  �����û�����  ��LoginFrame�д�����
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
