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


//jack   ʵ���ϴ��ļ�  �����ļ�  �ļ��б� �޸�����
public class Operator extends User{
	//���캯��
	public Operator(String name, String password, String role) {
		super(name,password,role);
	}
	
	public void showMenu() {
		int ch = 0;
		while(true) {
			System.out.println("*******����¼��Ա�˵�*******");
			System.out.println("\t1.�ϴ��ļ�\n\t2.�����ļ�\n\t3.�ļ��б�\n\t4.�޸�����\n\t5.��     ��");
			System.out.println("***************************");
			System.out.print("��ѡ����ţ�");
			
			try{//������Tools ��������쳣���д���
				ch = Integer.parseInt(Tools.input());
			} catch (NumberFormatException e) {
				System.out.println("���쳣�������棡��������� ����Tools����쳣�ѱ�����\n\t       ��ʾ��������һ��...");
				ch = 5;//�޷������Զ��ص���һ���˵�
			}
			switch(ch) {
			case 1:try {//������uploadFile���쳣���д���
					uploadFile();
				} catch (FileNotFoundException e) {
					System.out.println(e.toString());
					System.out.println("���쳣�������棡δ���ִ��ļ�");
				} catch (IOException e) {
					System.out.println(e.toString());
					System.out.println("���쳣�������棡�ļ���д����");
				} catch (SQLException e) {
					System.out.println(e.toString());
					System.out.println("���쳣�������棡���ݿ������쳣");
				} break;
			case 2:try {//������User���downloadFile �׳���IO�쳣���д���  �����׳�
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
			case 3:try {//������User���showFileList �׳���SQL�쳣���д���  �����׳�
					showFileList();
				} catch (SQLException e) {
//					e.printStackTrace();
					System.out.println(e.toString());
				}break;
			case 4:changeSelfInfo2();break;
			case 5:return;
			default :System.out.println("���棡��������ȷ���������");
			}
		}
		
	}
	//���������޸����뺯��  ����ʹ�ø����޸����뺯��
	public void changeSelfInfo2() {
		String password = null;
		System.out.print("�������µ����룺");
		password = Tools.input();
		//���ø�����޸ĺ���ʵ�������޸�
		try {//������User���changeSelfInfo �׳���SQL�쳣���д���  �����׳�
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
	//�ļ��ϴ�   ������δʵ��
	public void uploadFile() throws IOException ,FileNotFoundException, SQLException{
		System.out.println("**********�ļ��ϴ�**********");
		String filePath = null;
//		int fileID = 0;
		String fileID= null;
		String fileDes = null;
		System.out.print("���������·�����ļ�����");
		filePath = Tools.input();
		System.out.print("�����뵵���ţ�");
//		fileID = Integer.parseInt(Tools.input().trim());
		fileID = Tools.input();
		System.out.print("�������ļ�������");
		fileDes = Tools.input();
//		//�жϵ������Ƿ��ظ�
//		if(DataProcessing.searchDoc(fileID) != null)
//			return ;
		//��ȡ�ļ�name
		String fileName = null;
		int beginIndex = filePath.lastIndexOf("\\");
		int endIndex = filePath.length();
		try{
			fileName = filePath.substring(beginIndex+1, endIndex);
		}catch(StringIndexOutOfBoundsException e) {
			System.out.println("���쳣�������棡�������׼�ļ�·�����ļ���\n\t      ������һ��...");
		}
		
		//�ļ�copy
		byte[] buffer = new byte[1024];
		//�����ļ�ԴĿ¼���������ļ�Ŀ��Ŀ¼       �˴��׳��쳣
		BufferedInputStream inFile = new BufferedInputStream(new FileInputStream(new File(filePath)));
		BufferedOutputStream targetFile = new BufferedOutputStream(new FileOutputStream(new File(upFilePath+fileName)));
		while(true) {
			int byteRead = inFile.read(buffer);//���ļ��ж�ȡ
			if(byteRead == -1) 
				break;
			targetFile.write(buffer,0,byteRead);
		}
		inFile.close();
		targetFile.close();
		//�������ݿ��ຯ��
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		DataProcessing.insertDoc(fileID,this.getName(),timestamp,fileDes,fileName);
		
		System.out.println("��ʾ���ļ��ϴ��ɹ�");
		return ;
	}

}
