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

	String name = null;//用来传递用户名
	String role = null;
	private JPanel contentPane;
	public JMenu userMMenu = new JMenu("用户管理");
	public JMenuItem addUserMenuItem = new JMenuItem("新增用户");
	public JMenuItem changeMenuItem = new JMenuItem("修改用户");
	public JMenuItem delMenuItem = new JMenuItem("删除用户");
	public JMenuItem upFMenuItem = new JMenuItem("档案上传");
	public JMenuItem downFMenuItem = new JMenuItem("档案下载");
	public JMenuItem changeIMenuItem = new JMenuItem("信息修改");
	public JMenu ChangeInfoMenu = new JMenu("个人信息修改");
	public JMenu docMMenu = new JMenu("档案管理");
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		//重载窗口关闭函数实现 数据库的关闭
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			DataProcessing.closeDatabase();//关闭数据库
//			DocClient.sendMessageToServer("CLIENT_LOGOUT");
			DocClient.sendMessageToServer("CLIENT>>> TERMINATE");//关闭网络连接
			System.exit(0);
		}
		});
		setBounds(100, 100, 900, 600);
		setTitle("系统主界面");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 231, 26);
		contentPane.add(menuBar);
		
//		JMenu userMMenu = new JMenu("用户管理");
		menuBar.add(userMMenu);
		
		
//		JMenuItem addUserMenuItem = new JMenuItem("新增用户");
		userMMenu.add(addUserMenuItem);
		
//		JMenuItem changeMenuItem = new JMenuItem("修改用户");
		userMMenu.add(changeMenuItem);
		
//		JMenuItem delMenuItem = new JMenuItem("删除用户");
		userMMenu.add(delMenuItem);
		
//		JMenu docMMenu = new JMenu("档案管理");
		menuBar.add(docMMenu);
		
//		JMenuItem upFMenuItem = new JMenuItem("档案上传");
		docMMenu.add(upFMenuItem);
		
//		JMenuItem downFMenuItem = new JMenuItem("档案下载");
		docMMenu.add(downFMenuItem);
		
//		JMenu ChangeInfoMenu = new JMenu("个人信息修改");
		menuBar.add(ChangeInfoMenu);
		
//		JMenuItem changeIMenuItem = new JMenuItem("信息修改");
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
		// 信息修改
		if(e.getSource() == changeIMenuItem) {
			SelfFrame selfFrame = new SelfFrame(name, role);
			selfFrame.setVisible(true);
//			缺少关于窗口能否编辑的功能
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
