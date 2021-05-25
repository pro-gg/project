package Project.pro.gg.Service;

import Project.pro.gg.Model.TeamDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {
    public TeamDTO selectTeam(TeamDTO teamDTO);

    public void insertTeamData(TeamDTO teamDTO);

    public List<TeamDTO> selectTeamList();
}
