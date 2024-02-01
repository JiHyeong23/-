package sol.ActivityService.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.ActivityService.post.dto.PostCreationDto;
import sol.ActivityService.user.User;
import sol.ActivityService.userActivity.Activity;
import sol.ActivityService.util.ResponseDto;
import sol.ActivityService.util.UtilMethods;

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
