package Project.pro.gg.Controller;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

import org.apache.commons.io.FilenameUtils;

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

        // 이미지 경로 base64 인코딩
        // base64 인코딩 참조(https://nowonbun.tistory.com/476)
        File file = new File(save_path+filename);
        byte[] data = new byte[(int)file.length()];
        try(FileInputStream stream = new FileInputStream(file)){
            stream.read(data, 0, data.length);
        }catch (Exception e){
            e.printStackTrace();
        }

        byte[] binary = data;
        String encodedURL = Base64.getEncoder().encodeToString(binary);
//        System.out.println(encodedURL);

        String ext = FilenameUtils.getExtension(filename);
        if (ext.equals("JPG") || ext.equals("jpg")) ext = "jpeg"; // 확장자 변환
        String base64FrontURL = "data:image/"+ext+";base64,";
        String base64FullURL = base64FrontURL + new String(encodedURL);
        System.out.println(encodedURL);

        //base64 형태로 인코딩된 이미지에서 파일의 이름을 추출해 내는건 불가능하다
        //그러므로 base64 형태로 인코딩된 형태를 키 값으로 가지고 해당 하는 파일의 진짜 이름을 필드 값으로 갖는 테이블을 따로 만들어서
        //게시판에 업로드 되는 이미지를 저장하자.
        //업로드 되는 이미지의 경로와 파일 이름 까지 모두 일치하는 데이터가 들어오게 될 경우 따로 데이터베이스에 저장하는 것이 불가능 하므로
        //기존에 데이터베이스에 저장되어 있는 파일을 불러와서 업로드 시키는 방향으로 대체한다.

        request.setAttribute("url", base64FullURL);
//        request.setAttribute("url", save_path+filename);
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
            boardNumber = jsonObject.getInt("boardNumber");

            PostDTO postDTO = new PostDTO();
            postDTO.setBoardNumber(boardNumber);
            postDTO.setPostContent(content);
            postDTO.setPostTitle(title);
            postDTO.setNickname(member.getNickname());
//            postService.insertPost(postDTO);

            System.out.println(title);
            System.out.println(content); // 이미지 태그에 업로드한 이미지가 삽입 되어야 한다.(현재는 img 태그만 넘어와 있는 상태)

            // 글 작성을 통해 넘어온 base64 타입 이미지를 디코딩 해줘야 한다.
            // 일단 넘어온 내용 중에서 base64 타입 데이터를 추출해 내야 한다.

            // 별로 좋은 로직은 아닌것 같음....
            String base64STR = "";
            if (content.contains("img src")){
                System.out.println("이미지 포함되어 있음");
                for (int i = 0; i < content.length(); i++){
                    if (content.charAt(i) == 's') {
                        i++;
                        if (content.charAt(i) == 'r'){
                            i++;
                            if (content.charAt(i) == 'c'){
                                i += 3;
                                while(content.charAt(i) != '\"'){
                                    base64STR = base64STR + content.charAt(i);
                                    i++;
                                }
                            }
                        }
                    }
                }
            }
//            System.out.println("base64 : " + base64STR);

            // ,까지 문자열을 잘라야 한다.
            System.out.println(base64STR);
            System.out.println();
            System.out.println();
            for (int i = 0; i < base64STR.length(); i++){
                if (base64STR.charAt(i) == ','){
                    i++;
                    base64STR = base64STR.substring(i, base64STR.length());
                    break;
                }
            }

            // 추출해낸 base64 데이터를 원래 경로가 되게끔 다시 디코딩 해줘야 한다.
            // 아직 해결중
//            System.out.println("base64 : " + base64STR);
            URLDecoder.decode(base64STR, "UTF-8");
            byte[] decodedURL = org.apache.commons.codec.binary.Base64.decodeBase64(base64STR);
            File file = new File(new String(decodedURL));
            System.out.println(file.getAbsolutePath());

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

}
