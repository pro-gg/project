package Project.pro.gg.Service;

import Project.pro.gg.DAO.PostDAO;
import Project.pro.gg.Model.ReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Project.pro.gg.API.Paging;
import Project.pro.gg.Model.PostDTO;
import Project.pro.gg.Repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{


	private final PostDAO postRepository;
	
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
	public List<ReplyDTO> selectPastReply(String nickname) {
		return postRepository.selectPastReply(nickname);
	}

	@Override
	public PostDTO selectPostContent(Long postNumber, String nickname) {
		return postRepository.selectPostContent(postNumber, nickname);
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

	@Override
	public void updateNotRecommendCount(PostDTO postDTO) {
		postRepository.updateNotRecommendCount(postDTO);
	}
	
	@Override
	public List<PostDTO> selectPostList_By_ConditionCheck(PostDTO postDTO) {
		return postRepository.selectPostList_By_ConditionCheck(postDTO);
	}
}
