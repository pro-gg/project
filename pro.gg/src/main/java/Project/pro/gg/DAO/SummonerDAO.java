package Project.pro.gg.DAO;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;

public interface SummonerDAO {

    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO);
    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO);
}
