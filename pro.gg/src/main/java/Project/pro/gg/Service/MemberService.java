package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberService  {

    public void insert(MemberDTO memberDTO);
    public String selectOne(String id, String passwd);
    public MemberDTO selectMemberOne(String id);
    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO);
    public void deleteSummonerName(MemberDTO memberDTO);
    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO);
}
