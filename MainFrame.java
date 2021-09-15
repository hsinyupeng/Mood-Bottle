import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
	private BufferedImage img_main, img_history, img_bad, img_good, img_fortune, img_friend, img_setting, img_umbrella,
			img_logout, img_stat;
	private JFrame mainframe;
	private JButton good = new JButton();
	private JButton bad = new JButton();
	private JButton fortune = new JButton();
	private JButton history = new JButton();
	private JButton friend = new JButton();
	private JButton setting = new JButton();
	private JButton logout = new JButton();
	private JButton stat = new JButton();
	private JLabel umbrella;
	private JLabel friend_label = new JLabel("Share Diary with Friends");
	private JLabel weather_label = new JLabel("Today's Weather");
	private JLabel good_label = new JLabel("Share your Good Mood");
	private JLabel bad_label = new JLabel("Release your Bad Mood");
	private JLabel fortune_label = new JLabel("Divine Today's Fortune");
	private JLabel history_label = new JLabel("Recall Your Past");
	private JLabel stat_label = new JLabel("See Your Stat");
	private String ID;
	private Crawler c = new Crawler();
	private WeatherBuild w = new WeatherBuild();
	private WeatherPanel weather = new WeatherPanel(c, w);
	private Statistic s = new Statistic(ID);

	MainFrame(String UserID) throws SQLException {
		LoadMainFile();
		SetMainTable();
		this.ID = UserID;
		new First(ID);
	}

	public void SetMainTable() {
		weather.setBounds(80, 70, weather.getWidth(), weather.getHeight());
		logout.setIcon(new ImageIcon(img_logout));
		logout.setBounds(743, 160, img_logout.getWidth(), img_logout.getHeight());
		umbrella = new JLabel(new ImageIcon(img_umbrella));
		umbrella.setBounds(830, 250, img_umbrella.getWidth(), img_umbrella.getHeight());
		umbrella.setVisible(false);

		if (weather.getPath().equals("/rainy.png") || weather.getPath().equals("/rainy_night.png")) {
			umbrella.setVisible(true);
		}
		mainframe = new JFrame("");
		JLabel scrollPane = new JLabel(new ImageIcon(img_main));// 把Image放進label裡

		friend_label.setBounds(541, 168, 140, 20);
		friend_label.setVisible(false);
		friend_label.setOpaque(true);
		friend_label.setBackground(new Color(255, 255, 255));
		mainframe.add(friend_label);
		weather_label.setBounds(270, 280, 100, 20);
		weather_label.setVisible(false);
		weather_label.setOpaque(true);
		weather_label.setBackground(new Color(255, 255, 255));
		mainframe.add(weather_label);
		good_label.setBounds(510, 285, 135, 20);
		good_label.setVisible(false);
		good_label.setOpaque(true);
		good_label.setBackground(new Color(255, 255, 255));
		mainframe.add(good_label);
		bad_label.setBounds(465, 285, 140, 20);
		bad_label.setVisible(false);
		bad_label.setOpaque(true);
		bad_label.setBackground(new Color(255, 255, 255));
		mainframe.add(bad_label);
		fortune_label.setBounds(580, 385, 135, 20);
		fortune_label.setVisible(false);
		fortune_label.setOpaque(true);
		fortune_label.setBackground(new Color(255, 255, 255));
		mainframe.add(fortune_label);
		history_label.setBounds(586, 285, 100, 20);
		history_label.setVisible(false);
		history_label.setOpaque(true);
		history_label.setBackground(new Color(255, 255, 255));
		mainframe.add(history_label);
		stat_label.setBounds(586, 285, 100, 20);
		stat_label.setVisible(false);
		stat_label.setOpaque(true);
		stat_label.setBackground(new Color(255, 255, 255));
		mainframe.add(stat_label);

		friend.setIcon(new ImageIcon(img_friend));
		bad.setIcon(new ImageIcon(img_bad));
		good.setIcon(new ImageIcon(img_good));
		history.setIcon(new ImageIcon(img_history));
		setting.setIcon(new ImageIcon(img_setting));
		fortune.setIcon(new ImageIcon(img_fortune));
		stat.setIcon(new ImageIcon(img_stat));

		friend.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		friend.setVerticalTextPosition(SwingConstants.CENTER);
		friend.setHorizontalTextPosition(SwingConstants.CENTER);

		friend.setBounds(586, 225, img_friend.getWidth(), img_friend.getHeight());
		bad.setBounds(465, 225, img_bad.getWidth(), img_bad.getHeight());
		good.setBounds(518, 225, img_good.getWidth(), img_good.getHeight());
		history.setBounds(460, 324, img_history.getWidth(), img_history.getHeight());
		setting.setBounds(445, 334, img_setting.getWidth(), img_setting.getHeight());
		fortune.setBounds(556, 324, img_fortune.getWidth(), img_fortune.getHeight());
		stat.setBounds(487, 117, img_stat.getWidth(), img_stat.getHeight());
		friend.setActionCommand("Friend");
		bad.setActionCommand("Bad");
		good.setActionCommand("Good");
		history.setActionCommand("History");
		setting.setActionCommand("Setting");
		fortune.setActionCommand("Fortune");
		logout.setActionCommand("Logout");
		stat.setActionCommand("Stat");
		friend.setContentAreaFilled(false);
		bad.setContentAreaFilled(false);
		good.setContentAreaFilled(false);
		history.setContentAreaFilled(false);
		setting.setContentAreaFilled(false);
		fortune.setContentAreaFilled(false);
		logout.setContentAreaFilled(false);
		stat.setContentAreaFilled(false);
		friend.setBorder(null);
		bad.setBorder(null);
		good.setBorder(null);
		history.setBorder(null);
		setting.setBorder(null);
		fortune.setBorder(null);
		logout.setBorder(null);
		stat.setBorder(null);
		mainframe.add(friend);
		mainframe.add(bad);
		mainframe.add(good);
		mainframe.add(history);
		// mainframe.add(setting);
		mainframe.add(fortune);
		mainframe.add(weather);
		mainframe.add(umbrella);
		mainframe.add(logout);
		mainframe.add(stat);

		scrollPane.setSize(img_main.getWidth(), img_main.getHeight());
		mainframe.getContentPane().add(scrollPane);
		mainframe.pack();
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setTitle("Main");
		mainframe.setLocationRelativeTo(null);
		mainframe.setVisible(true);
		mainframe.setResizable(false);
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals("Good")) {
					new GoodMood(ID);

					mainframe.setVisible(false);
				} else if (command.equals("Bad")) {
					new BadMood(ID);

					mainframe.setVisible(false);
				} else if (command.equals("History")) {
					try {
						PastDiary past = new PastDiary(ID);
						past.open();
						mainframe.setVisible(false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (command.equals("Fortune")) {
					try {
						new Fortune(ID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					mainframe.setVisible(false);
				} else if (command.equals("Friend")) {
					try {
						new Friend_List(ID);
						mainframe.setVisible(false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (command.equals("Stat")) {
					s.open();
					mainframe.setVisible(false);
				} else if (command.equals("Setting")) {
				} else if (command.equals("Logout")) {
					int op = JOptionPane.showConfirmDialog(null, "是否要登出", "提示", JOptionPane.YES_NO_OPTION);
					;
					if (op == JOptionPane.YES_OPTION) {
						new LoginFrame();
						mainframe.setVisible(false);
					} else if (op == JOptionPane.NO_OPTION) {

					}
				}
			}
		}
		class mouselistener implements MouseListener {
			@Override
			// 當滑鼠進入某物件範圍時
			public void mouseEntered(MouseEvent e) {
				if (e.getSource() == friend) {
					friend_label.setVisible(true);
				}
				if (e.getSource() == weather) {
					weather_label.setVisible(true);
				}
				if (e.getSource() == good) {
					good_label.setVisible(true);
				}
				if (e.getSource() == bad) {
					bad_label.setVisible(true);
				}
				if (e.getSource() == fortune) {
					fortune_label.setVisible(true);
				}
				if (e.getSource() == history) {
					history_label.setVisible(true);
				}
				if (e.getSource() == stat) {
					stat_label.setVisible(true);
				}
			}

			@Override
			// 當滑鼠退出某物件範圍時
			public void mouseExited(MouseEvent e) {
				if (e.getSource() == friend) {
					friend_label.setVisible(false);
				}
				if (e.getSource() == weather) {
					weather_label.setVisible(false);
				}
				if (e.getSource() == good) {
					good_label.setVisible(false);
				}
				if (e.getSource() == bad) {
					bad_label.setVisible(false);
				}
				if (e.getSource() == fortune) {
					fortune_label.setVisible(false);
				}
				if (e.getSource() == history) {
					history_label.setVisible(false);
				}
				if (e.getSource() == stat) {
					stat_label.setVisible(false);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		}
		MouseListener listener_touch = new mouselistener();
		ActionListener listener = new ButtonListener();
		friend.addActionListener(listener);
		good.addActionListener(listener);
		bad.addActionListener(listener);
		history.addActionListener(listener);
		setting.addActionListener(listener);
		fortune.addActionListener(listener);
		logout.addActionListener(listener);
		stat.addActionListener(listener);
		friend.addMouseListener(listener_touch);
		weather.addMouseListener(listener_touch);
		good.addMouseListener(listener_touch);
		bad.addMouseListener(listener_touch);
		fortune.addMouseListener(listener_touch);
		history.addMouseListener(listener_touch);
		stat.addMouseListener(listener_touch);

		weather.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					String url = "https://www.cwb.gov.tw/V8/C/";
					java.net.URI uri = java.net.URI.create(url);
					java.awt.Desktop dp = java.awt.Desktop.getDesktop();
					if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
						dp.browse(uri);
					}
				} catch (java.lang.NullPointerException e1) {
					e1.printStackTrace();
				} catch (java.io.IOException e2) {
					e2.printStackTrace();
				}
			}

		});
	}

	public void LoadMainFile() {
		String historyPath = "/history.png";
		String mainPath = "/main.png";
		String badPath = "/bad.png";
		String goodPath = "/good.png";
		String fortunePath = "/fortune.png";
		String friendPath = "/friend.png";
		String settingPath = "/setting.png";
		String umbrellaPath = "/umbrella.png";
		String logoutPath = "/logout.png";
		String statPath = "/sb1.png";
		java.net.URL imgURL;
		if (s.getBGPath().equals("/b1.png")) {
			statPath = "/sb1.png";
		} else if (s.getBGPath().equals("/b2.png")) {
			statPath = "/sb2.png";
		} else if (s.getBGPath().equals("/g1.png")) {
			statPath = "/sg1.png";
		} else if (s.getBGPath().equals("/g2.png")) {
			statPath = "/sg2.png";
		}
		try {
			imgURL = MainFrame.class.getResource(mainPath);
			img_main = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(historyPath);
			img_history = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(badPath);
			img_bad = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(goodPath);
			img_good = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(fortunePath);
			img_fortune = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(friendPath);
			img_friend = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(settingPath);
			img_setting = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(umbrellaPath);
			img_umbrella = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(logoutPath);
			img_logout = ImageIO.read(imgURL);

			imgURL = MainFrame.class.getResource(statPath);
			img_stat = ImageIO.read(imgURL);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}