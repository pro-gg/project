package Project.pro.gg.Controller;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.TeamDTO;
import Project.pro.gg.Service.TeamServiceImpl;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.List;

@Controller
public class TeamController {

    @Autowired
    TeamServiceImpl teamService;

    @GetMapping("/createTeam.do")
    public String createTeam(@RequestParam("teamData") String teamData, Model model){

        TeamDTO teamDTO = new TeamDTO();
        MemberDTO memberDTO = new MemberDTO();
        try{
            JSONObject jsonObject = new JSONObject(teamData);

            teamDTO.setTeamName((String) jsonObject.getString("teamName"));
            teamDTO.setWeek_input((String) jsonObject.getString("week_input"));
            teamDTO.setStartTime((String) jsonObject.getString("startTime"));
            teamDTO.setEndTime((String) jsonObject.getString("endTime"));
            teamDTO.setTier_limit((String) jsonObject.getString("tier_limit"));
            teamDTO.setTeam_description((String) jsonObject.getString("team_description"));

            teamDTO.setCaptinName((String) jsonObject.getString("captinName"));
            memberDTO.setUserid((String) jsonObject.getString("userid"));
            memberDTO.setTeamName(teamDTO.getTeamName());
        }catch (Exception e){
            e.printStackTrace();
        }

        TeamDTO teamDTO_exist = teamService.selectTeam(teamDTO);

        if (teamDTO_exist != null){
            model.addAttribute("teamname_exist", teamDTO_exist);
            return "../valid/teamNameValid";
        } else {
            teamService.insertTeamData(teamDTO, memberDTO);
            return "redirect:/move/teammatch.do";
        }
    }

    @GetMapping("/teamList.do")
    public String callTeamList(Model model){

        List<TeamDTO> teamDTOList = teamService.selectTeamList();
        model.addAttribute("teamList", teamDTOList);
        return "teamList";
    }

    //팀 정보 불러오기
    @GetMapping("/teamdetail.do")
    public String teamDetail(@RequestParam("teamName") String teamName, Model model) {

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName(teamName);
        teamDTO = teamService.selectTeam(teamDTO);

        model.addAttribute("team", teamDTO);
        return "teamDetail";
    }
}
