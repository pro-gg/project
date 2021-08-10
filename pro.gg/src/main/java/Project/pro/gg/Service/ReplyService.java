package Project.pro.gg.Service;

import Project.pro.gg.Model.ReplyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReplyService {

    public void replyInsert(ReplyDTO replyDTO);

    public List<ReplyDTO> callreplyList(Long postNumber);

    public ReplyDTO selectReplyBy_replyNumber(int replyNumber);

    public void updateRecommendCount(ReplyDTO replyDTO);

    public void updateNotRecommendCount(ReplyDTO replyDTO);

    public void updateReply(ReplyDTO replyDTO);

    public void replyDelete(ReplyDTO replyDTO);
}
