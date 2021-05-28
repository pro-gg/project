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

import java.net.URLEncoder;

@AutoConfigureMockMvc
@Aspect
@SpringBootTest
@WebAppConfiguration
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createTeam() throws Exception{

        String teamName = "첫번째팀";
        String week_input = "주말";
        String startTime = "14:00";
        String endTime = "16:00";
        String tier_limit = "GOLD";
        String team_description = "처음으로만든팀";

        String captinName = "FirstRegister";
        String userid = "ghwns6659";

        startTime = URLEncoder.encode(startTime, "UTF-8");
        endTime = URLEncoder.encode(endTime, "UTF-8");

        String teamData = "{ 'teamName':"+ teamName +", 'week_input':" + week_input + ", 'startTime':" + startTime + ", 'endTime':" + endTime +
                ", 'tier_limit':" + tier_limit + ", 'team_description':" + team_description + ", 'captinName':"+captinName + ", 'userid':"+userid + " }";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/createTeam.do")
                        .param("teamData", teamData))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void callTeamList() throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.get("/move/teammatch.do"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamList.do"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("teamList"));
    }

    @Test
    public void teamDetail() throws Exception{

        String teamName = "첫번째팀";

        this.mockMvc.perform(MockMvcRequestBuilders.get("/teamdetail.do")
                    .param("teamName", teamName))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
    }
}