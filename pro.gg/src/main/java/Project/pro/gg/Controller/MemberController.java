package Project.pro.gg.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Project.pro.gg.Model.*;
import Project.pro.gg.Service.SummonerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Project.pro.gg.Service.MemberServiceImpl;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    SummonerServiceImpl summonerService;

    public static HttpSession session;

    @PostMapping("/check_id.do")
    public void check_id(HttpServletRequest request, HttpServletResponse response) {
    	String userid = request.getParameter("id");
    	MemberDTO memberDTO = memberService.selectMemberOne(userid);
    	if(memberDTO == null) {
    		try {
				response.getWriter().write("OK");
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else {
    		try {
				response.getWriter().write("Fail");
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

    @PostMapping("/check_nickname.do")
    public void check_nickname(@RequestParam("nickname") String nickname, HttpServletResponse response){

        MemberDTO memberDTO = memberService.findByNickname(nickname);

        if (memberDTO == null){
            try {
                response.getWriter().write("OK");
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().write("Fail");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/tryregister.do")
    public String tryRegister(@RequestParam(value = "id") String userid,
                              @RequestParam(value = "passwd") String passwd,
                              @RequestParam(value = "email") String email,
                              @RequestParam(value = "nickname") String nickname,
                              @RequestParam(value = "name") String name){

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserid(userid);
        memberDTO.setPasswd(passwd);
        memberDTO.setEmail(email);
        memberDTO.setNickname(nickname);
        memberDTO.setName(name);

        memberService.insert(memberDTO);

        return "main";
    }

    // 보안 상 민감한 정보를 url 상에서 가리기 위해 post mapping
    @PostMapping("/trylogin.do")
    public String tryLogin(@RequestParam("id") String userid, @RequestParam("passwd") String passwd,
                           Model model, HttpServletRequest request){

    	AdminDTO adminDTO = new AdminDTO();
        session = request.getSession();
        String result="";
        
        if(userid.equals("root")) {
        	if(passwd.equals("1234")) {
        		result="Success";
        		model.addAttribute("result", result);
        		adminDTO.setAdminId("root") ;
                adminDTO.setAdminPasswd("1234");
                session.setAttribute("admin", adminDTO);
                model.addAttribute("result",result);
                model.addAttribute("admin", session.getAttribute("admin"));
        		return "../valid/adminloginvalid";
        	}else {
        		session.setAttribute("admin", null);
        		model.addAttribute("result",result);
                model.addAttribute("admin", session.getAttribute("admin"));
        		return "../valid/adminloginvalid";
        	}
        }else {
        	 result = memberService.selectOne(userid, passwd);
             if(result == "Success"){
                 MemberDTO memberDTO = memberService.selectMemberOne(userid);
                 memberDTO.setSummoner_name(memberService.selectInnerJoinsummoner_name(memberDTO.getUserid()));
                 session.setAttribute("member", memberDTO);
             } else{
                 session.setAttribute("member", null);
             }
        }
        model.addAttribute("result", result);

        return "../valid/loginvalid";
    }

    @GetMapping("/logout.do")
    public String logout(Model model){
        try{
            session.removeAttribute("member");
            session.removeAttribute("admin");
        }catch (NullPointerException e){
            // 세션이 만료된 상태일 때 로그아웃 기능이 동작한 경우 수행
        }
        
        return "main";
    }

    @GetMapping("/findId.do")
    public String findId(@RequestParam("findId") String findId, Model model){
        MemberDTO memberDTO = new MemberDTO();

        try {
            JSONObject jsonObject = new JSONObject(findId);
            String name = (String)jsonObject.get("name");
            String email = (String)jsonObject.get("email");

            memberDTO.setName(name);
            memberDTO.setEmail(email);

            memberDTO = memberService.findId(memberDTO);
            if (memberDTO.getUserid() != null) {
                model.addAttribute("memberId", memberDTO.getUserid());
            }

        } catch (Exception e) {
            model.addAttribute("memberId", null);
        }
        return "../valid/findIdValid";
    }

    @PostMapping("findIdSuccess.do")
    public String findIdSuccess(@RequestParam("memberId") String memberId, Model model){

        String findMemberId = "";
        try {
            JSONObject jsonObject = new JSONObject(memberId);
            findMemberId = (String) jsonObject.get("memberId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("memberId", findMemberId);
        return "../popup/findIdSuccess_popup";
    }

    @GetMapping("/findPasswd.do")
    public String findPasswd(@RequestParam("findPasswd") String findPasswd, Model model){
        MemberDTO memberDTO = new MemberDTO();

        try {
            JSONObject jsonObject = new JSONObject(findPasswd);
            String userid = (String) jsonObject.get("userid");
            String name = (String) jsonObject.get("name");
            String email = (String) jsonObject.get("email");

            memberDTO.setUserid(userid);
            memberDTO.setName(name);
            memberDTO.setEmail(email);

            memberDTO = memberService.findPasswd(memberDTO);
            if (memberDTO != null){
                model.addAttribute("memberDTO", memberDTO);
            }
        }catch (Exception e){
            model.addAttribute("memberDTO", null);
        }

        return "../valid/findPasswdValid";
    }

    @PostMapping("/findPasswdSuccess.do")
    public String findPasswdSuccess(@RequestParam("userid") String userid, Model model){
        MemberDTO memberDTO = new MemberDTO();
        try {
            JSONObject jsonObject = new JSONObject(userid);
            memberDTO.setUserid((String) jsonObject.get("userid"));
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("member_userid", memberDTO.getUserid());
        return "../popup/updateMemberPasswd_popup";
    }

    @PostMapping("/updatePasswd.do")
    public String updatePasswd(@RequestParam("updatePasswd") String updatePasswd){
        MemberDTO memberDTO = new MemberDTO();
        try {
            JSONObject jsonObject = new JSONObject(updatePasswd);
            updatePasswd = (String) jsonObject.get("updatePasswd");
            String userid = (String) jsonObject.get("userid");

            memberDTO.setUserid(userid);
            memberDTO.setPasswd(updatePasswd);
            memberService.updatePasswd(memberDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((MemberDTO)session.getAttribute("member") != null) session.removeAttribute("member");
        return "../popup/updateMemberPasswd_popup";
    }

    @PostMapping("/updateMemberData.do")
    public String updateMemberData(@RequestParam("updateMember") String updateMember){
        try {
            JSONObject jsonObject = new JSONObject(updateMember);
            MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

            memberDTO.setName((String) jsonObject.getString("name"));
            memberDTO.setNickname((String) jsonObject.getString("nickname"));
            memberDTO.setEmail((String) jsonObject.getString("email"));

            memberService.updateMemberData(memberDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "mypage";
    }

    @PostMapping("/memberSecession.do")
    public String memberSecession(){

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        memberService.deleteMember(memberDTO);
        session.removeAttribute("member");
        return "../popup/currentPasswd_popup";
    }

    @GetMapping("/findmemberdata.do")
    public String findMemberData(@RequestParam("nickname") String nickname, Model model){

        // 회원 닉네임 검색에서 출력 시켜줄 정보
        // 1. 회원 닉네임, 소환사 명, 솔랭 및 자랭 정보, 최근 전적
        MemberDTO memberDTO = memberService.findByNickname(nickname);
        SummonerDTO summonerDTO = summonerService.findByUserid(memberDTO.getUserid());
        RankedSoloDTO rankedSoloDTO = summonerService.selectRankedSoloData(summonerDTO.getId());
        RankedFlexDTO rankedFlexDTO = summonerService.selectRankedFlexData(summonerDTO.getId());

        // 최근전적의 경우 url 에 target 변수를 추가하여 어디서 온 매핑 요청인지를 명확히 시켜준 후
        // SummonerController 클래스의 matchHistory 메소드를 실행시킨다.
        // 이후 matchHistory 메소드에서 target 값을 통해 어떤 작업을 수행해야 하는지 알아낸 후 해당되는 작업을 수행한다.
        // 마이페이지에서 최근 전적 기능을 호출한 경우 -> api 를 통해 데이터를 호출하여 데이터베이스에 삽입 한 후, 해당 데이터들을 출력시키는 기능 수행
        // 닉네임 검색을 통해 최근 전적 기능을 호출한 경우 -> 테이블이 비어있다면 위와 동일한 기능 수행, 그렇지 않다면 단순 검색 후 출력
        model.addAttribute("searchNickName", memberDTO);
        model.addAttribute("summoner", summonerDTO);
        model.addAttribute("ranked_solo", rankedSoloDTO);
        model.addAttribute("ranked_flex", rankedFlexDTO);
        return "searchNickNameResult";
    }

    @PostMapping("/facebookLogin.do")
    public String facebookLogin(@RequestParam("facebookName") String facebookName, @RequestParam("facebookId") String facebookId,
                                @RequestParam("facebookEmail") String facebookEmail, HttpServletRequest request,
                                Model model){
        
        // 데이터베이스 검색 시 기존에 존재하는 계정이라면 로그인 성공 처리
        facebookId = "A"+facebookId; // 전적 테이블 명 처리를 위한 문자 삽입
        MemberDTO memberDTO = memberService.selectMemberOne(facebookId);
        session = request.getSession();

        System.out.println(memberDTO);

        if(memberDTO == null){
            //존재하지 않는 계정이라면 데이터베이스에 삽입시킨 다음 로그인 성공 처리
            // 비밀번호는 아이디와 동일한 값으로 삽입하고 닉네임은 이름과 똑같은 값으로 삽입 시켜준다.
            memberDTO = new MemberDTO();

            memberDTO.setUserid(facebookId);
            memberDTO.setPasswd(facebookId);
            memberDTO.setName(facebookName);
            memberDTO.setNickname(facebookName);
            memberDTO.setEmail(facebookEmail);
            memberService.insert(memberDTO);
        }else{
            memberDTO.setSummoner_name(memberService.selectInnerJoinsummoner_name(memberDTO.getUserid()));
        }
        session.setAttribute("member", memberDTO);
        model.addAttribute("result", "Success");
        return "../valid/loginvalid";
    }
}
