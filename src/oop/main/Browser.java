package oop.main;

import java.io.IOException;
import java.sql.SQLException;

//rose   ����û���  ʵ�������ļ�  ��  �ļ��б�   �޸�����
public class Browser extends User{
	//���캯��
	public Browser(String name, String password, String role) {
		super(name,password,role);
	}
	
	public void showMenu() {
		int ch = 0;
		while(true) {
			
			System.out.println("*******�������Ա�˵�*******");
			System.out.println("\t1.�����ļ�\n\t2.�ļ��б�\n\t3.�޸�����\n\t4.��      ��");
			System.out.println("***************************");
			System.out.print("��ѡ����ţ�");
			
			try{//������Tools ��������쳣���д���
				ch = Integer.parseInt(Tools.input());
			} catch (NumberFormatException e) {
				System.out.println("���쳣�������棡��������� ����Tools����쳣�ѱ�����\n��ʾ��������һ��...");
				ch = 4;//�����쳣 �Զ�������һ���˵�
			}
			switch(ch) {//ȱ�ٱ�Ҫ���ݴ���� 
			case 1:
				try {//������User ���IOException�쳣���д���
					if(downloadFile() == true)
						System.out.println("��ʾ���ļ����سɹ�");
					else
					    System.out.println("��ʾ��δ�ҵ����ļ�������ʧ��");
				} catch (IOException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				} catch (SQLException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				}break;
			case 2:
				try {//������User ���SQLException�쳣���д���
					showFileList();
				} catch (SQLException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				}break;
			case 3:changeSelfInfo2();break;//���ñ��ຯ��
			case 4:return;
			default :System.out.println("���棡��������ȷ���������");
			}
		}
		
	}
	public void changeSelfInfo2() {
		String password = null;
		System.out.print("�������µ����룺");
		password = Tools.input();
		//���ø�����޸ĺ���ʵ�������޸�
		try {//������User changeSelfInfo���׳���IOException�쳣���д���
			if(changeSelfInfo(password)) {
				System.out.println("��ʾ���޸ĳɹ�");
			} else {
				System.out.println("���棡�޸�ʧ��");
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println(e.toString());
		}
	}


}
