import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Statistic {
	private JFrame frame;
	private JLabel background;
	private JButton back, ad, diary;
	private String ID;
	private BufferedImage img_bg, img_back, img_ad, img_diary, img_g1, img_g2, img_b1, img_b2, img_diary2;
	private String bgPath, backPath, adPath, diaryPath, g1Path, g2Path, b1Path, b2Path, diary2Path;
	private String server = "jdbc:mysql://140.119.19.73:9306/";
	private String database = "TG06?useUnicode=true&characterEncoding=UTF-8";
	private String url = server + database;
	private String username = "TG06";
	private String sqlpassword = "bMIEqf";
	private Connection conn = null;

	public Statistic(String ID) throws SQLException {
		this.ID = ID;
		LoadMainFile();
		createFrame();
	}

	public void createFrame() throws SQLException {
//		if() {
//			setBackground(img_b1, b1Path);  讓你決定心情好壞的地方
//		}
		setBackground(img_b1, b1Path); //上面是根據心情好壞放背景，弄好後這行可以砍掉
		background = new JLabel(new ImageIcon(img_bg));
		frame = new JFrame();

		createLabel();
		frame.getContentPane().add(background);
		frame.pack();
		frame.setTitle("Statistic");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
	}

	public void createLabel() throws SQLException {
		back = new JButton(new ImageIcon(img_back));
		back.setActionCommand("Back");
		back.setBounds(600, 530, img_back.getWidth(), img_back.getHeight());
		back.setContentAreaFilled(false);
		back.setBorder(null);

		ad = new JButton(new ImageIcon(img_ad));
		ad.setActionCommand("Advice");
		ad.setBounds(159, 530, img_ad.getWidth(), img_ad.getHeight());
		ad.setContentAreaFilled(false);
		ad.setBorder(null);

		diary = new JButton(new ImageIcon(img_diary));
		diary.setActionCommand("Diary");
		diary.setBounds(380, 530, img_diary.getWidth(), img_diary.getHeight());
		diary.setContentAreaFilled(false);
		diary.setBorder(null);

		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if (command.equals("Back")) {
					frame.setVisible(false);
					try {
						new MainFrame(ID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (command.equals("Advice")) {
					try {
						String webURL = "";
						if (getBGPath().equals(b1Path)) {
							webURL = "https://www.youtube.com";
						} else if (getBGPath().equals(b2Path)) {
							webURL = "";
						} else if (getBGPath().equals(g1Path)) {
							webURL = "";
						} else {
							webURL = "";
						}
				        java.awt.Desktop.getDesktop().browse(java.net.URI.create(webURL));
					} catch (java.lang.NullPointerException e) {
						e.printStackTrace();
					} catch (java.io.IOException e) {
				        System.out.println(e.getMessage());
				    }
				} else if (command.equals("Diary")) {
					background.setIcon(new ImageIcon(img_diary2));//把背景清空給你放日記
					
				}
			}
		}
		ActionListener listener = new ButtonListener();
		back.addActionListener(listener);
		ad.addActionListener(listener);
		diary.addActionListener(listener);

		frame.add(back);
		frame.add(ad);
		frame.add(diary);
	}

	public void LoadMainFile() {
		backPath = "/back3.png";
		adPath = "/advice.png";
		diaryPath = "/diary.png";
		g1Path = "/g1.png";
		g2Path = "/g2.png";
		b1Path = "/b1.png";
		b2Path = "/b2.png";
		diary2Path = "/diary2.png";
		java.net.URL imgURL;
		try {
			imgURL = Statistic.class.getResource(backPath);
			img_back = ImageIO.read(imgURL);
			imgURL = Statistic.class.getResource(adPath);
			img_ad = ImageIO.read(imgURL);
			imgURL = Statistic.class.getResource(diaryPath);
			img_diary = ImageIO.read(imgURL);
			imgURL = Statistic.class.getResource(g1Path);
			img_g1 = ImageIO.read(imgURL);
			imgURL = Statistic.class.getResource(g2Path);
			img_g2 = ImageIO.read(imgURL);
			imgURL = Statistic.class.getResource(b1Path);
			img_b1 = ImageIO.read(imgURL);
			imgURL = Statistic.class.getResource(b2Path);
			img_b2 = ImageIO.read(imgURL);
			imgURL = Statistic.class.getResource(diary2Path);
			img_diary2 = ImageIO.read(imgURL);
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: " + bgPath);
			img_bg = null;
		}
	}

	public void setBackground(BufferedImage image, String path) {
		img_bg = image;
		bgPath = path;
	}

	public String getBGPath() {
		String path = "";
		path = bgPath;
		return path;
	}
	
	public void open() {
		frame.setVisible(true);
	}
}
