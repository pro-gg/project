package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Long postNumber; // 게시글 번호 순번
    private int boardNumber; // 게시판 분류 번호 (1 : 자유 게시판, 2 : 팀원 모집 게시판, 3 : 팁 게시판)
    private String postTitle;
    private String postContent;
    private String nickname;
    private Date postDate;
    private int replyCount;
    private int lookupCount;
    private int postRecommendCount;
    private int postNotRecommendCount;
}
