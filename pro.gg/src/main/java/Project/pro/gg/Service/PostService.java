package Project.pro.gg.Service;

import org.springframework.stereotype.Service;

import Project.pro.gg.Model.PostDTO;

import java.util.List;

@Service
public interface PostService {
	public void insertPost(PostDTO postDTO);

    List<PostDTO> selectPastPost(String nickname);
}
