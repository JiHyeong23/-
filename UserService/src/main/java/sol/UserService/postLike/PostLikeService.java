package sol.UserService.postLike;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.UserService.post.Post;
import sol.UserService.postLike.dto.PostLikeDto;
import sol.UserService.user.User;
import sol.UserService.userActivity.Activity;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

@Service
@AllArgsConstructor
public class PostLikeService {
    private UtilMethods utilMethods;
    private PostLikeRepository postLikeRepository;
    public ResponseDto savePostLike(PostLikeDto postLikeDto, User user) {
        Post post = utilMethods.findPost(postLikeDto.getPostId());

        PostLike postLike = new PostLike();
        postLike.setUser(user);
        postLike.setPost(post);
        postLikeRepository.save(postLike);

        post.updateLikeCount();

        utilMethods.saveActivity(user, Activity.POST_LIKE, postLike.getId(), post.getUser());

        return utilMethods.makeSuccessResponseDto("Successfully saved", postLike.getId());
    }
}
