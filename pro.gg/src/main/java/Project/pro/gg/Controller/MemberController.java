package Project.pro.gg.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Project.pro.gg.API.KakaoAPI;
import Project.pro.gg.Model.AdminDTO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.RankedFlexDTO;
import Project.pro.gg.Model.RankedSoloDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Service.MemberServiceImpl;
import Project.pro.gg.Service.SummonerServiceImpl;

@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    SummonerServiceImpl summonerService;

    public static HttpSession session;
    
    KakaoAPI kakaoApi = new KakaoAPI();

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

    @PostMapping("/check_nickname.do")
    public void check_nickname(@RequestParam("nickname") String nickname, HttpServletResponse response){

        MemberDTO memberDTO = memberService.findByNickname(nickname);

        if (memberDTO == null){
            try {
                response.getWriter().write("OK");
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().write("Fail");
            }catch (Exception e){
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
                 memberDTO.setSummoner_name(memberService.selectInnerJoinsummoner_name(memberDTO.getUserid()));
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
        try{
            session.removeAttribute("member");
            session.removeAttribute("admin");
        }catch (NullPointerException e){
            // 세션이 만료된 상태일 때 로그아웃 기능이 동작한 경우 수행
        }
        
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

    @GetMapping("/findmemberdata.do")
    public String findMemberData(@RequestParam("nickname") String nickname, Model model){

        // 회원 닉네임 검색에서 출력 시켜줄 정보
        // 1. 회원 닉네임, 소환사 명, 솔랭 및 자랭 정보, 최근 전적
        MemberDTO memberDTO = memberService.findByNickname(nickname);
        SummonerDTO summonerDTO = summonerService.findByUserid(memberDTO.getUserid());
        RankedSoloDTO rankedSoloDTO = summonerService.selectRankedSoloData(summonerDTO.getId());
        RankedFlexDTO rankedFlexDTO = summonerService.selectRankedFlexData(summonerDTO.getId());

        // 최근전적의 경우 url 에 target 변수를 추가하여 어디서 온 매핑 요청인지를 명확히 시켜준 후
        // SummonerController 클래스의 matchHistory 메소드를 실행시킨다.
        // 이후 matchHistory 메소드에서 target 값을 통해 어떤 작업을 수행해야 하는지 알아낸 후 해당되는 작업을 수행한다.
        // 마이페이지에서 최근 전적 기능을 호출한 경우 -> api 를 통해 데이터를 호출하여 데이터베이스에 삽입 한 후, 해당 데이터들을 출력시키는 기능 수행
        // 닉네임 검색을 통해 최근 전적 기능을 호출한 경우 -> 테이블이 비어있다면 위와 동일한 기능 수행, 그렇지 않다면 단순 검색 후 출력
        model.addAttribute("searchNickName", memberDTO);
        model.addAttribute("summoner", summonerDTO);
        model.addAttribute("ranked_solo", rankedSoloDTO);
        model.addAttribute("ranked_flex", rankedFlexDTO);
        return "searchNickNameResult";
    }
    @GetMapping("/naver.do")
    public String naverLogin(@RequestParam("code") String code, @RequestParam("state") String state) throws UnsupportedEncodingException {
    	String clientId = "_GlAhgDzVIlPh0a5FTYm";
		String clientSecret = "3bQFANR1Il"; 
		String redirectURI = URLEncoder.encode("/main.jsp","UTF-8");
				
		String apiURL = "";
		apiURL += "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		String access_token = "";
		String refresh_token = ""; //나중에 이용
		
		try { 
			  URL naverUrl = new URL(apiURL);
		      HttpURLConnection con = (HttpURLConnection)naverUrl.openConnection();
		      con.setRequestMethod("GET");
		      int responseCode = con.getResponseCode();
		      BufferedReader br;
		      if(responseCode==200) { // 정상 호출
		        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		      } else {  // 에러 발생
		        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		      }
		      String inputLine;
		      StringBuffer res = new StringBuffer();
		      while ((inputLine = br.readLine()) != null) {
		        res.append(inputLine);
		      }
		      br.close();
		      if(responseCode==200) {
//		    	  System.out.println(res.toString());
		    		JSONParser parsing = new JSONParser();
		    		org.json.simple.JSONObject result = (org.json.simple.JSONObject)parsing.parse(res.toString());
		    		
		    		
		    		access_token = (String)result.get("access_token");
		    		refresh_token = (String)result.get("refresh_token");
		    		if(access_token != null) {
		    			try {
		    				 String header = "Bearer " + access_token;
		    				 String apiurl = "https://openapi.naver.com/v1/nid/me";
		    				 naverUrl = new URL(apiurl);
		    				 con = (HttpURLConnection)naverUrl.openConnection();
		    				con.setRequestMethod("GET");
		    				con.setRequestProperty("Authorization", header);
		    				responseCode = con.getResponseCode();
		    				if(responseCode==200) { // 정상 호출
		    				 br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    				} else {  // 에러 발생
		    				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		    				}
		    				inputLine = "";
		    				res = new StringBuffer();
		    				 while ((inputLine = br.readLine()) != null) {
		    				res.append(inputLine);
		    				}
		    				br.close();
		    				
		    				parsing = new JSONParser();
		    				result = (org.json.simple.JSONObject)parsing.parse(res.toString());
		    				
		    				String id = (String)((org.json.simple.JSONObject)result.get("response")).get("id");
		    				String email = (String)((org.json.simple.JSONObject)result.get("response")).get("email");
		    				String name = (String)((org.json.simple.JSONObject)result.get("response")).get("name");
		    				String nickname = (String)((org.json.simple.JSONObject)result.get("response")).get("nickname");
		    				
		    				System.out.println(id);
		    				MemberDTO memberDTO = memberService.selectMemberOne(id);
		    				if(memberDTO == null) {
		    					memberDTO = new MemberDTO();
		    	    			memberDTO.setUserid(id);
		    	    			memberDTO.setPasswd(id);
		    	    			memberDTO.setName(name);
		    	    			memberDTO.setNickname(nickname);
		    	    			memberDTO.setEmail(email);
		    	    			memberService.insert(memberDTO);
		    	    		}
		    				
		    				session.setAttribute("member", memberDTO);
						} catch (Exception e) {
							
						}
		    		}
		      }
		    } catch (Exception e) {
		      System.out.println(e);
		    }
    	
    	return "main";
    }
    
    @GetMapping("/kakao.do")
    public String login(@RequestParam("code") String code, HttpServletRequest request) {
    	String access_token = kakaoApi.getAccessToken(code);
    	HashMap<String, String> userInfo = kakaoApi.getUserInfo(access_token);
    	MemberDTO memberDTO = new MemberDTO();
    	if(userInfo.get("id") != null) {
    		memberDTO = memberService.selectMemberOne(userInfo.get("id"));
    		if(memberDTO == null) {
    			memberDTO = new MemberDTO();
    			memberDTO.setUserid(userInfo.get("id"));
    			memberDTO.setPasswd(userInfo.get("id"));
    			memberDTO.setNickname(userInfo.get("nickname"));
    			memberDTO.setName(userInfo.get("nickname"));
    			memberDTO.setEmail(userInfo.get("email"));
    			
    			memberService.insert(memberDTO);
    		}
    	}
    	session = request.getSession();
    	session.setAttribute("member", memberDTO);
    	
    	return "main";
    }
}
