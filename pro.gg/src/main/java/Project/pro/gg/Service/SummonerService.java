package Project.pro.gg.Service;

import Project.pro.gg.Model.*;
import org.springframework.stereotype.Service;

@Service
public interface SummonerService {

    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO);
    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO);

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
