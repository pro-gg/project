package Project.pro.gg.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Project.pro.gg.Service.*;
import com.sun.net.httpserver.HttpsServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import Project.pro.gg.API.Paging;
import Project.pro.gg.Model.CommentDTO;
import Project.pro.gg.Model.MemberDTO;
import Project.pro.gg.Model.PostDTO;
import Project.pro.gg.Model.ReplyDTO;


@Controller
@MultipartConfig(maxRequestSize = 1024*1024*50) //50MB
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController{


    private static final long serialVersionUID = 1L;
    private int maxRequestSize = 1024*1024*50;

    private final PostService postService;
    private final ReplyService replyService;
    private final MemberService memberService;

    private static HttpSession session;

    @GetMapping("/boardList.do")
    public String freeboardList(Model model, Paging paging,
    		@RequestParam("boardNumber") int boardNumber,
    		@RequestParam(value="nowPage", required=false) String nowPage,
    		@RequestParam(value="cntPerPage", required=false) String cntPerPage){
    	
    	//작성된 게시글들 전체 갯수 계산
    	int total = postService.countPost(boardNumber);
    	
    	if(nowPage == null && cntPerPage == null) {
    		nowPage = "1";
    		cntPerPage = "5";
    	} else if(nowPage == null) {
    		nowPage = "1";
    	} else if(cntPerPage == null) {
    		cntPerPage = "5";
    	}
    	
    	paging = new Paging(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage), boardNumber);
    	
        model.addAttribute("boardList", postService.selectPostList(paging));
        model.addAttribute("paging", paging);
        
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
        String path = "WEB-INF/classes/static/images/freeUploadImage/";
        String real_save_path = request.getServletContext().getRealPath("").toString();
        System.out.println(real_save_path);
        StringBuilder save_path = new StringBuilder(real_save_path);
        System.out.println(save_path);

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

        request.setAttribute("url", "/resources/images/freeUploadImage/" + filename);
        request.setAttribute("uploaded", true);
        return request;
    }

    @PostMapping("/postWriting.do")
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
            postDTO.setPostRecommendCount(0);
            postDTO.setPostNotRecommendCount(0);
            postService.insertPost(postDTO);

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

    @GetMapping("/searchPastReply.do")
    public String searchPastReply(@RequestParam("nickname") String nickname, Model model){
        List<ReplyDTO> searchReplyList = new ArrayList<>();
        searchReplyList = postService.selectPastReply(nickname);
        model.addAttribute("searchReplyList", searchReplyList);
        return "../board/pastReply";
    }

    @GetMapping("/postdetail.do")
    public String postDetail(@RequestParam("postNumber") int postNumber, Model model, HttpServletRequest request){
        // 게시글 출력 로직은 승진이형 코드 리베이스 받으면서 가져온다.
        // 지금은 조회수 변경 기능만 구현
        PostDTO postDTO = postService.selectPostBy_postNumber(postNumber);;
        MemberDTO memberDTO = null;

        try{
            session = request.getSession();
            memberDTO = (MemberDTO) session.getAttribute("member");
        }catch (Exception no){
            no.getStackTrace();

            // 로그인이 안 된 상태일 경우 조회수를 높이지 않는다.
            model.addAttribute("post", postDTO);
            return "../board/postDetail";
        }


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
        return "redirect:/board/searchPastPost.do?nickname="+nickname;
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

    @GetMapping("/clickRecommend.do")
    public String clickRecommend(@RequestParam("postNumber") int postNumber, @RequestParam("nickname") String nickname,
                                 HttpServletRequest request) throws JSONException {

        PostDTO postDTO = postService.selectPostBy_postNumber(postNumber);
        MemberDTO memberDTO = memberService.findByNickname(nickname);
        String str_recommendPost = memberDTO.getRecommendpost();
        String str_jsonArray = null;
        // 처음으로 추천 버튼을 눌렀을 때 처리
        if (str_recommendPost == null){
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(postNumber);

            str_jsonArray = jsonArray.toString();
            memberDTO.setRecommendpost(str_jsonArray);

            memberService.updateRecommendPost(memberDTO);

            postDTO.setPostRecommendCount(postDTO.getPostRecommendCount()+1);
            postService.updateRecommendCount(postDTO);
        }else{
            // 처음 추천 버튼을 누른게 아닌경우 처리
            JSONArray jsonArray = new JSONArray(str_recommendPost);
            boolean exist_recommend = false;
            
            // 이미 눌렀던 게시글인지 아닌지 판별(순차 탐색)
            for (int i = 0; i < jsonArray.length(); i++){
                // 현재까지 추천 버튼을 누른 게시글과의 비교
                if (postNumber == (Integer)jsonArray.get(i)){
                    // 추천 버튼을 누른 적 있는 게시글일 경우 처리
                    exist_recommend = true;

                    // 회원 데이터에서 게시글 추천 기록 삭제
                    jsonArray.remove(i);
                    str_jsonArray = jsonArray.toString();
                    memberDTO.setRecommendpost(str_jsonArray);
                    memberService.updateRecommendPost(memberDTO);

                    // 해당 게시글에서 추천 횟수 1회 차감
                    postDTO.setPostRecommendCount(postDTO.getPostRecommendCount()-1);
                    postService.updateRecommendCount(postDTO);
                    break;
                }
            }

            // 반복문을 모두 거쳤음에도 추천 버튼을 누른 게시글들 중에 글 번호가 일치하는 경우가 없는 경우
            // 즉, 이전에 추천 버튼을 누른적이 없는 게시글일 경우 처리
            if (!exist_recommend){
                jsonArray.put(postNumber);
                str_jsonArray = jsonArray.toString();
                memberDTO.setRecommendpost(str_jsonArray);
                memberService.updateRecommendPost(memberDTO);

                postDTO.setPostRecommendCount(postDTO.getPostRecommendCount()+1);
                postService.updateRecommendCount(postDTO);
            }
        }

        session = request.getSession();
        session.setAttribute("member", memberDTO);

        return "redirect:/board/postdetail.do?postNumber="+postNumber;
    }

    @GetMapping("/clickNotRecommend.do")
    public String clickNotRecommend(@RequestParam("postNumber") int postNumber, @RequestParam("nickname") String nickname,
                                    HttpServletRequest request) throws JSONException {

        PostDTO postDTO = postService.selectPostBy_postNumber(postNumber);
        MemberDTO memberDTO = memberService.findByNickname(nickname);
        String str_NotrecommendPost = memberDTO.getNot_recommendpost();
        String str_jsonArray = null;
        // 처음으로 비추천 버튼을 눌렀을 때 처리
        if (str_NotrecommendPost == null){
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(postNumber);

            str_jsonArray = jsonArray.toString();
            memberDTO.setNot_recommendpost(str_jsonArray);

            memberService.updateNotRecommendPost(memberDTO);

            postDTO.setPostNotRecommendCount(postDTO.getPostNotRecommendCount()+1);
            postService.updateNotRecommendCount(postDTO);
        }else{
            // 처음 비추천 버튼을 누른게 아닌경우 처리
            JSONArray jsonArray = new JSONArray(str_NotrecommendPost);
            boolean exist_Notrecommend = false;

            // 이미 눌렀던 게시글인지 아닌지 판별(순차 탐색)
            for (int i = 0; i < jsonArray.length(); i++){
                // 현재까지 비추천 버튼을 누른 게시글과의 비교
                if (postNumber == (Integer)jsonArray.get(i)){
                    // 비추천 버튼을 누른 적 있는 게시글일 경우 처리
                    exist_Notrecommend = true;

                    // 회원 데이터에서 게시글 추천 기록 삭제
                    jsonArray.remove(i);
                    str_jsonArray = jsonArray.toString();
                    memberDTO.setNot_recommendpost(str_jsonArray);
                    memberService.updateNotRecommendPost(memberDTO);

                    // 해당 게시글에서 추천 횟수 1회 차감
                    postDTO.setPostNotRecommendCount(postDTO.getPostNotRecommendCount()-1);
                    postService.updateNotRecommendCount(postDTO);
                    break;
                }
            }

            // 반복문을 모두 거쳤음에도 비추천 버튼을 누른 게시글들 중에 글 번호가 일치하는 경우가 없는 경우
            // 즉, 이전에 비추천 버튼을 누른적이 없는 게시글일 경우 처리
            if (exist_Notrecommend == false){
                jsonArray.put(postNumber);
                str_jsonArray = jsonArray.toString();
                memberDTO.setNot_recommendpost(str_jsonArray);
                memberService.updateNotRecommendPost(memberDTO);

                postDTO.setPostNotRecommendCount(postDTO.getPostNotRecommendCount()+1);
                postService.updateNotRecommendCount(postDTO);
            }
        }

        session = request.getSession();
        session.setAttribute("member", memberDTO);

        return "redirect:/board/postdetail.do?postNumber="+postNumber;
    }

    @GetMapping("/replyregister.do")
    public String replyRegister(@RequestParam("reply") String reply, HttpServletRequest request){
        MemberDTO memberDTO = null;
        ReplyDTO replyDTO = new ReplyDTO();

        session = request.getSession();
        memberDTO = (MemberDTO) session.getAttribute("member");

        Long postNumber = null;
        try{
            JSONObject jsonObject = new JSONObject(reply);

            postNumber = jsonObject.getLong("postNumber");
            String replyDate = jsonObject.getString("replyDate");
            String replyTime = jsonObject.getString("replyTime");
            String replyContent = jsonObject.getString("replyContent");

            replyDTO.setNickname(memberDTO.getNickname());
            replyDTO.setReplyDate(replyDate);
            replyDTO.setReplyTime(replyTime);
            replyDTO.setReplyContent(replyContent);
            replyDTO.setPostNumber(postNumber);
            replyDTO.setReplyRecommendCount(0);
            replyDTO.setReplyNotRecommendCount(0);


        }catch (Exception e){
            e.printStackTrace();
        }
        replyService.replyInsert(replyDTO);
        return "redirect:/board/postdetail.do?postNumber="+postNumber;
    }

    @GetMapping("/callReplyList.do")
    public String callReplyList(@RequestParam("postNumber") Long postNumber, Model model){

        List<ReplyDTO> replyDTOList =  replyService.callreplyList(postNumber);
        Collections.reverse(replyDTOList);
        model.addAttribute("replyDTOList", replyDTOList);
        model.addAttribute("replyListSize", replyDTOList.size());
        return "../board/replyList";
    }

    @PostMapping("/replyRecommendClick.do")
    public String replyRecommendClick(@RequestParam("replyNumber") int replyNumber, @RequestParam("nickname") String nickname,
                                      HttpServletRequest request) throws JSONException {

        ReplyDTO replyDTO = replyService.selectReplyBy_replyNumber(replyNumber);
        MemberDTO memberDTO = memberService.findByNickname(nickname);
        String str_recommendReply = memberDTO.getRecommendreply();
        String str_jsonArray = null;

        if (str_recommendReply == null){
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(replyNumber);

            str_jsonArray = jsonArray.toString();
            memberDTO.setRecommendreply(str_jsonArray);

            memberService.updateRecommendReply(memberDTO);

            replyDTO.setReplyRecommendCount(replyDTO.getReplyRecommendCount()+1);
            replyService.updateRecommendCount(replyDTO);
        }else {
            // 처음 추천 버튼을 누른게 아닌경우 처리
            JSONArray jsonArray = new JSONArray(str_recommendReply);
            boolean exist_recommend = false;

            // 이미 눌렀던 댓글 인지 아닌지 판별(순차 탐색)
            for (int i = 0; i < jsonArray.length(); i++) {
                // 현재까지 추천 버튼을 누른 댓글 과의 비교
                if (replyNumber == (Integer) jsonArray.get(i)) {
                    // 비추천 버튼을 누른 적 있는 댓글일 경우 처리
                    exist_recommend = true;

                    // 회원 데이터에서 댓글 추천 기록 삭제
                    jsonArray.remove(i);
                    str_jsonArray = jsonArray.toString();
                    memberDTO.setRecommendreply(str_jsonArray);
                    memberService.updateRecommendReply(memberDTO);

                    // 해당 댓글에서 추천 횟수 1회 감소
                    replyDTO.setReplyRecommendCount(replyDTO.getReplyRecommendCount() - 1);
                    replyService.updateRecommendCount(replyDTO);
                    break;
                }
            }

            // 반복문을 모두 거쳤음에도 추천 버튼을 누른 게시글들 중에 글 번호가 일치하는 경우가 없는 경우
            // 즉, 이전에 추천 버튼을 누른적이 없는 게시글일 경우 처리
            if (exist_recommend == false) {
                jsonArray.put(replyNumber);
                str_jsonArray = jsonArray.toString();
                memberDTO.setRecommendreply(str_jsonArray);
                memberService.updateRecommendReply(memberDTO);

                replyDTO.setReplyRecommendCount(replyDTO.getReplyRecommendCount() + 1);
                replyService.updateRecommendCount(replyDTO);
            }
        }

        session = request.getSession();
        session.setAttribute("member", memberDTO);

        return "redirect:/board/postdetail.do?postNumber="+replyDTO.getPostNumber();
    }

    @PostMapping("/replyNotRecommendClick.do")
    public String replyNotRecommendClick(@RequestParam("replyNumber") int replyNumber, @RequestParam("nickname") String nickname,
                                         HttpServletRequest request) throws JSONException {

        ReplyDTO replyDTO = replyService.selectReplyBy_replyNumber(replyNumber);
        MemberDTO memberDTO = memberService.findByNickname(nickname);
        String str_NotrecommendReply = memberDTO.getNot_recommendreply();
        String str_jsonArray = null;

        if (str_NotrecommendReply == null){
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(replyNumber);

            str_jsonArray = jsonArray.toString();
            memberDTO.setNot_recommendreply(str_jsonArray);

            memberService.updateNotRecommendReply(memberDTO);

            replyDTO.setReplyNotRecommendCount(replyDTO.getReplyNotRecommendCount()+1);
            replyService.updateNotRecommendCount(replyDTO);
        }else {
            // 처음 추천 버튼을 누른게 아닌경우 처리
            JSONArray jsonArray = new JSONArray(str_NotrecommendReply);
            boolean exist_recommend = false;

            // 이미 눌렀던 댓글 인지 아닌지 판별(순차 탐색)
            for (int i = 0; i < jsonArray.length(); i++) {
                // 현재까지 추천 버튼을 누른 댓글 과의 비교
                if (replyNumber == (Integer) jsonArray.get(i)) {
                    // 비추천 버튼을 누른 적 있는 댓글일 경우 처리
                    exist_recommend = true;

                    // 회원 데이터에서 댓글 추천 기록 삭제
                    jsonArray.remove(i);
                    str_jsonArray = jsonArray.toString();
                    memberDTO.setNot_recommendreply(str_jsonArray);
                    memberService.updateNotRecommendReply(memberDTO);

                    // 해당 댓글에서 추천 횟수 1회 감소
                    replyDTO.setReplyNotRecommendCount(replyDTO.getReplyNotRecommendCount() - 1);
                    replyService.updateNotRecommendCount(replyDTO);
                    break;
                }
            }

            // 반복문을 모두 거쳤음에도 추천 버튼을 누른 게시글들 중에 글 번호가 일치하는 경우가 없는 경우
            // 즉, 이전에 추천 버튼을 누른적이 없는 게시글일 경우 처리
            if (exist_recommend == false) {
                jsonArray.put(replyNumber);
                str_jsonArray = jsonArray.toString();
                memberDTO.setNot_recommendreply(str_jsonArray);
                memberService.updateNotRecommendReply(memberDTO);

                replyDTO.setReplyNotRecommendCount(replyDTO.getReplyNotRecommendCount() + 1);
                replyService.updateNotRecommendCount(replyDTO);
            }
        }

        session = request.getSession();
        session.setAttribute("member", memberDTO);

        return "redirect:/board/postdetail.do?postNumber="+replyDTO.getPostNumber();
    }

    @PostMapping("/replyUpdate.do")
    public String replyUpdate(@RequestParam("replyNumber") String str_replyNumber, @RequestParam("replyContent") String replyContent,
                              @RequestParam("nickname") String nickname, @RequestParam("target") String target, Model model){

        Long replyNumber = Long.parseLong(str_replyNumber);
        ReplyDTO replyDTO = new ReplyDTO();
        // 댓글 수정폼으로 이동
        if (target.equals("contentUpdate")){

            replyDTO.setReplyNumber(replyNumber);
            replyDTO.setNickname(nickname);
            replyDTO.setReplyContent(replyContent);

            model.addAttribute("reply", replyDTO);
            return "../board/replyUpdateForm";
        }
        // 수정 폼에서 수정 버튼이 클릭 되었을 경우 처리
        else if (target.equals("replyUpdate")){
            replyDTO.setReplyNumber(replyNumber);
            replyDTO.setNickname(nickname);
            replyDTO.setReplyContent(replyContent);

            replyService.updateReply(replyDTO);
            int integer_replyNumber = Integer.parseInt(str_replyNumber);
            replyDTO = replyService.selectReplyBy_replyNumber(integer_replyNumber);
            return "redirect:/board/postdetail.do?postNumber="+replyDTO.getPostNumber();
        } // 댓글 삭제 버튼이 눌려졌을 경우 처리
        else{
            replyDTO.setReplyNumber(replyNumber);
            replyDTO.setNickname(nickname);
            replyDTO.setReplyContent(replyContent);

            int integer_replyNumber = Integer.parseInt(str_replyNumber);
            replyDTO = replyService.selectReplyBy_replyNumber(integer_replyNumber);
            Long postNumber = replyDTO.getPostNumber();

            replyService.replyDelete(replyDTO);

            return "redirect:/board/postdetail.do?postNumber="+postNumber;
        }
    }
    
    @GetMapping("/postSearch.do")
    public String postSearch(@RequestParam("searchKeyword") String searchKeyword, @RequestParam("target") String target, Model model){

        // 메소드를 하나만 만들어놓고 동적 쿼리를 통해 두 가지 조건의 검색 처리를 모두 해결한다.
        List<PostDTO> searchPostList = new ArrayList<>();
        PostDTO postDTO = new PostDTO();

        // 페이징 메소드 에서의 활용을 위해 검색 결과 길이 결과를 반환하는 코드를 만들어둔다.
        int total = 0;

        if (target.equals("nickname")){
            postDTO.setNickname(searchKeyword);
            searchPostList = postService.selectPostList_By_ConditionCheck(postDTO);
        }
        else if (target.equals("title")){
            postDTO.setPostTitle(searchKeyword);
            searchPostList = postService.selectPostList_By_ConditionCheck(postDTO);
        }

        // 페이징 메소드에 사용하기 위한 검색 사이즈 크기 반환
        total = searchPostList.size();

        model.addAttribute("searchPostByCondition", searchPostList);
        model.addAttribute("searchKeyword", searchKeyword);
        return "../board/searchPost";
    }
    
    @PostMapping("/addReplyComment.do")
    public String addReplyComment(@RequestParam("comment") String comment, HttpServletRequest request) {
    	MemberDTO memberDTO = null;
        CommentDTO commentDTO;

        session = request.getSession();
        memberDTO = (MemberDTO) session.getAttribute("member");

        Long postNumber = null;
        try{
            JSONObject jsonObject = new JSONObject(comment);

            postNumber = jsonObject.getLong("postNumber");
            String commentDate = jsonObject.getString("commentDate");
            String commentTime = jsonObject.getString("commentTime");
            String commentContent = jsonObject.getString("commentContent");
            Long replyNumber = jsonObject.getLong("replyNumber");
            
            commentDTO = new CommentDTO(replyNumber, commentContent, memberDTO.getNickname(), commentDate, commentTime );
            replyService.replyCommentInsert(commentDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
    	return "redirect:/board/postdetail.do?postNumber="+postNumber;
    }
    
    @GetMapping("/callReplyCommentList.do")
    public String callReplyCommentList(@RequestParam("replyNumber") Long replyNumber, @RequestParam("postNumber") Long postNumber,  Model model){
        List<CommentDTO> commentList =  replyService.callCommentList(replyNumber);
        Collections.reverse(commentList);
        model.addAttribute("replyNumber", replyNumber);
        model.addAttribute("postNumber", postNumber);
        model.addAttribute("commentList", commentList);
        return "../board/replyCommentList";
    }
    
    @GetMapping("/commentUpdate.do")
    public String commentUpdate(@RequestParam("commentNumber") Long commentNumber, @RequestParam("commentContent") String commentContent,
                              @RequestParam("nickname") String nickname, @RequestParam("postNumber") Long postNumber, @RequestParam("target") String target, Model model){

        CommentDTO commentDTO = new CommentDTO();
        // 댓글 수정폼으로 이동
        if (target.equals("update")){

            commentDTO.setCommentNumber(commentNumber);
            commentDTO.setNickname(nickname);
            commentDTO.setCommentContent(commentContent);

            model.addAttribute("comment", commentDTO);
            model.addAttribute("postNumber", postNumber);
            return "../board/commentUpdateForm";
        }
        // 수정 폼에서 수정 버튼이 클릭 되었을 경우 처리
        else if (target.equals("commentUpdate")){
            commentDTO.setCommentNumber(commentNumber);
            commentDTO.setNickname(nickname);
            commentDTO.setCommentContent(commentContent);

            replyService.updateComment(commentDTO);
            return "redirect:/board/postdetail.do?postNumber="+postNumber;
        } // 댓글 삭제 버튼이 눌려졌을 경우 처리
        else{
            commentDTO.setCommentNumber(commentNumber);
            commentDTO.setNickname(nickname);
            commentDTO.setCommentContent(commentContent);

            replyService.commentDelete(commentDTO);

            return "redirect:/board/postdetail.do?postNumber="+postNumber;
        }
    }
}
