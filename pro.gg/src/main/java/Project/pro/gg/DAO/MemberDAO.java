package Project.pro.gg.DAO;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;

public interface MemberDAO {

    public void insert(MemberDTO memberDTO);
    public MemberDTO selectOne(String id);
    public void deleteSummonerName(MemberDTO memberDTO);

    public MemberDTO findId(MemberDTO memberDTO);

    public MemberDTO findPasswd(MemberDTO memberDTO);

    public void updatePasswd(MemberDTO memberDTO);
}
