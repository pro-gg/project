package Project.pro.gg.Repository;

import java.util.HashMap;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Project.pro.gg.DAO.TeamDAO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.RankedSoloDTO;
import Project.pro.gg.Model.TeamApplyDTO;
import Project.pro.gg.Model.TeamDTO;

@Repository
@RequiredArgsConstructor
public class TeamRepository implements TeamDAO {

    private final SqlSession sqlSession;

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

    @Override
    public TeamApplyDTO selectTeamApply_Join(String nickname) {
        return sqlSession.selectOne("team.selectTeamApply_Join", nickname);
    }

    @Override
    public int selectTierValue(String tier, String tier_rank) {
        String sql = "select tier_value from tiervalue where tier='"+tier+"' and tier_rank='"+tier_rank+"'";
        return sqlSession.selectOne("team.selectTierValue", sql);
    }
    
    @Override
	public String selectTier(int tier_value) {
    	String sql = "SELECT concat(tier,' ', tier_rank) FROM tiervalue WHERE tier_value = "+ tier_value;
		return sqlSession.selectOne("team.selectTier", sql);
	}

	@Override
    public void insertApply(TeamApplyDTO teamApplyDTO) {
        sqlSession.insert("team.insertApply", teamApplyDTO);
    }

    @Override
    public String selectLine(TeamApplyDTO teamApplyDTO) {
        return sqlSession.selectOne("team.selectLine", teamApplyDTO);
    }

    @Override
    public List<TeamApplyDTO> selectApplyMemberList(String teamName) {
        return sqlSession.selectList("team.selectApplyMember", teamName);
    }

    @Override
    public void deleteApplyMember(TeamApplyDTO teamApplyDTO) {
        sqlSession.delete("team.deleteApplyMember", teamApplyDTO);
    }

    @Override
    public void deleteTeam(TeamDTO teamDTO) {
        sqlSession.delete("team.deleteTeam", teamDTO);
    }

    @Override
    public TeamApplyDTO selectApplyStatus(String nickname) {
        return sqlSession.selectOne("team.selectApplyStatus", nickname);
    }

    @Override
    public void updateTeamLine(TeamApplyDTO teamApplyDTO) {
        sqlSession.update("team.updateTeamLine", teamApplyDTO);
    }

	@Override
	public void updateTierAvg(TeamDTO teamDTO) {
		sqlSession.update("team.updateTierAvg", teamDTO);
	}

	@Override
	public List<TeamDTO> selectMatchList(HashMap<String,Integer> idx) {
		return sqlSession.selectList("team.selectMatchList",idx);
	}

    @Override
    public List<TeamDTO> selectDynamicSearch(TeamDTO teamDTO) {
        return sqlSession.selectList("team.selectDynamicSearch", teamDTO);
    }

    @Override
    public List<RankedSoloDTO> selectDynamicSearch_Crew(RankedSoloDTO rankedSoloDTO) {
        return sqlSession.selectList("team.selectDynamicSearch_Crew", rankedSoloDTO);
    }

    @Override
    public void updateTeam(TeamDTO teamDTO) {
        sqlSession.update("team.updateTeam", teamDTO);
    }
}
