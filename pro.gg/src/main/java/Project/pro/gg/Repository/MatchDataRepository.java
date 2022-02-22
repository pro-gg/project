package Project.pro.gg.Repository;

import Project.pro.gg.DAO.MatchDataDAO;
import Project.pro.gg.Model.MatchDataDTO;
import Project.pro.gg.Model.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchDataRepository implements MatchDataDAO {


    private final SqlSession sqlSession;

    @Override
    public String selectMatchData(MatchDataDTO matchDataDTO, MemberDTO memberDTO) {
        String sql = "select ifnull(matchId, null) from " + memberDTO.getUserid() + " where matchId = '" + matchDataDTO.getMatchId()+"'";
        return sqlSession.selectOne("matchData.selectMatchData", sql);
    }

    @Override
    public void insertMatchData(MatchDataDTO matchDataDTO, MemberDTO memberDTO) {

        String sql = "insert into " + memberDTO.getUserid() + "(matchId, win, championId, championName, goldEarned, goldSpent, itemList, kills, assists, deaths, spellList)" +
                "values('"+ matchDataDTO.getMatchId() + "', " + matchDataDTO.isWin() + ", " + matchDataDTO.getChampionId() + ", '" + matchDataDTO.getChampionName() + "', " +
                matchDataDTO.getGoldEarned() + ", " + matchDataDTO.getGoldSpent() + ", '" + matchDataDTO.getJson_itemList() + "', " + matchDataDTO.getKills() + ", " +
                matchDataDTO.getAssists() + ", " + matchDataDTO.getDeaths() + ", '" + matchDataDTO.getJson_spellList() + "')";
        sqlSession.insert("matchData.insertMatchData", sql);
    }

    @Override
    public List<MatchDataDTO> selectMatchDataAll(MemberDTO memberDTO) {
        return sqlSession.selectList("matchData.selectMatchDataAll", memberDTO);
    }
}
