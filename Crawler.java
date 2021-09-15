import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class Crawler {
	private static final String URL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-228C4368-DC90-48B5-99C0-724EFD090DF3&format=JSON&locationName=%E8%87%BA%E5%8C%97%E5%B8%82&elementName=Wx";
	private String location;
	private String weather;
	public Crawler() {
		crawling();
	}
	
	public void crawling() {
		try {
			URL url = new URL(getUrl());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			String urlString = "";

			while ((inputLine = in.readLine()) != null) {
				urlString += inputLine;
			}
			in.close();

			JSONObject json = JSONObject.fromObject(urlString);			
			json = json.getJSONObject("records");			
			json = (JSONObject) json.getJSONArray("location").get(0);
			location = json.getString("locationName");
			json = (JSONObject) json.getJSONArray("weatherElement").get(0);
			json = (JSONObject) json.getJSONArray("time").get(1);
			json = json.getJSONObject("parameter");
			weather = json.getString("parameterName");

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public String getUrl() {
		return URL;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getWeather() {
		return weather;
	}

}
