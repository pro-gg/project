package Project.pro.gg.Controller;

import Project.pro.gg.Service.PostServiceImpl;
import Project.pro.gg.Service.ReplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

@Controller
@MultipartConfig(maxRequestSize = 1024*1024*50) //50MB
public class BoardController{
    private static final long serialVersionUID = 1L;
    private int maxRequestSize = 1024*1024*50;

    @Autowired
    PostServiceImpl postService;

    @Autowired
    ReplyServiceImpl replyService;

    @RequestMapping(value = "/image.do", headers = "content-type=multipart/form-data", method = {RequestMethod.GET, RequestMethod.POST})
    public void imgUpload(@RequestParam("boardNumber") int boardNumber, HttpServletRequest request,
                            HttpServletResponse response, @RequestParam MultipartFile upload) throws ServletException, IOException {

        // 파일이름 중복성 제거
        UUID uuid = UUID.randomUUID();

        //한글 인코딩
        request.setCharacterEncoding("UTF-8");
        //파라미터로 전달되는 한글 인코딩
//        response.setContentType("charset=utf-8");
        
        // 업로드 파일 이름
        String filename = uuid.toString() + "_" + upload.getOriginalFilename();
        System.out.println("업로드 파일명 : " + filename);

        //파일을 바이트로 변환
        byte[] bytes = upload.getBytes();

        // 이미지를 업로드 할 폴더(주의 : 개발자 폴더 이므로 반드시 ~/images 을 새로고침 해야함)
        String path = "/WEB-INF/freeUploadImage";
        String real_save_path = request.getServletContext().getRealPath(path)+"\\";

        // 서버로 업로드
        // write 메소드의 매개값으로 파일의 총 바이트를 매개값으로 준다.
        // 지정된 바이트를 출력 스트림에 쓴다.(출력하기 위해서)
        FileOutputStream out = new FileOutputStream(new File(real_save_path+filename));
        out.write(bytes);
        
        // 클라이언트에 결과표시 : ckeditor 자체에서 사용되는 파라미터 이름
        String callback = request.getParameter("CKEditorFuncNum");

        // 서버 => 클라이언트로 텍스트 전송(자바스크립트 실행)
        PrintWriter printWriter = response.getWriter();
        String fileUrl = request.getContextPath()+"\\freeUploadImage\\"+filename;

        printWriter.println("<script>window.parent/CKEDITOR.tools.callFunction("+callback+",'"+fileUrl+"','이미지가 업로드 되었습니다.'"+"</script>");
        printWriter.flush();
        System.out.println(fileUrl);
        System.out.println(real_save_path);
    }

    @GetMapping("/freeboardList.do")
    public String freeboardList(Model model){
        return "freeboardList";
    }
}
