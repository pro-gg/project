package Project.pro.gg.DAO;

import Project.pro.gg.Model.MatchDataDTO;
import Project.pro.gg.Model.MemberDTO;

import java.util.List;

public interface MatchDataDAO {

    public String selectMatchData(MatchDataDTO matchDataDTO, MemberDTO memberDTO);
    public void insertMatchData(MatchDataDTO matchDataDTO, MemberDTO memberDTO);
    public List<MatchDataDTO> selectMatchDataAll(MemberDTO memberDTO);
}
