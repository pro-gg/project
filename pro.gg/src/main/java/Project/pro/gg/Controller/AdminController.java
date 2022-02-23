package Project.pro.gg.Controller;

import Project.pro.gg.Configuration.DBConfiguration;
import Project.pro.gg.Model.AdminDTO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    HttpSession session;

    @PostMapping("confirmAdmin.do")
    public String adminLogin(@RequestParam("admin") String admin, Model model,  HttpServletRequest request){

        AdminDTO adminDTO = new AdminDTO();
        session = request.getSession();
        String result = "";


        try {
            JSONObject jsonObject = new JSONObject(admin);
            adminDTO.setAdminId((String) jsonObject.get("adminId")) ;
            adminDTO.setAdminPasswd((String) jsonObject.get("adminPasswd"));

            result = adminService.selectAdmin(adminDTO.getAdminId(), adminDTO.getAdminPasswd());
            if(result == "Success"){
                adminDTO = adminService.selectAdminOne(adminDTO.getAdminId());
                session.setAttribute("admin", adminDTO);
            } else{
                session.setAttribute("admin", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("result", result);
        model.addAttribute("admin", (AdminDTO)session.getAttribute("admin"));

        return "../valid/adminloginvalid";
    }

    @GetMapping("/memberSelect.do")
    public String memberSelect(@RequestParam("selectMember") String nickname, Model model){
        MemberDTO memberDTO = adminService.selectByNickName(nickname);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = mapper.writeValueAsString(memberDTO);

            model.addAttribute("jsonMember", jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "admin";
    }


}
