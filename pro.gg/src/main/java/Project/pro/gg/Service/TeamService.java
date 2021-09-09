package Project.pro.gg.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.RankedSoloDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;

@Service
public interface TeamService {
    public TeamDTO selectTeam(TeamDTO teamDTO);

    public void insertTeamData(TeamDTO teamDTO, MemberDTO memberDTO);

    public List<TeamDTO> selectTeamList();

    public TeamApplyDTO selectTeamApply_Join(String nickname);

    public boolean tierCheck(String tier, String tier_rank, String[] tierArray);
    
    public int tierCalculate(String tier, String tier_rank);
    
    public String getTier(int tier_value);

    public void insertApply(TeamApplyDTO teamApplyDTO);

    public String selectLine(TeamApplyDTO teamApplyDTO);

    public List<TeamApplyDTO> selectApplyMemberList(String teamName);

    public void deleteApplyMember(TeamApplyDTO teamApplyDTO);

    public void deleteTeam(TeamDTO teamDTO);

    public boolean selectOtherApply(String nickname);

    public TeamApplyDTO selectApplyStatus(String nickname);

    public void updateTeamLine(TeamApplyDTO teamApplyDTO);
    
    public void updateTierAvg(TeamDTO teamDTO);
    
    public List<TeamDTO> selectMatchList(HashMap<String,Integer> idx);

    public List<TeamDTO> selectDynamicSearch(TeamDTO teamDTO);

    public List<RankedSoloDTO> selectDynamicSearch_Crew(RankedSoloDTO rankedSoloDTO);

    public void updateTeam(TeamDTO teamDTO);
}
