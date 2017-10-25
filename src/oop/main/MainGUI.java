package oop.main;

import oop.frame.*;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainGUI{

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		DataProcessing.connectDataBase();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame loginFrame = new LoginFrame();
					loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		DocClient docClient = new DocClient("127.0.0.1");
	}
	
}
