import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherBuild {
	private List<String> weathers;
	private Map<String, String> map;

	public WeatherBuild() {
		weathers = new ArrayList<String>();
		map = new HashMap<>();
		setWeather();
	}

	public void setWeather() {
		try {
//			Path path = FileSystems.getDefault().getPath("weather.txt");
//			String content = Files.readString(path, StandardCharsets.UTF_8);
			String content = readFromJARFile("weather.txt");
			List<String> list = new ArrayList<String>(Arrays.asList(content.split("\n")));
			for (int i = 0; i < list.size(); i++) {
				list.set(i, list.get(i).replace("\n", ""));
				list.set(i, list.get(i).replace("\r", ""));
			}

			Map<String, String> map = new HashMap<>();
			map.put(list.get(0), "01");
			for (int j = 0; j < 7; j++) {
				map.put(list.get(j), "0" + (j + 1));
			}
			for (int j = 7; j < 17; j++) {
				map.put(list.get(j), "08");
			}
			map.put(list.get(17), "09");
			map.put(list.get(18), "09");
			for (int j = 19; j < 21; j++) {
				map.put(list.get(j), "10");
			}
			for (int j = 21; j < 27; j++) {
				map.put(list.get(j), "11");
			}
			for (int j = 27; j < 31; j++) {
				map.put(list.get(j), "12");
			}
			for (int j = 31; j < 34; j++) {
				map.put(list.get(j), "13");
			}
			for (int j = 34; j < 41; j++) {
				map.put(list.get(j), "14");
			}
			for (int j = 41; j < 52; j++) {
				map.put(list.get(j), "15");
			}
			for (int j = 52; j < 59; j++) {
				map.put(list.get(j), "16");
			}
			for (int j = 59; j < 64; j++) {
				map.put(list.get(j), "17");
			}
			for (int j = 64; j < 82; j++) {
				map.put(list.get(j), "18");
			}
			for (int j = 82; j < 96; j++) {
				map.put(list.get(j), "19");
			}
			for (int j = 96; j < 106; j++) {
				map.put(list.get(j), "20");
			}
			for (int j = 106; j < 122; j++) {
				map.put(list.get(j), "21");
			}
			for (int j = 122; j < 135; j++) {
				map.put(list.get(j), "22");
			}
			for (int j = 135; j < 173; j++) {
				map.put(list.get(j), "23");
			}
			for (int j = 173; j < 175; j++) {
				map.put(list.get(j), "24");
			}
			for (int j = 175; j < 177; j++) {
				map.put(list.get(j), "25");
			}
			for (int j = 177; j < 179; j++) {
				map.put(list.get(j), "26");
			}
			for (int j = 179; j < 183; j++) {
				map.put(list.get(j), "27");
			}
			for (int j = 183; j < 189; j++) {
				map.put(list.get(j), "28");
			}
			for (int j = 189; j < 193; j++) {
				map.put(list.get(j), "29");
			}
			for (int j = 193; j < 209; j++) {
				map.put(list.get(j), "30");
			}
			for (int j = 209; j < 231; j++) {
				map.put(list.get(j), "31");
			}
			for (int j = 231; j < 272; j++) {
				map.put(list.get(j), "32");
			}
			for (int j = 272; j < 276; j++) {
				map.put(list.get(j), "33");
			}
			for (int j = 276; j < 292; j++) {
				map.put(list.get(j), "34");
			}
			for (int j = 292; j < 305; j++) {
				map.put(list.get(j), "35");
			}
			for (int j = 305; j < 336; j++) {
				map.put(list.get(j), "36");
			}
			for (int j = 336; j < 342; j++) {
				map.put(list.get(j), "37");
			}
			for (int j = 342; j < 346; j++) {
				map.put(list.get(j), "38");
			}
			for (int j = 346; j < 348; j++) {
				map.put(list.get(j), "39");
			}
			for (int j = 348; j < 350; j++) {
				map.put(list.get(j), "40");
			}
			for (int j = 350; j < 353; j++) {
				map.put(list.get(j), "41");
			}
			this.weathers = list;
			this.map = map;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public String readFromJARFile(String filename) throws IOException {
		InputStream is = getClass().getResourceAsStream(filename);
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		br.close();
		isr.close();
		is.close();
		return sb.toString();
	}

	public List<String> getWeathers() {
		return weathers;
	}

	public Map<String, String> getMap() {
		return map;
	}
}