package Project.pro.gg.Service;

import Project.pro.gg.Model.CommentDTO;
import Project.pro.gg.Model.ReplyDTO;
import Project.pro.gg.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    ReplyRepository replyRepository;

    public void replyInsert(ReplyDTO replyDTO) {
        replyRepository.replyInsert(replyDTO);
    }

    @Override
    public List<ReplyDTO> callreplyList(Long postNumber) {
        return replyRepository.callreplyList(postNumber);
    }

    @Override
    public ReplyDTO selectReplyBy_replyNumber(int replyNumber) {
        return replyRepository.selectReplyBy_replyNumber(replyNumber);
    }

    @Override
    public void updateRecommendCount(ReplyDTO replyDTO) {
        replyRepository.updateRecommendCount(replyDTO);
    }

    @Override
    public void updateNotRecommendCount(ReplyDTO replyDTO) {
        replyRepository.updateNotRecommendCount(replyDTO);
    }

    @Override
    public void updateReply(ReplyDTO replyDTO) {
        replyRepository.updateReply(replyDTO);
    }

    @Override
    public void replyDelete(ReplyDTO replyDTO) {
        replyRepository.replyDelete(replyDTO);
    }

	@Override
	public void replyCommentInsert(CommentDTO commentDTO) {
		replyRepository.replyCommentInsert(commentDTO);
	}

	@Override
	public List<CommentDTO> callCommentList(Long replyNumber) {
		return replyRepository.callCommentList(replyNumber);
	}

	@Override
	public CommentDTO selectComment(Long commentNumber) {
		return replyRepository.selectComment(commentNumber);
	}

	@Override
	public void updateComment(CommentDTO commentDTO) {
		replyRepository.updateComment(commentDTO);
	}

	@Override
	public void commentDelete(CommentDTO commentDTO) {
		replyRepository.commentDelete(commentDTO);
	}
}
