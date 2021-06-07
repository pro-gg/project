package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {
    public TeamDTO selectTeam(TeamDTO teamDTO);

    public void insertTeamData(TeamDTO teamDTO, MemberDTO memberDTO);

    public List<TeamDTO> selectTeamList();

    public TeamApplyDTO selectTeamApply_Join(String nickname);

    public boolean tierCheck(String tier, String tier_rank, String[] tierArray);

    public void insertApply(TeamApplyDTO teamApplyDTO);

    public String selectLine(TeamApplyDTO teamApplyDTO);

    public List<TeamApplyDTO> selectApplyMemberList(String teamName);

    public void deleteApplyMember(TeamApplyDTO teamApplyDTO);
}
