package oop.frame;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import oop.main.DataProcessing;
import oop.main.DocClient;

public class MainFrame extends JFrame implements ActionListener{

	String name = null;//���������û���
	String role = null;
	private JPanel contentPane;
	public JMenu userMMenu = new JMenu("�û�����");
	public JMenuItem addUserMenuItem = new JMenuItem("�����û�");
	public JMenuItem changeMenuItem = new JMenuItem("�޸��û�");
	public JMenuItem delMenuItem = new JMenuItem("ɾ���û�");
	public JMenuItem upFMenuItem = new JMenuItem("�����ϴ�");
	public JMenuItem downFMenuItem = new JMenuItem("��������");
	public JMenuItem changeIMenuItem = new JMenuItem("��Ϣ�޸�");
	public JMenu ChangeInfoMenu = new JMenu("������Ϣ�޸�");
	public JMenu docMMenu = new JMenu("��������");
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		//���ش��ڹرպ���ʵ�� ���ݿ�Ĺر�
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			DataProcessing.closeDatabase();//�ر����ݿ�
//			DocClient.sendMessageToServer("CLIENT_LOGOUT");
			DocClient.sendMessageToServer("CLIENT>>> TERMINATE");//�ر���������
			System.exit(0);
		}
		});
		setBounds(100, 100, 900, 600);
		setTitle("ϵͳ������");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 231, 26);
		contentPane.add(menuBar);
		
//		JMenu userMMenu = new JMenu("�û�����");
		menuBar.add(userMMenu);
		
		
//		JMenuItem addUserMenuItem = new JMenuItem("�����û�");
		userMMenu.add(addUserMenuItem);
		
//		JMenuItem changeMenuItem = new JMenuItem("�޸��û�");
		userMMenu.add(changeMenuItem);
		
//		JMenuItem delMenuItem = new JMenuItem("ɾ���û�");
		userMMenu.add(delMenuItem);
		
//		JMenu docMMenu = new JMenu("��������");
		menuBar.add(docMMenu);
		
//		JMenuItem upFMenuItem = new JMenuItem("�����ϴ�");
		docMMenu.add(upFMenuItem);
		
//		JMenuItem downFMenuItem = new JMenuItem("��������");
		docMMenu.add(downFMenuItem);
		
//		JMenu ChangeInfoMenu = new JMenu("������Ϣ�޸�");
		menuBar.add(ChangeInfoMenu);
		
//		JMenuItem changeIMenuItem = new JMenuItem("��Ϣ�޸�");
		ChangeInfoMenu.add(changeIMenuItem);
		addUserMenuItem.addActionListener(this);
		changeMenuItem.addActionListener(this);
		delMenuItem.addActionListener(this);
		upFMenuItem.addActionListener(this);
		downFMenuItem.addActionListener(this);
		changeIMenuItem.addActionListener(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// ��Ϣ�޸�
		if(e.getSource() == changeIMenuItem) {
			SelfFrame selfFrame = new SelfFrame(name, role);
			selfFrame.setVisible(true);
//			ȱ�ٹ��ڴ����ܷ�༭�Ĺ���
//			
		}
		if(e.getSource() == addUserMenuItem) {
			UserFrame userFrame = new UserFrame(name);
			userFrame.setDefaultTab(0);
			userFrame.setVisible(true);
		}
		if(e.getSource() == changeMenuItem) {
			UserFrame userFrame = new UserFrame(name);
			userFrame.setDefaultTab(1);
			userFrame.setVisible(true);
		}
		if(e.getSource() == delMenuItem) {
			UserFrame userFrame = new UserFrame(name);
			userFrame.setDefaultTab(2);
			userFrame.setVisible(true);
		}
		if(e.getSource() == upFMenuItem) {
			FileFrame fileFrame = new FileFrame(name);
			fileFrame.setDefaultTab(1);
			fileFrame.setVisible(true);
		}
		if(e.getSource() == downFMenuItem) {
			FileFrame filename = new FileFrame(name);
			filename.setDefaultTab(0);
			filename.setVisible(true);
		}

	}



}
