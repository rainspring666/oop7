package oop.frame;
/**
 * 和fileFrame一样的问题  删除用户时从hashmap导入数据到表格里
 * 修改用户时 下拉列表的用户显示问题
 * 
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import oop.main.DataProcessing;
import oop.main.Doc;
import oop.main.DocClient;
import oop.main.User;

import javax.swing.JScrollPane;

public class UserFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	Object[][] obj = new Object[10][3] ;
	String name = null;
	String header[] = { "Name", "Password", "Role" };   //身份 
	String labels_role[] = { "operator", "browser", "administrator" };
	private JTextField userNameField ;
	private JTextField userPwdField ;
	private JTextField userPwdField_change;
	
	private JButton addButton;
	private JButton cancleButton;
	private JButton changeButton_change;
	private JButton cancleButton_change;
	private JButton delButton;
	private JButton cancelButton_del;
	
	private JComboBox comboBox_addPanel;
	private JComboBox comboBox_userName;
	private JComboBox comboBox_change;
	private JTable table;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public UserFrame(String name) {
		try {
			DataProcessing.getAllUser();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			JOptionPane.showMessageDialog(this, "数据库异常", "异常处理", JOptionPane.ERROR_MESSAGE); 
			e.printStackTrace();
		}
		this.name = name;//传递用户的名字
		setBounds(100, 100, 450, 315);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭当前窗口而不关闭后面的
		setTitle("用户管理界面");
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 444, 280);
		contentPane.add(tabbedPane);
		//添加用户面板
		JPanel addPanel = new JPanel();
		addPanel.setLayout(null);
		tabbedPane.addTab("\u65B0\u589E\u7528\u6237", null, addPanel, null);
		
		JLabel userNameLabel = new JLabel("用户名");
		userNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userNameLabel.setFont(new Font("楷体", Font.PLAIN, 18));
		userNameLabel.setBounds(87, 40, 72, 18);
		addPanel.add(userNameLabel);
		
		JLabel userPwdLabel = new JLabel("密码");
		userPwdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userPwdLabel.setFont(new Font("楷体", Font.PLAIN, 18));
		userPwdLabel.setBounds(87, 94, 72, 18);
		addPanel.add(userPwdLabel);
		
		JLabel userRoleLabel = new JLabel("角色");
		userRoleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userRoleLabel.setFont(new Font("楷体", Font.PLAIN, 18));
		userRoleLabel.setBounds(87, 145, 72, 18);
		addPanel.add(userRoleLabel);
		
		userNameField = new JTextField();
//		userNameField.setText(null);
		userNameField.setHorizontalAlignment(SwingConstants.LEFT);
		userNameField.setBounds(204, 39, 130, 24);
		userNameField.setFont(new Font("楷体", Font.PLAIN, 16));
		addPanel.add(userNameField);
		userNameField.setColumns(10);
		
		userPwdField = new JTextField();
//		userPwdField.setText(null);
		userPwdField.setHorizontalAlignment(SwingConstants.LEFT);
		userPwdField.setBounds(204, 93, 130, 24);
		userPwdField.setFont(new Font("楷体", Font.PLAIN, 16));
		addPanel.add(userPwdField);
		userPwdField.setColumns(10);
		
		comboBox_addPanel = new JComboBox(labels_role);
		comboBox_addPanel.setBounds(204, 144, 130, 24);
		comboBox_addPanel.addActionListener(this);
		addPanel.add(comboBox_addPanel);
		
		addButton = new JButton("新增");
		addButton.setFont(new Font("楷体", Font.PLAIN, 18));
		addButton.setBounds(103, 193, 90, 27);
		addButton.addActionListener(this);
		addPanel.add(addButton);
		
		cancleButton = new JButton("取消");
		cancleButton.setFont(new Font("楷体", Font.PLAIN, 18));
		cancleButton.setBounds(256, 193, 90, 27);
		cancleButton.addActionListener(this);
		addPanel.add(cancleButton);
		
		JPanel changePanel = new JPanel();//修改用户面盘
		changePanel.setLayout(null);
		tabbedPane.addTab("修改用户", null, changePanel, null);

		JLabel userNameLabel_change = new JLabel("用户名");
		userNameLabel_change.setHorizontalAlignment(SwingConstants.RIGHT);
		userNameLabel_change.setFont(new Font("楷体", Font.PLAIN, 18));
		userNameLabel_change.setBounds(87, 40, 72, 18);
		changePanel.add(userNameLabel_change);
		
		JLabel userPwdLabel_change = new JLabel("密码");
		userPwdLabel_change.setHorizontalAlignment(SwingConstants.RIGHT);
		userPwdLabel_change.setFont(new Font("楷体", Font.PLAIN, 18));
		userPwdLabel_change.setBounds(87, 94, 72, 18);
		changePanel.add(userPwdLabel_change);
		
		JLabel userRoleLabel_change = new JLabel("角色");
		userRoleLabel_change.setHorizontalAlignment(SwingConstants.RIGHT);
		userRoleLabel_change.setFont(new Font("楷体", Font.PLAIN, 18));
		userRoleLabel_change.setBounds(87, 145, 72, 18);
		changePanel.add(userRoleLabel_change);
		
		userPwdField_change = new JTextField();
		userPwdField_change.setFont(new Font("楷体", Font.PLAIN, 16));
		userPwdField_change.setHorizontalAlignment(SwingConstants.LEFT);
		userPwdField_change.setBounds(204, 93, 130, 24);
		changePanel.add(userPwdField_change);
		userPwdField_change.setColumns(10);
		
		comboBox_change = new JComboBox(labels_role);
		comboBox_change.setSize(130, 24);
		comboBox_change.setLocation(204, 144);
		comboBox_change.setBounds(204, 144, 130, 24);
		changePanel.add(comboBox_change);
		
		changeButton_change = new JButton("\u4FEE\u6539");
		changeButton_change.setFont(new Font("楷体", Font.PLAIN, 18));
		changeButton_change.setBounds(103, 193, 90, 27);
		changeButton_change.addActionListener(this);
		changePanel.add(changeButton_change);
		
		cancleButton_change = new JButton("取消");
		cancleButton_change.setFont(new Font("楷体", Font.PLAIN, 18));
		cancleButton_change.setBounds(256, 193, 90, 27);
		cancleButton_change.addActionListener(this);
		changePanel.add(cancleButton_change);
		
		//下拉列表中的显示所用用户
		Enumeration<User> e1 = DataProcessing.users.elements();
		User user1;
		Vector v1 = new Vector();
		while(e1.hasMoreElements()) {
			user1 = e1.nextElement();
			v1.addElement(user1.getName());

		}
		comboBox_userName = new JComboBox(v1);
		comboBox_userName.setBounds(204, 39, 130, 24);
		changePanel.add(comboBox_userName);
		
		JPanel delPanel = new JPanel();
		delPanel.setLayout(null);
		tabbedPane.addTab("\u5220\u9664\u7528\u6237", null, delPanel, null);
		
		delButton = new JButton("删除");
		delButton.setFont(new Font("楷体", Font.PLAIN, 18));
		delButton.setBounds(103, 193, 90, 27);
		delButton.addActionListener(this);
		delPanel.add(delButton);

		cancelButton_del = new JButton("取消");
		cancelButton_del.setFont(new Font("楷体", Font.PLAIN, 18));
		cancelButton_del.setBounds(256, 193, 90, 27);
		cancelButton_del.addActionListener(this);
		delPanel.add(cancelButton_del);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 15, 360, 160);
		delPanel.add(scrollPane);
		//将数据添加到表格中
		DefaultTableModel DocData  = new DefaultTableModel(header, 0);
		Enumeration<User> e = DataProcessing.users.elements();
		User user;
		while(e.hasMoreElements()) {
			user = e.nextElement();
			Vector v = new Vector();
			v.addElement(user.getName());
			v.addElement(user.getPassword());
			v.addElement(user.getRole());
			DocData.addRow(v);
		}
		
		table = new JTable();
		table.setModel(DocData);
		table.setVisible(true);
		scrollPane.setViewportView(table);
	}
	public void setDefaultTab(int defaultTab){

		((JTabbedPane)contentPane.getComponent(0)).setSelectedIndex(defaultTab);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource() == addButton) {
			
			boolean flag = false;
			int c = JOptionPane.showConfirmDialog(this,
			       "新增此用户？", "提示", JOptionPane.YES_NO_OPTION,
			       JOptionPane.WARNING_MESSAGE, null);
			switch(c) {
				case JOptionPane.YES_NO_OPTION: {
					if(!userPwdField.getText().trim().equals("") && !userNameField.getText().trim().equals("")) {
						try {
							flag = DataProcessing.insert(userNameField.getText(), userPwdField.getText(), (String)comboBox_addPanel.getSelectedItem());}
						catch (SQLException e1) {
							JOptionPane.showMessageDialog(this, "       数据库异常", "异常处理！", JOptionPane.ERROR_MESSAGE);  }
					} else {
						flag = false;
						JOptionPane.showMessageDialog(this, "       请输入用户名和密码", "出错了！", JOptionPane.ERROR_MESSAGE);
					}
					//判断是否新增成
					if(flag == true) {
						JOptionPane.showMessageDialog(this, "       亲，新增用户成功了", "提示!", JOptionPane.INFORMATION_MESSAGE);
						DocClient.sendMessageToServer("CLIENT_USER_ADD");
						this.dispose();
					}else {
						JOptionPane.showMessageDialog(this, "       亲，新增用户失败了", "出错了！", JOptionPane.ERROR_MESSAGE);
					}
//					break;
				}
				case JOptionPane.NO_OPTION:{
//					this.dispose();
				}
			}
		}else if(e.getSource() == cancleButton) {
			this.dispose();
//			this.removeAll();
		}
		
		//删除的按钮操作
		if(e.getSource() == delButton) {
			
			try{
			int choose = JOptionPane.showConfirmDialog(this, "继续删除此用户？","提示！",JOptionPane.YES_NO_OPTION,
				       JOptionPane.WARNING_MESSAGE, null);
			switch(choose) {
			case JOptionPane.YES_NO_OPTION:{
				int row = table.getSelectedRow();
				if(row != -1) {
					String name = (String)table.getValueAt(row, 0);//获取对应行数的值
					 if(DataProcessing.delete(name) == true) {
						 //提示
						 JOptionPane.showMessageDialog(this.getContentPane(),
								 "删除用户成功", "提示！", JOptionPane.INFORMATION_MESSAGE);
						 DocClient.sendMessageToServer("CLIENT_USER_DEL");
						 this.dispose();
					 } else {
						 //提示
						 JOptionPane.showMessageDialog(this, "删除用户失败", "出错了！", JOptionPane.ERROR_MESSAGE); 
					 }
				}
			}
			case JOptionPane.NO_OPTION:{
//				this.dispose();
			}
			}
		} catch(SQLException e1) {
			//警告
			JOptionPane.showMessageDialog(this, "数据库异常", "异常处理", JOptionPane.ERROR_MESSAGE); 
		} catch(NullPointerException e1) {
			JOptionPane.showMessageDialog(this, "请选择存在的用户", "出错了！", JOptionPane.ERROR_MESSAGE); 
		}
		} else if(e.getSource() == cancelButton_del) {
			this.dispose();
		}
		
		//修改用户操作
		if(e.getSource() == changeButton_change) {
			
			if(!userPwdField_change.getText().trim().equals("")) {
			
			try{
				int choose = JOptionPane.showConfirmDialog(this, "继续修改此用户？","提示！",JOptionPane.YES_NO_OPTION,
					       JOptionPane.WARNING_MESSAGE, null);
				switch(choose) {
				case JOptionPane.YES_NO_OPTION:{
					
					DataProcessing.update((String)comboBox_userName.getSelectedItem(), userPwdField_change.getText().trim(), (String)comboBox_change.getSelectedItem());
					JOptionPane.showMessageDialog(this.getContentPane(),
							 "修改用户成功", "提示！", JOptionPane.INFORMATION_MESSAGE);
					DocClient.sendMessageToServer("CLIENT_USER_CHA");
					this.dispose();
				}
				case JOptionPane.NO_OPTION:{
//					this.dispose();
				}
				}
			} catch(SQLException e1) {
				//警告
				JOptionPane.showMessageDialog(this, "数据库异常", "异常处理", JOptionPane.ERROR_MESSAGE); 
			} catch(NullPointerException e1) {
				JOptionPane.showMessageDialog(this, "请选择存在的用户", "出错了！", JOptionPane.ERROR_MESSAGE); 
			}
			} else {
				JOptionPane.showMessageDialog(this, "你必须输入新的密码", "出错了！", JOptionPane.ERROR_MESSAGE); 
			}
		} else if(e.getSource() == cancleButton_change) {
			this.dispose();
		}
		
		

			
			
		
		
		

		
	}
}
