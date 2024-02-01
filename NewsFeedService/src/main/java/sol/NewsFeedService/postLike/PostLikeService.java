package sol.NewsFeedService.postLike;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.NewsFeedService.post.Post;
import sol.NewsFeedService.postLike.dto.PostLikeDto;
import sol.NewsFeedService.user.User;
import sol.NewsFeedService.userActivity.Activity;
import sol.NewsFeedService.util.ResponseDto;
import sol.NewsFeedService.util.UtilMethods;

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
