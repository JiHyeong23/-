package sol.UserService.comment.dto;

import lombok.Getter;

@Getter
public class CommentCreationDto {
    private String body;
    private Long postId;
}
