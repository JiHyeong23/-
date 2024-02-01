package sol.NewsFeedService.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.NewsFeedService.comment.dto.CommentCreationDto;
import sol.NewsFeedService.post.Post;
import sol.NewsFeedService.user.User;
import sol.NewsFeedService.userActivity.Activity;
import sol.NewsFeedService.util.ResponseDto;
import sol.NewsFeedService.util.UtilMethods;

@Service
@AllArgsConstructor
public class CommentService {
    private UtilMethods utilMethods;
    private CommentRepository commentRepository;

    public ResponseDto saveComment(CommentCreationDto commentCreationDto, User user) {
        Post post = utilMethods.findPost(commentCreationDto.getPostId());
        Comment comment = Comment.builder().body(commentCreationDto.getBody()).post(post).user(user).build();
        commentRepository.save(comment);

        utilMethods.saveActivity(user, Activity.COMMENT, comment.getId(), comment.getPost().getUser());

        return utilMethods.makeSuccessResponseDto("Successfully saved", comment.getBody());
    }
}
