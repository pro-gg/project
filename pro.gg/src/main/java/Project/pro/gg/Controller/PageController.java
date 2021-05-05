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

    @GetMapping("/move/login.do")
    public String login(){
        return "login";
    }
}
