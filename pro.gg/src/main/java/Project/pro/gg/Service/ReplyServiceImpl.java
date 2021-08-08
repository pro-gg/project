package Project.pro.gg.Service;

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
}
