package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Long postNumber; // 게시글 번호 순번
    private int boardNumber; // 게시판 분류 번호 (1 : 자유 게시판, 2 : 팀원 모집 게시판, 3 : 팁 게시판)
    private String postTitle;
    private String postContent; // 작성된 글 html 문자열 저장
    // 이미지, 동영상과 같이 용량이 큰 파일의 경우 데이터베이스에 그대로 저장하는 것이 아니라 용량이 큰 데이터는 외부에 저장하고 URL로 경로를 호출하는 방식으로 활용해야 한다.
    private String nickname;
    private String postDate;
    private String postTime; // 작성일시가 자정을 넘지 않았을 경우 하루를 넘지 않았을 경우 당일 작성 시간 표시(postDate 값을 기준으로 비교한다.)
    private int replyCount;
    private int lookupCount;
    private int postRecommendCount;
    private int postNotRecommendCount;
}
