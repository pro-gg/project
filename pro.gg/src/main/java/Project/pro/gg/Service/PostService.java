package Project.pro.gg.Service;

import org.springframework.stereotype.Service;

import Project.pro.gg.Model.PostDTO;

@Service
public interface PostService {
	public void insertPost(PostDTO postDTO);
}
