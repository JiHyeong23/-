package sol.ActivityService.commentLike;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.ActivityService.comment.Comment;
import sol.ActivityService.comment.CommentRepository;
import sol.ActivityService.commentLike.dto.CommentLikeDto;
import sol.ActivityService.user.User;
import sol.ActivityService.userActivity.Activity;
import sol.ActivityService.util.ResponseDto;
import sol.ActivityService.util.UtilMethods;

@Service
@AllArgsConstructor
public class CommentLikeService {
    private UtilMethods utilMethods;
    private CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    public ResponseDto saveCommentLike(CommentLikeDto commentLikeDto, User user) {
        Comment comment = utilMethods.findComment(commentLikeDto.getCommentId());

        CommentLike commentLike = new CommentLike();
        commentLike.setUser(user);
        commentLike.setComment(comment);
        commentLikeRepository.save(commentLike);

        comment.updateLikeCount();
        commentRepository.save(comment);

        utilMethods.saveActivity(user, Activity.COMMENT_LIKE, commentLike.getId(), comment.getUser());

        return utilMethods.makeSuccessResponseDto("Successfully saved", commentLike.getId());
    }
}
