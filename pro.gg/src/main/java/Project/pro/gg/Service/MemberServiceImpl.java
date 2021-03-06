package Project.pro.gg.Service;

import Project.pro.gg.DAO.MemberDAO;
import Project.pro.gg.Model.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberDAO memberRepository;

    @Override
    public void insert(MemberDTO memberDTO) {
        memberRepository.insert(memberDTO);
    }

    @Override
    public String selectOne(String userid, String passwd) {
        String result = "Success";
        MemberDTO memberDTO = memberRepository.selectOne(userid);

        if (memberDTO == null) result = "NotExistId";
        else if (!memberDTO.getPasswd().equals(passwd)) result = "failPasswd";

        return result;
    }

    @Override
    public MemberDTO selectMemberOne(String userid) {
        MemberDTO memberDTO = memberRepository.selectOne(userid);
        return memberDTO;
    }

    @Override
    public void deleteSummonerName(MemberDTO memberDTO) {
        memberRepository.deleteSummonerName(memberDTO);
    }

    @Override
    public MemberDTO findId(MemberDTO memberDTO) {
        return memberRepository.findId(memberDTO);
    }

    @Override
    public MemberDTO findPasswd(MemberDTO memberDTO) {
        return memberRepository.findPasswd(memberDTO);
    }

    @Override
    public MemberDTO findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    @Override
    public void updateTeamName(MemberDTO memberDTO) {
        memberRepository.updateTeamName(memberDTO);
    }

    @Override
    public void updateRecommendPost(MemberDTO memberDTO) {
        memberRepository.updateRecommendPost(memberDTO);
    }

    @Override
    public void updateNotRecommendPost(MemberDTO memberDTO) {
        memberRepository.updateNotRecommendPost(memberDTO);
    }

    @Override
    public void updateRecommendReply(MemberDTO memberDTO) {
        memberRepository.updateRecommendReply(memberDTO);
    }

    @Override
    public void updateNotRecommendReply(MemberDTO memberDTO) {
        memberRepository.updateNotRecommendReply(memberDTO);
    }

    @Override
    public void updatePasswd(MemberDTO memberDTO) {
        memberRepository.updatePasswd(memberDTO);
    }

    @Override
    public void updateMemberData(MemberDTO memberDTO) {
        memberRepository.updateMemberData(memberDTO);
    }

    @Override
    public void deleteMember(MemberDTO memberDTO) {
        memberRepository.deleteMember(memberDTO);
    }

    @Override
    public String selectInnerJoinsummoner_name(String userid) {
        return memberRepository.selectInnerJoinsummoner_name(userid);
    }


}
