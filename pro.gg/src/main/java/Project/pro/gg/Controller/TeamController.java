package Project.pro.gg.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.RankedSoloDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;
import Project.pro.gg.Service.MemberServiceImpl;
import Project.pro.gg.Service.SummonerServiceImpl;
import Project.pro.gg.Service.TeamServiceImpl;

@Controller
public class TeamController {
    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    SummonerServiceImpl summonerService;

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

            if(teamApplyDTO == null){
                // 솔랭 기록이 없는 회원의 경우 처리하는 로직
                model.addAttribute("notExistSoloRank", "notExistSoloRank");
                return "../valid/teamNameValid";
            }

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
    public String teamDetail(@RequestParam("teamName") String teamName, @RequestParam("target") String target, Model model) {

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName(teamName);
        teamDTO = teamService.selectTeam(teamDTO);

        MemberDTO memberDTO_top = null;
        MemberDTO memberDTO_middle = null;
        MemberDTO memberDTO_jungle = null;
        MemberDTO memberDTO_bottom = null;
        MemberDTO memberDTO_suppoter = null;
        
        int cnt = 0;
        int tiertotal = 0;
        
        // 팀 디테일 티어, 승률 정보 보내기
        if (teamDTO.getTop() != null){
            memberDTO_top = memberService.findByNickname(teamDTO.getTop());
            SummonerDTO summonerDTO_top = summonerService.findByUserid(memberDTO_top.getUserid());
            RankedSoloDTO rankedSoloDTO_top = summonerService.selectRankedSoloData(summonerDTO_top.getId());
            
            cnt++;
            tiertotal += teamService.tierCalculate(rankedSoloDTO_top.getTier(), rankedSoloDTO_top.getTier_rank());
            
            model.addAttribute("team_top", summonerDTO_top.getSummoner_name());
            model.addAttribute("soloData_top", rankedSoloDTO_top);
        }
        if (teamDTO.getMiddle() != null){
            memberDTO_middle = memberService.findByNickname(teamDTO.getMiddle());
            SummonerDTO summonerDTO_middle = summonerService.findByUserid(memberDTO_middle.getUserid());
            RankedSoloDTO rankedSoloDTO_middle = summonerService.selectRankedSoloData(summonerDTO_middle.getId());
            
            cnt++;
            tiertotal += teamService.tierCalculate(rankedSoloDTO_middle.getTier(), rankedSoloDTO_middle.getTier_rank());

            model.addAttribute("team_middle", summonerDTO_middle.getSummoner_name());
            model.addAttribute("soloData_middle", rankedSoloDTO_middle);
        }
        if (teamDTO.getJungle() != null){
            memberDTO_jungle = memberService.findByNickname(teamDTO.getJungle());
            SummonerDTO summonerDTO_jungle = summonerService.findByUserid(memberDTO_jungle.getUserid());
            RankedSoloDTO rankedSoloDTO_jungle = summonerService.selectRankedSoloData(summonerDTO_jungle.getId());
            
            cnt++;
            tiertotal += teamService.tierCalculate(rankedSoloDTO_jungle.getTier(), rankedSoloDTO_jungle.getTier_rank());

            model.addAttribute("team_jungle", summonerDTO_jungle.getSummoner_name());
            model.addAttribute("soloData_jungle", rankedSoloDTO_jungle);
        }
        if (teamDTO.getBottom() != null){
            memberDTO_bottom = memberService.findByNickname(teamDTO.getBottom());
            SummonerDTO summonerDTO_bottom = summonerService.findByUserid(memberDTO_bottom.getUserid());
            RankedSoloDTO rankedSoloDTO_bottom = summonerService.selectRankedSoloData(summonerDTO_bottom.getId());
            
            cnt++;
            tiertotal += teamService.tierCalculate(rankedSoloDTO_bottom.getTier(), rankedSoloDTO_bottom.getTier_rank());
            
            model.addAttribute("team_bottom", summonerDTO_bottom.getSummoner_name());
            model.addAttribute("soloData_bottom", rankedSoloDTO_bottom);
        }
        if (teamDTO.getSuppoter() != null){
            memberDTO_suppoter = memberService.findByNickname(teamDTO.getSuppoter());
            SummonerDTO summonerDTO_suppoter = summonerService.findByUserid(memberDTO_suppoter.getUserid());
            RankedSoloDTO rankedSoloDTO_suppoter = summonerService.selectRankedSoloData(summonerDTO_suppoter.getId());
            
            cnt++;
            tiertotal += teamService.tierCalculate(rankedSoloDTO_suppoter.getTier(), rankedSoloDTO_suppoter.getTier_rank());

            model.addAttribute("team_suppoter", summonerDTO_suppoter.getSummoner_name());
            model.addAttribute("soloData_suppoter", rankedSoloDTO_suppoter);
        }
        teamDTO.setTier_average(Math.round(tiertotal/cnt));
        teamService.updateTierAvg(teamDTO);
        String tier = teamService.getTier(teamDTO.getTier_average());
        model.addAttribute("team", teamDTO);
        model.addAttribute("tier", tier);
        
       
        
        if (target.equals("update")){
            return "teamUpdateForm";
        }
        if (target.equals("overlap")){
            model.addAttribute("overlap", "overlap");
        }else{
            model.addAttribute("overlap", null);
        }
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
            
            if(teamApplyDTO == null){
                // 솔랭 기록이 없는 회원의 경우 처리하는 로직
                model.addAttribute("notExistSoloRank", "notExistSoloRank");
                model.addAttribute("teamName", (String) jsonObject.getString("teamName"));
                return "../valid/teamApplyValid";
            }
            
            // 다른 팀에 지원한 사실이 있는지 확인하기 위해 닉네임 우선 세팅
            teamApplyDTO.setNickname(memberDTO.getNickname());
            
            if(teamService.selectOtherApply(teamApplyDTO.getNickname())){ // 다른 팀에 지원한 사실이 있는 경우
                model.addAttribute("otherTeamApply", "otherTeamApply");
                model.addAttribute("teamName", (String) jsonObject.getString("teamName"));
                return "../valid/teamApplyValid";
            }
            else{ // 다른 팀에 지원한 사실이 없는 경우
                teamApplyDTO.setTeamName((String) jsonObject.getString("teamName"));
                teamApplyDTO.setLine((String) jsonObject.getString("line"));

                tier_limit = (String) jsonObject.getString("tier_limit");
                String[] tierArray = tier_limit.split(" ");
                // 신청 티어 제한 검사
                check_result = teamService.tierCheck(teamApplyDTO.getTier(), teamApplyDTO.getTier_rank(), tierArray);
            }
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

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName(teamName);
        TeamDTO team = teamService.selectTeam(teamDTO);

        model.addAttribute("applyMemberList", applyMemberList);
        model.addAttribute("team", team);
        return "applyMemberList";
    }

