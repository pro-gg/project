package Project.pro.gg.Controller;

import Project.pro.gg.Model.*;
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
import java.util.*;

@Controller
public class SummonerController {

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    SummonerServiceImpl summonerService;

    @Autowired
    MatchDataServiceImpl matchDataService;

    String developKey = "RGAPI-f9c25bdc-ee01-4591-aa09-a31092edb536";
    String apiURL = "";
    URL riotURL = null;
    HttpURLConnection urlConnection = null;
    BufferedReader br = null;

    // 소환사 데이터 검색 및 저장 메소드
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

        if(summonerService.selectSummonerData(summonerDTO) != null) {
            memberDTO.setSummoner_name(null);
            model.addAttribute("summoner_name_exist", summonerName);
            return "../valid/notexistSummonerValid";
        }

        memberDTO.setSummoner_name(summonerDTO.getSummoner_name());
        session.setAttribute("member", memberDTO);
        session.setAttribute("summoner", summonerDTO);

        String summoner_register = "summoner_name_register";

        return "redirect:/updateSummonerData.do?target="+summoner_register;
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

    @GetMapping("/updateSummonerData.do")
    public String updateSummonerData(@RequestParam("target") String target, Model model){

        // MemberController 에서 로그인 을 통해 생성된 세션 값을 가져온다.
        HttpSession session = MemberController.session;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        SummonerDTO summonerDTO = null;

        RankedSoloDTO rankedSoloDTO = new RankedSoloDTO();
        RankedFlexDTO rankedFlexDTO = new RankedFlexDTO();

        String id = null;
        String result = "";
        String line = "";

        JSONArray jsonArray = null;
        JSONObject json_RankedSolo = null;
        JSONObject json_RankedFlex = null;

        int total = 0;
        double rate = 0.0;

        if(target.equals("summoner_name_register")){
            summonerDTO = (SummonerDTO) session.getAttribute("summoner");
            id = summonerDTO.getId();
        } else if (target.equals("updateSummonerData")){
            summonerDTO = new SummonerDTO();
            summonerDTO.setSummoner_name(memberDTO.getSummoner_name());
            summonerDTO = summonerService.selectSummonerData(summonerDTO);

            id = summonerDTO.getId();
        }

        // api 를 통해 데이터를 가져온다.(공통)
        try{
            apiURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"+id+"?api_key="+developKey;
            riotURL = new URL(apiURL);
            urlConnection = (HttpURLConnection)riotURL.openConnection();
            urlConnection.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while((line=br.readLine()) != null) {
                result += line;
            }

            jsonArray = new JSONArray(result);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 소환사 명 등록으로 인해 넘어오는 경우
        if (target.equals("summoner_name_register")){
            summonerService.insertSummonerData(summonerDTO, memberDTO);

            // 소환사 큐 타입별 티어, 승패 횟수 등의 데이터 수집
            try{
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String queueType = jsonObject.getString("queueType");

                    if (queueType.equals("RANKED_SOLO_5x5")){
                        json_RankedSolo = jsonObject;

                        rankedSoloDTO.setId(json_RankedSolo.getString("summonerId"));
                        rankedSoloDTO.setQueueType(json_RankedSolo.getString("queueType"));
                        rankedSoloDTO.setTier(json_RankedSolo.getString("tier"));
                        rankedSoloDTO.setTier_rank(json_RankedSolo.getString("rank"));
                        rankedSoloDTO.setLeaguePoints(json_RankedSolo.getInt("leaguePoints"));
                        rankedSoloDTO.setWins(json_RankedSolo.getInt("wins"));
                        rankedSoloDTO.setLosses(json_RankedSolo.getInt("losses"));

                        total = rankedSoloDTO.getWins() + rankedSoloDTO.getLosses();
                        rate = (double)Math.round((double) rankedSoloDTO.getWins()/(double) total*1000)/10;
                        rankedSoloDTO.setRate(rate);

                        summonerService.insertRankedSoloData(rankedSoloDTO);
                    }
                    if (queueType.equals("RANKED_FLEX_SR")){
                        json_RankedFlex = jsonObject;

                        rankedFlexDTO.setId(json_RankedFlex.getString("summonerId"));
                        rankedFlexDTO.setQueueType(json_RankedFlex.getString("queueType"));
                        rankedFlexDTO.setTier(json_RankedFlex.getString("tier"));
                        rankedFlexDTO.setTier_rank(json_RankedFlex.getString("rank"));
                        rankedFlexDTO.setLeaguePoints(json_RankedFlex.getInt("leaguePoints"));
                        rankedFlexDTO.setWins(json_RankedFlex.getInt("wins"));
                        rankedFlexDTO.setLosses(json_RankedFlex.getInt("losses"));

                        total = rankedFlexDTO.getWins() + rankedFlexDTO.getLosses();
                        rate = (double)Math.round((double) rankedFlexDTO.getWins()/(double) total*1000)/10;
                        rankedFlexDTO.setRate(rate);

                        summonerService.insertRankedFlexData(rankedFlexDTO);
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            model.addAttribute("member", memberDTO);
            session.removeAttribute("summoner");

            return "../valid/summonerNameValid";
        }
        // 마이페이지 정보 갱신을 통해 넘어오는 경우
        else if (target.equals("updateSummonerData")){
            // 소환사 큐 타입별 티어, 승패 횟수 등의 데이터 수집
            try{
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String queueType = jsonObject.getString("queueType");

                    if (queueType.equals("RANKED_SOLO_5x5")){
                        json_RankedSolo = jsonObject;

                        rankedSoloDTO.setId(json_RankedSolo.getString("summonerId"));
                        rankedSoloDTO.setQueueType(json_RankedSolo.getString("queueType"));
                        rankedSoloDTO.setTier(json_RankedSolo.getString("tier"));
                        rankedSoloDTO.setTier_rank(json_RankedSolo.getString("rank"));
                        rankedSoloDTO.setLeaguePoints(json_RankedSolo.getInt("leaguePoints"));
                        rankedSoloDTO.setWins(json_RankedSolo.getInt("wins"));
                        rankedSoloDTO.setLosses(json_RankedSolo.getInt("losses"));

                        total = rankedSoloDTO.getWins() + rankedSoloDTO.getLosses();
                        rate = (double)Math.round((double) rankedSoloDTO.getWins()/(double) total*1000)/10;
                        rankedSoloDTO.setRate(rate);

                        summonerService.updateRankedSoloData(rankedSoloDTO);
                    }
                    if (queueType.equals("RANKED_FLEX_SR")){
                        json_RankedFlex = jsonObject;

                        rankedFlexDTO.setId(json_RankedFlex.getString("summonerId"));
                        rankedFlexDTO.setQueueType(json_RankedFlex.getString("queueType"));
                        rankedFlexDTO.setTier(json_RankedFlex.getString("tier"));
                        rankedFlexDTO.setTier_rank(json_RankedFlex.getString("rank"));
                        rankedFlexDTO.setLeaguePoints(json_RankedFlex.getInt("leaguePoints"));
                        rankedFlexDTO.setWins(json_RankedFlex.getInt("wins"));
                        rankedFlexDTO.setLosses(json_RankedFlex.getInt("losses"));

                        total = rankedFlexDTO.getWins() + rankedFlexDTO.getLosses();
                        rate = (double)Math.round((double) rankedFlexDTO.getWins()/(double) total*1000)/10;
                        rankedFlexDTO.setRate(rate);

                        summonerService.updateRankedFlexData(rankedFlexDTO);
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        return "redirect:/move/mypage.do";
    }

    @GetMapping("/printSummonerData_aside.do")
    public String pringSummonerData_aside(Model model){
        HttpSession session = MemberController.session;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        SummonerDTO summonerDTO = new SummonerDTO();
        summonerDTO.setSummoner_name(memberDTO.getSummoner_name());
        summonerDTO = summonerService.selectSummonerData(summonerDTO);

        RankedSoloDTO rankedSoloDTO = new RankedSoloDTO();
        RankedFlexDTO rankedFlexDTO = new RankedFlexDTO();

        rankedSoloDTO = summonerService.selectRankedSoloData(summonerDTO.getId());
        if(rankedSoloDTO != null){
            model.addAttribute("ranked_solo", rankedSoloDTO);
        }
        rankedFlexDTO = summonerService.selectRankedFlexData(summonerDTO.getId());
        if(rankedFlexDTO != null){
            model.addAttribute("ranked_flex", rankedFlexDTO);
        }

        return "aside_ranked";
    }

    @GetMapping("/printSummonerData_mypage.do")
    public String pringSummonerData_mypage(Model model){

        HttpSession session = MemberController.session;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        SummonerDTO summonerDTO = new SummonerDTO();
        summonerDTO.setSummoner_name(memberDTO.getSummoner_name());
        summonerDTO = summonerService.selectSummonerData(summonerDTO);

        RankedSoloDTO rankedSoloDTO = new RankedSoloDTO();
        RankedFlexDTO rankedFlexDTO = new RankedFlexDTO();

        rankedSoloDTO = summonerService.selectRankedSoloData(summonerDTO.getId());
        if(rankedSoloDTO != null){
            model.addAttribute("ranked_solo", rankedSoloDTO);
        }
        rankedFlexDTO = summonerService.selectRankedFlexData(summonerDTO.getId());
        if(rankedFlexDTO != null){
            model.addAttribute("ranked_flex", rankedFlexDTO);
        }

        return "mypage_ranked";
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
        Collections.reverse(matchDataDTOList);
        model.addAttribute("matchDataList", matchDataDTOList);
        return "matchDataList";
    }
}
