package Project.pro.gg.DAO;

import Project.pro.gg.Model.MemberDTO;

public interface MemberDAO {

    public void insert(MemberDTO memberDTO);
    public MemberDTO selectOne(String id);
    public void deleteSummonerName(MemberDTO memberDTO);

    public MemberDTO findId(MemberDTO memberDTO);

    public MemberDTO findPasswd(MemberDTO memberDTO);

    public void updatePasswd(MemberDTO memberDTO);

    public void updateMemberData(MemberDTO memberDTO);

    public void deleteMember(MemberDTO memberDTO);

    public String selectInnerJoinsummoner_name(String userid);

    public MemberDTO findByNickname(String nickname);

    public void relationReleaseOfTeam(MemberDTO memberDTO);
}
