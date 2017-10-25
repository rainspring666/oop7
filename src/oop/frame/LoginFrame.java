package oop.frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import oop.main.*;

public class LoginFrame extends JFrame implements ActionListener{

	private static JPanel contentPane;
	private static JTextField uNameField;
	private static JPasswordField uPwdField;
	private static JButton loginButton = new JButton("登录");
	private static JButton exitButton = new JButton("退出");
	public static MainFrame mainFrame = new MainFrame();
	
	private static String name = null;
	private String password = null;

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
	
	//重载窗口关闭函数实现 数据库的关闭
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//					DataProcessing.closeDatabase();//关闭数据库
				DocClient.sendMessageToServer("CLIENT>>> TERMINATE");//关闭网络连接
				System.exit(0);
			}
			});
		setSize(450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);//布局管理器为空
		setLocationRelativeTo(null);//屏幕中央
		setVisible(true);
		setResizable(false);
		
		JLabel uNameLabel = new JLabel("用户名");
		uNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		uNameLabel.setFont(new Font("楷体", Font.PLAIN, 18));
		uNameLabel.setBounds(75, 67, 72, 24);
		contentPane.add(uNameLabel);
		
		JLabel uPwdLabel = new JLabel("密码");
		uPwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		uPwdLabel.setFont(new Font("楷体", Font.PLAIN, 18));
		uPwdLabel.setBounds(75, 114, 72, 24);
		contentPane.add(uPwdLabel);
		
		uNameField = new JTextField();
		uNameField.setBounds(180, 69, 180, 24);
		contentPane.add(uNameField);
//		uNameField.setFont(new Font("楷体", Font.PLAIN, 16));
		uNameField.setColumns(10);
		
		uPwdField = new JPasswordField();
		uPwdField.setBounds(180, 116, 180, 24);
//		uPwdField.setFont(new Font("楷体", Font.PLAIN, 16));
		contentPane.add(uPwdField);
		uPwdField.setColumns(10);
		
//		登录按钮
//		JButton loginButton = new JButton("登录");
		loginButton.setFont(new Font("楷体", Font.PLAIN, 18));
		loginButton.setBounds(117, 185, 90, 30);
		contentPane.add(loginButton);
		loginButton.addActionListener(this);//添加监听事件
		
//		退出程序按钮
//		JButton exitButton = new JButton("退出");
		exitButton.setFont(new Font("楷体", Font.PLAIN, 18));
		exitButton.setBounds(251, 185, 90, 30);
		contentPane.add(exitButton);
		exitButton.addActionListener(this);//添加监听事件
		
	}
	

	@SuppressWarnings({ "deprecation", "unused" })
	public void actionPerformed(ActionEvent e) {
		// 退出按钮时间监听处理
		String roleAndError = null;//接受来自LoginUser的结构
		if(e.getSource() == exitButton) {
			System.exit(0);
		}
		// 登录按钮时间监听处理l
		if(e.getSource() == loginButton) {
			//获取文本框的文本 判断用户类型
			name = uNameField.getText();
			password = String.valueOf(uPwdField.getPassword());
			
			DocClient.sendMessageToServer("CLIENT_LOGIN");
			
			roleAndError = LoginUser.judgeUser(name, password);
			
			DocClient.sendMessageToServer(name);
			//operator 用户权限 // 点击登录开启MainGUI
			if(roleAndError == "operator") {
				mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.userMMenu.setEnabled(false);
				mainFrame.name = name;//开始传递用户名
				mainFrame.role = "operator";
				this.dispose();//同时自动关闭登录窗口
			}
			// browser 用户的权限//点击登录开启MainGUI
			if(roleAndError.equals("browser")) {
				mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.userMMenu.setEnabled(false);
				mainFrame.upFMenuItem.setEnabled(false);
				mainFrame.name = name;//开始传递用户名
				mainFrame.role = "browser";
				this.dispose();//同时自动关闭登录窗口
			}
			//administrator 用户权限
			if(roleAndError.equals("administrator")) {
				mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.upFMenuItem.setEnabled(false);
				mainFrame.name = name;//开始传递用户名
				mainFrame.role = "administrator";
				this.dispose();//同时自动关闭登录窗口
			}
			//错误和异常处理窗口
			if(roleAndError.equals(null)) {
				JOptionPane.showMessageDialog(this, "       亲，用户名或密码错误", "出错啦！", 
						JOptionPane.ERROR_MESSAGE);  
				uNameField.setText("");
				uPwdField.setText("");
			}
			if(roleAndError.equals("SQLException")) {
				JOptionPane.showMessageDialog(this, "       亲，数据库异常", "异常处理！", 
						JOptionPane.ERROR_MESSAGE);
				uNameField.setText("");
				uPwdField.setText("");
				
			}
			

		}
	}
}
