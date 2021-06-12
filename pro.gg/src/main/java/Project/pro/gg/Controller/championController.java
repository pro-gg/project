package Project.pro.gg.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class championController {
	
	BufferedReader br = null;
	String urlstr = "";
	URL rioturl = null;
	HttpURLConnection urlconnection = null;
	
	@GetMapping("/champion.do")
    public String champion(Model model) {
		
		try {
			urlstr = "https://ddragon.leagueoflegends.com/cdn/11.11.1/data/ko_KR/champion.json";
			rioturl = new URL(urlstr);
			urlconnection = (HttpURLConnection)rioturl.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			String result = "";
			String line;
			while((line=br.readLine())!= null) {
				result = result + line;
			}
			JSONParser jsonParser = new JSONParser();
			JSONObject k = (JSONObject) jsonParser.parse(result);
			JSONObject data = (JSONObject) k.get("data");
			Iterator iter = data.keySet().iterator();
			List<String> champList = new ArrayList<String>();
			String imgPath = "https://ddragon.leagueoflegends.com/cdn/11.11.1/img/champion/";
			while(iter.hasNext()) {
				champList.add((String)iter.next());
			}
			model.addAttribute("champList", champList);
			model.addAttribute("imgPath", imgPath);
		}catch(Exception e) {
			
		}
    	return "champion";
    }
	
	@GetMapping("champInfo.do")
	public String champInfo(@RequestParam("champ") String champion, Model model) {
		try {
			urlstr = "http://ddragon.leagueoflegends.com/cdn/11.11.1/data/ko_KR/champion/"+champion+".json";
			rioturl = new URL(urlstr);
			urlconnection = (HttpURLConnection)rioturl.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			String result = "";
			String line;
			while((line=br.readLine())!= null) {
				result = result + line;
			}
			JSONParser jsonParser = new JSONParser();
			JSONObject k = (JSONObject) jsonParser.parse(result);
			JSONObject data = (JSONObject) k.get("data");
			JSONObject champJson = (JSONObject) data.get(champion);
			JSONObject passiveJson = (JSONObject) champJson.get("passive");
			passiveJson = (JSONObject)passiveJson.get("image");
			String passive = (String)passiveJson.get("full");
			JSONArray spells = (JSONArray) champJson.get("spells");
			
			List<String> spellList = new ArrayList<String>();
			for(int i = 0; i<spells.size(); i++) {
				JSONObject tmp = (JSONObject) spells.get(i);
				
				spellList.add((String)tmp.get("id"));
			}
			model.addAttribute("champion", champion);
			model.addAttribute("passive", passive);
			model.addAttribute("spells", spellList);
		}catch(Exception e) {
			
		}
		return "champInfo";
	}
	
}
