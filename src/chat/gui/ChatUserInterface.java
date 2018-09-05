package chat.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat.controller.FileUtils;

public class ChatUserInterface {
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JMenu menus[];
	private JMenuItem menuItems[];
	private JPanel panel;
	private JLabel label;
	private JTextField textField;
	private JButton buttonSend;
	private JButton buttonReset;
	private JTextArea textArea;
	private JFileChooser fileBrowser;
	
	public void showChatWindow() {
		// Create the Frame
		mainFrame = new JFrame("Chat Window");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(500, 500);
		
		// Create the MenuBar and add components
		menuBar = new JMenuBar();
		menus = new JMenu[2];
		menus[0] = new JMenu("File");
		menus[1] = new JMenu("Help");
		menus[1].addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() != null && e.getButton() == MouseEvent.BUTTON1) {
					showHelper();
				}
			}
		});
				
		menuBar.add(menus[0]); menuBar.add(menus[1]);
		menuItems = new JMenuItem[2];
		menuItems[0] = new JMenuItem("Open");
		menuItems[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openFileTxt();
			}
		});
		menuItems[1] = new JMenuItem("Save as");
		menuItems[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsFileTxt();
			}
		});
		menus[0].add(menuItems[0]);
		menus[0].add(menuItems[1]);
		
		// Create the panel at bottom and add components
		panel = new JPanel();
		label = new JLabel("Enter text");
		textField = new JTextField(20);
		textField.setDocument(new JTextFieldLimit(30)); // accept up to 30 characters
		textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyPress = e.getKeyCode();
				if (keyPress == KeyEvent.VK_ENTER) {
					sendTextMessage();
				}
			}
		});
		
		buttonSend = new JButton("Send");
		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendTextMessage();
			}
		});
		
		buttonReset = new JButton("Reset");
		buttonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
		});
		panel.add(label);
		panel.add(textField);
		panel.add(buttonSend);
		panel.add(buttonReset);
		
		textArea = new JTextArea();
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setEditable(false);
		JScrollPane scrollTextArea = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
															JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		mainFrame.getContentPane().add(BorderLayout.SOUTH, panel);
		mainFrame.getContentPane().add(BorderLayout.NORTH, menuBar);
		mainFrame.getContentPane().add(BorderLayout.CENTER, scrollTextArea);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
	
	private void sendTextMessage() {
		Toolkit.getDefaultToolkit().beep();
		String textMsg = textField.getText();
		textArea.append(textMsg + "\n");
		textField.setText("");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	private void resetAll() {
		textArea.setText("");
		textField.setText("");
	}
	
	private void openFileTxt() {
		if (fileBrowser == null) {
			fileBrowser = new JFileChooser();
			fileBrowser.addChoosableFileFilter(new TextFileFilter());
			fileBrowser.setAcceptAllFileFilterUsed(false);
		}
		int valueSelect = fileBrowser.showOpenDialog(mainFrame);
		if (valueSelect == JFileChooser.APPROVE_OPTION) {
			File file = fileBrowser.getSelectedFile();
			String extensionFileSelect = FileUtils.getExtension(file);
			if (extensionFileSelect != null) {
				if (FileUtils.getExtension(file).equals(FileUtils.TXT)) {
					textArea.append(FileUtils.readFile(file) + "\n");
				} else {
					JOptionPane.showMessageDialog(mainFrame, "Only .txt File", 
													"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(mainFrame, "Error when select file", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		textArea.setCaretPosition(textArea.getDocument().getLength());
		fileBrowser.setSelectedFile(null);
	}
	
	private void saveAsFileTxt() {
		if (fileBrowser == null) {
			fileBrowser = new JFileChooser();
			fileBrowser.setCurrentDirectory(new File("/Users/mixmex/Desktop"));
		}
		int valueSelect = fileBrowser.showSaveDialog(mainFrame);
		if (valueSelect == JFileChooser.APPROVE_OPTION) {
			try {
				File f = new File(fileBrowser.getSelectedFile() + ".txt");
				FileUtils.writeFile(f, textArea.getText());
				textArea.setText("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fileBrowser.setSelectedFile(null);
	}
	
	private void showHelper() {
		ImageIcon icon = new ImageIcon("src/source/info_icon.png");
		JOptionPane.showMessageDialog(mainFrame, "Demo Chat Application\nAuthor: Kein", 
										"About", JOptionPane.INFORMATION_MESSAGE, icon);
	}
}
