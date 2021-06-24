package Project.pro.gg.DAO;

import java.util.HashMap;
import java.util.List;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.RankedSoloDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;

public interface TeamDAO {
    public TeamDTO selectTeam(TeamDTO teamDTO);

    public void insertTeamData(TeamDTO teamDTO, MemberDTO memberDTO);

    public List<TeamDTO> selectTeamList();

    public TeamApplyDTO selectTeamApply_Join(String nickname);

    public int selectTierValue(String tier, String tier_rank);
    
    public String selectTier(int tier_value);

    public void insertApply(TeamApplyDTO teamApplyDTO);

    public String selectLine(TeamApplyDTO teamApplyDTO);

    public List<TeamApplyDTO> selectApplyMemberList(String teamName);

    public void deleteApplyMember(TeamApplyDTO teamApplyDTO);

    public void deleteTeam(TeamDTO teamDTO);

    public TeamApplyDTO selectApplyStatus(String nickname);

    public void updateTeamLine(TeamApplyDTO teamApplyDTO);
    
    public void updateTierAvg(TeamDTO teamDTO);
    
    public List<TeamDTO> selectMatchList(HashMap<String, Integer> idx);

    public List<TeamDTO> selectDynamicSearch(TeamDTO teamDTO);

    public List<RankedSoloDTO> selectDynamicSearch_Crew(RankedSoloDTO rankedSoloDTO);
}
