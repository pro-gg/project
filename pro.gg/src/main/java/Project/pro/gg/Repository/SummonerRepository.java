package Project.pro.gg.Repository;

import Project.pro.gg.DAO.SummonerDAO;
import Project.pro.gg.Model.*;
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

    @Override
    public void insertRankedSoloData(RankedSoloDTO rankedSoloDTO) {
        sqlSession.insert("summoner.insertRankedSoloData", rankedSoloDTO);
    }

    @Override
    public void insertRankedFlexData(RankedFlexDTO rankedFlexDTO) {
        sqlSession.insert("summoner.insertRankedFlexData", rankedFlexDTO);
    }

    @Override
    public void updateRankedSoloData(RankedSoloDTO rankedSoloDTO) {
        sqlSession.update("summoner.updateRankedSoloData", rankedSoloDTO);
    }

    @Override
    public void updateRankedFlexData(RankedFlexDTO rankedFlexDTO) {
        sqlSession.update("summoner.updateRankedFlexData", rankedFlexDTO);
    }

    @Override
    public RankedSoloDTO selectRankedSoloData(String id) {
        return sqlSession.selectOne("summoner.selectRankedSoloData", id);
    }

    @Override
    public RankedFlexDTO selectRankedFlexData(String id) {
        return sqlSession.selectOne("summoner.selectRankedFlexData", id);
    }

    @Override
    public SummonerDTO findByUserid(String userid) {
        return sqlSession.selectOne("summoner.findByUserid", userid);
    }

    @Override
    public void insertSpellData(SpellDTO spellDTO) {
        sqlSession.insert("summoner.insertSpell", spellDTO);
    }

    @Override
    public SpellDTO selectSpellData(int keyValue) {
        return sqlSession.selectOne("summoner.selectSpell", keyValue);
    }
}
