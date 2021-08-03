package Project.pro.gg.Service;

import org.springframework.stereotype.Service;

import Project.pro.gg.API.Paging;
import Project.pro.gg.Model.PostDTO;

import java.util.List;

@Service
public interface PostService {
	public void insertPost(PostDTO postDTO);
	
	public List<PostDTO> selectPostList(Paging paging);

    public List<PostDTO> selectPastPost(String nickname);

    public String selectPostContent(String postTitle, String nickname);

    public PostDTO selectPostBy_postNumber(int postNumber);

    public void updateLookUpCount(PostDTO postDTO);

    public void postDelete(int postNumber);

    public PostDTO selectPostDetail(int postNumber);

    public void updatePostContent(PostDTO post);

    public int countPost(int boardNumber);

    public void updateRecommendCount(PostDTO postDTO);
}
