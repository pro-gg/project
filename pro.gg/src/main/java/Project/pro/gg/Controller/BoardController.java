package Project.pro.gg.Controller;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.PostDTO;
import Project.pro.gg.Service.PostServiceImpl;
import Project.pro.gg.Service.ReplyServiceImpl;



@Controller
@MultipartConfig(maxRequestSize = 1024*1024*50) //50MB
public class BoardController{
    private static final long serialVersionUID = 1L;
    private int maxRequestSize = 1024*1024*50;

    @Autowired
    PostServiceImpl postService;

    @Autowired
    ReplyServiceImpl replyService;

    @GetMapping("/freeboardList.do")
    public String freeboardList(@RequestParam("boardNumber") int boardNumber, Model model){
    	List<PostDTO> postDTOList = postService.selectPostList(boardNumber);
        Collections.reverse(postDTOList);
        model.addAttribute("boardList", postDTOList);
        
        if(boardNumber == 1) {
        	model.addAttribute("postType", "자유게시판");
        }else if(boardNumber == 2) {
        	model.addAttribute("postType", "팀원 모집 게시판");
        }else if(boardNumber == 3) {
        	model.addAttribute("postType", "팁 게시판");
        }
        return "../board/boardList";
    }

    @RequestMapping(value = "/image.do", headers = "content-type=multipart/form-data", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpServletRequest imgUpload(@RequestParam("boardNumber") int boardNumber, HttpServletRequest request,
                                        HttpServletResponse response, @RequestParam MultipartFile upload) throws ServletException, IOException, JSONException {
        // 파일이름 중복성 제거
        UUID uuid = UUID.randomUUID();

        //한글 인코딩
        request.setCharacterEncoding("UTF-8");
        //파라미터로 전달되는 한글 인코딩
        response.setContentType("charset=utf-8");

        // 업로드 파일 이름
        String filename = uuid.toString() + "_" + upload.getOriginalFilename();
        System.out.println("업로드 파일명 : " + filename);

        //파일을 바이트로 변환
        byte[] bytes = upload.getBytes();
        // 이미지를 업로드 할 폴더(주의 : 개발자 폴더 이므로 반드시 ~/images 을 새로고침 해야함)
//        String path = "/WEB-INF/freeUploadImage";
        String path = "resources\\static\\images\\freeUploadImage\\";
        String real_save_path = request.getServletContext().getRealPath("").toString();
//        String real_save_path = request.getServletContext().getRealPath(path)+"\\";
        StringBuilder save_path = new StringBuilder(real_save_path);

        save_path.deleteCharAt(save_path.length()-1);

        for (int i = save_path.length()-1; save_path.charAt(i) != '\\'; i--){
            save_path.deleteCharAt(i);
        }
        save_path.append(path);
        System.out.println(save_path);
        // 서버로 업로드
        // write 메소드의 매개값으로 파일의 총 바이트를 매개값으로 준다.
        // 지정된 바이트를 출력 스트림에 쓴다.(출력하기 위해서)
        FileOutputStream out = new FileOutputStream(new File(save_path+filename));
        out.write(bytes);

        try {
            // 이미지 업로드 시간을 벌기 위한 시간 딜레이
            Thread.sleep(5000);
        }catch(Exception e) {

        }

        request.setAttribute("url", "/images/freeUploadImage/" + filename);
        request.setAttribute("uploaded", true);
        return request;
    }

    @GetMapping("/postWriting.do")
    public String postWriting(@RequestParam("post") String post, HttpServletRequest request){
        int boardNumber = 0;
        HttpSession session = request.getSession();
        MemberDTO member = (MemberDTO)session.getAttribute("member");
        try{
            JSONObject jsonObject = new JSONObject(post);
            String title = jsonObject.getString("title");
            String content = jsonObject.getString("writedPosting");
            String postDate = jsonObject.getString("postDate");
            String postTime = jsonObject.getString("postTime");
            boardNumber = jsonObject.getInt("boardNumber");

            PostDTO postDTO = new PostDTO();
            postDTO.setBoardNumber(boardNumber);
            postDTO.setPostContent(content);
            postDTO.setPostTitle(title);
            postDTO.setNickname(member.getNickname());
            postDTO.setPostDate(postDate);
            postDTO.setPostTime(postTime);
            postDTO.setLookupCount(0);
            postService.insertPost(postDTO);

            System.out.println(title);
            System.out.println(content); // 이미지 태그에 업로드한 이미지가 삽입 되어야 한다.(현재는 img 태그만 넘어와 있는 상태)
        }catch (Exception e){
            e.printStackTrace();
        }
        return "../board/freeboard";
    }

    @GetMapping("/searchPastPost.do")
    public String searchPastPost(@RequestParam("nickname") String nickname, Model model){

        List<PostDTO> searchPostList = new ArrayList<>();
        searchPostList = postService.selectPastPost(nickname);
        model.addAttribute("searchPostList", searchPostList);
        return "../board/pastPost";
    }

    @GetMapping("/callPostContent.do")
    public String callPostContent(@RequestParam("postTitle") String postTitle, @RequestParam("nickname") String nickname,
                                  @RequestParam("postNumber") Long postNUmber, Model model){
        PostDTO postDTO = new PostDTO();
        String postContent = postService.selectPostContent(postTitle, nickname);
        postDTO.setNickname(nickname);
        postDTO.setPostTitle(postTitle);
        postDTO.setPostContent(postContent);
        postDTO.setPostNumber(postNUmber);
        model.addAttribute("selectPostContent", postDTO);
        return "../board/printPostContent";
    }

    @GetMapping("/postdetail.do")
    public String postDetail(@RequestParam("postNumber") int postNumber, Model model){
        // 게시글 출력 로직은 승진이형 코드 리베이스 받으면서 가져온다.
        // 지금은 조회수 변경 기능만 구현

        PostDTO postDTO = postService.selectPostBy_postNumber(postNumber);
        HttpSession session = MemberController.session;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");

        // 작성자가 아닌 사람이 클릭 했을 경우 조회수 증가
        // 작성자 본인이 클릭한 경우 조회수가 증가하지 않게끔 한다.
        if (!memberDTO.getNickname().equals(postDTO.getNickname())){
            postDTO.setLookupCount(postDTO.getLookupCount()+1);
            postService.updateLookUpCount(postDTO);
        }

        model.addAttribute("post", postDTO);

        return "../board/postDetail";
    }

    @GetMapping("/postDelete.do")
    public String postDelete(@RequestParam("postNumber") int postNumber, @RequestParam("nickname") String nickname){

        // 일단 지금은 간단하게 끝내지만 나중에 댓글기능이 구현되었을 땐
        // 글을 삭제할 때 댓글과의 연관관계 또한 고려해서 로직을 다시 짜야 한다.
        postService.postDelete(postNumber);
        return "redirect:/searchPastPost.do?nickname="+nickname;
    }

    @GetMapping("/postModify.do")
    public String postModify(@RequestParam("postNumber")int postNumber, Model model) {
    	PostDTO postDTO = postService.selectPostDetail(postNumber);

    	model.addAttribute("post", postDTO);

    	return "../board/postUpdate";
    }

    @GetMapping("/postUpdate.do")
    public String postUpdate(@RequestParam("post") String post) {
    	JSONObject jsonObject;
    	PostDTO postDTO = new PostDTO();
		try {
			jsonObject = new JSONObject(post);
	        postDTO.setPostTitle(jsonObject.getString("title"));
	        postDTO.setPostContent(jsonObject.getString("writedPosting"));
	        postDTO.setPostNumber(jsonObject.getLong("postNumber"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

    	postService.updatePostContent(postDTO);
    	return "../board/freeboard";
    }

}
