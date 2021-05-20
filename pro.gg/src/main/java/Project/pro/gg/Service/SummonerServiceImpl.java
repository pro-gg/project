package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummonerServiceImpl implements SummonerService{

    @Autowired
    SummonerRepository summonerRepository;

    @Override
    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO) {
        summonerRepository.insertSummonerData(summonerDTO, memberDTO);
    }

    @Override
    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO) {

        return summonerRepository.selectSummonerData(summonerDTO);
    }
}
