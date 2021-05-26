package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberService  {

    public void insert(MemberDTO memberDTO);
    public String selectOne(String id, String passwd);
    public MemberDTO selectMemberOne(String id);
    public void deleteSummonerName(MemberDTO memberDTO);

    public MemberDTO findId(MemberDTO memberDTO);

    public MemberDTO findPasswd(MemberDTO memberDTO);

    public void updatePasswd(MemberDTO memberDTO);

    public void updateMemberData(MemberDTO memberDTO);

    public void deleteMember(MemberDTO memberDTO);
}
