package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import org.springframework.stereotype.Service;

@Service
public interface SummonerService {

    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO);
    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO);
}