    @GetMapping("/teamapprove.do")
    public String teamApprove(@RequestParam("nickname") String nickname, Model model){
        // 신청 상태정보 검색을 통해 신청을 수락받은 회원이 신청한 팀 이름을 추출하고, 이를 통해 연관관계를 매핑해준다.
        TeamApplyDTO teamApplyDTO = teamService.selectApplyStatus(nickname);

        // 팀 라인 정보 업데이트
        teamService.updateTeamLine(teamApplyDTO);

        // 회원 teamName 필드 업데이트(연관관계 매핑)
        MemberDTO memberDTO = memberService.findByNickname(nickname);
        memberDTO.setTeamName(teamApplyDTO.getTeamName());
        memberService.updateTeamName(memberDTO);
        
        // apply 테이블에서 지원 내역 삭제
        teamService.deleteApplyMember(teamApplyDTO);
        String returnTeamName = null;
        try{
            // URL 상 한글 인코딩 깨짐현상 방지를 위한 퍼센트 인코딩
            returnTeamName = URLEncoder.encode(teamApplyDTO.getTeamName(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/teamdetail.do?teamName="+returnTeamName+"&target=detail";
    }

    @GetMapping("/rejectapply.do")
    public String rejectapply(@RequestParam("nickname") String nickname, @RequestParam("teamName") String teamName,
                              @RequestParam("target") String target, Model model){

        HttpSession session = MemberController.session;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();
        teamApplyDTO.setNickname(nickname);
        teamApplyDTO.setTeamName(teamName);

        teamService.deleteApplyMember(teamApplyDTO);

        if (target.equals("updateSummonerName")){
            model.addAttribute("member", memberDTO);
            System.out.println("신청 거부");
            return "updateSummonerName";
        }

        String returnTeamName = null;
        try{
            // URL 상 한글 인코딩 깨짐현상 방지를 위한 퍼센트 인코딩
            returnTeamName = URLEncoder.encode(teamName, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/teamdetail.do?teamName="+returnTeamName+"&target=detail";
    }

    @GetMapping("/captinsecession.do")
    public String captinSecession(@RequestParam("teamName") String teamName){
        HttpSession session = MemberController.session;
        MemberDTO memberDTO_captin = (MemberDTO) session.getAttribute("member");

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName(teamName);
        teamDTO = teamService.selectTeam(teamDTO);

        List<String> lineList = new ArrayList<>();
        if (teamDTO.getTop() != null){
            lineList.add(teamDTO.getTop());
        }
        if (teamDTO.getMiddle() != null){
            lineList.add(teamDTO.getMiddle());
        }
        if (teamDTO.getJungle() != null){
            lineList.add(teamDTO.getJungle());
        }
        if (teamDTO.getBottom() != null){
            lineList.add(teamDTO.getBottom());
        }
        if (teamDTO.getSuppoter() != null){
            lineList.add(teamDTO.getSuppoter());
        }

        // 연관관계 매핑 해제 - 회원 닉네임을 통해 회원 데이터 검색 후 해당 데이터의 teamName 필드값을 null 로 업데이트
        for (int i = 0; i < lineList.size(); i++){
            MemberDTO memberDTO = memberService.findByNickname(lineList.get(i));

            if (memberDTO != null) {
                memberDTO.setTeamName(null);
                memberService.updateTeamName(memberDTO);
            }
        }

        // 회원 측 연관관계 해제 이후 팀 삭제기능 동작
        teamService.deleteTeam(teamDTO);
        // 세션에서 또한 teamName 필드 데이터 삭제
        memberDTO_captin.setTeamName(null);
        session.setAttribute("member", memberDTO_captin);
        return "redirect:/move/teammatch.do";
    }

    @GetMapping("/crewsecession.do")
    public String crewSecession(@RequestParam("teamName") String teamName, @RequestParam("target") String target, Model model){
        // 팀에서 탈퇴하는 팀원 라인 null 값 처리
        // 팀원 개인 member 데이터에서 teamName 필드 null 값 처리
        HttpSession session = MemberController.session;
        MemberDTO memberDTO_crew = (MemberDTO) session.getAttribute("member");

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName(teamName);
        teamDTO = teamService.selectTeam(teamDTO);

        // 단 하나의 메소드로 5가지 라인 각각에 지정되어 있는 팀원의 닉네임 값을 삭제해주기 위한 TeamApplyDTO 객체 사용
        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();
        teamApplyDTO.setTeamName(teamName);
        teamApplyDTO.setNickname(null);

        // 어떤 라인의 회원이 탈퇴하는지 확인하기 위한 조건문
        if (memberDTO_crew.getNickname().equals(teamDTO.getTop())){
          teamApplyDTO.setLine("top");
        } else if (memberDTO_crew.getNickname().equals(teamDTO.getMiddle())){
            teamApplyDTO.setLine("middle");
        } else if (memberDTO_crew.getNickname().equals(teamDTO.getJungle())){
            teamApplyDTO.setLine("jungle");
        } else if (memberDTO_crew.getNickname().equals(teamDTO.getBottom())){
            teamApplyDTO.setLine("bottom");
        } else if (memberDTO_crew.getNickname().equals(teamDTO.getSuppoter())){
            teamApplyDTO.setLine("suppoter");
        }

        // 어떤 라인의 회원이 탈퇴하는지 판별 완료 후 해당 라인 필드 값 null 로 업데이트
        teamService.updateTeamLine(teamApplyDTO);

        // 회원 측 teamName 필드 null 로 업데이트
        memberDTO_crew.setTeamName(null);
        memberService.updateTeamName(memberDTO_crew);
        session.setAttribute("member", memberDTO_crew);
        if (target.equals("updateSummonerName")){
            model.addAttribute("member", memberDTO_crew);
            return "updateSummonerName";
        }
        return "redirect:/move/teammatch.do";
    }

    @GetMapping("/crewexile.do")
    public String crewExile(@RequestParam("exile") String exile){

        TeamDTO teamDTO = new TeamDTO();
        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();

        String nickname = null;

        try{
            JSONObject jsonObject = new JSONObject(exile);
            teamDTO.setTeamName((String) jsonObject.getString("teamName"));
            teamDTO = teamService.selectTeam(teamDTO);

            nickname = (String) jsonObject.getString("nickname");

            teamApplyDTO.setTeamName(teamDTO.getTeamName());
            teamApplyDTO.setNickname(null);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(nickname.equals(teamDTO.getTop())){
            teamApplyDTO.setLine("top");
        } else if (nickname.equals(teamDTO.getMiddle())){
            teamApplyDTO.setLine("middle");
        } else if (nickname.equals(teamDTO.getJungle())){
            teamApplyDTO.setLine("jungle");
        } else if (nickname.equals(teamDTO.getBottom())){
            teamApplyDTO.setLine("bottom");
        } else if (nickname.equals(teamDTO.getSuppoter())){
            teamApplyDTO.setLine("suppoter");
        }

        teamService.updateTeamLine(teamApplyDTO);

        MemberDTO memberDTO = memberService.findByNickname(nickname);
        memberDTO.setTeamName(null);
        memberService.updateTeamName(memberDTO);
        return "reload";
    }

    @GetMapping("/teamLineUpdate.do")
    public String teamUpdate(@RequestParam("positionArray") String [] positionArray, @RequestParam("teamName") String teamName) throws UnsupportedEncodingException {

        if (Arrays.stream(positionArray).distinct().count() != positionArray.length)
            return "redirect:/teamdetail.do?teamName="+URLEncoder.encode(teamName, "UTF-8")+"&target=overlap";

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName(teamName);
        teamDTO = teamService.selectTeam(teamDTO);

        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();
        Queue<String> positionQueue = new LinkedList<>(); // 포지션 현황 큐
        List<String> nullCheckList = new ArrayList<>(); // 비어있는 포지션 리스트

        if (teamDTO.getTop() == null) nullCheckList.add("top");
        if (teamDTO.getMiddle() == null) nullCheckList.add("middle");
        if (teamDTO.getJungle() == null) nullCheckList.add("jungle");
        if (teamDTO.getBottom() == null) nullCheckList.add("bottom");
        if (teamDTO.getSuppoter() == null) nullCheckList.add("suppoter");

        for (int i = 0; i < positionArray.length; i++)
            positionQueue.add(positionArray[i]);

        if (teamDTO.getTop() != null){
            if (!positionQueue.peek().equals("top")){
                teamApplyDTO.setNickname(teamDTO.getTop());
                teamApplyDTO.setLine(positionQueue.poll());
                teamApplyDTO.setTeamName(teamName);
                teamService.updateTeamLine(teamApplyDTO);

                if (nullCheckList.contains(teamApplyDTO.getLine())){
                    teamApplyDTO.setNickname(null);
                    teamApplyDTO.setLine("top");
                    teamService.updateTeamLine(teamApplyDTO);
                }
            }else {
                positionQueue.poll();
            }
        }else {
            positionQueue.poll();
        }

        if (teamDTO.getMiddle() != null){
            if (!positionQueue.peek().equals("middle")){
                teamApplyDTO.setNickname(teamDTO.getMiddle());
                teamApplyDTO.setLine(positionQueue.poll());
                teamApplyDTO.setTeamName(teamName);
                teamService.updateTeamLine(teamApplyDTO);

                if (nullCheckList.contains(teamApplyDTO.getLine())){
                    teamApplyDTO.setNickname(null);
                    teamApplyDTO.setLine("middle");
                    teamService.updateTeamLine(teamApplyDTO);
                }
            }else {
                positionQueue.poll();
            }
        }else {
            positionQueue.poll();
        }

        if (teamDTO.getJungle() != null){
            if (!positionQueue.peek().equals("jungle")){
                teamApplyDTO.setNickname(teamDTO.getJungle());
                teamApplyDTO.setLine(positionQueue.poll());
                teamApplyDTO.setTeamName(teamName);
                teamService.updateTeamLine(teamApplyDTO);

                if (nullCheckList.contains(teamApplyDTO.getLine())){
                    teamApplyDTO.setNickname(null);
                    teamApplyDTO.setLine("jungle");
                    teamService.updateTeamLine(teamApplyDTO);
                }
            }else {
                positionQueue.poll();
            }
        }else {
            positionQueue.poll();
        }

        if (teamDTO.getBottom() != null){
            if (!positionQueue.peek().equals("bottom")){
                teamApplyDTO.setNickname(teamDTO.getBottom());
                teamApplyDTO.setLine(positionQueue.poll());
                teamApplyDTO.setTeamName(teamName);
                teamService.updateTeamLine(teamApplyDTO);

                if (nullCheckList.contains(teamApplyDTO.getLine())){
                    teamApplyDTO.setNickname(null);
                    teamApplyDTO.setLine("bottom");
                    teamService.updateTeamLine(teamApplyDTO);
                }
            }else {
                positionQueue.poll();
            }
        }else {
            positionQueue.poll();
        }

        if (teamDTO.getSuppoter() != null){
            if (!positionQueue.peek().equals("suppoter")){
                teamApplyDTO.setNickname(teamDTO.getSuppoter());
                teamApplyDTO.setLine(positionQueue.poll());
                teamApplyDTO.setTeamName(teamName);
                teamService.updateTeamLine(teamApplyDTO);

                if (nullCheckList.contains(teamApplyDTO.getLine())){
                    teamApplyDTO.setNickname(null);
                    teamApplyDTO.setLine("suppoter");
                    teamService.updateTeamLine(teamApplyDTO);
                }
            }else {
                positionQueue.poll();
            }
        }else {
            positionQueue.poll();
        }

        return "redirect:/teamdetail.do?teamName="+URLEncoder.encode(teamName, "UTF-8")+"&target=detail";
    }
    
    @GetMapping("/matchList.do")
    public String matchList(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	MemberDTO member = (MemberDTO)session.getAttribute("member");
		TeamDTO teamDTO = new TeamDTO();
	    teamDTO.setTeamName(member.getTeamName());
	    TeamDTO team = teamService.selectTeam(teamDTO);
	    int startIdx = 0;
	    int endIdx = 0;
	    List<TeamDTO> teamDTOList = null;
	    if(team.getTop() != null || team.getMiddle() != null || team.getJungle() != null || team.getBottom() != null || team.getSuppoter() != null) {
	    	if(team.getTier_average() > 0 && team.getTier_average()<=4) {
	    		startIdx = 1;
	    		endIdx = 8;
	    	}else if(team.getTier_average() > 4 && team.getTier_average()<=8) {
	    		startIdx = 1;
	    		endIdx = 12;
	    	}else if(team.getTier_average() > 8 && team.getTier_average()<=12) {
	    		startIdx = 5;
	    		endIdx = 16;
	    	}else if(team.getTier_average() > 12 && team.getTier_average()<=16) {
	    		startIdx = 9;
	    		endIdx = 20;
	    	}else if(team.getTier_average() > 16 && team.getTier_average()<=20) {
	    		startIdx = 13;
	    		endIdx = 24;
	    	}else if(team.getTier_average() > 21 && team.getTier_average()<=27) {
	    		startIdx = 17;
	    		endIdx = 27;
	    	}
	    	teamDTOList = teamService.selectMatchList(startIdx, endIdx);
	    	System.out.println(teamDTOList);
	    }
        model.addAttribute("teamList", teamDTOList);
    	return "matchList";
    }
}
