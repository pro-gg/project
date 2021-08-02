package Project.pro.gg.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Project.pro.gg.Model.PostDTO;
import Project.pro.gg.Repository.PostRepository;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	PostRepository postRepository;
	
	@Override
	public void insertPost(PostDTO postDTO) {
		postRepository.insertPost(postDTO);
	}
	
	@Override
	public List<PostDTO> selectPostList(int boardNumber) {
		return postRepository.selectPostList(boardNumber);
	}

	@Override
	public List<PostDTO> selectPastPost(String nickname) {
		return postRepository.selectPastPost(nickname);
	}

	@Override
	public String selectPostContent(String postTitle, String nickname) {
		return postRepository.selectPostContent(postTitle, nickname);
	}

	@Override
	public PostDTO selectPostDetail(int postNumber) {
		return postRepository.selectPostDetail(postNumber);
	}

	@Override
	public void updatePostContent(PostDTO post) {
		postRepository.updatePostContent(post);
	}
}
