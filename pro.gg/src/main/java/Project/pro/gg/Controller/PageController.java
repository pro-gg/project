package Project.pro.gg.Controller;

import Project.pro.gg.Model.RankedSoloDTO;
import Project.pro.gg.Model.TeamDTO;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class PageController {

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/article.do")
    public String article(){
        return "article";
    }

    @GetMapping("/aside.do")
    public String aside(){
        return "aside";
    }

    @GetMapping("/header.do")
    public String header(){ return "header"; }

    @PostMapping("/move/adminpage.do")
    public String adminPage(){ return "admin"; };

    @GetMapping("/move/login.do")
    public String login(){
        return "login";
    }
    
    @GetMapping("/move/terms.do")
    public String terms() {
    	return "registerTerms";
    }

    @PostMapping("/move/register.do")
    public String register(){
        return "register";
    }

    @GetMapping("/move/mypage.do")
    public String myPage(){
        return "mypage";
    }
    
    @GetMapping("/move/teammatch.do")
    public String teammatch() {
    	return "teammatch";
    }

    @GetMapping("/move/findId.do")
    public String findId(){
        return "../popup/findId_popup";
    }

    @GetMapping("/move/findPasswd.do")
    public String findPasswd(){
        return "../popup/findPasswd_popup";
    }

    @GetMapping("/move/updateMember.do")
    public String updateMember(){
        return "updateMemberData";
    }

    @GetMapping("/move/teamCreate.do")
    public String teamCreate(){
        return "teamcreate";
    }

    @GetMapping("/move/currentPasswd_popup.do")
    public String confirmCurrentPasswd(@RequestParam("target") String target, Model model){
        if (target.equals("secession")) {
            model.addAttribute("secession", target);
        }
        else if(target.equals("change")) {
            model.addAttribute("change", target);
        }
        return "../popup/currentPasswd_popup";
    }

    @GetMapping("/move/teamapplyForm.do")
    public String teamApplyForm(@RequestParam("team") String team, Model model){

        TeamDTO teamDTO = new TeamDTO();

        try{
            JSONObject jsonObject = new JSONObject(team);

            teamDTO.setTeamName((String) jsonObject.getString("teamName"));
            teamDTO.setTier_limit((String) jsonObject.getString("tier_limit"));
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("team", teamDTO);
        return "teamApplyForm";
    }

    @GetMapping("/move/teamUpdateForm.do")
    public String teamUpdateForm(@RequestParam("teamObject") String teamObject, Model model){

        HashMap<String, String> team_summonerName = new HashMap<>();
        HashMap<String, RankedSoloDTO> team_rankSoloData = new HashMap<>();

        TeamDTO teamDTO = new TeamDTO();

        String team_top = null;
        String team_middle = null;
        String team_jungle = null;
        String team_bottom = null;
        String team_suppoter = null;

        RankedSoloDTO soloData_top = new RankedSoloDTO();
        RankedSoloDTO soloData_middle = new RankedSoloDTO();
        RankedSoloDTO soloData_jungle = new RankedSoloDTO();
        RankedSoloDTO soloData_bottom = new RankedSoloDTO();
        RankedSoloDTO soloData_suppoter = new RankedSoloDTO();

        try{
            JSONObject jsonObject = new JSONObject(teamObject);

            JSONObject jsonObject_team = jsonObject.getJSONObject("team");
            teamDTO.setTeamName((String) jsonObject_team.getString("teamName"));
            teamDTO.setTeam_description((String) jsonObject_team.getString("teamDescription"));
            teamDTO.setCaptinName((String) jsonObject_team.getString("teamCaptin"));
            teamDTO.setTop((String) jsonObject_team.getString("top_nickname"));
            teamDTO.setMiddle((String) jsonObject_team.getString("middle_nickname"));
            teamDTO.setJungle((String) jsonObject_team.getString("jungle_nickname"));
            teamDTO.setBottom((String) jsonObject_team.getString("bottom_nickname"));
            teamDTO.setSuppoter((String) jsonObject_team.getString("suppoter_nickname"));

            team_top = (String) jsonObject.getString("team_top");
            team_summonerName.put("team_top", team_top);

            team_middle = (String) jsonObject.getString("team_middle");
            team_summonerName.put("team_middle", team_middle);

            team_jungle = (String) jsonObject.getString("team_jungle");
            team_summonerName.put("team_jungle", team_jungle);

            team_bottom = (String) jsonObject.getString("team_bottom");
            team_summonerName.put("team_bottom", team_bottom);

            team_suppoter = (String) jsonObject.getString("team_suppoter");
            team_summonerName.put("team_suppoter", team_suppoter);

            JSONObject jsonObject_top = jsonObject.getJSONObject("soloData_top");
            soloData_top.setTier((String) jsonObject_top.getString("soloData_top_tier"));
            soloData_top.setTier_rank((String) jsonObject_top.getString("soloData_top_tierRank"));
            if (!jsonObject_top.getString("soloData_top_rate").isEmpty()){
                soloData_top.setRate((double) jsonObject_top.getDouble("soloData_top_rate"));
            }
            team_rankSoloData.put("soloData_top", soloData_top);

            JSONObject jsonObject_middle = jsonObject.getJSONObject("soloData_middle");
            soloData_middle.setTier((String) jsonObject_middle.getString("soloData_middle_tier"));
            soloData_middle.setTier_rank((String) jsonObject_middle.getString("soloData_middle_tierRank"));
            if (!jsonObject_middle.getString("soloData_middle_rate").isEmpty()){
                soloData_middle.setRate((double) jsonObject_middle.getDouble("soloData_middle_rate"));
            }
            team_rankSoloData.put("soloData_middle", soloData_middle);

            JSONObject jsonObject_jungle = jsonObject.getJSONObject("soloData_jungle");
            soloData_jungle.setTier((String) jsonObject_jungle.getString("soloData_jungle_tier"));
            soloData_jungle.setTier_rank((String) jsonObject_jungle.getString("soloData_jungle_tierRank"));
            if (!jsonObject_jungle.getString("soloData_jungle_rate").isEmpty()){
                soloData_jungle.setRate((double) jsonObject_jungle.getDouble("soloData_jungle_rate"));
            }
            team_rankSoloData.put("soloData_jungle", soloData_jungle);

            JSONObject jsonObject_bottom = jsonObject.getJSONObject("soloData_bottom");
            soloData_bottom.setTier((String) jsonObject_bottom.getString("soloData_bottom_tier"));
            soloData_bottom.setTier_rank((String) jsonObject_bottom.getString("soloData_bottom_tierRank"));
            if (!jsonObject_bottom.getString("soloData_bottom_rate").isEmpty()){
                soloData_bottom.setRate((double) jsonObject_bottom.getDouble("soloData_bottom_rate"));
            }
            team_rankSoloData.put("soloData_bottom", soloData_bottom);

            JSONObject jsonObject_suppoter = jsonObject.getJSONObject("soloData_suppoter");
            soloData_suppoter.setTier((String) jsonObject_suppoter.getString("soloData_suppoter_tier"));
            soloData_suppoter.setTier_rank((String) jsonObject_suppoter.getString("soloData_suppoter_tierRank"));
            if (!jsonObject_suppoter.getString("soloData_suppoter_rate").isEmpty()){
                soloData_suppoter.setRate((double) jsonObject_suppoter.getDouble("soloData_suppoter_rate"));
            }
            team_rankSoloData.put("soloData_suppoter", soloData_suppoter);

        }catch (Exception e){
            e.printStackTrace();
        }

        Object[] summonerName_hashKey = team_summonerName.keySet().toArray();
        Object[] rankSoloData_hashKey = team_rankSoloData.keySet().toArray();

        for (int i = 0; i < summonerName_hashKey.length; i++){
            model.addAttribute((String) summonerName_hashKey[i], team_summonerName.get((String) summonerName_hashKey[i]));
            model.addAttribute((String) rankSoloData_hashKey[i], team_rankSoloData.get((String) rankSoloData_hashKey[i]));
        }
        model.addAttribute("team", teamDTO);
        return "teamUpdateForm";
    }
}
