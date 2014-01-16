package org.mysql.document.tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JWindow;

import org.mysql.document.tool.history.History;
import org.mysql.document.tool.util.ObjectUtils;
import org.mysql.document.tool.util.StringUtils;

/**
 * 图形界面
 * 
 * @author hxl 
 * @date 2012-7-8下午06:31:41
 */
public class MysqlDGToolGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int w = 450;
	private static final int h = 260;

	private static final Dimension dimension = Toolkit.getDefaultToolkit()
			.getScreenSize();

	private JMenuBar menuBar;

	private JPanel contentPane;
	private JTextField hostTextField;
	private JTextField portTextField;
	private JTextField databaseNameTextField;
	private JTextField tableNameTextField;
	private JTextField userNameTextField;
	private JPasswordField passWordTextField;
	private JTextField pathTextField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MysqlDGToolGUI frame = new MysqlDGToolGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MysqlDGToolGUI() {
		setTitle("MySql文档工具GUI_V1.0");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int) (dimension.getWidth() - w) / 2,
				(int) (dimension.getHeight() - h) / 2, w, h);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		menuBar = new JMenuBar();
		/**
		 * History Menu Item Event Handler
		 */
		final EventHandler evenHandler = new AbstractEventHanler() {
			@Override
			public Object handler(Object... args) {
				Parameters parameters = History.retrieveHistory(String.valueOf(args[0]));
				hostTextField.setText(parameters.getHost());
				portTextField.setText(parameters.getPort());
				databaseNameTextField.setText(parameters.getDatabase());
				tableNameTextField.setText(parameters.getTable());
				userNameTextField.setText(parameters.getUser());
				passWordTextField.setText(parameters.getPassword());
				pathTextField.setText(parameters.getPath());
				return null;
			}
		};
		final JMenu historyMenu = createJMenu("history" , evenHandler);
		menuBar.add(historyMenu);
		this.setJMenuBar(menuBar);

		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(10, 10, 47, 15);
		contentPane.add(lblHost);

		hostTextField = new JTextField();
		hostTextField.setBounds(67, 7, 219, 21);
		contentPane.add(hostTextField);
		hostTextField.setColumns(10);
		hostTextField.setText("localhost");

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(302, 10, 46, 15);
		contentPane.add(lblPort);

		portTextField = new JTextField();
		portTextField.setBounds(358, 7, 54, 21);
		contentPane.add(portTextField);
		portTextField.setColumns(10);
		portTextField.setText("3306");

		JLabel lblDatabasename = new JLabel("DataBaseName:");
		lblDatabasename.setBounds(10, 50, 101, 15);
		contentPane.add(lblDatabasename);

		databaseNameTextField = new JTextField();
		databaseNameTextField.setBounds(121, 47, 89, 21);
		contentPane.add(databaseNameTextField);
		databaseNameTextField.setColumns(10);

		JLabel lblTablename = new JLabel("TableName:");
		lblTablename.setBounds(226, 50, 89, 15);
		contentPane.add(lblTablename);

		tableNameTextField = new JTextField();
		tableNameTextField.setBounds(332, 47, 80, 21);
		contentPane.add(tableNameTextField);
		tableNameTextField.setColumns(10);
		tableNameTextField.setText("_NULL_");

		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setBounds(10, 95, 80, 15);
		contentPane.add(lblUsername);

		userNameTextField = new JTextField();
		userNameTextField.setBounds(109, 92, 101, 21);
		contentPane.add(userNameTextField);
		userNameTextField.setColumns(10);

		JLabel lblPassword = new JLabel("PassWord:");
		lblPassword.setBounds(226, 95, 74, 15);
		contentPane.add(lblPassword);

		passWordTextField = new JPasswordField();
		passWordTextField.setBounds(311, 92, 80, 21);
		contentPane.add(passWordTextField);
		passWordTextField.setColumns(10);

		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(10, 140, 54, 15);
		contentPane.add(lblPath);

		pathTextField = new JTextField();
		pathTextField.setBounds(67, 137, 324, 21);
		pathTextField.setEditable(false);
		contentPane.add(pathTextField);
		pathTextField.setColumns(10);

		JButton btnProduct = new JButton("Generate");
		btnProduct.setBounds(298, 179, 93, 23);
		contentPane.add(btnProduct);
		final ProgressBar progressBar = new ProgressBar(this);
		btnProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (StringUtils.hasLength(databaseNameTextField.getText())
						&& StringUtils.hasLength(userNameTextField.getText())
						&& StringUtils.hasLength(new String(passWordTextField.getPassword()))) {
					progressBar.showBar();
					new ProductActionEventHandler(progressBar).start();
				} else {
					JOptionPane.showMessageDialog(null,
							"DataBaseName,UserName,PassWord 不能为空.", "警告",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		JButton btnFileChoose = new JButton("...");
		btnFileChoose.setBounds(401, 136, 23, 23);
		contentPane.add(btnFileChoose);
		btnFileChoose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser
						.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jFileChooser.showSaveDialog(null);
				File dirFile = jFileChooser.getSelectedFile();
				String dirPath = dirFile == null ? "" : dirFile.getAbsolutePath();
				pathTextField.setText(dirPath);
			}
		});
		
		JButton saveBnt = new JButton("save");
		saveBnt.setBounds(200, 179, 93, 23);
		contentPane.add(saveBnt);
		saveBnt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//String host, String user, String port, String password,
				//String database, String table, String path
				String markName = JOptionPane.showInputDialog("Please Input Mark Name:");
				if(!StringUtils.hasLength(markName)){
					JOptionPane.showMessageDialog(null, "Please Input Mark Name.", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				History.saveHistory(markName, new Parameters(hostTextField.getText() , 
						userNameTextField.getText(),portTextField.getText(),new String(passWordTextField.getPassword())
						,databaseNameTextField.getText(),tableNameTextField.getText(),pathTextField.getText()));
				createMenuItem(historyMenu, evenHandler, markName);
				
			}
		});
	}

	private JMenu createJMenu(String menuName, final EventHandler eventHandler) {
		JMenu menu = new JMenu(menuName);
		Map<String, Parameters> historyMap = History.getHistoryMap();
		for (Iterator<String> iter = historyMap.keySet().iterator(); iter
				.hasNext();) {
			String markName = iter.next();
			createMenuItem(menu, eventHandler, markName);
		}

		return menu;
	}
	
	private void createMenuItem(JMenu jmenu , final EventHandler eventHandler , String menuItemName){
		JMenuItem menuItem = new JMenuItem(menuItemName);
		jmenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source instanceof JMenuItem) {
					eventHandler.handler(((JMenuItem) source).getText());
				}

			}
		});
	}

	private class ProductActionEventHandler extends Thread {
		private ProgressBar progressBar = null;

		protected ProductActionEventHandler(ProgressBar progressBar) {
			this.progressBar = progressBar;
		}

		@Override
		public void run() {

			Parameters parameters = new Parameters();
			parameters.setHost(hostTextField.getText());
			parameters.setPort(portTextField.getText());
			parameters.setDatabase(databaseNameTextField.getText());
			parameters.setTable(tableNameTextField.getText());
			parameters.setUser(userNameTextField.getText());
			parameters.setPassword(new String(passWordTextField.getPassword()));
			parameters.setPath(pathTextField.getText());

			DBUtils dbUtils = new DBUtils(parameters);
			try {
				// Thread.sleep(2000);
				Word2007.productWordForm(dbUtils.getDatabaseInfo(), parameters);
			} catch (Exception e1) {
				e1.printStackTrace();
				return;
			}
			if (!ObjectUtils.isNull(progressBar)) {
				progressBar.hideBar();
			}
			JOptionPane.showMessageDialog(null, "success.");
		}

	}

	private static class ProgressBar extends JWindow {

		private static final long serialVersionUID = 1L;
		private JProgressBar jProgressBar = null;

		ProgressBar(JFrame owner) {
			super(owner);
			Point ownerP = owner.getLocation();
			jProgressBar = new JProgressBar();
			jProgressBar.setStringPainted(true);
			jProgressBar.setString("Please Waiting...");
			jProgressBar.setIndeterminate(true);
			this.setLayout(new BorderLayout());
			this.getContentPane().add(jProgressBar, BorderLayout.CENTER);
			this.setLocation((int) ownerP.getX() + 25, (int) ownerP.getY()
					+ (h - 20) / 2);
			this.setSize(w - 50, 20);
			// showBar();
		}

		public void showBar() {
			this.setVisible(true);
		}

		public void hideBar() {
			this.setVisible(false);
		}
	}
}