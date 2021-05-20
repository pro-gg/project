package Project.pro.gg.Repository;

import Project.pro.gg.DAO.SummonerDAO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SummonerRepository implements SummonerDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO) {
        return sqlSession.selectOne("summoner.searchSummonerData", summonerDTO);
    }

    @Override
    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO) {
        sqlSession.insert("summoner.insertSummonerData", summonerDTO);
        sqlSession.update("member.summonerName", memberDTO);

        String sql = "create table "+ memberDTO.getUserid() + "(\n" +
                "            matchId varchar(30) not null primary key,\n" +
                "            win boolean not null,\n" +
                "            championId int not null,\n" +
                "            championName varchar(20) not null,\n" +
                "            goldEarned int not null,\n" +
                "            goldSpent int not null,\n" +
                "            itemList JSON not null,\n" +
                "            kills int not null,\n" +
                "            assists int not null,\n" +
                "            deaths int not null,\n" +
                "            spellList JSON not null\n" +
                "        )";
        sqlSession.update("summoner.createTable", sql);
    }
}
