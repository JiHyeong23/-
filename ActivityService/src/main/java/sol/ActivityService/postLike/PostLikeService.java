package sol.ActivityService.postLike;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.ActivityService.post.Post;
import sol.ActivityService.postLike.dto.PostLikeDto;
import sol.ActivityService.user.User;
import sol.ActivityService.userActivity.Activity;
import sol.ActivityService.util.ResponseDto;
import sol.ActivityService.util.UtilMethods;

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
