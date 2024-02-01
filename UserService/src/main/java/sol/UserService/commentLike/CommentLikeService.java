package sol.UserService.commentLike;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.UserService.comment.Comment;
import sol.UserService.comment.CommentRepository;
import sol.UserService.commentLike.dto.CommentLikeDto;
import sol.UserService.user.User;
import sol.UserService.userActivity.Activity;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

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
