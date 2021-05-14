package Project.pro.gg.DAO;

import Project.pro.gg.Model.AdminDTO;
import Project.pro.gg.Model.MemberDTO;

public interface AdminDAO {

    public AdminDTO selectAdmin(String adminId);
    public MemberDTO selectOne(String userid);

    public MemberDTO selectByNickName(String nickname);
}
