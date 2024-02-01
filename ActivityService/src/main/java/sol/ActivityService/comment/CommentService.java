package sol.ActivityService.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.ActivityService.comment.dto.CommentCreationDto;
import sol.ActivityService.post.Post;
import sol.ActivityService.user.User;
import sol.ActivityService.userActivity.Activity;
import sol.ActivityService.util.ResponseDto;
import sol.ActivityService.util.UtilMethods;

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
