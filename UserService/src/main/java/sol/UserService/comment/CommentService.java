package sol.UserService.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.UserService.comment.dto.CommentCreationDto;
import sol.UserService.post.Post;
import sol.UserService.user.User;
import sol.UserService.userActivity.Activity;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

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
