package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDTO {

    private Long replyNumber; // 댓글 번호 순번
    private Long postNumber;
    private Blob replyContent;
    private String nickname;
    private String replyDate;
    private int replyRecommendCount;
    private int replyNotRecommendCount;
}
