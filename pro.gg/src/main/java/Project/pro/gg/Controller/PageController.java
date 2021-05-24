package Project.pro.gg.Controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

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
}
