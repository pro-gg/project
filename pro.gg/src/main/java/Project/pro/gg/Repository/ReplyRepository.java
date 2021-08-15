package Project.pro.gg.Repository;

import Project.pro.gg.DAO.ReplyDAO;
import Project.pro.gg.Model.CommentDTO;
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

    @Override
    public ReplyDTO selectReplyBy_replyNumber(int replyNumber) {
        return sqlSession.selectOne("reply.selectReplyBy_replyNumber", replyNumber);
    }

    @Override
    public void updateRecommendCount(ReplyDTO replyDTO) {
        sqlSession.update("reply.updateRecommendCount", replyDTO);
    }

    @Override
    public void updateNotRecommendCount(ReplyDTO replyDTO) {
        sqlSession.update("reply.updateNotRecommendCount", replyDTO);
    }

    @Override
    public void updateReply(ReplyDTO replyDTO) {
        sqlSession.update("reply.updateReply", replyDTO);
    }

    @Override
    public void replyDelete(ReplyDTO replyDTO) {
        sqlSession.delete("reply.replyDelete", replyDTO);
    }
    
    //댓글 답글

	@Override
	public void replyCommentInsert(CommentDTO commentDTO) {
		sqlSession.insert("reply.insertReplyComment", commentDTO);
	}

	@Override
	public List<CommentDTO> callCommentList(Long replyNumber) {
		return sqlSession.selectList("reply.commentList", replyNumber);
	}

	@Override
	public CommentDTO selectComment(Long commentNumber) {
		return sqlSession.selectOne("reply.selectComment", commentNumber);
	}

	@Override
	public void updateComment(CommentDTO commentDTO) {
		sqlSession.update("reply.updateComment", commentDTO);
	}

	@Override
	public void commentDelete(CommentDTO commentDTO) {
		sqlSession.delete("reply.commentDelete", commentDTO);
	}
}
