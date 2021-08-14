package Project.pro.gg.DAO;

import java.util.List;

import Project.pro.gg.Model.CommentDTO;
import Project.pro.gg.Model.ReplyDTO;

public interface ReplyDAO {

    public void replyInsert(ReplyDTO replyDTO);

    public List<ReplyDTO> callreplyList(Long postNumber);

    public ReplyDTO selectReplyBy_replyNumber(int replyNumber);

    public void updateRecommendCount(ReplyDTO replyDTO);

    public void updateNotRecommendCount(ReplyDTO replyDTO);

    public void updateReply(ReplyDTO replyDTO);

    public void replyDelete(ReplyDTO replyDTO);
    
    
    //댓글 답글
    public void replyCommentInsert(CommentDTO commentDTO);
    
    public List<CommentDTO> callCommentList(Long replyNumber);
    
    public CommentDTO selectComment(Long commentNumber);
    
    public void updateComment(CommentDTO commentDTO);

    public void commentDelete(CommentDTO commentDTO);
}
