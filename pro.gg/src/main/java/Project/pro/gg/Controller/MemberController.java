package Project.pro.gg.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Service.MemberServiceImpl;

@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

    HttpSession session;

    String developKey = "RGAPI-f547a1bc-18ff-4634-872d-3de8716886f1";
    String apiURL = "";
    URL riotURL = null;
    HttpURLConnection urlConnection = null;
    BufferedReader br = null;

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

        session = request.getSession();

        String result = memberService.selectOne(userid, passwd);
        if(result == "Success"){
            MemberDTO memberDTO = memberService.selectMemberOne(userid);
            session.setAttribute("member", memberDTO);
        } else{
            session.setAttribute("member", null);
        }
        model.addAttribute("result", result);
        model.addAttribute("member", (MemberDTO)session.getAttribute("member"));

        return "../valid/loginvalid";
    }

    @GetMapping("/logout.do")
    public String logout(Model model){
        try{
            MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
            memberDTO = null;
            session.setAttribute("member", memberDTO);
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("member", (MemberDTO)session.getAttribute("member"));
        return "main";
    }

    @GetMapping("/SearchSummonerData.do")
    public String searchSummonerData(@RequestParam("summonerName") String summonerName, Model model){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        SummonerDTO summonerDTO = null;
        try {
            JSONObject jsonObject = new JSONObject(summonerName);
            summonerName = URLEncoder.encode((String) jsonObject.get("summonerName"), "UTF-8");

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

            summonerDTO = new SummonerDTO(
                    summonerObject.getString("name"),
                    summonerObject.getString("id"),
                    summonerObject.getString("accountId"),
                    summonerObject.getString("puuid"),
                    summonerObject.getInt("profileIconId"),
                    summonerObject.getLong("summonerLevel"),
                    summonerObject.getLong("revisionDate"),
                    memberDTO.getUserid());
            String profileIconURL = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/"+summonerDTO.getProfileiconId()+".png";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("summoner_name", summonerName);
            return "../valid/notexistSummonerValid";
        }

        if(memberService.selectSummonerData(summonerDTO) != null){
            memberDTO.setSummoner_name(null);
            model.addAttribute("summoner_name_exist", summonerName);
            return "../valid/notexistSummonerValid";
        } else{
            memberDTO.setSummoner_name(summonerDTO.getSummoner_name());
            memberService.insertSummonerData(summonerDTO, memberDTO);
            session.setAttribute("member", memberDTO);
            model.addAttribute("member", (MemberDTO)session.getAttribute("member"));
        }

        return "../valid/summonerNameValid";
    }

    @GetMapping("/updateSummonerName.do")
    public String updateSummonerData(Model model){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        memberService.deleteSummonerName(memberDTO);
        model.addAttribute("member", memberDTO);
        return "updateSummonerName";
    }

}
