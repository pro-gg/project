package Project.pro.gg.DAO;

import Project.pro.gg.Model.PostDTO;

import java.util.List;

import Project.pro.gg.API.Paging;
import Project.pro.gg.Model.PostDTO;

public interface PostDAO {
	public void insertPost(PostDTO postDTO);
	
	public List<PostDTO> selectPostList(Paging paging);

    public List<PostDTO> selectPastPost(String nickname);

    public String selectPostContent(String postTitle, String nickname);

    public PostDTO selectPostBy_postNumber(int postNumber);

    void updateLookUpCount(PostDTO postDTO);

    public PostDTO selectPostDetail(int postNumber);

    public void updatePostContent(PostDTO post);

    public void postDelete(int postNumber);

    public void updateRecommendCount(PostDTO postDTO);
    
    public int countPost(int boardNumber);
}
