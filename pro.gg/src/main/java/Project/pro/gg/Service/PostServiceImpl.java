package Project.pro.gg.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Project.pro.gg.API.Paging;
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
	public List<PostDTO> selectPostList(Paging paging) {
		return postRepository.selectPostList(paging);
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
	public PostDTO selectPostBy_postNumber(int postNumber) {
		return postRepository.selectPostBy_postNumber(postNumber);
	}

	@Override
	public void updateLookUpCount(PostDTO postDTO) {
		postRepository.updateLookUpCount(postDTO);
	}

	@Override
	public void postDelete(int postNumber) {
		postRepository.postDelete(postNumber);
	}

	@Override
	public PostDTO selectPostDetail(int postNumber) {
		return postRepository.selectPostDetail(postNumber);
	}

	@Override
	public void updatePostContent(PostDTO post) {
		postRepository.updatePostContent(post);
	}

	@Override
	public int countPost(int boardNumber) {
		return postRepository.countPost(boardNumber);
	}

	@Override
	public void updateRecommendCount(PostDTO postDTO) {
		postRepository.updateRecommendCount(postDTO);
	}
}
