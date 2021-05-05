package Project.pro.gg.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    @GetMapping("/trylogin.do")
    public String tryLogin(@RequestParam("id") String id, @RequestParam("passwd") String passwd,
                           Model model){
        System.out.println(id);
        System.out.println(passwd);
        return "main";
    }
}
