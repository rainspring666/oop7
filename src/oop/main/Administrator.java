package oop.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

//kate
//ϵͳ����Ա��  �̳�User������ 
//ʵ�ֳ��󷽷�public abstract void showMenu();
// ���Ȩ��  ���û��Ĳ���  ɾ�� ���� �޸� �г��û� �����ļ� �ļ��б��޸�����
public class Administrator extends User{
	//���캯��
	public Administrator(String name, String password, String role) {
		super(name,password,role);
	}
	
	public void showMenu() {
		int ch = 0;
		while(true) {
			System.out.println("*******��������Ա�˵�*******");
			System.out.println("\t1.�޸��û�\n\t2.ɾ���û�\n\t3.�����û�\n\t"
					+ "4.�г��û�\n\t5.�����ļ�\n\t6.�ļ��б�\n\t7.������Կ\n\t8.��   ��");
			System.out.println("***************************");
			System.out.print("��ѡ����ţ�");
			try{//������Tools ��������쳣���д���
				ch = Integer.parseInt(Tools.input());
			} catch (NumberFormatException e) {
				System.out.println("���쳣�������棡��������� ����Tools����쳣�ѱ�����\n��ʾ��������һ��...");
				ch = 8;//���ܼ�������  ͨ���˵�����
			}
			
			switch(ch) {
			case 1:try {//��Ա� ���changeUserInfo �����׳���SQL�쳣��������
					changeUserInfo();
				} catch (SQLException e1) {
//					e1.printStackTrace();
					System.out.println(e1.toString());
				}break;
			case 2:try {//��Ա� ���delUser �����׳���SQL�쳣��������
					delUser();
				} catch (SQLException e1) {
					System.out.println(e1.toString());
//					e1.printStackTrace();
				}break;
			case 3:try {//��Ա� ���addUser �����׳���SQL�쳣��������
					addUser();
				} catch (SQLException e1) {
					System.out.println(e1.toString());
//					e1.printStackTrace();
				}break;
			case 4:listUser();break;
		
			case 5:try {//���USer ���down �����׳���IO�쳣��������
				if(downloadFile() == true)
					System.out.println("��ʾ���ļ����سɹ�");
				else
				    System.out.println("��ʾ��δ�ҵ����ļ�������ʧ��");
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println(e.toString());
			} catch (SQLException e) {
//				e.printStackTrace();
				System.out.println(e.toString());
			}break;
			case 6:try {//���USer ���show �����׳���SQL�쳣��������
					showFileList();
				} catch (SQLException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				}break;
			case 7:changeSelfInfo2();break;
			case 8:return;
			default :System.out.println("���棡��������ȷ���������");
			}
		}
		
	}
	
	public void changeSelfInfo2() {
		String password = null;
		System.out.print("�������µ����룺");
		password = Tools.input();
		//���ø�����޸ĺ���ʵ�������޸�
		try {//�������User ��� change �����׳���SQL�쳣�����������׳�
			if(changeSelfInfo(password)) {
				System.out.println("��ʾ���޸ĳɹ�");
			} else {
				System.out.println("���棡�޸�ʧ��");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	//���еĲ������� 
	//�������DataProcessing ��� search �����׳���SQL�쳣���������׳�
	public void changeUserInfo() throws SQLException{
		String name = null;
		String password = null;
		String role = null;
		System.out.println("******�޸��û�******");
		System.out.print("�������û�����");
		name = Tools.input();
		System.out.print("���������룺");
		password = Tools.input();
		System.out.print("�������û����ԣ�");
		role = Tools.input();
		//�ж��Ƿ��д��û�  ����ɾ��  ���򱨴�
		if(role.equals("operator")||role.equals("browser")||role.equals("administrator")) {
			if(DataProcessing.search(name) != null) {
				//���µ���Ϣ���� ���� 
				if(DataProcessing.update(name,password,role) == true) {
					System.out.println("��ʾ���û���Ϣ�޸ĳɹ�");
				}
			} else {
				System.out.println("���棡�޸���Ϣʧ�ܣ�����δ�ҵ����û�");
			}
		}else{
			System.out.println("���棡�û������������");
		}
		return ;
	}
	//ɾ���û�
	//�������DataProcessing ��� search �����׳���SQL�쳣���������׳�
	public void delUser() throws SQLException{
		String name = null;
		System.out.println("******ɾ���û�******");
		System.out.print("�������û�����");
		name = Tools.input();
		//ɾ���Ƿ�ɹ�
		if(DataProcessing.search(name) != null) {
			DataProcessing.delete(name);//ɾ��
			System.out.println("��ʾ���ɹ�ɾ�����û�");
		} else {
			System.out.println("���棡ɾ��ʧ�ܣ�����δ�ҵ����û�");
		}
		return ;
	}
	//�г������û�
	public void listUser() {
		System.out.println("******�г��û�******");
		System.out.println("�����û���Ϣ����");
		Enumeration<User> e = DataProcessing.users.elements();
		User user;//����û���Ϣ
		while(e.hasMoreElements()) {
			user = e.nextElement();
			System.out.println("Name:"+user.getName()+"\t Password:"+user.getPassword()+"\tRole:"+user.getRole());
		}
		return ;
	}
	//�����û�
	//�������DataProcessing ��� search �����׳���SQL�쳣���������׳�
	public void addUser() throws SQLException{
		String name = null;
		String password = null;
		String role = null;
		System.out.println("*****�����û�******");
		System.out.print("�������û�����");
		name = Tools.input();
		System.out.print("���������룺");
		password = Tools.input();
		System.out.print("�������û����ԣ�");
		role = Tools.input();
		//��role�ؼ����ж�
		if(role.equals("operator")||role.equals("browser")||role.equals("administrator")) {
			//�ж��Ƿ��Ѿ��й����û�
			if(DataProcessing.search(name) == null) {
				//�ж��û���Ϣ�Ƿ������ɹ�
				if(DataProcessing.insert(name, password, role) == true)
					System.out.println("��ʾ�������û���Ϣ�ɹ�");
				else 
					System.out.println("���棡�����û���Ϣʧ��");
			}else
				System.out.println("���棡���û��Ѵ��ڣ������û�ʧ��");
		} else 
			System.out.println("���棡�û������������");
		
		return ;
	}
	

}