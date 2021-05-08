package Project.pro.gg.Controller;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

    HttpSession session;

    @GetMapping("/trylogin.do")
    public String tryLogin(@RequestParam("id") String id, @RequestParam("passwd") String passwd,
                           Model model, HttpServletRequest request){

        session = request.getSession();

        String result = memberService.selectOne(id, passwd);
        if(result == "Success"){
            MemberDTO memberDTO = memberService.selectMemberOne(id);
            session.setAttribute("member", memberDTO);
        } else{
            session.setAttribute("member", null);
        }
        model.addAttribute("result", result);
        model.addAttribute("member", session.getAttribute("member"));

        return "../valid/loginvalid";
    }

    @GetMapping("/logout.do")
    public String logout(Model model){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        memberDTO = null;
        session.removeAttribute("member");
        model.addAttribute("member", memberDTO);

        return "main";
    }

    @GetMapping("/mypage.do")
    public String myPage(){
        return "mypage";
    }

    @GetMapping("/tryregister.do")
    public String tryRegister(@RequestParam(value = "id") String id,
                              @RequestParam(value = "passwd") String passwd,
                              @RequestParam(value = "email") String email,
                              @RequestParam(value = "nickname") String nickname,
                              @RequestParam(value = "name") String name){

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        memberDTO.setPasswd(passwd);
        memberDTO.setEmail(email);
        memberDTO.setNickname(nickname);
        memberDTO.setName(name);

        memberService.insert(memberDTO);

        return "main";
    }
}