package Project.pro.gg.Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public String freeboardList(Model model){
        return "freeboardList";
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
            postService.insertPost(postDTO);

            System.out.println(title);
            System.out.println(content); // 이미지 태그에 업로드한 이미지가 삽입 되어야 한다.(현재는 img 태그만 넘어와 있는 상태)
        }catch (Exception e){
            e.printStackTrace();
        }
        if (boardNumber == 1){
            return "../board/freeboard";
        }else if (boardNumber == 2) {
            return "../board/crewRecruitBoard";
        }else{
            return "../board/tipboard";
        }
    }

    @GetMapping("/searchPastPost.do")
    public String searchPastPost(@RequestParam("nickname") String nickname, Model model){

        List<PostDTO> searchPostList = new ArrayList<>();
        searchPostList = postService.selectPastPost(nickname);
        model.addAttribute("searchPostList", searchPostList);
        return "../board/pastPost";
    }

}
