package Project.pro.gg.DAO;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.TeamDTO;

import java.util.List;

public interface TeamDAO {
    public TeamDTO selectTeam(TeamDTO teamDTO);

    public void insertTeamData(TeamDTO teamDTO, MemberDTO memberDTO);

    public List<TeamDTO> selectTeamList();
}
