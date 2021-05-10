package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.SummonerDTO;
import Project.pro.gg.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository memberRepository;

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
    public void insertSummonerData(SummonerDTO summonerDTO, MemberDTO memberDTO) {
        memberRepository.insertSummonerData(summonerDTO, memberDTO);
    }

    @Override
    public void deleteSummonerName(MemberDTO memberDTO) {
        memberRepository.deleteSummonerName(memberDTO);
    }

    @Override
    public SummonerDTO selectSummonerData(SummonerDTO summonerDTO) {

        return memberRepository.selectSummonerData(summonerDTO);
    }
}
