package Project.pro.gg.DAO;

import Project.pro.gg.Model.PostDTO;

import java.util.List;

public interface PostDAO {
	public void insertPost(PostDTO postDTO);

    public List<PostDTO> selectPastPost(String nickname);
}
