package Project.pro.gg.Controller;

import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@Aspect
@SpringBootTest
@WebAppConfiguration
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 회원가입 테스트
    @Test
    @Transactional
    public void tryRegisgterTest() throws Exception{
        String userid = "ghwns1603";
        String passwd = "zxc3226659";
        String email = "ghwns6659@gmail.com";
        String name = "서호준";
        String nickname = "FirstRegister";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/tryregister.do")
                .param("id", userid)
                .param("passwd", passwd)
                .param("email", email)
                .param("nickname", nickname)
                .param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // 세션이 필요한 테스트들 모음(로그인(세션 생성) -> 소환사 명 등록 -> 로그아웃)
    @Test
    @Transactional
    public void sessionTest() throws Exception{
        String userid = "ghwns6659";
        String passwd = "zxc3226659";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/trylogin.do")
            .param("id", userid)
            .param("passwd", passwd))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("member"));

        String summonerName = "hideonbush";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/SearchSummonerData.do")
                .param("summonerName", summonerName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.model().attributeExists("summoner_name_exist"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/updateSummonerName.do"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("member"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/logout.do"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.model().attributeExists("member"));
    }
}