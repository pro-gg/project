package Project.pro.gg.Controller;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Service.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberControllerTest {

    String developKey = "RGAPI-b55c47ea-063a-4b68-a2a3-80b791144986";
    String apiURL = "";
    URL riotURL = null;
    HttpURLConnection urlConnection = null;
    BufferedReader br = null;

    @Autowired
    MemberServiceImpl memberService;

    // 통합 테스트 - 회원가입
    @Test
    @Transactional
    public void register(){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserid("test");
        memberDTO.setPasswd("test");
        memberDTO.setName("test");
        memberDTO.setEmail("test@test.com");
        memberDTO.setNickname("test");

        memberService.insert(memberDTO);

        MemberDTO memberDTOtest = memberService.selectMemberOne("test");
        assertEquals(memberDTO.getUserid(), memberDTOtest.getUserid());
    }

    // 단위 테스트
    @Test
    public void searchSummonerData() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserid("ghwns6659");

        String summonerName = "수골딱";
        try {
            summonerName = URLEncoder.encode(summonerName, "UTF-8"); // 퍼센트 인코딩

            apiURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+summonerName+"?api_key="+developKey;
            riotURL = new URL(apiURL);
            urlConnection = (HttpURLConnection)riotURL.openConnection();
            urlConnection.setRequestMethod("GET");

            // 정상적으로 데이터를 받아오는지 테스트
            assertNotNull(urlConnection.getInputStream());

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String result="";
            String line="";
            while((line=br.readLine()) != null) {
                result += line;
            }

            JSONObject summonerObject = new JSONObject(result);
            
            String summoner_name = URLEncoder.encode(summonerObject.getString("name"), "UTF-8");
            assertEquals(summonerName, summoner_name); // 인코딩 일치 확인

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}