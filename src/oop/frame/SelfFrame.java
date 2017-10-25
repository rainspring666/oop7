package oop.frame;
/**
 * 个人信息管理界面   信息修改提示框
 */

import oop.main.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.SwingConstants;

public class SelfFrame extends JFrame implements ActionListener{

	String name1;//来自用户的信息
	String role1;
	User user = null; //保存用户的相关信息
	private JPanel contentPane;
	private JTextField uNtField;
	private JTextField uPwtField;
	private JTextField upwtField_new;
	private JTextField upwtField_new_2;
	private JTextField role;
	private JButton changeButton = new JButton("修改");
    private JButton cancleButton = new JButton("取消");


	/**
	 * Create the frame.
	 */
	public SelfFrame(String name1, String role1) {
		this.name1 = name1;
		this.role1 = role1;
		user = getUserInfo(name1);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭当前窗口而不关闭后面的
		setSize(350, 450);
		setTitle("个人信息修改");
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("用户名");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("楷体", Font.PLAIN, 18));
		lblNewLabel.setBounds(45, 61, 72, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("密码");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("楷体", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(45, 110, 72, 18);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("新密码");
		lblNewLabel_2.setFont(new Font("楷体", Font.PLAIN, 18));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(45, 166, 72, 18);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("确认密码");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("楷体", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(45, 221, 72, 18);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("角色");
		lblNewLabel_4.setFont(new Font("楷体", Font.PLAIN, 18));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(45, 269, 72, 18);
		contentPane.add(lblNewLabel_4);
		
//		
		changeButton.setFont(new Font("楷体", Font.PLAIN, 18));
		changeButton.setBounds(66, 337, 80, 27);
		contentPane.add(changeButton);
		changeButton.addActionListener(this);

//		JButton cancleButton = new JButton("取消");
		cancleButton.setFont(new Font("楷体", Font.PLAIN, 18));
		cancleButton.setBounds(191, 337, 80, 27);
		contentPane.add(cancleButton);
		cancleButton.addActionListener(this);
		
		uNtField = new JTextField();
		uNtField.setText(name1);
		uNtField.setFont(new Font("楷体", Font.PLAIN, 16));
		uNtField.setEnabled(false);
		uNtField.setBounds(151, 58, 130, 24);
		contentPane.add(uNtField);
		uNtField.setColumns(20);
		
		uPwtField = new JTextField();
		uPwtField.setBounds(151, 107, 130, 24);
		uPwtField.setFont(new Font("楷体", Font.PLAIN, 16));
		contentPane.add(uPwtField);
		uPwtField.setColumns(10);
		
		upwtField_new = new JTextField();
		upwtField_new.setFont(new Font("楷体", Font.PLAIN, 16));
		upwtField_new.setBounds(151, 163, 130, 24);
		contentPane.add(upwtField_new);
		upwtField_new.setColumns(10);
		
		upwtField_new_2 = new JTextField();
		upwtField_new_2.setFont(new Font("楷体", Font.PLAIN, 16));
		upwtField_new_2.setBounds(151, 218, 130, 24);
		contentPane.add(upwtField_new_2);
		upwtField_new_2.setColumns(10);
		
		role = new JTextField();
		role.setFont(new Font("楷体", Font.PLAIN, 16));
		role.setEnabled(false);
		role.setText(role1);
		role.setBounds(151, 266, 130, 24);
		contentPane.add(role);
		role.setColumns(20);
		
	}
	public User getUserInfo(String name1) { //获取当前用户信息
		try {
			return DataProcessing.search(name1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "       不好意思，数据库出错了", "异常处理", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource() == changeButton) {
			
			//密码确定 首先判断两次密码是否一样  而后判断原密码是否正确
			if(upwtField_new.getText().trim().equals(upwtField_new_2.getText().trim())) {
				if(user.getPassword().equals(uPwtField.getText().trim())) { 
					try {  //更新hash表中用户的信息
						if(DataProcessing.update(name1, upwtField_new.getText().trim(), role1) == true) {
							JOptionPane.showMessageDialog(this, "       亲，恭喜你修改成功了", "提示!", JOptionPane.INFORMATION_MESSAGE);
							DocClient.sendMessageToServer("CLIENT_SELF_MOD");
							this.dispose();
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(this, "       不好意思，数据库出错了", "异常处理", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(this, "    亲，你的密码不对哦", "出错啦！", JOptionPane.ERROR_MESSAGE);
				}	
		} else {
			JOptionPane.showMessageDialog(this, "       亲，两次新密码不一样哦", "出错啦！", JOptionPane.ERROR_MESSAGE);
		}

	}
	if(e.getSource() == cancleButton) {
			this.dispose();
	}
 }
}