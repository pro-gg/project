package Project.pro.gg.Controller;

import Project.pro.gg.Model.*;
import Project.pro.gg.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final MemberService memberService;
    private final SummonerService summonerService;
    private final MatchDataService matchDataService;
    private final TeamService teamService;

    String developKey = "RGAPI-d2e8a04d-ab09-4524-81ef-49715bea3ebb";
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
    public String updateSummonerData(Model model) throws UnsupportedEncodingException {
        // MemberController 에서 로그인 을 통해 생성된 세션 값을 가져온다.
        HttpSession session = MemberController.session;

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        memberService.deleteSummonerName(memberDTO);
        model.addAttribute("member", memberDTO);

        TeamApplyDTO teamApplyDTO = teamService.selectApplyStatus(memberDTO.getNickname());

        // 소속된 팀에서 추방시키는 로직과 특정팀에 대한 지원 내역이 있을 시 그 또한 취소되는 로직을 작성한다.
        // 팀에 소속되어 있을 경우 추방시키는 로직
        if (memberDTO.getTeamName() != null){
            return "redirect:/crewsecession.do?teamName="+URLEncoder.encode(memberDTO.getTeamName(), "UTF-8")+"&target=updateSummonerName";
        }else if (teamApplyDTO != null){
            return "redirect:/rejectapply.do?nickname="+URLEncoder.encode(memberDTO.getNickname(), "UTF-8")+
                    "&teamName="+URLEncoder.encode(teamApplyDTO.getTeamName(), "UTF-8")+"&target=updateSummonerName";
        }
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
    public String matchHistory(@RequestParam("summoner_name") String summoner_name, @RequestParam("target") String target,
                               Model model) throws JSONException {

        // MemberController 에서 로그인 을 통해 생성된 세션 값을 가져온다.
        HttpSession session = MemberController.session;
        MemberDTO memberDTO = null;

        // 마이페이지, 닉네임 검색 중 어느 경로로 요청이 온 것인지 판별한다.
        if (target.equals("callMyPage")){
            memberDTO = (MemberDTO) session.getAttribute("member");
        }else {
            memberDTO = memberService.findByNickname(target);
            System.out.println(memberDTO.getNickname());
        }

        MatchDataDTO matchData = new MatchDataDTO();

        SummonerDTO summonerDTO = new SummonerDTO();
        summonerDTO.setSummoner_name(summoner_name);
        summonerDTO = summonerService.selectSummonerData(summonerDTO);

        String puuid = summonerDTO.getPuuid();
        List<String> matchIdList = new ArrayList<>();

        try{
            apiURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"+puuid+"/ids?start="+0+"&count="+30+"&api_key="+developKey;
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
                        json_spellList.put("summoner1Id", jsonMatchData.getInt("summoner1Id"));
                        json_spellList.put("summoner2Id", jsonMatchData.getInt("summoner2Id"));
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

        JSONObject jsonObject_itemList = null;
        JSONObject jsonObject_spellList = null;

        for (int i = 0; i < matchDataDTOList.size(); i++){
            // 구매한 아이템들 이미지 처리
            List<String> itemImageList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject((Map) matchDataDTOList.get(i));
            jsonObject_itemList = new JSONObject(jsonObject.getString("itemList"));

            String item0 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item0")+".png";
            String item1 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item1")+".png";
            String item2 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item2")+".png";
            String item3 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item3")+".png";
            String item4 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item4")+".png";
            String item5 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item5")+".png";
            String item6 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item6")+".png";

            itemImageList.add(item0);
            itemImageList.add(item1);
            itemImageList.add(item2);
            itemImageList.add(item3);
            itemImageList.add(item4);
            itemImageList.add(item5);
            itemImageList.add(item6);

            String sendItemImage = "itemlist_List"+i;
            model.addAttribute(sendItemImage, itemImageList);

            // 사용한 소환사 스펠 이미지 처리
            List<String> spellImageList = new ArrayList<>();
            jsonObject_spellList = new JSONObject(jsonObject.getString("spellList"));

            int keyValue1 = jsonObject_spellList.getInt("summoner1Id");
            int keyValue2 = jsonObject_spellList.getInt("summoner2Id");

            SpellDTO spellDTO1 = summonerService.selectSpellData(keyValue1);
            SpellDTO spellDTO2 = summonerService.selectSpellData(keyValue2);

            String spell1 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/spell/"+spellDTO1.getSpellName()+".png";
            String spell2 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/spell/"+spellDTO2.getSpellName()+".png";

            spellImageList.add(spell1);
            spellImageList.add(spell2);

            String sendSummonerSpell = "spellList_List"+i;
            model.addAttribute(sendSummonerSpell, spellImageList);

            // 선택한 챔피언 이미지 처리
            String championName = jsonObject.getString("championName");
            String championImagePath = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/champion/"+championName+".png";

            String sendChampionImage = "championImage"+i;
            model.addAttribute(sendChampionImage, championImagePath);

            try{
                apiURL = "https://ddragon.leagueoflegends.com/cdn/11.12.1/data/ko_KR/champion/"+championName+".json";
                riotURL = new URL(apiURL);
                urlConnection = (HttpURLConnection)riotURL.openConnection();
                urlConnection.setRequestMethod("GET");

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String result="";
                String line="";
                while((line=br.readLine()) != null) {
                    result += line;
                }

                JSONObject jsonObject1 = new JSONObject(result);
                JSONObject championList = jsonObject1.getJSONObject("data");
                JSONObject championJSON = championList.getJSONObject(championName);
                championName = championJSON.getString("name");
            }catch (Exception e){
                e.printStackTrace();
            }

            String sendChapionName = "championName"+i;
            model.addAttribute(sendChapionName, championName);
        }
        return "matchDataList";
    }

    @GetMapping("/searchMatchList.do")
    public String searchMatchList(@RequestParam("nickname") String nickname, @RequestParam("summoner_name") String summoner_name,
                                  Model model) throws UnsupportedEncodingException, JSONException {

        MemberDTO memberDTO = memberService.findByNickname(nickname);
        List<MatchDataDTO> matchDataDTOList = matchDataService.selectMatchDataAll(memberDTO);
        if (matchDataDTOList.size() == 0) {
            return "redirect:/matchHistory.do?summoner_name="+URLEncoder.encode(summoner_name, "UTF-8")+
                    "&target="+URLEncoder.encode(nickname, "UTF-8");
        }
        Collections.reverse(matchDataDTOList);
        model.addAttribute("matchDataList", matchDataDTOList);

        JSONObject jsonObject_itemList = null;
        JSONObject jsonObject_spellList = null;
        
        for (int i = 0; i < matchDataDTOList.size(); i++){
            // 구매한 아이템들 이미지 처리
            List<String> itemImageList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject((Map) matchDataDTOList.get(i));
            jsonObject_itemList = new JSONObject(jsonObject.getString("itemList"));

            String item0 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item0")+".png";
            String item1 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item1")+".png";
            String item2 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item2")+".png";
            String item3 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item3")+".png";
            String item4 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item4")+".png";
            String item5 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item5")+".png";
            String item6 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item6")+".png";

            itemImageList.add(item0);
            itemImageList.add(item1);
            itemImageList.add(item2);
            itemImageList.add(item3);
            itemImageList.add(item4);
            itemImageList.add(item5);
            itemImageList.add(item6);

            String sendItemImage = "itemlist_List"+i;
            model.addAttribute(sendItemImage, itemImageList);

            // 사용한 소환사 스펠 이미지 처리
            List<String> spellImageList = new ArrayList<>();
            jsonObject_spellList = new JSONObject(jsonObject.getString("spellList"));

            int keyValue1 = jsonObject_spellList.getInt("summoner1Id");
            int keyValue2 = jsonObject_spellList.getInt("summoner2Id");

            SpellDTO spellDTO1 = summonerService.selectSpellData(keyValue1);
            SpellDTO spellDTO2 = summonerService.selectSpellData(keyValue2);

            String spell1 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/spell/"+spellDTO1.getSpellName()+".png";
            String spell2 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/spell/"+spellDTO2.getSpellName()+".png";

            spellImageList.add(spell1);
            spellImageList.add(spell2);

            String sendSummonerSpell = "spellList_List"+i;
            model.addAttribute(sendSummonerSpell, spellImageList);

            // 선택한 챔피언 이미지 처리
            String championName = jsonObject.getString("championName");
            String championImagePath = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/champion/"+championName+".png";

            String sendChampionImage = "championImage"+i;
            model.addAttribute(sendChampionImage, championImagePath);

            try{
                apiURL = "https://ddragon.leagueoflegends.com/cdn/11.12.1/data/ko_KR/champion/"+championName+".json";
                riotURL = new URL(apiURL);
                urlConnection = (HttpURLConnection)riotURL.openConnection();
                urlConnection.setRequestMethod("GET");

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String result="";
                String line="";
                while((line=br.readLine()) != null) {
                    result += line;
                }

                JSONObject jsonObject1 = new JSONObject(result);
                JSONObject championList = jsonObject1.getJSONObject("data");
                JSONObject championJSON = championList.getJSONObject(championName);
                championName = championJSON.getString("name");
            }catch (Exception e){
                e.printStackTrace();
            }

            String sendChapionName = "championName"+i;
            model.addAttribute(sendChapionName, championName);
        }
        return "matchDataList";
    }

    // 메인 화면 소환사명 검색 메소드
    // 가져온 데이터를 데이터베이스에 저장하지 않고 곧장 출력한다.
    @GetMapping("/searchSummonerName.do")
    public String searchSummonerName(@RequestParam("summonerName") String summonerName, Model model) throws UnsupportedEncodingException, JSONException {


        SummonerDTO summonerDTO = new SummonerDTO();
        summonerDTO.setSummoner_name(summonerName);
        summonerDTO = summonerService.selectSummonerData(summonerDTO);

        RankedSoloDTO rankedSoloDTO = new RankedSoloDTO();
        RankedFlexDTO rankedFlexDTO = new RankedFlexDTO();

        // 소환사 데이터가 이미 데이터베이스 존재하는 데이터일 경우 처리
        if(summonerDTO != null) {
            model.addAttribute("summoner", summonerDTO);

            rankedSoloDTO = summonerService.selectRankedSoloData(summonerDTO.getId());
            rankedFlexDTO = summonerService.selectRankedFlexData(summonerDTO.getId());
            model.addAttribute("ranked_solo", rankedSoloDTO);
            model.addAttribute("ranked_flex", rankedFlexDTO);

            return "searchSummonerNameResult";
        }
        else{ // 그렇지 않은 경우 처리
            // 소환사 데이터 크롤링
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

                summonerDTO = new SummonerDTO();

                summonerDTO.setSummoner_name(summonerObject.getString("name"));
                summonerDTO.setId(summonerObject.getString("id"));
                summonerDTO.setAccountId(summonerObject.getString("accountId"));
                summonerDTO.setPuuid(summonerObject.getString("puuid"));
                summonerDTO.setProfileiconId(summonerObject.getInt("profileIconId"));
                summonerDTO.setSummonerLevel(summonerObject.getLong("summonerLevel"));
                summonerDTO.setRevisionDate(summonerObject.getLong("revisionDate"));
                String profileIconURL = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/"+summonerDTO.getProfileiconId()+".png";

            } catch (Exception e) {
                System.out.println(e.getMessage());
                model.addAttribute("summoner_name", summonerName);
                return "../valid/notexistSummonerValid";
            }

            // 소환사 데이터를 통한 솔랭, 자랭 데이터 크롤링
            String id = summonerDTO.getId();
            String result = "";
            String line = "";

            JSONArray jsonArray = null;
            JSONObject json_RankedSolo = null;
            JSONObject json_RankedFlex = null;

            int total = 0;
            double rate = 0.0;

            // api 를 통해 데이터를 가져온다.
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

                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            
            // 최근 전적 데이터 크롤링 및 이미지 처리
            String puuid = summonerDTO.getPuuid();
            List<String> matchIdList = new ArrayList<>();

            try{
                apiURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"+puuid+"/ids?start="+0+"&count="+30+"&api_key="+developKey;
                riotURL = new URL(apiURL);
                urlConnection = (HttpURLConnection)riotURL.openConnection();
                urlConnection.setRequestMethod("GET");

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                result="";
                line="";
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

            List<MatchDataDTO> matchDataDTOList = new ArrayList<>();

            for (int i = 0; i < matchIdList.size(); i++){
                try{
                    apiURL = "https://asia.api.riotgames.com/lol/match/v5/matches/"+matchIdList.get(i)+"?api_key="+developKey;
                    riotURL = new URL(apiURL);
                    urlConnection = (HttpURLConnection)riotURL.openConnection();
                    urlConnection.setRequestMethod("GET");

                    br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    result="";
                    line="";
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

                            MatchDataDTO matchData = new MatchDataDTO();

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
                            json_spellList.put("summoner1Id", jsonMatchData.getInt("summoner1Id"));
                            json_spellList.put("summoner2Id", jsonMatchData.getInt("summoner2Id"));
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

                            matchDataDTOList.add(matchData);
                            System.out.println(matchDataDTOList.get(matchDataDTOList.size()-1).getChampionName());

                            break;
                        }
                    }
                }catch (FileNotFoundException fileNotFoundException){
                    System.out.println(matchIdList.get(i) + " : 수집해 올 수 없는 전적 기록입니다.");
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            model.addAttribute("matchDataList", matchDataDTOList);

            JSONObject jsonObject_itemList = null;
            JSONObject jsonObject_spellList = null;

            for (int i = 0; i < matchDataDTOList.size(); i++){
                // 구매한 아이템들 이미지 처리
                List<String> itemImageList = new ArrayList<>();
                jsonObject_itemList = matchDataDTOList.get(i).getJson_itemList();

                String item0 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item0")+".png";
                String item1 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item1")+".png";
                String item2 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item2")+".png";
                String item3 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item3")+".png";
                String item4 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item4")+".png";
                String item5 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item5")+".png";
                String item6 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/item/"+(Integer) jsonObject_itemList.getInt("item6")+".png";

                itemImageList.add(item0);
                itemImageList.add(item1);
                itemImageList.add(item2);
                itemImageList.add(item3);
                itemImageList.add(item4);
                itemImageList.add(item5);
                itemImageList.add(item6);

                String sendItemImage = "itemlist_List"+i;
                model.addAttribute(sendItemImage, itemImageList);

                // 사용한 소환사 스펠 이미지 처리
                List<String> spellImageList = new ArrayList<>();
                jsonObject_spellList = matchDataDTOList.get(i).getJson_spellList();

                int keyValue1 = jsonObject_spellList.getInt("summoner1Id");
                int keyValue2 = jsonObject_spellList.getInt("summoner2Id");

                SpellDTO spellDTO1 = summonerService.selectSpellData(keyValue1);
                SpellDTO spellDTO2 = summonerService.selectSpellData(keyValue2);

                String spell1 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/spell/"+spellDTO1.getSpellName()+".png";
                String spell2 = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/spell/"+spellDTO2.getSpellName()+".png";

                spellImageList.add(spell1);
                spellImageList.add(spell2);

                String sendSummonerSpell = "spellList_List"+i;
                model.addAttribute(sendSummonerSpell, spellImageList);

                // 선택한 챔피언 이미지 처리
                String championName = matchDataDTOList.get(i).getChampionName();
                String championImagePath = "http://ddragon.leagueoflegends.com/cdn/11.12.1/img/champion/"+championName+".png";

                String sendChampionImage = "championImage"+i;
                model.addAttribute(sendChampionImage, championImagePath);

                try{
                    apiURL = "https://ddragon.leagueoflegends.com/cdn/11.12.1/data/ko_KR/champion/"+championName+".json";
                    riotURL = new URL(apiURL);
                    urlConnection = (HttpURLConnection)riotURL.openConnection();
                    urlConnection.setRequestMethod("GET");

                    br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    result="";
                    line="";
                    while((line=br.readLine()) != null) {
                        result += line;
                    }

                    JSONObject jsonObject1 = new JSONObject(result);
                    JSONObject championList = jsonObject1.getJSONObject("data");
                    JSONObject championJSON = championList.getJSONObject(championName);
                    championName = championJSON.getString("name");
                }catch (Exception e){
                    e.printStackTrace();
                }

                String sendChapionName = "championName"+i;
                model.addAttribute(sendChapionName, championName);
            }

            model.addAttribute("summoner", summonerDTO);
            model.addAttribute("ranked_solo", rankedSoloDTO);
            model.addAttribute("ranked_flex", rankedFlexDTO);
        }

        return "searchSummonerNameResult";
    }
}

// 임의로 소환사 스펠 데이터들을 모두 가져올 코드 생성(추후에 주석 처리 또는 코드 제거)
// 데이터베이스에 소환사 스펠 데이터들을 모두 가지고 있어야 소환사 스펠에 대한 이미지 처리가 용이해진다.
//        try{
//                apiURL = "https://ddragon.leagueoflegends.com/cdn/11.12.1/data/ko_KR/summoner.json";
//                riotURL = new URL(apiURL);
//                urlConnection = (HttpURLConnection)riotURL.openConnection();
//                urlConnection.setRequestMethod("GET");
//
//                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//                String result="";
//                String line="";
//                while((line=br.readLine()) != null) {
//                result += line;
//                }
//
//                JSONObject jsonObject = new JSONObject(result);
//                JSONObject spellObjects = jsonObject.getJSONObject("data");
//                // DTO 구조 : 스펠 영문이름(이미지 처리를 위함 - 기본키), name : 스펠 한글 이름, description : 스펠 설명, key : 스펠 키값(이미지 처리 조건)
//                SpellDTO spellDTO = new SpellDTO();
//
//                JSONObject spellObejct = spellObjects.getJSONObject("SummonerBarrier");
//                spellDTO.setSpellName("SummonerBarrier");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerBoost");
//                spellDTO.setSpellName("SummonerBoost");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerDot");
//                spellDTO.setSpellName("SummonerDot");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerExhaust");
//                spellDTO.setSpellName("SummonerExhaust");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerFlash");
//                spellDTO.setSpellName("SummonerFlash");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerHaste");
//                spellDTO.setSpellName("SummonerHaste");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerHeal");
//                spellDTO.setSpellName("SummonerHeal");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerMana");
//                spellDTO.setSpellName("SummonerMana");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerPoroRecall");
//                spellDTO.setSpellName("SummonerPoroRecall");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerPoroThrow");
//                spellDTO.setSpellName("SummonerPoroThrow");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerSmite");
//                spellDTO.setSpellName("SummonerSmite");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerSnowURFSnowball_Mark");
//                spellDTO.setSpellName("SummonerSnowURFSnowball_Mark");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerSnowball");
//                spellDTO.setSpellName("SummonerSnowball");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                spellObejct = spellObjects.getJSONObject("SummonerTeleport");
//                spellDTO.setSpellName("SummonerTeleport");
//                spellDTO.setName(spellObejct.getString("name"));
//                spellDTO.setDescription(spellObejct.getString("description"));
//                spellDTO.setKeyValue(spellObejct.getInt("key"));
//                summonerService.insertSpellData(spellDTO);
//
//                }catch (Exception e){
//                e.printStackTrace();
//                }
