import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class PastDiary {
	private static JFrame frame;
	private JLabel diarylabel;
	private JLabel diary1;
	private JLabel diary2;
	private JLabel diary3;
	private JLabel diary4;
	private static JLabel bottle1;
	private static JLabel bottle2;
	private static JLabel bottle3;
	private static JLabel bottle4;
	private static JLabel fName1;
	private static JLabel fName2;
	private static JLabel fName3;
	private static JLabel fName4;
	private static BufferedImage image1;
	private static BufferedImage image2;
	private static BufferedImage image3;
	private static BufferedImage image4;
	private static BufferedImage image5;
	private static BufferedImage image6;
	private static BufferedImage image7;
	private static BufferedImage image8;
	private static BufferedImage image_Test1;
	private static BufferedImage image_Test2;
	private static BufferedImage image_Test3;
	private static BufferedImage image_Test4;
	private static JTextArea print1;
	private static JTextArea print2;
	private static JTextArea print3;
	private static JTextArea print4;
	private JComboBox<String> box1;
	private JComboBox<String> box2;
	private JComboBox<String> box3;
	private String ID;
	private static String Text1;
	private static String Text2;
	private static String Text3;
	private static String Text4;
	private static String name1;
	private static String name2;
	private static String name3;
	private static String name4;
	private String server = "jdbc:mysql://140.119.19.73:9306/";
	private String database = "TG06";
	private String url = server + database;
	private String username = "TG06";
	private String sqlpassword = "bMIEqf";
	private Connection conn = null;
	private Font mf = new Font("TimesRoman", Font.PLAIN, 24);
	private JButton back;
	private JButton search = new JButton("Search");
	private static JButton past1;
	private static JButton past2;
	private static JButton past3;
	private static JButton past4;
	private static ArrayList<String> info;
	private static ArrayList<String> date;
	private static ArrayList<BufferedImage> num;
	private static ArrayList<String> nameList;

	PastDiary(String UserID) throws SQLException {
		this.ID = UserID;
		LoadMainFile();
		connection();
		print1 = new JTextArea();
		print2 = new JTextArea();
		print3 = new JTextArea();
		print4 = new JTextArea();

		SetSearchTable();
	}

	public void open() {
		frame.setVisible(true);
	}

	public void SetSearchTable() throws SQLException {
		frame = new JFrame("History");
		JLabel ScrollPane = new JLabel(new ImageIcon(image1));
		ScrollPane.setBounds(100, 100, 400, 400);
		box1 = new JComboBox<String>();
		box2 = new JComboBox<String>();
		box3 = new JComboBox<String>();
		setsearchbox();
		box1.setBounds(100, 550, 70, 50);
		box2.setBounds(170, 550, 70, 50);
		box3.setBounds(240, 550, 70, 50);
		search.setBounds(140, 600, 100, 50);
		box1.setOpaque(false);
		box2.setOpaque(false);
		box3.setOpaque(false);
		frame.add(box1);
		frame.add(box2);
		frame.add(box3);
		frame.add(search);
		past1 = new JButton();
		past2 = new JButton();
		past3 = new JButton();
		past4 = new JButton();
		past1.setIcon(new ImageIcon(image8));
		past2.setIcon(new ImageIcon(image8));
		past3.setIcon(new ImageIcon(image8));
		past4.setIcon(new ImageIcon(image8));
		past1.setOpaque(false);
		past2.setOpaque(false);
		past3.setOpaque(false);
		past4.setOpaque(false);
		past1.setBorder(null);
		past2.setBorder(null);
		past3.setBorder(null);
		past4.setBorder(null);
		past1.setContentAreaFilled(false);
		past2.setContentAreaFilled(false);
		past3.setContentAreaFilled(false);
		past4.setContentAreaFilled(false);
		past1.setBounds(820, 30, image8.getWidth(), image8.getHeight());
		past2.setBounds(820, 170, image8.getWidth(), image8.getHeight());
		past3.setBounds(820, 310, image8.getWidth(), image8.getHeight());
		past4.setBounds(820, 450, image8.getWidth(), image8.getHeight());
		past1.setVisible(false);
		past2.setVisible(false);
		past3.setVisible(false);
		past4.setVisible(false);
		past4.setActionCommand("past1");
		past3.setActionCommand("past2");
		past2.setActionCommand("past3");
		past1.setActionCommand("past4");
		search.setActionCommand("Search");
		back = new JButton();
		back.setActionCommand("Back");
		back.setIcon(new ImageIcon(image2));
		back.setBounds(200, 350, image2.getWidth(), image2.getHeight());
		back.setOpaque(false);
		back.setBorder(null);
		back.setContentAreaFilled(false);
		creatediary();
		diarylabel.add(back);

		frame.add(past1);
		frame.add(past2);
		frame.add(past3);
		frame.add(past4);
		frame.add(bottle1);
		frame.add(bottle2);
		frame.add(bottle3);
		frame.add(bottle4);
		frame.add(fName1);
		frame.add(fName2);
		frame.add(fName3);
		frame.add(fName4);
		frame.add(diarylabel);

		frame.setSize(image1.getWidth(), image1.getHeight());
		frame.add(ScrollPane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Search");
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals("Search")) {
					findsearch();
				} else if (command.equals("Back")) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					frame.setVisible(false);
					try {
						new MainFrame(ID);

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (command.equals("past1")) {
					if (image_Test1 == image5) {
						new GoodMood(ID, Text1);
					} else if (image_Test1 == image6) {
						new BadMood(ID, Text1);

					} else if (image_Test1 == image7) {
						new Friend(ID, Text1, name1);
					}
				} else if (command.equals("past2")) {
					if (image_Test2 == image5) {
						new GoodMood(ID, Text2);

					} else if (image_Test2 == image6) {
						new BadMood(ID, Text2);

					} else if (image_Test2 == image7) {
						new Friend(ID, Text2, name2);
					}

				} else if (command.equals("past3")) {
					if (image_Test3 == image5) {
						new GoodMood(ID, Text3);

					} else if (image_Test3 == image6) {
						new BadMood(ID, Text3);

					} else if (image_Test3 == image7) {
						new Friend(ID, Text3, name3);
					}

				} else if (command.equals("past4")) {
					if (image_Test4 == image5) {
						new GoodMood(ID, Text4);
					} else if (image_Test4 == image6) {
						new BadMood(ID, Text4);

					} else if (image_Test4 == image7) {
						new Friend(ID, Text4, name4);
					}
				}
			}
		}
		ActionListener listener = new ButtonListener();
		back.addActionListener(listener);
		search.addActionListener(listener);
		past1.addActionListener(listener);
		past2.addActionListener(listener);
		past3.addActionListener(listener);
		past4.addActionListener(listener);
	}

	public void LoadMainFile() {
		String FileMainname = "/history.jpg";// 設定檔名
		String backname = "/back.png";
		String labelname = "/happy.jpg";
		String label = "/history 1.jpg";
		String gbottle = "/good.png";
		String bbottle = "/bad.png";
		String fbottle = "/friend.png";
		String arrow = "/arrow.png";
		java.net.URL imgURL;
		try {
			imgURL = PastDiary.class.getResource(FileMainname);
			image1 = ImageIO.read(imgURL);// 讀取檔案
			imgURL = PastDiary.class.getResource(backname);
			image2 = ImageIO.read(imgURL);
			imgURL = PastDiary.class.getResource(labelname);
			image3 = ImageIO.read(imgURL);
			imgURL = PastDiary.class.getResource(label);
			image4 = ImageIO.read(imgURL);
			imgURL = PastDiary.class.getResource(gbottle);
			image5 = ImageIO.read(imgURL);
			imgURL = PastDiary.class.getResource(bbottle);
			image6 = ImageIO.read(imgURL);
			imgURL = PastDiary.class.getResource(fbottle);
			image7 = ImageIO.read(imgURL);
			imgURL = PastDiary.class.getResource(arrow);
			image8 = ImageIO.read(imgURL);
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: " + FileMainname);
			image1 = null;// 如果錯誤的話顯示錯誤訊息
		}
	}

	public void creatediary() throws SQLException {
		diary1 = new JLabel(new ImageIcon(image3));
		diary2 = new JLabel(new ImageIcon(image3));
		diary3 = new JLabel(new ImageIcon(image3));
		diary4 = new JLabel(new ImageIcon(image3));
		bottle1 = new JLabel();
		bottle2 = new JLabel();
		bottle3 = new JLabel();
		bottle4 = new JLabel();
		fName1 = new JLabel();
		fName2 = new JLabel();
		fName3 = new JLabel();
		fName4 = new JLabel();
		diarylabel = new JLabel(new ImageIcon(image4));

		print1.setFont(mf);
		print2.setFont(mf);
		print3.setFont(mf);
		print4.setFont(mf);
		print1.setBounds(200, 60, 605, 55);
		print2.setBounds(200, 60, 605, 55);
		print3.setBounds(200, 60, 605, 55);
		print4.setBounds(200, 60, 605, 55);
		print1.setEditable(false);
		print2.setEditable(false);
		print3.setEditable(false);
		print4.setEditable(false);
		readdiary();
		bottle1.setBounds(100, 20, image5.getWidth(), image5.getHeight());
		bottle2.setBounds(100, 160, image5.getWidth(), image5.getHeight());
		bottle3.setBounds(100, 300, image5.getWidth(), image5.getHeight());
		bottle4.setBounds(100, 442, image5.getWidth(), image5.getHeight());
		fName1.setBounds(150, 20, 200, 100);
		fName2.setBounds(150, 160, 200, 100);
		fName3.setBounds(150, 300, 200, 100);
		fName4.setBounds(150, 442, 200, 100);
		diary1.add(print1);
		diary2.add(print2);
		diary3.add(print3);
		diary4.add(print4);
		diarylabel.setLayout(new GridLayout(5, 1));
		diarylabel.add(diary1);
		diarylabel.add(diary2);
		diarylabel.add(diary3);
		diarylabel.add(diary4);
		diarylabel.setBounds(0, -10, image1.getWidth(), image1.getHeight());
	}

	public void connection() throws SQLException {
		try {
			conn = DriverManager.getConnection(url, username, sqlpassword);
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
			System.out.println("資料庫處理錯誤!!!");
		}
	}

	public void readdiary() {
		try {
			Statement stat = conn.createStatement();
			Statement stat1 = conn.createStatement();
			Statement stat2 = conn.createStatement();
			String query = String.format(
					"SELECT `GoodDiary`,`BadDiary`,`Friend_Diary`FROM `Diary` WHERE `UserID`='%s' and(`GoodDiary` is not null or `BadDiary`is not null or `Friend_Diary` is not null) ORDER BY`Date`DESC LIMIT 4",
					ID);
			String query1 = String.format(
					"SELECT DATE_FORMAT(`Date`, '%%Y %%b %%d ') FROM `Diary` WHERE `UserID`='%s' ORDER BY`Date`DESC LIMIT 4",
					ID);
			String query2 = String
					.format("SELECT `Friend_Name` FROM `Diary` WHERE `UserID`='%s ' ORDER BY`Date`DESC LIMIT 4", ID);
			boolean hasResultSet = stat.execute(query);
			boolean hasResultSet1 = stat1.execute(query1);
			boolean hasResultSet2 = stat2.execute(query2);
			if (hasResultSet & hasResultSet1 & hasResultSet2) {
				ResultSet result = stat.getResultSet();
				ResultSet result1 = stat1.getResultSet();
				ResultSet result2 = stat2.getResultSet();
				showResultSet(result, result1, result2);
				result.close();
				result1.close();
				result2.close();
			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
			System.out.println("資料庫處理錯誤!!!");
		}
	}

	public void setsearchbox() {
		for (int i = 2021; i <= 2030; i++) {
			box1.addItem(String.format("%d", i));
			box1.setLocation(100, 600);
		}
		for (int i = 1; i <= 9; i++) {
			box2.addItem(String.format("0%d", i));
		}
		for (int i = 10; i <= 12; i++) {
			box2.addItem(String.format("%d", i));
			box2.setLocation(200, 600);
		}
		for (int i = 1; i <= 9; i++) {
			box3.addItem(String.format("0%d", i));
		}
		for (int i = 10; i <= 31; i++) {
			box3.addItem(String.format("%d", i));
			box3.setLocation(300, 600);
		}
	}

	public void findsearch() {
		String date = String.format("%s-%s-%s", box1.getSelectedItem(), box2.getSelectedItem(), box3.getSelectedItem());
		try {
			Statement stat = conn.createStatement();
			Statement stat1 = conn.createStatement();
			Statement stat2 = conn.createStatement();
			String query = String.format(
					"SELECT `GoodDiary`,`BadDiary`,`Friend_Diary`FROM `Diary` WHERE `UserID`='%s' and `Date` <='%s 24:00:00' ORDER BY`Date`DESC LIMIT 4",
					ID, date);
			String query1 = String.format(
					"SELECT DATE_FORMAT(`Date`, '%%Y %%b %%d ') FROM `Diary` WHERE `UserID`='%s ' and `Date` <='%s 24:00:00' ORDER BY`Date`DESC LIMIT 4",
					ID, date);
			String query2 = String.format(
					"SELECT `Friend_Name` FROM `Diary` WHERE `UserID`='%s ' and `Date` <='%s 24:00:00' ORDER BY`Date`DESC LIMIT 4",
					ID, date);
			boolean hasResultSet = stat.execute(query);
			boolean hasResultSet1 = stat1.execute(query1);
			boolean hasResultSet2 = stat2.execute(query2);
			past1.setVisible(false);
			past2.setVisible(false);
			past3.setVisible(false);
			past4.setVisible(false);
			if (hasResultSet & hasResultSet1 & hasResultSet2) {
				ResultSet result = stat.getResultSet();
				ResultSet result1 = stat1.getResultSet();
				ResultSet result2 = stat2.getResultSet();
				showResultSet(result, result1, result2);
				result.close();
				result1.close();
				result2.close();
			}
		} catch (SQLException e) {
			System.out.println("錯誤訊息:" + e.getMessage());
			System.out.println("資料庫處理錯誤!!!");
		}
	}

	public static void showResultSet(ResultSet result, ResultSet result1, ResultSet result2) throws SQLException {
		info = new ArrayList<String>();
		date = new ArrayList<String>();
		num = new ArrayList<BufferedImage>();
		nameList = new ArrayList<String>();

		bottle4.setIcon(null);
		print4.setText("");
		bottle3.setIcon(null);
		print3.setText("");
		bottle2.setIcon(null);
		print2.setText("");
		bottle1.setIcon(null);
		print1.setText("");
		String index = "";
		String index1 = "";
		String index2 = "";

		int j = 0;
		while (result.next()) {
			for (int i = 1; i <= 3; i++) {
				if (result.getString(i) != null) {
					index += result.getString(i);
					info.add(j, index);
					if (i == 1) {
						num.add(image5);
					} else if (i == 2) {
						num.add(image6);
					} else if (i == 3) {
						num.add(image7);
					}
				}
			}
			index = "";
			j++;
		}
		j = 0;
		while (result1.next()) {
			if (result1.getString(1) != null) {
				index1 += result1.getString(1);
				date.add(j, index1);
			}
			index1 = "";
			j++;
		}
		j = 0;
		while (result2.next()) {
			if (result2.getString(1) != null) {
				index2 += result2.getString(1);
				nameList.add(j, index2);
			}
			index2 = "";
			j++;
		}
		if (date.size() > 0) {
			bottle4.setIcon(new ImageIcon(num.get(0)));
			print4.setText(String.format("%-20s%-20s%s", date.get(0), nameList.get(0), info.get(0)));
			Text1 = info.get(0);
			name1 = nameList.get(0);
			image_Test1 = num.get(0);
			past4.setVisible(true);

			if (date.size() > 1) {
				bottle3.setIcon(new ImageIcon(num.get(1)));
				print3.setText(String.format("%-20s%-20s%s", date.get(1), nameList.get(1), info.get(1)));
				Text2 = info.get(1);
				name2 = nameList.get(1);
				image_Test2 = num.get(1);
				past3.setVisible(true);

				if (date.size() > 2) {
					bottle2.setIcon(new ImageIcon(num.get(2)));
					print2.setText(String.format("%-20s%-20s%s", date.get(2), nameList.get(2), info.get(2)));
					Text3 = info.get(2);
					name3 = nameList.get(2);
					image_Test3 = num.get(2);
					past2.setVisible(true);

					if (date.size() > 3) {
						bottle1.setIcon(new ImageIcon(num.get(3)));
						print1.setText(String.format("%-20s%-20s%s", date.get(3), nameList.get(3), info.get(3)));
						Text4 = info.get(3);
						name4 = nameList.get(3);
						image_Test4 = num.get(3);
						past1.setVisible(true);

					}
				}
			}
		}

	}
}