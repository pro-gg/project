package Project.pro.gg.Repository;

import Project.pro.gg.DAO.MemberDAO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
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
    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO) {
        sqlSession.insert("summoner.insertSummonerData", summonerDTO);
        sqlSession.update("member.summonerName", memberDTO);
    }

    @Override
    public void deleteSummonerName(MemberDTO memberDTO) {
        sqlSession.delete("summoner.deleteSummonerData", memberDTO);
        sqlSession.update("member.deleteSummonerName", memberDTO);
    }

    @Override
    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO) {
        return sqlSession.selectOne("summoner.searchSummonerData", summonerDTO);
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
    public void updatePasswd(MemberDTO memberDTO) {
        sqlSession.update("member.updatePasswd", memberDTO);
    }
}
