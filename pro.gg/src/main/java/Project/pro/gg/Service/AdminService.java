package Project.pro.gg.Service;

import Project.pro.gg.Model.AdminDTO;
import Project.pro.gg.Model.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    public String selectAdmin(String adminId, String adminPasswd);
    public AdminDTO selectAdminOne(String adminId);
    public MemberDTO selectOne(String userid);

    public MemberDTO selectByNickName(String nickname);
}
