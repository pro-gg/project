package Project.pro.gg.Repository;

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
	public List<PostDTO> selectPostList(int boardNumber) {
		String sql = "SELECT postNumber, postTitle, postContent, postDate, postTime, nickname, lookupCount FROM boardpost WHERE boardNumber = "+boardNumber;
		return sqlSession.selectList("post.selectPostList", sql);
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
}
