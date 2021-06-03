package Project.pro.gg.Service;

import Project.pro.gg.Model.MatchDataDTO;
import Project.pro.gg.Model.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchDataService {

    public String selectMatchData(MatchDataDTO matchDataDTO, MemberDTO memberDTO);
    public void insertMatchData(MatchDataDTO matchDataDTO, MemberDTO memberDTO);
    public List<MatchDataDTO> selectMatchDataAll(MemberDTO memberDTO);
}
