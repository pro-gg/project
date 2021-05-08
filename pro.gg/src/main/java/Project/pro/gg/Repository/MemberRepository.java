package Project.pro.gg.Repository;

import Project.pro.gg.DAO.MemberDAO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.TableMapping.MemberSummoner;
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
    public MemberDTO selectOne(String id) {
        MemberDTO memberDTO = null;
        memberDTO = sqlSession.selectOne("member.selectOne", id);

        return memberDTO;
    }

    @Override
    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO) {
        MemberSummoner memberSummoner= new MemberSummoner(memberDTO.getId(), summonerDTO.getSummonerName());
        sqlSession.insert("summoner.insertSummonerData", summonerDTO);
        sqlSession.update("member.summonerName", memberSummoner);
    }
}
