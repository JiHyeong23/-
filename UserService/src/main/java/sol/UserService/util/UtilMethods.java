package sol.UserService.util;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import sol.UserService.comment.Comment;
import sol.UserService.comment.CommentRepository;
import sol.UserService.follow.FollowRepository;
import sol.UserService.post.Post;
import sol.UserService.post.PostRepository;
import sol.UserService.security.JwtHelper;
import sol.UserService.user.User;
import sol.UserService.user.UserRepository;
import sol.UserService.userActivity.Activity;
import sol.UserService.userActivity.UserActivity;
import sol.UserService.userActivity.UserActivityRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@AllArgsConstructor
public class UtilMethods {
    private JwtHelper jwtHelper;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private FollowRepository followRepository;
    private UserActivityRepository userActivityRepository;

    public User parseTokenForUser(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").substring(7);
            String email = jwtHelper.getEmailFromJwtToken(token);
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "토큰이 없습니다");
        }
    }

    public ResponseDto makeSuccessResponseDto(String message) {
        return ResponseDto.builder()
                .result(Result.SUCCESS).message(message)
                .build();
    }

    public <T>ResponseDto makeSuccessResponseDto(String message, T response) {
        return ResponseDto.builder()
                .result(Result.SUCCESS).message(message).response(response)
                .build();
    }

    public <T>ResponseDto makeFailResponseDto(String message, T response) {
        return ResponseDto.builder()
                .result(Result.FAIL).message(message).response(response)
                .build();
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId).get();
    }
    public User findUser(String email) {return userRepository.findByEmail(email);}
    public User findUser(Long userId) {return userRepository.findById(userId).get();}
    public Comment findComment(Long commentId) {return commentRepository.findById(commentId).get();}
    public List<User> getFollowing(User user) {
        return followRepository.findAllByFollowing(user);

    }
    public void saveActivity(User user, Activity activity, Long activityId, User contentBy) {
        UserActivity userActivity = UserActivity.builder()
                .user(user).activity(activity).activityId(activityId).contentedBy(contentBy).build();
        userActivityRepository.save(userActivity);
    }
}
