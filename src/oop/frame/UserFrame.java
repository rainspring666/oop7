package oop.frame;
/**
 * ��fileFrameһ��������  ɾ���û�ʱ��hashmap�������ݵ������
 * �޸��û�ʱ �����б���û���ʾ����
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
	String header[] = { "Name", "Password", "Role" };   //��� 
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
			// TODO �Զ����ɵ� catch ��
			JOptionPane.showMessageDialog(this, "���ݿ��쳣", "�쳣����", JOptionPane.ERROR_MESSAGE); 
			e.printStackTrace();
		}
		this.name = name;//�����û�������
		setBounds(100, 100, 450, 315);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//�رյ�ǰ���ڶ����رպ����
		setTitle("�û��������");
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 444, 280);
		contentPane.add(tabbedPane);
		//����û����
		JPanel addPanel = new JPanel();
		addPanel.setLayout(null);
		tabbedPane.addTab("\u65B0\u589E\u7528\u6237", null, addPanel, null);
		
		JLabel userNameLabel = new JLabel("�û���");
		userNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userNameLabel.setFont(new Font("����", Font.PLAIN, 18));
		userNameLabel.setBounds(87, 40, 72, 18);
		addPanel.add(userNameLabel);
		
		JLabel userPwdLabel = new JLabel("����");
		userPwdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userPwdLabel.setFont(new Font("����", Font.PLAIN, 18));
		userPwdLabel.setBounds(87, 94, 72, 18);
		addPanel.add(userPwdLabel);
		
		JLabel userRoleLabel = new JLabel("��ɫ");
		userRoleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userRoleLabel.setFont(new Font("����", Font.PLAIN, 18));
		userRoleLabel.setBounds(87, 145, 72, 18);
		addPanel.add(userRoleLabel);
		
		userNameField = new JTextField();
//		userNameField.setText(null);
		userNameField.setHorizontalAlignment(SwingConstants.LEFT);
		userNameField.setBounds(204, 39, 130, 24);
		userNameField.setFont(new Font("����", Font.PLAIN, 16));
		addPanel.add(userNameField);
		userNameField.setColumns(10);
		
		userPwdField = new JTextField();
//		userPwdField.setText(null);
		userPwdField.setHorizontalAlignment(SwingConstants.LEFT);
		userPwdField.setBounds(204, 93, 130, 24);
		userPwdField.setFont(new Font("����", Font.PLAIN, 16));
		addPanel.add(userPwdField);
		userPwdField.setColumns(10);
		
		comboBox_addPanel = new JComboBox(labels_role);
		comboBox_addPanel.setBounds(204, 144, 130, 24);
		comboBox_addPanel.addActionListener(this);
		addPanel.add(comboBox_addPanel);
		
		addButton = new JButton("����");
		addButton.setFont(new Font("����", Font.PLAIN, 18));
		addButton.setBounds(103, 193, 90, 27);
		addButton.addActionListener(this);
		addPanel.add(addButton);
		
		cancleButton = new JButton("ȡ��");
		cancleButton.setFont(new Font("����", Font.PLAIN, 18));
		cancleButton.setBounds(256, 193, 90, 27);
		cancleButton.addActionListener(this);
		addPanel.add(cancleButton);
		
		JPanel changePanel = new JPanel();//�޸��û�����
		changePanel.setLayout(null);
		tabbedPane.addTab("�޸��û�", null, changePanel, null);

		JLabel userNameLabel_change = new JLabel("�û���");
		userNameLabel_change.setHorizontalAlignment(SwingConstants.RIGHT);
		userNameLabel_change.setFont(new Font("����", Font.PLAIN, 18));
		userNameLabel_change.setBounds(87, 40, 72, 18);
		changePanel.add(userNameLabel_change);
		
		JLabel userPwdLabel_change = new JLabel("����");
		userPwdLabel_change.setHorizontalAlignment(SwingConstants.RIGHT);
		userPwdLabel_change.setFont(new Font("����", Font.PLAIN, 18));
		userPwdLabel_change.setBounds(87, 94, 72, 18);
		changePanel.add(userPwdLabel_change);
		
		JLabel userRoleLabel_change = new JLabel("��ɫ");
		userRoleLabel_change.setHorizontalAlignment(SwingConstants.RIGHT);
		userRoleLabel_change.setFont(new Font("����", Font.PLAIN, 18));
		userRoleLabel_change.setBounds(87, 145, 72, 18);
		changePanel.add(userRoleLabel_change);
		
		userPwdField_change = new JTextField();
		userPwdField_change.setFont(new Font("����", Font.PLAIN, 16));
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
		changeButton_change.setFont(new Font("����", Font.PLAIN, 18));
		changeButton_change.setBounds(103, 193, 90, 27);
		changeButton_change.addActionListener(this);
		changePanel.add(changeButton_change);
		
		cancleButton_change = new JButton("ȡ��");
		cancleButton_change.setFont(new Font("����", Font.PLAIN, 18));
		cancleButton_change.setBounds(256, 193, 90, 27);
		cancleButton_change.addActionListener(this);
		changePanel.add(cancleButton_change);
		
		//�����б��е���ʾ�����û�
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
		
		delButton = new JButton("ɾ��");
		delButton.setFont(new Font("����", Font.PLAIN, 18));
		delButton.setBounds(103, 193, 90, 27);
		delButton.addActionListener(this);
		delPanel.add(delButton);

		cancelButton_del = new JButton("ȡ��");
		cancelButton_del.setFont(new Font("����", Font.PLAIN, 18));
		cancelButton_del.setBounds(256, 193, 90, 27);
		cancelButton_del.addActionListener(this);
		delPanel.add(cancelButton_del);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 15, 360, 160);
		delPanel.add(scrollPane);
		//��������ӵ������
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
		// TODO �Զ����ɵķ������
		if(e.getSource() == addButton) {
			
			boolean flag = false;
			int c = JOptionPane.showConfirmDialog(this,
			       "�������û���", "��ʾ", JOptionPane.YES_NO_OPTION,
			       JOptionPane.WARNING_MESSAGE, null);
			switch(c) {
				case JOptionPane.YES_NO_OPTION: {
					if(!userPwdField.getText().trim().equals("") && !userNameField.getText().trim().equals("")) {
						try {
							flag = DataProcessing.insert(userNameField.getText(), userPwdField.getText(), (String)comboBox_addPanel.getSelectedItem());}
						catch (SQLException e1) {
							JOptionPane.showMessageDialog(this, "       ���ݿ��쳣", "�쳣����", JOptionPane.ERROR_MESSAGE);  }
					} else {
						flag = false;
						JOptionPane.showMessageDialog(this, "       �������û���������", "�����ˣ�", JOptionPane.ERROR_MESSAGE);
					}
					//�ж��Ƿ�������
					if(flag == true) {
						JOptionPane.showMessageDialog(this, "       �ף������û��ɹ���", "��ʾ!", JOptionPane.INFORMATION_MESSAGE);
						DocClient.sendMessageToServer("CLIENT_USER_ADD");
						this.dispose();
					}else {
						JOptionPane.showMessageDialog(this, "       �ף������û�ʧ����", "�����ˣ�", JOptionPane.ERROR_MESSAGE);
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
		
		//ɾ���İ�ť����
		if(e.getSource() == delButton) {
			
			try{
			int choose = JOptionPane.showConfirmDialog(this, "����ɾ�����û���","��ʾ��",JOptionPane.YES_NO_OPTION,
				       JOptionPane.WARNING_MESSAGE, null);
			switch(choose) {
			case JOptionPane.YES_NO_OPTION:{
				int row = table.getSelectedRow();
				if(row != -1) {
					String name = (String)table.getValueAt(row, 0);//��ȡ��Ӧ������ֵ
					 if(DataProcessing.delete(name) == true) {
						 //��ʾ
						 JOptionPane.showMessageDialog(this.getContentPane(),
								 "ɾ���û��ɹ�", "��ʾ��", JOptionPane.INFORMATION_MESSAGE);
						 DocClient.sendMessageToServer("CLIENT_USER_DEL");
						 this.dispose();
					 } else {
						 //��ʾ
						 JOptionPane.showMessageDialog(this, "ɾ���û�ʧ��", "�����ˣ�", JOptionPane.ERROR_MESSAGE); 
					 }
				}
			}
			case JOptionPane.NO_OPTION:{
//				this.dispose();
			}
			}
		} catch(SQLException e1) {
			//����
			JOptionPane.showMessageDialog(this, "���ݿ��쳣", "�쳣����", JOptionPane.ERROR_MESSAGE); 
		} catch(NullPointerException e1) {
			JOptionPane.showMessageDialog(this, "��ѡ����ڵ��û�", "�����ˣ�", JOptionPane.ERROR_MESSAGE); 
		}
		} else if(e.getSource() == cancelButton_del) {
			this.dispose();
		}
		
		//�޸��û�����
		if(e.getSource() == changeButton_change) {
			
			if(!userPwdField_change.getText().trim().equals("")) {
			
			try{
				int choose = JOptionPane.showConfirmDialog(this, "�����޸Ĵ��û���","��ʾ��",JOptionPane.YES_NO_OPTION,
					       JOptionPane.WARNING_MESSAGE, null);
				switch(choose) {
				case JOptionPane.YES_NO_OPTION:{
					
					DataProcessing.update((String)comboBox_userName.getSelectedItem(), userPwdField_change.getText().trim(), (String)comboBox_change.getSelectedItem());
					JOptionPane.showMessageDialog(this.getContentPane(),
							 "�޸��û��ɹ�", "��ʾ��", JOptionPane.INFORMATION_MESSAGE);
					DocClient.sendMessageToServer("CLIENT_USER_CHA");
					this.dispose();
				}
				case JOptionPane.NO_OPTION:{
//					this.dispose();
				}
				}
			} catch(SQLException e1) {
				//����
				JOptionPane.showMessageDialog(this, "���ݿ��쳣", "�쳣����", JOptionPane.ERROR_MESSAGE); 
			} catch(NullPointerException e1) {
				JOptionPane.showMessageDialog(this, "��ѡ����ڵ��û�", "�����ˣ�", JOptionPane.ERROR_MESSAGE); 
			}
			} else {
				JOptionPane.showMessageDialog(this, "����������µ�����", "�����ˣ�", JOptionPane.ERROR_MESSAGE); 
			}
		} else if(e.getSource() == cancleButton_change) {
			this.dispose();
		}
		
		

			
			
		
		
		

		
	}
}
