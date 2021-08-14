package Project.pro.gg.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Project.pro.gg.Model.CommentDTO;
import Project.pro.gg.Model.ReplyDTO;

@Service
public interface ReplyService {

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
