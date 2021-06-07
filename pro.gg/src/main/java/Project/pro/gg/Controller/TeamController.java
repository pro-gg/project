package Project.pro.gg.Controller;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;
import Project.pro.gg.Service.MemberServiceImpl;
import Project.pro.gg.Service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeamController {

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    TeamServiceImpl teamService;

    @GetMapping("/createTeam.do")
    public String createTeam(@RequestParam("teamData") String teamData, Model model){

        HttpSession session = MemberController.session;

        TeamDTO teamDTO = new TeamDTO();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        boolean check_result = false;
        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();

        try{
            JSONObject jsonObject = new JSONObject(teamData);

            teamDTO.setTeamName((String) jsonObject.getString("teamName"));
            teamDTO.setWeek_input((String) jsonObject.getString("week_input"));
            teamDTO.setStartTime((String) jsonObject.getString("startTime"));
            teamDTO.setEndTime((String) jsonObject.getString("endTime"));
            teamDTO.setTier_limit((String) jsonObject.getString("tier_limit"));
            teamDTO.setTeam_description((String) jsonObject.getString("team_description"));

            teamDTO.setCaptinName((String) jsonObject.getString("captinName"));
            teamDTO.setTop((String) jsonObject.getString("captinName"));
            memberDTO.setUserid((String) jsonObject.getString("userid"));
            memberDTO.setTeamName(teamDTO.getTeamName());

            String[] tierArray = teamDTO.getTier_limit().split(" ");
            teamApplyDTO = teamService.selectTeamApply_Join(memberDTO.getNickname());
            check_result = teamService.tierCheck(teamApplyDTO.getTier(), teamApplyDTO.getTier_rank(), tierArray);
        }catch (Exception e){
            e.printStackTrace();
        }


        // 생성하고자 하는 팀의 티어 제한에 생성하는 회원 본인이 통과하지 못 하는 경우
        if (check_result == false){
            String memberTier = teamApplyDTO.getTier() + " " + teamApplyDTO.getTier_rank();
            model.addAttribute("memberTier", memberTier);
            return "../valid/teamNameValid";
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

    @PostMapping("/teamapply.do")
    public String teamApply(@RequestParam("teamapply") String teamapply, Model model){

        HttpSession session = MemberController.session;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();

        String tier_limit = null;
        boolean check_result = false;

        try{
            JSONObject jsonObject = new JSONObject(teamapply);

            // 조인문 으로 가져올 데이터 : 회원의 소환사 명, 솔랭 티어, 승률
            teamApplyDTO = teamService.selectTeamApply_Join(memberDTO.getNickname());
            teamApplyDTO.setTeamName((String) jsonObject.getString("teamName"));
            teamApplyDTO.setNickname(memberDTO.getNickname());
            teamApplyDTO.setLine((String) jsonObject.getString("line"));

            tier_limit = (String) jsonObject.getString("tier_limit");
            String[] tierArray = tier_limit.split(" ");
            // 신청 티어 제한 검사
            check_result = teamService.tierCheck(teamApplyDTO.getTier(), teamApplyDTO.getTier_rank(), tierArray);

        }catch (Exception e){
            e.printStackTrace();
        }

        // 티어 제한에 통과된 경우 지원하는 라인에 이미 지정된 회원이 있는지 검사
        if (check_result == true){
            String line_exist = teamService.selectLine(teamApplyDTO);

            // 지원하는 라인에 지정된 회원이 있는 경우 처리
            if(line_exist != null){
                model.addAttribute("exist_liner", line_exist);
                model.addAttribute("teamName", teamApplyDTO.getTeamName());
                return "../valid/teamApplyValid";
            }
        }else if (check_result == false){
            // 티어 제한을 통과하지 못 한 경우 처리
            model.addAttribute("tierLimit", tier_limit);
            model.addAttribute("teamName", teamApplyDTO.getTeamName());
            return "../valid/teamApplyValid";
        }
        // 티어 제한 통과 및 지원하는 라인에 지정된 회원이 없을 경우 처리 
        teamService.insertApply(teamApplyDTO);
        model.addAttribute("applySuccess", "applySuccess");
        model.addAttribute("teamName", teamApplyDTO.getTeamName());
        return "../valid/teamApplyValid";
    }

    @GetMapping("/applyStatusView.do")
    public String applyStatusView(@RequestParam("teamName") String teamName, Model model){

        List<TeamApplyDTO> applyMemberList = teamService.selectApplyMemberList(teamName);
        model.addAttribute("applyMemberList", applyMemberList);
        return "applyMemberList";
    }

    @GetMapping("/rejectapply.do")
    public String rejectapply(@RequestParam("nickname") String nickname, @RequestParam("teamName") String teamName, Model model){

        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();
        teamApplyDTO.setNickname(nickname);
        teamApplyDTO.setTeamName(teamName);
        teamService.deleteApplyMember(teamApplyDTO);
        return "redirect:/teamdetail.do?teamName="+teamName;
    }
}
