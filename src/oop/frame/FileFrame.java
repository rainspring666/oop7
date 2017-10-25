package oop.frame;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import oop.main.DataProcessing;
import oop.main.Doc;
import oop.main.DocClient;


public class FileFrame extends JFrame implements ActionListener{
	private String name,upOrdown;
	private JPanel contentPane;
	String[] header =  { "ID", "Creator", "Timestamp","Description", "Filename" };  
//	Object[][] obj = new Object[15][5];
	private String upFilePath = "E:\\OOPFile\\uploadfile\\";//�����ļ���·��
	String fileNamePath =null;
	String id  = null;
//	int id = 0;
	String des = null;
	private JTextField docNumField;
	private JTextField docPathField;
	private JTextArea docDesArea;
	private JButton downButton_cancle = new JButton("ȡ��");
	private JButton downButton_down = new JButton("����");
	private JButton upButton_cancle = new JButton("ȡ��");
	private JButton upButton_up = new JButton("�ϴ�");
	private JButton filePathButton;
	private FileDialog openDia; //ѡ���ļ� 
	private FileDialog saveDia; //ѡ���ļ� 
	private JScrollPane scrollPane;
	private JTable table;
	private File downfile;


	/**
	 * Create the frame.
	 */
	public FileFrame(String name) {
		try {
			DataProcessing.getAllDoc();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			JOptionPane.showMessageDialog(this, "���ݿ��쳣", "�쳣����", JOptionPane.ERROR_MESSAGE); 
			e.printStackTrace();
		}
		this.name = name;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//�رյ�ǰ���ڶ����رպ����
		setSize(520, 390);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("�ļ��������");
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 514, 355);
		contentPane.add(tabbedPane);
		
		JPanel downPanel = new JPanel();
		downPanel.setLayout(null);
		tabbedPane.addTab("�ļ�����", null, downPanel, null);

		downButton_down.setFont(new Font("����", Font.PLAIN, 18));
		downButton_down.setBounds(133, 269, 90, 27);
		downButton_down.addActionListener(this);
		downPanel.add(downButton_down);
		
	
		downButton_cancle.setFont(new Font("����", Font.PLAIN, 18));
		downButton_cancle.setBounds(284, 269, 90, 27);
		downButton_cancle.addActionListener(this);
		downPanel.add(downButton_cancle);
			
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(39, 29, 433, 209);
		downPanel.add(scrollPane);
		//����hashmap��  ���浽������ʾ
		DefaultTableModel DocData  = new DefaultTableModel(header, 0);
		Enumeration<Doc> e = DataProcessing.docs.elements();
		Doc doc;
		while(e.hasMoreElements()) {
			doc = e.nextElement();
			Vector v = new Vector();
			v.addElement(doc.getID());
			v.addElement(doc.getCreator());
			v.addElement(doc.getTimestamp());
			v.addElement(doc.getDescription());
			v.addElement(doc.getFilename());
			DocData.addRow(v);
		}
		
		table = new JTable();
		table.setModel(DocData);
		table.setVisible(true);
		scrollPane.setViewportView(table);
		
		
		tabbedPane.setEnabledAt(0, true);
		
		JPanel upPanel = new JPanel();
		upPanel.setLayout(null);
		tabbedPane.addTab("�ļ��ϴ�", null, upPanel, null);
		
		JLabel docNumLabel = new JLabel("������");
		docNumLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docNumLabel.setFont(new Font("����", Font.PLAIN, 18));
		docNumLabel.setBounds(70, 53, 72, 18);
		upPanel.add(docNumLabel);
		
		JLabel docDesLabel = new JLabel("��������");
		docDesLabel.setFont(new Font("����", Font.PLAIN, 18));
		docDesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docDesLabel.setBounds(70, 114, 72, 18);
		upPanel.add(docDesLabel);
		
		JLabel docPathLabel = new JLabel("����·��");
		docPathLabel.setFont(new Font("����", Font.PLAIN, 18));
		docPathLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docPathLabel.setBounds(70, 208, 72, 18);
		upPanel.add(docPathLabel);
		
		docNumField = new JTextField();
		docNumField.setBounds(182, 50, 203, 24);
		upPanel.add(docNumField);
		docNumField.setColumns(10);
		
		docDesArea = new JTextArea();
		docDesArea.setBounds(182, 112, 203, 64);
		upPanel.add(docDesArea);
		docDesArea.setColumns(50);
		
		docPathField = new JTextField();
		docPathField.setBounds(182, 205, 203, 24);
		upPanel.add(docPathField);
		docPathField.setColumns(10);

		upButton_up.setFont(new Font("����", Font.PLAIN, 18));
		upButton_up.setBounds(133, 269, 90, 27);
		upButton_up.addActionListener(this);
		upPanel.add(upButton_up);
		

		upButton_cancle.setFont(new Font("����", Font.PLAIN, 18));
		upButton_cancle.setBounds(284, 269, 90, 27);
		upButton_cancle.addActionListener(this);
		upPanel.add(upButton_cancle);
		
