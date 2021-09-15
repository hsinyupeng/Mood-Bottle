import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Friend implements Bottle {
	private JFrame moodFrame;
	private JButton check = new JButton("Check");
	private JButton reset = new JButton("Reset");
	private JButton back = new JButton("Back");
	private String FileMainname;
	private String savename;
	private String deletename;
	private String backname;
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image3;
	private BufferedImage image4;
	private JTextArea moodtext = new JTextArea();
	private JTextArea name;
	private JLabel label = new JLabel("");
	private String UserID;
	private String FriendID;
	private Font mf = new Font("黑體", Font.PLAIN, 16);
	private String Friend_Name;

	Friend(String ID, String Friend_ID, String Friend_Name, int i) {
		this.Friend_Name = Friend_Name;
		this.FriendID = Friend_ID;
		this.UserID = ID;
		i = 0;
		LoadMainFile();
		SetMoodTable();
	}

	Friend(String ID, String Text, String Friend_Name) {
		this.Friend_Name = Friend_Name;
		this.UserID = ID;
		LoadMainFile();
		SetMoodTable();
		moodtext.setText(Text);
		moodtext.setEditable(false);
		back.setActionCommand("BackPast");
		reset.setVisible(false);
		check.setVisible(false);
	}

	public void SetMoodTable() {
		name = new JTextArea(Friend_Name);

		name.setBounds(730, 140, 130, 50);
		name.setOpaque(false);
		name.setBorder(null);
		name.setFont(new Font("黑體", Font.BOLD, 24));
		name.setForeground(new Color(255, 250, 250));

		moodFrame = new JFrame("");
		JLabel scrollPane = new JLabel(new ImageIcon(image1));
		label.setBounds(370, 20, 100, 50);
		check.setIcon(new ImageIcon(image2));
		check.setBounds(680, 260, image2.getWidth(), image2.getHeight());
		check.setOpaque(false);
		check.setBorder(null);
		check.setContentAreaFilled(false);

		reset.setIcon(new ImageIcon(image3));
		reset.setBounds(680, 360, image3.getWidth(), image3.getHeight());
		reset.setOpaque(false);
		reset.setBorder(null);
		reset.setContentAreaFilled(false);

		back.setIcon(new ImageIcon(image4));
		back.setBounds(680, 460, image4.getWidth(), image4.getHeight());
		back.setOpaque(false);
		back.setBorder(null);
		back.setContentAreaFilled(false);
		moodtext.setBounds(70, 127, 540, 460);
		moodtext.setLineWrap(true);
		moodtext.setWrapStyleWord(true);
		moodtext.setBorder(null);
		moodtext.setOpaque(false);
		moodtext.setFont(mf);
		moodFrame.setResizable(false);
		moodFrame.add(name);
		moodFrame.add(moodtext);
		moodFrame.add(check);
		moodFrame.add(reset);
		moodFrame.add(label);
		moodFrame.add(back);
		moodFrame.getContentPane().add(scrollPane);
		moodFrame.pack();
		moodFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		moodFrame.setTitle("FriendMood");
		moodFrame.setLocationRelativeTo(null);
		moodFrame.setVisible(true);

		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals("Reset")) {
					moodtext.setText("");
				} else if (command.equals("Check")) {
					try {
						inputdata();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (command.equals("Back")) {
					moodFrame.setVisible(false);

					try {
						new MainFrame(UserID);

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (command.equals("BackPast")) {
					moodFrame.setVisible(false);
				}
			}
		}
		ActionListener listener = new ButtonListener();
		reset.addActionListener(listener);
		check.addActionListener(listener);
		back.addActionListener(listener);
	}

	public void LoadMainFile() {	
		FileMainname = "/friend.jpg";// 設定檔名
		savename = "/save.png";
		deletename = "/delete.png";
		backname = "/back.png";
		java.net.URL imgURL;
		try {
			imgURL = Friend.class.getResource(FileMainname);
			image1 = ImageIO.read(imgURL);// 讀取檔案
			imgURL = Friend.class.getResource(savename);
			image2 = ImageIO.read(imgURL);// 讀取檔案
			imgURL = Friend.class.getResource(deletename);
			image3 = ImageIO.read(imgURL);
			imgURL = Friend.class.getResource(backname);
			image4 = ImageIO.read(imgURL);

		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: " + FileMainname);
			image1 = null;// 如果錯誤的話顯示錯誤訊息
		}
	}

	public void open() {
		moodFrame.setVisible(true);
	}

	public void inputdata() throws SQLException {
		String server = "jdbc:mysql://140.119.19.73:9306/";
		String database = "TG06?useUnicode=true&characterEncoding=UTF-8";
		String url = server + database;
		String username = "TG06";
		String sqlpassword = "bMIEqf";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, sqlpassword);
			PreparedStatement stat;
			PreparedStatement stat2;
			stat = conn.prepareStatement("INSERT INTO `Diary`(`UserID`, `Friend_Diary`,`Friend_Name`) VALUES(?,?,?)");
			stat2 = conn.prepareStatement("INSERT INTO `Diary`(`UserID`, `Friend_Diary`,`Friend_Name`) VALUES(?,?,?)");
			stat.setString(1, UserID);
			stat.setString(2, moodtext.getText());
			stat.setString(3, Friend_Name);
			stat2.setString(1, FriendID);
			stat2.setString(2, moodtext.getText());
			stat2.setString(3, UserID);
			int i = stat.executeUpdate();
			if (i == 1) {
				stat2.execute();
				JOptionPane.showMessageDialog(null, "成功", "提示訊息", JOptionPane.WARNING_MESSAGE);
				moodFrame.setVisible(false);
				new MainFrame(UserID);
			} else {
				JOptionPane.showMessageDialog(null, "失敗", "提示訊息", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
			System.out.println("資料庫處理錯誤!!!");
		} finally {
			conn.close();
		}
	}
}