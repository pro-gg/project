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

    // 회원가입 테스트(입력)
    @Test
    public void tryRegisgterTest() throws Exception{
        String userid = "ghwns6659";
        String passwd = "@zxc3226659@";
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
        String passwd = "@zxc3226659@";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/trylogin.do")
            .param("id", userid)
            .param("passwd", passwd))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("result"));

        String summonerName = "hideonbush";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/SearchSummonerData.do")
                .param("summonerName", summonerName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/updateSummonerName.do"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("member"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/logout.do"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // 회원 탈퇴(로그인(세션 생성) -> 회원 정보 수정(수정) -> 회원 아이디 찾기(조회, 검색) -> 회원 탈퇴(삭제))
    @Test
    @Transactional
    public void memberSecessionTest() throws Exception{
        String userid = "ghwns6659";
        String passwd = "@zxc3226659@";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/trylogin.do")
                .param("id", userid)
                .param("passwd", passwd))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("result"));

        String email = "ghwns6659@gmail.com";
        String name = "서호준";
        String nickname = "FirstRegister";
        String updateMember = "{'nickname':" + nickname +", 'name':" + name + ", 'email':" + email + "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/updateMemberData.do")
                .param("updateMember", updateMember))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        String findId = "{ 'name':"+ name + ", 'email': "+ email + "}";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/findId.do")
                .param("findId", findId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("memberId"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/memberSecession.do"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}