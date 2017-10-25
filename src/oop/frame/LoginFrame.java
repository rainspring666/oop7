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
	private static JButton loginButton = new JButton("��¼");
	private static JButton exitButton = new JButton("�˳�");
	public static MainFrame mainFrame = new MainFrame();
	
	private static String name = null;
	private String password = null;

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
	
	//���ش��ڹرպ���ʵ�� ���ݿ�Ĺر�
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//					DataProcessing.closeDatabase();//�ر����ݿ�
				DocClient.sendMessageToServer("CLIENT>>> TERMINATE");//�ر���������
				System.exit(0);
			}
			});
		setSize(450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);//���ֹ�����Ϊ��
		setLocationRelativeTo(null);//��Ļ����
		setVisible(true);
		setResizable(false);
		
		JLabel uNameLabel = new JLabel("�û���");
		uNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		uNameLabel.setFont(new Font("����", Font.PLAIN, 18));
		uNameLabel.setBounds(75, 67, 72, 24);
		contentPane.add(uNameLabel);
		
		JLabel uPwdLabel = new JLabel("����");
		uPwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		uPwdLabel.setFont(new Font("����", Font.PLAIN, 18));
		uPwdLabel.setBounds(75, 114, 72, 24);
		contentPane.add(uPwdLabel);
		
		uNameField = new JTextField();
		uNameField.setBounds(180, 69, 180, 24);
		contentPane.add(uNameField);
//		uNameField.setFont(new Font("����", Font.PLAIN, 16));
		uNameField.setColumns(10);
		
		uPwdField = new JPasswordField();
		uPwdField.setBounds(180, 116, 180, 24);
//		uPwdField.setFont(new Font("����", Font.PLAIN, 16));
		contentPane.add(uPwdField);
		uPwdField.setColumns(10);
		
//		��¼��ť
//		JButton loginButton = new JButton("��¼");
		loginButton.setFont(new Font("����", Font.PLAIN, 18));
		loginButton.setBounds(117, 185, 90, 30);
		contentPane.add(loginButton);
		loginButton.addActionListener(this);//��Ӽ����¼�
		
//		�˳�����ť
//		JButton exitButton = new JButton("�˳�");
		exitButton.setFont(new Font("����", Font.PLAIN, 18));
		exitButton.setBounds(251, 185, 90, 30);
		contentPane.add(exitButton);
		exitButton.addActionListener(this);//��Ӽ����¼�
		
	}
	

	@SuppressWarnings({ "deprecation", "unused" })
	public void actionPerformed(ActionEvent e) {
		// �˳���ťʱ���������
		String roleAndError = null;//��������LoginUser�Ľṹ
		if(e.getSource() == exitButton) {
			System.exit(0);
		}
		// ��¼��ťʱ���������l
		if(e.getSource() == loginButton) {
			//��ȡ�ı�����ı� �ж��û�����
			name = uNameField.getText();
			password = String.valueOf(uPwdField.getPassword());
			
			DocClient.sendMessageToServer("CLIENT_LOGIN");
			
			roleAndError = LoginUser.judgeUser(name, password);
			
			DocClient.sendMessageToServer(name);
			//operator �û�Ȩ�� // �����¼����MainGUI
			if(roleAndError == "operator") {
				mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.userMMenu.setEnabled(false);
				mainFrame.name = name;//��ʼ�����û���
				mainFrame.role = "operator";
				this.dispose();//ͬʱ�Զ��رյ�¼����
			}
			// browser �û���Ȩ��//�����¼����MainGUI
			if(roleAndError.equals("browser")) {
				mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.userMMenu.setEnabled(false);
				mainFrame.upFMenuItem.setEnabled(false);
				mainFrame.name = name;//��ʼ�����û���
				mainFrame.role = "browser";
				this.dispose();//ͬʱ�Զ��رյ�¼����
			}
			//administrator �û�Ȩ��
			if(roleAndError.equals("administrator")) {
				mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.upFMenuItem.setEnabled(false);
				mainFrame.name = name;//��ʼ�����û���
				mainFrame.role = "administrator";
				this.dispose();//ͬʱ�Զ��رյ�¼����
			}
			//������쳣������
			if(roleAndError.equals(null)) {
				JOptionPane.showMessageDialog(this, "       �ף��û������������", "��������", 
						JOptionPane.ERROR_MESSAGE);  
				uNameField.setText("");
				uPwdField.setText("");
			}
			if(roleAndError.equals("SQLException")) {
				JOptionPane.showMessageDialog(this, "       �ף����ݿ��쳣", "�쳣����", 
						JOptionPane.ERROR_MESSAGE);
				uNameField.setText("");
				uPwdField.setText("");
				
			}
			

		}
	}
}
