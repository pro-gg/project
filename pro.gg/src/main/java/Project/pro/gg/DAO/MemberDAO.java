package Project.pro.gg.DAO;

import Project.pro.gg.Model.MemberDTO;

public interface MemberDAO {

    public void insert(MemberDTO memberDTO);
    public MemberDTO selectOne(String id);
}
