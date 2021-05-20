package Project.pro.gg.Controller;

import Project.pro.gg.Model.MatchDataDTO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Service.MatchDataServiceImpl;
import Project.pro.gg.Service.MemberServiceImpl;
import Project.pro.gg.Service.SummonerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SummonerController {

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    SummonerServiceImpl summonerService;

    @Autowired
    MatchDataServiceImpl matchDataService;

    String developKey = "RGAPI-10288975-bbd3-41f8-b111-e927fd7c5926";
    String apiURL = "";
    URL riotURL = null;
    HttpURLConnection urlConnection = null;
    BufferedReader br = null;

    // 소환사 데이터 검색 및 저장 
    @GetMapping("/SearchSummonerData.do")
    public String searchSummonerData(@RequestParam("summonerName") String summonerName, Model model){

        // MemberController 에서 로그인 을 통해 생성된 세션 값을 가져온다.
        HttpSession session = MemberController.session;

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        SummonerDTO summonerDTO = null;
        try {
            JSONObject jsonObject = new JSONObject(summonerName);
            summonerName = URLEncoder.encode((String) jsonObject.get("summonerName"), "UTF-8");

            apiURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+summonerName+"?api_key="+developKey;
            riotURL = new URL(apiURL);
            urlConnection = (HttpURLConnection)riotURL.openConnection();
            urlConnection.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String result="";
            String line="";
            while((line=br.readLine()) != null) {
                result += line;
            }

            JSONObject summonerObject = new JSONObject(result);

            summonerDTO = new SummonerDTO(
                    summonerObject.getString("name"),
                    summonerObject.getString("id"),
                    summonerObject.getString("accountId"),
                    summonerObject.getString("puuid"),
                    summonerObject.getInt("profileIconId"),
                    summonerObject.getLong("summonerLevel"),
                    summonerObject.getLong("revisionDate"),
                    memberDTO.getUserid());
            String profileIconURL = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/"+summonerDTO.getProfileiconId()+".png";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("summoner_name", summonerName);
            return "../valid/notexistSummonerValid";
        }

        if(summonerService.selectSummonerData(summonerDTO) != null){
            memberDTO.setSummoner_name(null);
            model.addAttribute("summoner_name_exist", summonerName);
            return "../valid/notexistSummonerValid";
        } else{
            memberDTO.setSummoner_name(summonerDTO.getSummoner_name());
            summonerService.insertSummonerData(summonerDTO, memberDTO);
            session.setAttribute("member", memberDTO);
            model.addAttribute("member", (MemberDTO)session.getAttribute("member"));
        }

        return "../valid/summonerNameValid";
    }

    @GetMapping("/updateSummonerName.do")
    public String updateSummonerData(Model model){
        // MemberController 에서 로그인 을 통해 생성된 세션 값을 가져온다.
        HttpSession session = MemberController.session;

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        memberService.deleteSummonerName(memberDTO);
        model.addAttribute("member", memberDTO);
        return "updateSummonerName";
    }

    @GetMapping("/matchHistory.do")
    public String matchHistory(@RequestParam("summoner_name") String summoner_name, Model model){

        // MemberController 에서 로그인 을 통해 생성된 세션 값을 가져온다.
        HttpSession session = MemberController.session;

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        MatchDataDTO matchData = new MatchDataDTO();

        SummonerDTO summonerDTO = new SummonerDTO();
        summonerDTO.setSummoner_name(summoner_name);
        summonerDTO = summonerService.selectSummonerData(summonerDTO);

        String puuid = summonerDTO.getPuuid();
        List<String> matchIdList = new ArrayList<>();

        try{
            apiURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"+puuid+"/ids?start="+0+"&count="+50+"&api_key="+developKey;
            riotURL = new URL(apiURL);
            urlConnection = (HttpURLConnection)riotURL.openConnection();
            urlConnection.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String result="";
            String line="";
            while((line=br.readLine()) != null) {
                result += line;
            }

            JSONArray matchList = new JSONArray(result);
            for (int i = 0; i < matchList.length(); i++) {
                matchIdList.add((String) matchList.get(i));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        for (int i = 0; i < matchIdList.size(); i++){
            try{
                apiURL = "https://asia.api.riotgames.com/lol/match/v5/matches/"+matchIdList.get(i)+"?api_key="+developKey;
                riotURL = new URL(apiURL);
                urlConnection = (HttpURLConnection)riotURL.openConnection();
                urlConnection.setRequestMethod("GET");

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String result="";
                String line="";
                while((line=br.readLine()) != null) {
                    result += line;
                }

                JSONObject jsonObject = new JSONObject(result);
                JSONObject metadata = (JSONObject) jsonObject.get("metadata");
                JSONArray participantsPuuid = metadata.getJSONArray("participants");

                JSONObject info = (JSONObject)jsonObject.get("info");
                JSONArray participantData = info.getJSONArray("participants");

                for(int j = 0; j < participantData.length(); j++) {
                    JSONObject jsonMatchData = (JSONObject) participantData.get(j);

                    if(!jsonMatchData.getString("puuid").equals(puuid))
                        continue;
                    else {

                        matchData.setMatchId(matchIdList.get(i));

                        // 선택한 챔피언 id 값 및 이름
                        matchData.setChampionId(jsonMatchData.getInt("championId"));
                        matchData.setChampionName(jsonMatchData.getString("championName"));

                        // K/D/A 출력
                        matchData.setKills(jsonMatchData.getInt("kills"));
                        matchData.setDeaths(jsonMatchData.getInt("deaths"));
                        matchData.setAssists(jsonMatchData.getInt("assists"));

                        // 승패 출력
                        matchData.setWin(jsonMatchData.getBoolean("win"));

                        // 획득한 골드 및 소모한 골드 값 출력
                        matchData.setGoldEarned(jsonMatchData.getInt("goldEarned"));
                        matchData.setGoldSpent(jsonMatchData.getInt("goldSpent"));

                        // 소환사 스펠 출력
                        JSONObject json_spellList = new JSONObject();
                        json_spellList.put("spell1Casts", jsonMatchData.getInt("spell1Casts"));
                        json_spellList.put("spell2Casts", jsonMatchData.getInt("spell2Casts"));
                        json_spellList.put("spell3Casts", jsonMatchData.getInt("spell3Casts"));
                        json_spellList.put("spell4Casts", jsonMatchData.getInt("spell4Casts"));
                        matchData.setJson_spellList(json_spellList);

                        // 구매한 아이템 리스트 출력
                        JSONObject json_itemList = new JSONObject();
                        json_itemList.put("item0", jsonMatchData.getInt("item0"));
                        json_itemList.put("item1", jsonMatchData.getInt("item1"));
                        json_itemList.put("item2", jsonMatchData.getInt("item2"));
                        json_itemList.put("item3", jsonMatchData.getInt("item3"));
                        json_itemList.put("item4", jsonMatchData.getInt("item4"));
                        json_itemList.put("item5", jsonMatchData.getInt("item5"));
                        json_itemList.put("item6", jsonMatchData.getInt("item6"));
                        matchData.setJson_itemList(json_itemList);

                        // 이미 데이터베이스에 해당 하는 전적 기록이 존재할 경우 건너뛴다, 그렇지 않을 경우 데이터베이스에 삽입한다.
                        if (matchDataService.selectMatchData(matchData, memberDTO) != null) continue;
                        else matchDataService.insertMatchData(matchData, memberDTO);

                        break;
                    }
                }
            }catch (FileNotFoundException fileNotFoundException){
                System.out.println(matchIdList.get(i) + " : 수집해 올 수 없는 전적 기록입니다.");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        List<MatchDataDTO> matchDataDTOList = matchDataService.selectMatchDataAll(memberDTO);
        model.addAttribute("matchDataList", matchDataDTOList);
        return "matchDataList";
    }
}
