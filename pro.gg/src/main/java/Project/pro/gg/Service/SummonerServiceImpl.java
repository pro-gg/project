package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.RankedFlexDTO;
import Project.pro.gg.Model.RankedSoloDTO;
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

    @Override
    public void insertRankedSoloData(RankedSoloDTO rankedSoloDTO) {
        summonerRepository.insertRankedSoloData(rankedSoloDTO);
    }

    @Override
    public void insertRankedFlexData(RankedFlexDTO rankedFlexDTO) {
        summonerRepository.insertRankedFlexData(rankedFlexDTO);
    }

    @Override
    public void updateRankedSoloData(RankedSoloDTO rankedSoloDTO) {
        summonerRepository.updateRankedSoloData(rankedSoloDTO);
    }

    @Override
    public void updateRankedFlexData(RankedFlexDTO rankedFlexDTO) {
        summonerRepository.updateRankedFlexData(rankedFlexDTO);
    }

    @Override
    public RankedSoloDTO selectRankedSoloData(String id) {
        return summonerRepository.selectRankedSoloData(id);
    }

    @Override
    public RankedFlexDTO selectRankedFlexData(String id) {
        return summonerRepository.selectRankedFlexData(id);
    }

    @Override
    public SummonerDTO findByUserid(String userid) {
        return summonerRepository.findByUserid(userid);
    }
}
