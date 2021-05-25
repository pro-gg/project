package Project.pro.gg.DAO;

import Project.pro.gg.Model.TeamDTO;

import java.util.List;

public interface TeamDAO {
    public TeamDTO selectTeam(TeamDTO teamDTO);

    public void insertTeamData(TeamDTO teamDTO);

    public List<TeamDTO> selectTeamList();
}
