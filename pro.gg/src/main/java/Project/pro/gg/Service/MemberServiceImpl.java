package Project.pro.gg.Service;

import Project.pro.gg.Model.MemberDTO;
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
    public String selectOne(String id, String passwd) {
        String result = "Success";
        MemberDTO memberDTO = memberRepository.selectOne(id);

        if (memberDTO == null) result = "NotExistId";
        else if (!memberDTO.getPasswd().equals(passwd)) result = "failPasswd";

        return result;
    }

    @Override
    public MemberDTO selectMemberOne(String id) {
        MemberDTO memberDTO = memberRepository.selectOne(id);
        return memberDTO;
    }
}
