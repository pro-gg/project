package Project.pro.gg.API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class KakaoAPI {
	public String getAccessToken(String code) {
		String accessToken = "";
		String refreshToken = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=909c3c588f9893fec11631686c08c54a");
			sb.append("&redirect_uri=https://progg.cf/kakao.do");
			sb.append("&code="+code);

			bw.write(sb.toString());
			bw.flush();

			int responseCode = conn.getResponseCode();
			System.out.println("response code = " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";
			while((line = br.readLine()) != null) {
				result += line;
			}

			System.out.println("response body="+result);

			JSONParser parser = new JSONParser();
			JSONObject element = (JSONObject)parser.parse(result);

			accessToken = (String)element.get("access_token");
			refreshToken = (String)element.get("refresh_token");

			br.close();
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	public HashMap<String, String> getUserInfo(String accessToken){
		HashMap<String, String> userInfo = new HashMap<String, String>();
		String reqUrl = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String res = "";

			while((line = br.readLine())!= null) {
				res += line;
			}
			System.out.println("response body = "+res);

			JSONParser parsing = new JSONParser();
			JSONObject result = (JSONObject)parsing.parse(res.toString());

			JSONObject properties = (JSONObject)result.get("properties");
			JSONObject kakaoAccount = (JSONObject)result.get("kakao_account");
			String id = result.get("id").toString();
			String email = (String)kakaoAccount.get("email");
			String nickname = (String)properties.get("nickname");

			userInfo.put("id", id);
			userInfo.put("nickname", nickname);
			userInfo.put("email", email);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}
}