package Project.pro.gg.Controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/move/login.do")
    public String login(){
        return "login";
    }

    @GetMapping("/move/register.do")
    public String register(){
        return "register";
    }

    @GetMapping("/mypage.do")
    public String myPage(){
        return "mypage";
    }
}
