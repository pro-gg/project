package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.TeamDTO;
import Project.pro.gg.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService{

    @Autowired
    TeamRepository teamRepository;

    @Override
    public TeamDTO selectTeam(TeamDTO teamDTO) {
        return teamRepository.selectTeam(teamDTO);
    }

    @Override
    public void insertTeamData(TeamDTO teamDTO, MemberDTO memberDTO) {
        teamRepository.insertTeamData(teamDTO, memberDTO);
    }

    @Override
    public List<TeamDTO> selectTeamList() {
        return teamRepository.selectTeamList();
    }
}
