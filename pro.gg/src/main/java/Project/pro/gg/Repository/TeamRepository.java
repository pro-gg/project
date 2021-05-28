package Project.pro.gg.Repository;

import Project.pro.gg.DAO.TeamDAO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.TeamDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamRepository implements TeamDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public TeamDTO selectTeam(TeamDTO teamDTO) {
        return sqlSession.selectOne("team.selectTeamName", teamDTO);
    }

    @Override
    public void insertTeamData(TeamDTO teamDTO, MemberDTO memberDTO) {
        sqlSession.insert("team.insertTeamData", teamDTO);
        sqlSession.update("member.updateTeamName", memberDTO);
    }

    @Override
    public List<TeamDTO> selectTeamList() {
        return sqlSession.selectList("team.selectTeamList");
    }
}
