package Project.pro.gg.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import Project.pro.gg.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.RankedSoloDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {


    private final MemberService memberService;
    private final SummonerService summonerService;
    private final TeamService teamService;

    private static HttpSession session;

    @PostMapping("/createTeam.do")
    public String createTeam(@RequestParam("teamData") String teamData, Model model, HttpServletRequest request){

        session = request.getSession();

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
                // ?????? ????????? ?????? ????????? ?????? ???????????? ??????
                model.addAttribute("notExistSoloRank", "notExistSoloRank");
                return "../valid/teamNameValid";
            }

            check_result = teamService.tierCheck(teamApplyDTO.getTier(), teamApplyDTO.getTier_rank(), tierArray);
        }catch (Exception e){
            e.printStackTrace();
        }


        // ??????????????? ?????? ?????? ?????? ????????? ???????????? ?????? ????????? ???????????? ??? ?????? ??????
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
            int tiertotal = teamService.tierCalculate(teamApplyDTO.getTier(), teamApplyDTO.getTier_rank());
            teamDTO.setTier_average(tiertotal);
            teamService.insertTeamData(teamDTO, memberDTO);
            return "redirect:/move/searchTeamName.do";
        }
    }

    @GetMapping("/teamList.do")
    public String callTeamList(Model model){

        List<TeamDTO> teamDTOList = teamService.selectTeamList();
        model.addAttribute("teamList", teamDTOList);
        return "teamList";
    }

    //??? ?????? ????????????
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
        
        // ??? ????????? ??????, ?????? ?????? ?????????
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
    public String teamApply(@RequestParam("teamapply") String teamapply, Model model, HttpServletRequest request){

        session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();

        String tier_limit = null;
        boolean check_result = false;

        try{
            JSONObject jsonObject = new JSONObject(teamapply);

            // ????????? ?????? ????????? ????????? : ????????? ????????? ???, ?????? ??????, ??????
            teamApplyDTO = teamService.selectTeamApply_Join(memberDTO.getNickname());
            
            if(teamApplyDTO == null){
                // ?????? ????????? ?????? ????????? ?????? ???????????? ??????
                model.addAttribute("notExistSoloRank", "notExistSoloRank");
                model.addAttribute("teamName", (String) jsonObject.getString("teamName"));
                return "../valid/teamApplyValid";
            }
            
            // ?????? ?????? ????????? ????????? ????????? ???????????? ?????? ????????? ?????? ??????
            teamApplyDTO.setNickname(memberDTO.getNickname());
            
            if(teamService.selectOtherApply(teamApplyDTO.getNickname())){ // ?????? ?????? ????????? ????????? ?????? ??????
                model.addAttribute("otherTeamApply", "otherTeamApply");
                model.addAttribute("teamName", (String) jsonObject.getString("teamName"));
                return "../valid/teamApplyValid";
            }
            else{ // ?????? ?????? ????????? ????????? ?????? ??????
                teamApplyDTO.setTeamName((String) jsonObject.getString("teamName"));
                teamApplyDTO.setLine((String) jsonObject.getString("line"));

                tier_limit = (String) jsonObject.getString("tier_limit");
                String[] tierArray = tier_limit.split(" ");
                // ?????? ?????? ?????? ??????
                check_result = teamService.tierCheck(teamApplyDTO.getTier(), teamApplyDTO.getTier_rank(), tierArray);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // ?????? ????????? ????????? ?????? ???????????? ????????? ?????? ????????? ????????? ????????? ??????
        if (check_result){
            String line_exist = teamService.selectLine(teamApplyDTO);

            // ???????????? ????????? ????????? ????????? ?????? ?????? ??????
            if(line_exist != null){
                model.addAttribute("exist_liner", line_exist);
                model.addAttribute("teamName", teamApplyDTO.getTeamName());
                return "../valid/teamApplyValid";
            }
        }else {
            // ?????? ????????? ???????????? ??? ??? ?????? ??????
            model.addAttribute("tierLimit", tier_limit);
            model.addAttribute("teamName", teamApplyDTO.getTeamName());
            return "../valid/teamApplyValid";
        }
        // ?????? ?????? ?????? ??? ???????????? ????????? ????????? ????????? ?????? ?????? ?????? 
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

    @PostMapping("/teamapprove.do")
    public String teamApprove(@RequestParam("nickname") String nickname, Model model){
        // ?????? ???????????? ????????? ?????? ????????? ???????????? ????????? ????????? ??? ????????? ????????????, ?????? ?????? ??????????????? ???????????????.
        TeamApplyDTO teamApplyDTO = teamService.selectApplyStatus(nickname);

        // ??? ?????? ?????? ????????????
        teamService.updateTeamLine(teamApplyDTO);

        // ?????? teamName ?????? ????????????(???????????? ??????)
        MemberDTO memberDTO = memberService.findByNickname(nickname);
        memberDTO.setTeamName(teamApplyDTO.getTeamName());
        memberService.updateTeamName(memberDTO);
        
        // apply ??????????????? ?????? ?????? ??????
        teamService.deleteApplyMember(teamApplyDTO);
        String returnTeamName = null;
        try{
            // URL ??? ?????? ????????? ???????????? ????????? ?????? ????????? ?????????
            returnTeamName = URLEncoder.encode(teamApplyDTO.getTeamName(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/team/teamdetail.do?teamName="+returnTeamName+"&target=detail";
    }

    @PostMapping("/rejectapply.do")
    public String rejectapply(@RequestParam("nickname") String nickname, @RequestParam("teamName") String teamName,
                              @RequestParam("target") String target, Model model, HttpServletRequest request){

        session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();
        teamApplyDTO.setNickname(nickname);
        teamApplyDTO.setTeamName(teamName);

        teamService.deleteApplyMember(teamApplyDTO);

        if (target.equals("updateSummonerName")){
            model.addAttribute("member", memberDTO);
            System.out.println("?????? ??????");
            return "updateSummonerName";
        }

        String returnTeamName = null;
        try{
            // URL ??? ?????? ????????? ???????????? ????????? ?????? ????????? ?????????
            returnTeamName = URLEncoder.encode(teamName, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/team/teamdetail.do?teamName="+returnTeamName+"&target=detail";
    }

    @PostMapping("/captinsecession.do")
    public String captinSecession(@RequestParam("teamName") String teamName, HttpServletRequest request){
        session = request.getSession();
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

        // ???????????? ?????? ?????? - ?????? ???????????? ?????? ?????? ????????? ?????? ??? ?????? ???????????? teamName ???????????? null ??? ????????????
        for (int i = 0; i < lineList.size(); i++){
            MemberDTO memberDTO = memberService.findByNickname(lineList.get(i));

            if (memberDTO != null) {
                memberDTO.setTeamName(null);
                memberService.updateTeamName(memberDTO);
            }
        }

        // ?????? ??? ???????????? ?????? ?????? ??? ???????????? ??????
        teamService.deleteTeam(teamDTO);
        // ???????????? ?????? teamName ?????? ????????? ??????
        memberDTO_captin.setTeamName(null);
        session.setAttribute("member", memberDTO_captin);
        return "redirect:/move/searchTeamName.do";
    }

    @PostMapping("/crewsecession.do")
    public String crewSecession(@RequestParam("teamName") String teamName, @RequestParam("target") String target, Model model,
                                HttpServletRequest request){
        // ????????? ???????????? ?????? ?????? null ??? ??????
        // ?????? ?????? member ??????????????? teamName ?????? null ??? ??????
        session = request.getSession();
        MemberDTO memberDTO_crew = (MemberDTO) session.getAttribute("member");

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName(teamName);
        teamDTO = teamService.selectTeam(teamDTO);

        // ??? ????????? ???????????? 5?????? ?????? ????????? ???????????? ?????? ????????? ????????? ?????? ??????????????? ?????? TeamApplyDTO ?????? ??????
        TeamApplyDTO teamApplyDTO = new TeamApplyDTO();
        teamApplyDTO.setTeamName(teamName);
        teamApplyDTO.setNickname(null);

        // ?????? ????????? ????????? ??????????????? ???????????? ?????? ?????????
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

        // ?????? ????????? ????????? ??????????????? ?????? ?????? ??? ?????? ?????? ?????? ??? null ??? ????????????
        teamService.updateTeamLine(teamApplyDTO);
        // ?????? ??? teamName ?????? null ??? ????????????
        memberDTO_crew.setTeamName(null);
        memberService.updateTeamName(memberDTO_crew);
        session.setAttribute("member", memberDTO_crew);
        if (target.equals("updateSummonerName")){
            model.addAttribute("member", memberDTO_crew);
            return "updateSummonerName";
        }
        return "redirect:/move/searchTeamName.do";
    }

    @PostMapping("/crewexile.do")
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

    @PostMapping("/teamLineUpdate.do")
    public String teamUpdate(@RequestParam("positionJSON") String positionJSON, @RequestParam("teamName") String teamName) throws UnsupportedEncodingException {

        TeamDTO teamDTO = new TeamDTO();
        // positionJSON ??? ?????? ????????? ????????? JSON ?????????
        try{
            JSONObject jsonObject = new JSONObject(positionJSON);

            String top = jsonObject.getString("top");
            String middle = jsonObject.getString("middle");
            String jungle = jsonObject.getString("jungle");
            String bottom = jsonObject.getString("bottom");
            String suppoter = jsonObject.getString("suppoter");

            if (!top.equals(""))
                teamDTO.setTop(top);
            if (!middle.equals(""))
                teamDTO.setMiddle(middle);
            if (!jungle.equals(""))
                teamDTO.setJungle(jungle);
            if (!bottom.equals(""))
                teamDTO.setBottom(bottom);
            if (!suppoter.equals(""))
                teamDTO.setSuppoter(suppoter);

            teamDTO.setTeamName(teamName);

            teamService.updateTeam(teamDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "redirect:/team/teamdetail.do?teamName="+URLEncoder.encode(teamName, "UTF-8")+"&target=detail";
    }

    @GetMapping("/matchList.do")
    public String matchList(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	MemberDTO member = (MemberDTO)session.getAttribute("member");
		TeamDTO teamDTO = new TeamDTO();
	    teamDTO.setTeamName(member.getTeamName());
	    TeamDTO team = teamService.selectTeam(teamDTO);
	    HashMap<String,Integer> idx = new HashMap<String, Integer>();
	    List<TeamDTO> teamDTOList = null;
	    if(team.getTop() != null || team.getMiddle() != null || team.getJungle() != null || team.getBottom() != null || team.getSuppoter() != null) {
	    	if(team.getTier_average() > 0 && team.getTier_average()<=4) {
	    		idx.put("startIdx", 1);
	    		idx.put("endIdx", 8);
	    	}else if(team.getTier_average() > 4 && team.getTier_average()<=8) {
	    		idx.put("startIdx", 1);
	    		idx.put("endIdx", 12);
	    	}else if(team.getTier_average() > 8 && team.getTier_average()<=12) {
	    		idx.put("startIdx", 5);
	    		idx.put("endIdx", 16);
	    	}else if(team.getTier_average() > 12 && team.getTier_average()<=16) {
	    		idx.put("startIdx", 9);
	    		idx.put("endIdx", 20);
	    	}else if(team.getTier_average() > 16 && team.getTier_average()<=20) {
	    		idx.put("startIdx", 13);
	    		idx.put("endIdx", 24);
	    	}else if(team.getTier_average() > 21 && team.getTier_average()<=27) {
	    		idx.put("startIdx", 17);
	    		idx.put("endIdx", 27);
	    	}
	    	teamDTOList = teamService.selectMatchList(idx);
	    }
	    System.out.println(teamDTOList);
        model.addAttribute("teamList", teamDTOList);
    	return "matchList";
    }

    @GetMapping("/searchTeam.do")
    public String searchTeamName(@RequestParam("searchData") String searchData, Model model){

        String teamName = null;
        String tier_limit = null;
        String empty_line = null;

        try{
            JSONObject jsonObject = new JSONObject(searchData);

            teamName = jsonObject.getString("teamName");
            tier_limit = jsonObject.getString("tier_limit");
            empty_line = jsonObject.getString("empty_line");

        }catch (Exception e){
            e.printStackTrace();
        }

        TeamDTO teamDTO = new TeamDTO();

        if (!teamName.equals("")) {
            teamDTO.setTeamName(teamName);
        }
        if (!tier_limit.equals("")) {
            teamDTO.setTier_limit(tier_limit);
        }
        if (!empty_line.equals("")) {
            teamDTO.setLine(empty_line);
        }

        List<TeamDTO> teamDTOList = new ArrayList<>();

        // ???????????? ???????????? ????????? ?????? ?????? ????????? ?????? ????????? ?????? ?????? ?????????
        if (teamDTO.getTeamName() != null || teamDTO.getTier_limit() != null || teamDTO.getLine() != null){
            teamDTOList = teamService.selectDynamicSearch(teamDTO);
        }

        if (teamDTOList.size() == 0){
            model.addAttribute("notexistTeam", "notexistTeam");
            return "searchTeam";
        }
        model.addAttribute("notexistTeam", null);
        model.addAttribute("teamDTOList", teamDTOList);
        return "searchTeam";
    }

    @GetMapping("/searchCrew.do")
    public String searchCrew(@RequestParam("searchData") String searchData, Model model){

        String tier = null;
        String rateRank = null;
        double rate;

        try{
            JSONObject jsonObject = new JSONObject(searchData);
            tier = jsonObject.getString("tier");
            rateRank = jsonObject.getString("rate");
        }catch (Exception e){
            e.printStackTrace();
        }

        RankedSoloDTO rankedSoloDTO = new RankedSoloDTO();
        // ????????? ????????? ?????? ???????????? ?????? ????????? ?????? ?????????.
        if (!tier.equals("")){
            String [] tier_array = tier.split(" ");
            rankedSoloDTO.setTier(tier_array[0]);
            rankedSoloDTO.setTier_rank(tier_array[1]);
        }

        if (!rateRank.equals("")){
            // rateRank ?????? ?????? ?????? ?????? ?????? ??????(?????? ??????)
            // RankedSoloDTO ??? ?????????????????? ????????? ???????????? ?????? ?????? ?????? ????????? ?????? ????????? ?????? ??? ???
            rankedSoloDTO.setRateRank(rateRank);
        }

        // ?????? ??????????????? ????????? ??????, ?????? ???????????? ?????? ?????? ?????????, ????????? ?????? ?????? ???????????? ?????? ??????.
        // ????????? ?????? ????????? MemberDTO ??? ???????????? ??????.(?????????????????? ?????? ???????????? ??????)
        List<RankedSoloDTO> rankedSoloDTOList = new ArrayList<>();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        if ((rankedSoloDTO.getTier() != null && rankedSoloDTO.getTier_rank() != null) || rankedSoloDTO.getRateRank() != null){
            // ?????? ?????????
            // ?????? ????????? ????????? ?????? ?????? ????????? ????????????.(???????????? RankedSoloDTO ?????? ????????? ?????????)
            // ?????? ??????????????? ????????? id ?????? ???????????? ?????? ?????? ????????? ???????????? ????????????.(SummonerDTO) - ????????? ??? ????????? ????????? ??? ??????
            // ????????? ?????? ????????? userid ????????? ?????? ?????? ???????????? ?????? ??? ?????? ????????? ????????? ????????????.
            // ????????? ??????????????? selectList ???????????? ???????????? ????????? RankedSoloDTO ????????? ????????? ?????? RankedSoloDTO ?????? ???????????? ????????? ????????? ????????? ?????? ?????????
            // ??? ?????? ????????? ????????? ?????? ?????? ????????? ???????????? ????????????

            rankedSoloDTOList = teamService.selectDynamicSearch_Crew(rankedSoloDTO);

            try{
                JSONObject jsonObject = null;
                for (int i = 0; i < rankedSoloDTOList.size(); i++){
                    MemberDTO memberDTO = new MemberDTO();

                    jsonObject = new JSONObject((Map) rankedSoloDTOList.get(i));
                    rankedSoloDTO.setId(jsonObject.getString("id"));
                    rankedSoloDTO.setTier(jsonObject.getString("tier"));
                    rankedSoloDTO.setTier_rank(jsonObject.getString("tier_rank"));
                    rankedSoloDTO.setRate(jsonObject.getDouble("rate"));

                    memberDTO.setTier(rankedSoloDTO.getTier()+" "+rankedSoloDTO.getTier_rank());
                    memberDTO.setRate(rankedSoloDTO.getRate());

                    SummonerDTO summonerDTO = summonerService.findByid(rankedSoloDTO.getId());
                    memberDTO.setSummoner_name(summonerDTO.getSummoner_name());
                    memberDTO.setUserid(summonerDTO.getUserid());

                    MemberDTO findNickName = memberService.selectMemberOne(memberDTO.getUserid());
                    memberDTO.setNickname(findNickName.getNickname());

                    memberDTOList.add(memberDTO);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (rankedSoloDTOList.size() == 0){
            model.addAttribute("notexistCrew", "notexistCrew");
            return "crewSearchList";
        }

        model.addAttribute("notexistCrew", null);
        model.addAttribute("memberDTOList", memberDTOList);
        return "crewSearchList";
    }
}
