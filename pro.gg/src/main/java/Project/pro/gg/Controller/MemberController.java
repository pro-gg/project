package Project.pro.gg.Controller;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Service.MemberService;
import Project.pro.gg.Service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

    @GetMapping("/trylogin.do")
    public String tryLogin(@RequestParam("id") String id, @RequestParam("passwd") String passwd,
                           Model model){
        String result = memberService.selectOne(id, passwd);
        model.addAttribute("result", result);
        return "../valid/loginvalid";
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

        return "login";
    }
}
