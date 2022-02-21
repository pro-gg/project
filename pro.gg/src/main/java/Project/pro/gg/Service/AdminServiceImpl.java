package Project.pro.gg.Service;

import Project.pro.gg.DAO.AdminDAO;
import Project.pro.gg.Model.AdminDTO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{


    private final AdminDAO adminRepository;

    @Override
    public String selectAdmin(String adminId, String adminPasswd) {
        String result = "Success";
        AdminDTO adminDTO = adminRepository.selectAdmin(adminId);

        if (adminDTO == null) result = "NotExistId";
        else if (!adminDTO.getAdminPasswd().equals(adminPasswd)) result = "failPasswd";

        return result;
    }

    @Override
    public AdminDTO selectAdminOne(String adminId) {
        return adminRepository.selectAdmin(adminId);
    }

    @Override
    public MemberDTO selectOne(String userid) {
        return adminRepository.selectOne(userid);
    }

    @Override
    public MemberDTO selectByNickName(String nickname) {
        return adminRepository.selectByNickName(nickname);
    }
}
