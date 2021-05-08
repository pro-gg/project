package Project.pro.gg.Controller;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Service.MemberServiceImpl;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

    HttpSession session;

    String developKey = "RGAPI-0d729973-77ce-492f-8454-df90934550d0";
    String apiURL = "";
    URL riotURL = null;
    HttpURLConnection urlConnection = null;
    BufferedReader br = null;

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

    @GetMapping("SearchSummonerData.do")
    public String searchSummonerData(@RequestParam("summonerName") String summonerName){
        try {
            JSONObject jsonObject = new JSONObject(summonerName);
            summonerName = (String) jsonObject.get("summonerName");

            apiURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+summonerName+"?api_key="+developKey;
            riotURL = new URL(apiURL);
            urlConnection = (HttpURLConnection)riotURL.openConnection();
            urlConnection.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String result="";
            String line="";
            while((line=br.readLine()) != null) {
                result += line;
            }

            JSONObject summonerObject = new JSONObject(result);

            SummonerDTO summonerDTO = new SummonerDTO(
                    summonerObject.getString("name"),
                    summonerObject.getString("id"),
                    summonerObject.getString("accountId"),
                    summonerObject.getString("puuid"),
                    summonerObject.getInt("profileIconId"),
                    summonerObject.getLong("summonerLevel"),
                    summonerObject.getLong("revisionDate"));
            String profileIconURL = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/"+summonerDTO.getProfileiconId()+".png";

            memberService.insertSummonerData(summonerDTO, (MemberDTO)session.getAttribute("member"));

        } catch (Exception e) {
            e.printStackTrace();
        }

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
