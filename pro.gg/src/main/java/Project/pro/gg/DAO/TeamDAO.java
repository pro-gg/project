package Project.pro.gg.DAO;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;

import java.util.List;

public interface TeamDAO {
    public TeamDTO selectTeam(TeamDTO teamDTO);

    public void insertTeamData(TeamDTO teamDTO, MemberDTO memberDTO);

    public List<TeamDTO> selectTeamList();

    public TeamApplyDTO selectTeamApply_Join(String nickname);

    public int selectTierValue(String tier, String tier_rank);

    public void insertApply(TeamApplyDTO teamApplyDTO);

    public String selectLine(TeamApplyDTO teamApplyDTO);

    public List<TeamApplyDTO> selectApplyMemberList(String teamName);

    public void deleteApplyMember(TeamApplyDTO teamApplyDTO);

    public void deleteTeam(TeamDTO teamDTO);

    public TeamApplyDTO selectApplyStatus(String nickname);
}
