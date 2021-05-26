package Project.pro.gg.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Project.pro.gg.Model.AdminDTO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Service.MemberServiceImpl;

@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

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
        session.removeAttribute("member");
        session.removeAttribute("admin");
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
}
