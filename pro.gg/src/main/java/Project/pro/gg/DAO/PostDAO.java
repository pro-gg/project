package Project.pro.gg.DAO;

import Project.pro.gg.Model.PostDTO;

import java.util.List;

public interface PostDAO {
	public void insertPost(PostDTO postDTO);
	
	public List<PostDTO> selectPostList(int boardNumber);

    public List<PostDTO> selectPastPost(String nickname);

    public String selectPostContent(String postTitle, String nickname);
}
