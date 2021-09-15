import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Fortune {
	private JFrame fortune;
	private JButton check = new JButton("Click here to divine");
	private JButton back = new JButton("Back");
	private String star;
	private String word;
	private String backname;
	private Icon lucky_loading;
	private BufferedImage image2, lucky_first, lucky_result;
	private JLabel scrollPane;
	private JLabel stars;
	private JTextArea words;
	private JTextArea moodtext = new JTextArea();
	private JLabel label1;
	private JLabel label2;
	private String UserID;
	private Font mf = new Font("Helvetica", Font.BOLD, 16);
	private Font mf1 = new Font("Helvetica", Font.BOLD, 22);
	private String ID;
	private String server = "jdbc:mysql://140.119.19.73:9306/";
	private String database = "TG06";
	private String url = server + database + "?useUnicode=true&characterEncoding=utf8";
	private String username = "TG06";
	private String sqlpassword = "bMIEqf";
	private Connection conn = null;
	private ResultSet rs;
	private int rs2;
	private ResultSet rs3;
	private Color color = new Color(1, 49, 157);

	Fortune(String ID) throws SQLException {
		this.UserID = ID;
		LoadMainFile();
		createFrame();
		dailyLogined(UserID);// 登入可以直接顯示
	}

	public void createFrame() {
		label1 = new JLabel("本日運勢");
		label1.setForeground(color);

		label2 = new JLabel("幸運指數");
		label2.setForeground(color);

		stars = new JLabel();
		stars.setForeground(color);
		words = new JTextArea();
		words.setForeground(color);
		label1.setFont(mf1);
		label1.setBounds(392, 120, 200, 100);
		label1.setVisible(false);
		label2.setFont(mf);
		label2.setBounds(405, 150, 200, 100);
		label2.setVisible(false);
		stars.setBounds(400, 180, 200, 100);
		stars.setFont(mf);
		stars.setVisible(false);
		words.setBounds(350, 250, 190, 120);
		words.setFont(mf);
		words.setLineWrap(true);
		words.setVisible(false);
		words.setWrapStyleWord(true);
		words.setBorder(null);
		words.setOpaque(false);
		words.setEditable(false);
		scrollPane = new JLabel(new ImageIcon(lucky_first));
		fortune = new JFrame();
		back.setIcon(new ImageIcon(image2));
		back.setBounds(340, 520, image2.getWidth(), image2.getHeight());
		back.setOpaque(false);
		back.setBorder(null);
		back.setContentAreaFilled(false);

		check.setBounds(340, 450, image2.getWidth(), image2.getHeight() - 100);
		check.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		check.setForeground(color);
		check.setFocusPainted(false);
		check.setOpaque(false);
		check.setBorder(null);
		check.setContentAreaFilled(false);
		fortune.add(label1);
		fortune.add(label2);
		fortune.add(stars);
		fortune.add(words);
		fortune.add(back);
		fortune.add(check);
		fortune.getContentPane().add(scrollPane);
		fortune.pack();
		fortune.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fortune.setTitle("Fortune");
		fortune.setLocationRelativeTo(null);
		fortune.setVisible(true);

		class Sleep extends Thread {
			public void run() {
				try {
					scrollPane.setIcon(lucky_loading);
					check.setVisible(false);
					sleep(2390);
					scrollPane.setIcon(new ImageIcon(lucky_result));
					dailyLogin(UserID);
					stars.setVisible(true);
					words.setVisible(true);
					label1.setVisible(true);
					label2.setVisible(true);

				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals("Click here to divine")) {
					Thread t = new Sleep();
					t.start();
				}

				else if (command.equals("Back")) {
					fortune.setVisible(false);
					try {
						new MainFrame(UserID);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		ActionListener listener = new ButtonListener();
		back.addActionListener(listener);
		check.addActionListener(listener);
	}

	public void LoadMainFile() {
		backname = "/back.png";
		String luckyFirstPath = "/Lucky.jpg";// 設定檔名
		String luckyLoadingPath = "/lucky_loading.gif";
		String luckyResultPath = "/Lucky6.jpg";
		java.net.URL imgURL;
		try {
			imgURL = Fortune.class.getResource(backname);
			image2 = ImageIO.read(imgURL);
			imgURL = Fortune.class.getResource(luckyFirstPath);
			lucky_first = ImageIO.read(imgURL);// 讀取檔案
			imgURL = Fortune.class.getResource(luckyLoadingPath);
			lucky_loading = new ImageIcon(imgURL);
			imgURL = Fortune.class.getResource(luckyResultPath);
			lucky_result = ImageIO.read(imgURL);;

		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: ");
			lucky_first = null;// 如果錯誤的話顯示錯誤訊息
		}
	}

	public void findDefine() {

		try {
			conn = DriverManager.getConnection(url, username, sqlpassword);
			Statement stat = conn.createStatement();
			ThreadLocalRandom tlr = ThreadLocalRandom.current();
			int i = tlr.nextInt(1, 60 + 1);
			String query = String.format("SELECT `LEVEL`,`TEXT`FROM Fortune WHERE ID='%d'", i);

			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {

				ResultSet result = stat.getResultSet();
				showResultSet(result);
				result.close();
			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
			System.out.println("資料庫處理錯誤!!!");
		}
	}

	public void showResultSet(ResultSet result) throws SQLException {
		while (result.next()) {
			stars.setText(result.getString(1));
			words.setText(result.getString(2));

		}
	}

	public void dailyLogin(String ID) throws SQLException {

		try {
			conn = DriverManager.getConnection(url, username, sqlpassword);
			PreparedStatement stat;
			PreparedStatement stat2;
			PreparedStatement stat3;
			stat = conn.prepareStatement(
					"SELECT COUNT(`UserID`)FROM LoginTime where `UserID`=? and to_days(`Time`) = to_days(CURDATE())");
			stat2 = conn.prepareStatement("INSERT INTO `LoginTime`(`UserID`,`Stars`,`Text`) VALUES (?,?,?)");
			stat3 = conn.prepareStatement(
					"SELECT `Stars`,`Text` FROM LoginTime where `UserID`=? and to_days(`Time`) = to_days(CURDATE())");
			stat.setString(1, ID);
			stat3.setString(1, ID);
			rs = stat.executeQuery();

			rs3 = stat3.executeQuery();
			if (rs.next()) {
				ResultSet result = stat.getResultSet();
				ResultSet result3 = stat3.getResultSet();
				if (result.getInt(1) == 0) {
					findDefine();
					stat2.setString(1, ID);
					stat2.setString(2, stars.getText());
					stat2.setString(3, words.getText());
					rs2 = stat2.executeUpdate();

				} else {
					if (rs3.next()) {
						scrollPane.setIcon(new ImageIcon(lucky_result));
						stars.setText(result3.getString(1));
						words.setText(result3.getString(2));
						words.setVisible(true);
						label1.setVisible(true);
						label2.setVisible(true);
						check.setVisible(false);
						JOptionPane.showMessageDialog(null, "今日已占卜", "提示訊息", JOptionPane.WARNING_MESSAGE);
					}

				}
			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
		} finally {
			conn.close();
		}
	}

	public void dailyLogined(String ID) throws SQLException {

		try {
			conn = DriverManager.getConnection(url, username, sqlpassword);
			PreparedStatement stat;

			PreparedStatement stat3;
			stat = conn.prepareStatement(
					"SELECT COUNT(`UserID`)FROM LoginTime where `UserID`=? and to_days(`Time`) = to_days(CURDATE())");
			stat3 = conn.prepareStatement(
					"SELECT `Stars`,`Text` FROM LoginTime where `UserID`=? and to_days(`Time`) = to_days(CURDATE())");
			stat.setString(1, ID);
			stat3.setString(1, ID);
			rs = stat.executeQuery();

			rs3 = stat3.executeQuery();
			if (rs3.next()) {
				ResultSet result3 = stat3.getResultSet();
				scrollPane.setIcon(new ImageIcon(lucky_result));
				stars.setText(result3.getString(1));
				words.setText(result3.getString(2));
				stars.setVisible(true);
				words.setVisible(true);
				label1.setVisible(true);
				label2.setVisible(true);
				check.setVisible(false);
				JOptionPane.showMessageDialog(null, "今日已占卜", "提示訊息", JOptionPane.WARNING_MESSAGE);

			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
		} finally {
			conn.close();
		}
	}
}