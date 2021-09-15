import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Friend_List {
	private JFrame frame;
	private JLabel IDlabel;
	private JLabel namelabel;
	private JTable table;
	private JPanel friendPanel;
	private JTextField IDInput;
	private JTextField nameInput;
	private JTextField blank;
	private JScrollPane scrollPane;
	private JButton friend_add;
	private JButton friend_Diary;
	private JButton back;
	private String ID;
	private String bgName;
	private String backName;
	private String checkName;
	private String addPath;
	private BufferedImage image1, img_add;
	private BufferedImage image2;
	private BufferedImage image3;
	private String server = "jdbc:mysql://140.119.19.73:9306/";
	private String database = "TG06?useUnicode=true&characterEncoding=UTF-8";
	private String url = server + database;
	private String username = "TG06";
	private String sqlpassword = "bMIEqf";
	private Connection conn = null;

	private DefaultTableModel tableModel;

	public Friend_List(String ID) throws SQLException {
		this.ID = ID;
		LoadMainFile();
		createFrame();
	}

	public void createFrame() throws SQLException {
		JLabel background = new JLabel(new ImageIcon(image1));
		frame = new JFrame();

		createLabel();
		frame.getContentPane().add(background);
		frame.pack();
		frame.setTitle("FriendList");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void createLabel() throws SQLException {
		IDlabel = new JLabel("Enter Friend's ID:");
		namelabel = new JLabel("Set Name:");
		blank = new JTextField("");
		IDInput = new JTextField();
		nameInput = new JTextField();
		friend_add = new JButton(new ImageIcon(img_add));
		back = new JButton(new ImageIcon(image2));
		friend_Diary = new JButton(new ImageIcon(image3));

		friend_add.setActionCommand("ADD");
		back.setActionCommand("Back");
		friend_Diary.setActionCommand("Diary");

		blank.setBounds(0, 0, 1, 1);
		IDlabel.setBounds(50, 150, 100, 20);
		namelabel.setBounds(80, 180, 100, 20);
		IDInput.setBounds(150, 150, 150, 20);
		nameInput.setBounds(150, 180, 150, 20);
		friend_add.setBounds(325, 150, img_add.getWidth(), img_add.getHeight());
		back.setBounds(248, 530, image2.getWidth(), image2.getHeight());
		friend_Diary.setBounds(40, 530, image3.getWidth(), image3.getHeight());
		friend_add.setContentAreaFilled(false);
		friend_add.setBorder(null);
		back.setContentAreaFilled(false);
		back.setBorder(null);
		friend_Diary.setContentAreaFilled(false);
		friend_Diary.setBorder(null);

		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals("ADD")) {
					try {
						findFriend();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (command.equals("Back")) {
					frame.setVisible(false);

					try {
						new MainFrame(ID);

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (command.equals("Diary")) {
					frame.setVisible(false);
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					new Friend(ID, model.getValueAt(table.getSelectedRow(), 0).toString(),
							model.getValueAt(table.getSelectedRow(), 1).toString(), 0);
				}
			}
		}
		friendList();
		ActionListener listener = new ButtonListener();
		friend_add.addActionListener(listener);
		back.addActionListener(listener);
		friend_Diary.addActionListener(listener);

		scrollPane.setBounds(70, 210, 350, 260);

		frame.add(blank);
		frame.add(IDlabel);
		frame.add(namelabel);
		frame.add(IDInput);
		frame.add(nameInput);
		frame.add(friend_add);
		frame.add(friend_Diary);
		frame.add(back);

	}

	public void LoadMainFile() {
		bgName = "/paper.png";
		backName = "/back2.png";
		checkName = "/share.png";
		addPath = "/add.png";
		java.net.URL imgURL;
		try {
			imgURL = Friend_List.class.getResource(bgName);
			image1 = ImageIO.read(imgURL);
			imgURL = Friend_List.class.getResource(backName);
			image2 = ImageIO.read(imgURL);
			imgURL = Friend_List.class.getResource(checkName);
			image3 = ImageIO.read(imgURL);
			imgURL = Friend_List.class.getResource(addPath);
			img_add = ImageIO.read(imgURL);
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: " + bgName);
			image1 = null;
		}
	}

	public void addFriend() throws SQLException {
		try {
			conn = DriverManager.getConnection(url, username, sqlpassword);
			PreparedStatement stat;
			PreparedStatement stat2;
			stat = conn.prepareStatement("INSERT INTO `FriendList`(`UserID`, `FriendID`,`Name`) VALUES(?,?,?)");
			stat2 = conn.prepareStatement(
					"SELECT `FriendID`,`Name` FROM `FriendList` WHERE `UserID`= ? and`ID`=(SELECT MAX(ID) FROM `FriendList`)");
			stat.setString(1, ID);
			stat.setString(2, IDInput.getText());
			stat.setString(3, nameInput.getText());
			stat2.setString(1, ID);

			int i = stat.executeUpdate();

			if (i == 1) {
				JOptionPane.showMessageDialog(null, "加入成功", "提示訊息", JOptionPane.WARNING_MESSAGE);
				IDInput.setText("");
				nameInput.setText("");
				ResultSet rs = stat2.executeQuery();
				if (rs.next()) {
					((DefaultTableModel) table.getModel())
							.addRow(new Object[] { (String) rs.getString(1), rs.getString(2) });
				}

			} else {
				JOptionPane.showMessageDialog(null, "輸入錯誤", "提示訊息", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
			System.out.println("資料庫處理錯誤!!!");
		} finally {
			conn.close();
		}
	}

	public void findFriend() throws SQLException {
		try {
			conn = DriverManager.getConnection(url, username, sqlpassword);
			PreparedStatement stat;
			stat = conn.prepareStatement("SELECT UserID FROM UserInformation WHERE `UserID`=?");
			stat.setString(1, IDInput.getText());
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				addFriend();
			} else {
				JOptionPane.showMessageDialog(null, "無此ID", "提示訊息", JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
			System.out.println("資料庫處理錯誤!!!");
		} finally {
			conn.close();
		}
	}

	public void friendList() throws SQLException {
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new Object[] { "ID", "Name" });

		try {
			conn = DriverManager.getConnection(url, username, sqlpassword);
			PreparedStatement stat;
			stat = conn.prepareStatement("SELECT `FriendID`, `Name`FROM FriendList WHERE `UserID`=?");
			stat.setString(1, ID);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				tableModel.addRow(new Object[] { (String) rs.getString(1), rs.getString(2) });
			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
			System.out.println("資料庫處理錯誤!!!");
		} finally {
			conn.close();
		}

		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		JTableHeader head = table.getTableHeader(); // 创建表格标题对象
		head.setPreferredSize(new Dimension(head.getWidth(), 25));// 设置表头大小

		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

		table.setRowHeight(30);
		setColumnWidths(table, 100, 100);
		table.setOpaque(false);
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		scrollPane = new JScrollPane(table);

		frame.add(scrollPane);
	}

	public static void setColumnWidths(JTable table, int... widths) {
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < widths.length; i++) {
			if (i < columnModel.getColumnCount()) {
				columnModel.getColumn(i).setPreferredWidth(widths[i]);
			} else
				break;
		}
	}
}