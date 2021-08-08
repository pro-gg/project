package Project.pro.gg.Repository;

import Project.pro.gg.DAO.ReplyDAO;
import Project.pro.gg.Model.ReplyDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReplyRepository implements ReplyDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public void replyInsert(ReplyDTO replyDTO) {
        sqlSession.insert("reply.insertReply", replyDTO);
    }

    @Override
    public List<ReplyDTO> callreplyList(Long postNumber) {
        return sqlSession.selectList("reply.replyList", postNumber);
    }
}
