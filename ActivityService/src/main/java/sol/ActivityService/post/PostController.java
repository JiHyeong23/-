package sol.ActivityService.post;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sol.ActivityService.post.dto.PostCreationDto;
import sol.ActivityService.user.User;
import sol.ActivityService.util.ResponseDto;
import sol.ActivityService.util.UtilMethods;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private UtilMethods utilMethods;
    private PostService postService;

    //test
    @GetMapping
    public String hello() {
        return "activity-service";
    }
    @PostMapping
    public ResponseEntity createPost(@RequestBody PostCreationDto postCreationDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        ResponseDto responseDto = postService.savePost(postCreationDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