		filePathButton = new JButton("��");
		filePathButton.setFont(new Font("����", Font.PLAIN, 15));
		filePathButton.setBounds(416, 206, 90, 27);
		filePathButton.addActionListener(this);
		upPanel.add(filePathButton);

	}

	public void setDefaultTab(int defaultTab){

		((JTabbedPane)contentPane.getComponent(0)).setSelectedIndex(defaultTab);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource() == downButton_down) {

			//�ļ�����ѡ��Ի���
			File downfile = null;
			saveDia = new FileDialog(this,"���ļ�",FileDialog.LOAD); 
			saveDia.setVisible(true);
			if(saveDia.getFile()!=null) 
	            downfile = new File(saveDia.getDirectory(),saveDia.getFile());
	            
			int choose = JOptionPane.showConfirmDialog(this, "�������ش��ļ�","��ʾ��",JOptionPane.YES_NO_OPTION,
				       JOptionPane.WARNING_MESSAGE, null);
			switch(choose) {
			case JOptionPane.YES_NO_OPTION:{
				try{
					int row = table.getSelectedRow();//��ȡ����
					if(row != -1){

					String a = (String)table.getValueAt(row, 0);//��ȡ��Ӧ������ֵ
					Doc doc;
						doc = DataProcessing.searchDoc(a);
					//�ļ��Ķ�ȡ  ��ȡ�����ļ������·��  �����µ��ļ�·��������һ��

					DocClient.file_down(downfile);

					JOptionPane.showMessageDialog(this.getContentPane(),
							 "�ļ����سɹ�", "��ʾ��", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(this, "�ļ�����ʧ��", "�����ˣ�", JOptionPane.ERROR_MESSAGE); 
					}
				}catch(SQLException e1){
					JOptionPane.showMessageDialog(this, "���ݿ��쳣", "�쳣����", JOptionPane.ERROR_MESSAGE); 
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(this, "��ѡ����ڵ��ļ�", "�쳣����", JOptionPane.ERROR_MESSAGE);
				}catch (ClassNotFoundException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				} catch (IOException e1) {
//					 TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
//			this.dispose();
	    	 break;
	    	 }
			case JOptionPane.NO_OPTION: {//ѡ���
//				this.dispose();
			}
			}
		}else if(e.getSource() == downButton_cancle) {
			this.dispose();
		}
		//�ļ�·����ѡ��
		if(e.getSource() == filePathButton) {
			openDia = new FileDialog(this,"���ļ�",FileDialog.LOAD); 
			openDia.setVisible(true);
			fileNamePath= openDia.getDirectory()+openDia.getFile();//��ȡ�ļ�·��  
			docPathField.setText(fileNamePath);	
	    }
		//�ļ��ϴ�
		if(e.getSource() == upButton_up){//�ϴ�
			
			int choose = JOptionPane.showConfirmDialog(this,
				       "�����ϴ��ļ���", "��ʾ��", JOptionPane.YES_NO_OPTION,
				       JOptionPane.WARNING_MESSAGE, null);
		   switch (choose) {
			     case JOptionPane.YES_NO_OPTION: {
			    	 boolean flag = false;
			    	 fileNamePath = docPathField.getText().trim();
			    	 id = docNumField.getText().trim();

			    	 try{
			    		 des = docDesArea.getText().trim();
			    	 }catch (NullPointerException e1) {
			    		 JOptionPane.showMessageDialog(this, "�㻹û����������", "�����ˣ�", JOptionPane.ERROR_MESSAGE);
			    		 flag = false;
			    	 }
			    	 
			    	 File tempFile = new File(fileNamePath);
			    	 String fileName = tempFile.getName();
			
			    	 try{
			    		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			    		 if( DocClient.file_up(id, des, tempFile))//�����ļ��ϴ�
			    			 {flag = true;
			    			  
			    			 }
						DataProcessing.insertDoc(id, name, timestamp, des, fileName);
			    	}catch (ClassNotFoundException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
					}catch(SQLException e1){
			    		 e1.printStackTrace();
			    		 JOptionPane.showMessageDialog(this, "���ݿ��쳣", "�쳣����", JOptionPane.ERROR_MESSAGE); 
			    		 flag = false;
//			    		 this.dispose();
			    	 }catch(IOException e1){
			    		 JOptionPane.showMessageDialog(this, "�ļ���ȡ�쳣", "�쳣����", JOptionPane.ERROR_MESSAGE); 
			    		 flag = false;
//			    		 this.dispose();
			    	 }
			    	 if(flag == true) {
			    		 JOptionPane.showMessageDialog(this.getContentPane(),
					 "�ļ��ϴ��ɹ�", "��ʾ��", JOptionPane.INFORMATION_MESSAGE);
			    	 this.dispose();
			    	 } else {
			    		 JOptionPane.showMessageDialog(this.getContentPane(),
								 "�ļ��ϴ�ʧ��", "�����ˣ�", JOptionPane.INFORMATION_MESSAGE);
			    	 }
			    	 }
			     case JOptionPane.NO_OPTION:{
//			    	 this.dispose();
			     }
		   }
		}else if(e.getSource() == upButton_cancle) {//ȡ����  �رյ�ǰ����
			this.dispose();
		}

}
	}

//*********************  MyTableModel()  **************?//
