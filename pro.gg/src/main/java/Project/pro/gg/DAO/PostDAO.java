package Project.pro.gg.DAO;

import Project.pro.gg.Model.PostDTO;

import java.util.List;

import Project.pro.gg.API.Paging;
import Project.pro.gg.Model.PostDTO;
import Project.pro.gg.Model.ReplyDTO;

public interface PostDAO {
	public void insertPost(PostDTO postDTO);
	
	public List<PostDTO> selectPostList(Paging paging);

    public List<PostDTO> selectPastPost(String nickname);

    public PostDTO selectPostContent(Long postNumber, String nickname);

    public PostDTO selectPostBy_postNumber(int postNumber);

    void updateLookUpCount(PostDTO postDTO);

    public PostDTO selectPostDetail(int postNumber);

    public void updatePostContent(PostDTO post);

    public void postDelete(int postNumber);

    public void updateRecommendCount(PostDTO postDTO);

    public void updateNotRecommendCount(PostDTO postDTO);

    public int countPost(int boardNumber);
    
    public List<PostDTO> selectPostList_By_ConditionCheck(PostDTO postDTO);

    public List<ReplyDTO> selectPastReply(String nickname);
}
