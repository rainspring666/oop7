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
	private String upFilePath = "E:\\OOPFile\\uploadfile\\";//本地文件夹路径
	String fileNamePath =null;
	String id  = null;
//	int id = 0;
	String des = null;
	private JTextField docNumField;
	private JTextField docPathField;
	private JTextArea docDesArea;
	private JButton downButton_cancle = new JButton("取消");
	private JButton downButton_down = new JButton("下载");
	private JButton upButton_cancle = new JButton("取消");
	private JButton upButton_up = new JButton("上传");
	private JButton filePathButton;
	private FileDialog openDia; //选择文件 
	private FileDialog saveDia; //选择文件 
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
			// TODO 自动生成的 catch 块
			JOptionPane.showMessageDialog(this, "数据库异常", "异常处理", JOptionPane.ERROR_MESSAGE); 
			e.printStackTrace();
		}
		this.name = name;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭当前窗口而不关闭后面的
		setSize(520, 390);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("文件管理界面");
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 514, 355);
		contentPane.add(tabbedPane);
		
		JPanel downPanel = new JPanel();
		downPanel.setLayout(null);
		tabbedPane.addTab("文件下载", null, downPanel, null);

		downButton_down.setFont(new Font("楷体", Font.PLAIN, 18));
		downButton_down.setBounds(133, 269, 90, 27);
		downButton_down.addActionListener(this);
		downPanel.add(downButton_down);
		
	
		downButton_cancle.setFont(new Font("楷体", Font.PLAIN, 18));
		downButton_cancle.setBounds(284, 269, 90, 27);
		downButton_cancle.addActionListener(this);
		downPanel.add(downButton_cancle);
			
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(39, 29, 433, 209);
		downPanel.add(scrollPane);
		//来自hashmap的  保存到表中显示
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
		tabbedPane.addTab("文件上传", null, upPanel, null);
		
		JLabel docNumLabel = new JLabel("档案号");
		docNumLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docNumLabel.setFont(new Font("楷体", Font.PLAIN, 18));
		docNumLabel.setBounds(70, 53, 72, 18);
		upPanel.add(docNumLabel);
		
		JLabel docDesLabel = new JLabel("档案描述");
		docDesLabel.setFont(new Font("楷体", Font.PLAIN, 18));
		docDesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docDesLabel.setBounds(70, 114, 72, 18);
		upPanel.add(docDesLabel);
		
		JLabel docPathLabel = new JLabel("档案路径");
		docPathLabel.setFont(new Font("楷体", Font.PLAIN, 18));
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

		upButton_up.setFont(new Font("楷体", Font.PLAIN, 18));
		upButton_up.setBounds(133, 269, 90, 27);
		upButton_up.addActionListener(this);
		upPanel.add(upButton_up);
		

		upButton_cancle.setFont(new Font("楷体", Font.PLAIN, 18));
		upButton_cancle.setBounds(284, 269, 90, 27);
		upButton_cancle.addActionListener(this);
		upPanel.add(upButton_cancle);
		
		filePathButton = new JButton("打开");
		filePathButton.setFont(new Font("楷体", Font.PLAIN, 15));
		filePathButton.setBounds(416, 206, 90, 27);
		filePathButton.addActionListener(this);
		upPanel.add(filePathButton);

	}

	public void setDefaultTab(int defaultTab){

		((JTabbedPane)contentPane.getComponent(0)).setSelectedIndex(defaultTab);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource() == downButton_down) {

			//文件下载选择对话框
			File downfile = null;
			saveDia = new FileDialog(this,"打开文件",FileDialog.LOAD); 
			saveDia.setVisible(true);
			if(saveDia.getFile()!=null) 
	            downfile = new File(saveDia.getDirectory(),saveDia.getFile());
	            
			int choose = JOptionPane.showConfirmDialog(this, "继续下载此文件","提示！",JOptionPane.YES_NO_OPTION,
				       JOptionPane.WARNING_MESSAGE, null);
			switch(choose) {
			case JOptionPane.YES_NO_OPTION:{
				try{
					int row = table.getSelectedRow();//获取行数
					if(row != -1){

					String a = (String)table.getValueAt(row, 0);//获取对应行数的值
					Doc doc;
						doc = DataProcessing.searchDoc(a);
					//文件的读取  获取下载文件保存的路径  创建新的文件路径传给下一级

					DocClient.file_down(downfile);

					JOptionPane.showMessageDialog(this.getContentPane(),
							 "文件下载成功", "提示！", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(this, "文件下载失败", "出错了！", JOptionPane.ERROR_MESSAGE); 
					}
				}catch(SQLException e1){
					JOptionPane.showMessageDialog(this, "数据库异常", "异常处理", JOptionPane.ERROR_MESSAGE); 
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(this, "请选择存在的文件", "异常处理", JOptionPane.ERROR_MESSAGE);
				}catch (ClassNotFoundException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (IOException e1) {
//					 TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
//			this.dispose();
	    	 break;
	    	 }
			case JOptionPane.NO_OPTION: {//选择否
//				this.dispose();
			}
			}
		}else if(e.getSource() == downButton_cancle) {
			this.dispose();
		}
		//文件路径的选择
		if(e.getSource() == filePathButton) {
			openDia = new FileDialog(this,"打开文件",FileDialog.LOAD); 
			openDia.setVisible(true);
			fileNamePath= openDia.getDirectory()+openDia.getFile();//获取文件路径  
			docPathField.setText(fileNamePath);	
	    }
		//文件上传
		if(e.getSource() == upButton_up){//上传
			
			int choose = JOptionPane.showConfirmDialog(this,
				       "继续上传文件？", "提示！", JOptionPane.YES_NO_OPTION,
				       JOptionPane.WARNING_MESSAGE, null);
		   switch (choose) {
			     case JOptionPane.YES_NO_OPTION: {
			    	 boolean flag = false;
			    	 fileNamePath = docPathField.getText().trim();
			    	 id = docNumField.getText().trim();

			    	 try{
			    		 des = docDesArea.getText().trim();
			    	 }catch (NullPointerException e1) {
			    		 JOptionPane.showMessageDialog(this, "你还没有输入描述", "出错了！", JOptionPane.ERROR_MESSAGE);
			    		 flag = false;
			    	 }
			    	 
			    	 File tempFile = new File(fileNamePath);
			    	 String fileName = tempFile.getName();
			
			    	 try{
			    		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			    		 if( DocClient.file_up(id, des, tempFile))//网络文件上传
			    			 {flag = true;
			    			  
			    			 }
						DataProcessing.insertDoc(id, name, timestamp, des, fileName);
			    	}catch (ClassNotFoundException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
					}catch(SQLException e1){
			    		 e1.printStackTrace();
			    		 JOptionPane.showMessageDialog(this, "数据库异常", "异常处理", JOptionPane.ERROR_MESSAGE); 
			    		 flag = false;
//			    		 this.dispose();
			    	 }catch(IOException e1){
			    		 JOptionPane.showMessageDialog(this, "文件读取异常", "异常处理", JOptionPane.ERROR_MESSAGE); 
			    		 flag = false;
//			    		 this.dispose();
			    	 }
			    	 if(flag == true) {
			    		 JOptionPane.showMessageDialog(this.getContentPane(),
					 "文件上传成功", "提示！", JOptionPane.INFORMATION_MESSAGE);
			    	 this.dispose();
			    	 } else {
			    		 JOptionPane.showMessageDialog(this.getContentPane(),
								 "文件上传失败", "出错了！", JOptionPane.INFORMATION_MESSAGE);
			    	 }
			    	 }
			     case JOptionPane.NO_OPTION:{
//			    	 this.dispose();
			     }
		   }
		}else if(e.getSource() == upButton_cancle) {//取消的  关闭当前窗口
			this.dispose();
		}

}
	}

//*********************  MyTableModel()  **************?//
