package sol.UserService.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.UserService.post.dto.PostCreationDto;
import sol.UserService.user.User;
import sol.UserService.userActivity.Activity;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

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
