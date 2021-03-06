package Project.pro.gg.Controller;

import Project.pro.gg.Model.TeamDTO;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        if(target.equals("change")) {
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

    @GetMapping("/move/searchTeamName.do")
    public String moveSearchTeamName(){
        return "searchTeam";
    }

    @GetMapping("/move/searchCrew.do")
    public String moveSearchCrew(){
        return "searchCrew";
    }
    
    @GetMapping("/move/myMatching.do")
    public String myMatching() {
    	return "myMatching";
    }

    @GetMapping("/move/board.do")
    public String moveBoard(){
        return "../board/freeboard"; // 기본링크 - 자유게시판
    }

    @PostMapping("/move/boardpost.do")
    public String boardpost(@RequestParam("boardNumber") int boardNumber, Model model){
        // 게시판 글 작성 페이지로 이동
        model.addAttribute("boardNumber", boardNumber);
        return "../board/writepost";
    }
}
