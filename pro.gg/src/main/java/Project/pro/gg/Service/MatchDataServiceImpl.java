package Project.pro.gg.Service;

import Project.pro.gg.Model.MatchDataDTO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Repository.MatchDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchDataServiceImpl implements MatchDataService{

    @Autowired
    MatchDataRepository matchDataRepository;

    @Override
    public String selectMatchData(MatchDataDTO matchDataDTO, MemberDTO memberDTO) {
        return matchDataRepository.selectMatchData(matchDataDTO, memberDTO);
    }

    @Override
    public void insertMatchData(MatchDataDTO matchDataDTO, MemberDTO memberDTO) {
        matchDataRepository.insertMatchData(matchDataDTO, memberDTO);
    }

    @Override
    public List<MatchDataDTO> selectMatchDataAll(MemberDTO memberDTO) {
        return matchDataRepository.selectMatchDataAll(memberDTO);
    }
}
