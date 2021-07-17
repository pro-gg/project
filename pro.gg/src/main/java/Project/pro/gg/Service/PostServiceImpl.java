package Project.pro.gg.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Project.pro.gg.Model.PostDTO;
import Project.pro.gg.Repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	PostRepository postRepository;
	
	@Override
	public void insertPost(PostDTO postDTO) {
		postRepository.insertPost(postDTO);
	}
}
