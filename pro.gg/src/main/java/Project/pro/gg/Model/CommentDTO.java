package Project.pro.gg.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long commentNumber;
    private Long replyNumber;
    private String commentContent;
    private String nickname;
    private String commentDate;
    private String commentTime;
    
    public CommentDTO(Long replyNumber, String commentContent, String nickname, String commentDate, String commentTime ) {
    	this.replyNumber = replyNumber;
    	this.commentContent = commentContent;
    	this.nickname = nickname;
    	this.commentDate = commentDate;
    	this.commentTime = commentTime;
    }
}
