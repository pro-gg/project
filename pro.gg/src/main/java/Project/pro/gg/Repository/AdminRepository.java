package Project.pro.gg.Repository;

import Project.pro.gg.DAO.AdminDAO;
import Project.pro.gg.Model.AdminDTO;
import Project.pro.gg.Model.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepository implements AdminDAO {


    private final SqlSession sqlSession;

    @Override
    public AdminDTO selectAdmin(String adminId) {
        return sqlSession.selectOne("admin.selectAdmin", adminId);
    }

    @Override
    public MemberDTO selectOne(String userid) {
        return sqlSession.selectOne("admin.selectOne", userid);
    }

    @Override
    public MemberDTO selectByNickName(String nickname) {
        return sqlSession.selectOne("admin.selectByNickname", nickname);
    }
}
