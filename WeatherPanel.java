import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherPanel extends JPanel {
	private JLabel imgLabel, textLabel;
	private Crawler crawler;
	private WeatherBuild weatherBuild;
	private BufferedImage img_weather;
	private String path;
	private java.net.URL imgURL;
	private Date nowTime;

	public WeatherPanel(Crawler crawler, WeatherBuild weatherBuild) {
		this.crawler = crawler;
		this.weatherBuild = weatherBuild;
		createComp();
	}

	public void createComp() {
		imgLabel = new JLabel();
		path = "";
		int i;
		for (i = 1; i <= 2; i++) {
			if (weatherBuild.getMap().get(crawler.getWeather()).equals("0" + Integer.toString(i))) {
				path = "/" + "sunny" + ".png";
				break;
			}
		}
		for (i = 3; i >= 3 && i <= 7; i++) {
			if (weatherBuild.getMap().get(crawler.getWeather()).equals("0" + Integer.toString(i))) {
				path = "/" + "cloudy" + ".png";
				break;
			}
		}
		for (i = 8; i >= 8 && i <= 23; i++) {
			if (i >= 8 && i <= 9) {
				if (weatherBuild.getMap().get(crawler.getWeather()).equals("0" + Integer.toString(i))) {
					path = "/" + "rainy" + ".png";
					break;
				}
			}
			if (weatherBuild.getMap().get(crawler.getWeather()).equals(Integer.toString(i))) {
				path = "/" + "rainy" + ".png";
				break;
			}
		}
		for (i = 24; i >= 24 && i <= 26; i++) {
			if (weatherBuild.getMap().get(crawler.getWeather()).equals(Integer.toString(i))) {
				path = "/" + "sunny" + ".png";
				break;
			}
		}
		for (i = 27; i >= 27 && i <= 36; i++) {
			if (weatherBuild.getMap().get(crawler.getWeather()).equals(Integer.toString(i))) {
				path = "/" + "cloudy" + ".png";
				break;
			}
		}
		for (i = 37; i >= 37 && i <= 41; i++) {
			if (weatherBuild.getMap().get(crawler.getWeather()).equals(Integer.toString(i))) {
				path = "/" + "rainy" + ".png";
				break;
			}
		}
		if (time()) {
			if (path.equals("/sunny.png")) {
				path = "/sunny_night.png";
			} else if (path.equals("/cloudy.png")) {
				path = "/cloudy_night.png";
			} else if (path.equals("/rainy.png")) {
				path = "/rainy_night.png";
			}
		}
		try {
			imgURL = WeatherPanel.class.getResource(path);
			img_weather = ImageIO.read(imgURL);
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, "¸ü¤J¹ÏÀÉ¿ù»~: " + path);
		}
		imgLabel.setIcon(new ImageIcon(img_weather));
		add(imgLabel);
		setSize(img_weather.getWidth(), img_weather.getHeight());
		setOpaque(false);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean time() {
		try {
			SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
			nowTime = new Date();
			nowTime = sdFormat.parse(sdFormat.format(nowTime));
			Calendar date = Calendar.getInstance();
			date.setTime(nowTime);

			Date beginTime = sdFormat.parse("05:30");
			Calendar begin = Calendar.getInstance();
			begin.setTime(beginTime);

			Date endTime = sdFormat.parse("18:00");
			Calendar end = Calendar.getInstance();
			end.setTime(endTime);

			if (date.after(begin) && date.before(end)) {
				return false;
			} else if (nowTime.getTime() == beginTime.getTime() || nowTime.getTime() == endTime.getTime()) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {

		}
		return false;
	}
}
