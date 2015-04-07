package parsing;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class WebsiteParser {
	private final static String USER_AGENT = "Mozilla/5.0";
	private final static boolean debug = true;
	
	protected String returnMatchedValue(Pattern pattern, String searchString){
		return returnMatchedValue(pattern, searchString, 1);
	}
	
	protected String returnMatchedValue(Pattern pattern, String searchString, int groupId){
		Matcher matcher = pattern.matcher(searchString);
		
		while (matcher.find()) {
			// return first occurence
			return matcher.group(groupId);
		}
		
		return null;
	}
	
	protected String[] returnMatchedValue(Pattern pattern, String searchString, int[] groupId){
		Matcher matcher = pattern.matcher(searchString);
		
		while (matcher.find()) {
			String[] result = new String[groupId.length];
			
			for(int i=0;i<groupId.length;i++){
				result[i] = matcher.group(groupId[i]);
			}
			
			// return first occurence
			return result;
		}
		
		return null;
	}
	
	// HTTP GET request
	protected static String sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		
		if( debug ){
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		}
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();
	}
	
	protected static String sendPost(String url, String postData) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		String urlParameters = postData;//"sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
 
		// Send post request
		con.setDoOutput(true);
		
		// get response
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		
		if( debug ){
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
		}
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();
	}
}
