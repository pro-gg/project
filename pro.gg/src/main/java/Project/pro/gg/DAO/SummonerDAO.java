package Project.pro.gg.DAO;

import Project.pro.gg.Model.*;

public interface SummonerDAO {

    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO);
    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO);

    public void insertRankedSoloData(RankedSoloDTO rankedSoloDTO);

    public void insertRankedFlexData(RankedFlexDTO rankedFlexDTO);

    public void updateRankedSoloData(RankedSoloDTO rankedSoloDTO);

    public void updateRankedFlexData(RankedFlexDTO rankedFlexDTO);

    public RankedSoloDTO selectRankedSoloData(String id);

    public RankedFlexDTO selectRankedFlexData(String id);

    public SummonerDTO findByUserid(String userid);

    public void insertSpellData(SpellDTO spellDTO);

    public SpellDTO selectSpellData(int keyValue);
}
