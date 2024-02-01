package sol.NewsFeedService.comment.dto;

import lombok.Getter;

@Getter
public class CommentCreationDto {
    private String body;
    private Long postId;
}
