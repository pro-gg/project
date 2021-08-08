package Project.pro.gg.Service;

import Project.pro.gg.Model.ReplyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReplyService {

    public void replyInsert(ReplyDTO replyDTO);

    public List<ReplyDTO> callreplyList(Long postNumber);
}
