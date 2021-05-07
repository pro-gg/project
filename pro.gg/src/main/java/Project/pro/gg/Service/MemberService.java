package Project.pro.gg.Service;

import Project.pro.gg.DAO.MemberDAO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface MemberService  {

    public void insert(MemberDTO memberDTO);
    public String selectOne(String id, String passwd);
    public MemberDTO selectMemberOne(String id);
}
