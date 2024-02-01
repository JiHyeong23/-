package sol.NewsFeedService.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.NewsFeedService.post.dto.PostCreationDto;
import sol.NewsFeedService.user.User;
import sol.NewsFeedService.userActivity.Activity;
import sol.NewsFeedService.util.ResponseDto;
import sol.NewsFeedService.util.UtilMethods;

@Service
@AllArgsConstructor
public class PostService {
    private UtilMethods utilMethods;
    private PostRepository postRepository;
    private PostMapper postMapper;

    public ResponseDto savePost(PostCreationDto postCreationDto, User user) {
        Post post = postMapper.creationDtoToPost(postCreationDto);
        post.setUser(user);
        postRepository.save(post);

        utilMethods.saveActivity(user, Activity.POST, post.getId(), user);

        return utilMethods.makeSuccessResponseDto("Successfully saved", post.getTitle());
    }
}
