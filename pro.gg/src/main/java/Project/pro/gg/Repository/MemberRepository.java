package Project.pro.gg.Repository;

import Project.pro.gg.DAO.MemberDAO;
import Project.pro.gg.Model.MemberDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository implements MemberDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public void insert(MemberDTO memberDTO) {
        sqlSession.insert("member.insert", memberDTO);
    }

    @Override
    public MemberDTO selectOne(String userid) {
        MemberDTO memberDTO = null;
        memberDTO = sqlSession.selectOne("member.selectOne", userid);

        return memberDTO;
    }

    @Override
    public void deleteSummonerName(MemberDTO memberDTO) {
        String sql = "drop table if exists " + memberDTO.getUserid();
        sqlSession.delete("summoner.dropTable", sql);
        sqlSession.delete("summoner.deleteSummonerData", memberDTO);
    }

    @Override
    public MemberDTO findId(MemberDTO memberDTO) {
        return sqlSession.selectOne("member.findId", memberDTO);
    }

    @Override
    public MemberDTO findPasswd(MemberDTO memberDTO) {
        return sqlSession.selectOne("member.findPasswd", memberDTO);
    }

    @Override
    public MemberDTO findByNickname(String nickname) {
        return sqlSession.selectOne("member.findByNickname", nickname);
    }

    @Override
    public void relationReleaseOfTeam(MemberDTO memberDTO) {
        sqlSession.update("member.updateTeamName", memberDTO);
    }

    @Override
    public void updatePasswd(MemberDTO memberDTO) {
        sqlSession.update("member.updatePasswd", memberDTO);
    }

    @Override
    public void updateMemberData(MemberDTO memberDTO) {
        sqlSession.update("member.updateMemberData", memberDTO);
    }

    @Override
    public void deleteMember(MemberDTO memberDTO) {
        sqlSession.delete("member.deleteMember", memberDTO);

        String sql = "drop table if exists " + memberDTO.getUserid();
        sqlSession.delete("summoner.dropTable", sql);

    }

    @Override
    public String selectInnerJoinsummoner_name(String userid) {
        return sqlSession.selectOne("member.selectInnerJoinsummoner_name", userid);
    }


}
