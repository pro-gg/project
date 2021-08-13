package Project.pro.gg.Repository;

import Project.pro.gg.API.Paging;
import Project.pro.gg.DAO.PostDAO;
import Project.pro.gg.Model.PostDTO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository implements PostDAO {

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void insertPost(PostDTO postDTO) {
		sqlSession.insert("post.insert", postDTO);
	}


	@Override
	public List<PostDTO> selectPostList(Paging paging) {
		return sqlSession.selectList("post.selectPostList", paging);
	}
	
	@Override
	public List<PostDTO> selectPastPost(String nickname) {
		return sqlSession.selectList("post.selectPastPost", nickname);
	}

	@Override
	public String selectPostContent(String postTitle, String nickname) {
		PostDTO postDTO = new PostDTO();
		postDTO.setPostTitle(postTitle);
		postDTO.setNickname(nickname);
		return sqlSession.selectOne("post.selectPostContent", postDTO);
	}

	@Override
	public PostDTO selectPostBy_postNumber(int postNumber) {
		return sqlSession.selectOne("post.selectPostBy_postNumber", postNumber);
	}

	@Override
	public void updateLookUpCount(PostDTO postDTO) {
		sqlSession.update("post.updateLookUpCount", postDTO);
	}

	@Override
	public void postDelete(int postNumber) {
		sqlSession.delete("post.postDelete", postNumber);
	}

	@Override
	public void updateRecommendCount(PostDTO postDTO) {
		sqlSession.update("post.updateRecommendCount", postDTO);
	}

	@Override
	public void updateNotRecommendCount(PostDTO postDTO) {
		sqlSession.update("post.updateNotRecommendCount", postDTO);
	}


	@Override
	public PostDTO selectPostDetail(int postNumber) {
		return sqlSession.selectOne("post.selectPostDetail", postNumber);
	}


	@Override
	public void updatePostContent(PostDTO post) {
		sqlSession.update("post.updatePostContent",post);
	}


	@Override
	public int countPost(int boardNumber) {
		return sqlSession.selectOne("post.countPost", boardNumber);
	}

	@Override
	public List<PostDTO> selectPostList_By_ConditionCheck(PostDTO postDTO) {
		return sqlSession.selectList("post.selectPostList_By_ConditionCheck", postDTO);
	}


}
