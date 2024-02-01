package sol.NewsFeedService.commentLike;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.NewsFeedService.comment.Comment;
import sol.NewsFeedService.comment.CommentRepository;
import sol.NewsFeedService.commentLike.dto.CommentLikeDto;
import sol.NewsFeedService.user.User;
import sol.NewsFeedService.userActivity.Activity;
import sol.NewsFeedService.util.ResponseDto;
import sol.NewsFeedService.util.UtilMethods;

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
