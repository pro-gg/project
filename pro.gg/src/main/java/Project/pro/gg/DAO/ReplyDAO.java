package Project.pro.gg.DAO;

import Project.pro.gg.Model.ReplyDTO;

import java.util.List;

public interface ReplyDAO {

    public void replyInsert(ReplyDTO replyDTO);

    public List<ReplyDTO> callreplyList(Long postNumber);
}
